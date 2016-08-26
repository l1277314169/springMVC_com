/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.comm.pagination;

import com.comm.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Pagination {
    private static final Log logger = LogFactory.getLog(Pagination.class);
    private long page;
    String str = "";
    private long totalPage;
    private String action;
    private int mode;
    private long showTimes = 1L;
    private String type;
    private long pageSize;
    private String cssStyle;
    private long totalResultSize = 0L;
    private int[] pageSizeList = { 10, 20, 50, 100, 200 };
    private String currentPageParamName;
    private String pageName = "page";
    private String pageAName = "PageLink_page";
    private String url;
    private String param;
    private String other;

    public static String pagination(long pageSize, long totalPage, String url,
                                    long page) {
        Pagination pagination = new Pagination();
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(totalPage);
        pagination.setAction(url);
        pagination.setPage(page);
        pagination.setAction(url);
        pagination.setMode(10);
        return pagination.doStartTag();
    }

    public static String pagination(long pageSize, long totalPage, String url,
                                    long page, String type) {
        Pagination pagination = new Pagination();
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(totalPage);
        pagination.setAction(url);
        pagination.setPage(page);
        pagination.setAction(url);
        pagination.setMode(10);
        pagination.setType(type);
        return pagination.doStartTag();
    }

    public static String pagination(long pageSize, long totalPage, String url,
                                    long page, long totalResultSize) {
        Pagination pagination = new Pagination();
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(totalPage);
        pagination.setAction(url);
        pagination.setPage(page);
        pagination.setTotalResultSize(totalResultSize);
        pagination.setAction(url);
        pagination.setMode(10);
        return pagination.doStartTag();
    }

    public Pagination() {
    }

    public Pagination(long pageSize, long totalPage, String url, long page) {
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.action = url;
        this.page = page;
        this.mode = 10;
    }

    public static String pagination(Page<?> page) {
        if ((page != null) && (page.getTotalPageNum() > 0L)) {
            Pagination pagination = new Pagination();
            pagination.setPageSize(page.getPageSize());
            pagination.setTotalPage(page.getTotalPageNum());
            pagination.setAction(page.getUrl());
            pagination.setPage(page.getCurrentPage());
            pagination.setMode(12);
            pagination.setPageSizeParamName(page.getCurrentPageParamName());
            return pagination.doStartTag();
        }
        return "";
    }

    public static String pagePost(long pageSize, long totalPage, String url,
                                  long page) {
        Pagination pagination = new Pagination();
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(totalPage);
        pagination.setAction(url);
        pagination.setPage(page);
        pagination.setMode(12);
        pagination.setPageSizeParamName("page");
        return pagination.doStartTag();
    }

    public static String pageSupplier(Page<?> page, String other) {
        if ((page != null) && (page.getTotalPageNum() > 0L)) {
            Pagination pagination = new Pagination();
            pagination.setPageSize(page.getPageSize());
            pagination.setTotalPage(page.getTotalPageNum());
            pagination.setAction(page.getUrl());
            pagination.setPage(page.getCurrentPage());
            pagination.setTotalResultSize(page.getTotalResultSize());
            pagination.setMode(13);
            pagination.setPageSizeParamName(page.getCurrentPageParamName());
            pagination.setOther(other);
            return pagination.doStartTag();
        }
        return "";
    }

    public void setOther(String other) {
        this.other = other;
    }

    public long getTotalResultSize() {
        return this.totalResultSize;
    }

    public void setTotalResultSize(long totalResultSize) {
        this.totalResultSize = totalResultSize;
    }

    public long getPage() {
        return this.page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private void initPage() throws Exception {
        try {
            if (this.page == 0L) {
                this.page = 1L;
            }
            if (this.totalPage == 0L) {
                this.totalPage = 1L;
            }
            if (this.page < 1L) {
                this.page = 1L;
            }
            if (this.totalPage < 1L) {
                this.totalPage = 1L;
            }
            if (this.page > this.totalPage)
                this.page = this.totalPage;
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }

    public void createTag(int type) {
        try {
            long prevPage = this.page - 1L;
            long nextPage = this.page + 1L;
            long startPage = 0L;
            if (this.pageSize == 0L) {
                logger.error("pagination url:" + getAction()
                        + " set pageSize=0");
                this.pageSize = 1L;
            }
            if (this.page % this.pageSize == 0L)
                startPage = this.page - (this.pageSize - 1L);
            else {
                startPage = this.page - (this.page % this.pageSize) + 1L;
            }
            switch (type) {
                case 0:
                    this.str += "<span class='number'>";
                    if (prevPage < 1L) {
                        this.str += "<span title='到第一页'>&#171;</span>";
                        this.str += "<span title='上一页'>&#139;</span>";
                    } else {
                        Pagination tmp283_282 = this;
                        tmp283_282.str = tmp283_282.str
                                + "<span title='到第一页'><a href='" + toPage(1L)
                                + "'>&#171;</a></span>";
                        Pagination tmp322_321 = this;
                        tmp322_321.str = tmp322_321.str
                                + "<span title='上一页'><a href='" + toPage(prevPage)
                                + ");'>&#139;</a></span>";
                    }
                    for (long i = 1L; i <= this.totalPage; i += 1L) {
                        if (i > 0L) {
                            if (i == this.page) {
                                Pagination tmp391_390 = this;
                                tmp391_390.str = tmp391_390.str + "<span title='第 "
                                        + i + "页 '>[" + i + "]</span>";
                            } else {
                                Pagination tmp440_439 = this;
                                tmp440_439.str = tmp440_439.str + "<span title='第 "
                                        + i + "页'><a href='" + toPage(i) + "'>["
                                        + i + "]</a></span>";
                            }
                        }
                    }
                    if (nextPage > this.totalPage) {
                        this.str += "<span title='下页'>&#155;</span>";
                        this.str += "<span title='最后一页'>&#187;</span>";
                    } else {
                        Pagination tmp574_573 = this;
                        tmp574_573.str = tmp574_573.str
                                + "<span title='下一页'><a href='" + toPage(nextPage)
                                + "'>&#155;</a></span>";
                        Pagination tmp614_613 = this;
                        tmp614_613.str = tmp614_613.str
                                + "<span title='到最后一页'><a href='"
                                + toPage(this.totalPage)
                                + " ');'>&#187;</a></span>";
                    }
                    this.str += "</span><br/>";

                    break;
                case 1:
                    this.str += "<span class='number'>";
                    if (prevPage < 1L) {
                        this.str += "<span title='到第一页'>&#171;</span>";
                        this.str += "<span title='上一页'>&#139;</span>";
                    } else {
                        Pagination tmp772_771 = this;
                        tmp772_771.str = tmp772_771.str
                                + "<span title='到第一页'><a href='" + toPage(1L)
                                + "'>&#171;</a></span>";
                        Pagination tmp811_810 = this;
                        tmp811_810.str = tmp811_810.str
                                + "<span title='上一页'><a href='" + toPage(prevPage)
                                + " ');'>&#139;</a></span>";
                    }

                    if (startPage > this.pageSize) {
                        Pagination tmp860_859 = this;
                        tmp860_859.str = tmp860_859.str + "<span title='Prev "
                                + this.pageSize + " Pages'><a href='"
                                + toPage(startPage - 1L) + "'>上一页</a></span>";
                    }
                    for (long i = startPage; i < startPage + this.pageSize; i += 1L) {
                        if (i > this.totalPage) {
                            break;
                        }
                        if (i == this.page) {
                            Pagination tmp954_953 = this;
                            tmp954_953.str = tmp954_953.str + "<span title='第 " + i
                                    + "页'>[" + i + "]</span>";
                        } else {
                            Pagination tmp1003_1002 = this;
                            tmp1003_1002.str = tmp1003_1002.str
                                    + "<span title='Page " + i + "'><a href='"
                                    + toPage(i) + "'>[" + i + "]</a></span>";
                        }
                    }
                    if (this.totalPage >= startPage + this.pageSize) {
                        Pagination tmp1087_1086 = this;
                        tmp1087_1086.str = tmp1087_1086.str + "<span title='Next "
                                + this.pageSize + " Pages'><a href='"
                                + toPage(startPage + this.pageSize)
                                + "'>下一页</a></span>";
                    }
                    if (nextPage > this.totalPage) {
                        this.str += "<span title='下页'>&#155;</span>";
                        this.str += "<span title='最后一页'>&#187;</span>";
                    } else {
                        Pagination tmp1209_1208 = this;
                        tmp1209_1208.str = tmp1209_1208.str
                                + "<span title='下一页'><a href='" + toPage(nextPage)
                                + "'>&#155;</a></span>";
                        Pagination tmp1249_1248 = this;
                        tmp1249_1248.str = tmp1249_1248.str
                                + "<span title='到最后一页'><a href='"
                                + toPage(this.totalPage)
                                + " ');'>&#187;</a></span>";
                    }
                    this.str += "</span><br/>";
                    break;
                case 2:
                    long endPage = 0L;
                    this.str += "<span class='number'>";
                    if (prevPage < 1L) {
                        this.str += "<span title='到第一页'>&#171;</span>";
                        this.str += "<span title='上一页'>&#139;</span>";
                    } else {
                        Pagination tmp1410_1409 = this;
                        tmp1410_1409.str = tmp1410_1409.str
                                + "<span title='到第一页'><a href='" + toPage(1L)
                                + "'>&#171;</a></span>";
                        Pagination tmp1449_1448 = this;
                        tmp1449_1448.str = tmp1449_1448.str
                                + "<span title='上一页'><a href='" + toPage(prevPage)
                                + " ');'>&#139;</a></span>";
                    }
                    if (this.page != 1L) {
                        Pagination tmp1497_1496 = this;
                        tmp1497_1496.str = tmp1497_1496.str
                                + "<span title='到第一页'><a href='" + toPage(1L)
                                + "'>[1]</a></span>";
                    }

                    if (this.page >= 5L) {
                        this.str += "<span>...</span>";
                    }
                    if (this.totalPage > this.page + 2L)
                        endPage = this.page + 2L;
                    else {
                        endPage = this.totalPage;
                    }
                    for (long i = this.page - 2L; i <= endPage; i += 1L) {
                        if (i > 0L) {
                            if (i == this.page) {
                                Pagination tmp1643_1642 = this;
                                tmp1643_1642.str = tmp1643_1642.str
                                        + "<span title='第 " + i + "页'>[" + i
                                        + "]</span>";
                            } else if ((i != 1L) && (i != this.totalPage)) {
                                Pagination tmp1709_1708 = this;
                                tmp1709_1708.str = tmp1709_1708.str
                                        + "<span title='Page " + i + "'><a href='"
                                        + toPage(i) + "'>[" + i + "]</a></span>";
                            }
                        }
                    }

                    if (this.page + 3L < this.totalPage) {
                        this.str += "<span>...</span>";
                    }
                    if (this.page != this.totalPage) {
                        Pagination tmp1832_1831 = this;
                        tmp1832_1831.str = tmp1832_1831.str + "<span title= '第 "
                                + this.totalPage + "'><a href='"
                                + toPage(this.totalPage) + "');'>["
                                + this.totalPage + "]</a></span>";
                    }

                    if (nextPage > this.totalPage) {
                        this.str += "<span title='下页'>&#155;</span>";
                        this.str += "<span title='最后一页'>&#187;</span>";
                    } else {
                        Pagination tmp1963_1962 = this;
                        tmp1963_1962.str = tmp1963_1962.str
                                + "<span title='下一页'><a href='" + toPage(nextPage)
                                + "'>&#155;</a></span>";
                        Pagination tmp2003_2002 = this;
                        tmp2003_2002.str = tmp2003_2002.str
                                + "<span title='到最后一页'><a href='"
                                + toPage(this.totalPage)
                                + " ');'>&#187;</a></span>";
                    }
                    this.str += "</span><br/>";
                    break;
                case 3:
                    this.str += "<style type=\"text/css\">.p-unsele-page {font-size:12px;border:1px solid #ccc;}</style>";
                    this.str += "<table id =\"pag\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
                    this.str += "<tr>";

                    if (this.page == 1L) {
                        this.str += "<td width=\"20\" height=\"20\">";
                        this.str += "<button class=\"page_prew\"></button>";
                        this.str += "</td>";
                    } else {
                        this.str += "<td width=\"20\" height=\"20\">";
                        Pagination tmp2268_2267 = this;
                        tmp2268_2267.str = tmp2268_2267.str
                                + "<button class=\"page_prew\" onclick=\"window.location='"
                                + toPage(prevPage) + "'" + "\"></button>";
                        this.str += "</td>";
                    }
                    if ((this.page > 5L) && (this.page + 5L >= this.totalPage)) {
                        this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                        Pagination tmp2391_2390 = this;
                        tmp2391_2390.str = tmp2391_2390.str
                                + "<a class=\"user_vip_page\"  href=" + toPage(1L)
                                + " title=\"第" + 1 + "页\">" + 1 + "</a>";
                        this.str += "</td>";
                        this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                        this.str += "...";
                        this.str += "</td>";
                        if (this.page - 5L >= 1L) {
                            for (long i = this.page - 5L; i <= this.totalPage; i += 1L) {
                                this.str += "<td width=\"20\" height=\"20\" align=\"center\" class=\"p-unsele-page\">";
                                if (this.page == i) {
                                    Pagination tmp2621_2620 = this;
                                    tmp2621_2620.str = tmp2621_2620.str
                                            + "<font color=\"red\">" + i
                                            + "</font>";
                                } else {
                                    Pagination tmp2660_2659 = this;
                                    tmp2660_2659.str = tmp2660_2659.str
                                            + "<a class=\"user_vip_page\"  href="
                                            + toPage(i) + "" + " title=\"第" + i
                                            + "页\">" + i + "</a>";
                                }
                                this.str += "</td>";
                            }

                            if (this.page + 5L == this.totalPage - 1L) {
                                this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                                Pagination tmp2804_2803 = this;
                                tmp2804_2803.str = tmp2804_2803.str
                                        + "<a class=\"user_vip_page\" href="
                                        + toPage(this.totalPage) + ""
                                        + " title=\"第" + this.totalPage + "页\">"
                                        + this.totalPage + "</a>";
                                this.str += "</td>";
                            } else if (this.page != this.totalPage) {
                                if (this.page + 5L < this.totalPage) {
                                    this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                                    this.str += "...";
                                    this.str += "</td>";
                                    this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                                    Pagination tmp3042_3041 = this;
                                    tmp3042_3041.str = tmp3042_3041.str
                                            + "<a class=\"user_vip_page\" href="
                                            + toPage(this.totalPage) + ""
                                            + " title=\"第" + this.totalPage
                                            + "页\">" + this.totalPage + "</a>";
                                    this.str += "</td>";
                                }
                            }
                        } else if (this.page + this.pageSize <= this.totalPage) {
                            if (this.page - 5L >= 1L) {
                                for (long i = this.page - 5L; i <= this.page
                                        + this.pageSize; i += 1L) {
                                    this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                                    if (i == this.page) {
                                        Pagination tmp3233_3232 = this;
                                        tmp3233_3232.str = tmp3233_3232.str
                                                + "<font color=\"red\">" + i
                                                + "</font>";
                                    } else {
                                        Pagination tmp3272_3271 = this;
                                        tmp3272_3271.str = tmp3272_3271.str
                                                + "<a class=\"user_vip_page\" href="
                                                + toPage(i) + "" + " title=\"第" + i
                                                + "页\">" + i + "</a>";
                                    }
                                    this.str += "</td>";
                                }
                            } else {
                                for (long i = 1L; i <= this.page + this.pageSize; i += 1L) {
                                    this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                                    if (i == this.page) {
                                        Pagination tmp3429_3428 = this;
                                        tmp3429_3428.str = tmp3429_3428.str
                                                + "<font color=\"red\">" + i
                                                + "</font>";
                                    } else {
                                        Pagination tmp3468_3467 = this;
                                        tmp3468_3467.str = tmp3468_3467.str
                                                + "<a class=\"user_vip_page\" href="
                                                + toPage(i) + "" + " title=\"第" + i
                                                + "页\">" + i + "</a>";
                                    }
                                    this.str += "</td>";
                                }
                            }

                            if (this.page + this.pageSize != this.totalPage) {
                                this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                                this.str += "...";
                                this.str += "</td>";
                                this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                                Pagination tmp3689_3688 = this;
                                tmp3689_3688.str = tmp3689_3688.str
                                        + "<a class=\"user_vip_page\" href="
                                        + toPage(this.totalPage) + ""
                                        + " title=\"第" + this.totalPage + "页\">"
                                        + this.totalPage + "</a>";
                                this.str += "</td>";
                            }
                        } else {
                            for (long i = 1L; i <= this.totalPage; i += 1L) {
                                if (this.page == i) {
                                    this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                                    Pagination tmp3838_3837 = this;
                                    tmp3838_3837.str = tmp3838_3837.str
                                            + "<font color=\"red\">" + i
                                            + "</font>";
                                    this.str += "</td>";
                                } else {
                                    this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                                    Pagination tmp3929_3928 = this;
                                    tmp3929_3928.str = tmp3929_3928.str
                                            + "<a class=\"user_vip_page\" href="
                                            + toPage(i) + "" + " title=\"第" + i
                                            + "页\">" + i + "</a>";
                                    this.str += "</td>";
                                }

                            }

                        }

                    } else if (this.page - 5L >= 3L) {
                        this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                        Pagination tmp4073_4072 = this;
                        tmp4073_4072.str = tmp4073_4072.str
                                + "<a class=\"user_vip_page\"  href=" + toPage(1L)
                                + " title=\"第" + 1 + "页\">" + 1 + "</a>";
                        this.str += "</td>";
                        this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                        this.str += "...";
                        this.str += "</td>";
                        for (long i = this.page - 5L; i <= this.page + 5L; i += 1L) {
                            this.str += "<td width=\"20\" class=\"p-unsele-page\" height=\"20\" align=\"center\" >";
                            if (this.page == i) {
                                Pagination tmp4294_4293 = this;
                                tmp4294_4293.str = tmp4294_4293.str
                                        + "<font color=\"red\">" + i + "</font>";
                            } else {
                                Pagination tmp4333_4332 = this;
                                tmp4333_4332.str = tmp4333_4332.str
                                        + "<a class=\"user_vip_page\" href="
                                        + toPage(i) + " title=\"第" + i + "页\">" + i
                                        + "</a>";
                            }
                            this.str += "</td>";
                        }

                        if (this.page + 5L == this.totalPage - 1L) {
                            this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                            Pagination tmp4472_4471 = this;
                            tmp4472_4471.str = tmp4472_4471.str
                                    + "<a class=\"user_vip_page\" href="
                                    + toPage(this.totalPage) + "" + " title=\"第"
                                    + this.totalPage + "页\">" + this.totalPage
                                    + "</a>";
                            this.str += "</td>";
                        } else if (this.page != this.totalPage) {
                            if (this.page + 5L < this.totalPage) {
                                this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                                this.str += "...";
                                this.str += "</td>";
                                this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                                Pagination tmp4710_4709 = this;
                                tmp4710_4709.str = tmp4710_4709.str
                                        + "<a class=\"user_vip_page\" href="
                                        + toPage(this.totalPage) + ""
                                        + " title=\"第" + this.totalPage + "页\">"
                                        + this.totalPage + "</a>";
                                this.str += "</td>";
                            }
                        }
                    } else if (this.page + this.pageSize <= this.totalPage) {
                        if (this.page - 5L >= 1L) {
                            for (long i = this.page - 5L; i <= this.page
                                    + this.pageSize; i += 1L) {
                                this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                                if (i == this.page) {
                                    Pagination tmp4901_4900 = this;
                                    tmp4901_4900.str = tmp4901_4900.str
                                            + "<font color=\"red\">" + i
                                            + "</font>";
                                } else {
                                    Pagination tmp4940_4939 = this;
                                    tmp4940_4939.str = tmp4940_4939.str
                                            + "<a class=\"user_vip_page\" href="
                                            + toPage(i) + "" + " title=\"第" + i
                                            + "页\">" + i + "</a>";
                                }
                                this.str += "</td>";
                            }
                        } else {
                            for (long i = 1L; i <= this.page + this.pageSize; i += 1L) {
                                this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                                if (i == this.page) {
                                    Pagination tmp5097_5096 = this;
                                    tmp5097_5096.str = tmp5097_5096.str
                                            + "<font color=\"red\">" + i
                                            + "</font>";
                                } else {
                                    Pagination tmp5136_5135 = this;
                                    tmp5136_5135.str = tmp5136_5135.str
                                            + "<a class=\"user_vip_page\" href="
                                            + toPage(i) + "" + " title=\"第" + i
                                            + "页\">" + i + "</a>";
                                }
                                this.str += "</td>";
                            }
                        }

                        if (this.page + this.pageSize != this.totalPage) {
                            this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                            this.str += "...";
                            this.str += "</td>";
                            this.str += "<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
                            Pagination tmp5357_5356 = this;
                            tmp5357_5356.str = tmp5357_5356.str
                                    + "<a class=\"user_vip_page\" href="
                                    + toPage(this.totalPage) + "" + " title=\"第"
                                    + this.totalPage + "页\">" + this.totalPage
                                    + "</a>";
                            this.str += "</td>";
                        }
                    } else {
                        for (long i = 1L; i <= this.totalPage; i += 1L) {
                            if (this.page == i) {
                                this.str += "<td width=\"20\" class=\"p-unsele-page\" height=\"20\" align=\"center\" >";
                                Pagination tmp5506_5505 = this;
                                tmp5506_5505.str = tmp5506_5505.str
                                        + "<font color=\"red\">" + i + "</font>";
                                this.str += "</td>";
                            } else {
                                this.str += "<td width=\"20\" class=\"p-unsele-page\" height=\"20\" align=\"center\" >";
                                Pagination tmp5597_5596 = this;
                                tmp5597_5596.str = tmp5597_5596.str
                                        + "<a class=\"user_vip_page\" href="
                                        + toPage(i) + "" + " title=\"第" + i
                                        + "页\">" + i + "</a>";
                                this.str += "</td>";
                            }

                        }

                    }

                    if (this.page != this.totalPage) {
                        this.str += "<td width=\"20\" height=\"20\">";
                        Pagination tmp5735_5734 = this;
                        tmp5735_5734.str = tmp5735_5734.str
                                + "<button class=\"page_next\" onclick=\"window.location='"
                                + toPage(nextPage) + "" + "'\"></button>";
                        this.str += "</td>";
                        this.str += "<td width=\"40\" height=\"20\">到第</td>";
                        this.str += "<td width=\"20\" height=\"20\"><input id=\"page\" name=\"textfield2\" type=\"text\" class=\"writein_field\" size=\"2\" /></td>";
                        this.str += "<td width=\"20\" height=\"20\">页</td>";
                        Pagination tmp5884_5883 = this;
                        tmp5884_5883.str = tmp5884_5883.str
                                + " <td  height=\"20\"><button class=\"page_fir\" align=\"left\" onclick=\"toPage('"
                                + this.action + "')\"></button></td>";
                    } else {
                        this.str += "<td width=\"20\" height=\"20\">";
                        this.str += "<button class=\"page_next\"></button>";
                        this.str += "</td>";
                        this.str += "<td width=\"40\" height=\"20\">到第</td>";
                        this.str += "<td width=\"20\" height=\"20\"><input id=\"page\" name=\"textfield2\" type=\"text\" class=\"writein_field\" size=\"2\" /></td>";
                        this.str += "<td width=\"20\" height=\"20\">页</td>";
                        Pagination tmp6081_6080 = this;
                        tmp6081_6080.str = tmp6081_6080.str
                                + " <td  height=\"20\" align=\"left\"><button class=\"page_fir\" onclick=\"toPage('"
                                + this.action + "')\"></button></td>";
                    }

                    this.str += "</tr>";
                    this.str += "</table>";
                    break;
                case 4:
                    this.str += "<span class='number'>";
                    if (prevPage < 1L) {
                        this.str += "<span title='到第一页'><strong><<</strong></span>";
                        this.str += "<span title='上一页'><strong><</strong> </span>";
                    } else {
                        Pagination tmp6261_6260 = this;
                        tmp6261_6260.str = tmp6261_6260.str
                                + "<span title='到第一页'><a href='" + toPage(1L)
                                + "'><strong><<</strong></a></span>";
                        Pagination tmp6300_6299 = this;
                        tmp6300_6299.str = tmp6300_6299.str
                                + "<span title='上一页'><a href='" + toPage(prevPage)
                                + "'><font color='red'> < </font></a></span>";
                    }
                    if (nextPage > this.totalPage) {
                        this.str += "<span title='下页'><strong>></strong></span>";
                        this.str += "<span title='最后一页'><strong>>></strong></span>";
                    } else {
                        Pagination tmp6404_6403 = this;
                        tmp6404_6403.str = tmp6404_6403.str
                                + "<span title='下一页'><a href='" + toPage(nextPage)
                                + "'><font color='red'> > </font></a></span>";
                        Pagination tmp6444_6443 = this;
                        tmp6444_6443.str = tmp6444_6443.str
                                + "<span title='最后一页'><a href='"
                                + toPage(this.totalPage)
                                + "'><strong>>></strong></a></span>";
                    }
                    this.str += "</span><br/>";
                    break;
                case 5:
                    if (this.totalPage < 1L) {
                        this.str += "<select name='toPage' disabled>";
                        this.str += "<option value='0'>No Pages</option>";
                    } else {
                        this.str += "<select name='toPage' onchange='location=this.options[this.selectedIndex].value'>";
                        for (long i = 1L; i <= this.totalPage; i += 1L) {
                            String chkSelect;
                            if (this.page == i)
                                chkSelect = " selected='selected'";
                            else
                                chkSelect = "";
                            Pagination tmp6639_6638 = this;
                            tmp6639_6638.str = tmp6639_6638.str + "<option value='"
                                    + toPage(i) + "'" + chkSelect + ">到:  " + i
                                    + " /  " + this.totalPage + "页</option>";
                        }
                    }
                    this.str += "</select>";
                    break;
                case 6:
                    this.str += "<span class='input'>";
                    if (this.totalPage < 1L) {
                        this.str += "<input type='text' name='toPage' value='No Pages' class='itext' disabled='disabled'>";
                        this.str += "<input type='button' name='go' value='GO' class='ibutton' disabled='disabled'></option>";
                    } else {
                        this.str += "<input type='text' value='输入页码:' class='ititle' readonly='readonly'>";
                        Pagination tmp6865_6864 = this;
                        tmp6865_6864.str = tmp6865_6864.str
                                + "<input type='text' id='pageInput"
                                + this.showTimes
                                + " + ' value='"
                                + this.page
                                + "' class='itext' title='Input page'  onfocus='this.select()'>";
                        Pagination tmp6915_6914 = this;
                        tmp6915_6914.str = tmp6915_6914.str
                                + "<input type='text' value=' / " + this.totalPage
                                + "' class='icount' readonly='readonly'>";
                        Pagination tmp6953_6952 = this;
                        tmp6953_6952.str = tmp6953_6952.str
                                + "<input type='button' name='go' value='GO' class='ibutton' onclick='location='"
                                + toPage(1L) + "'></input>";
                    }
                    this.str += "</span>";
                    break;
                case 7:
                    this.str += "<span class='number'>";
                    if (this.totalPage <= 1L) {
                        this.str += "<span title=\"第一页\">第一页</span>";
                    } else if (this.page == 1L) {
                        this.str += "<span title=\"第一页\">第一页</span>";
                        Pagination tmp7120_7119 = this;
                        tmp7120_7119.str = tmp7120_7119.str
                                + "<span>&nbsp;&nbsp;|&nbsp;&nbsp;" + this.page
                                + "&nbsp;&nbsp;<a href='" + toPage(this.page + 1L)
                                + "'>" + (this.page + 1L)
                                + "</a>&nbsp;&nbsp;|&nbsp;&nbsp;</span>";
                        Pagination tmp7190_7189 = this;
                        tmp7190_7189.str = tmp7190_7189.str
                                + "<span title=\"下一页\"><a href='"
                                + toPage(nextPage) + "'>下一页</a></span>";

                        this.str += "<select name=\"toPage\" onchange='location=this.options[this.selectedIndex].value'>";
                        for (long i = 1L; i <= this.totalPage; i += 1L) {
                            String chkSelect;
                            if (this.page == i) {
                                chkSelect = " selected=\"selected\"";
                            } else
                                chkSelect = "";
                            Pagination tmp7290_7289 = this;
                            tmp7290_7289.str = tmp7290_7289.str + "<option value='"
                                    + toPage(i) + "'" + chkSelect + ">到:  " + i
                                    + " /  " + this.totalPage + "页</option>";
                        }

                        this.str += "</select>";
                    } else if ((this.page > 1L) && (this.page < this.totalPage)) {
                        Pagination tmp7421_7420 = this;
                        tmp7421_7420.str = tmp7421_7420.str
                                + "<span title=\"上一页\"><a href='"
                                + toPage(prevPage) + "'>上一页</a></span>";
                        Pagination tmp7460_7459 = this;
                        tmp7460_7459.str = tmp7460_7459.str
                                + "<span>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='"
                                + toPage(this.page - 1L) + "'>" + (this.page - 1L)
                                + "</a>&nbsp;&nbsp;" + this.page
                                + "&nbsp;&nbsp;<a href='" + toPage(this.page + 1L)
                                + "'>" + (this.page + 1L)
                                + "</a>&nbsp;&nbsp;|&nbsp;&nbsp;</span>";
                        Pagination tmp7562_7561 = this;
                        tmp7562_7561.str = tmp7562_7561.str
                                + "<span title=\"下一页\"><a href='"
                                + toPage(nextPage) + "'>下一页</a></span>";

                        this.str += "<select name=\"toPage\" onchange='location=this.options[this.selectedIndex].value'>";
                        for (long i = 1L; i <= this.totalPage; i += 1L) {
                            String chkSelect;
                            if (this.page == i)
                                chkSelect = " selected=\"selected\"";
                            else
                                chkSelect = "";
                            Pagination tmp7662_7661 = this;
                            tmp7662_7661.str = tmp7662_7661.str + "<option value='"
                                    + toPage(i) + "'" + chkSelect + ">到  " + i
                                    + " /  " + this.totalPage + "页</option>";
                        }

                        this.str += "</select>";
                    } else if (this.page == this.totalPage) {
                        Pagination tmp7784_7783 = this;
                        tmp7784_7783.str = tmp7784_7783.str
                                + "<span title=\"上一页\"><a href='"
                                + toPage(prevPage) + "'>上一页</a></span>";
                        Pagination tmp7823_7822 = this;
                        tmp7823_7822.str = tmp7823_7822.str
                                + "<span>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='"
                                + toPage(this.page - 1L) + "'>" + (this.page - 1L)
                                + "</a>&nbsp;&nbsp;" + this.page
                                + "&nbsp;&nbsp;|&nbsp;&nbsp;</span>";

                        this.str += "<span title=\"最后一页\">最后一页</span>";

                        this.str += "<select name=\"toPage\" onchange='location=this.options[this.selectedIndex].value'>";
                        for (long i = 1L; i <= this.totalPage; i += 1L) {
                            String chkSelect;
                            if (this.page == i)
                                chkSelect = " selected=\"selected\"";
                            else
                                chkSelect = "";
                            Pagination tmp7979_7978 = this;
                            tmp7979_7978.str = tmp7979_7978.str + "<option value='"
                                    + toPage(i) + "'" + chkSelect + ">到  " + i
                                    + " /  " + this.totalPage + "页</option>";
                        }

                        this.str += "</select>";
                    }
                    break;
                case 8:
                    if (startPage > this.pageSize) {
                        Pagination tmp8099_8098 = this;
                        tmp8099_8098.str = tmp8099_8098.str
                                + " <span id='pages' title='Prev " + this.pageSize
                                + " Pages'><a href='" + toPage(startPage - 1L)
                                + "'>上一页</a></span>";
                    }
                    for (long i = startPage; i < startPage + this.pageSize; i += 1L) {
                        if (i > this.totalPage) {
                            break;
                        }
                        if (i == this.page) {
                            Pagination tmp8193_8192 = this;
                            tmp8193_8192.str = tmp8193_8192.str
                                    + "<span id='currentPage' title='Page " + i
                                    + "'>" + i + "</span>";
                        } else {
                            Pagination tmp8242_8241 = this;
                            tmp8242_8241.str = tmp8242_8241.str
                                    + "<span id='test' title='Page " + i
                                    + "'><a href='" + toPage(i) + "'>" + i
                                    + "</a></span>";
                        }

                    }

                    if (this.totalPage >= startPage + this.pageSize) {
                        Pagination tmp8326_8325 = this;
                        tmp8326_8325.str = tmp8326_8325.str
                                + "<span id='pages' title='Next " + this.pageSize
                                + " Pages'><a href='"
                                + toPage(startPage + this.pageSize)
                                + "')'>下一页</a></span>";
                        this.str += "</span><br/>";
                    }
                    break;
                case 9:
                    long endPage1 = 0L;
                    this.str += "<span class='number'>";
                    if (prevPage < 1L) {
                        this.str += "<span id=\"last\" title='第一页'>第一页</span>";
                        this.str += "<span id=\"last\" title='上一页'>上一页</span>";
                    } else {
                        Pagination tmp8502_8501 = this;
                        tmp8502_8501.str = tmp8502_8501.str
                                + "<span id=\"pages\"  title='到第一页'><a href='"
                                + toPage(1L) + "'>首页</a></span>";
                        Pagination tmp8541_8540 = this;
                        tmp8541_8540.str = tmp8541_8540.str
                                + "<span id=\"pages\" title='上一页'><a href='"
                                + toPage(prevPage) + " ');'>上一页</a></span>";
                    }
                    if (this.page != 1L) {
                        Pagination tmp8589_8588 = this;
                        tmp8589_8588.str = tmp8589_8588.str
                                + "<span id=\"test\" title='到第一页'><a href='"
                                + toPage(1L) + "'>1</a></span>";
                    }

                    if (this.page >= 5L) {
                        this.str += "<span id=\"test\">......</span>";
                    }
                    if (this.totalPage > this.page + 2L)
                        endPage1 = this.page + 2L;
                    else {
                        endPage1 = this.totalPage;
                    }
                    for (long i = this.page - 2L; i <= endPage1; i += 1L) {
                        if (i > 0L) {
                            if (i == this.page) {
                                Pagination tmp8735_8734 = this;
                                tmp8735_8734.str = tmp8735_8734.str
                                        + "<span id=\"currentPage\" title='第 " + i
                                        + "页'>" + i + "</span>";
                            } else if ((i != 1L) && (i != this.totalPage)) {
                                Pagination tmp8801_8800 = this;
                                tmp8801_8800.str = tmp8801_8800.str
                                        + "<span id=\"test\" title='第 " + i
                                        + "页'><a href='" + toPage(i) + "'>" + i
                                        + "</a></span>";
                            }
                        }
                    }

                    if (this.page + 3L < this.totalPage) {
                        this.str += "<span id=\"test\">......</span>";
                    }
                    if (this.page != this.totalPage) {
                        Pagination tmp8924_8923 = this;
                        tmp8924_8923.str = tmp8924_8923.str
                                + "<span id=\"test\" title= '第 " + this.totalPage
                                + "页'><a href='" + toPage(this.totalPage) + "');'>"
                                + this.totalPage + "</a></span>";
                    }

                    if (nextPage > this.totalPage) {
                        this.str += "<span id=\"last\" title='下页'>下页</span>";
                        this.str += "<span id=\"last\"  title='最后一页'>最后一页</span>";
                    } else {
                        Pagination tmp9055_9054 = this;
                        tmp9055_9054.str = tmp9055_9054.str
                                + "<span id=\"pages\"  title='下一页'><a href='"
                                + toPage(nextPage) + "'>下一页</a></span>";
                        Pagination tmp9095_9094 = this;
                        tmp9095_9094.str = tmp9095_9094.str
                                + "<span id=\"toLast\" title='最后一页'><a href='"
                                + toPage(this.totalPage) + " ');'>最后一页</a></span>";
                    }
                    this.str += "</span><br/>";
                    break;
                case 10:
                    if (StringUtils.isNotBlank(this.action)) {
                        this.action = this.action.replaceAll("\"", "'");
                    }
                    this.str += "<div id=\"lv_page\">";
                    Pagination tmp9217_9216 = this;
                    tmp9217_9216.str = tmp9217_9216.str
                            + "<div class=\"Pages\" url='"
                            + this.action.replace("&page=", "") + "'>";
                    long endPage2 = 0L;
                    if (prevPage < 1L) {
                        this.str += "<a href=\"#\" title=\"上一页\" class=\"PrevPage\" >上一页</a>";
                    } else {
                        Pagination tmp9300_9299 = this;
                        tmp9300_9299.str = tmp9300_9299.str + "<a href=\""
                                + toPage(prevPage) + "\" page=\"" + prevPage
                                + "\" title=\"上一页\" class=\"PrevPage\" >上一页</a>";
                    }
                    if (this.page != 1L) {
                        Pagination tmp9357_9356 = this;
                        tmp9357_9356.str = tmp9357_9356.str + "<a href=\""
                                + toPage(1L) + "\" page=\"" + 1
                                + "\" title=\"到第一页\" class=\"PageLink\" >1</a>";
                    }

                    if (this.page >= 5L) {
                        this.str += "<span class=\"PageMore\">...</span>";
                    }
                    if (this.totalPage > this.page + 5L)
                        endPage2 = this.page + 5L;
                    else {
                        endPage2 = this.totalPage;
                    }
                    for (long i = this.page - 5L; i <= endPage2; i += 1L) {
                        if (i > 0L) {
                            if (i == this.page) {
                                Pagination tmp9512_9511 = this;
                                tmp9512_9511.str = tmp9512_9511.str
                                        + "<span class=\"PageSel\">" + i
                                        + "</span>";
                            } else if ((i != 1L) && (i != this.totalPage)) {
                                Pagination tmp9568_9567 = this;
                                tmp9568_9567.str = tmp9568_9567.str + "<a href=\""
                                        + toPage(i) + "\" page=\"" + i
                                        + "\" class=\"PageLink\" title='第 " + i
                                        + "页'>" + i + "</a>";
                            }
                        }
                    }

                    if (this.page + 3L < this.totalPage) {
                        this.str += "<span class=\"PageMore\">...</span>";
                    }
                    if (this.page != this.totalPage) {
                        Pagination tmp9701_9700 = this;
                        tmp9701_9700.str = tmp9701_9700.str + "<a  href=\""
                                + toPage(this.totalPage) + "\" page=\""
                                + this.totalPage
                                + "\" class=\"PageLink\" title= '第 "
                                + this.totalPage + "页'>" + this.totalPage + "</a>";
                    }

                    if (nextPage > this.totalPage) {
                        this.str += "<a href=\"#\" title=\"下一页\" class=\"NextPage\" >下一页</a>";
                    } else {
                        Pagination tmp9818_9817 = this;
                        tmp9818_9817.str = tmp9818_9817.str
                                + "<a href=\""
                                + toPage(nextPage)
                                + "\" page=\""
                                + nextPage
                                + "\" title=\"下一页\" class=\"NextPage PageLink PageLink_page\" >下一页</a>";
                    }
                    this.str += "</span><br/>";
                    this.str += "</div>";
                    this.str += "</div>";
                    break;
                case 11:
                    this.str += "<div class=\"page_div\">";
                    Pagination tmp9975_9974 = this;
                    tmp9975_9974.str = tmp9975_9974.str + "<span>总共 "
                            + this.totalResultSize + " 条记录 每页 " + this.pageSize
                            + " 条记录</span> ";
                    long endPage11 = 0L;
                    if (prevPage < 1L) {
                        this.str += "<a href=\"#\" title=\"上一页\" class=\"PrevPage\" >上一页</a>";
                    } else {
                        Pagination tmp10063_10062 = this;
                        tmp10063_10062.str = tmp10063_10062.str + "<a href='"
                                + toPage(prevPage)
                                + "' title=\"上一页\" class=\"PrevPage\" >上一页</a>";
                    }
                    if (this.page != 1L) {
                        Pagination tmp10111_10110 = this;
                        tmp10111_10110.str = tmp10111_10110.str + "<a href='"
                                + toPage(1L)
                                + "' title=\"到第一页\" class=\"PageLink\" >1</a>";
                    }

                    if (this.page >= 5L) {
                        this.str += "<span class=\"PageMore\">...</span>";
                    }
                    if (this.totalPage > this.page + 5L)
                        endPage11 = this.page + 5L;
                    else {
                        endPage11 = this.totalPage;
                    }
                    for (long i = this.page - 5L; i <= endPage11; i += 1L) {
                        if (i > 0L) {
                            if (i == this.page) {
                                Pagination tmp10257_10256 = this;
                                tmp10257_10256.str = tmp10257_10256.str
                                        + "<span class=\"PageSel\">&nbsp;&nbsp;"
                                        + i + "&nbsp;&nbsp;</span>";
                            } else if ((i != 1L) && (i != this.totalPage)) {
                                Pagination tmp10313_10312 = this;
                                tmp10313_10312.str = tmp10313_10312.str
                                        + "<a href='" + toPage(i)
                                        + "' class=\"PageLink\" title='第 " + i
                                        + "页'>" + i + "</a>";
                            }
                        }
                    }

                    if (this.page + 3L < this.totalPage) {
                        this.str += "<span class=\"PageMore\">...</span>";
                    }
                    if (this.page != this.totalPage) {
                        Pagination tmp10436_10435 = this;
                        tmp10436_10435.str = tmp10436_10435.str + "<a  href='"
                                + toPage(this.totalPage)
                                + "' class=\"PageLink\" title= '第 "
                                + this.totalPage + "页'>" + this.totalPage + "</a>";
                    }

                    if (nextPage > this.totalPage) {
                        this.str += "<a href=\"#\" title=\"下一页\" class=\"NextPage\" >下一页</a>";
                    } else {
                        Pagination tmp10541_10540 = this;
                        tmp10541_10540.str = tmp10541_10540.str
                                + "<a class=\"PageLink NextPage PageLink_page\" href='"
                                + toPage(nextPage) + " ' title=\"下一页\" >下一页</a>";
                    }
                    this.str += "<span> 显示<select name=\"changePageSize\" style=\"width:100px;\" onchange='location=this.options[this.selectedIndex].value'>";
                    String chkSelect11 = "";
                    for (int size : this.pageSizeList) {
                        if (this.pageSize == size)
                            chkSelect11 = " selected=\"selected\"";
                        else
                            chkSelect11 = "";
                        Pagination tmp10661_10660 = this;
                        tmp10661_10660.str = tmp10661_10660.str + "<option value='"
                                + toPageWithSize(size) + "'" + chkSelect11 + ">"
                                + size + "</option>";
                    }

                    this.str += "</select>条</span>";
                    break;
                case 12:
                    this.str = createFormPagination();
                    break;
                case 13:
                    this.str = createFormPagination2();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createAsyncTag(int type) {
        try {
            long prevPage = this.page - 1L;
            long nextPage = this.page + 1L;
            long startPage = 0L;
            if (this.pageSize == 0L) {
                logger.error("pagination url:" + getAction()
                        + " set pageSize=0");
                this.pageSize = 1L;
            }
            if (this.page % this.pageSize == 0L)
                startPage = this.page - (this.pageSize - 1L);
            else {
                startPage = this.page - (this.page % this.pageSize) + 1L;
            }
            switch (type) {
                case 0:
                    this.str += "<span class='number'>";
                    if (prevPage < 1L) {
                        this.str += "<span title='到第一页'>&#171;</span>";
                        this.str += "<span title='上一页'>&#139;</span>";
                    } else {
                        Pagination tmp283_282 = this;
                        tmp283_282.str = tmp283_282.str
                                + "<span title='到第一页'><a onclick='asyncQuery('"
                                + toPage(1L) + "')'>&#171;</a></span>";
                        Pagination tmp322_321 = this;
                        tmp322_321.str = tmp322_321.str
                                + "<span title='上一页'><a onclick='asyncQuery('"
                                + toPage(prevPage) + "');'>&#139;</a></span>";
                    }
                    for (long i = 1L; i <= this.totalPage; i += 1L) {
                        if (i > 0L) {
                            if (i == this.page) {
                                Pagination tmp391_390 = this;
                                tmp391_390.str = tmp391_390.str + "<span title='第 "
                                        + i + "页 '>[" + i + "]</span>";
                            } else {
                                Pagination tmp440_439 = this;
                                tmp440_439.str = tmp440_439.str + "<span title='第 "
                                        + i + "页'><a onclick='asyncQuery('"
                                        + toPage(i) + "')'>[" + i + "]</a></span>";
                            }
                        }
                    }
                    if (nextPage > this.totalPage) {
                        this.str += "<span title='下页'>&#155;</span>";
                        this.str += "<span title='最后一页'>&#187;</span>";
                    } else {
                        Pagination tmp574_573 = this;
                        tmp574_573.str = tmp574_573.str
                                + "<span title='下一页'><a onclick='asyncQuery('"
                                + toPage(nextPage) + "')'>&#155;</a></span>";
                        Pagination tmp614_613 = this;
                        tmp614_613.str = tmp614_613.str
                                + "<span title='到最后一页'><a onclick='asyncQuery('"
                                + toPage(this.totalPage) + "');'>&#187;</a></span>";
                    }
                    this.str += "</span><br/>";

                    break;
                case 1:
                    long endPage1 = 0L;
                    this.str += "<span class='number'>";
                    if (prevPage < 1L) {
                        this.str += "<span id=\"last\" title='第一页' >第一页</span>";
                        this.str += "<span id=\"last\" title='上一页' >上一页</span>";
                    } else {
                        Pagination tmp775_774 = this;
                        tmp775_774.str = tmp775_774.str
                                + "<span id=\"pages\" title='到第一页'><a style=\"border:1px solid #ddd;\" onclick=\"asyncQuery('"
                                + toPage(1L) + "')\">首页</a></span>";
                        Pagination tmp814_813 = this;
                        tmp814_813.str = tmp814_813.str
                                + "<span id=\"pages\" title='上一页'><a style=\"border:1px solid #ddd;\" onclick=\"asyncQuery('"
                                + toPage(prevPage) + "')\">上一页</a></span>";
                    }
                    if (this.page != 1L) {
                        Pagination tmp862_861 = this;
                        tmp862_861.str = tmp862_861.str
                                + "<span id=\"test\"  title='到第一页'><a style=\"border:1px solid #ddd;\" onclick=\"asyncQuery('"
                                + toPage(1L) + "')\">1</a></span>";
                    }

                    if (this.page >= 5L) {
                        this.str += "<span id=\"test\">......</span>";
                    }
                    if (this.totalPage > this.page + 2L)
                        endPage1 = this.page + 2L;
                    else {
                        endPage1 = this.totalPage;
                    }
                    for (long i = this.page - 2L; i <= endPage1; i += 1L) {
                        if (i > 0L) {
                            if (i == this.page) {
                                Pagination tmp1008_1007 = this;
                                tmp1008_1007.str = tmp1008_1007.str
                                        + "<span id=\"currentPage\" title='第 " + i
                                        + "页'>" + i + "</span>";
                            } else if ((i != 1L) && (i != this.totalPage)) {
                                Pagination tmp1074_1073 = this;
                                tmp1074_1073.str = tmp1074_1073.str
                                        + "<span id=\"test\" title='第 "
                                        + i
                                        + "页'><a  style=\"border:1px solid #ddd;\" onclick=\"asyncQuery('"
                                        + toPage(i) + "')\">" + i + "</a></span>";
                            }
                        }
                    }

                    if (this.page + 3L < this.totalPage) {
                        this.str += "<span id=\"test\">......</span>";
                    }
                    if (this.page != this.totalPage) {
                        Pagination tmp1197_1196 = this;
                        tmp1197_1196.str = tmp1197_1196.str
                                + "<span id=\"test\"  title= '第 "
                                + this.totalPage
                                + "页'><a style=\"border:1px solid #ddd;\" onclick=\"asyncQuery('"
                                + toPage(this.totalPage) + "')\">" + this.totalPage
                                + "</a></span>";
                    }

                    if (nextPage > this.totalPage) {
                        this.str += "<span id=\"last\"  title='下页'>下页</span>";
                        this.str += "<span id=\"last\"  title='最后一页'>最后一页</span>";
                    } else {
                        Pagination tmp1328_1327 = this;
                        tmp1328_1327.str = tmp1328_1327.str
                                + "<span id=\"pages\"  title='下一页'><a style=\"border:1px solid #ddd;\" onclick=\"asyncQuery('"
                                + toPage(nextPage) + "')\">下一页</a></span>";
                        Pagination tmp1368_1367 = this;
                        tmp1368_1367.str = tmp1368_1367.str
                                + "<span id=\"toLast\" title='最后一页'><a style=\"border:1px solid #ddd;\" onclick=\"asyncQuery('"
                                + toPage(this.totalPage) + "')\">最后一页</a></span>";
                    }
                    this.str += "</span><br/>";
                    break;
                case 2:
                    this.str += "<div id=\"lv_page\">";
                    Pagination tmp1465_1464 = this;
                    tmp1465_1464.str = tmp1465_1464.str
                            + "<div class=\"Pages\" url=\""
                            + this.action.replace("&page=", "") + "\">";
                    long endPage2 = 0L;
                    if (prevPage < 1L) {
                        this.str += "<a href=\"#\" title=\"上一页\" class=\"PrevPage\" >上一页</a>";
                    } else {
                        Pagination tmp1550_1549 = this;
                        tmp1550_1549.str = tmp1550_1549.str
                                + "<a  onclick=\"asyncQuery('" + toPage(prevPage)
                                + "')\" page=\"" + prevPage
                                + "\" title=\"上一页\" class=\"PrevPage\" >上一页</a>";
                    }
                    if (this.page != 1L) {
                        Pagination tmp1609_1608 = this;
                        tmp1609_1608.str = tmp1609_1608.str
                                + "<a  onclick=\"asyncQuery('" + toPage(1L)
                                + "')\"   page=\"" + 1
                                + "\" title=\"到第一页\" class=\"PageLink\" >1</a>";
                    }

                    if (this.page >= 5L) {
                        this.str += "<span class=\"PageMore\">...</span>";
                    }
                    if (this.totalPage > this.page + 5L)
                        endPage2 = this.page + 5L;
                    else {
                        endPage2 = this.totalPage;
                    }
                    for (long i = this.page - 5L; i <= endPage2; i += 1L) {
                        if (i > 0L) {
                            if (i == this.page) {
                                Pagination tmp1766_1765 = this;
                                tmp1766_1765.str = tmp1766_1765.str
                                        + "<span class=\"PageSel\">" + i
                                        + "</span>";
                            } else if ((i != 1L) && (i != this.totalPage)) {
                                Pagination tmp1822_1821 = this;
                                tmp1822_1821.str = tmp1822_1821.str
                                        + "<a onclick=\"asyncQuery('" + toPage(i)
                                        + "')\"  page=\"" + i
                                        + "\" class=\"PageLink\" title='第 " + i
                                        + "页'>" + i + "</a>";
                            }
                        }
                    }

                    if (this.page + 3L < this.totalPage) {
                        this.str += "<span class=\"PageMore\">...</span>";
                    }
                    if (this.page != this.totalPage) {
                        Pagination tmp1957_1956 = this;
                        tmp1957_1956.str = tmp1957_1956.str
                                + "<a  onclick=\"asyncQuery('"
                                + toPage(this.totalPage) + "')\"  page=\""
                                + this.totalPage
                                + "\" class=\"PageLink\" title= '第 "
                                + this.totalPage + "页'>" + this.totalPage + "</a>";
                    }

                    if (nextPage > this.totalPage) {
                        this.str += "<a href=\"#\" title=\"下一页\" class=\"NextPage\" >下一页</a>";
                    } else {
                        Pagination tmp2076_2075 = this;
                        tmp2076_2075.str = tmp2076_2075.str
                                + "<a onclick=\"asyncQuery('"
                                + toPage(nextPage)
                                + "')\"  page=\""
                                + nextPage
                                + "\" title=\"下一页\" class=\"NextPage PageLink PageLink_page\" >下一页</a>";
                    }
                    this.str += "</span><br/>";
                    this.str += "</div>";
                    this.str += "</div>";
                    break;
                case 12:
                    this.str = createFormPagination();
                    break;
                case 13:
                    this.str = createFormPagination2();
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String createParam(String param) {
        StringBuffer sb = new StringBuffer();
        if (!(param.isEmpty())) {
            String[] array = StringUtils.split(param, "&");
            for (String str : array) {
                String[] kv = str.split("=");
                if (kv == null)
                    continue;
                if (kv.length != 2) {
                    continue;
                }

                sb.append("<input type='hidden' name=\"");
                String key = kv[0];
                if (kv[0].contains("\"")) {
                    key = key.replaceAll("\"", "'");
                }
                sb.append(key);
                sb.append("\" value='");
                sb.append(kv[1]);
                sb.append("'/>");
            }
        }
        if ((this.currentPageParamName == null)
                || ("page".equals(this.currentPageParamName))) {
            sb.append("<input type='hidden' name='" + this.pageName
                    + "' value='");
        } else {
            sb.append("<input type='hidden' name='" + this.currentPageParamName
                    + "' value='");
            this.pageName = this.currentPageParamName;
            this.pageAName = this.currentPageParamName.replaceAll("\\.", "_");
        }
        sb.append(this.page);
        sb.append("'/>");
        return sb.toString();
    }

    private String createPage(String label, long page, boolean useLi) {
        StringBuffer sb = new StringBuffer();
        if (useLi) {
            sb.append("<li>");
        }
        sb.append("<a href='javascript:void(0)' title='");
        sb.append(label);
        sb.append("' class='");
        sb.append(this.pageAName);
        sb.append("' page='");
        sb.append(page);
        sb.append("'>");
        sb.append(label);
        sb.append("</a>");
        if (useLi) {
            sb.append("</li>");
        }
        return sb.toString();
    }

    private String createPage(long page) {
        return createPage(String.valueOf(page), page, false);
    }

    private boolean hasPrev() {
        return (this.page > 1L);
    }

    private boolean hasNext() {
        return (this.page < this.totalPage);
    }

    private String createFormPagination2() {
        StringBuffer sb = new StringBuffer();
        createAction();

        sb.append("<div class=\"paging graystyle\">");
        sb.append("<form action='");
        sb.append(this.url);
        sb.append("' method='post'>");
        sb.append(createParam(this.param));

        sb.append("<span class='gopage'>");
        sb.append("到第&nbsp;<input type=\"text\" class=\"input-text\" name='gogo'/>&nbsp;页&nbsp;");
        sb.append("<a href=\"javascript:void(0)\" type=\"button\" class=\"btn btn-gray btn-small "
                + this.pageAName
                + "\" total='"
                + this.totalPage
                + "' result='gogo'>确定</a></span>");
        sb.append("\t<div class=\"pagebox\">");
        sb.append("<span class=\"pageinfo\">共 <b class=\"num\">");
        sb.append(this.totalResultSize);
        sb.append("</b> 条记录，每页显示");
        sb.append(this.pageSize);
        sb.append("条记录</span>");
        sb.append(this.other);
        if (hasPrev()) {
            sb.append("<a href='javascript:void(0)' title='");
            sb.append("上一页");
            sb.append("' class='");
            sb.append(this.pageAName);
            sb.append("' page='");
            sb.append(this.page - 1L);
            sb.append("'>");
            sb.append("\t<i class='larr'></i>");
            sb.append("上一页");
            sb.append("</a>");
            sb.append("</i>");
        }
        long startPage = 1L;
        long endPage = this.totalPage;
        if (this.totalPage > 10L) {
            if (this.totalPage - this.page > 5L) {
                startPage = this.page - 5L;
                if (startPage < 1L) {
                    startPage = 1L;
                }
                endPage = startPage + 9L;
                if (endPage > this.totalPage)
                    endPage = this.totalPage;
            } else {
                endPage = this.totalPage;
                startPage = this.totalPage - 9L;
            }
        }
        for (long i = startPage; i <= endPage; i += 1L) {
            if (i == this.page) {
                sb.append("<span class=\"pagesel\">");
                sb.append(i);
                sb.append("</span>");
            } else {
                sb.append(createPage(i));
            }
        }
        sb.append("<span class='pagemore'>...</span>");
        if (hasNext()) {
            sb.append("<a href='javascript:void(0)' title='");
            sb.append("下一页");
            sb.append("' class='");
            sb.append(this.pageAName);
            sb.append("' page='");
            sb.append(this.page + 1L);
            sb.append("'>");
            sb.append("\t<i class='rarr'></i>");
            sb.append("下一页");
            sb.append("</a>");
            sb.append("</i>");
        }

        sb.append("</form>");
        sb.append("</div>");
        sb.append(createScript());
        return sb.toString();
    }

    private String createScript() {
        StringBuffer sb = new StringBuffer();

        sb.append("<script type='text/javascript'>");
        sb.append("$(function() {\t\t\t\t\t\t\t\t");
        sb.append(" function getParent($div, tagName) {\t\t\t");
        sb.append("\tvar $p = $div.parent();\t\t\t\t\t\t");
        sb.append("\tvar tmp = $p.get(0).tagName.toUpperCase();\t");
        sb.append("\tif (tmp == 'BODY') {\t\t\t\t\t\t");
        sb.append("\t\treturn null;\t\t\t\t\t\t\t");
        sb.append("\t}\t\t\t\t\t\t\t\t\t\t\t");
        sb.append("\tif (tmp == tagName.toUpperCase()) {\t\t\t");
        sb.append("\t\treturn $p;\t\t\t\t\t\t\t\t");
        sb.append("\t} else {\t\t\t\t\t\t\t\t\t");
        sb.append("\t\treturn getParent($p, tagName);\t\t\t");
        sb.append("\t}\t\t\t\t\t\t\t\t\t\t\t");
        sb.append("}\t\t\t\t\t\t\t\t\t\t\t\t");
        sb.append("\tfunction goPage($this,page) {\t\t\t\t");
        sb.append("\t\tvar $form = getParent($this, 'form');\t");
        sb.append("\t\t$form.find('input[name=" + this.pageName
                + "]').val(page);");
        sb.append("\t\t$form.submit();\t\t\t\t\t\t\t");
        sb.append("\t}\t\t\t\t\t\t\t\t\t\t\t");
        sb.append("\t$('a." + this.pageAName + "').click(function() {\t\t");
        sb.append("\t\tvar page = $(this).attr('page');\t\t");
        sb.append("\t\tvar result = $(this).attr('result');\t");
        sb.append("\t\tif (result != 'undefined' && result == 'gogo') {");
        sb.append("\t\t\tvar $form = getParent($(this), 'form');");
        sb.append("\t\t\tvar val = $form.find('input[name=gogo]').val();");
        sb.append("\t\t\tif ($.trim(val) == '') {\t\t\t");
        sb.append("\t\t\t\treturn false;\t\t\t\t\t");
        sb.append("\t\t\t}\t\t\t\t\t\t\t\t\t");
        sb.append("\t\t\tvar reg = /^(\\d+)$/;\t\t\t\t");
        sb.append("\t\t\tif(!reg.test(val)){return false;}\t\t");
        sb.append("\t\t\tpage = parseInt(val);\t\t\t\t");
        sb.append("\t\t\tvar totalPage=parseInt($(this).attr('total'));");
        sb.append("\t\t\tif (page > totalPage || page < 1) {\t");
        sb.append("\t\t\t\treturn false;\t\t\t\t\t");
        sb.append("\t\t\t}\t\t\t\t\t\t\t\t\t");
        sb.append("\t\t}\t\t\t\t\t\t\t\t\t\t");
        sb.append("\t\tgoPage($(this),page);\t\t\t\t\t");
        sb.append("\t});\t\t\t\t\t\t\t\t\t\t\t");
        sb.append("});\t\t\t\t\t\t\t\t\t\t\t\t");
        sb.append("</script>\t\t\t\t\t\t\t\t\t");
        return sb.toString();
    }

    private void createAction() {
        if (this.action == null) {
            this.action = "?";
        }
        int pos = this.action.indexOf("?");

        this.url = this.action;
        if (pos != -1) {
            this.url = this.action.substring(0, pos);
            this.param = this.action.substring(pos + 1);
        }
    }

    private String createFormPagination() {
        createAction();
        StringBuffer sb = new StringBuffer();
        sb.append("<style type='text/css'>span.PageSel,a.PageLink{margin:0 3px;}span.PageSel{font-weight: bold}</style>");
        sb.append("<form id='paginationForm' action='");
        sb.append(this.url);
        sb.append("' method='post'>");

        sb.append(createParam(this.param));

        long prevPage = this.page - 1L;
        long nextPage = this.page + 1L;
        if (this.page % this.pageSize == 0L)
            ;
        sb.append("<div id=\"lv_page\">");
        sb.append("<div class=\"Pages\">");
        long endPage2 = 0L;
        if (prevPage < 1L)
            sb.append("<a href='#' title='上一页' class='PrevPage'>上一页</a>");
        else {
            sb.append("<a href='#' title='上一页' class='PageLink PrevPage "
                    + this.pageAName + "' page='" + prevPage + "'>上一页</a>");
        }
        if (this.page != 1L) {
            sb.append("<a href='#' title='到第一页' class='PageLink "
                    + this.pageAName + "' page='1'>1</a>");
        }
        if (this.page >= 5L) {
            sb.append("<span class='PageMore'>...</span>");
        }
        if (this.totalPage > this.page + 5L)
            endPage2 = this.page + 5L;
        else {
            endPage2 = this.totalPage;
        }
        for (long i = this.page - 5L; i <= endPage2; i += 1L) {
            if (i > 0L) {
                if (i == this.page) {
                    sb.append("<span class='PageSel'>" + i + "</span>");
                } else if ((i != 1L) && (i != this.totalPage)) {
                    sb.append("<a href='#' class='PageLink " + this.pageAName
                            + "'  page='" + i + "' title='第" + i + "页'>" + i
                            + "</a>");
                }
            }
        }

        if (this.page + 3L < this.totalPage) {
            sb.append("<span class=\"PageMore\">...</span>");
        }
        if (this.page != this.totalPage) {
            sb.append("<a  href='#' page='" + this.totalPage
                    + "' class=\"PageLink " + this.pageAName + "\" title= '第"
                    + this.totalPage + "页'>" + this.totalPage + "</a>");
        }

        if (nextPage > this.totalPage)
            sb.append("<a href='#' title='下一页' class='NextPage'>下一页</a>");
        else {
            sb.append("<a href='#' title='下一页' class='NextPage PageLink "
                    + this.pageAName + "' page='" + nextPage + "' >下一页</a>");
        }
        sb.append("</span><br/>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</form>");
        sb.append(createScript());
        return sb.toString();
    }

    public String doStartTag() {
        try {
            initPage();
            Pagination tmp12_11 = this;
            tmp12_11.str = tmp12_11.str
                    + "<div id='pages_' class='pages' style='" + this.cssStyle
                    + "'> ";
            createTag(getMode());
            this.str += "</div>";
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return this.str;
    }

    public String doAsyncStartTag() {
        try {
            initPage();
            Pagination tmp12_11 = this;
            tmp12_11.str = tmp12_11.str
                    + "<div id='pages_' class='pages' style='" + this.cssStyle
                    + "'> ";
            createAsyncTag(getMode());
            this.str += "</div>";
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return this.str;
    }

    private String toPageWithSize(int size) {
        String url = toPage(1L);
        String params = url.substring(url.lastIndexOf("?") + 1);
        if (url.length() == params.length()) {
            url = url + "?pageSize=" + size;
            return url;
        }
        String psStr = "";
        for (String p : params.split("&")) {
            if (p.startsWith("pageSize=")) {
                psStr = p;
                break;
            }
        }
        if (!(psStr.equals("")))
            url = url.replace(psStr, "pageSize=" + size);
        else {
            url = url + "&pageSize=" + size;
        }
        return url;
    }

    private String toPage(long page) {
        String url = "";
        if ("htm".equals(this.type)) {
            url = this.action + "" + page + ".htm";
            return url;
        }
        if ("do".equals(this.type)) {
            if (this.action != null) {
                boolean containsPage = (this.action.contains("&page="))
                        || (this.action.contains("?page="));
                boolean containsQuestionMark = this.action.contains("?");
                if (!(containsQuestionMark)) {
                    url = this.action + "?page=" + page;
                } else if (containsPage)
                    url = this.action + page;
                else {
                    url = this.action + "&page=" + page;
                }
            }

            return url;
        }
        if ("js".equals(this.type))
            if (null != this.action) {
                url = this.action.replaceAll("argPage", String.valueOf(page));
                return url;
            } else if ("ajax".equals(this.type)) {
                return "javascript:void(0)";
            }

        url = this.action + page;

        return url;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public long getShowTimes() {
        return this.showTimes;
    }

    public void setShowTimes(long showTimes) {
        this.showTimes = showTimes;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public String getCssStyle() {
        return this.cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public String getPageSizeParamName() {
        return this.currentPageParamName;
    }

    public void setPageSizeParamName(String pageSizeParamName) {
        this.currentPageParamName = pageSizeParamName;
    }
}