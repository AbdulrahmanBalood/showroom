package car.showroom.project.advice;

import car.showroom.project.exceptions.BusinessValidationException;
import car.showroom.project.exceptions.NotFoundException;
import car.showroom.project.util.ErrorCode;
import car.showroom.project.util.ErrorResponse;
import car.showroom.project.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageUtils messageUtils;

    @ExceptionHandler(BusinessValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBusinessValidationException(BusinessValidationException ex) {
        log.error("BusinessValidationException: {}", ex.getMessage());
        return new ErrorResponse(ErrorCode.BUSINESS_VALIDATION_ERROR, messageUtils.getMessage(ex.getMessage()));
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleNotFoundException(NotFoundException ex) {
        log.error("NotFoundException: {}", ex.getMessage());
        return new ErrorResponse(ErrorCode.BUSINESS_VALIDATION_ERROR, messageUtils.getMessage(ex.getMessage()));
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
        log.error("AccessDeniedException: {}", ex.getMessage());
        return new ErrorResponse(ErrorCode.BUSINESS_VALIDATION_ERROR, messageUtils.getMessage("access.denied"));
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(Exception ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        return new ErrorResponse(ErrorCode.TECHNICAL_ERROR, messageUtils.getMessage("server.error"));
    }
}
