package per.zs.forum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import per.zs.forum.beans.dto.PostInfoDto;
import per.zs.forum.beans.req.PostInfoReq;
import per.zs.forum.beans.req.PostPageReq;
import per.zs.forum.db.entity.PostInfo;

/** 
* Create time 2021年5月7日 下午3:34:34 
* @author sheng.zhong 
* @Description  
*/
public interface PostInfoService extends IService<PostInfo>{

    /**
     * 分页获取主题帖
     * @param req 查询条件
     * @return
     */
    IPage<PostInfoDto> getPostList(PostPageReq req);

    /**
     * 新增主题帖
     * @param req 主题帖信息
     * @param currentUser 当前登录用户名
     * @return
     */
    Integer publishNewPost(PostInfoReq req,String currentUser);

    /**
     * 根据主题帖id获取主题帖详情
     * @param postId 主题帖id
     * @param currentUser 当前登录用户名
     * @return
     */
    PostInfoDto getPostInfo(Integer postId,String currentUser);

    /**
     * 根据id删除主题帖
     * @param postId 主题帖id
     * @param currentUser 当前登录用户名
     * @return
     */
    void deletePost(Integer postId,String currentUser);

    /**
     * 修改主题帖
     * @param req 主题帖信息
     * @param currentUser 当前登录用户
     * @return
     */
    Integer updatePost(PostInfoReq req, String currentUser);

}
