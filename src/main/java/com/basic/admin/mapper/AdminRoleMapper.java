package com.basic.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basic.admin.model.entity.AdminMenu;
import com.basic.admin.model.entity.AdminRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-01-30
 */
@Mapper
public interface AdminRoleMapper extends BaseMapper<AdminRole>
{

    @Select("select admin_role.* from admin_role left join admin_user_role on admin_role.id = admin_user_role.role_id where admin_role.is_delete = 0 and admin_user_role.user_id = #{userId}")
    List<AdminRole> getRolesByUserId(String userId);
}
