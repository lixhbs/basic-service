package com.basic.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.basic.admin.common.result.JsonData;
import com.basic.admin.common.result.PageVO;
import com.basic.admin.common.security.AuthSubject;
import com.basic.admin.manager.AuthManager;
import com.basic.admin.mapper.AdminMenuMapper;
import com.basic.admin.model.entity.AdminMenu;
import com.basic.admin.model.entity.AdminRole;
import com.basic.admin.model.param.AdminMenuParam;
import com.basic.admin.model.result.MenuResult;
import com.basic.admin.model.result.MenuRouteMetaResult;
import com.basic.admin.model.result.MenuRouteResult;
import com.basic.admin.service.IAdminMenuService;
import com.basic.admin.service.IAdminRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-01-10
 */
@Service("adminMenuService")
public class AdminMenuServiceImpl extends ServiceImpl<AdminMenuMapper, AdminMenu> implements IAdminMenuService
{
    @Resource
    private AdminMenuMapper adminMenuMapper;

    @Resource
    private AuthManager authManager;


    @Override
    public PageVO<AdminMenu> listPage(AdminMenuParam param)
    {
        Integer pageNum = param.getPageNum();
        Integer pageSize = param.getPageSize();
        Page<AdminMenu> pageInfo = new Page<>();
        LambdaQueryWrapper<AdminMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.orderByDesc(AdminMenu::getCreatedTime);
        pageInfo.setCurrent(pageNum).setSize(pageSize);
        Page<AdminMenu> page = page(pageInfo, lambdaQueryWrapper);
        return new PageVO<>(page);
    }

    @Override
    public List<MenuResult> listMenu()
    {
        LambdaQueryWrapper<AdminMenu> adminMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminMenuLambdaQueryWrapper.eq(AdminMenu::getIsDelete, '0');
        adminMenuLambdaQueryWrapper.orderByDesc(AdminMenu::getOrderNo);
        List<AdminMenu> list = list(adminMenuLambdaQueryWrapper);
        if (CollectionUtil.isEmpty(list))
        {
            return null;
        }
        // 先处理目录
        HashMap<String, MenuResult> menuMap = menuHandle(list);
        List<MenuResult> menuList = new ArrayList<>();
        menuMap.forEach((k, v) -> {
            menuList.add(v);
        });
        return menuList;
    }

    @Override
    public AdminMenu saveMenu(AdminMenu adminMenu)
    {
        adminMenu.setIsDelete("0");
        boolean save = save(adminMenu);
        if (save)
        {
            return adminMenu;
        } else
        {
            return new AdminMenu();
        }
    }

    @Override
    public boolean delMenuById(String id)
    {
        // 这里先判断下是否有删除的权限
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.setIsDelete("1");
        adminMenu.setId(id);
        adminMenu.setUpdatedTime(LocalDateTime.now());
        return updateById(adminMenu);
    }

    @Override
    public AdminMenu editMenu(AdminMenu adminMenu)
    {
        adminMenu.setUpdatedTime(LocalDateTime.now());
        boolean save = updateById(adminMenu);
        if (save)
        {
            return adminMenu;
        } else
        {
            return new AdminMenu();
        }
    }

    /**
     * 目录
     *
     * @param list 所有菜单
     * @return java.util.HashMap<java.lang.String, com.basic.admin.entity.AdminMenu>
     * @author l.c.y
     * @date 2022/2/14 16:40
     */
    public HashMap<String, MenuResult> menuCatalogHandle(List<AdminMenu> list)
    {
        HashMap<String, MenuResult> menuMap = new HashMap<>();
        list.forEach(item -> {
            String type = item.getType();
            if ("0".equals(type))
            {
                MenuResult menuVO = new MenuResult();
                BeanUtil.copyProperties(item, menuVO);
                menuMap.put(item.getId(), menuVO);
            }
        });
        return menuMap;
    }

    public HashMap<String, MenuResult> menuHandle(List<AdminMenu> list)
    {
        HashMap<String, MenuResult> menuMap = menuCatalogHandle(list);

        list.forEach(item -> {
            String parentMenu = item.getParentMenu();
            String type = item.getType();
            if (menuMap.containsKey(parentMenu) && "1".equals(type))
            {
                MenuResult menuVO = menuMap.get(parentMenu);
                MenuResult menuChildren = new MenuResult();
                BeanUtil.copyProperties(item, menuChildren);
                List<MenuResult> children = menuVO.getChildren();
                if (CollectionUtil.isNotEmpty(children))
                {
                    children.add(menuChildren);
                } else
                {
                    menuVO.setChildren(ListUtil.toList(menuChildren));
                    menuMap.put(parentMenu, menuVO);
                }
            }
        });
        return menuMap;
    }

