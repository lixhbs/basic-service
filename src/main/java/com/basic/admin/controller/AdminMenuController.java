package com.basic.admin.controller;


import com.basic.admin.common.result.JsonData;
import com.basic.admin.manager.AuthManager;
import com.basic.admin.model.entity.AdminMenu;
import com.basic.admin.model.entity.AdminRole;
import com.basic.admin.service.IAdminMenuService;
import com.basic.admin.service.IAdminRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-01-10
 */
@RestController
@Validated
@RequestMapping("/api/admin/menu")
public class AdminMenuController
{
    @Resource
    private IAdminMenuService adminMenuService;

    @GetMapping("/getMenuList")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData getMenuList(AdminMenu adminMenu)
    {
        return JsonData.buildSuccess(adminMenuService.listMenu());
    }

    @GetMapping("/listMenuByRole/{roleId}")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData listMenuByRole(@PathVariable("roleId") String roleId)
    {
        ArrayList<AdminRole> adminRoles = new ArrayList<>();
        AdminRole adminRole = new AdminRole();
        adminRole.setId(roleId);
        adminRoles.add(adminRole);
        return JsonData.buildSuccess(adminMenuService.listMenuByRole(adminRoles));
    }

    @GetMapping("/listUserMenu")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData listUserMenu()
    {
        return adminMenuService.listUserMenu();
    }

    @PostMapping("/SaveMenu")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData SaveMenu(@RequestBody AdminMenu adminMenu)
    {
        return JsonData.buildSuccess(adminMenuService.saveMenu(adminMenu));
    }

    @PostMapping("/editMenu")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData editMenu(@RequestBody AdminMenu adminMenu)
    {
        return JsonData.buildSuccess(adminMenuService.editMenu(adminMenu));
    }

    @GetMapping("/delMenuById/{menuId}")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData delMenuById(@PathVariable("menuId") String id)
    {
        return JsonData.buildSuccess(adminMenuService.delMenuById(id));
    }
}

