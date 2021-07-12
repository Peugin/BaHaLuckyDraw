package tw.peugin.baha.luckydraw.controller.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Error> processValidationError(BindException ex) {
        return ResponseEntity.badRequest().body(new Error(HttpStatus.BAD_REQUEST.value(),ex.getAllErrors().get(0).getDefaultMessage()));
    }
    class Error{
        private int status;
        private String message;

        public Error(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}