package per.zs.login.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import per.zs.login.beans.dto.PermissionDto;
import per.zs.login.db.entity.Permission;

/** 
* Create time 2021年4月30日 上午10:26:31 
* @author sheng.zhong 
* @Description  
*/
@Mapper
public interface PermissionMapper extends BaseMapper<Permission>{

    /**
     * 获取用户权限
     * @param userId 用户id
     * @return
     */
    @Select(value = "select p.id as perId,p.per_name,p.per_value,p.per_type,p.parent_id,p.per_description,p.menu_comonent,p.is_del "
            + "from t_permission p left join t_role_permission rp on rp.permission_id = p.id "
            + "left join t_user_role ur on ur.role_id = rp.role_id "
            + "where ur.user_id = #{userId} group by perId,p.per_name,p.per_value,p.per_type,p.parent_id,p.per_description,p.menu_comonent,p.is_del")
    List<PermissionDto> selectPermissionByUserId(@Param("userId")Integer userId);

    /**
     * 获取角色权限
     * @param roleId 角色id
     * @return
     */
    @Select(value = "select p.id as perId,p.per_name,p.per_value,p.per_type,p.parent_id,p.per_description,p.menu_comonent,p.is_del "
            + "from t_permission p left join t_role_permission rp on rp.permission_id = p.id "
            + "where rp.role_id = #{roleId}")
    List<PermissionDto> selectPermissionByRoleId(@Param("roleId")Integer roleId);

    /**
     * 获取全部权限
     * @return
     */
    @Select(value = "select p.id as perId,p.per_name,p.per_value,p.per_type,p.parent_id,p.per_description,p.menu_comonent,p.is_del "
            + "from t_permission p")
    List<PermissionDto> selectAllPermission();

    /**
     * 删除角色的权限信息
     * @param roleId 角色id
     */
    @Delete(value = "delete from t_role_permission where role_id = #{roleId}")
    int deletefromRolePermission(@Param("roleId")Integer roleId);

    /**
     * 设置角色权限
     * @param roleId 角色id
     * @param permission 权限id
     */
    @Insert(value = "insert into t_role_permission (role_id,permission_id) values (#{roleId},#{permissionId})")
    int insertPermissionsToRolePermission(@Param("roleId")Integer roleId, @Param("permissionId")String permissionId);

}
