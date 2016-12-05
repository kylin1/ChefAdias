package web.model.exceptions;


public class BadInputException extends MyException {

    public BadInputException(String errorCode) {
        super(errorCode);
    }
}
