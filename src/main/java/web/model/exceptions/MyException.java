package web.model.exceptions;


public class MyException extends Exception {

    protected String errorCode;

    public MyException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public MyException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
