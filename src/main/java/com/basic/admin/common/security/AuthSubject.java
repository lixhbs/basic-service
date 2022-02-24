package com.basic.admin.common.security;

import com.basic.admin.model.entity.AdminRole;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


/**
 * @author sys
 */
@Data
@Builder
public class AuthSubject implements Serializable {

    /**
     * 用户id
     */
    String userId;

    /**
     * 账户名
     */
    String account;

    String realName;

    Set<String> apiRoles;
    List<AdminRole> roles;

    String areaId;
    String fullName;
    String orgName;
    String nickName;
    String phone;
    String orgId;
    String orgSysCode;
    String positionName;
    String positionId;
    String token;

    long timeout;
}
