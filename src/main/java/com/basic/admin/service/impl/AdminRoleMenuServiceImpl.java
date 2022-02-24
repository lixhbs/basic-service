package com.basic.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.basic.admin.mapper.AdminRoleMenuMapper;
import com.basic.admin.model.entity.AdminRoleMenu;
import com.basic.admin.service.IAdminRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色菜单关联表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-02-17
 */
@Service("adminRoleMenuService")
public class AdminRoleMenuServiceImpl extends ServiceImpl<AdminRoleMenuMapper, AdminRoleMenu> implements IAdminRoleMenuService {

}
