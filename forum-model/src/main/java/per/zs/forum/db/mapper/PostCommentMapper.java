package per.zs.forum.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import per.zs.forum.beans.dto.PostCommentDto;
import per.zs.forum.db.entity.PostComment;

/** 
* Create time 2021年5月7日 下午3:48:15 
* @author sheng.zhong 
* @Description  
*/
@Mapper
public interface PostCommentMapper extends BaseMapper<PostComment>{

    /**
     * 分页获取主题帖评论
     * @param page 分页参数
     * @param currentUser 当前登录用户
     * @param queryWrapper 查询条件
     * @return
     */
    @Select(value = "select ${ew.sqlSelect} from t_post_comment c left join t_user u on u.id = c.create_user ${ew.customSqlSegment}")
    IPage<PostCommentDto> selectByPostId(IPage<PostCommentDto> page,@Param("currentUser")String currentUser, @Param(Constants.WRAPPER)QueryWrapper<PostCommentDto> queryWrapper);

    /**
     * 获取评论创建人用户名
     * @param commentId 评论id
     * @return
     */
    @Select(value = "select u.user_name from t_post_comment c left join t_user u on u.id = c.create_user where c.id = #{commentId}")
    String selectCreateUserName(@Param("commentId")Integer commentId);

    /**
     * 将评论设置为删除
     * @param commentId 评论id
     * @return
     */
    @Update(value = "update t_post_comment set is_del = 1 where id = #{commentId}")
    Integer setCommentToDel(@Param("commentId")Integer commentId);

    /**
     * 将主题帖下所有评论设置为删除
     * @param postId 主题帖id
     */
    @Update(value = "update t_post_comment set is_del = 1 where post_id = #{postId}")
    Integer setPostCommentToDel(@Param("postId")Integer postId);

    /**
     * 根据评论id获取主题帖id
     * @param commentId 评论id
     * @return
     */
    @Select(value = "select post_id from t_post_comment where id = #{commentId}")
    Integer selectPostIdByCommentId(@Param("commentId")Integer commentId);

}
