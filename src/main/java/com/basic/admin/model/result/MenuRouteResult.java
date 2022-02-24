package com.basic.admin.model.result;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lcy
 * @version 1.0
 * @description: TODO
 * @date 2022/2/21 19:26
 */
@Data
@Builder
public class MenuRouteResult implements Serializable
{
    private String path;
    private MenuRouteMetaResult meta;
    private String component;
    private String name;
    private List<String> alias;
    private String redirect;
    private boolean caseSensitive;
    private List<MenuRouteResult> children;
}
