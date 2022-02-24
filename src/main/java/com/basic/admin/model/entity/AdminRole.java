package com.basic.admin.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.admin.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author baomidou
 * @since 2022-02-17
 */
@Getter
@Setter
@TableName("admin_role")
public class AdminRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 角色值
     */
    @TableField("role_value")
    private String roleValue;

    /**
     * 状态;0：启用、1:禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 排序
     */
    @TableField("order_no")
    private Integer orderNo;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 是否删除
     */
    @TableField("is_delete")
    private Integer isDelete;


}
