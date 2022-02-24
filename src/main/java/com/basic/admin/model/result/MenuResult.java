package com.basic.admin.model.result;

import com.basic.admin.model.entity.AdminMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author lcy
 * @version 1.0
 * @description: TODO
 * @date 2022/2/14 16:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuResult extends AdminMenu
{
    List<MenuResult> children;
}
