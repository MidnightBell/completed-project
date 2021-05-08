package per.zs.forum.beans.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* Create time 2021年5月7日 下午4:15:46 
* @author sheng.zhong 
* @Description  
*/
@Data
public class BasePageReq {
    
    @ApiModelProperty(value = "当前页",example = "1", position = 1)
    private Integer pageNum = 1;
    
    @ApiModelProperty(value = "每页数量",example = "10", position = 2)
    private Integer pageSize = 10;
}
