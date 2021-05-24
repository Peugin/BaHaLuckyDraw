package tw.peugin.baha.luckydraw.controller.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import tw.peugin.baha.bahaForum.entity.ErrorResponse;
import tw.peugin.baha.luckydraw.exception.ArgumentInvalidException;
import tw.peugin.baha.luckydraw.exception.InternalServerException;

@ControllerAdvice
public class RestfulApiHandler {
    @ExceptionHandler(ArgumentInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse processValidationError(ArgumentInvalidException ex) {
        return new ErrorResponse(401,ex.getException().getClass().getSimpleName(),ex.getMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse processInternalServerError(InternalServerException ex) {
        ex.getException().printStackTrace();
        return new ErrorResponse(501,ex.getException().getClass().getSimpleName(),ex.getMessage());
    }
}
