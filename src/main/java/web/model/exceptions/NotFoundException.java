package web.model.exceptions;

public class NotFoundException extends MyException{

    public NotFoundException(String errorCode) {
        super(errorCode);
    }
}


