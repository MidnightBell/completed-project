package per.zs.login.beans.dto;
 
import lombok.Data;
 
/**
 * 验证码类
 */
@Data
public class VerifyCode {
 
    private String code;
 
    private byte[] imgBytes;
 
    private long expireTime;
 
}