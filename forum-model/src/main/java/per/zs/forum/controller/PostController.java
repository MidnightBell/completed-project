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
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import per.zs.common.beans.enums.HttpCodeEnum;
import per.zs.common.beans.response.ResultBaseRes;
import per.zs.common.beans.response.ResultRes;
import per.zs.common.utils.CommonUtil;
import per.zs.forum.beans.dto.PostInfoDto;
import per.zs.forum.beans.req.PostInfoReq;
import per.zs.forum.beans.req.PostPageReq;
import per.zs.forum.service.PostInfoService;

/** 
* Create time 2021年5月7日 下午1:17:56 
* @author sheng.zhong 
* @Description  
*/
@Api(tags = "主题帖相关接口")
@Validated
@RestController
@RequestMapping(value = "post")
public class PostController {
    
    @Autowired
    private PostInfoService postInfoService;
    
    @ApiOperation(value = "分页获取主题帖")
    @PostMapping("getPostList")
    public IPage<PostInfoDto> getPostList(@Validated @RequestBody PostPageReq req) {
        
        return postInfoService.getPostList(req);
    }
    
    @ApiOperation(value = "根据主题帖id获取正文详情")
    @GetMapping("getPostInfo/{postId}")
    public ResultRes<PostInfoDto> getPostInfo(@PathVariable Integer postId) {
        String currentUser = CommonUtil.getCurrentUserName();
        if(StringUtils.isEmpty(currentUser)) {
            return new ResultRes<>(HttpCodeEnum.FAIL,null,"获取当前登录用户失败");
        }
        return new ResultRes<>(HttpCodeEnum.TRUE,postInfoService.getPostInfo(postId,currentUser));
    }
    
    @ApiOperation(value = "新增主题帖")
    @PostMapping("publishNewPost")
    public ResultRes<Integer> publishNewPost(@Validated @RequestBody PostInfoReq req) {
        String currentUser = CommonUtil.getCurrentUserName();
        if(StringUtils.isEmpty(currentUser)) {
            return new ResultRes<>(HttpCodeEnum.FAIL,null,"获取当前登录用户失败");
        }
        return new ResultRes<>(HttpCodeEnum.TRUE,postInfoService.publishNewPost(req,currentUser),"新增主题帖成功");
    }
    
    @ApiOperation(value = "根据主题帖id删除主题帖")
    @DeleteMapping("deletePost/{postId}")
    public ResultBaseRes deletePost(@PathVariable Integer postId) {
        String currentUser = CommonUtil.getCurrentUserName();
        if(StringUtils.isEmpty(currentUser)) {
            return ResultBaseRes.builder(HttpCodeEnum.FAIL,"获取当前登录用户失败");
        }
        postInfoService.deletePost(postId,currentUser);
        return ResultBaseRes.builder(HttpCodeEnum.TRUE);
    }
    
    @ApiOperation(value = "修改主题帖")
    @PostMapping("updatePost")
    public ResultRes<Integer> updatePost(@Validated @RequestBody PostInfoReq req) {
        String currentUser = CommonUtil.getCurrentUserName();
        if(StringUtils.isEmpty(currentUser)) {
            return new ResultRes<>(HttpCodeEnum.FAIL,null,"获取当前登录用户失败");
        }
        return new ResultRes<>(HttpCodeEnum.TRUE,postInfoService.updatePost(req,currentUser),"修改主题帖成功");
    }
}
