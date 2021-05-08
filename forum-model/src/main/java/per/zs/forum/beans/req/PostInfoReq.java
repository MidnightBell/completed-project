package per.zs.forum.beans.req;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年5月7日 下午5:01:53 
* @author sheng.zhong 
* @Description  新增及修改主题帖请求体
*/
@Data
public class PostInfoReq {
    @ApiModelProperty(value = "主题帖id-新增时为空，修改时必填")
    private Integer postId;
    
    @ApiModelProperty(value = "标题")
    @NotEmpty(message = "主题帖标题不能为空")
    private String postTitle;
    
    @ApiModelProperty(value = "主题帖正文")
    @NotEmpty(message = "主题帖正文不能为空")
    private String postContent;
    
}
