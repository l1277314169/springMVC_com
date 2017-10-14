package cn.mobilizer.channel.report.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.export.po.DataInfo;
import cn.mobilizer.channel.report.vo.ReportGlobal;

public class ReportVo implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Logger log = Logger.getLogger(this.getClass());

	// Parts
	private Integer partId;
	private Integer reportId;
	private Integer elementId;
	private String partName;
	private String partFilter;
	private String columnList;
	private String chartType;
	private String layout;
	private List<Filter> filterList; // 请求参数
	private List<Column> columns;// 字段<报表表头>
	private List<TreeMap<String, DataVo>> values; // 报表数据
	private TreeMap<String, List<TreeMap<String, DataVo>>> mapValues = null;; //报表数据
	private DataInfo dataInfo; //返回字段时，数据存在该字段
	private List<Column> ferreros = new ArrayList<Column>();
	private List<String> heards; // 报表表头<用于excel导出>
	private List<ChartTypes> chartTypes; // 图标显示方式
	private Integer allItems; // 总记录数，在存储过程分页查询时有效
	private boolean isLimit = false; // 是否需要分页<默认为false不分页>
	private Set<String> columnNames;
	private Map<String, Object> params; // 参数
	private int dashboard = 0;
	private Layout layoutVo;
	private String chartSetting;
	private ChartSetting setting;
	
	private String options;
	private String seriesStr;
	
	private String divCol1;
	private String divCol2;
	private String divCol3;
	
	// Element
	private String sqlTemplate;
	private String parameters;
	private List<Filter> parametersList;

	// ECharts页面展示数据
	private String legendData; // echarts中显示的图例
	private String buttomData; // 直角坐标系中，在坐标轴底部显示的数据
	private String leftName; // 直角坐标系中在左侧显示的数据元素
	private String rightName; // 直角坐标系中在右侧显示的数据
	private List<Series> series; // 根据values字段转换成的echarts页面识别的用于展示的数据格式
	private Set<Column> drillCols; // 可被钻取列的集合

	private boolean hasDept; // 是否包含部门,如果包含部门
	private List<String> keyIndexs = null;
	
	public List<String> getKeyIndexs() {
		return keyIndexs;
	}

	public boolean isHasDept() {
		for (Filter rep : getFilterList()) {
			if (rep.getType() == ReportGlobal.Filters.SELECT_DEPT) {
				this.hasDept = true;
				break;
			}
		}
		return hasDept;
	}
	
	public DataInfo getDataInfo() {
		return dataInfo;
	}

	public void setDataInfo(DataInfo dataInfo) {
		this.dataInfo = dataInfo;
	}
	
	public String getChartSetting() {
		return chartSetting;
	}

	public void setChartSetting(String chartSetting) {
		this.chartSetting = chartSetting;
		this.setting = new ChartSetting().transJson(this.chartSetting);
	}

	public void setHasDept(boolean hasDept) {
		this.hasDept = hasDept;
	}
	
	public List<Column> getFerreros() {
		return ferreros;
	}

	public void setFerreros(List<Column> ferreros) {
		this.ferreros = ferreros;
	}

	public Layout getLayoutVo() {
		return layoutVo;
	}
	
	public void setLayoutVo(Layout layoutVo) {
		this.layoutVo = layoutVo;
	}
	
	public ChartSetting getSetting() {
		return setting;
	}

	public void setSetting(ChartSetting setting) {
		this.setting = setting;
	}

	public String getSeriesStr() {
		return seriesStr;
	}

	public void setSeriesStr(String seriesStr) {
		this.seriesStr = seriesStr;
	}

	public int getDashboard() {
		return dashboard;
	}
	
	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public void setDashboard(int dashboard) {
		this.dashboard = dashboard;
	}

	public String getFilterName(int type) {
		String name = null;
		for (Filter rep : getFilterList()) {
			if (rep.getType() == type) {
				name = rep.getArg();
				break;
			}
		}
		return name;
	}

	public Filter getFilter(int type) {
		Filter filter = null;
		for (Filter rep : getFilterList()) {
			if (rep.getType() == type) {
				filter = rep;
				break;
			}
		}
		return filter;
	}

	public String getMyBatisSql(Map<String, Object> filterMap) {
		Set<String> keys = filterMap.keySet();
		String sql = getSqlTemplate();
		for (String key : keys) {
			Filter reFilter = this.getParamByName(key);
			if (null == reFilter) {
				continue;
			}
			Object val = filterMap.get(key);
			if (reFilter.getType() == ReportGlobal.ColumnType.NUMBER) {
				val = val == null ? "0" : val;
				if(StringUtil.isEmptyString(val.toString())){
					val = "0";
				}
				sql = sql.replaceAll(key, val + "");
			} else {
				val = val == null ? "" : val;
				sql = sql.replaceAll(key, "'" + val + "'");
			}
		}
		log.info("call sql: " + sql.toString());
		return sql;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * 根据列名获取字段名称
	 * 
	 * @param desc
	 * @return
	 */
	public Column getColumnByDesc(String desc) {
		Column col = null;
		for (Column c : getColumns()) {
			if (c.getDesc().equals(desc)) {
				col = c;
				break;
			}
		}
		return col;
	}

	/**
	 * 根据字段名称获取字段信息
	 * 
	 * @param colName
	 * @return
	 */
	public Filter getParamByName(String colName) {
		Filter rep = null;
		for (Filter c : getParametersList()) {
			if (c.getArg().equals(colName)) {
				rep = c;
				break;
			}
		}
		return rep;
	}

	/**
	 * 根据制定的Tag获取该列的索引
	 * 
	 * @param tag
	 * @return
	 */
	public Set<String> getTagColName(String tag) {
		Set<String> tags = new HashSet<String>();
		for (int i = 0; i < getColumns().size(); i++) {
			Column column = getColumns().get(i);
			if (null != column.getTag() && column.getTag().equals(tag)) {
				tags.add(column.getColName());
			}
		}
		return tags;
	}

	/**
	 * 获取tag列对应的所有数据项
	 * 
	 * @param tag
	 * @return
	 */
	public List<String> getValueByTag(String tag) {
		List<String> list = new ArrayList<String>();
		Set<String> tags = this.getTagColName(tag);
		for (TreeMap<String, DataVo> v : getValues()) {
			Set<String> keys = v.keySet();
			for (String key : keys) {
				if (tags.contains(key)) {
					DataVo vo = v.get(key);
					list.add(vo.getValue() + "");
				}
			}
		}
		return list;
	}

	/**
	 * 数据格式转换为echarts能解析的数据
	 * 
	 * @return
	 */
	public List<Series> getSeries() {
		return series;
	}

	public Column getColumnByPlace(String place) {
		Column c = null;
		for (Column column : getColumns()) {
			if (column.getPlace().equals(place)) {
				c = column;
				break;
			}
		}
		return c;
	}

	public Column getDrillColumn(String drillType) {
		Column c = null;
		for (Column column : getColumns()) {
			if (column.getDesc().equals(drillType)) {
				c = column;
				break;
			}
		}
		return c;
	}

	public String getColumnDesc(String place) {
		String val = null;
		for (Column column : getColumns()) {
			if (null != column.getPlace() && column.getPlace().equals(place)) {
				val = column.getDesc();
				break;
			}
		}
		return val;
	}

	public String getLegendData() {

		return legendData;
	}

	/**
	 * 获取图列取值地方
	 * 
	 * @return
	 */
	public Column getLegendColumn() {
		Column col = null;
		for (Column column : getColumns()) {
			if (!StringUtil.isEmptyString(column.getLegend())
					&& column.getLegend().equals(ReportGlobal.LEGEND)) {
				col = column;
				break;
			}
		}
		return col;
	}

	public Column getDataColumn() {
		Column col = null;
		for (Column column : getColumns()) {
			if (!StringUtil.isEmptyString(column.getChartType())) {
				col = column;
				break;
			}
		}
		return col;
	}

	public Column getButtomColumn() {
		Column col = null;
		for (Column column : getColumns()) {
			if (null != column.getPlace()
					&& column.getPlace().equals(ReportGlobal.Place.BUTTOM)) {
				col = column;
				break;
			}
		}
		return col;
	}

	public void setLegendData(String legendData) {
		this.legendData = legendData;
	}

	public int getColumnIndex(String key) {
		int index = -1;
		for (int i = 0; i < columns.size(); i++) {
			Column column = columns.get(i);
			if (key.equals(column.getColName())) {
				return i;
			}
		}
		return index;
	}

	public Column getColumnByKey(String key) {
		Column col = null;
		for (int i = 0; i < columns.size(); i++) {
			Column column = columns.get(i);
			if (key.equals(column.getColName())) {
				col = column;
				break;
			}
		}
		return col;
	}

	public TreeMap<String, Column> getMapColumn() {
		TreeMap<String, Column> map = new TreeMap<String, Column>();
		for (Column column : getColumns()) {
			map.put(column.getColName(), column);
		}
		return map;
	}

	public String getLeftName() {

		return leftName == null ? "" : leftName;
	}

	public String getRightName() {
		return rightName == null ? "" : rightName;
	}

	public String getButtomData() {

		return buttomData;
	}

	public void setPartFilter(String partFilter) {
		this.partFilter = partFilter == null ? null : partFilter.trim();
		this.filterList = new Filter().transJSON(partFilter);
	}

	public void setColumnList(String columnList) {
		this.columnList = columnList == null ? null : columnList.trim();
		this.columns = new Column().transJSON(columnList);

		drillCols = new HashSet<Column>();
		heards = new ArrayList<String>();
		columnNames = new HashSet<String>();

		for (int i = 0; i < columns.size(); i++) {
			Column column = columns.get(i);
			if (column.getDrill().equals(ReportGlobal.Drill.ON)) {
				drillCols.add(column);
			}
			if(column.getHide()!=ReportGlobal.HIDE){
				heards.add(column.getDesc());
			}
			columnNames.add(column.getColName());
		}
	}
	

	public void setSeries(List<Series> series) {
		this.series = series;
	}

	public void setButtomData(String buttomData) {
		this.buttomData = buttomData;
	}

	public List<TreeMap<String, DataVo>> getValues() {
		return values;
	}

	public void setValues(List<TreeMap<String, DataVo>> values) {
		this.values = values;
		series = new ArrayList<Series>();
		if (null == this.values || this.values.isEmpty()) {
			// 防止未查询到数据时，echarts报错。
			if (null == series || series.isEmpty()) {
				Series ser = new Series();
				ser.setData("");
				series.add(ser);
			}
			return;
		}

		Column legend = this.getLegendColumn();
		// legend
		StringBuffer buffer = new StringBuffer();
		Column legendColum = this.getLegendColumn();

		if (null == legendColum) {
			for (Column column : getColumns()) {
				if (null != column.getPlace()
						&& !column.getPlace().equals(ReportGlobal.Place.BUTTOM)) {
					buffer.append(column.getDesc()).append(",");
				}
			}
		} else {// 根据某一列获取图列
			Set<Object> setTemp = new HashSet<Object>();
			for (TreeMap<String, DataVo> vals : getValues()) {
				Set<String> keys = vals.keySet();
				for (String key : keys) {
					if (key.split("@")[1].equals(legendColum.getColName())) {
						DataVo dataVo = vals.get(key);
						if (!setTemp.contains(dataVo.getValue())) {
							buffer.append(dataVo.getValue()).append(",");
							setTemp.add(dataVo.getValue());
						}
					}
				}
			}
		}

		if (buffer.toString().endsWith(",")) {
			buffer.deleteCharAt(buffer.length() - 1);
		}
		this.legendData = buffer.toString();

		// rightName
		this.rightName = this.getColumnDesc(ReportGlobal.Place.RIGHT);

		// leftName
		// Column legend = this.getLegendColumn();
		if (null == legend) {
			double temp = 0;
			Series sTemp = null;
			for (int i = 0; i < getSeries().size(); i++) {
				Series s = getSeries().get(i);
				double max = s.getMaxData();
				if (temp < max) {
					temp = max;
					sTemp = s;
				}
			}
			if (null != sTemp) {
				this.leftName = sTemp.getName();
			}
		} else {
			Column column = getDataColumn();
			if(null!=column){
				leftName = column.getDesc();
			}
		}

		// buttomData
		Set<Object> setTemp = new HashSet<Object>();
		StringBuffer sf = new StringBuffer();
		for (TreeMap<String, DataVo> vals : getValues()) {
			Set<String> keys = vals.keySet();
			for (String k : keys) {
				String[] arrayKey = k.split("@");
				for (Column column : getColumns()) {
					if (null != column.getPlace()
							&& column.getColName().equals(arrayKey[1])
							&& column.getPlace().equals(
									ReportGlobal.Place.BUTTOM)) {
						if (!setTemp.contains(vals.get(k).getValue())) {
							sf.append(vals.get(k).getValue()).append(",");
							setTemp.add(vals.get(k).getValue());
						}
					}
				}
			}
		}
		if (sf.toString().endsWith(",")) {
			sf.deleteCharAt(sf.length() - 1);
		}
		this.buttomData = sf.toString();

		
		if(getChartType().indexOf(ReportGlobal.PIE)!=-1){ //饼图<数据库查询的结果第一列默认为name第二列为value>
			
			StringBuffer bf = new StringBuffer();
			for (TreeMap<String, DataVo> v : getValues()) {
				Set<String> keys = v.keySet();
				List<String> keysList = new ArrayList<String>(keys);
				
				for (int i = 0; i < keysList.size(); i+=2) {
					bf.append("{value:").append(v.get(keysList.get(i+1)).getValue()).append(", name:'").append(v.get(keysList.get(i)).getValue()+"'},");
					Series ser = new Series();
					ser.setName(v.get(keysList.get(i+1)).getValue()+"");
					ser.setData(v.get(keysList.get(i)).getValue()+"");
					series.add(ser);
				}
			}
			if(bf.toString().endsWith(",")){
				bf = bf.deleteCharAt(bf.length()-1);
			}
			this.setSeriesStr(bf.toString());
			
		}else if(getChartType().indexOf(ReportGlobal.DIV)!=-1){
			
			if(getChartType().indexOf(ReportGlobal.DIV2)!=-1){
				for (TreeMap<String, DataVo> v : getValues()) {
					Set<String> keys = v.keySet();
					List<String> keysList = new ArrayList<String>(keys);
					for (int i = 0; i < keysList.size(); i+=3) {
						this.setDivCol1(v.get(keysList.get(i+1)).getValue()+"");
						this.setDivCol2(v.get(keysList.get(i)).getValue()+"");
						this.setDivCol3(v.get(keysList.get(i+2)).getValue()+"");
					}
				}
			}else{
				for (TreeMap<String, DataVo> v : getValues()) {
					Set<String> keys = v.keySet();
					List<String> keysList = new ArrayList<String>(keys);
					for (int i = 0; i < keysList.size(); i+=2) {
						this.setDivCol1(v.get(keysList.get(i+1)).getValue()+"");
						this.setDivCol2(v.get(keysList.get(i)).getValue()+"");
					}
				}
			}
		}else if (null == legend) {// 正常的echarts图表
			for (Column c : getColumns()) {
				if (!StringUtil.isEmptyString(c.getChartType())) {
					Series ser = new Series();
					ser.setName(c.getDesc());
					ser.setType(c.getChartType());
					ser.setPlace(c.getPlace());

					StringBuffer data = new StringBuffer();
					for (TreeMap<String, DataVo> v : getValues()) {
						Set<String> keys = v.keySet();
						for (String key : keys) {
							String[] arrayKey = key.split("@");
							if (arrayKey[1].equals(c.getColName())) {
								data.append(v.get(key).getValue()).append(",");
							}
						}
					}
					if (data.toString().endsWith(",")) {
						data.deleteCharAt(data.length() - 1);
					}
					ser.setData(data.toString());
					series.add(ser);
				}
			}
		}else { // echarts堆叠图表，由于存储过程返回的数据格式，不是echarts需要的格式，此处需要转换。
			Column c = this.getDataColumn();
			Column b = this.getButtomColumn();
			String[] buttObj = this.getButtomData().split(",");
			for (TreeMap<String, DataVo> v : getValues()) {
				Series ser = new Series();
				ser.setType(c.getChartType());
				ser.setPlace(c.getPlace());

				Set<String> keys = v.keySet();
				// 根据人员获取数据
				StringBuffer data = new StringBuffer();
				for (String key : keys) {
					String[] arrayKey = key.split("@");
					if (arrayKey[1].equals(c.getColName())) {
						for (String s : buttObj) {
							if (s.equals(ser.getStack())) {
								data.append(v.get(key).getValue()).append(",");
							} else {
								data.append("0,");
							}
						}
					} else if (arrayKey[1].equals(legend.getColName())) {
						ser.setName(v.get(key).getValue().toString());
					} else if (arrayKey[1].equals(b.getColName())) {
						ser.setStack(v.get(key).getValue().toString());
					}
				}

				if (data.toString().endsWith(",")) {
					data.deleteCharAt(data.length() - 1);
				}
				ser.setData(data.toString());
				series.add(ser);
			}
			Map<String, Series> serMaps = megerSeries(series);
			Set<String> keys = serMaps.keySet();
			series.clear();
			for (String key : keys) {
				Series ser = serMaps.get(key);
				series.add(ser);
				log.debug("data:" + ser.getData() + ",name:" + ser.getName()+ ",stack:" + ser.getStack() + ",type:" + ser.getType());
			}
		}
	}

	public Map<String, Series> megerSeries(List<Series> seriesList) {
		TreeMap<String, Series> serMaps = new TreeMap<String, Series>();
		for (Series ser : seriesList) {
			if (!serMaps.containsKey(ser.getName())) {
				serMaps.put(ser.getName(), ser);
			} else {
				Series series = serMaps.get(ser.getName());
				String data[] = series.getData().split(",");
				int index = this.getIndex(ser.getStack());
				String dtemp[] = ser.getData().split(",");
				data[index] = dtemp[index];
				String d = toString(data);
				series.setData(d);
				serMaps.put(ser.getName(), series);
			}
		}
		return serMaps;
	}

	public String toString(String[] str) {
		StringBuffer buffer = new StringBuffer();
		for (String s : str) {
			buffer.append(s).append(",");
		}
		if (buffer.toString().endsWith(",")) {
			buffer.deleteCharAt(buffer.length() - 1);
		}
		return buffer.toString();
	}

	public int getIndex(String name) {
		String[] buttObj = this.getButtomData().split(",");
		int index = -1;
		for (int i = 0; i < buttObj.length; i++) {
			if (name.equals(buttObj[i])) {
				index = i;
				break;
			}
		}
		return index;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public List<Filter> getFilterList() {

		return filterList;
	}

	public void setLeftName(String leftName) {
		this.leftName = leftName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Integer getElementId() {
		return elementId;
	}

	public void setElementId(Integer elementId) {
		this.elementId = elementId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName == null ? null : partName.trim();
	}

	public String getPartFilter() {
		return partFilter;
	}

	public String getColumnList() {
		return columnList;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType == null ? null : chartType.trim();
		this.chartTypes = new ChartTypes().transJSON(this.chartType);
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout == null ? null : layout.trim();
		this.layoutVo = new Layout(this.layout);
	}

	public List<String> getHeards() {
		return heards;
	}

	public void setHeards(List<String> heards) {
		this.heards = heards;
	}

	public Set<Column> getDrillCols() {
		return drillCols;
	}

	public void setDrillCols(Set<Column> drillCols) {
		this.drillCols = drillCols;
	}

	public void setFilterList(List<Filter> filterList) {
		this.filterList = filterList;
	}

	public List<ChartTypes> getChartTypes() {
		return chartTypes;
	}

	public void setChartTypes(List<ChartTypes> chartTypes) {
		this.chartTypes = chartTypes;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public String getSqlTemplate() {
		return sqlTemplate;
	}

	public void setSqlTemplate(String sqlTemplate) {
		this.sqlTemplate = sqlTemplate;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
		this.parametersList = new Filter().transJSON(this.parameters);
	}

	public List<Filter> getParametersList() {
		return parametersList;
	}

	public void setParametersList(List<Filter> parametersList) {
		this.parametersList = parametersList;
	}

	public Integer getAllItems() {
		return allItems;
	}

	public void setAllItems(Integer allItems) {
		this.allItems = allItems;
	}

	public boolean isLimit() {
		for (Filter rep : getParametersList()) {
			if (rep.getArg().equals(ReportGlobal.LimitArg.ARG_PAGE_SIZE)|| rep.getArg().equals(ReportGlobal.LimitArg.ARG_PAGE_START)) {
				this.isLimit = true;
				break;
			}
		}
		return isLimit;
	}

	public void setLimit(boolean isLimit) {
		this.isLimit = isLimit;
	}

	public Set<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(Set<String> columnNames) {
		this.columnNames = columnNames;
	}

	public String getDivCol1() {
		return divCol1;
	}

	public void setDivCol1(String divCol1) {
		this.divCol1 = divCol1;
	}

	public String getDivCol2() {
		return divCol2;
	}

	public void setDivCol2(String divCol2) {
		this.divCol2 = divCol2;
	}

	public String getDivCol3() {
		return divCol3;
	}

	public void setDivCol3(String divCol3) {
		this.divCol3 = divCol3;
	}

	public TreeMap<String, List<TreeMap<String, DataVo>>> getMapValues() {
		return mapValues;
	}

	public void setMapValues(List<TreeMap<String, DataVo>> values,String key,String legendKey) {
		mapValues = new TreeMap<String, List<TreeMap<String,DataVo>>>();
		keyIndexs = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		this.divCol1 = key;
		this.divCol2 = legendKey;
		
		for (TreeMap<String, DataVo> map : values) {
			DataVo data = map.get(key);
			
			if(StringUtil.isNotEmptyString(legendKey)){
				DataVo lData = map.get(legendKey);
				if(null!=lData){
					set.add(lData.getValue()+"");
				}
			}
			if(null==data){
				continue;
			}
			
			if(mapValues.containsKey(data.getValue())){
				List<TreeMap<String, DataVo>> lists = mapValues.get(data.getValue());
				lists.add(map);
			}else{
				List<TreeMap<String, DataVo>> lists = new ArrayList<TreeMap<String,DataVo>>();
				lists.add(map);
				mapValues.put(data.getValue()+"", lists);
				keyIndexs.add(data.getValue()+"");
			}
		}
		
		StringBuffer buffer = new StringBuffer();
		for (String string : set) {
			buffer.append(string).append(",");
		}
		if(buffer.toString().endsWith(",")){
			this.legendData = buffer.deleteCharAt(buffer.length()-1).toString();
		}
		
		List<Column> columns = getColumns();
		StringBuffer sbf = new StringBuffer();
		for (Column column : columns) {
			if(column.getColName().startsWith("W")){
				Column column2 = new Column();
				column2.setArgName(column.getArgName());
				column2.setColName(column.getColName());
				ferreros.add(column2);
				sbf.append(column.getColName()).append(",");
			}
		}
		if(sbf.toString().endsWith(",")){
			this.buttomData = sbf.deleteCharAt(sbf.length()-1).toString();
		}
	}
	
}
