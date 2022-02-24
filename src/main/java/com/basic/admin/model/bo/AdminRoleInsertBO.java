package com.basic.admin.model.bo;

import com.basic.admin.model.entity.AdminRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author lcy
 * @version 1.0
 * @description: TODO
 * @date 2022/2/15 16:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminRoleInsertBO extends AdminRole
{
    /**
     * 角色名称
     */
    @NotEmpty
    private String roleName;

    /**
     * 角色值;
     */
    @NotEmpty
    private String roleValue;

    /**
     * 状态;1:启用、2:禁用
     */
    @NotEmpty
    private Integer status;

    @NotEmpty
    private List<String> menu;
}
