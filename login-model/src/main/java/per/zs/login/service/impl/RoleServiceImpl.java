package per.zs.login.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import per.zs.login.beans.dto.RoleInfoDto;
import per.zs.login.beans.req.RoleInfoReq;
import per.zs.login.db.entity.Role;
import per.zs.login.db.mapper.RoleMapper;
import per.zs.login.db.mapper.UserMapper;
import per.zs.login.exception.CustomException;
import per.zs.login.service.RoleService;

/** 
* Create time 2021年4月29日 上午11:12:38 
* @author sheng.zhong 
* @Description  
*/
@Service("roleServiceImpl")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserMapper userMapper;
    
    @Override
    public Integer insertRoleInfo(RoleInfoReq req, String currentUser) {
        Role role = null;
        String roleName = req.getRoleName();
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name", roleName);
        role = baseMapper.selectOne(wrapper);
        
        if(role != null) {
            throw new CustomException("新增失败：角色名<"+role.getRoleName()+">已存在！");
        }else {
            role = new Role();
        }
        
        Integer currentUserId = userMapper.selectIdByName(currentUser);
        
        role.setRoleName(roleName);
        role.setCreateUser(currentUserId);
        
        return baseMapper.insert(role);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void userRoleSet(Integer userId, String roles) {
        
        //1.先删除用户的角色信息
        baseMapper.deletefromUserRole(userId);
        
        //2.再新增
        if(!StringUtils.isEmpty(roles)) {
            String[] roleArr = roles.split(",");
            for(String role:roleArr) {
                Role roleInfo = baseMapper.selectById(role);
                if(roleInfo == null) {
                    throw new CustomException("角色id为<"+role+">对应的角色不存在");
                }
                baseMapper.insertRolseToUserRole(userId,role);
            }
        }
    }

    @Override
    public List<RoleInfoDto> getRoleInfoByUserId(Integer userId) {
        
        QueryWrapper<RoleInfoDto> wrapper = Wrappers.<RoleInfoDto>query().select("r.id as roleId","r.role_name");
        wrapper.eq("ur.user_id", userId);
        return baseMapper.selectRoleInfoDto(wrapper);
    }

    @Override
    public List<RoleInfoDto> getAllRole() {
        QueryWrapper<RoleInfoDto> queryWrapper = Wrappers.<RoleInfoDto>query().select("id as roleId","role_name");
        queryWrapper.eq("is_del", 0);
        return baseMapper.selectAllRole(queryWrapper);
    }
    
}
