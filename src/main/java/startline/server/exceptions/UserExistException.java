package startline.server.exceptions;

public class UserExistException extends Exception{
    private final String ERR_CODE = "621";

    public UserExistException() {
        super();
    }
    public String getErrorCode() {
        return ERR_CODE;
    }
    public String getMessage() {
        return "이미 등록된 이메일입니다.";
    }
    public void printStackTrace() {
        printStackTrace(System.err);
    }
}
