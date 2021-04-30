package per.zs.login.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import io.jsonwebtoken.ExpiredJwtException;
import per.zs.login.beans.constants.Constant;
import per.zs.login.exception.UnLoginException;
import per.zs.login.utils.CookieUtil;
import per.zs.login.utils.LocalCacheUtils;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 放过静态资源,静态资源有个点号
        String servletPath = request.getServletPath();
        if (servletPath.contains(".") || "/".equals(servletPath)) {
            chain.doFilter(request, servletResponse);
            return;
        }
        // 放过swagger
        if (!"pro".equals(System.getProperty("spring.profiles.active"))) {
            String swaggerIgnoreUrl = Constant.SWAGGER_IGNORE;
            List<String> swaggerIgnoreList = Arrays.asList(swaggerIgnoreUrl.split(","));
            for (String swaggerIgnore : swaggerIgnoreList) {
                if (servletPath.contains(swaggerIgnore)) {
                    chain.doFilter(request, servletResponse);
                    return;
                }
            }
        }
        // 忽略列表
        String ignoreUrl = Constant.IGNORE_URL;
        List<String> ignoreUrlList = Arrays.asList(ignoreUrl.split(","));
        if (ignoreUrlList.contains(servletPath)) {
            chain.doFilter(request, servletResponse);
            return;
        }
        String userName = null;
        String cookieRandom = null;
        // 从cookie中获取token信息
        String authToken = CookieUtil.getCookieValueByName(request, Constant.COOKIE_KEY_STR);

        if (StringUtils.isEmpty(authToken)) {
            // 从header中获取-swagger验证接口时将token放在header中（测试用，不验证随机数即一个token可以反复使用）
            authToken = request.getHeader(Constant.COOKIE_KEY_STR);
            if (StringUtils.isEmpty(authToken)) {
                throw new UnLoginException("未登录");
            }
            
            try {
                // 通过Token获取userName
                userName = CookieUtil.getUsernameFromToken(authToken, Constant.USERNAME);
            } catch (ExpiredJwtException e) {
                throw new UnLoginException("登录信息已过期");
            }
            if (StringUtils.isBlank(userName)) {
                throw new UnLoginException("用户解析异常");
            }
        }else {
            // 验证token是否过期,包含了验证jwt是否正确
            try {
                // 通过Token获取userName
                userName = CookieUtil.getUsernameFromToken(authToken, Constant.USERNAME);
                // 通过Token获取cookie中的随机数
                cookieRandom = CookieUtil.getUsernameFromToken(authToken, Constant.RANDOMKEY);
            } catch (ExpiredJwtException e) {
                throw new UnLoginException("登录信息已过期");
            }
            if (StringUtils.isBlank(userName) || StringUtils.isBlank(cookieRandom)) {
                throw new UnLoginException("用户解析异常");
            } else {
                
                // 对比cookie中的随机数与当前登录时保存的随机数-不一致则认为cookie无效
                // 此处保证了账号同一时间只能单人登录(若要多人登录则登录保存随机数时保存为List)
                String random = LocalCacheUtils.getLocalRandomCache(userName) == null ? ""
                        : LocalCacheUtils.getLocalRandomCache(userName).toString();
                if (!StringUtils.equals(random, cookieRandom)) {
                    throw new UnLoginException("无效的cookie");
                }
            }
        }
        chain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}