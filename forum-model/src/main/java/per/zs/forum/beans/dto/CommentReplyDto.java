package per.zs.forum.beans.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年5月8日 上午11:31:26 
* @author sheng.zhong 
* @Description  
*/
@Data
public class CommentReplyDto {
    @ApiModelProperty(value = "回复Id")
    private Integer replyId;
    
    @ApiModelProperty(value = "评论id")
    private Integer commentId;
    
    @ApiModelProperty(value = "评论回复详情")
    private String reply;
    
    @ApiModelProperty(value = "回复对象id")
    private Integer replyTo;
    
    @ApiModelProperty(value = "回复人id")
    private Integer createUser;
    
    @ApiModelProperty(value = "回复时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "回复修改时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @ApiModelProperty(value = "是否显示删除按钮（0：不显示 1：显示）")
    private Integer isDelButton = 0;
    @ApiModelProperty(value = "是否显示修改按钮（0：不显示 1：显示）")
    private Integer isUpdateButton = 0;
}
