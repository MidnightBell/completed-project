package per.zs.forum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import per.zs.forum.beans.dto.PostCommentDto;
import per.zs.forum.beans.req.PostCommentReq;
import per.zs.forum.db.entity.PostComment;

/** 
* Create time 2021年5月7日 下午3:49:34 
* @author sheng.zhong 
* @Description  
*/
public interface PostCommentService extends IService<PostComment>{

    /**
     * 分页获取主题帖评论
     * @param postId 主题帖id
     * @param currentUser 当前登录用户
     * @param page 分页参数
     * @return
     */
    IPage<PostCommentDto> getPostCommentList(Integer postId, String currentUser, IPage<PostCommentDto> page);

    /**
     * 新增评论
     * @param req 评论信息
     * @param currentUser 当前登录用户
     * @return
     */
    void publishNewPostComment(PostCommentReq req, String currentUser);

    /**
     * 根据评论id删除评论
     * @param commentId 评论id
     * @param currentUser 当前登录用户
     * @return
     */
    void deletePostComment(Integer commentId, String currentUser);

    /**
     * 修改评论
     * @param req 贫血信息
     * @param currentUser 当前登录用户
     * @return
     */
    Integer updatePostComment(PostCommentReq req, String currentUser);

}
