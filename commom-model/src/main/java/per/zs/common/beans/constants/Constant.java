package per.zs.common.beans.constants;

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
    
    
    // 邮箱
    public static final String REGEX_EMAIL = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
    // 日期 yyyy
    public static final String REGEX_STRING_YEAR = "^((?:19|20)\\d\\d)$";
    // 日期 yyyy-MM
    public static final String REGEX_STRING_MONTH = "^((?:19|20)\\d\\d)-(0[1-9]|1[012])$";
    // 日期 yyyy yyyyQx yyyy-MM
    public static final String REGEX_STRING_YMQ = "^((?:19|20)\\d\\d)(Q[1-4]|-(0[1-9]|1[012])){0,1}$";
    // 日期 yyyy-MM-dd
    public static final String REGEX_STRING_DAY = "^((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
    // 日期 yyyy-MM-dd HH:mm:ss
    public static final String REGEX_STRING_DATE = "^((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) (20|21|22|23|[0-1][0-9]):([0-5][0-9]):([0-5][0-9])$";
    // 手机号码 [1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$
    public static final String REGEX_TEL = "[0-9]{11}";
    
}
