package per.zs.forum.beans.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年5月8日 上午9:43:00 
* @author sheng.zhong 
* @Description  
*/
@Data
public class PostCommentDto {
    @ApiModelProperty(value = "评论Id")
    private Integer commentId;
    
    @ApiModelProperty(value = "主题贴id")
    private Integer postId;
    
    @ApiModelProperty(value = "评论")
    private String postComment;
    
    @ApiModelProperty(value = "评论回复数")
    private Integer replyNum;
    
    @ApiModelProperty(value = "评论创建人")
    private String createUser;
    
    @ApiModelProperty(value = "评论时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @ApiModelProperty(value = "是否显示删除按钮（0：不显示 1：显示）")
    private Integer isDelButton = 0;
    @ApiModelProperty(value = "是否显示修改按钮（0：不显示 1：显示）")
    private Integer isUpdateButton = 0;
}
