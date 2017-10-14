package cn.mobilizer.channel.report.vo;


public class ReportGlobal {

	public final static class Filters{
		
		public final static int SELECT_DEPT 			= 1; //部门下拉框 
		public final static int INPUT_DATETEXT_START	= 2; //日期文本框开始时间 arg_start_day_id yyyyMMdd
		public final static int INPUT_DATETEXT_END		= 3; //日期文本框结束时间< yyyyMMdd 开始时间与结束时间需要区分开来,因为需要初始化数据> arg_end_day_id
		public final static int INPUT_HIDDEN 			= 4; //隐藏域Hidden<待确认字段>
		public final static int INPUT_DATETIME_START  	= 5; //开始时间yyyy-MM-dd
		public final static int INPUT_DATETIME_END		= 6; //结束时间yyyy-MM-dd
		public final static int SELECT_POSITION 		= 7; //人员职位控件 
		public final static int SELECT_PARENT 			= 8; //直属上级下拉控件
		public final static int DAILOG_STORE_CHANNEL 	= 9; //店铺渠道
		public final static int INPUT_MONTH 			= 11; //月份编号
		
		public final static int INPUT_MONTH_START 		= 12; //查询开始月份编号
		public final static int INPUT_MONTH_END 		= 13; //查询结束月份编号
		
		public final static int SELECT_ROLE 			= 14; //角色
		
		public final static int SELECT_IS_ACTIVATION	= 15; //账号状态
		public final static int SELECT_WORK_STATUS		= 16; //在职状态
		public final static int SELECT_CLIENTUSER		= 17; //用户
		
		//hidden
		public final static int HIDE_CLIENT_ID 			= 18; //客户编号
		public final static int HIDE_LEVEL 				= 19; //级别
		public final static int HIDE_DRILL 				= 20; //钻取
		public final static int HIDE_ARG_FLAG 			= 21; //导出标识
		public final static int HIDE_PAGE_START 		= 22; //分页开始位置
		public final static int HIDE_PAGE_SIZE			= 23; //分页大小
		public final static int HIDE_CHAIN_ID 			= 24; //连锁
		
		public final static int SELECT_VISIT_TYPE		= 25; //拜访类型
		public final static int HIDE_FILTER_USER_IDS	= 26; //权限过滤条件(直属下级人员)
		public final static int HIDE_FILTER_STRUCTURE_IDS	= 27; //权限过滤条件(用户所属角色可以查看的部门)
		
		public final static int SELECT_BRAND 			= 28; //品牌(string)
		public final static int SELECT_CATEGORY 		= 29; //品类(string)
		
		public final static int HIDE_LOGIN_USER_ROLE 	= 30;//登陆用户拥有的角色信息
		
		public final static int INPUT_TEXT 				= 31; //标准文本框
		public final static int PROVINCE				= 32; //省编号
		public final static int CITY					= 33; //市编号
		
		public final static int SELECT_WORK_START 		= 34; //周选择控件start
		public final static int SELECT_WORK_END 		= 35; //周选择控件end
		
		public final static int SELECT_PROVICE			= 36; //单独的省份下拉框
		public final static int SELECT_CITY				= 37; //单独的城市下拉框
		
		public final static int SELECT_BRAND2 			= 38; //品牌(int)
		public final static int SELECT_CATEGORY2 		= 39; //品类(int)
		public final static int SELECT_PARENT2 			= 40; //直属上级下拉控件 (int)
		
		public final static int INPUT_DATETEXT_START_7	= 41; //日期文本框开始时间  yyyyMMdd<前后查询不能超过7天>
		public final static int INPUT_DATETEXT_END_7	= 42; //日期文本框结束时间  yyyyMMdd
		
		public final static int INPUT_DATETEXT_START_15	= 43; //日期文本框开始时间  yyyyMMdd<前后查询不能超过15天>
		public final static int INPUT_DATETEXT_END_15	= 44; //日期文本框结束时间  yyyyMMdd
		
		public final static int INPUT_DATETIME_START_7  	= 45; //开始时间yyyy-MM-dd<前后查询不能超过7天>
		public final static int INPUT_DATETIME_END_7		= 46; //结束时间yyyy-MM-dd
		
		public final static int INPUT_DATETIME_START_15  	= 47; //开始时间yyyy-MM-dd<前后查询不能超过15天>
		public final static int INPUT_DATETIME_END_15		= 48; //结束时间yyyy-MM-dd
		
		public final static int STRUCTURE_GRADE             = 49; //部门层级 arg_structure_grade
		public final static int CLIENT_USER_ID 				= 50; //系统当前用户
		
		public final static int ARG_DEPT_IDS				=53;//部门编号(多个以逗号分隔)
		public final static int ARG_SKU_IDS					=51;//SKU编号(多个以逗号分隔)
		
		public final static int SALESTYPE					=52;//销量类型(1-普通)
		
		public final static int PROJECTSALESTYPE			=53;//销量类型(2-重点品类)
		
		public final static int STORE_ID					=54;//门店编号
		
		public final static int WEEKID						=55;//周编号
		
		public final static int DEFAULT						=56;//默认值0
		
		public final static int DEFAULTSTR					=57;//默认值是空
		
		public final static int ENUM						=58; //枚举值，通过数据库配置的下拉框  
		
		public final static int STORE_TYPE  				=59; //对应account
		
		public final static int STORE_TYPE_TEXT 			=60; //value值为text
		
		public final static int SELECTEDMULTI				=62;//部门多选;
		
		public final static int YEAR 						=63; //年份
		
		public final static int CLIENTPOSITION				=64;//职位多选（暂时没用到）
		
		public final static int MG_ROLE 					= 65; //角色为手机-业务代表和手机-业务代表主管
		public final static int MG_POSITION 				= 66; //角色为手机-业务代表和手机-业务代表主管下的职位
		
