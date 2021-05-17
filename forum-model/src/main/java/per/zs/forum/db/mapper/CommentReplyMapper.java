package per.zs.forum.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import per.zs.forum.beans.dto.CommentReplyDto;
import per.zs.forum.db.entity.CommentReply;

/** 
* Create time 2021年5月7日 下午3:48:50 
* @author sheng.zhong 
* @Description  
*/
@Mapper
public interface CommentReplyMapper extends BaseMapper<CommentReply> {

    /**
     * 分页获取评论回复
     * @param page 分页参数
     * @param currentUser 当前登录用户
     * @param queryWrapper 查询条件
     * @return
     */
    @Select(value = "select ${ew.sqlSelect} from t_comment_reply r left join t_user u on u.id = r.create_user"
            + " left join t_user u1 on u1.id = r.reply_to ${ew.customSqlSegment}")
    IPage<CommentReplyDto> selectByCommentId(IPage<CommentReplyDto> page, @Param("currentUser")String currentUser,
            @Param(Constants.WRAPPER)QueryWrapper<CommentReplyDto> queryWrapper);

    /**
     * 获取回复人用户名
     * @param replyId 回复id
     * @return
     */
    @Select(value = "select u.user_name from t_comment_reply r left join t_user u on u.id = r.create_user where r.id = #{replyId}")
    String selectCreateUserName(@Param("replyId")Integer replyId);

    /**
     * 将回复设置为删除
     * @param replyId 回复id
     */
    @Update(value = "update t_comment_reply set is_del = 1 where id = #{replyId}")
    Integer setReplyToDel(@Param("replyId")Integer replyId);

    /**
     * 根据回复id获取评论id
     * @param replyId 回复id
     * @return
     */
    @Select(value = "select comment_id from t_comment_reply where id = #{replyId}")
    Integer selectCommentIdByReplyId(@Param("replyId")Integer replyId);

    /**
     * 根据回复id获取回复详情
     * @param replyId 回复id
     * @return
     */
    @Select(value = "select id as replyId,comment_id,reply,reply_to,create_time,create_user from t_comment_reply where id = #{replyId} and is_del = 0")
    CommentReplyDto selectByReplyId(@Param("replyId")Integer replyId);

}
