package per.zs.login.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import per.zs.login.beans.dto.PermissionDto;
import per.zs.login.db.entity.Permission;
import per.zs.login.db.mapper.PermissionMapper;
import per.zs.login.exception.CustomException;
import per.zs.login.service.PermissionService;

/** 
* Create time 2021年4月30日 上午10:28:12 
* @author sheng.zhong 
* @Description  
*/
@Service("permissionServiceImpl")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService{

    @Override
    public List<PermissionDto> getUserPermission(Integer userId) {
        List<PermissionDto> permissionDtoList = baseMapper.selectPermissionByUserId(userId);
        if(permissionDtoList == null || permissionDtoList.isEmpty()) {
            return new ArrayList<>();
        }
        return permissionDtoList;
    }

    @Override
    public List<PermissionDto> getRolePermission(Integer roleId) {
        List<PermissionDto> allPermissionDtoList = baseMapper.selectAllPermission();
        
        List<PermissionDto> permissionDtoList = baseMapper.selectPermissionByRoleId(roleId);
        if(permissionDtoList == null || permissionDtoList.isEmpty()) {
            return allPermissionDtoList;
        }else {
            
            Map<Integer, PermissionDto> map = allPermissionDtoList.stream().map(perDto ->{
                if(permissionDtoList.contains(perDto)) {
                    perDto.setIsSelected(1);
                }
                return perDto;
            }).collect(Collectors.toMap(dto -> dto.getPerId(), dto -> dto));
            
            //将数据加入层级,并仅需要第一级
            List<PermissionDto> treeList = map.values().stream().peek(data -> {
                Integer parent = data.getParentId();
                if (parent != 0) {
                    map.get(parent).getChildren().add(data);
                }
            }).filter(data -> data.getParentId() == 0).collect(Collectors.toList());
            
            return treeList;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void rolePermissionSet(Integer roleId, String permissions) {
        //1.先删除角色的权限信息
        baseMapper.deletefromRolePermission(roleId);
        
        //2.再新增
        if(!StringUtils.isEmpty(permissions)) {
            String[] permissionArr = permissions.split(",");
            for(String permission:permissionArr) {
                Permission permissionInfo = baseMapper.selectById(permission);
                if(permissionInfo == null) {
                    throw new CustomException("权限id为<"+permission+">对应的权限不存在");
                }
                baseMapper.insertPermissionsToRolePermission(roleId,permission);
            }
        }
        
    }

}
