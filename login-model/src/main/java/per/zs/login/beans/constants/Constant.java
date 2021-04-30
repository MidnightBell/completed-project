package per.zs.login.beans.constants;

/** 
* Create time 2020年9月8日 下午4:32:52 
* @author sheng.zhong 
* @Description  常量类
*/
public class Constant {
    
    public static final String PASSWORD_REQ = "^(?![a-z0-9_]+$)(?![A-Za-z_]+$)(?![A-Z0-9_]+$)[a-zA-Z0-9_]{8,}$";

    public static final String IGNORE_URL = "/auth/login,/auth/loginAuth,/auth/encryptPassword,/auth/verifyCode,/auth/register";
    
    public static final String SWAGGER_IGNORE = "/swagger-resources,/webjars,/v2,/swagger-ui.html";
    
    public static final String COOKIE_KEY_STR = "myToken";
    
    public static final String RANDOMKEY = "randomKey";
    public static final String USERNAME = "userName";
    
    public static final String JWT_SECRET = "mySecret@123";
    
}
