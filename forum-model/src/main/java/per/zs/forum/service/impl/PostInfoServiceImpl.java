package per.zs.forum.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import per.zs.common.beans.constants.Constant;
import per.zs.common.exception.CustomException;
import per.zs.common.exception.ParamException;
import per.zs.forum.beans.dto.PostInfoDto;
import per.zs.forum.beans.req.PostInfoReq;
import per.zs.forum.beans.req.PostPageReq;
import per.zs.forum.db.entity.PostInfo;
import per.zs.forum.db.mapper.PostCommentMapper;
import per.zs.forum.db.mapper.PostInfoMapper;
import per.zs.forum.service.PostInfoService;

/** 
* Create time 2021年5月7日 下午3:35:31 
* @author sheng.zhong 
* @Description  
*/
@Service("postThemeServiceImpl")
public class PostInfoServiceImpl extends ServiceImpl<PostInfoMapper, PostInfo> implements PostInfoService {

    @Autowired
    private PostCommentMapper postCommentMapper;
    
    @Override
    public IPage<PostInfoDto> getPostList(PostPageReq req) {
        IPage<PostInfoDto> page = new Page<>(req.getPageNum(), req.getPageSize());
        
        QueryWrapper<PostInfoDto> queryWrapper = Wrappers.<PostInfoDto>query().select("p.id as postId","p.post_title",
                "p.post_content","p.reply_num","u.nick_name as createUser","p.create_time","p.update_time");
        if(!StringUtils.isEmpty(req.getParam())) {
            queryWrapper.and(wrapper -> wrapper.like("concat(p.post_title,p.post_content)",req.getParam())
            .or().like("concat(u.nick_name)",req.getParam()));
        }
        if(!StringUtils.isEmpty(req.getStartTime())) {
            if(req.getStartTime().matches(Constant.REGEX_STRING_DATE)) {
                queryWrapper.ge("p.create_time", req.getStartTime());
            }else {
                throw new ParamException("起始时间值格式错误！正确格式为yyyy-MM-dd");
            }
        }
        if(!StringUtils.isEmpty(req.getEndTime())) {
            if(req.getEndTime().matches(Constant.REGEX_STRING_DATE)) {
                queryWrapper.le("p.create_time", req.getStartTime());
            }else {
                throw new ParamException("结束时间值格式错误！正确格式为yyyy-MM-dd");
            }
        }
        queryWrapper.eq("p.is_del", 0);
        IPage<PostInfoDto> postInfoDtoPage = baseMapper.selectByParam(page, queryWrapper);
        return postInfoDtoPage;
    }

    @Override
    public Integer publishNewPost(PostInfoReq req, String currentUser) {
        Integer currentUserId = 0;
        if(!StringUtils.isEmpty(currentUser)) {
            currentUserId = baseMapper.selectUserIdByName(currentUser);
        }
        PostInfo postInfo = new PostInfo();
        postInfo.setPostTitle(req.getPostTitle());
        postInfo.setPostContent(req.getPostContent());
        postInfo.setCreateUser(currentUserId);
        return baseMapper.insert(postInfo);
    }

    @Override
    public PostInfoDto getPostInfo(Integer postId,String currentUser) {
        return baseMapper.selectByPostId(postId,currentUser);
    }

    @Override
    public void deletePost(Integer postId,String currentUser) {
        //1.首先判断当前的用户是否创建者
        String createUserName = baseMapper.selectCreateUserName(postId);
        if(!StringUtils.equals(currentUser,createUserName)) {
            throw new CustomException("当前用户无权删除此帖！");
        }
        //2.删除主题帖
        baseMapper.setPostToDel(postId);
        //3.删除主题帖下的评论
        postCommentMapper.setPostCommentToDel(postId);
    }

    @Override
    public Integer updatePost(PostInfoReq req, String currentUser) {
        //1.首先判断当前的用户是否创建者
        String createUserName = baseMapper.selectCreateUserName(req.getPostId());
        if(!StringUtils.equals(currentUser,createUserName)) {
            throw new CustomException("当前用户无权修改此帖！");
        }
        PostInfo postInfo = new PostInfo();
        postInfo.setId(req.getPostId());
        postInfo.setPostTitle(req.getPostTitle());
        postInfo.setPostContent(req.getPostContent());
        return baseMapper.updateById(postInfo);
    }

}
