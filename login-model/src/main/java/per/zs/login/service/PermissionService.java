package per.zs.login.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import per.zs.login.beans.dto.PermissionDto;
import per.zs.login.db.entity.Permission;

/** 
* Create time 2021年4月30日 上午10:27:23 
* @author sheng.zhong 
* @Description  
*/
public interface PermissionService extends IService<Permission>{

    /**
     * 获取用户权限
     * @param userId 用户id
     * @return
     */
    List<PermissionDto> getUserPermission(Integer userId);

    /**
     * 获取角色权限
     * @param roleId 角色id
     * @return
     */
    List<PermissionDto> getRolePermission(Integer roleId);

    /**
     * 设置角色权限
     * @param roleId 角色id
     * @param permissions 权限列表-多个用,分隔
     */
    void rolePermissionSet(Integer roleId, String permissions);

}
