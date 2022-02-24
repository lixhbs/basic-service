package com.basic.admin.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author sys
 */
@Data
public class BasePageable implements Serializable {

    private Integer pageNum;
    private Integer pageSize;

    public Integer getPageNum()
    {
        return Optional.ofNullable(pageNum).orElse(1);
    }

    public Integer getPageSize()
    {
        return Optional.ofNullable(pageSize).orElse(10);
    }
}
