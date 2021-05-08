package per.zs.login.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import per.zs.common.beans.constants.Constant;
import per.zs.common.beans.enums.HttpCodeEnum;
import per.zs.common.beans.response.ResultBaseRes;
import per.zs.common.beans.response.ResultRes;
import per.zs.common.exception.CustomException;
import per.zs.common.exception.ParamException;
import per.zs.common.utils.CommonUtil;
import per.zs.login.beans.dto.RoleInfoDto;
import per.zs.login.beans.dto.UserInfoDto;
import per.zs.login.beans.req.RoleInfoReq;
import per.zs.login.beans.req.UserInfoReq;
import per.zs.login.service.RoleService;
import per.zs.login.service.UserService;
import per.zs.login.utils.RSACoder;

/** 
* Create time 2020年9月8日 下午3:46:57 
* @author sheng.zhong 
* @Description  成员管理
*/
@Api(tags = "成员及角色相关接口")
@Validated
@RestController
@RequestMapping(value = "userManage")
public class UserRoleController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    
    /*
     * 成员信息列表查询
     */
    @ApiOperation(value = "成员信息列表查询")
    @PostMapping("getUserInfoList/{pageNum}/{pageSize}")
    public IPage<UserInfoDto> getUserInfoList(@ApiParam("查询条件") @RequestParam(value = "param",required = false) String param,
            @ApiParam("当前页") @PathVariable("pageNum") Integer pageNum,
            @ApiParam("每页显示条数") @PathVariable("pageSize") Integer pageSize) {
        IPage<UserInfoDto> page = new Page<>(pageNum, pageSize);
        return userService.getUserInfoList(param,page);
    }
    
    /*
     * 成员信息新增
     */
    @ApiOperation(value = "成员信息新增")
    @PostMapping("insertUserInfo")
    public ResultRes<Integer> insertUserInfo(@Validated @RequestBody UserInfoReq req) {
        String currentUser = CommonUtil.getCurrentUserName();
        if(StringUtils.isEmpty(currentUser)) {
            return new ResultRes<>(HttpCodeEnum.FAIL,null,"获取当前登录用户失败");
        }
        String password = StringUtils.isEmpty(req.getPassword())?"":req.getPassword();
        String realPassword = "";
        try {
            realPassword = new String(RSACoder.decryptByPrivateKey(password, RSACoder.privateKey));
        }catch(Exception e) {
            throw new ParamException("密码解析错误");
        }
        if(StringUtils.isEmpty(realPassword) || !realPassword.matches(Constant.PASSWORD_REQ)) {
            throw new CustomException("密码由字母、数字或下划线组成，必须包含字母大小写和数字，且不少于8位");
        }
        req.setPassword(realPassword);
        return new ResultRes<>(HttpCodeEnum.TRUE,userService.insertUserInfo(req,currentUser),"用户新增成功");
    }
    
//    /*
//     * 成员信息删除
//     */
//    @ApiOperation(value = "成员信息删除")
//    @PostMapping("delUserInfo")
//    public ResultRes<Integer> delUserInfo(@Validated @RequestBody BatchOperReq req) {
//        return userService.delUserInfo(req);
//    }
//    
//    /*
//     * 成员信息修改
//     */
//    @ApiOperation(value = "成员信息修改")
//    @PostMapping("updUserInfo")
//    public ResultRes<Integer> updUserInfo(@Validated @RequestBody UserAddOrUpdReq req) {
//        String password = StringUtils.isEmpty(req.getPassword())?"":req.getPassword();
//        String realPassword = "";
//        try {
//            realPassword = new String(RSACoder.decryptByPrivateKey(password, RSACoder.privateKey));
//        }catch(Exception e) {
//            throw new ParamException("密码解析错误");
//        }
//        if(StringUtils.isEmpty(realPassword) || !realPassword.matches(Constant.PASSWORD_REQ)) {
//            throw new CustomException("密码由字母、数字或下划线组成，必须包含字母大小写和数字，且不少于8位");
//        }
//        req.setPassword(realPassword);
//        return userService.updUserInfo(req);
//    }
    
    @ApiOperation(value = "获取全部可用角色")
    @GetMapping("getAllRole")
    public ResultRes<List<RoleInfoDto>> getAllRole(){
        return new ResultRes<>(HttpCodeEnum.TRUE,roleService.getAllRole());
    }
    
    @ApiOperation(value = "新增角色")
    @PostMapping("insertRoleInfo")
    public ResultRes<Integer> insertRoleInfo(@Validated @RequestBody RoleInfoReq req){
        String currentUser = CommonUtil.getCurrentUserName();
        
        if(StringUtils.isEmpty(currentUser)) {
            return new ResultRes<>(HttpCodeEnum.FAIL,null,"获取当前登录用户失败");
        }
        
        return new ResultRes<>(HttpCodeEnum.TRUE,roleService.insertRoleInfo(req,currentUser),"角色新增成功");
    }
    
    @ApiOperation(value = "获取用户的角色信息")
    @GetMapping("getRoleInfo")
    public ResultRes<List<RoleInfoDto>> getRoleInfoByUserId(@RequestParam("userId") Integer userId){
        return new ResultRes<>(HttpCodeEnum.TRUE,roleService.getRoleInfoByUserId(userId));
    }
    
    @ApiOperation(value = "设置用户角色")
    @PostMapping("userRoleSet")
    public ResultBaseRes userRoleSet(@ApiParam("用户id") @RequestParam("userId")Integer userId,
            @ApiParam("角色id列表-多个以,分隔") @RequestParam("roles")String roles){
        roleService.userRoleSet(userId,roles);
        return ResultBaseRes.builder(HttpCodeEnum.TRUE,"角色设置成功");
    }
}
