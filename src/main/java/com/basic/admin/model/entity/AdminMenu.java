package com.basic.admin.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.admin.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author baomidou
 * @since 2022-02-07
 */
@Getter
@Setter
@TableName("admin_menu")
public class AdminMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父ID
     */
    @TableField("parent_menu")
    private String parentMenu;

    /**
     * 菜单类型;类型 0:目录，1：菜单，2：按钮
     */
    @TableField("type")
    private String type;

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 显示名称
     */
    @TableField("menu_show_name")
    private String menuShowName;

    /**
     * 代码
     */
    @TableField("code")
    private String code;

    /**
     * 排序
     */
    @TableField("order_no")
    private Integer orderNo;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 路由地址
     */
    @TableField("route_path")
    private String routePath;

    /**
     * 组件路径
     */
    @TableField("component")
    private String component;

    /**
     * 权限标识
     */
    @TableField("permission")
    private String permission;

    /**
     * 跳转
     */
    @TableField("redirect")
    private String redirect;

    /**
     * 外链标记;0:否;1:是
     */
    @TableField("is_ext")
    private String isExt;

    /**
     * 缓存标记;0:否;1:是
     */
    @TableField("keepalive")
    private String keepalive;

    /**
     * 是否显示;0:否;1:是
     */
    @TableField("show")
    private String show;

    /**
     * 状态;0:启用;1:禁用
     */
    @TableField("status")
    private String status;

    /**
     * 是否删除;0:否;1:是
     */
    @TableField("is_delete")
    private String isDelete;

}
