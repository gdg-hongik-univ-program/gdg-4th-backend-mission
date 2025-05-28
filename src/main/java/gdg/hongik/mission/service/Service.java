package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.*;
import gdg.hongik.mission.model.Item;
import gdg.hongik.mission.purchase.PurchaseItem;
import gdg.hongik.mission.purchase.PurchaseRecord;
import gdg.hongik.mission.reservation.Reservation;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import gdg.hongik.mission.model.User
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InventoryService {
    private final Map<String, Item> items = new ConcurrentHashMap<>();
    private final Map<String, Reservation> reservations = new ConcurrentHashMap<>();
    private final Map<String, List<PurchaseRecord>> purchaseHistory = new ConcurrentHashMap<>();
    private long idSeq = 1;

    @PostConstruct
    public void init() {
        addNewItem("apple", 1000, 100);
        addNewItem("banana", 1500, 50);
    }

    public synchronized Item addNewItem(String name, int price, int stock) {
        if (items.containsKey(name)) {
            throw new IllegalArgumentException("Item already exists: " + name);
        }
        Item itm = new Item((int)idSeq++, name, price, stock);
        items.put(name, itm);
        return itm;
    }

    public Item getItem(String name) {
        Item itm = items.get(name);
        if (itm == null) throw new NoSuchElementException("Not found: " + name);
        return itm;
    }


    public synchronized PurchaseResponse purchase(String consumer, List<ItemCount> order) {
        int total = 0;
        List<PurchaseItem> bought = new ArrayList<>();
        for (ItemCount ic : order) {
            Item item = getItem(ic.getName());
            if (item.getStock() < ic.getCount()) {
                throw new IllegalArgumentException("Insufficient stock: " + ic.getName());
            }
            item.setStock(item.getStock() - ic.getCount());
            int cost = item.getPrice() * ic.getCount();
            total += cost;
            bought.add(new PurchaseItem(ic.getName(), ic.getCount(), cost));
        }
        purchaseHistory.computeIfAbsent(consumer, k -> new ArrayList<>())
                .add(new PurchaseRecord(order));
        return new PurchaseResponse(total, bought);
    }

    public synchronized void reserve(String consumer, List<ItemCount> order) {
        if (reservations.containsKey(consumer)) {
            throw new IllegalArgumentException("Already reserved: " + consumer);
        }
        for (ItemCount ic : order) {
            Item itm = getItem(ic.getName());
            if (itm.getStock() < ic.getCount()) {
                throw new IllegalArgumentException("Insufficient stock: " + ic.getName());
            }
            itm.setStock(itm.getStock() - ic.getCount());
        }
        reservations.put(consumer, new Reservation(consumer, order));
    }

    public synchronized void cancelReserve(String actor, String consumer) {
        Reservation res = reservations.get(consumer);
        if (res == null) {
            throw new NoSuchElementException("No reservation for: " + consumer);
        }
        if (!actor.equals(consumer)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed");
        }
        for (ItemCount ic : res.getItems()) {
            Item itm = getItem(ic.getName());
            itm.setStock(itm.getStock() + ic.getCount());
        }
        reservations.remove(consumer);
    }

    public List<Reservation> listReservations() {
        return new ArrayList<>(reservations.values());
    }

    public List<PurchaseRecord> getPurchaseRecords(String consumer) {
        return purchaseHistory.getOrDefault(consumer, Collections.emptyList());
    }

    public StatisticsResponse getStatistics(String consumer) {
        List<PurchaseRecord> recs = getPurchaseRecords(consumer);
        Map<String, int[]> agg = new HashMap<>(); // name -> {totalCount, orderCount}
        for (PurchaseRecord r : recs) {
            for (ItemCount ic : r.getItems()) {
                agg.computeIfAbsent(ic.getName(), k -> new int[2]);
                int[] arr = agg.get(ic.getName());
                arr[0] += ic.getCount();
                arr[1]++;
            }
        }
        List<StatisticItem> stats = new ArrayList<>();
        for (String iname : items.keySet()) {
            int[] arr = agg.getOrDefault(iname, new int[]{0,0});
            int totalCount = arr[0], orderCount = arr[1];
            double avg = orderCount > 0 ? (double) totalCount / orderCount : 0;
            stats.add(new StatisticItem(iname, totalCount, avg));
        }
        return new StatisticsResponse(stats);
    }

    public synchronized AddStockResponse addStock(String name, int count) {
        Item itm = getItem(name);
        itm.setStock(itm.getStock() + count);
        return new AddStockResponse(name, itm.getStock());
    }

    public synchronized DeleteResponse deleteItems(List<ItemCount> order) {
        for (ItemCount ic : order) {
            items.remove(ic.getName());
        }
        List<AddStockResponse> remaining = new ArrayList<>();
        for (Item itm : items.values()) {
            remaining.add(new AddStockResponse(itm.getName(), itm.getStock()));
        }
        return new DeleteResponse(remaining);
    }
}







