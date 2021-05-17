package per.zs.forum.beans.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年5月17日 上午9:27:29 
* @author sheng.zhong 
* @Description  新增及修改评论回复请求体
*/
@Data
public class CommentReplyReq {
    @ApiModelProperty(value = "回复id-新增时为空，修改时必填")
    private Integer replyId;
    
    @ApiModelProperty(value = "评论id")
    @NotNull(message = "评论id不能为空")
    private Integer commentId;
    
    @ApiModelProperty(value = "回复内容")
    @NotEmpty(message = "回复内容不能为空")
    private String reply;
}
