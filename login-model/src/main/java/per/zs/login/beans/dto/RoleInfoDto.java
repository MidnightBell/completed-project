package per.zs.login.beans.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年4月29日 下午1:49:37 
* @author sheng.zhong 
* @Description  
*/
@Data
public class RoleInfoDto {
    
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
    
    @ApiModelProperty(value = "角色名")
    private String roleName;

}
