package com.basic.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.basic.admin.common.constants.ConstantKey;
import com.basic.admin.common.enums.BizCodeEnum;
import com.basic.admin.common.result.JsonData;
import com.basic.admin.common.result.PageResult;
import com.basic.admin.common.result.PageVO;
import com.basic.admin.common.security.AuthSubject;
import com.basic.admin.manager.AuthManager;
import com.basic.admin.mapper.AdminUserMapper;
import com.basic.admin.model.bo.AdminUserEditBO;
import com.basic.admin.model.bo.AdminUserInsertBO;
import com.basic.admin.model.entity.AdminRole;
import com.basic.admin.model.entity.AdminUser;
import com.basic.admin.model.entity.AdminUserRole;
import com.basic.admin.model.param.AdminLoginParam;
import com.basic.admin.model.param.AdminUserParam;
import com.basic.admin.model.result.AdminUserResult;
import com.basic.admin.service.IAdminRoleService;
import com.basic.admin.service.IAdminUserRoleService;
import com.basic.admin.service.IAdminUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-01-10
 */
@Service("adminUserService")
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService
{

    @Resource
    private AuthManager authManager;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private IAdminUserRoleService adminUserRoleService;

    @Resource
    private IAdminRoleService adminRoleService;

    /**
     * 用户登录
     *
     * @param adminLoginParam 登录信息
     * @return com.basic.admin.common.result.JsonData
     * @author l.c.y
     * @date 2022/1/10 22:21
     */
    @Override
    public JsonData innerSystemLogin(AdminLoginParam adminLoginParam)
    {
        String paramPassword = adminLoginParam.getPassword();
        String paramUsername = adminLoginParam.getUsername();

        // 用户token过期时间
        long userTimeOut = 1 << 7;

        // 校验验证码

        // 判断账号是否被锁定 TODO

        // 查询是否存在用户
        AdminUser adminUser = getUserByAccount(paramUsername);

        if (null == adminUser)
        {
            // 用户不存在 提示账户或密码错误
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_UNREGISTER);
        }
        // 账号密码不通过
        if (!authManager.checkPassword(paramPassword, adminUser.getPassword()))
        {
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_UNREGISTER);
        }

        String userId = adminUser.getId();
        String account = adminUser.getAccount();

        // 验证通过， 生成token
        String token = UUID.randomUUID().toString();

        // 通过用户ID查询role
        List<AdminRole> adminRoles = adminRoleService.listRoleByUserId(userId);

        AuthSubject authSubject = AuthSubject.builder()
                .userId(userId)
                .token(token)
                .roles(adminRoles)
                .realName(account)
                .apiRoles(new HashSet<>())
                .timeout(userTimeOut)
                .account(account).build();

        JSONObject jsonObject = JSONUtil.parseObj(authSubject, false, true);
        redisTemplate.opsForValue().set(String.format(ConstantKey.SYS_TOKEN_REDIS_PREFIX, token), jsonObject.toString(), userTimeOut, TimeUnit.MINUTES);
        redisTemplate.opsForList().rightPush(String.format(ConstantKey.SYS_USER_TOKEN_LIST_REDIS_PREFIX, ConstantKey.SUPPER_ADMIN_NAME), token);

        return JsonData.buildSuccess(authSubject);
    }

    @Override
    public PageVO<AdminUserResult> listAccount(AdminUserParam param)
    {
        Integer pageNum = param.getPageNum();
        Integer pageSize = param.getPageSize();
        Page<AdminUser> pageInfo = new Page<>();
        LambdaQueryWrapper<AdminUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AdminUser::getIsDelete, 0);
        lambdaQueryWrapper.orderByDesc(AdminUser::getCreatedTime);
        pageInfo.setCurrent(pageNum).setSize(pageSize);
        Page<AdminUser> page = page(pageInfo, lambdaQueryWrapper);
        PageVO<AdminUserResult> dataResult = new PageVO<>();
        if (page != null)
        {
            PageResult<AdminUser, AdminUserResult> userResult = new PageResult<>(page, AdminUserResult.class);
            dataResult = userResult.getDataResult();
            dataResult.getItems().forEach(item -> {
                String id = item.getId();
                List<AdminRole> adminRoles = adminRoleService.listRoleByUserId(id);
                item.setRoles(adminRoles);
            });
        }
        return dataResult;
    }

    @Override
    public boolean delUserById(String id)
    {
        // 先查询该账号是否是管理员，如果是管理员就不能删除
        // 不允许删除自己
        AdminUser user = getById(id);
        Integer type = user.getType();
        // 不允许删除超级管理员
        if (type == 9)
        {
            return false;
        }
        AdminUser adminUser = new AdminUser();
        adminUser.setId(id);
        adminUser.setIsDelete(1);
        adminUser.updateParam();
        return updateById(adminUser);
    }

    @Override
    public JsonData insertUser(AdminUserInsertBO adminUserInsertBO)
    {
        String account = adminUserInsertBO.getAccount();
        // 查询是否存在用户
        boolean isAccountExist = accountExist(account);
        if (isAccountExist)
        {
            // 账户名存在
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_REPEAT);
        }
        AdminUser adminUser = new AdminUser();
        BeanUtil.copyProperties(adminUserInsertBO, adminUser);
        adminUser.setType(1);
        adminUser.setIsDelete(0);
        adminUser.setStatus(0); // 0 正常， 1 停用
        adminUser.setLoginCount(0);
        adminUser.insertParam();
        boolean save = save(adminUser);
        if (save)
        {
            List<String> roleIds = adminUserInsertBO.getRoleIds();
            if (CollectionUtil.isNotEmpty(roleIds))
            {
                boolean batchRes = batchSaveUserRole(adminUser.getId(), roleIds);
                if (!batchRes)
                {
                    return JsonData.buildError("角色添加失败！");
                }
            }
            return JsonData.buildSuccess(adminUser);
        }
        return JsonData.buildError("新增账户失败！");
    }

    public boolean batchSaveUserRole(String userId, List<String> roleIds)
    {
        List<AdminUserRole> relationList = new ArrayList<>();
        for (String roleId : roleIds)
        {
            AdminUserRole relation = new AdminUserRole();
            relation.setRoleId(roleId);
            relation.setUserId(userId);
            relationList.add(relation);
        }
        return adminUserRoleService.saveBatch(relationList);
    }

    @Override
    public JsonData editUser(AdminUserEditBO adminUser)
    {
        adminUser.updateParam();
        String userId = adminUser.getId();
        boolean bool = updateById(adminUser);
        // 添加哪一些字段不能修改
        if (bool)
        {
            List<String> roleIds = adminUser.getRoleIds();
            if (CollectionUtil.isNotEmpty(roleIds))
            {
                LambdaQueryWrapper<AdminUserRole> adminUserRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
                adminUserRoleLambdaQueryWrapper.eq(AdminUserRole::getUserId, userId);
                adminUserRoleService.remove(adminUserRoleLambdaQueryWrapper);

                boolean batchRes = batchSaveUserRole(userId, roleIds);
                if (!batchRes)
                {
                    return JsonData.buildError("角色添加失败！");
                }
            }
            return JsonData.buildSuccess(adminUser);
        }
        return JsonData.buildError("修改账户失败！");
    }

    @Override
    public JsonData logout()
    {
        AuthSubject currentUserInfo = authManager.getCurrentUserInfo();
        String token = currentUserInfo.getToken();
        // 这里没有删除opsForList的东西
        Boolean delete = redisTemplate.delete(String.format(ConstantKey.SYS_TOKEN_REDIS_PREFIX, token));
        if (Boolean.TRUE == delete)
        {
            return JsonData.buildSuccess();
        } else
        {
            return JsonData.buildError("退出失败！");
        }
    }

    /**
     * 根据用户名查询用户是否存在
     *
     * @param account 用户名
     * @return com.basic.admin.entity.AdminUser
     * @author l.c.y
     * @date 2022/1/16 23:09
     */
    private AdminUser getUserByAccount(String account)
    {
        LambdaQueryWrapper<AdminUser> dmsUserTableLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dmsUserTableLambdaQueryWrapper.eq(AdminUser::getAccount, account)
                .select(AdminUser::getPassword, AdminUser::getAccount, AdminUser::getId);
        List<AdminUser> list = list(dmsUserTableLambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(list))
        {
            return list.get(0);
        } else
        {
            return null;
        }
    }

    @Override
    public boolean accountExist(String account)
    {
        AdminUser userByUserFlag = getUserByAccount(account);
        return userByUserFlag != null;
    }


}