    @Override
    public HashSet<AdminMenu> listMenuByRole(List<AdminRole> roles)
    {
        HashSet<AdminMenu> menus = new HashSet<>();
        roles.forEach(adminRole -> {
            List<AdminMenu> menuByRole = adminMenuMapper.getMenuByRole(adminRole.getId());
            menus.addAll(menuByRole);
        });
        return menus;
    }

    @Override
    public JsonData listUserMenu()
    {
        // 拿到用户信息
        AuthSubject userInfo = authManager.getCurrentUserInfo();
        // 通过用户角色获取菜单
        List<AdminRole> roles = userInfo.getRoles();
        HashSet<AdminMenu> adminMenus = new HashSet<>();
        roles.forEach(item -> {
            List<AdminMenu> menuByRole = adminMenuMapper.getMenuByRole(item.getId());
            adminMenus.addAll(menuByRole);
        });
        ArrayList<AdminMenu> adminMenuList = new ArrayList<>(adminMenus);
        adminMenuList.sort((o1, o2) -> o2.getOrderNo().compareTo(o1.getOrderNo()));

        List<MenuRouteResult> menuResult = userMenuHandle(adminMenuList);

        MenuRouteResult menuRouteResult = userRouteHomeHandle(menuResult);
        menuResult.add(menuRouteResult);
        return JsonData.buildSuccess(menuResult);
    }

    public MenuRouteResult userRouteHomeHandle(List<MenuRouteResult> menuResult){
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.setMenuName("BASE_HOME");
        adminMenu.setRoutePath("/BASE_HOME");
        adminMenu.setIsDelete("0");
        adminMenu.setStatus("0");
        adminMenu.setShow("0");
        adminMenu.setRedirect(menuResult.get(0).getPath());
        return menuRouteHandle(adminMenu);
    }

    public MenuRouteResult menuRouteHandle(AdminMenu adminMenu)
    {
        boolean isShow = "0".equals(adminMenu.getShow());
        Integer orderNo = adminMenu.getOrderNo();
        MenuRouteMetaResult meta = MenuRouteMetaResult.builder()
                .title(adminMenu.getMenuShowName())
                .icon(adminMenu.getIcon())
                .hideMenu(isShow)
                .hideTab(isShow)
                .orderNo(orderNo)
                .build();

        return MenuRouteResult.builder()
                .alias(new ArrayList<>())
                .name(adminMenu.getMenuName())
                .component(adminMenu.getComponent())
                .path(adminMenu.getRoutePath())
                .redirect(adminMenu.getRedirect())
                .meta(meta)
                .build();
    }

    private List<MenuRouteResult> userMenuHandle(ArrayList<AdminMenu> userMenuList)
    {
        // 先放目录 ， 然后放没有父节点的菜单   按钮权限先不管
        HashMap<String, MenuRouteResult> menuRouteResultMap = new HashMap<>();
        userMenuList.forEach(item -> {
            String type = item.getType();
            String parentMenu = item.getParentMenu();
            MenuRouteResult menuRouteResult = menuRouteHandle(item);
            if ("0".equals(type) || ("1".equals(type) && StrUtil.isEmpty(parentMenu)))
            {
                menuRouteResult.setComponent("LAYOUT");
                menuRouteResultMap.put(item.getId(), menuRouteResult);
            }
        });
        HashMap<String, MenuRouteResult> menuRouteChildrenResult = menuRouteChildrenHandle(userMenuList, menuRouteResultMap);
        ArrayList<MenuRouteResult> menuRouteResults = new ArrayList<>();
        menuRouteChildrenResult.forEach((k, v) -> menuRouteResults.add(v));
        return menuRouteResults;
    }

    private HashMap<String, MenuRouteResult> menuRouteChildrenHandle(ArrayList<AdminMenu> userMenuList, HashMap<String, MenuRouteResult> menuRouteResultMap)
    {
        userMenuList.forEach(item -> {
            String parentMenu = item.getParentMenu();
            boolean containsRes = menuRouteResultMap.containsKey(parentMenu);
            if (containsRes)
            {
                MenuRouteResult menuRouteResult = menuRouteHandle(item);
                MenuRouteResult menuRouteTop = menuRouteResultMap.get(parentMenu);
                List<MenuRouteResult> children = menuRouteTop.getChildren();
                if (CollectionUtil.isEmpty(children))
                {
                    children = new ArrayList<>();
                    menuRouteTop.setRedirect(item.getRoutePath());
                    menuRouteTop.setChildren(children);
                }
                children.add(menuRouteResult);
            }
        });
        return menuRouteResultMap;
    }
}
