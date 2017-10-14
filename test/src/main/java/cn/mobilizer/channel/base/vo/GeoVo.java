package cn.mobilizer.channel.base.vo;

import java.math.BigDecimal;

public class GeoVo {

	public boolean isPointInRect(Point point,Bounds bounds) {
		Point sw = bounds.getSouthWest(); //西南脚点
		Point ne = bounds.getNorthEast(); //东北脚点
        //return (point.lng >= sw.lng && point.lng <= ne.lng && point.lat >= sw.lat && point.lat <= ne.lat);
		if(point.getLng().compareTo(sw.getLng())>-1 && point.getLng().compareTo(ne.getLng())<=0
				&& point.getLat().compareTo(sw.getLat())>-1 && point.getLat().compareTo(ne.getLat())<=0){
			return true;
		}else{
			return false;
		}
	}

	public Point getPoint() {
		return new Point();
	}

	public Bounds getBounds() {
		return new Bounds();
	}

	public class Point {
		
		private BigDecimal lng;
		private BigDecimal lat;

		public BigDecimal getLng() {
			return lng;
		}

		public void setLng(BigDecimal lng) {
			this.lng = lng;
		}

		public BigDecimal getLat() {
			return lat;
		}

		public void setLat(BigDecimal lat) {
			this.lat = lat;
		}

	}

	/**
	 * 区域坐标点
	 * 
	 * @author Rocky
	 *
	 */
	public class Bounds {

		private Point southWest;// 西南脚点
		private Point northEast;// 东北脚点

		public Point getSouthWest() {
			return southWest;
		}

		public void setSouthWest(Point southWest) {
			this.southWest = southWest;
		}

		public Point getNorthEast() {
			return northEast;
		}

		public void setNorthEast(Point northEast) {
			this.northEast = northEast;
		}
	}

}
