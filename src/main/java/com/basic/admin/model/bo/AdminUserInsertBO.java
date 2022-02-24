package com.basic.admin.model.bo;

import com.basic.admin.model.entity.AdminUser;
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
public class AdminUserInsertBO extends AdminUser
{
    @NotEmpty
    private String account;

    @NotEmpty
    private String password;

    @NotEmpty
    private List<String> roleIds;
}
