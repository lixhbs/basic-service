package com.basic.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.basic.admin.common.result.JsonData;
import com.basic.admin.common.result.PageVO;
import com.basic.admin.model.bo.AdminRoleEditBO;
import com.basic.admin.model.bo.AdminRoleEditStatusBO;
import com.basic.admin.model.bo.AdminRoleInsertBO;
import com.basic.admin.model.entity.AdminRole;
import com.basic.admin.model.param.AdminRoleParam;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-01-17
 */
public interface IAdminRoleService extends IService<AdminRole> {

    PageVO<AdminRole> listPage(AdminRoleParam adminRoleParam);

    JsonData insertRole(AdminRole adminRole);

    JsonData insertRoleAndMenu(AdminRoleInsertBO adminRoleInsertBO);

    JsonData editRoleAndMenu(AdminRoleEditBO adminRoleEditBO);

    JsonData deleteRoleAndMenu(String id);

    JsonData editRoleStatus(AdminRoleEditStatusBO adminRoleEditStatusBO);

    List<AdminRole> listAllRole(AdminRoleParam adminRoleParam);

    List<AdminRole> listRoleByUserId(String userId);
}
