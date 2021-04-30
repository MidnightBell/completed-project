package per.zs.login.beans.req;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年4月28日 下午4:17:08 
* @author sheng.zhong 
* @Description  
*/
@Data
@ApiModel("成员新增及修改参数")
public class UserInfoReq {
    @ApiModelProperty(value = "Id-新增时为空，修改时不能为空")
    private Long id;
    
    @ApiModelProperty(value = "用户名（唯一不重复）")
    @NotEmpty(message = "用户名不能为空")
    private String userName;
    
    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "密码不能为空")
//    @Pattern(regexp = Constant.PASSWORD_REQ, message = "密码由字母、数字或下划线组成，必须包含字母大小写和数字，且不少于8位")
    private String password;
    
    @ApiModelProperty(value = "电话")
    private String phone;
    
    @ApiModelProperty(value = "昵称")
    private String nickName = "丑八怪唉咿呀~";
    
    @ApiModelProperty(value = "性别")
    private String sex;
}
