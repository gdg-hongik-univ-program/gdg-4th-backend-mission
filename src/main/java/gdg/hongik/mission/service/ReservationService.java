package gdg.hongik.mission.service;

import gdg.hongik.mission.DTO.PurchaseRequest.ItemOrder;
import gdg.hongik.mission.DTO.UserRequest;
import gdg.hongik.mission.entity.*;
import gdg.hongik.mission.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final ItemRepository itemRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              PurchaseHistoryRepository purchaseHistoryRepository,
                              ItemRepository itemRepository) {
        this.reservationRepository = reservationRepository;
        this.purchaseHistoryRepository = purchaseHistoryRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void reserve(String username, List<ItemOrder> items) {
        if (reservationRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 예약이 존재합니다");
        }

        List<ReservedItem> reservedItems = new ArrayList<>();
        for (ItemOrder order : items) {
            Item item = itemRepository.findByName(order.name())
                    .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

            if (item.getStock() < order.count()) {
                throw new IllegalArgumentException("재고 부족: " + item.getName());
            }

            item.setStock(item.getStock() - order.count());
            itemRepository.save(item);

            reservedItems.add(new ReservedItem(order.name(), order.count()));
        }

        reservationRepository.save(new Reservation(null, username, reservedItems));
    }

    @Transactional
    public void cancelReservation(UserRequest request) {
        Reservation reservation = reservationRepository.findByUsername(request.name())
                .orElseThrow(() -> new IllegalArgumentException("예약 없음"));

        if (!request.name().equals(reservation.getUsername()) && request.position() != UserRequest.Role.ADMIN) {
            throw new IllegalArgumentException("권한 없음");
        }

        for (ReservedItem item : reservation.getItems()) {
            Item dbItem = itemRepository.findByName(item.getName())
                    .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

            dbItem.setStock(dbItem.getStock() + item.getCount());
            itemRepository.save(dbItem);
        }

        reservationRepository.delete(reservation);
    }

    @Transactional
    public List<Map<String, Object>> findAllReservations(UserRequest request) {
        if (request.position() != User.Role.ADMIN)
            throw new IllegalArgumentException("관리자만 조회할 수 있습니다.");

        return reservationRepository.findAll().stream()
                .map(res -> Map.of(
                        "name", res.getUsername(),
                        "items", res.getItems().stream()
                                .map(i -> Map.of(
                                        "name", i.getItemName(),
                                        "count", i.getCount()
                                ))
                                .toList()
                ))
                .toList();
    }


    @Transactional
    public Map<String, Object> getPurchaseStatistics(UserRequest request) {
        List<PurchaseHistory> historyList = purchaseHistoryRepository.findByUsername(request.name());

        Map<String, Integer> totalCountMap = new HashMap<>();
        Map<String, Double> averageMap = new HashMap<>();

        if (!historyList.isEmpty()) {
            Set<String> itemNames = historyList.get(0).getItems().stream()
                    .map(PurchasedItem::getName)
                    .collect(Collectors.toSet());

            for (String item : itemNames) {
                int total = historyList.stream()
                        .flatMap(h -> h.getItems().stream())
                        .filter(i -> i.getName().equals(item))
                        .mapToInt(PurchasedItem::getCount)
                        .sum();

                double avg = historyList.stream()
                        .mapToInt(h -> h.getItems().stream()
                                .filter(i -> i.getName().equals(item))
                                .mapToInt(PurchasedItem::getCount)
                                .sum())
                        .average().orElse(0);

                totalCountMap.put(item, total);
                averageMap.put(item, Math.round(avg * 10000.0) / 10000.0);
            }
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (String name : totalCountMap.keySet()) {
            result.add(Map.of(
                    "name", name,
                    "count", totalCountMap.get(name),
                    "average", averageMap.get(name)
            ));
        }

        return Map.of("items", result);
    }
}
