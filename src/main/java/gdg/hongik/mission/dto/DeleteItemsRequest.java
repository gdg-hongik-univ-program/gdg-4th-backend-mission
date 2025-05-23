package gdg.hongik.mission.dto;

import java.util.List;

public class DeleteItemsRequest {
    private List<String> names;

    public DeleteItemsRequest() {}

    public DeleteItemsRequest(List<String> names) {
        this.names = names;
    }

    public List<String> getNames() { return names; }
    public void setNames(List<String> names) { this.names = names; }
}