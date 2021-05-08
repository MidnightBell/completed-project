package per.zs.forum.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import per.zs.forum.db.entity.CommentReply;
import per.zs.forum.db.mapper.CommentReplyMapper;
import per.zs.forum.service.CommentReplyService;

/** 
* Create time 2021年5月7日 下午3:52:11 
* @author sheng.zhong 
* @Description  
*/
@Service("commentReplyServiceImpl")
public class CommentReplyServiceImpl extends ServiceImpl<CommentReplyMapper, CommentReply> implements CommentReplyService{

}
