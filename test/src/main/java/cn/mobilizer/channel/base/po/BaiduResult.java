package cn.mobilizer.channel.base.po;

public class BaiduResult {
	private BaiduLocation location;
	private Integer precise;
	private Integer confidence;
	private String level;
	public BaiduLocation getLocation() {
		return location;
	}
	public void setLocation(BaiduLocation location) {
		this.location = location;
	}
	public Integer getPrecise() {
		return precise;
	}
	public void setPrecise(Integer precise) {
		this.precise = precise;
	}
	public Integer getConfidence() {
		return confidence;
	}
	public void setConfidence(Integer confidence) {
		this.confidence = confidence;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
}
