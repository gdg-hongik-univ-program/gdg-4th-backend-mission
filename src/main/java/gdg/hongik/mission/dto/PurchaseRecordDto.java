package gdg.hongik.mission.dto;

import java.util.List;

/**
 * 소비자의 1회 구매 내역을 나타내는 DTO
 */
public record PurchaseRecordDto(
        List<ItemCountDto> items
) {}
