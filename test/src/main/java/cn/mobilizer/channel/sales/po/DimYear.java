package cn.mobilizer.channel.sales.po;

public class DimYear extends DimYearKey {
    private String yearDesc;

    private String yearDescEn;

    private Integer preYearId;

    public String getYearDesc() {
        return yearDesc;
    }

    public void setYearDesc(String yearDesc) {
        this.yearDesc = yearDesc == null ? null : yearDesc.trim();
    }

    public String getYearDescEn() {
        return yearDescEn;
    }

    public void setYearDescEn(String yearDescEn) {
        this.yearDescEn = yearDescEn == null ? null : yearDescEn.trim();
    }

    public Integer getPreYearId() {
        return preYearId;
    }

    public void setPreYearId(Integer preYearId) {
        this.preYearId = preYearId;
    }
}