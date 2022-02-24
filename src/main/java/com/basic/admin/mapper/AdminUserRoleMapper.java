package com.basic.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basic.admin.model.entity.AdminUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户角色关系表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-02-18
 */
@Mapper
public interface AdminUserRoleMapper extends BaseMapper<AdminUserRole> {

}
