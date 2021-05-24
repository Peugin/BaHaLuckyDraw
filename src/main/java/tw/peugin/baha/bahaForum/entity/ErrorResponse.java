package tw.peugin.baha.bahaForum.entity;

public class ErrorResponse {
    private int code;
    private String exceptionName;
    private String message;

    public ErrorResponse(int code, String exception, String message) {
        this.code = code;
        this.exceptionName = exception;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
