package com.basic.admin.model.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author lcy
 * @version 1.0
 * @description: TODO
 * @date 2022/2/17 17:46
 */
@Data
public class AdminRoleEditStatusBO
{
    @NotEmpty
    String id;

    @NotEmpty
    Integer status;
}
