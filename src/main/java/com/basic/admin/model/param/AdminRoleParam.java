package com.basic.admin.model.param;

import com.basic.admin.common.result.PageableBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdminRoleParam extends PageableBO implements Serializable
{

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色值
     */
    private String roleValue;

    /**
     * 状态;0：启用、1:禁用
     */
    private Integer status;

    /**
     * 是否删除
     */
    private Integer isDelete;
}
