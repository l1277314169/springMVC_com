package cn.mobilizer.channel.constant;


public class ImportConstants {
	public static final String CLIENT_STRUCTURE_NAME = "clientStructureName";//部门名称
	public static final String PROVINCE_NAME = "provinceName";//省份
	public static final String CITY_NAME = "cityName";//城市
	public static final String DISTRICT_NAME = "districtName";//区县
	public static final String POSITION_NAME = "positionName";//职位
	public static final String CHANNEL_NAME = "channelName";//渠道
	public static final String CHAIN_NAME = "chainName";//连锁
	public static final String CLIENT_USER_NAME = "clientUserName";//人员姓名
	public static final String POP_TYPE = "pop_Type";//门店类型
	public static final String POP_NAME = "pop_Name";//门店名称
	public static final String POP_CODE = "pop_Code";//门店编号
	public static final String CALL_DATE = "call_date";//计划
	public static final String I_BRAND = "i_brand";//品牌
	public static final String I_CATEGORY = "i_category";//品类
	public static final String I_UNIT = "i_unit";//单位
	public static final String I_ROLE = "i_role";//系统角色
	public static final String I_UP_LEVEL = "i_up_level";//上级主管
	public static final String I_USER = "i_user";//人员姓名
	public static final String I_PLAT_USER = "i_plat_user";//平台权限，手机/Web
	public static final String I_DISTRIBUTOR= "i_distributor";//经销商
	public static final String USER_NAME = "loginName";//登录名
	public static final String WORKTIMETYPE = "work_time_type";//班次
	
	
	
	
	
	
	//姓名	拜访日期	客户类型	客户名称	客户代码
	public static final String[] CALL_PLANNING_TITLE =  {"姓名","拜访日期","客户类型","客户名称","客户代码"};
	public static final String[] CALL_PLANNING_COLUMN =  {CLIENT_USER_NAME,CALL_DATE,POP_TYPE,POP_NAME,POP_CODE};
	
	public static final String[] STORE_TITLE =  {"客户名称","客户代码","省份","城市","区县","地址","电话","部门","渠道","连锁","理货员","业务主管","业务代表","经销商","BA人员"};
	public static final String[] STORE_COLUMN =  {"storeName","storeNo",PROVINCE_NAME,CITY_NAME,DISTRICT_NAME,"addr","telephoneNo",CLIENT_STRUCTURE_NAME,CHANNEL_NAME,CHAIN_NAME,I_USER,I_USER,I_USER,I_DISTRIBUTOR,I_USER};
	
	public static final String[] STORE_COLGATE_TITLE = {"Store_No","Store_Name","Store_Address","City","Channel","APG","Store_Hierarchy","CM","BM","RD","Remark"};
	public static final String[] STORE_COLGATE_COLUMN = {"storeNo","storeName","addr","structureName",CHANNEL_NAME,CHAIN_NAME,"storeTypeName","loginName_One","loginName_Two","loginName_Three","remark"};
	
	public static final String[] STORE_APPLECARE_TITLE = {"轮次","门店编号","机器型号","规格","S/N","IMEI","城市","服务商店名","地址","顾客需要告知服务商的问题","如果被问及原因，就这样告知服务商"};
	public static final String[] STORE_APPLECARE_COLUMN = {"storeType","storeNo","contact","fax","telephoneNo","mobileNo","cityName","storeName","addr","bannerPhoto","remark"};
	
	public static final String[] STORE_BWYB_TITLE = {"客户区域","省","城市","渠道","客户渠道","连锁名称","门店编码","多次执行门店编号","门店名称","门店地址","执行人员"};
	public static final String[] STORE_BWYB_COLUMN = {"structureName",PROVINCE_NAME,CITY_NAME,"firstChannelName","secondChannelName",CHAIN_NAME,"storeNo","","storeName","addr","loginName"};
	
	public static final String[] STORE_WORKLOG_TITLE = {"门店编号","门店名称","所在部门","省份","城市","渠道","连锁","地址","备注"};
	public static final String[] STORE_WORKLOG_COLUMN = {"storeNo","storeName","structureName",PROVINCE_NAME,CITY_NAME,CHANNEL_NAME,CHAIN_NAME,"addr","remark"};
	
