package com.basic.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.basic.admin.common.result.JsonData;
import com.basic.admin.common.result.PageVO;
import com.basic.admin.model.entity.AdminMenu;
import com.basic.admin.model.entity.AdminRole;
import com.basic.admin.model.param.AdminMenuParam;
import com.basic.admin.model.result.MenuResult;

import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-01-10
 */
public interface IAdminMenuService extends IService<AdminMenu> {

    PageVO<AdminMenu> listPage(AdminMenuParam adminMenuParam);

    List<MenuResult> listMenu();

    AdminMenu saveMenu(AdminMenu adminMenu);

    boolean delMenuById(String id);

    AdminMenu editMenu(AdminMenu adminMenu);

    HashSet<AdminMenu> listMenuByRole(List<AdminRole> roles);

    JsonData listUserMenu();
}
