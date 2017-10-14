package cn.mobilizer.channel.util;

public final class Constants {

	private Constants() {
		
	}

	public static final String SYSTEM = "SYSTEM"; // system

	public static final String INVALID_STATUS = "N"; // 无效
	public static final String VALID_STATUS = "Y"; // 有效
	public static final Byte IS_DELETE_TRUE = 1; // 删除
	public static final Byte IS_DELETE_FALSE = 0; // 未删除
	public static final Byte PARAMETER_TYPE_SKU = 1; // 
	public static final Byte PARAMETER_TYPE_BRAND = 2; // 
	public static final String OPTION_STORE_NAME = "store_status";//门店状态

	public static final String  MOBI_OPTION_STORE = "手机-促销员";
	public static final String 	PROMOTING_GROWTH  ="长促";
	public static final String 	PROMOTING_TEAM = "促销队";
	
	public static final Byte ISACTIVATION = 100;//自定义常量

	
	public static final String ADD_NEW_STRUCTURE = "请输入名称";
	public static final String DEFAULTPWASSWORD="8888";
	public static final Integer SELF_STRUCTURE_ID=0;//本部门
	public static final String SELF_STRUCTURE_NAME="本部门";
	public static final String IS_CHECKED="true";
	
	public static final Byte MESSAGE = 1;//短信
	public static final Byte INFORM = 2;//通知
	
	public static final Byte YES=1;//是
	public static final Byte NO=0;//否
	
	public static final String SHOW_EDIT="display:inline";//显示编辑
	public static final String NO_SHOW_EDIT="display:none";//不显示编辑
	
	public static final Byte NO_CALL_STATUS = 0;//未拜访
	public static final Byte IN_CALL_STATUS = 1;//拜访中
	public static final Byte OUT_CALL_STATUS = 2;//拜访完成
	public static final Byte CANCEL_CALL_STATUS = 3;//取消拜访
	
	public static final Byte STORE_POP_TYPE = 1; //门店
	public static final Byte DISTRIBUTOR_POP_TYPE = 2;//经销商
	
	public static final String STORE_TYPE = "store_type";//
	
	public static final Byte LOG_TYPE_WEB = 4;//web端登录
	public static final Integer USED_FLAG_WEB = 1;//使用范围是web端
	public static final String DATA_MOD_CONF = "conf";//数据访问模式为读取client_business_definition
	
	/**client_business_definition中定义的tableName**/
	public static final String TABLENAME_CLIENTUSER = "client_user";//client_user表对应的人员管理模块
	public static final String TABLENAME_STORE = "store";//client_user表对应的人员管理模块
	
	public static final String OPTION_NAME_STORE_STATUS = "store_status";//option表，option_name值
	public static final String OPTIONS_NAME_WORK_TYPE = "work_time_type";//排班类型
	
	public static final Byte STORE_STATUS_WX = 3;//门店状态无效
	
	public static final Integer WEB_PLANNING_TYPE = 2;//web端修改
	public static final Integer MOBI_TEMP_PLANNING_TYPE = 3;//手机端添加临时计划
	public static final Integer MOBI_NOTEMP_PLANNING_TYPE = 4;//手机添加非临时计划
	
	public static final Integer WEB_WORK_TYPE_STORE = 0;//正常走店类的计划
	public static final Integer WEB_WORK_TYPE_NOTSTORE = 1;//不需要走店的计划
	
	public static final Integer WEB_VISIT = 0;//拜访
	public static final Integer NOT_WEB_VISIT = 1;//非拜访
	
	public static final Byte MSG_IS_READ_FALSE = 0; //信息未读
	public static final Byte MSG_IS_READ_TRUE = 1; //信息已读
	
	public static final Byte SALES_ORDER_TYPE_VISIT= 1; //拜访订单
	public static final Byte SALES_ORDER_TYPE_phone= 2; //电话订单
	public static final Byte SALES_ORDER_TYPE_IMPORT= 3; //导入订单
	
	public static final Byte NO_STATUS = -1;//-1-审核不通过
	public static final Byte YES_STATUS = 1;//1-审核通过
	public static final Byte COM_STATUS = 0;//0-未审核
	
	public static final Integer  RUN_VISIT_TYPE = 1;//1-走店
	public static final Integer  TO_VISIT_TYPE	 = 2;//2-协访
	public static final Integer  WORK_VISIT_TYPE = 3;//3-店内工作
	
	public static final Byte  DATA_CHECK_STATUS_1 = 1;//1-未验证
	public static final Byte  DATA_CHECK_STATUS_2 = 2;//2-已经验证没有数据差异
	public static final Byte  DATA_CHECK_STATUS_3 = 3;//3-需要重新上传数据
	
//	public static final Byte APP_CODE_ANDROID = 1;//安卓类型
//	public static final Byte APP_CODE_IPHONE = 2;//Iphone类型
//	public static final Byte APP_CODE_IPAD = 3;//Ipad类型
	
