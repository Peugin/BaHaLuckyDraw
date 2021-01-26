package tw.peugin.bahaluckydraw.exception;

public class ArgumentInvalidException extends Exception {
    private String errorMessage;

    public ArgumentInvalidException(String message) {
        super();
        setErrorMessage(errorMessage);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
