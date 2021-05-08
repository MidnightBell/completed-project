package per.zs.forum.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import per.zs.forum.beans.dto.PostInfoDto;
import per.zs.forum.db.entity.PostInfo;

/** 
* Create time 2021年5月7日 下午3:33:10 
* @author sheng.zhong 
* @Description  
*/
@Mapper
public interface PostInfoMapper extends BaseMapper<PostInfo>{

    /**
     * 分页获取主题帖信息
     * @param page 分页信息
     * @param queryWrapper 查询条件
     * @return
     */
    @Select(value = "select ${ew.sqlSelect} from t_post_info p left join t_user u on u.id = p.create_user ${ew.customSqlSegment}")
    IPage<PostInfoDto> selectByParam(IPage<PostInfoDto> page, @Param(Constants.WRAPPER)QueryWrapper<PostInfoDto> queryWrapper);

    /**
     * 根据用户名获取用户id
     * @param userName 用户名
     * @return
     */
    @Select(value = "select id from t_user where user_name = #{userName}")
    Integer selectUserIdByName(@Param("userName")String userName);

    /**
     * 根主题帖id获取主题帖详情
     * @param postId 主题帖id
     * @return
     */
    @Select(value = "select p.id as postId, p.post_title, p.post_content, p.reply_num, u.nick_name as createUser,p.create_time, p.update_time, "
            + "case when u.user_name = #{currentUser} then 1 else 0 end as isDelButton, "
            + "case when u.user_name = #{currentUser} then 1 else 0 end as isUpdateButton "
            + "from t_post_info p left join t_user u on u.id = p.create_user where p.id = #{postId} and p.is_del = 0")
    PostInfoDto selectByPostId(@Param("postId")Integer postId,@Param("currentUser")String currentUser);

    /**
     * 获取当前主题帖的创建人用户名
     * @param postId 主题帖id
     * @return
     */
    @Select(value = "select u.user_name from t_post_info p left join t_user u on u.id = p.create_user where p.id = #{postId}")
    String selectCreateUserName(@Param("postId")Integer postId);

    /**
     * 将主题帖设置为删除
     * @param postId 主题帖id
     */
    @Update(value = "update t_post_info set is_del = 1 where id = #{postId}")
    Integer setPostToDel(@Param("postId")Integer postId);

}
