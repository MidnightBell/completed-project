package per.zs.login.beans.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import per.zs.login.db.entity.User;

/** 
* Create time 2021年4月28日 上午9:47:50 
* @author sheng.zhong 
* @Description  
*/
@Data
@NoArgsConstructor
public class UserInfoDto {
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    
    @ApiModelProperty(value = "用户名（唯一不重复）")
    private String userName;
    
    @ApiModelProperty(value = "电话")
    private String phone;
    
    @ApiModelProperty(value = "昵称")
    private String nickName;
    
    @ApiModelProperty(value = "性别")
    private String sex;
    
    @ApiModelProperty(value = "角色信息")
    private List<RoleInfoDto> role;
    
    public UserInfoDto(User user) {
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.phone = user.getPhone();
        this.nickName = user.getNickName();
        this.sex = user.getSex();
        this.role = new ArrayList<>();
    }
}
