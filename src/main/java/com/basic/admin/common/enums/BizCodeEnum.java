package com.basic.admin.common.enums;

import lombok.Getter;

/**
 * @author li
 */

public enum BizCodeEnum
{
    /*                                  --- 通用操作码 Begin ---                            */
    /**
     * 重复操作
     * @date 2021-05-06-11:31
     */
    OPS_REPEAT(110001,"重复操作"),

    /**
     * 没有权限
     * @date 2021-05-06-11:31
     */
    OPS_NOT_AUTHORITY(110002,"没有权限"),

    /**
     * 操作异常
     * @date 2021-05-06-11:31
     */
    OPS_EXCEPTION(110003,"操作异常"),

    /**
     * 参数错误
     * @date 2021-05-06-11:31
     */
    OPS_NOT_PARAMETER(110004,"参数错误"),

    /**
     * 数据为空
     */
    OPS_DATA_NOT(110005, "数据为空"),

    /*                                  --- 通用操作码 End ---                            */

    /*                                  --- 验证码操作码 Begin ---                            */

    /**
     * 验证码发送失败
     */
    CODE_TO_ERROR(240001,"验证码发送失败"),

    /**
     * 验证码发送过快
     */
    CODE_LIMITED(240002,"验证码发送过快"),

    /**
     * 验证码错误
     */
    CODE_ERROR(240003,"验证码错误"),

    /**
     * 图形验证码错误
     */
    CODE_CAPTCHA(240105,"图形验证码错误"),

    /*                                  --- 验证码操作码 End ---                            */

    /*                                  --- 账号操作码 Begin ---                            */

    /**
     * 账号已经存在
     */
    ACCOUNT_REPEAT(250001,"账号已经存在"),

    /**
     * 账号或者密码错误
     */
    ACCOUNT_UNREGISTER(250002,"账号或者密码错误"),

    /**
     * 密码至少包含一个大写字母一个小写字母和一个数字，且6~18位！
     */
    ACCOUNT_PWD_REGISTER_ERROR(250003,"密码至少包含一个大写字母一个小写字母和一个数字，且6~18位！"),

    /**
     * 未登录
     */
    ACCOUNT_UNLOGIN(250004,"未登录"),

    /**
     * 账号输入错误
     */
    ACCOUNT_ERROR(250005,"账号输入错误"),

    /**
     * 账号不存在
     */
    ACCOUNT_NOT_REPEAT(250011,"账号不存在"),

    /**
     * 两次输入密码不一致
     */
    ACCOUNT_UPDATEPWD(250007,"两次输入密码不一致"),

    /**
     * 原密码错误
     */
    ACCOUNT_OLDPWD(250008,"原密码错误"),

    /**
     * 原密码与新密码一致
     */
    ACCOUNT_OLD_PWDANDNEWPWD(250009,"原密码与新密码一致"),

    /**
     * 原手机号不正确
     */
    ACCOUNT_OLD_PWDANDNEWPHONE(250010,"原手机号不正确"),

    /**
     * 输入手机号格式不正确
     */
    ACCOUNT_VALIPHONE(250011,"输入手机号格式不正确"),

    /**
     * 请输入账号
     */
    ACCOUNT_IS_NULL(250012,"请输入账号"),

    /**
     * 请输入账号
     */
    ACCOUNT_IS_PWD_NULL(250013,"请输入账号"),

    /**
     * 请输入用户名
     */
    ACCOUNT_IS_NICJNAME_NULL(250014,"请输入用户名"),

    /**
     * 登录失败
     */
    ACCOUNT_LOGIN_FAIL(250015,"登录失败"),

    /**
     * 用户未实名
     */
    ACCOUNT_NOT_AUTH(250016,"用户未实名"),

    /**
     * 账号验证失败
     */
    ACCOUNT_VALIDATE_FAIL(250017,"账号验证失败"),

    /*                                  --- 账号操作码 End ---                            */



    /*                                  --- 认证操作码 Begin ---                           */

    /**
     * 用户已认证
     */
    VERIFIC_FIND_SUCCESS(260001,"用户已认证"),

    /**
     * 用户认证信息审核中
     */
    VERIFIC_FIND_ON(260002,"用户认证信息审核中"),

    /*                                  --- 认证操作码 End ---                            */

    /*                                  --- 文件相关 Begin ---                            */

    /**
     * 用户头像文件上传失败
     */
    FILE_UPLOAD_USER_IMG_FAIL(600001,"用户头像文件上传失败");

    /*                                  --- 文件相关 End ---                            */

    @Getter
    private final String message;

    @Getter
    private final Integer code;

    BizCodeEnum(int code, String message){
        this.code = code;
        this.message = message;
    }
}
