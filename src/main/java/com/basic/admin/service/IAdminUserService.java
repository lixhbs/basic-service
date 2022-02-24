package com.basic.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.basic.admin.common.result.JsonData;
import com.basic.admin.common.result.PageVO;
import com.basic.admin.model.bo.AdminUserEditBO;
import com.basic.admin.model.bo.AdminUserInsertBO;
import com.basic.admin.model.entity.AdminUser;
import com.basic.admin.model.param.AdminLoginParam;
import com.basic.admin.model.param.AdminUserParam;
import com.basic.admin.model.result.AdminUserResult;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-01-10
 */
public interface IAdminUserService extends IService<AdminUser> {

    JsonData innerSystemLogin(AdminLoginParam adminLoginParam);

    PageVO<AdminUserResult> listAccount(AdminUserParam adminUserParam);

    boolean delUserById(String id);

    JsonData insertUser(AdminUserInsertBO adminUser);

    boolean accountExist(String account);

    JsonData editUser(AdminUserEditBO adminUser);

    JsonData logout();
}
