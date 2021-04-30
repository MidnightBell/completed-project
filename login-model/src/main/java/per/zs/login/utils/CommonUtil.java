package per.zs.login.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import per.zs.login.beans.constants.Constant;

/**
 * Create time 2021年4月28日 上午11:11:59
 * 
 * @author sheng.zhong
 * @Description 公用的工具类
 */
public class CommonUtil {

    /**
     * 获取当前登录用户名
     * @return
     */
    public static String getCurrentUserName() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String authToken = CookieUtil.getCookieValueByName(request, Constant.COOKIE_KEY_STR);
        if (StringUtils.isEmpty(authToken)) {
            // 从header中获取-swagger验证接口时将token放在header中
            authToken = request.getHeader(Constant.COOKIE_KEY_STR);
            if (StringUtils.isEmpty(authToken)) {
                return "";
            }
        }
        String userName = CookieUtil.getUsernameFromToken(authToken, Constant.USERNAME);
        if (StringUtils.isBlank(userName)) {
            return "";
        } else {
            return userName;
        }
    }

}
