package com.basic.admin.controller;

import com.basic.admin.common.result.JsonData;
import com.basic.admin.common.result.PageVO;
import com.basic.admin.manager.AuthManager;
import com.basic.admin.model.bo.AdminUserEditBO;
import com.basic.admin.model.bo.AdminUserInsertBO;
import com.basic.admin.model.entity.AdminUser;
import com.basic.admin.model.param.AdminLoginParam;
import com.basic.admin.model.param.AdminUserParam;
import com.basic.admin.service.IAdminUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author baomidou
 * @since 2022-01-10
 */
@RestController
@Validated
@RequestMapping("/api/admin/user")
public class AdminUserController
{
    @Resource
    private IAdminUserService adminUserService;

    @Resource
    private AuthManager authManager;

    @PostMapping("/login")
    public JsonData login(@Valid @RequestBody AdminLoginParam adminLoginParam)
    {
        return adminUserService.innerSystemLogin(adminLoginParam);
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData logout()
    {
        return adminUserService.logout();
    }

    @GetMapping("/listAccount")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData listAccount(AdminUserParam adminUserParam)
    {
        return JsonData.buildSuccess(adminUserService.listAccount(adminUserParam));
    }

    @GetMapping("/getUserInfo")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData getUserInfo()
    {
        return JsonData.buildSuccess(authManager.getCurrentUserInfo());
    }

    @PostMapping("/insertUser")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData insertUser(@RequestBody AdminUserInsertBO adminUser)
    {
        return adminUserService.insertUser(adminUser);
    }

    @PostMapping("/editUser")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData editUser(@RequestBody AdminUserEditBO adminUser)
    {
        return adminUserService.editUser(adminUser);
    }

    @PostMapping("/delUserById/{userId}")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData delUserById(@PathVariable("userId") String id)
    {
        return JsonData.buildSuccess(adminUserService.delUserById(id));
    }

    @PostMapping("/accountExist")
    @PreAuthorize("hasAuthority('AUTH.LOGIN.AFTER')")
    public JsonData accountExist(@RequestBody String account)
    {
        return JsonData.buildSuccess(adminUserService.accountExist(account));
    }

}

