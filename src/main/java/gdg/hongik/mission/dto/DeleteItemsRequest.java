package gdg.hongik.mission.dto;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteItemsRequest {
    private List<Item> items;

    public DeleteItemsRequest() {}

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public static class Item {
        private String name;
        public Item() {}
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public List<String> getNames() {
        if(items == null) return null;
        return items.stream().map(Item::getName).collect(Collectors.toList());
    }
}