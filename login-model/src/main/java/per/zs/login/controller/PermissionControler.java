package per.zs.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import per.zs.login.beans.dto.PermissionDto;
import per.zs.login.beans.enums.HttpCodeEnum;
import per.zs.login.beans.response.ResultBaseRes;
import per.zs.login.beans.response.ResultRes;
import per.zs.login.service.PermissionService;

/** 
* Create time 2021年4月30日 上午10:17:05 
* @author sheng.zhong 
* @Description  权限管理
*/
@Api(tags = "权限相关接口")
@Validated
@RestController
@RequestMapping(value = "permissionManage")
public class PermissionControler {
    
    @Autowired
    private PermissionService permissionService;
    
    @ApiOperation("获取用户权限")
    @GetMapping(value = "userPermission/{userId}")
    public ResultRes<List<PermissionDto>> userPermission(@PathVariable(value = "userId",required = true) Integer userId) {
        List<PermissionDto> permissionDtoList = permissionService.getUserPermission(userId);
        return new ResultRes<>(HttpCodeEnum.TRUE,permissionDtoList,"用户权限获取成功");
    }
    
    @ApiOperation("获取角色权限-包含未拥有的权限")
    @GetMapping(value = "rolePermission/{roleId}")
    public ResultRes<List<PermissionDto>> rolePermission(@PathVariable(value = "roleId",required = true) Integer roleId) {
        List<PermissionDto> permissionDtoList = permissionService.getRolePermission(roleId);
        return new ResultRes<>(HttpCodeEnum.TRUE,permissionDtoList,"用户权限获取成功");
    }
    
    @ApiOperation(value = "设置角色权限")
    @PostMapping("rolePermissionSet")
    public ResultBaseRes rolePermissionSet(@ApiParam("角色id") @RequestParam("roleId")Integer roleId,
            @ApiParam("权限id列表-多个以,分隔") @RequestParam("permissions")String permissions){
        permissionService.rolePermissionSet(roleId,permissions);
        return ResultBaseRes.builder(HttpCodeEnum.TRUE,"角色设置成功");
    }
}
