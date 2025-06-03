package gdg.hongik.mission.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 사용자 등록 요청 데이터를 전달하기 위한 DTO 클래스입니다.
 */
@Getter @Setter
public class UserRequestDTO {

    /**
     * 사용자 이름.
     */
    private String name;

    /**
     * 사용자 역할 (ADMIN or CONSUMER).
     */
    private String position;
}
