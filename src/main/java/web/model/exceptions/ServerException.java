package web.model.exceptions;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
public class ServerException extends MyException {
    public ServerException(String errorCode) {
        super(errorCode);
    }
}