	public static final Byte OBJ_CLIENTUSER = 1;//1-人员
	public static final Byte OBJ_STORE = 2;//2-门店
	
	public static final Byte MSI_SURVEY_COMPLAIN = 1;   //已申诉
	public static final Byte MSI_SURVEY_COMPLAIN_SUCCESS = 2;   //已经审批通过
	public static final Byte MSI_SURVEY_COMPLAIN_STATUS_FAILURE = 3;   //已经审批未通过
	
	public static final Byte MSI_SURVEY_IMG = 1;	//问卷
	public static final Byte MSI_SURVEY_COMPLAIN_IMG = 2; //申诉图片

	//周期类型
	public static final Byte CYCLE_DAY   = 1; //天
	public static final Byte CYCLE_WEEK  = 2; //周
	public static final Byte CYCLE_HALF_MONTH = 3; //半月
	public static final Byte CYCLE_MONTH = 4;//月
	
	public static final Byte DATEDAY = -1;//前一天
	public static final String HMS = " 05:59:59";//固定的时间界限
	
	public static final Integer CHECK_SUCCESS = 0;//审核成功;
	public static final Integer CHECK_FALSE = 1;//审核失败;

	//问卷类型
	public static final Byte MSI_SURVEY_TYPE_EXPERIENCE = 1;    //体验店
	public static final Byte MSI_SURVEY_TYPE_COMMON = 2;			//普通店
	
	public static final Byte DATA_YES 	= 1; //有数据对应dim_week表
	public static final Byte DATA_NO 	= 0; //无数据
	
	public static final Integer COLGATE_CLIENTID = 15;//高露洁1clientId
	public static final Integer PUBLIC_CLIENTID = 0;//colgat的 clientId
	
	public static final Byte APPLE_SURVEY_STATUS_INIT = 0;   //未保存
	public static final Byte APPLE_SURVEY_STATUS_SAVE = 1;   //已保存
	public static final Byte APPLE_SURVEY_STATUS_UPLOAD = 2;	//上传
	public static final Byte APPLE_SURVEY_STATUS_CHECK = 3;		//已审核
	
	/**
	 *  client_id  工作日志
	 */
	public static final Integer CLINT_ID_WORK = 8;//工作日志导入
	/**
	 * client_id  费列罗
	 */
	public static final Integer CLINT_ID_FERRERO = 11; //费列罗 
	
	
	public static final String WORKTYPE = "休息";//mg排班时间特殊处理
	public static final String TIMEONE = "00:00";
	
	public static final Byte MANIN =1;//男
	public static final Byte WOMANIN =2;//女
	
	
	public static final String MAN ="男";
	public static final String WOMAN = "女";
	
	
	public static final Byte ZZINTEGER= 1;
	public static final String ZZ="在职";
	public static final String LZ="离职";
	
	public static final Byte JYINTEGER = 0;
	public static final String JY ="禁用";
	public static final String QY = "启用";
	public static final String WSY ="无法使用正常APP";
	
	public static final Integer CTBATIMPORT = 2;//ctbat计划导入
	
	public static final int UNICHARM = 7;//unicharm 客户id
	
	public static final String SICK_LEAVE = "病假";
	public static final String CASUAL_LEAVE = "事假";
	public static final String PROMOTER = "促销员";
	
	/**
	 * ShiroUser 用户
	 */
	public static final String SHIRO_USER  = "ShiroUser";
	/**
	 * 作者  ：彭伟
	 * 登录失败URL  
	 * 	高露洁中文版  登录需要
	 */
	public static final String ON_LOGIN_URL = "onLoginUrl";
			
	/**
	 * 验证码
	 */
	public static final String VERIFICATION_CODE ="VERIFICATION_CODE";
	
	public static final String STORENO = "storeNo";//门店编号
	
	
	public static final String MOBI_BUSINESS = "手机-业务代表,手机-业务代表主管";//角色为手机-业务代表和手机-业务代表主管
	
	public static final Integer IS_REPORT_DATA = 1; //已提交
	public static final Integer IS_NOT_REPORT_DATA 	= 2; //未提报
	
	public static final String STORE_TYPE_OPTION = "success_or_not";//(门店是否成功访问，opiton_name=’ success_or_not’)
	
	public static final Byte APPLE_DATA_TYPE_VISIT = 1;          //访问员数据类型
	
	public static final Byte APPLE_DATA_TYPE_APPROVAL = 2;		 //审核员数据类型	
	
	public static final Byte APPLE_DATA_TYPE_APPEAL = 3;	     //申诉员数据类型		
	
}