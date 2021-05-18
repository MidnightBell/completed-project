package per.zs.common.utils;

import org.springframework.data.redis.core.RedisTemplate;

/** 
* Create time 2021年5月18日 上午10:21:18 
* @author sheng.zhong 
* @Description  redis缓存数据
*/
public class RedisCacheUtil {
    
    public static void set(String key,String value){
        RedisTemplate<String,String> redisTemplate = (RedisTemplate) SpringContextUtil.getBean("redisTemplate");
        redisTemplate.opsForValue().set(key,value);
    }
    
    public static String get(String key) {
        RedisTemplate<String,String> redisTemplate = (RedisTemplate) SpringContextUtil.getBean("redisTemplate");
        return redisTemplate.opsForValue().get(key);
    }
}
