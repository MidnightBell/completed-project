package per.zs.forum.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/** 
* Create time 2021年5月8日 上午11:16:21 
* @author sheng.zhong 
* @Description  
*/
@Api(tags = "评论回复相关接口")
@Validated
@RestController
@RequestMapping(value = "reply")
public class CommentReplyController {
//    @Autowired
//    private CommentReplyService commentReplyService;
    
//    @ApiOperation(value = "分页评论回复")
//    @GetMapping("getPostList/{pageNum}/{pageSize}")
//    public IPage<CommentReplyDto> getPostList(@RequestParam("postId") Integer commentId,@PathVariable("pageNum") Integer pageNum,
//            @PathVariable("pageSize") Integer pageSize) {
//        String currentUser = CommonUtil.getCurrentUserName();
//        if(StringUtils.isEmpty(currentUser)) {
//            throw new CustomException("获取当前登录用户失败");
//        }
//        IPage<CommentReplyDto> page = new Page<>(pageNum, pageSize);
//        return commentReplyService.getCommentReplyList(commentId,currentUser,page);
//    }
//    
//    @ApiOperation(value = "新增回复")
//    @PostMapping("publishNewPost")
//    public ResultBaseRes publishNewPost(@Validated @RequestBody PostCommentReq req) {
//        String currentUser = CommonUtil.getCurrentUserName();
//        if(StringUtils.isEmpty(currentUser)) {
//            return ResultBaseRes.builder(HttpCodeEnum.FAIL,"获取当前登录用户失败");
//        }
//        commentReplyService.publishNewCommentReply(req,currentUser);
//        return ResultBaseRes.builder(HttpCodeEnum.TRUE,"评论成功");
//    }
//    
//    @ApiOperation(value = "根据回复id删除回复")
//    @DeleteMapping("deletePostComment/{commentId}")
//    public ResultBaseRes deletePostComment(@PathVariable("commentId") Integer replyId) {
//        String currentUser = CommonUtil.getCurrentUserName();
//        if(StringUtils.isEmpty(currentUser)) {
//            return ResultBaseRes.builder(HttpCodeEnum.FAIL,"获取当前登录用户失败");
//        }
//        commentReplyService.deleteCommentReply(commentId,currentUser);
//        return ResultBaseRes.builder(HttpCodeEnum.TRUE);
//    }
//    
//    @ApiOperation(value = "修改评论")
//    @PostMapping("updatePostComment")
//    public ResultRes<Integer> updatePostComment(@Validated @RequestBody PostCommentReq req) {
//        String currentUser = CommonUtil.getCurrentUserName();
//        if(StringUtils.isEmpty(currentUser)) {
//            return new ResultRes<>(HttpCodeEnum.FAIL,null,"获取当前登录用户失败");
//        }
//        return new ResultRes<>(HttpCodeEnum.TRUE,commentReplyService.updateCommentReply(req,currentUser),"评论修改成功");
//    }
}
