package per.zs.common.exception;

/** 
* Create time 2020年9月11日 上午8:48:11 
* @author sheng.zhong 
* @Description  
*/
public class UnLoginException extends RuntimeException  {

    /**
     * 
     */
    private static final long serialVersionUID = -4108570585124285761L;

    public UnLoginException(String message) {
        super(message);
    }
}
