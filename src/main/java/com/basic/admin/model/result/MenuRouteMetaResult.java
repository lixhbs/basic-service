package com.basic.admin.model.result;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lcy
 * @version 1.0
 * @description: TODO
 * @date 2022/2/21 19:26
 */
@Data
@Builder
public class MenuRouteMetaResult implements Serializable
{
    String icon;
    String title;

    /**
     * 是否固定
     */
    boolean affix;

    /**
     * 是否外链
     */
    boolean isLink;

    boolean hideMenu;

    boolean hideTab;

    Integer orderNo;
}
