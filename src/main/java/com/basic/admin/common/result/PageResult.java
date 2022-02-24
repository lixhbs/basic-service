package com.basic.admin.common.result;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lix.
 * @title com.longrise.dpsi.component.PageResult
 * @description Carpe Diem!
 * @createtime 2021-05-12 14:50
 */
@NoArgsConstructor
public class PageResult<T1, T2>
{
    @Getter
    private PageVO<T1> pageResult;

    @Getter
    private PageVO<T2> dataResult;

    public PageResult(Page<T1> page)
    {
        pageResult = new PageVO<>();
        pageResult.setPageNum(page.getCurrent());
        pageResult.setPageSize(page.getSize());
        pageResult.setTotalPage(page.getPages());
        pageResult.setTotal(page.getTotal());
        pageResult.setItems(page.getRecords());
    }

    public PageResult(Page<T1> page, Class<T2> tClass)
    {
        dataResult = new PageVO<>();
        dataResult.setPageNum(page.getCurrent());
        dataResult.setPageSize(page.getSize());
        dataResult.setTotalPage(page.getPages());
        dataResult.setTotal(page.getTotal());

        List<T2> collect = page.getRecords().stream().map(item -> {
            try
            {
                T2 t2 = tClass.newInstance();
                BeanUtil.copyProperties(item, t2);
                return t2;
            } catch (InstantiationException | IllegalAccessException e)
            {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        dataResult.setItems(collect);
    }
}
