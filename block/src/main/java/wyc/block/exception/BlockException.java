package wyc.block.exception;

public class BlockException extends Exception {

    private int errorCode;

    public BlockException(String message) {
        super(message);
    }

    public BlockException(Throwable cause) {
        super(cause);
    }


    public BlockException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BlockException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BlockException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
