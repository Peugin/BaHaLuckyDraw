package tw.peugin.bahaluckydraw.entity;

public class ErrorWithExceptionResponse extends ErrorResponse{
    Exception exception;

    public ErrorWithExceptionResponse(String error,Exception ex) {
        super(error);
        setException(ex);
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
