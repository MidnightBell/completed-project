package per.zs.forum.beans.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年5月7日 下午4:19:05 
* @author sheng.zhong 
* @Description  
*/
@Data
public class PostPageReq extends BasePageReq{
    
    @ApiModelProperty(value = "查询条件")
    private String param;
    
    @ApiModelProperty(value = "查询起始时间")
//    @Pattern(regexp = Constant.REGEX_STRING_DATE, message = "起始时间值格式错误")
    private String startTime;
    
    @ApiModelProperty(value = "查询结束时间")
//    @Pattern(regexp = Constant.REGEX_STRING_DATE, message = "结束时间值格式错误")
    private String endTime;
}
