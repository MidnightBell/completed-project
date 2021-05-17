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
import per.zs.forum.beans.dto.PostCommentDto;
import per.zs.forum.beans.dto.PostInfoDto;
import per.zs.forum.beans.req.PostCommentReq;
import per.zs.forum.db.entity.PostComment;
import per.zs.forum.db.entity.PostInfo;
import per.zs.forum.db.mapper.PostCommentMapper;
import per.zs.forum.db.mapper.PostInfoMapper;
import per.zs.forum.service.PostCommentService;
import per.zs.forum.service.PostInfoService;

/** 
* Create time 2021年5月7日 下午3:50:42 
* @author sheng.zhong 
* @Description  
*/
@Service("postCommentServiceImpl")
public class PostCommentServiceImpl extends ServiceImpl<PostCommentMapper, PostComment> implements PostCommentService {
    @Autowired
    private PostInfoService postInfoService;
    
    @Autowired
    private PostInfoMapper postInfoMapper;
    
    @Override
    public IPage<PostCommentDto> getPostCommentList(Integer postId, String currentUser, IPage<PostCommentDto> page) {
        //1.判断主题帖是否存在
        PostInfoDto postInfoDto = postInfoService.getPostInfo(postId, currentUser);
        if(postInfoDto == null) {
            throw new CustomException("当前主题帖不存在");
        }
        QueryWrapper<PostCommentDto> queryWrapper = Wrappers.<PostCommentDto>query().select("c.id as commentId","c.post_id",
                "c.post_comment","c.reply_num","u.nick_name as createUser","c.create_time","c.update_time",
                "case when u.user_name = #{currentUser} then 1 else 0 end as isDelButton",
                "case when u.user_name = #{currentUser} then 1 else 0 end as isUpdateButton");
        queryWrapper.eq("c.post_id", postId);
        queryWrapper.eq("c.is_del", 0);
        IPage<PostCommentDto> postCommentDtoPage = baseMapper.selectByPostId(page,currentUser,queryWrapper);
        return postCommentDtoPage;
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void publishNewPostComment(PostCommentReq req, String currentUser) {
        //1.判断主题帖是否存在
        PostInfoDto postInfoDto = postInfoService.getPostInfo(req.getPostId(), currentUser);
        if(postInfoDto == null) {
            throw new CustomException("当前主题帖不存在");
        }
        Integer currentUserId = 0;
        if(!StringUtils.isEmpty(currentUser)) {
            currentUserId = postInfoMapper.selectUserIdByName(currentUser);
        }
        //2.新增评论
        PostComment postComment = new PostComment();
        postComment.setPostId(req.getPostId());
        postComment.setPostComment(req.getPostComment());
        postComment.setCreateUser(currentUserId);
        baseMapper.insert(postComment);
        //3.将主题帖中评论数加一
        Integer replyNum = postInfoDto.getReplyNum();
        PostInfo postInfo = new PostInfo();
        postInfo.setId(req.getPostId());
        postInfo.setReplyNum(replyNum+1);
        postInfoMapper.updateById(postInfo);
        
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void deletePostComment(Integer commentId, String currentUser) {
        PostCommentDto postCommentDto = getPostCommentInfo(commentId);
        if(postCommentDto == null) {
            throw new CustomException("评论不存在");
        }
        
        Integer postId = getPostIdByCommentId(commentId);
        //1.判断主题帖是否存在
        PostInfoDto postInfoDto = postInfoService.getPostInfo(postId, currentUser);
        if(postInfoDto == null) {
            throw new CustomException("当前主题帖不存在");
        }
        // 2.判断当前的用户是否创建者
        String createUserName = baseMapper.selectCreateUserName(commentId);
        if (!StringUtils.equals(currentUser, createUserName)) {
            throw new CustomException("当前用户无权删除此评论！");
        }
        //3.删除评论
        baseMapper.setCommentToDel(commentId);
        
        //4.将主题帖中评论数减一
        Integer replyNum = postInfoDto.getReplyNum();
        PostInfo postInfo = new PostInfo();
        postInfo.setId(postId);
        postInfo.setReplyNum(replyNum>0?replyNum-1:0);
        postInfoMapper.updateById(postInfo);
    }

    @Override
    public Integer updatePostComment(PostCommentReq req, String currentUser) {
        PostCommentDto postCommentDto = getPostCommentInfo(req.getCommentId());
        if(postCommentDto == null) {
            throw new CustomException("评论不存在");
        }
        
        //1.判断主题帖是否存在
        PostInfoDto postInfoDto = postInfoService.getPostInfo(req.getPostId(), currentUser);
        if(postInfoDto == null) {
            throw new CustomException("当前主题帖不存在");
        }
        //2.首先判断当前的用户是否创建者
        String createUserName = baseMapper.selectCreateUserName(req.getCommentId());
        if(!StringUtils.equals(currentUser,createUserName)) {
            throw new CustomException("当前用户无权修改此评论！");
        }
        PostComment postComment = new PostComment();
        postComment.setId(req.getCommentId());
        postComment.setPostId(req.getPostId());
        postComment.setPostComment(req.getPostComment());
        return baseMapper.updateById(postComment);
    }

    @Override
    public PostCommentDto getPostCommentInfo(Integer commentId) {
        return baseMapper.selectByPostCommentId(commentId);
    }
    
    /**
     * 根据评论id获取主题帖id
     * @param commentId 评论id
     * @return
     */
    private Integer getPostIdByCommentId(Integer commentId) {
        return baseMapper.selectPostIdByCommentId(commentId);
    }
}
