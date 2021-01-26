package tw.peugin.bahaluckydraw.controller.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import tw.peugin.bahaluckydraw.entity.ErrorResponse;
import tw.peugin.bahaluckydraw.exception.ArgumentInvalidException;
import tw.peugin.bahaluckydraw.exception.InternalServerException;

import java.util.Arrays;

@ControllerAdvice
public class RestApiHandler {
    @ExceptionHandler(ArgumentInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse processValidationError(ArgumentInvalidException ex) {
        return new ErrorResponse(ex.getErrorMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse processInternalServerError(InternalServerException ex) {
        ex.getException().printStackTrace();
        return new ErrorResponse(Arrays.toString(ex.getException().getStackTrace()));
    }
}
