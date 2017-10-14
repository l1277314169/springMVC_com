package cn.mobilizer.channel.sales.po;

import java.util.Date;

public class DimWeek extends DimWeekKey {
    private Integer yearId;

    private String weekDesc;

    private String weekDescEn;

    private Date startDay;

    private Date endDay;

    private String weekLongDesc;

    private String weekLongDescEn;

    private Integer preWeekId;

    private Integer lyWeekId;

    private Byte hasData;

    public Integer getYearId() {
        return yearId;
    }

    public void setYearId(Integer yearId) {
        this.yearId = yearId;
    }

    public String getWeekDesc() {
        return weekDesc;
    }

    public void setWeekDesc(String weekDesc) {
        this.weekDesc = weekDesc == null ? null : weekDesc.trim();
    }

    public String getWeekDescEn() {
        return weekDescEn;
    }

    public void setWeekDescEn(String weekDescEn) {
        this.weekDescEn = weekDescEn == null ? null : weekDescEn.trim();
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    public String getWeekLongDesc() {
        return weekLongDesc;
    }

    public void setWeekLongDesc(String weekLongDesc) {
        this.weekLongDesc = weekLongDesc == null ? null : weekLongDesc.trim();
    }

    public String getWeekLongDescEn() {
        return weekLongDescEn;
    }

    public void setWeekLongDescEn(String weekLongDescEn) {
        this.weekLongDescEn = weekLongDescEn == null ? null : weekLongDescEn.trim();
    }

    public Integer getPreWeekId() {
        return preWeekId;
    }

    public void setPreWeekId(Integer preWeekId) {
        this.preWeekId = preWeekId;
    }

    public Integer getLyWeekId() {
        return lyWeekId;
    }

    public void setLyWeekId(Integer lyWeekId) {
        this.lyWeekId = lyWeekId;
    }

    public Byte getHasData() {
        return hasData;
    }

    public void setHasData(Byte hasData) {
        this.hasData = hasData;
    }
}