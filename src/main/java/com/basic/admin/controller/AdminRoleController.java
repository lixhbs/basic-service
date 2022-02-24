package com.basic.admin.controller;


import com.basic.admin.common.result.JsonData;
import com.basic.admin.common.result.PageVO;
import com.basic.admin.model.bo.AdminRoleEditBO;
import com.basic.admin.model.bo.AdminRoleEditStatusBO;
import com.basic.admin.model.bo.AdminRoleInsertBO;
import com.basic.admin.model.entity.AdminRole;
import com.basic.admin.model.param.AdminRoleParam;
import com.basic.admin.service.IAdminRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-01-17
 */
@RestController
@RequestMapping("/api/admin/role")
public class AdminRoleController
{
    @Resource
    IAdminRoleService adminRoleService;

    @PostMapping("/insertRole")
    @ResponseBody
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData insertRole(@RequestBody AdminRole adminRole)
    {
        return adminRoleService.insertRole(adminRole);
    }

    @PostMapping("/insertRoleAndMenu")
    @ResponseBody
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData insertRoleAndMenu(@RequestBody AdminRoleInsertBO adminRoleInsertBO)
    {
        return adminRoleService.insertRoleAndMenu(adminRoleInsertBO);
    }

    @PostMapping("/editRoleAndMenu")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData editRoleAndMenu(@RequestBody AdminRoleEditBO adminRoleEditBO)
    {
        return JsonData.buildSuccess(adminRoleService.editRoleAndMenu(adminRoleEditBO));
    }

    @GetMapping("/deleteRoleAndMenu/{id}")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData deleteRoleAndMenu(@PathVariable("id") String id)
    {
        return adminRoleService.deleteRoleAndMenu(id);
    }

    @PostMapping("/editRoleStatus")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData editRoleStatus(@RequestBody AdminRoleEditStatusBO adminRoleEditStatusBO)
    {
        return JsonData.buildSuccess(adminRoleService.editRoleStatus(adminRoleEditStatusBO));
    }

    @GetMapping("/getRoleListByPage")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData getRoleListByPage(AdminRoleParam adminRoleParam)
    {
        PageVO<AdminRole> pageVO = adminRoleService.listPage(adminRoleParam);
        return JsonData.buildSuccess(pageVO);
    }
    @GetMapping("/listAllRole")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData listAllRole(AdminRoleParam adminRoleParam)
    {
        List<AdminRole> list = adminRoleService.listAllRole(adminRoleParam);
        return JsonData.buildSuccess(list);
    }
}
