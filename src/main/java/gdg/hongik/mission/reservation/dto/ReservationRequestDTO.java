package gdg.hongik.mission.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 예약 요청 데이터를 전달하기 위한 DTO 클래스입니다.
 */
@Getter @Setter
public class ReservationRequestDTO {

    /**
     * 예약자 이름.
     */
    private String name;

    /**
     * 예약자 역할.
     */
    private String position;

    /**
     * 예약할 상품 목록.
     */
    private List<ReservationItemDTO> items;

    /**
     * 개별 예약 아이템의 이름과 수량 정보를 담은 내부 DTO 클래스입니다.
     */
    @Getter @Setter
    public static class ReservationItemDTO {
        private String name;
        private int count;
    }
}
