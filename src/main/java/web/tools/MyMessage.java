package web.tools;

/**
 * Created by kylin on 03/12/2016.
 * All rights reserved.
 */
public class MyMessage {

    boolean isSuccess;

    int errorCode;

    String debugInfo;

    String message;

    public MyMessage(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public MyMessage(boolean isSuccess, int errorCode, String debugInfo) {
        this.isSuccess = isSuccess;
        this.errorCode = errorCode;
        this.debugInfo = debugInfo;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getDebugInfo() {
        return debugInfo;
    }

    public String getMessage() {
        return message;
    }
}
