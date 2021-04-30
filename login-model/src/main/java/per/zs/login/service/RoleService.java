package per.zs.login.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import per.zs.login.beans.dto.RoleInfoDto;
import per.zs.login.beans.req.RoleInfoReq;
import per.zs.login.db.entity.Role;

/** 
* Create time 2021年4月29日 上午11:11:57 
* @author sheng.zhong 
* @Description  
*/
public interface RoleService extends IService<Role> {
    
    /**
     * 创建角色
     * @param req 角色信息
     * @param currentUser 当前登录用户
     * @return
     */
    Integer insertRoleInfo(RoleInfoReq req,String currentUser);

    /**
     * 设置用户角色
     * @param userId 用户id
     * @param roles 角色列表-多个用,分隔
     * @return
     */
    void userRoleSet(Integer userId, String roles);

    /**
     * 根据用户id获取用户角色信息
     * @param userId 用户id
     * @return
     */
    List<RoleInfoDto> getRoleInfoByUserId(Integer userId);

    /**
     * 获取全部可用角色
     * @return
     */
    List<RoleInfoDto> getAllRole();
    
}
