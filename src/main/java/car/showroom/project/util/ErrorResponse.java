package car.showroom.project.util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ErrorResponse {
    private final ErrorCode code;
    private final String message;
    public ErrorCode getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
