package per.zs.getAway.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* Create time 2021年5月17日 下午5:18:35 
* @author sheng.zhong 
* @Description  
*/
@Api(tags = "网关测试接口")
@Validated
@RestController
@RequestMapping(value = "test")
public class TestController {
    @ApiOperation(value = "测试接口")
    @GetMapping("check")
    public String test() {
        return "successs";
    }
}
