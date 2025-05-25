package gdg.hongik.mission.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러가 발생했습니다."),
  INPUT_MUST_BE_GREATER_THAN_ZERO(HttpStatus.BAD_REQUEST, "quantity must be greater than 0"),

  INVALID_AUTH(HttpStatus.UNAUTHORIZED, "invalid auth"),
  INVALID_INPUT(HttpStatus.BAD_REQUEST, "invalid input"),

  ALREADY_EXIST(HttpStatus.CONFLICT, "already exist"),
  NOT_EXIST(HttpStatus.NOT_FOUND, "not exist")
  ;

  private final HttpStatus status;
  private final String message;
}
