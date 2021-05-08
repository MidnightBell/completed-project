package per.zs.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import per.zs.common.beans.constants.Constant;

/** 
* Create time 2020年12月29日 上午9:32:35 
* @author sheng.zhong 
* @Description  
*/
public class CookieUtil {
    private final static String SUBJECT = "mySubject";
    
    /**
     * 获取jwt失效时间
     */
    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token).getExpiration();
    }
    
    /**
     * 获取subject
     */
    public static String getSubjectFromToken(String token) {
        return getClaimFromToken(token).getSubject();
    }
    
    /**
     * 获取指定信息
     */
    public static String getUsernameFromToken(String token,String key) {
        Object value = getClaimFromToken(token).get(key);
        if(value != null) {
            return value.toString();
        }
        return null;
    }
    
    /**
     * 获取jwt的payload部分
     */
    public static Claims getClaimFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(Constant.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
    
    /**
     * 生成token(通过用户名和签名时候用的随机数)
     */
    public static String generateToken(String userName, String randomKey, int expiration) {
        Map<String, Object> claims = new HashMap<>(8);//用来保存不敏感的信息，如当前登录的用户信息
        claims.put(Constant.RANDOMKEY, randomKey);
        claims.put(Constant.USERNAME, userName);
        return doGenerateToken(claims, expiration);
    }

    /**
     * 生成token
     */
    private static String doGenerateToken(Map<String, Object> claims, int expiration) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(SUBJECT)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, Constant.JWT_SECRET)
                .compact();
    }
    
    /**
     * 获取请求中的cookie值
     */
    public static String getCookieValueByName(HttpServletRequest request, String name) {
        Map<String, String> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            String cookieValue = cookieMap.get(name);
            return cookieValue;
        } else {
            return null;
        }
    }
    
    /**
     * 检索所有Cookie封装到Map集合
     */
    public static Map<String, String> readCookieMap(HttpServletRequest request) {
        Map<String, String> cookieMap = new HashMap<>(8);
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookieMap;
    }
    
    /**
     * 获取随机字符串
     */
    public static String getRandomKey() {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        System.out.println(generateToken("zhongsheng", "1233211233", 3600));
        System.out.println(getUsernameFromToken("eyJhbGciOiJIUzUxMiJ9.eyJyYW5kb21LZXkiOiIxMjMzMjExMjMzIiwic3ViIjoibXlTdWJqZWN0IiwidXNlck5hbWUiOiJ6aG9uZ3NoZW5nIiwiZXhwIjoxNjE5NTk2Njg3LCJpYXQiOjE2MTk1OTMwODd9.EmBZ_yc1r6j6EYcbpI9A5rXUo7O51_RhJhMvH4_6N8b3QM7Fa0xvKiAq5ajzoUXZlQeesa9riI9YHnRXLnnvVQ","userName"));
        System.out.println(getUsernameFromToken("eyJhbGciOiJIUzUxMiJ9.eyJyYW5kb21LZXkiOiIxMjMzMjExMjMzIiwic3ViIjoibXlTdWJqZWN0IiwidXNlck5hbWUiOiJ6aG9uZ3NoZW5nIiwiZXhwIjoxNjE5NTk2Njg3LCJpYXQiOjE2MTk1OTMwODd9.EmBZ_yc1r6j6EYcbpI9A5rXUo7O51_RhJhMvH4_6N8b3QM7Fa0xvKiAq5ajzoUXZlQeesa9riI9YHnRXLnnvVQ","randomKey"));
        System.out.println(getSubjectFromToken("eyJhbGciOiJIUzUxMiJ9.eyJyYW5kb21LZXkiOiIxMjMzMjExMjMzIiwic3ViIjoibXlTdWJqZWN0IiwidXNlck5hbWUiOiJ6aG9uZ3NoZW5nIiwiZXhwIjoxNjE5NTk2Njg3LCJpYXQiOjE2MTk1OTMwODd9.EmBZ_yc1r6j6EYcbpI9A5rXUo7O51_RhJhMvH4_6N8b3QM7Fa0xvKiAq5ajzoUXZlQeesa9riI9YHnRXLnnvVQ"));
        System.out.println(getExpirationDateFromToken("eyJhbGciOiJIUzUxMiJ9.eyJyYW5kb21LZXkiOiIxMjMzMjExMjMzIiwic3ViIjoibXlTdWJqZWN0IiwidXNlck5hbWUiOiJ6aG9uZ3NoZW5nIiwiZXhwIjoxNjE5NTk2Njg3LCJpYXQiOjE2MTk1OTMwODd9.EmBZ_yc1r6j6EYcbpI9A5rXUo7O51_RhJhMvH4_6N8b3QM7Fa0xvKiAq5ajzoUXZlQeesa9riI9YHnRXLnnvVQ"));
    }
}
