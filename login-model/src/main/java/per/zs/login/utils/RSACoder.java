package per.zs.login.utils;


import javax.crypto.Cipher;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSACoder {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    //如果每次生成,需要每次加载登录页,前端每次需要请求接口获取公钥,后端将公钥私钥放在session中处理,获取公钥解密;
    //暂时将公钥私钥写死,不每次生成,

    public static String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKLrcnV1gVYVncscF11vcOAyupl2ZPdOXRAQtoQl0J/l6ATCGvoPy3gxhcqrDbK5I2gUlzjPT43Mp4GaRGeWg+BTPuJ/T1ObTreBP7kH4MzmJl98ylagG34EG+Z/LcqE/jV1PneojLbUuRnyYiLY5E3nYGhvOEjQkosQJLTtQKmdAgMBAAECgYEAjyB3epdTm7P73x77q9smd+A5LG7lvpn6ig2Uur2Z7Y+F6QA5wOu1Xg9rsaPaseOSJnLIc2E1S6a6ldJYPATwLu7tf3YlMz7RAsZrndF8H4fOk8Db8krnkoat3fseJQ1JEd8GiI1rwmMKDgiqb05OUEEYYM0zsqx3gZ5+tqhW5QECQQDkx8OhvJR/AalzJsmK5eYthsdGVMCpYcgX4ZLb2o0K/FxUQsTGuFJCt7z886clXgGqaUIWxC432c+aaSB04L1dAkEAtk2wAXK1atl5JOIiBC+JkMUkR80wBbyrhYYTn6SbqGQpR1zM8Uryh9bFXqYDnDx2mNDZY7kN61Iz88JgttSZQQJBALcWp5loMe7yhYmCIQjns637jr7aVNU5S8jQ3ZbU8Ms56yoDxTbDjBGYucY1uC5PbUXTtvBBUDuiNGJ+f1sKAIECQQCzi0W512CHMFIYmkIIP01L8G0v7gzKyatf4NG42+SV7rec2e/aAQdl84cFFZneqMng7Wf8yAfGaHpUK28XAcFBAkA/d0whURNlFYCuGTCqVP5X+MdJztGI9Diq/N3xAnVoZA/msf5NZDm0Sisk91y3UofGc4Rg6ts8GA/NVgW9jnlc";
    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCi63J1dYFWFZ3LHBddb3DgMrqZdmT3Tl0QELaEJdCf5egEwhr6D8t4MYXKqw2yuSNoFJc4z0+NzKeBmkRnloPgUz7if09Tm063gT+5B+DM5iZffMpWoBt+BBvmfy3KhP41dT53qIy21LkZ8mIi2ORN52BobzhI0JKLECS07UCpnQIDAQAB";
    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    public static String encryptBASE64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return encryptBASE64(signature.sign());
    }

    public static PrivateKey strToPrivateKey(String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        return keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        // 解密由base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }

    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 解密<br>
     * 用私钥解密
     */
    public static byte[] decryptByPrivateKey(String data, String key)
            throws Exception {
        return decryptByPrivateKey(decryptBASE64(data), key);
    }

    /**
     * 解密<br>
     * 用公钥解密
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     */
    public static byte[] encryptByPublicKey(String data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    /**
     * 加密<br>
     * 用私钥加密
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     */
    public static String getPrivateKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取得公钥
     */
    public static String getPublicKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 初始化密钥
     */
    public static Map<String, Key> initKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Map<String, Key> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, keyPair.getPublic());// 公钥
        keyMap.put(PRIVATE_KEY, keyPair.getPrivate());// 私钥
        return keyMap;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new String(decryptByPrivateKey("S1MX6C5ZjmjGW6hDcHKuRIg/UPqvgFp1pp1jTKjolRjhxXGq6uY4LWlJsLtsB7zpXqAdBpo8f+sOaeF8h6YxRKnaEwdq3+TMPkW4X1q4NOmjbt01K9qg0qza1IMds83ezIvvnVj9mR13A3dZTBhxHhAavKh0IbHHhczFYyZ0coQ=", RSACoder.privateKey)));
        System.out.println(Base64.encodeBase64String(encryptByPublicKey("ChangHong001", RSACoder.publicKey)));
    }
}