	public static final String[] CLIENT_USER_TITLE =  {"姓名","手机号","编号","登录名","部门","职位","省份","城市","区县","邮箱","分部","用户角色","上级主管","平台权限"};
	public static final String[] CLIENT_USER_COLUMN =  {"name","mobileNo","userNo","loginName",CLIENT_STRUCTURE_NAME,POSITION_NAME,PROVINCE_NAME,CITY_NAME,DISTRICT_NAME,"email",CLIENT_STRUCTURE_NAME,I_ROLE,I_UP_LEVEL,I_PLAT_USER};
	
	//产品编号	产品名称	条码	品牌	品类	单位	价格
	public static final String[] SKU_TITLE =  {"编号","名称","条码","品牌","品类","单位","价格"};
	public static final String[] SKU_COLUMN =  {"skuNo","skuName","barcode",I_BRAND,I_CATEGORY,I_UNIT,"price"};
	//仓库导入模板
	public static final String[] WAREHOUSE_TITLE =  {"仓库编号","仓库名称","区域名称",/**"省份",*/ "城市名称","城市简称",/*"区县",*/"仓库地址","仓库面积","联系人","联系电话"};
	public static final String[] WAREHOUSE_COLUMN =  {"warehouseNo","warehouseName",CLIENT_STRUCTURE_NAME,/**PROVINCE_NAME,*/CITY_NAME,/*DISTRICT_NAME,*/"addr","area","contact","telephoneNo"};
	
	public static final String[] FACT_MONTHLYSALES_TITLE = {"客户自编号","店铺编号","店铺名称","大区","分部","省份","城市","销售月份","产品条码","SKU","销量","含税销售额（元）"};
	public static final String[] FACT_MONTHLYSALES_COLUMN = {"factMonthlySalesNo","storeCode","storeName","region","clientStructureName",PROVINCE_NAME,CITY_NAME,"monthId","barcode","skuName","salesVolume","salesAmount"};
	//人员导入通用模板
	public static final String[] PERSONNEL_TITLE=  {"姓名","手机号","邮箱","登录名","总部","区域","省份","城市","职位","用户角色","上级主管"};
	public static final String[] PERSONNEL_COLUMN= {"name","mobileNo","email","loginName",CLIENT_STRUCTURE_NAME,CLIENT_STRUCTURE_NAME,PROVINCE_NAME,CITY_NAME,POSITION_NAME,I_ROLE,I_UP_LEVEL};

	//门店通用导入模板
	public static final String[] STORECOMMON_TITLE={"门店编号","门店名称","门店地址","渠道","连锁","所属部门","负责人"};
	public static final String[] STORECOMMON_COLUMN = {"storeNo","storeName","addr",CHANNEL_NAME,CHAIN_NAME,CLIENT_STRUCTURE_NAME};
	
	//惠氏会员门店导入模板
	public static final String[] STOREHUISHI_TITLE={"门店编号","门店名称","zone","city","chain","负责人","手机号"};
	public static final String[] STOREHUISHI_COLUMN = {"storeNo","storeName",CLIENT_STRUCTURE_NAME,CLIENT_STRUCTURE_NAME,CHAIN_NAME,I_USER,"mobile_no",};
	
	//高露洁2门店导入模板
	public static final String[] STORECOLGATE2_TITLE={"计划编码","门店代码","门店名字","区域","省","市","店铺地址","客户编码_SAP","客户名称","零售环境","开始时间","结束时间","陈列类型","月份"};
	public static final String[] STORECOLGATE2_COLUMN = {"bannerPhoto","storeNo","storeName",CLIENT_STRUCTURE_NAME,PROVINCE_NAME,"fax","addr","externalNo","remark",CHANNEL_NAME,"contact","telephoneNo","mobileNo","storeType"};
		
	public static final String[] SURVEY_STORE_DISPLAY_TITLE = {"Plan_Code","Store_No","Effective_From","Effective_To","Display_Type","Promo_SKU"};
	public static final String[] SURVEY_STORE_DISPLAY_COLUMN = {"planCode","storeNo","validFrom","validTo","displayTypeDesc","promoSku"};
	
	//促销物料导入
	public static final String[] PROMOTION_MATERIAL_TITLE = {"品牌","物料分类","物料名称","数量(箱)","物料编号","年份","使用时间","子分类","关键节点","单价"};
	public static final String[] PROMOTION_MATERIAL_COLUMN = {"brand","category","materialName","quantity","materialCode","batch","usedTime","subCategory","spec","price"};
	
