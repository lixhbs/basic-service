package com.basic.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basic.admin.model.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-01-10
 */
@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUser> {

}
