package com.basic.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basic.admin.model.entity.AdminMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-02-07
 */
@Mapper
public interface AdminMenuMapper extends BaseMapper<AdminMenu>
{

    @Select("" +
            "select * from admin_menu " +
            "left join admin_role_menu on admin_menu.id = admin_role_menu.menu_id " +
            "where admin_role_menu.role_id = #{roleId} " +
            "and admin_menu.is_delete = '0' " +
            "order by admin_menu.order_no asc")
    List<AdminMenu> getMenuByRole(String roleId);
}
