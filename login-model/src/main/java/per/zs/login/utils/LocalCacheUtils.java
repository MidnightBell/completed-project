package per.zs.login.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author huang
 * @date 2019/11/19 0019
 * 略有问题,本地缓存记录,如果分布式部署,密码重试次数会加多
 */
public class LocalCacheUtils {

    /**
     * 缓存登录失败次数
     */
    //初始化 Guava内存  LRU算法
    private static Cache<String,Object> localCache = CacheBuilder.newBuilder()
            //设置缓存容器的初始容量为20
            .initialCapacity(20)
            //设置缓存中最大可以存储200个KEY,超过100个之后会按照LRU的策略移除缓存项
            .maximumSize(200)
            //设置写缓存后多少秒过期,10分钟过期
            .expireAfterWrite(600, TimeUnit.SECONDS).build();

    public static void setLocalCache(String key, Object value) {
        localCache.put(key,value);
    }

    public static Object getLocalCache(String key) {
        return localCache.getIfPresent(key);
    }
    
    /**
     * 缓存档次登录的随机数-会在退出登录时设置为空
     */
    //初始化 Guava内存  LRU算法
    private static Cache<String,Object> localRandomCache = CacheBuilder.newBuilder()
            //设置缓存容器的初始容量为20
            .initialCapacity(20)
            //设置缓存中最大可以存储200个KEY,超过100个之后会按照LRU的策略移除缓存项
            .maximumSize(200)
            //设置写缓存后多少秒过期,4000秒后过期（此处需要比cookie的有效时间长-否则认证时会在cookie有效期内报cookie异常）
            .expireAfterWrite(7*24*60*60, TimeUnit.SECONDS).build();
    
    public static void setLocalRandomCache(String key, Object value) {
        localRandomCache.put(key,value);
    }
    
    public static Object getLocalRandomCache(String key) {
        return localRandomCache.getIfPresent(key);
    }

    /**
     * 缓存验证码
     */
    //初始化 Guava内存  LRU算法
    private static Cache<String,Object> localVerifyCodeCache = CacheBuilder.newBuilder()
            //设置缓存容器的初始容量为20
            .initialCapacity(20)
            //设置缓存中最大可以存储200个KEY,超过100个之后会按照LRU的策略移除缓存项
            .maximumSize(200)
            //设置写缓存后多少秒过期,4000秒后过期
            .expireAfterWrite(60, TimeUnit.SECONDS).build();
    
    public static void setLocalVerifyCodeCache(String key, Object value) {
        localVerifyCodeCache.put(key,value);
    }
    
    public static Object getLocalVerifyCodeCache(String key) {
        return localVerifyCodeCache.getIfPresent(key);
    }

}