		public final static int CLIENTUSERPOSITION			= 67;//职位编号(枚举:61-促销队,65-长促)
		
		public final static int SUPERSANMONTH 				= 68;//超过三个月无登陆的用户
		
		public final static int TURN 						= 69; //轮次
		
		public final static int DATE_WEEK_ID 				= 70;//一周日期的周编号
		
		public final static int PROJECT_TYPE				= 71; //项目类型
		public final static int CUSTOMER_IDS				= 72; //客户
		public final static int BRAND_IDS					= 73; //品牌(与客户级联)
		
		public final static int STORE_SUCCESS				= 74;//(门店是否成功访问，opiton_name=’ success_or_not’)
		
		public final static int YEARS2						= 75;//年份
		public final static int PROVINCE2					= 76; //省份 select框
		
		public final static int INPUT_DATETIME_START_100  	= 77; //开始时间yyyy-MM-dd<前后查询不能超过100天>
		public final static int INPUT_DATETIME_END_100		= 78; //结束时间yyyy-MM-dd
		
		public final static int INPUT_DATETEXT_START_100	= 79; //日期文本框开始时间  yyyyMMdd<前后查询不能超过100天>
		public final static int INPUT_DATETEXT_END_100		= 80; //日期文本框结束时间  yyyyMMdd
		
		public final static int YAER_2008 					= 81; //年份下拉框，开始年份为2008年
		
		public final static int ARG_SKU_NAME 			    = 82; // 产品名称
		public final static int AM                           =83;  //AM
		public final static int RM                           =84;  //RM
		
		public final static int CATEGORY_NAME 				= 85; //品类(名称)

	}
	
	public final static class Day{
		public final static int D7 				= -6;
		public final static int A7 				=  6;
	}
	
	public final static String CHANGED = "1";
	
	public final static String HAS_SEARCH = "3";
	
	/**
	 * 请求参数类型
	 * @author Rocky
	 *
	 */
	public final static class ParametersType{
		public final static int INT = 1;
	}
	
	/**
	 * 报表元素所处位置
	 * @author Rocky
	 *
	 */
	public final static class Place{
		public final static String BUTTOM 	= "buttom";
//		public final static String TOP 		= "top";
		public final static String RIGHT 	= "right";
//		public final static String LEFT 	= "left";<echarts左侧元素不能定义，由最大数据列确定>
	}
	
	/**
	 * 是否可被钻取
	 * @author Rocky
	 *
	 */
	public final static class Drill{
		public final static String OFF = "0";
		public final static String ON  = "1";  //可用被钻取
	}
	
	public final static class ReportFlag{
		public final static int EXPORT = 1; //导出
		public final static int QUERY  = 0; //查询
	}
	
	public final static String DEFAULT_LEVEL = "1"; //部门层级默认值<待确认>
	public final static String CHARTSET = "UTF-8";
	
	/**
	 * 扩展字段
	 * @author Rocky
	 *
	 */
	public final class ColumnTag{
		public final static String IMAGE_COLUMN = "1"; //导出为图片
	}
	
	/**
	 * 报表功能项
	 * @author Rocky
	 *
	 */
	public final class Func{
		public final static String EXPORT_EXCEL = "1"; //导出为Excel
		public final static String EXPORT_IMAGE = "2"; //导出图片
	}
	
	/**
	 * 请求参数类型
	 * @author Rocky
	 *
	 */
	public final class ColumnType{
		public final static int NUMBER = 1; //数值类型
		public final static int STRING = 2; //字符串类型
	}
	
	/**
	 * 存储过程返回多个结果集时，定义每个结果集返回的数据类型
	 * @author Rocky
	 *
	 */
	public final class Procedure {
		public final static int VALUE = 0;
		public final static int ITEMS = 1;
	}
	
	/**
	 * 是否需要分页
	 * @author Rocky
	 *
	 */
	public final class LimitArg{
		public final static String ARG_PAGE_START = "arg_page_start";
		public final static String ARG_PAGE_SIZE  = "arg_page_size";
	}
	
	/**
	 * 显示格式
	 * @author Rocky
	 *
	 */
	public final class Format{
		public final static int PER = 1; //以百分比格式显示
	}
	
	public final class Style{
		public final static String MAX_STYLE = "max";
		public final static String MID_STYLE = "mid";
		public final static String MIN_STYLE = "min";
	}
	
	public final static String LEGEND = "1";
	public final static int DASHBOARD = 1;
	
	
	/**
	 * excel导出数据格式
	 * @author Rocky
	 *
	 */
	public final class ExcelType{
		public final static String NUMBER = "number";
		public final static String INT = "int";
		public final static String DATE = "date";
		public final static String TIME = "time";
	}
	
	/**
	 * 报表导出类型，如果为空默认的导出类型，通过colum_list确定导出字段，如果为1存储过程返回什么就导出什么
	 * @author Rocky
	 *
	 */
	public final class ExportType{
		public final static int PROCEDURE 	 = 1;
		public final static int DEFAULT 	 = 0;
	}
	
	public final class Color{
		public final static String RED 		= "1";
		public final static String GREEN 	= "2";
		public final static String BLUE 	= "3";
		public final static String YELLOW 	= "4";
		public final static String BLACK 	= "5";
		public final static String TEAL 	= "6";
	}
	
	public final static int HIDE = 1; //隐藏
	
	public final static String GRID2 	= "grid2";
	public final static String PIE 		= "pie";
	public final static String DIV		= "div";
	public final static String DIV2		= "div2";
	
	public final static int EXCEL_MAX_DATA = 200000;//excel数据导出如果查过20w条数据，需要提示用户缩短导出的日期  200000
	
}
