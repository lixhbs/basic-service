package com.basic.admin.common.constants;

/**
 * @author lix.
 * @title com.chenhuli.interviewoffer.common.constants.ConstantKey
 * @description Carpe Diem!
 * @createtime 2021-07-25 18:17
 */
public class ConstantKey
{
    public static final String TOKEN_SIGN_KEY = "honi-server";
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String REGEX_IDENTITY = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
            "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
    public static final String SUPPER_ADMIN_NAME = "root";

    public static final String SYS_TOKEN_REDIS_PREFIX = "SYS:TOKEN:%s";
    public static final String SYS_USER_TOKEN_LIST_REDIS_PREFIX = "SYS:TOKEN:LIST:%s";
}
