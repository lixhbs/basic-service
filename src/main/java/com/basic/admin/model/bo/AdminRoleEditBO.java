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
 * @date 2022/2/17 15:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminRoleEditBO extends AdminRole
{
    /**
     * 角色名称
     */
    @NotEmpty
    private String id;

    private List<String> menu;
}
