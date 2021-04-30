package per.zs.login.beans.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年4月30日 上午10:19:19 
* @author sheng.zhong 
* @Description  
*/
@Data
public class PermissionDto {
    
    @ApiModelProperty(value = "权限Id")
    private Integer perId;
    
    @ApiModelProperty(value = "权限名称")
    private String perName;
    
    @ApiModelProperty(value = "权限值")
    private String perValue;
    
    @ApiModelProperty(value = "权限类型（1：按钮权限 2：菜单权限）")
    private Integer perType;

    @ApiModelProperty(value = "父权限id")
    private Integer parentId;
    
    @ApiModelProperty(value = "权限描述")
    private String perDescription;
    
    @ApiModelProperty(value = "菜单地址")
    private String menuComonent;
    
    @ApiModelProperty(value = "是否删除（0：未删除 1：删除）")
    private Integer isDel;
    
    @ApiModelProperty(value = "是否拥有此权限（0：未拥有 1：拥有）")
    private Integer isSelected = 0;
    
    @ApiModelProperty(value = "子权限")
    private List<PermissionDto> children = new ArrayList<>();
    
}
