package per.zs.forum.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import per.zs.common.exception.CustomException;
import per.zs.forum.beans.dto.CommentReplyDto;
import per.zs.forum.beans.dto.PostCommentDto;
import per.zs.forum.beans.dto.PostInfoDto;
import per.zs.forum.beans.req.CommentReplyReq;
import per.zs.forum.db.entity.CommentReply;
import per.zs.forum.db.entity.PostComment;
import per.zs.forum.db.entity.PostInfo;
import per.zs.forum.db.mapper.CommentReplyMapper;
import per.zs.forum.db.mapper.PostCommentMapper;
import per.zs.forum.db.mapper.PostInfoMapper;
import per.zs.forum.service.CommentReplyService;
import per.zs.forum.service.PostCommentService;

/** 
* Create time 2021年5月7日 下午3:52:11 
* @author sheng.zhong 
* @Description  
*/
@Service("commentReplyServiceImpl")
public class CommentReplyServiceImpl extends ServiceImpl<CommentReplyMapper, CommentReply> implements CommentReplyService{

    @Autowired
    private PostCommentService postCommentService;
    @Autowired
    private PostInfoMapper postInfoMapper;
    @Autowired
    private PostCommentMapper postCommentMapper;
    
    @Override
    public IPage<CommentReplyDto> getCommentReplyList(Integer commentId, String currentUser,
            IPage<CommentReplyDto> page) {
        //1.判断评论是否存在
        PostCommentDto postCommentDto = postCommentService.getPostCommentInfo(commentId);
        if(postCommentDto == null) {
            throw new CustomException("当前评论不存在");
        }
        QueryWrapper<CommentReplyDto> queryWrapper = Wrappers.<CommentReplyDto>query().select("r.id as replyId","r.comment_id",
                "r.reply","u1.nick_name as replyTo","u.nick_name as createUser","r.create_time","r.update_time",
                "case when u.user_name = #{currentUser} then 1 else 0 end as isDelButton",
                "case when u.user_name = #{currentUser} then 1 else 0 end as isUpdateButton");
        queryWrapper.eq("r.comment_id", commentId);
        queryWrapper.eq("r.is_del", 0);
        IPage<CommentReplyDto> commentReplyDtoPage = baseMapper.selectByCommentId(page,currentUser,queryWrapper);
        return commentReplyDtoPage;
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void publishNewCommentReply(CommentReplyReq req, String currentUser) {
        //1.判断评论是否存在
        PostCommentDto postCommentDtoDto = postCommentService.getPostCommentInfo(req.getCommentId());
        if(postCommentDtoDto == null) {
            throw new CustomException("回复的评论不存在");
        }
        Integer currentUserId = 0;
        if(!StringUtils.isEmpty(currentUser)) {
            currentUserId = postInfoMapper.selectUserIdByName(currentUser);
        }
        //2.新增回复
        CommentReply commentReply = new CommentReply();
        commentReply.setCommentId(req.getCommentId());
        commentReply.setReply(req.getReply());
        commentReply.setReplyTo(Integer.valueOf(postCommentDtoDto.getCreateUser()));
        commentReply.setCreateUser(currentUserId);
        baseMapper.insert(commentReply);
        //3.将评论中评论数加一
        Integer replyNum = postCommentDtoDto.getReplyNum();
        PostComment postComment = new PostComment();
        postComment.setId(req.getCommentId());
        postComment.setReplyNum(replyNum+1);
        postCommentMapper.updateById(postComment);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void deleteCommentReply(Integer replyId, String currentUser) {
        CommentReplyDto commentReplyDto =  getCommentReplyInfo(replyId);
        if(commentReplyDto == null) {
            throw new CustomException("回复不存在");
        }
        
        Integer commentId = getCommentIdByReplyId(replyId);
        //1.判断评论是否存在
        PostCommentDto postCommentDto = postCommentService.getPostCommentInfo(commentId);
        if(postCommentDto == null) {
            throw new CustomException("当前评论不存在");
        }
        // 2.判断当前的用户是否创建者
        String createUserName = baseMapper.selectCreateUserName(replyId);
        if (!StringUtils.equals(currentUser, createUserName)) {
            throw new CustomException("当前用户无权删除此回复！");
        }
        //3.删除评论
        baseMapper.setReplyToDel(replyId);
        
        //4.将评论中回复数减一
        Integer replyNum = postCommentDto.getReplyNum();
        PostComment postComment = new PostComment();
        postComment.setId(commentId);
        postComment.setReplyNum(replyNum>0?replyNum-1:0);
        postCommentMapper.updateById(postComment);
    }
    
    @Override
    public Integer updateCommentReply(CommentReplyReq req, String currentUser) {
        CommentReplyDto commentReplyDto =  getCommentReplyInfo(req.getReplyId());
        if(commentReplyDto == null) {
            throw new CustomException("回复不存在");
        }
        
        //1.判断评论是否存在
        PostCommentDto postCommentDto = postCommentService.getPostCommentInfo(req.getCommentId());
        if(postCommentDto == null) {
            throw new CustomException("当前评论不存在");
        }
        //2.首先判断当前的用户是否创建者
        String createUserName = baseMapper.selectCreateUserName(req.getReplyId());
        if(!StringUtils.equals(currentUser,createUserName)) {
            throw new CustomException("当前用户无权修改此回复！");
        }
        CommentReply commentReply = new CommentReply();
        commentReply.setId(req.getReplyId());
        commentReply.setCommentId(req.getCommentId());
        commentReply.setReply(req.getReply());
        return baseMapper.updateById(commentReply);
    }

    /**
     * 根据回复id获取评论id
     * @param replyId 回复id
     * @return
     */
    private Integer getCommentIdByReplyId(Integer replyId) {
        return baseMapper.selectCommentIdByReplyId(replyId);
    }
    
    @Override
    public CommentReplyDto getCommentReplyInfo(Integer replyId) {
        return baseMapper.selectByReplyId(replyId);
    }
}
