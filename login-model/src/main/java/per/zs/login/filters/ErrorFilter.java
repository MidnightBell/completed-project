package per.zs.login.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.alibaba.fastjson.JSONObject;

import per.zs.login.beans.enums.HttpCodeEnum;
import per.zs.login.exception.UnLoginException;

public class ErrorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (UnLoginException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try {
                JSONObject res = new JSONObject();
                res.put("code", HttpCodeEnum.UN_LOGIN.getCode());
                res.put("msg", e.getMessage());
                out = response.getWriter();
                out.append(res.toString());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void destroy() {
        
    }
}
