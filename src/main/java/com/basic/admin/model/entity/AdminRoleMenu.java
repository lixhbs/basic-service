package com.basic.admin.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.admin.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色菜单关联表
 * </p>
 *
 * @author baomidou
 * @since 2022-02-17
 */
@Getter
@Setter
@TableName("admin_role_menu")
public class AdminRoleMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 菜单id
     */
    @TableField("menu_id")
    private String menuId;


}
