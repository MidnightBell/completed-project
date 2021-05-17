package per.zs.forum.beans.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年5月8日 上午10:05:06 
* @author sheng.zhong 
* @Description  新增及修改评论请求体
*/
@Data
public class PostCommentReq {
    @ApiModelProperty(value = "评论id-新增时为空，修改时必填")
    private Integer commentId;
    
    @ApiModelProperty(value = "主题帖id")
    @NotNull(message = "主题帖id不能为空")
    private Integer postId;
    
    @ApiModelProperty(value = "评论内容")
    @NotEmpty(message = "评论内容不能为空")
    private String postComment;
}
