package per.zs.forum.db.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年5月7日 下午3:26:19 
* @author sheng.zhong 
* @Description  
*/
@Data
@TableName("t_post_info")
public class PostInfo {
    @ApiModelProperty(value = "Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    @ApiModelProperty(value = "标题")
    private String postTitle;
    
    @ApiModelProperty(value = "主题帖正文")
    private String postContent;
    
    @ApiModelProperty(value = "评论数")
    private Integer replyNum;
    
    @ApiModelProperty(value = "发帖人id")
    private Integer createUser;
    
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @ApiModelProperty(value = "是否删除 1：删除 0：未删除")
    private Integer isDel;
    
}
