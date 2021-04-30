package per.zs.login.beans.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年4月29日 上午11:22:21 
* @author sheng.zhong 
* @Description  
*/
@Data
public class RoleInfoReq {
    @ApiModelProperty(value = "Id-新增时为空，修改时不为空")
    private Long id;
    
    @ApiModelProperty(value = "角色名")
    private String roleName;
}
