package per.zs.forum.db.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年5月7日 下午3:43:57 
* @author sheng.zhong 
* @Description  
*/
@Data
@TableName("t_comment_reply")
public class CommentReply {
    @ApiModelProperty(value = "Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    @ApiModelProperty(value = "评论id")
    private Integer commentId;
    
    @ApiModelProperty(value = "评论回复详情")
    private String reply;
    
    @ApiModelProperty(value = "回复对象id")
    private Integer replyTo;
    
    @ApiModelProperty(value = "回复人id")
    private Integer createUser;
    
    @ApiModelProperty(value = "回复时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "回复修改时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @ApiModelProperty(value = "是否删除 1：删除 0：未删除")
    private Integer isDel;
}
