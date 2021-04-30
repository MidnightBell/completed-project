package per.zs.login.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import per.zs.login.beans.dto.UserInfoDto;
import per.zs.login.db.entity.User;

/** 
* Create time 2021年4月28日 上午9:40:59 
* @author sheng.zhong 
* @Description  
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名获取用户id
     * @param userNmae 用户名
     * @return
     */
    @Select(value = "select id from t_user where user_name = #{userName}")
    Integer selectIdByName(@Param("userName")String userName);

    /**
     * 根据条件分页查询用户信息
     * @param queryWrapper 查询条件
     * @param page 分页参数
     * @return
     */
    @Select(value = "select ${ew.sqlSelect from t_user ${ew.customSqlSegment}}")
    IPage<UserInfoDto> selectByParam(@Param(Constants.WRAPPER)Wrapper<UserInfoDto> wrapper, IPage<UserInfoDto> page);

}
