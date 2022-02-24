package com.basic.admin.model.result;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.basic.admin.model.entity.AdminRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lcy
 * @version 1.0
 * @description: TODO
 * @date 2022/2/21 17:05
 */
@Data
public class AdminUserResult
{

    /**
     * 流水号
     */
    private String id;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedTime;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 账号
     */
    private String account;


    /**
     * 身份证号码
     */
    private String cardno;

    /**
     * 手机
     */
    private String mobilePhone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 角色名称
     */
    private List<AdminRole> roles;
}
