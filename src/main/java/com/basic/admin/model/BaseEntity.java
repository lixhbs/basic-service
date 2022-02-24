package com.basic.admin.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.basic.admin.common.security.AuthSubject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lix.
 * @title com.chenhuli.interviewoffer.model.entity.BaseEntity
 * @description Carpe Diem!
 * @createtime 2021-07-25 15:33
 */
@Data
public class BaseEntity implements Serializable
{

    private static final long serialVersionUID = 1L;

    /**
     * 流水号
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 租户号
     */
    @TableField(value = "tenant_id")
    private String tenantId;

    /**
     * 乐观锁
     */
    @TableField("revision")
    private String revision;

    /**
     * 创建人id
     */
    @TableField(value = "created_by_id", fill = FieldFill.INSERT)
    private String createdById;

    /**
     * 创建人
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 更新人id
     */
    @TableField(value = "updated_by_id", fill = FieldFill.INSERT_UPDATE)
    private String updatedById;

    /**
     * 更新人
     */
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;


    public void insertParam()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
        {
            AuthSubject currentUserInfo = (AuthSubject) authentication.getDetails();
            String account = currentUserInfo.getAccount();
            this.setCreatedBy(account);
            this.setUpdatedBy(account);
        }
        LocalDateTime now = LocalDateTime.now();
        this.setCreatedTime(now);
        this.setUpdatedTime(now);
    }

    public void updateParam(String userFlag)
    {
        this.setUpdatedBy(userFlag);
        this.setUpdatedTime(LocalDateTime.now());
    }

    public void updateParam()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
        {
            AuthSubject currentUserInfo = (AuthSubject) authentication.getDetails();
            String account = currentUserInfo.getAccount();
            this.setUpdatedBy(account);
        }
        this.setUpdatedTime(LocalDateTime.now());
    }
}
