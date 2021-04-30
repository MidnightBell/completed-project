package per.zs.login.beans.req;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

/** 
* Create time 2020年9月10日 下午3:34:34 
* @author sheng.zhong 
* @Description  登录请求
*/
public class LoginReq {
    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名不能为空")
    private String userName;
    
    @ApiModelProperty("密码")
    @NotEmpty(message = "密码不能为空")
    private String password;
    
    @ApiModelProperty("验证码")
    @NotEmpty(message = "验证码不能为空")
    private String verifyCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
