package com.basic.admin.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lcy
 * @version 1.0
 * @description: 登录参数
 * @date 2022/1/10 20:34
 */
@Data
public class AdminLoginParam
{
    @NotNull
    private String username;

    @NotNull
    private String password;
}
