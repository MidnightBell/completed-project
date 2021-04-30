package per.zs.login.utils;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/** 
* Create time 2020年9月25日 上午9:21:34 
* @author sheng.zhong 
* @Description  DES加解密
*/
public class DESUtil {
    private static String keyData = "classify_grade_keyData@2020changhong";
    /**
     *  EDS的加密解密代码
     */
      public static String encryptBasedDes(String data) {
          String encryptedData = null;
          try {
              // DES算法要求有一个可信任的随机数源
              SecureRandom sr = new SecureRandom();
              DESKeySpec deskey = new DESKeySpec(keyData.getBytes());
              // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
              SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
              SecretKey key = keyFactory.generateSecret(deskey);
              // 加密对象
              Cipher cipher = Cipher.getInstance("DES");
              cipher.init(Cipher.ENCRYPT_MODE, key, sr);
              // 加密，并把字节数组编码成字符串
              //Base64 加密
              encryptedData = Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
          } catch (Exception e) {
              // log.error("加密错误，错误信息：", e);
              throw new RuntimeException("加密错误！");
          }
          return encryptedData;
      }
      public static String decryptBasedDes(String cryptData) {
          String decryptedData = null;
          try {
              // DES算法要求有一个可信任的随机数源
              SecureRandom sr = new SecureRandom();
              DESKeySpec deskey = new DESKeySpec(keyData.getBytes());
              // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
              SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
              SecretKey key = keyFactory.generateSecret(deskey);
              // 解密对象
              Cipher cipher = Cipher.getInstance("DES");
              cipher.init(Cipher.DECRYPT_MODE, key, sr);
              // 把字符串进行解码，解码为为字节数组，并解密
              decryptedData = new String(cipher.doFinal(Base64.getDecoder().decode(cryptData)));
          } catch (Exception e) {
              throw new RuntimeException("解密错误！");
          }
          return decryptedData;
      }
      
      public static void main(String[] args) {
          System.out.println(encryptBasedDes("admin123456"));
          System.out.println(decryptBasedDes(""));
      }
}
