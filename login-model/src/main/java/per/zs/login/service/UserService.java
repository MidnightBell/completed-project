package per.zs.login.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import per.zs.login.beans.dto.UserInfoDto;
import per.zs.login.beans.req.UserInfoReq;
import per.zs.login.db.entity.User;

/** 
* Create time 2021年4月28日 上午9:42:57 
* @author sheng.zhong 
* @Description  
*/
public interface UserService extends IService<User>{

    /**
     * 根据用户名获取用户信息
     * @param userName 用户名-唯一
     * @return
     */
    User getUserByName(String userName);

    /**
     * 新增用户
     * @param req 用户信息
     * @param currentUser 当前登录用户
     * @return
     */
    Integer insertUserInfo(UserInfoReq req, String currentUser);

    /**
     * 获取用户列表
     * @param param 查询参数
     * @param page 分页参数
     * @return
     */
    IPage<UserInfoDto> getUserInfoList(String param,IPage<UserInfoDto> page);

    /**
     * 修改用户密码
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    Integer updatePassword(String userName, String password);

}
