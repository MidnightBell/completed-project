package per.zs.getAway.filter;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSONObject;

import io.jsonwebtoken.ExpiredJwtException;
import per.zs.common.beans.constants.Constant;
import per.zs.common.utils.CookieUtil;
import per.zs.common.utils.LocalCacheUtils;
import per.zs.common.utils.RedisCacheUtil;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 放过静态资源,静态资源有个点号
        String servletPath = request.getPath().value();
        if (servletPath.contains(".") || "/".equals(servletPath)) {
            return chain.filter(exchange);
        }
        // 放过swagger
        String swaggerIgnoreUrl = Constant.SWAGGER_IGNORE;
        List<String> swaggerIgnoreList = Arrays.asList(swaggerIgnoreUrl.split(","));
        for (String swaggerIgnore : swaggerIgnoreList) {
            if (servletPath.contains(swaggerIgnore)) {
                return chain.filter(exchange);
            }
        }
        // 忽略列表
        String ignoreUrl = Constant.IGNORE_URL;
        List<String> ignoreUrlList = Arrays.asList(ignoreUrl.split(","));
        if (ignoreUrlList.contains(servletPath)) {
            return chain.filter(exchange);
        }
        String userName = null;
        String cookieRandom = null;
        // 从cookie中获取token信息
        String authToken = CookieUtil.getCookieValueByName(request, Constant.COOKIE_KEY_STR);

        if (StringUtils.isEmpty(authToken)) {
            // 从header中获取-swagger验证接口时将token放在header中（测试用，不验证随机数即一个token可以反复使用）
            HttpHeaders httpHeaders = request.getHeaders();
            if(httpHeaders!=null){
                authToken = request.getHeaders().getFirst(Constant.COOKIE_KEY_STR);
            }else{
                return error(exchange, 300, "未登录");
            }
            if (StringUtils.isEmpty(authToken)) {
                return error(exchange, 300, "未登录");
            }
            
            try {
                // 通过Token获取userName
                userName = CookieUtil.getUsernameFromToken(authToken, Constant.USERNAME);
            } catch (ExpiredJwtException e) {
                return error(exchange, 300, "登录信息已过期");
            }
            if (StringUtils.isBlank(userName)) {
                return error(exchange, 300, "用户解析异常");
            }
        }else {
            // 验证token是否过期,包含了验证jwt是否正确
            try {
                // 通过Token获取userName
                userName = CookieUtil.getUsernameFromToken(authToken, Constant.USERNAME);
                // 通过Token获取cookie中的随机数
                cookieRandom = CookieUtil.getUsernameFromToken(authToken, Constant.RANDOMKEY);
            } catch (ExpiredJwtException e) {
                return error(exchange, 300, "登录信息已过期");
            }
            if (StringUtils.isBlank(userName) || StringUtils.isBlank(cookieRandom)) {
                return error(exchange, 300, "用户解析异常");
            } else {
                
                // 对比cookie中的随机数与当前登录时保存的随机数-不一致则认为cookie无效
                // 此处保证了账号同一时间只能单人登录(若要多人登录则登录保存随机数时保存为List)
//                String random = LocalCacheUtils.getLocalRandomCache(userName) == null ? ""
//                        : LocalCacheUtils.getLocalRandomCache(userName).toString();
                String random = RedisCacheUtil.get(userName) == null ? ""
                        : RedisCacheUtil.get(userName);
                if (!StringUtils.equals(random, cookieRandom)) {
                    return error(exchange, 300, "无效的cookie");
                }
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -3;
    }

    @PostConstruct
    public void init(){

    }
    
    private Mono<Void> error(ServerWebExchange exchange, int code, String msg) {
        ServerHttpResponse response = exchange.getResponse();
        JSONObject message = new JSONObject();
        message.put("code", code);
        message.put("message", msg);
        message.put("success", false);
        byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        // 指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}