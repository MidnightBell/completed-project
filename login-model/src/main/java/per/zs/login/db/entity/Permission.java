package per.zs.login.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年4月30日 上午10:21:45 
* @author sheng.zhong 
* @Description  
*/
@Data
@TableName("t_permission")
public class Permission {
    @ApiModelProperty(value = "Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
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
    
}
