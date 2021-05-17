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
import per.zs.forum.beans.dto.CommentReplyDto;
import per.zs.forum.beans.req.CommentReplyReq;
import per.zs.forum.beans.req.PostCommentReq;
import per.zs.forum.service.CommentReplyService;

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
    @Autowired
    private CommentReplyService commentReplyService;
    
    @ApiOperation(value = "分页获取评论回复")
    @GetMapping("getCommentReplyList/{pageNum}/{pageSize}")
    public IPage<CommentReplyDto> getCommentReplyList(@RequestParam("commentId") Integer commentId,@PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize) {
        String currentUser = CommonUtil.getCurrentUserName();
        if(StringUtils.isEmpty(currentUser)) {
            throw new CustomException("获取当前登录用户失败");
        }
        IPage<CommentReplyDto> page = new Page<>(pageNum, pageSize);
        return commentReplyService.getCommentReplyList(commentId,currentUser,page);
    }
    
    @ApiOperation(value = "新增回复")
    @PostMapping("publishNewCommentReply")
    public ResultBaseRes publishNewCommentReply(@Validated @RequestBody CommentReplyReq req) {
        String currentUser = CommonUtil.getCurrentUserName();
        if(StringUtils.isEmpty(currentUser)) {
            return ResultBaseRes.builder(HttpCodeEnum.FAIL,"获取当前登录用户失败");
        }
        commentReplyService.publishNewCommentReply(req,currentUser);
        return ResultBaseRes.builder(HttpCodeEnum.TRUE,"回复成功");
    }
    
    @ApiOperation(value = "根据回复id删除回复")
    @DeleteMapping("deleteCommentReply/{replyId}")
    public ResultBaseRes deleteCommentReply(@PathVariable("replyId") Integer replyId) {
        String currentUser = CommonUtil.getCurrentUserName();
        if(StringUtils.isEmpty(currentUser)) {
            return ResultBaseRes.builder(HttpCodeEnum.FAIL,"获取当前登录用户失败");
        }
        commentReplyService.deleteCommentReply(replyId,currentUser);
        return ResultBaseRes.builder(HttpCodeEnum.TRUE);
    }
    
    @ApiOperation(value = "修改回复")
    @PostMapping("updateCommentReply")
    public ResultRes<Integer> updateCommentReply(@Validated @RequestBody CommentReplyReq req) {
        String currentUser = CommonUtil.getCurrentUserName();
        if(StringUtils.isEmpty(currentUser)) {
            return new ResultRes<>(HttpCodeEnum.FAIL,null,"获取当前登录用户失败");
        }
        return new ResultRes<>(HttpCodeEnum.TRUE,commentReplyService.updateCommentReply(req,currentUser),"回复修改成功");
    }
}
