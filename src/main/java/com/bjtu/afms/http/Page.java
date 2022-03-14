package com.bjtu.afms.http;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private static final long serialVersionUID = 1L;
    private long total;			// 总条数
    private List<T> list;		// 对象列表，通过.list访问到
    private int pageNum;		// 当前页号
    private int pageSize;		// 每页记录数
    private int size;			// 当前页记录数
    private int pages;			// 总页数
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;

    public Page(List<T> list) {
        this.list = list;
    }

    public Page(PageInfo<T> pageInfo) {
        this.list = pageInfo.getList();
        this.total = pageInfo.getTotal();
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.size = pageInfo.getSize();
        this.pages = pageInfo.getPages();
        this.isFirstPage = pageInfo.isIsFirstPage();
        this.isLastPage = pageInfo.isIsLastPage();
        this.hasPreviousPage = pageInfo.isHasPreviousPage();
        this.hasNextPage = pageInfo.isHasNextPage();
    }

    public Page(PageInfo<?> pageInfo, List<T> list) {
        this.list = list;
        this.total = pageInfo.getTotal();
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.size = pageInfo.getSize();
        this.pages = pageInfo.getPages();
        this.isFirstPage = pageInfo.isIsFirstPage();
        this.isLastPage = pageInfo.isIsLastPage();
        this.hasPreviousPage = pageInfo.isHasPreviousPage();
        this.hasNextPage = pageInfo.isHasNextPage();
    }
}