	public static final String[] PROMOTION_MATERIAL_IMPORT_TITLE={"品牌","物料分类","物料名称","子分类","关键节点","单价","数量(箱)","物料编号","年份","数据维护时间","使用时间","是否参与报表计算"};
	public static final String[] PROMOTION_MATERUAL_IMPORT_COLIMN={"brand","category","materialName","subCategory","spec","price","quantity","materialCode","batch","lastUpdateTime","usedTime","calcFlag"};
	//销量目标导入
	public static final String[] FACT_KPI_TITLE = {"店铺编号","店铺名称","大区","分部","目标月份","销售目标"};
	public static final String[] FACT_KPI_COLUMN = {"storeNo","storeName","structureName","structureName","monthId","salesAmount"};
	
	//一个星期的名称
	public static final String[] WEEK_DATA =  {"一","二","三","四","五","六","日"};
	
	public static final String[] CALLPLANNING_EXPORT = {"序号","大区","分部","用户姓名","用户账户","职位","上级主管","拜访日期","任务内容","计划类型","门店编码","门店名称"};
	
	public static final String[] ATTENDANCE_TITLE = {"姓名","登录名","部门","考勤日期","上班打卡时间","下班打卡时间","备注"};
	
	public static final String[] WORK_TIME_SETTINGS_TITLE = {"姓名","登录名","部门","班次","工作日期","开始时间","结束时间","排班门店","备注"};
	
	//费列罗导入
	public static final String[] FACT_MONTHLY_SALES_TITLE = {"开始日期","结束日期","门店编号","产品编号","价格","数量"};
	//高露洁导入
	public static final String[] COLGATE_TITLES = {"SKU_ID","RSP"};
	//工作日志导入title
	public static final String[] WORK_LOG_CLIENT_USER_TITLE = {"工号","*姓名","*用户名","*部门","*职位","在职状态","电话","账号状态","身份证","性别","手机号","*省份","*城市","区县","*上级","家庭地址","邮编","*角色","备注"};
	
	/**
	 * 费列罗人员导入Title 
	 */
	public static final String[] FACT_CLIENT_USER_TITLE= {"用户名","身份证","姓名","入职时间","角色","部门","职位","在职状态","直属上级"};
			
	public static final String[] FACT_MONTHLY_SALES_UNICHARM_TITLE = {"渠道","PC工号","门店ID","大区","省","城市","PC姓名","企业","门店名","品类","销量","月份"};
	
	//ctbat计划导入
	public static final String[] CTBAT_PLANN_IMPORT = {"理货员","拜访日期一","拜访日期二","拜访频率","门店名称","门店地址"}; 
	//费列罗导入
	public static final String[] FERRERO_COLUMN = {"门店编号","部门","连锁","门店名称","促销员"};
	
	//高露洁门头照导入
	public static final String[] STORE_IMG_TITLE = {"Id","访问日期","门店编号","门店名称","区域","省","城市等级","城市","渠道类型","客户类型","连锁店","状态","门店照片",};
	
	//高露洁店内调查问卷
	public static final String[] STORE_SURVEY_TITLE = {"Id","门店编号","门店名称","产品编号","产品名称","品牌","类别","是否有售卖","SKU数","陈列面数","价格"};
	//poms库存明细导出

	public static final String[] PROMOTION_MATERIAL_STOCK = {"大区","城市","仓库所在地","品牌","子分类","物料分类","关键节点","物料名称","年份","POSMCode","数量(箱)","数量（个）","单价","库存金额","数据维护时间","备注"};


	//物料进出 导出
	public static final String[] POSMINOUT_EXPORT = {"大区","城市","仓库编号","品牌","物料分类","物料名称","物料编号","年份","收进/发出","数量(每箱规格)","数量（个）","时间","门店编号","客户","门店","仓库编号(目的地)","运输负责人",	"备注"};

	/**
	 * 进出明细模板 Title
	 * 															
	 */
	public static final String[] POSM_IN_OUT_TITLE = {"大区","城市","仓库编号","品牌","物料分类","物料名称","物料编号","年份","收进/发出","数量(每箱规格)","数量(个)","时间","门店编号","客户","门店","仓库编号(目的地)","运输负责人","备注"};
	/**
	 * 进出明细模板 Class Name
	 */
	public static final String[] POSM_IN_OUT_TITLE_COLUMN = {"structureName","cityName","warehouseId","brand","category","materialName","materialCode","batch","billType","spec","quantity","inOutTime","storeId","storeName","storeNo","outWarehouseId","handler","remark"};
	
	
}
