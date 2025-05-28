package gdg.hongik.mission.dto;

import gdg.hongik.mission.purchase.PurchaseRecord;

import java.util.List;

public class RecordResponse {
    private List<PurchaseRecord> record;

    public RecordResponse(List<PurchaseRecord> record) {
        this.record = record;
    }
    public List<PurchaseRecord> getRecord() { return record; }
}