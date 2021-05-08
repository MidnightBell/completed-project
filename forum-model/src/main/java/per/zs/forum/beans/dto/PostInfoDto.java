package per.zs.forum.beans.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年5月7日 下午4:12:04 
* @author sheng.zhong 
* @Description  
*/
@Data
public class PostInfoDto {
    @ApiModelProperty(value = "主题帖id")
    private Integer postId;
    
    @ApiModelProperty(value = "标题")
    private String postTitle;
    
    @ApiModelProperty(value = "主题帖正文")
    private String postContent;
    
    @ApiModelProperty(value = "评论数")
    private Integer replyNum;
    
    @ApiModelProperty(value = "发帖人")
    private String createUser;
    
    @ApiModelProperty(value = "创建时间")
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
