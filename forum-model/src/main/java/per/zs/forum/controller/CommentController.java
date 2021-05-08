package per.zs.forum.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import per.zs.common.beans.enums.HttpCodeEnum;
import per.zs.common.beans.response.ResultBaseRes;
import per.zs.common.beans.response.ResultRes;
import per.zs.common.exception.CustomException;
import per.zs.common.utils.CommonUtil;
import per.zs.forum.beans.dto.PostCommentDto;
import per.zs.forum.beans.req.PostCommentReq;
import per.zs.forum.service.PostCommentService;

/** 
* Create time 2021年5月8日 上午9:32:32 
* @author sheng.zhong 
* @Description  
*/
@Api(tags = "评论相关接口")
@Validated
@RestController
@RequestMapping(value = "comment")
public class CommentController {
    
    @Autowired
    private PostCommentService postCommentService;
    
    @ApiOperation(value = "分页获取主题帖评论")
    @GetMapping("getPostList/{pageNum}/{pageSize}")
    public IPage<PostCommentDto> getPostList(@RequestParam("postId") Integer postId,@PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize) {
        String currentUser = CommonUtil.getCurrentUserName();
        if(StringUtils.isEmpty(currentUser)) {
            throw new CustomException("获取当前登录用户失败");
        }
        IPage<PostCommentDto> page = new Page<>(pageNum, pageSize);
        return postCommentService.getPostCommentList(postId,currentUser,page);
    }
    
    @ApiOperation(value = "新增评论")
    @PostMapping("publishNewPost")
    public ResultBaseRes publishNewPost(@Validated @RequestBody PostCommentReq req) {
        String currentUser = CommonUtil.getCurrentUserName();
        if(StringUtils.isEmpty(currentUser)) {
            return ResultBaseRes.builder(HttpCodeEnum.FAIL,"获取当前登录用户失败");
        }
        postCommentService.publishNewPostComment(req,currentUser);
        return ResultBaseRes.builder(HttpCodeEnum.TRUE,"评论成功");
    }
    
    @ApiOperation(value = "根据评论id删除评论")
    @DeleteMapping("deletePostComment/{commentId}")
    public ResultBaseRes deletePostComment(@PathVariable("commentId") Integer commentId) {
        String currentUser = CommonUtil.getCurrentUserName();
        if(StringUtils.isEmpty(currentUser)) {
            return ResultBaseRes.builder(HttpCodeEnum.FAIL,"获取当前登录用户失败");
        }
        postCommentService.deletePostComment(commentId,currentUser);
        return ResultBaseRes.builder(HttpCodeEnum.TRUE);
    }
    
    @ApiOperation(value = "修改评论")
    @PostMapping("updatePostComment")
    public ResultRes<Integer> updatePostComment(@Validated @RequestBody PostCommentReq req) {
        String currentUser = CommonUtil.getCurrentUserName();
        if(StringUtils.isEmpty(currentUser)) {
            return new ResultRes<>(HttpCodeEnum.FAIL,null,"获取当前登录用户失败");
        }
        return new ResultRes<>(HttpCodeEnum.TRUE,postCommentService.updatePostComment(req,currentUser),"评论修改成功");
    }
}
