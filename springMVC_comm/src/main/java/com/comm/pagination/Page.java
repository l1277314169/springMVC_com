/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.comm.pagination;


import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class Page<T> implements Serializable {
    private static final long serialVersionUID = 8450665768936866696L;
    private static final long DEFAULT_PAGE_SIZE = 10L;
    private long currentPage = 1L;

    private long pageSize = 10L;

    private long totalResultSize = 0L;

    private List<T> items = Collections.emptyList();
    private long totalPageNum;
    private String actionType = "do";

    private String currentPageParamName = "page";
    private String url;
    private String requestURI;
    private boolean ajaxFlag = false;

    private List<T> allItems = Collections.emptyList();

    private List<RequestKV> params = Collections.emptyList();
    private T param;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getParam() {
        return this.param;
    }

    public void setParam(T param) {
        this.param = param;
    }

    public void buildUrl(HttpServletRequest req) {
        this.url = WebUtils.getPageUrl(req, initSkipParam());
        initUrl(req);
    }

    public void buildJSONUrl(HttpServletRequest req) {
        this.url = WebUtils.getPageUrl(req, initSkipParam());
        this.ajaxFlag = true;
        initUrl(req);
    }

    private void initUrl(HttpServletRequest req) {
        this.params = WebUtils.getParameterMap(req, initSkipParam());
        this.requestURI = req.getRequestURI();
    }

    public void buildJSONUrl(HttpServletRequest req, boolean use) {
        this.ajaxFlag = true;
        this.url = WebUtils.getPageUrl(req, initSkipParam());

        if (use) {
            if (this.url.endsWith("&page=")) {
                this.url = this.url.replace("&page=", "");
            }
            if (!(this.url.contains("useAjax"))) {
                String requestUri;
                if (this.url.contains("?"))
                    requestUri = "&useAjax=true";
                else {
                    requestUri = "?useAjax=true";
                }
                this.url += requestUri;
            }
        }
        initUrl(req);
    }

    private Map<String, String> initSkipParam() {
        Map skipParam = new HashMap();
        skipParam.put(getCurrentPageParamName(), getCurrentPageParamName());
        skipParam.put("perPageRecord", "perPageRecord");
        skipParam.put("gogo", "gogo");
        if (this.ajaxFlag) {
            skipParam.put("useAjax", "useAjax");
        }
        return skipParam;
    }

    public Page() {
    }

    public Page(long totalResultSize) {
        this.totalResultSize = totalResultSize;
        this.pageSize = 10L;
        this.currentPage = 1L;
    }

    public Page(long pageSize, long currentPage) {
        if (pageSize < 1L)
            this.pageSize = 10L;
        else {
            this.pageSize = pageSize;
        }
        this.currentPage = currentPage;
    }

    public Page(long totalResultSize, long pageSize, long currentPage) {
        this.totalResultSize = totalResultSize;
        this.pageSize = pageSize;
        if ((currentPage < 1L) || (totalResultSize <= pageSize))
            this.currentPage = 1L;
        else {
            this.currentPage = currentPage;
        }
        this.totalPageNum = getTotalPages();
        if (currentPage > this.totalPageNum)
            this.currentPage = 1L;
    }

    public long getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        if ((currentPage == null) || (currentPage.longValue() < 1L)) {
            this.currentPage = 1L;
        }
        this.currentPage = currentPage.longValue();
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalResultSize() {
        return this.totalResultSize;
    }

    public void setTotalResultSize(long totalResultSize) {
        this.totalResultSize = totalResultSize;
        this.totalPageNum = getTotalPages();
    }

    public List<T> getItems() {
        if ((((this.items == null) || (this.items.size() == 0)))
                && (this.allItems.size() > 0)) {
            Long i = Long.valueOf(getStartRows() - 1L);
            int end = getCurrentRowNum();
            if (i.longValue() < end) {
                return this.allItems.subList(i.intValue(), end);
            }

        }

        return this.items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public long getStartRows() {
        return ((this.currentPage - 1L) * this.pageSize);

    }

    public long getStartRowsMySql() {
        return ((this.currentPage - 1L) * this.pageSize);
    }

    public long getTotalPages() {
        if (this.totalResultSize % this.pageSize > 0L) {
            return (this.totalResultSize / this.pageSize + 1L);
        }
        return (this.totalResultSize / this.pageSize);
    }

    public static <T> Page<T> page(long totalResultSize, long pageSize,
                                   long currentPage) {
        return new Page(totalResultSize, pageSize, currentPage);
    }

    public static <T> Page<T> page(long pageSize, long currentPage) {
        return new Page(pageSize, currentPage);
    }

    public long getTotalPageNum() {
        return getTotalPages();
    }

    public void setTotalPageNum(long totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public void resetTotalResultSize(Page<T> pageConfig, long totalResultSize) {
        if (totalResultSize <= 0L) {
            return;
        }
        pageConfig.setTotalResultSize(totalResultSize);
        long totoalNumCount = getTotalPages();
        pageConfig.setTotalPageNum(totoalNumCount);
    }

    public long getEndRows() {
        long num = 0L;
        if (this.currentPage == this.totalPageNum)
            num = getTotalResultSize();
        else {
            num = getStartRows() + getPageSize() - 1L;
        }
        return num;
    }

    public String getPagination() {
        return pagination(10);
    }

    public String getAsyncPagination() {
        return paginationAsync(1);
    }

    public String pagination(int type) {
        Pagination p = new Pagination(getPageSize(), getTotalPageNum(),
                getUrl(), getCurrentPage());
        p.setMode(type);
        p.setTotalResultSize(this.totalResultSize);
        if (this.ajaxFlag) {
            p.setType("ajax");
        }
        return p.doStartTag();
    }

    public String paginationAsync(int type) {
        Pagination p = new Pagination(getPageSize(), getTotalPageNum(),
                getUrl(), getCurrentPage());
        p.setMode(type);
        p.setTotalResultSize(this.totalResultSize);
        if (this.ajaxFlag) {
            p.setType("ajax");
        }
        return p.doAsyncStartTag();
    }

    public String getCurrentPageParamName() {
        return this.currentPageParamName;
    }

    public void setCurrentPageParamName(String currentPageParamName) {
        this.currentPageParamName = currentPageParamName;
    }

    public String getActionType() {
        return this.actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public void setPage(Long page) {
        this.currentPage = page.longValue();
    }

    public Long getPage() {
        return Long.valueOf(this.currentPage);
    }

    public boolean hasNext() {
        long totalPage = getTotalPages();
        return (totalPage > this.currentPage);
    }

    public boolean hasPrev() {
        return (this.currentPage > 1L);
    }

    public List<T> getAllItems() {
        return this.allItems;
    }

    public void setAllItems(List<T> allItems) {
        this.allItems = allItems;
    }

    public int getCurrentRowNum() {
        Long num = Long.valueOf(0L);
        if (this.currentPage == this.totalPageNum) {
            num = Long.valueOf(getTotalResultSize());
        } else {
            num = Long.valueOf(getStartRows() - 1L + getPageSize());

            if (num.longValue() >= this.allItems.size()) {
                num = Long.valueOf(this.allItems.size() - 1L);
            }
        }
        return num.intValue();
    }

    public List<RequestKV> getParams() {
        return this.params;
    }

    public String getRequestURI() {
        return this.requestURI;
    }

    public static enum Page_Key {
        _pageSize, _start, _end, _orderby, _order;
    }
}