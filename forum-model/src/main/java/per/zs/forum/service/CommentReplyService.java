package per.zs.forum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import per.zs.forum.beans.dto.CommentReplyDto;
import per.zs.forum.beans.req.CommentReplyReq;
import per.zs.forum.db.entity.CommentReply;

/** 
* Create time 2021年5月7日 下午3:50:09 
* @author sheng.zhong 
* @Description  
*/
public interface CommentReplyService extends IService<CommentReply> {

    /**
     * 分页获取评论回复
     * @param commentId 评论id
     * @param currentUser 当前登录用户
     * @param page 分页参数
     * @return
     */
    IPage<CommentReplyDto> getCommentReplyList(Integer commentId, String currentUser, IPage<CommentReplyDto> page);

    /**
     * 新增评论回复
     * @param req 回复信息
     * @param currentUser 当前登录用户
     */
    void publishNewCommentReply(CommentReplyReq req, String currentUser);

    /**
     * 根据回复id删除回复
     * @param replyId 回复id
     * @param currentUser 当前登录用户
     */
    void deleteCommentReply(Integer replyId, String currentUser);

    /**
     * 修改回复
     * @param req 回复信息
     * @param currentUser 当前登录用户
     * @return
     */
    Integer updateCommentReply(CommentReplyReq req, String currentUser);

    /**
     * 根据回复id获取回复详情
     * @param replyId 回复id
     * @return
     */
    CommentReplyDto getCommentReplyInfo(Integer replyId);
}
