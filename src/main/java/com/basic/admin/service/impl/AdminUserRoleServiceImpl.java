package com.basic.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.basic.admin.mapper.AdminUserRoleMapper;
import com.basic.admin.model.entity.AdminUserRole;
import com.basic.admin.service.IAdminUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-02-18
 */
@Service("adminUserRoleService")
public class AdminUserRoleServiceImpl extends ServiceImpl<AdminUserRoleMapper, AdminUserRole> implements IAdminUserRoleService {

}
