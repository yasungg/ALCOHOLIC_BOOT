package startline.server.exceptions;

public class UserExistException extends Exception{
    private final int ERR_CODE;

    public UserExistException(String msg, int errcode) {
        super(msg);
        ERR_CODE = errcode;
    }
    public UserExistException(String msg) {
        this(msg, 100);
    }
    public int getErrorCode() {
        return ERR_CODE;
    }
}
