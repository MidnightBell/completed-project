package per.zs.login.exception;

/**
 * 
* Create time 2020年5月29日 上午9:41:14 
* @author sheng.zhong 
* @Description 自定义异常
 */
public class CustomException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -2292656108542379879L;

    public CustomException(String message) {
        super(message);
    }
}
