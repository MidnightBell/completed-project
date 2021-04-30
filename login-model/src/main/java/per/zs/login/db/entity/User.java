package per.zs.login.db.entity;


import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年4月28日 上午9:13:11 
* @author sheng.zhong 
* @Description  
*/
@Data
@TableName("t_user")
public class User {
    
    @ApiModelProperty(value = "Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    @ApiModelProperty(value = "用户名（唯一不重复）")
    private String userName;
    
    @ApiModelProperty(value = "密码")
    private String password;
    
    @ApiModelProperty(value = "电话")
    private String phone;
    
    @ApiModelProperty(value = "昵称")
    private String nickName;
    
    @ApiModelProperty(value = "性别")
    private String sex;
    
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
    private Integer isDel;
}
