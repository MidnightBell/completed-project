package per.zs.forum.db.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年5月7日 下午3:41:27 
* @author sheng.zhong 
* @Description  
*/
@Data
@TableName("t_post_comment")
public class PostComment {
    @ApiModelProperty(value = "Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    @ApiModelProperty(value = "主题贴id")
    private Integer postId;
    
    @ApiModelProperty(value = "评论")
    private String postComment;
    
    @ApiModelProperty(value = "评论回复数")
    private Integer replyNum;
    
    @ApiModelProperty(value = "评论创建人id")
    private Integer createUser;
    
    @ApiModelProperty(value = "评论时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @ApiModelProperty(value = "是否删除 1：删除 0：未删除")
    private Integer isDel;
}
