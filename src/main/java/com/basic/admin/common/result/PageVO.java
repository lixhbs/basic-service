package com.basic.admin.common.result;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> implements Serializable {

    private Long pageNum;
    private Long pageSize;
    private Long total;
    private Long totalPage;
    private List<T> items;

    public PageVO(Page<T> page) {
        mpPageFormat(page);
    }

    private void mpPageFormat(Page<T> page) {
        pageNum = page.getCurrent();
        pageSize = page.getSize();
        total = page.getTotal();
        totalPage = page.getPages();
        items = page.getRecords();
    }

}
