package per.zs.common.exception;

/** 
* Create time 2020年9月10日 下午4:10:12 
* @author sheng.zhong 
* @Description  
*/
public class ParamException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 5140780763460498395L;

    public ParamException(String message) {
        super(message);
    }
}
