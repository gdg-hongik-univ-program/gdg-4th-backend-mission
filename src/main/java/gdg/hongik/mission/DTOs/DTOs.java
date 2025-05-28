package gdg.hongik.mission.DTOs;

import gdg.hongik.mission.Model.BaseRequest;
import gdg.hongik.mission.Model.Reservation;

import java.util.List;

public class DTOs {
    class SearchRequest extends BaseRequest {
        public String searchName;
    }
    class SearchResponse {
        public int id;
        public String name;
        public int price;
        public int stock;
    }

    class PurchaseRequest extends BaseRequest {
        public List<ItemCount> items;
    }
    class ItemCount {
        public String name;
        public int count;
    }
    class PurchaseItem {
        public String name;
        public int count;
        public int price;
        public PurchaseItem(String name, int count, int price){
            this.name = name;
            this.count = count;
            this.price = price;
        }
    }
    class PurchaseResponse{
        public int totalPrice;
        public List<PurchaseItem> items;
        public PurchaseResponse(int totalPrice, List<PurchaseItem> items) {
            this.totalPrice = totalPrice;
            this.items = items;
        }
    }

    class RegisterRequest extends BaseRequest {
        public String name;
        public int price;
        public int stock;
    }
    class AddStockResponse {
        public String name;
        public int stock;
        public AddStockResponse(String name, int stock) {
            this.name = name;
            this.stock = stock;
        }
    }

    class DeleteRequest extends BaseRequest {
        public List<ItemCount> items;
    }

    class DeleteResponse {
        public List<AddStockResponse> items;
        public DeleteResponse(List<AddStockResponse> items) {
            this.items = items;
        }
    }

    class ReserveRequest extends  BaseRequest {
        public List<ItemCount> items;
    }
    class CancelReserveRequest extends BaseRequest {
        public String target;
    }
    class ReservationListResponse {
        public List<Reservation> data;
        public ReservationListResponse(List<Reservation>)
    }
    class Reservation {
        public final String consumer;
        public final List<DTOs.ItemCount> items;
        public Reservation(String consumer, List<ItemCount> items) {
            this.consumer = consumer;
            this.items = items;
        }
    }
    class PurchaseRecord {
        public final List<ItemCount> items;

    }

}
