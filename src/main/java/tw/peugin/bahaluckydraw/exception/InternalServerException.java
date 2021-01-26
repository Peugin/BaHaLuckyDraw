package tw.peugin.bahaluckydraw.exception;

public class InternalServerException extends Throwable {
    private Exception exception;
    private String message;

    public InternalServerException(Exception exception, String message) {
        setException(exception);
        setMessage(message);
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
