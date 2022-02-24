package com.basic.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basic.admin.model.entity.AdminRoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色菜单关联表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-02-17
 */
@Mapper
public interface AdminRoleMenuMapper extends BaseMapper<AdminRoleMenu> {

}
