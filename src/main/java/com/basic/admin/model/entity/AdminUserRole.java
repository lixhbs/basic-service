package com.basic.admin.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.admin.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户角色关系表
 * </p>
 *
 * @author baomidou
 * @since 2022-02-18
 */
@Getter
@Setter
@TableName("admin_user_role")
public class AdminUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private String roleId;


}
