//package per.zs.getAway.filter;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//
//import per.zs.getAway.config.GatewaySwaggerProvider;
//
///**
// * 
//* Create time 2021年5月17日 下午2:25:52 
//* @author sheng.zhong 
//* @Description
// */
//@Component
//public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory {
// 
//    private static final String HEADER_NAME = "X-Forwarded-Prefix";
// 
//    private static final String HOST_NAME = "X-Forwarded-Host";
// 
//    @Override
//    public GatewayFilter apply(Object config) {
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            String path = request.getURI().getPath();
//            if (!StringUtils.endsWithIgnoreCase(path, GatewaySwaggerProvider.API_URI)) {
//                return chain.filter(exchange);
//            }
//            String basePath = path.substring(0, path.lastIndexOf(GatewaySwaggerProvider.API_URI));
//            ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
//            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
//            return chain.filter(newExchange);
// 
//        };
// 
//    }
//}