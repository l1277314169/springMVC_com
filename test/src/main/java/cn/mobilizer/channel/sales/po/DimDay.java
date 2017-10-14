package cn.mobilizer.channel.sales.po;

public class DimDay extends DimDayKey {
    private String dayDesc;

    private String dayDescEn;

    private Integer weekId;

    private Integer monthId;

    private Integer quarterId;

    private Integer yearId;

    public String getDayDesc() {
        return dayDesc;
    }

    public void setDayDesc(String dayDesc) {
        this.dayDesc = dayDesc == null ? null : dayDesc.trim();
    }

    public String getDayDescEn() {
        return dayDescEn;
    }

    public void setDayDescEn(String dayDescEn) {
        this.dayDescEn = dayDescEn == null ? null : dayDescEn.trim();
    }

    public Integer getWeekId() {
        return weekId;
    }

    public void setWeekId(Integer weekId) {
        this.weekId = weekId;
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    public Integer getQuarterId() {
        return quarterId;
    }

    public void setQuarterId(Integer quarterId) {
        this.quarterId = quarterId;
    }

    public Integer getYearId() {
        return yearId;
    }

    public void setYearId(Integer yearId) {
        this.yearId = yearId;
    }
}