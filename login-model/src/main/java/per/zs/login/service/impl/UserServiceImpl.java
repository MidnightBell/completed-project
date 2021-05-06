package per.zs.login.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import per.zs.login.beans.dto.UserInfoDto;
import per.zs.login.beans.req.UserInfoReq;
import per.zs.login.db.entity.User;
import per.zs.login.db.mapper.UserMapper;
import per.zs.login.exception.CustomException;
import per.zs.login.service.UserService;

/** 
* Create time 2021年4月28日 上午9:44:29 
* @author sheng.zhong 
* @Description  
*/
@Service("userServiceImpl")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Override
    public User getUserByName(String userName) {
        QueryWrapper<User> queryWrapper = Wrappers.<User>query();
        queryWrapper.eq("user_name", userName);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer insertUserInfo(UserInfoReq req, String currentUser){
        User user = null;
        String userName = req.getUserName();
        user = getUserByName(userName);
        
        if(user != null) {
            throw new CustomException("新增失败：用户名<"+userName+">已存在！");
        }else {
            user = new User();
        }
        
        Integer currentUserId = 0;
        if(!StringUtils.isEmpty(currentUser)) {
            currentUserId = baseMapper.selectIdByName(currentUser);
        }
        
        user.setUserName(req.getUserName());
        user.setPassword(req.getPassword());
        user.setPhone(req.getPhone());
        user.setNickName(req.getNickName());
        user.setSex(req.getSex());
        user.setCreateUser(currentUserId);
        
        return baseMapper.insert(user);
    }

    @Override
    public IPage<UserInfoDto> getUserInfoList(String param,IPage<UserInfoDto> page) {
        QueryWrapper<UserInfoDto> queryWrapper = Wrappers.<UserInfoDto>query().select("id as userId","user_name",
                "phone","nick_name","sex");
//        if(!StringUtils.isEmpty(param)) {
//            queryWrapper.and(wrapper -> wrapper.like("concat(user_name,password,phone,nick_name,sex)",param)
//            .or().like("concat(u1.user_name)",ecnListDto.getKeyWord()));
//        }
        queryWrapper.like(!StringUtils.isEmpty(param),"concat(user_name,password,phone,nick_name,sex)",param);
        IPage<UserInfoDto> userInfoDtoPage = baseMapper.selectByParam(page, queryWrapper);
        
        return userInfoDtoPage;
    }

    @Override
    public Integer updatePassword(String userName, String password) {
        return baseMapper.updatePassword(userName,password);
    }
}
