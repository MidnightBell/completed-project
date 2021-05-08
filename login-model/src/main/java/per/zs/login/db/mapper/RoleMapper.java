package per.zs.login.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import per.zs.login.beans.dto.RoleInfoDto;
import per.zs.login.db.entity.Role;

/** 
* Create time 2021年4月29日 上午11:10:57 
* @author sheng.zhong 
* @Description  
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role>{

    /**
     * 根据用户id获取该用户角色
     * @param userId 用户id
     * @return
     */
    @Select(value = "select role_id from t_user_role where user_id = #{userId}")
    String selectRolesFromUserRole(@Param("userId")Integer userId);

    /**
     * 设置用户角色
     * @param userId 用户id
     * @param roleId 角色id
     */
    @Insert(value = "insert into t_user_role (user_id,role_id) values (#{userId},#{roleId})")
    int insertRolseToUserRole(@Param("userId")Integer userId, @Param("roleId")String roleId);

    /**
     * 修改用户角色
     * @param userId 用户id
     * @param roles 角色id列表
     */
    @Update(value = "update t_user_role set role_id = #{roles} where user_id = #{userId}")
    int updateRolesToUserRole(@Param("userId")Integer userId, @Param("roles")String roles);

    /**
     * 根据用户id查询角色信息
     * @param wrapper 查询条件
     * @return
     */
    @Select(value = "select ${ew.sqlSelect} from t_user_role ur left join t_role r on ur.role_id = r.id ${ew.customSqlSegment}")
    List<RoleInfoDto> selectRoleInfoDto(@Param(Constants.WRAPPER) Wrapper<RoleInfoDto> wrapper);

    /**
     * 获取符合条件的全部角色
     * @param queryWrapper
     */
    @Select(value = "select ${ew.sqlSelect} from t_role ${ew.customSqlSegment}")
    List<RoleInfoDto> selectAllRole(@Param(Constants.WRAPPER) Wrapper<RoleInfoDto> wrapper);

    /**
     * 删除用户的角色信息
     * @param userId 用户id
     */
    @Delete(value = "delete from t_user_role where user_id = #{userId}")
    int deletefromUserRole(@Param("userId")Integer userId);

}
