package per.zs.login.db.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年4月29日 上午11:08:20 
* @author sheng.zhong 
* @Description  
*/
@Data
@TableName("t_role")
public class Role {
    @ApiModelProperty(value = "Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    @ApiModelProperty(value = "角色名")
    private String roleName;
    
    @ApiModelProperty(value = "创建人id")
    private Integer createUser;
    
    @ApiModelProperty(value = "修改人id")
    private Integer updateUser;
    
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @ApiModelProperty(value = "是否删除 1：删除 0：未删除")
    private Integer is_del;
}
