package com.basic.admin.common.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
public class PageableBO implements Serializable {

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
