package tw.peugin.baha.luckydraw.exception;

public class ArgumentInvalidException extends Exception {
    private String message;
    private Exception exception;

    public ArgumentInvalidException(String message) {
        super();
        setMessage(message);
        setException(this);
    }

    public ArgumentInvalidException(String message,Exception exception) {
        super();
        setMessage(message);
        setException(exception);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
