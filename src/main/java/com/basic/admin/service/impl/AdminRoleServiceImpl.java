package com.basic.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.basic.admin.common.result.JsonData;
import com.basic.admin.common.result.PageVO;
import com.basic.admin.mapper.AdminRoleMapper;
import com.basic.admin.model.bo.AdminRoleEditBO;
import com.basic.admin.model.bo.AdminRoleEditStatusBO;
import com.basic.admin.model.bo.AdminRoleInsertBO;
import com.basic.admin.model.entity.AdminRole;
import com.basic.admin.model.entity.AdminRoleMenu;
import com.basic.admin.model.param.AdminRoleParam;
import com.basic.admin.service.IAdminRoleMenuService;
import com.basic.admin.service.IAdminRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-01-17
 */
@Service("adminRoleService")
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements IAdminRoleService
{
    @Resource
    private IAdminRoleMenuService adminRoleMenuService;

    @Resource
    private AdminRoleMapper adminRoleMapper;

    /**
     * 角色查询条件统一处理
     *
     * @param param 查询条件
     * @return com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<com.basic.admin.model.entity.AdminRole>
     * @author l.c.y
     * @date 2022/2/18 11:29
     */
    public LambdaQueryWrapper<AdminRole> searchRoleHandle(AdminRoleParam param)
    {
        LambdaQueryWrapper<AdminRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(AdminRole::getCreatedTime);

        Integer isDelete = param.getIsDelete();
        if (isDelete == null)
        {
            isDelete = 0;
        }
        lambdaQueryWrapper.eq(AdminRole::getIsDelete, isDelete);
        String roleName = param.getRoleName();
        if (StrUtil.isNotBlank(roleName))
        {
            lambdaQueryWrapper.eq(AdminRole::getRoleName, roleName);
        }
        String roleValue = param.getRoleValue();
        if (StrUtil.isNotBlank(roleValue))
        {
            lambdaQueryWrapper.eq(AdminRole::getRoleValue, roleValue);
        }
        Integer status = param.getStatus();
        if (status != null)
        {
            lambdaQueryWrapper.eq(AdminRole::getStatus, status);
        }

        return lambdaQueryWrapper;
    }

    /**
     * 根据条件查询所有的角色数据
     *
     * @param adminRoleParam 查询条件
     * @return java.util.List<com.basic.admin.model.entity.AdminRole>
     * @author l.c.y
     * @date 2022/2/18 11:31
     */
    @Override
    public List<AdminRole> listAllRole(AdminRoleParam adminRoleParam)
    {
        LambdaQueryWrapper<AdminRole> adminRoleLambdaQueryWrapper =  searchRoleHandle(adminRoleParam);
        return list(adminRoleLambdaQueryWrapper);
    }

    @Override
    public List<AdminRole> listRoleByUserId(String userId)
    {
        // 停用的没有处理
        return adminRoleMapper.getRolesByUserId(userId);
    }

    @Override
    public PageVO<AdminRole> listPage(AdminRoleParam param)
    {
        Integer pageNum = param.getPageNum();
        Integer pageSize = param.getPageSize();
        Page<AdminRole> pageInfo = new Page<>();
        LambdaQueryWrapper<AdminRole> lambdaQueryWrapper = searchRoleHandle(param);
        pageInfo.setCurrent(pageNum).setSize(pageSize);
        Page<AdminRole> page = page(pageInfo, lambdaQueryWrapper);
        return new PageVO<>(page);
    }

    /**
     * 判断角色名称和值是否存在
     *
     * @param roleName  角色名称
     * @param roleValue 角色值
     * @return boolean
     * @author l.c.y
     * @date 2022/2/17 14:18
     */
    public boolean roleExist(String roleName, String roleValue)
    {
        AdminRoleParam adminRoleParam = new AdminRoleParam();
        adminRoleParam.setRoleName(roleName);
        adminRoleParam.setRoleValue(roleValue);
        return CollectionUtil.isNotEmpty(listAllRole(adminRoleParam));
    }

    public AdminRole saveRole(AdminRole adminRole)
    {
        adminRole.insertParam();
        adminRole.setIsDelete(0);
        adminRole.setStatus(0);
        boolean save = save(adminRole);
        if (save)
        {
            return adminRole;
        }
        return null;
    }

    @Override
    public JsonData insertRole(AdminRole adminRole)
    {
        if (roleExist(adminRole.getRoleName(), adminRole.getRoleValue()))
        {
            return JsonData.buildError("角色已存在！");
        }
        AdminRole saveRes = saveRole(adminRole);
        if (saveRes == null)
        {
            return JsonData.buildError("创建角色失败！");
        }
        return JsonData.buildSuccess(saveRes);
    }

    @Override
    public JsonData insertRoleAndMenu(AdminRoleInsertBO adminRoleInsertBO)
    {
        if (roleExist(adminRoleInsertBO.getRoleName(), adminRoleInsertBO.getRoleValue()))
        {
            return JsonData.buildError("角色已存在！");
        }
        AdminRole saveRes = saveRole(adminRoleInsertBO);
        if (saveRes == null)
        {
            return JsonData.buildError("创建角色失败！");
        }
        boolean batchRes = batchSaveRoleMenu(saveRes.getId(), adminRoleInsertBO.getMenu());
        if (batchRes)
        {
            return JsonData.buildSuccess("新增成功！");
        }
        return JsonData.buildError("新增失败！");
    }

    public boolean batchSaveRoleMenu(String roleId, List<String> menuIds)
    {
        List<AdminRoleMenu> relationList = new ArrayList<>();
        for (String menuId : menuIds)
        {
            AdminRoleMenu relation = new AdminRoleMenu();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            relationList.add(relation);
        }
        return adminRoleMenuService.saveBatch(relationList);
    }

    @Override
    public JsonData editRoleAndMenu(AdminRoleEditBO adminRoleEditBO)
    {
        boolean update = updateById(adminRoleEditBO);
        if (!update)
        {
            return JsonData.buildError("角色信息修改失败");
        }
        List<String> menu = adminRoleEditBO.getMenu();
        if (CollectionUtil.isNotEmpty(menu))
        {
            //删除原有关系
            String roleId = adminRoleEditBO.getId();
            QueryWrapper<AdminRoleMenu> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(AdminRoleMenu::getRoleId, roleId);
            adminRoleMenuService.remove(wrapper);

            boolean bool = batchSaveRoleMenu(roleId, menu);
            if (!bool)
            {
                return JsonData.buildSuccess("关联菜单修改失败！");
            }
        }
        return JsonData.buildSuccess("角色信息修改成功");
    }

    @Override
    @Transactional
    public JsonData deleteRoleAndMenu(String id)
    {
        AdminRole adminRole = getById(id);
        if (adminRole == null)
        {
            return JsonData.buildError("没有该数据");
        }
        boolean removeBool = removeById(id);
        if (removeBool)
        {
            QueryWrapper<AdminRoleMenu> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(AdminRoleMenu::getRoleId, id);
            boolean remove = adminRoleMenuService.remove(wrapper);
            if (!remove)
            {
                return JsonData.buildError("角色关联菜单删除失败！");
            }
        }
        return JsonData.buildSuccess("删除成功！");
    }

    @Override
    public JsonData editRoleStatus(AdminRoleEditStatusBO adminRoleEditStatusBO)
    {
        AdminRole adminRole = new AdminRole();
        adminRole.updateParam();
        adminRole.setId(adminRoleEditStatusBO.getId());
        adminRole.setStatus(adminRoleEditStatusBO.getStatus());
        if (updateById(adminRole))
        {
            return JsonData.buildSuccess(adminRole);
        }
        return JsonData.buildError("角色状态修改失败");
    }


}