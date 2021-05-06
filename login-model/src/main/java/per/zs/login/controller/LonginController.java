package per.zs.login.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import per.zs.login.beans.constants.Constant;
import per.zs.login.beans.dto.UserInfoDto;
import per.zs.login.beans.dto.VerifyCode;
import per.zs.login.beans.enums.HttpCodeEnum;
import per.zs.login.beans.req.LoginReq;
import per.zs.login.beans.req.UserInfoReq;
import per.zs.login.beans.response.ResultBaseRes;
import per.zs.login.beans.response.ResultRes;
import per.zs.login.db.entity.User;
import per.zs.login.exception.CustomException;
import per.zs.login.exception.ParamException;
import per.zs.login.service.RoleService;
import per.zs.login.service.SimpleCharVerifyCodeGenImpl;
import per.zs.login.service.UserService;
import per.zs.login.utils.CommonUtil;
import per.zs.login.utils.CookieUtil;
import per.zs.login.utils.LocalCacheUtils;
import per.zs.login.utils.RSACoder;

/** 
* Create time 2020年9月10日 下午3:29:54 
* @author sheng.zhong 
* @Description  登录相关接口
*/
@Api(tags = "登录相关接口")
@Validated
@RestController
@RequestMapping(value = "auth")
public class LonginController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    
    /*
     * 密码加密-防止页面密码明文传输
     */
    @ApiOperation(value = "密码加密-前端调用")
    @GetMapping("encryptPassword")
    public String encryptByPublicKey(String password) {
        try {
            return Base64.encodeBase64String(RSACoder.encryptByPublicKey(password, RSACoder.publicKey));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("加密失败");
        }
    }
    
    @ApiOperation(value = "获取用户信息")
    @PostMapping("userInfo")
    public ResultRes<UserInfoDto> userInfo(@RequestParam(value = "token",required = true)String token){
        String userName = CookieUtil.getUsernameFromToken(token, Constant.USERNAME);
        if (StringUtils.isBlank(userName)) {
            throw new CustomException("获取用户信息失败");
        } else {
            User user = userService.getUserByName(userName);
            if(user == null) {
                return new ResultRes<>(HttpCodeEnum.NO_RETURN);
            }else {
                UserInfoDto userInfoDto = new UserInfoDto(user);
                userInfoDto.setRole(roleService.getRoleInfoByUserId(user.getId()));
                return new ResultRes<>(HttpCodeEnum.TRUE,userInfoDto);
            }
        }
    }
    
    /*
     * 注册
     */
    @ApiOperation(value = "注册")
    @PostMapping("register")
    public ResultRes<Integer> register(@Validated @RequestBody UserInfoReq req) {
        String currentUser = CommonUtil.getCurrentUserName();
        
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
    
    /*
     * 登录
     */
    @ApiOperation(value = "登录")
    @PostMapping("login")
    public ResultRes<String> login(@Validated @RequestBody LoginReq req,HttpServletRequest request) throws UnsupportedEncodingException {
        Object verifyCodeNum = LocalCacheUtils.getLocalVerifyCodeCache(request.getRemoteAddr());
        if(verifyCodeNum == null || StringUtils.isEmpty(String.valueOf(verifyCodeNum))) {
            throw new ParamException("验证码失效");
        }
        if(!req.getVerifyCode().equalsIgnoreCase(String.valueOf(verifyCodeNum))){
            throw new ParamException("验证码错误");
        }
        //验证码通过则让原验证码无效
        LocalCacheUtils.setLocalVerifyCodeCache(request.getRemoteAddr(),"");
        //暂时不加密
        String userName = req.getUserName();
        String password = req.getPassword();//前端通过RSA加密后的密码
        String realPassword = "";
//        String encryptPassword = "";
        try {
            realPassword = new String(RSACoder.decryptByPrivateKey(password, RSACoder.privateKey));
//            encryptPassword = DESUtil.encryptBasedDes(realPassword);//密码DES加密-用于与保存在数据库中的密码匹配
        }catch(Exception e) {
            throw new ParamException("密码解析异常错误");
        }
        Object localCacheValue = LocalCacheUtils.getLocalCache(userName);//获取当前用户密码输入次数
        int intValue = 0;
        int maxValue = 4;
        if (localCacheValue != null) {
            intValue = Integer.parseInt(String.valueOf(localCacheValue));
            if (intValue > maxValue) {
                throw new ParamException("密码错误5次,请10分钟后登录!");
            }
        }
        User user = userService.getUserByName(req.getUserName());
        if (user == null) {
            throw new ParamException("用户名或密码错误");
        }
        if (user.getIsDel().equals(1)) {
            throw new ParamException("用户名或密码错误");
        }
//        if (user.getPassword().equals(encryptPassword)) {
        if (user.getPassword().equals(realPassword)) {
            //登录成功,清除登录次数,不处理,就让其10分钟后自动过期
            if (intValue > 0) {
                LocalCacheUtils.setLocalCache(userName, 0);
            }
            
            //生成token
            String randomKey = CookieUtil.getRandomKey();
            String token = CookieUtil.generateToken(userName, randomKey, 7*24*60*60);
            
            /*
             * 保存下此次登录生成的随机数，token验证时需验证随机数是否为本次登录保存的随机数
             */
            LocalCacheUtils.setLocalRandomCache(userName,randomKey);
            
            return new ResultRes<>(HttpCodeEnum.TRUE,token,"登陆成功");
        } else {
            //密码不对,记录登录次数
            LocalCacheUtils.setLocalCache(userName, intValue+1);
            throw new ParamException("用户名或密码错误");
        }
    }
    
//    /*
//     * 单点登录认证-仅验证账号是否在系统中存在
//     */
//    @ApiOperation(value = "单点登录认证-仅验证账号是否在系统中存在")
//    @GetMapping("loginAuth")
//    public ResultRes<UserInfoDto> loginAuth(@RequestParam("account") String account,HttpServletResponse response,HttpServletRequest request) {
//        try {
//            User user = userService.authSingleUserName(account, response);
//            if (user != null) {
//                //设置Cookie 
//                String randomKey = CookieUtil.getRandomKey();
//                String encryptUserName = DESUtil.encryptBasedDes(user.getUserName()+"clagra"+randomKey);
//                String token = CookieUtil.generateToken(encryptUserName, randomKey, 3600);
//                LocalCacheUtils.setLocalRandomCache(user.getUserName(),randomKey);
//                Cookie cookie = new Cookie("classify_user", "classifyGrade"+token);
//                cookie.setHttpOnly(true);
//                cookie.setPath("/");
//                cookie.setMaxAge(3600);
//                response.addCookie(cookie);
//                UserInfoDto userInfoDto = new UserInfoDto(user);
//                response.sendRedirect(redirectUrl);
//                return new ResultRes<>(HttpCodeEnum.TRUE, userInfoDto, "登陆成功");
//            } else {
//                response.sendRedirect(redirectUrl);
//                return new ResultRes<>(HttpCodeEnum.UN_LOGIN, null, "认证失败：当前用户账号不存在");
//            }
//        } catch (Exception e) {
////            log.error(e.getMessage());
//            return new ResultRes<>(HttpCodeEnum.UN_LOGIN, null, "认证失败：账号解析错误");
//        }
//    }
    
    /**
     * 修改密码
     */
    @ApiOperation(value = "修改密码-修改后返回新的token")
    @PostMapping("updatePassword")
    public ResultRes<String> updatePassword(String oldPassword, String newPassword) {
        String currentUser = CommonUtil.getCurrentUserName();
        
        if(StringUtils.isEmpty(currentUser)) {
            return new ResultRes<>(HttpCodeEnum.FAIL,null,"获取当前登录用户失败");
        }
        String realNewPassword = "";
        String realOldPassword = "";
        try {
            realNewPassword = new String(RSACoder.decryptByPrivateKey(newPassword, RSACoder.privateKey));
            realOldPassword = new String(RSACoder.decryptByPrivateKey(oldPassword, RSACoder.privateKey));
        }catch(Exception e) {
            throw new ParamException("密码解析错误");
        }
        if(StringUtils.isEmpty(realNewPassword) || !realNewPassword.matches(Constant.PASSWORD_REQ)) {
            throw new CustomException("密码由字母、数字或下划线组成，必须包含字母大小写和数字，且不少于8位");
        }
        
        User user = userService.getUserByName(currentUser);
        String oPassword = user.getPassword();
//        String encryptNewPassword = DESUtil.encryptBasedDes(realNewPassword);
//        String encryptOldPassword = DESUtil.encryptBasedDes(realOldPassword);
//        
//        if(!StringUtils.equals(encryptOldPassword,oPassword)) {
//            throw new CustomException("原始密码错误");
//        }
//        if(StringUtils.equals(encryptNewPassword,oPassword)) {
//            throw new CustomException("新密码不能与原密码相同");
//        }
        
        if(!StringUtils.equals(realOldPassword,oPassword)) {
            throw new CustomException("原始密码错误");
        }
        if(StringUtils.equals(realNewPassword,oPassword)) {
            throw new CustomException("新密码不能与原密码相同");
        }
        Integer successNum = userService.updatePassword(currentUser,realNewPassword);
        if(successNum > 0) {
            //生成token
            String randomKey = CookieUtil.getRandomKey();
            String token = CookieUtil.generateToken(currentUser, randomKey, 7*24*60*60);
            
            /*
             * 修改密码后保存新的随机数-使原来的token失效（在测试时从header获取token不管用——未验证随机数，会导致之前的token仍然有效，从cookie获取则无此问题）
             */
            LocalCacheUtils.setLocalRandomCache(currentUser,randomKey);
            
            return new ResultRes<>(HttpCodeEnum.TRUE,token,"修改成功");
        }else {
            return new ResultRes<>(HttpCodeEnum.FAIL,null,"修改失败");
        }
    }
    
    /**
     * 退出
     */
    @ApiOperation(value = "退出")
    @GetMapping("logout")
    public ResultBaseRes logout() {
        String userName = CommonUtil.getCurrentUserName();
        if(StringUtils.isEmpty(userName)) {
            return ResultBaseRes.builder(HttpCodeEnum.FAIL,"获取当前登录用户失败");
        }
        LocalCacheUtils.setLocalRandomCache(userName,"");//在测试时从header获取token不管用——未验证随机数，会导致之前的token仍然有效，从cookie获取则无此问题
        return ResultBaseRes.builder(HttpCodeEnum.TRUE,"退出成功");
    }
    
    @ApiOperation(value = "验证码")
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        SimpleCharVerifyCodeGenImpl iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
        try {
            //设置长宽
            VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
            String code = verifyCode.getCode();
            //将verifyCode绑定请求客户端的ip后本地缓存一分钟
            LocalCacheUtils.setLocalVerifyCodeCache(request.getRemoteAddr(),code);
            //设置响应头
            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
