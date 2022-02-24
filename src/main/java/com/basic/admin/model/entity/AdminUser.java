package com.basic.admin.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.admin.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author baomidou
 * @since 2022-02-15
 */
@Getter
@Setter
@TableName("admin_user")
public class AdminUser extends BaseEntity
{

    private static final long serialVersionUID = 1L;

    /**
     * 登录次数
     */
    @TableField("login_count")
    private Integer loginCount;

    /**
     * 账号
     */
    @TableField("account")
    private String account;

    /**
     * openid
     */
    @TableField("openid")
    private String openid;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 身份证号码
     */
    @TableField("cardno")
    private String cardno;

    /**
     * 手机
     */
    @TableField("mobile_phone")
    private String mobilePhone;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 是否删除
     */
    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 类型
     */
    @TableField("type")
    private Integer type;

}
