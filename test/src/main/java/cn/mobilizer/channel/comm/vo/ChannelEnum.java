
/**
 *
 */
package cn.mobilizer.channel.comm.vo;

import java.io.Serializable;

/**
 * 拜访-相关的所有枚举
 *
 * @author yeeda.tian
 *
 */
public class ChannelEnum implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7608400028941821550L;

	/**
	 * 拜访步骤-参数控件类型
	 *
	 */
	public static enum VISIT_DATA_CONTROL_TYPE {
		TEXT ("单行文本",1),
		TEXT_EREA ("多行文本",2),
		INTEGER ("整数",3),
		DECIMAL ("小数",4),
		SELECT_SINGLE ("单选下拉菜单",5),
		SELECT_MULTI ("多选下拉菜单",6),
		CHECKBOC ("勾选",7),
		DATE ("日期",8),
		TIME ("日期时间",9),
		LOCATION ("定位",10),
		PHOTO ("拍照",11),
		LABEL ("标签",12),
		VIDEO ("视频",13),
		AUDIO ("音频",14),
		USERS ("联系人",15),
		CHECK_BOX_2 ("勾选(两种状态)",16),
		CHAIN ("门店连锁选择",17),
		PROVINCE ("省份",18),
		CITY ("城市",19),
		DISTRICT ("区县",20),
		STRUCTURE ("部门",21),
		DISTRIBUTOR ("经销商",22),
		USERS_RELATE ("人员相关",23),
		CHANNEL ("门店渠道选择",24),
		SKUPRICE ("SKU价格",4001),
		HIDDEN("隐藏域",101),
		MONTH("月份",100);
		

		private final String cnName;
		private final int key;

		VISIT_DATA_CONTROL_TYPE(String name,int key){
			this.cnName = name;
			this.key=key;
		}

		public String getCnName(String code){
			for(VISIT_DATA_CONTROL_TYPE item:VISIT_DATA_CONTROL_TYPE.values()){
				if(item.getCode().equals(code)){
					return item.cnName;
				}
			}
			return code;
		}
		
		public String getCnName(int key){
			for(VISIT_DATA_CONTROL_TYPE item:VISIT_DATA_CONTROL_TYPE.values()){
				if(item.getKey () == (key)){
					return item.cnName;
				}
			}
			return null;
		}

		public String getCode(){
			return this.name();
		}
		
		public int getKey() {  
	        return key;  
	    }
		
		public String getCnName(){
			return this.cnName;
		}
		
		
		@Override
		public String toString() {
			return this.name();
		}
	}
	
	/**
	 * 步骤类型
	 * @author yeeda.tian
	 *
	 */
	public static enum VISIT_TASK_STEP_TYPE {
		RELATED_SKU ("产品相关",(byte)(1)),
		RELATED_BRAND ("品牌相关",(byte)(2)),
		RELATED_CATEGORY ("品类相关",(byte)(3)),
		RELATED_QUESTIONNAIRE ("问卷(无对象)",(byte)(4)),
		RELATED_USER ("人员相关",(byte)(5)),
		RELATED_TERMINAL ("终端对象",(byte)(6)),
		RELATED_PRODUCT ("产品系列",(byte)(7)),
		RELATED_PRODUCT_CATEGORY ("产品分类",(byte)(8)),
		RELATED_PROMOTION ("促销物料相关",(byte)(10));
		
		private final String cnName;
		private final Byte key;

		VISIT_TASK_STEP_TYPE(String name,byte key){
			this.cnName = name;
			this.key=key;
		}

		public String getCnName(String code){
			for(VISIT_DATA_CONTROL_TYPE item:VISIT_DATA_CONTROL_TYPE.values()){
				if(item.getCode().equals(code)){
					return item.cnName;
				}
			}
			return code;
		}

		public String getCode(){
			return this.name();
		}
		
		public Byte getKey() {  
	        return key;  
	    }
		
		public String getCnName(){
			return this.cnName;
		}
		
		
		@Override
		public String toString() {
			return this.name();
		}
	}
	
	/**
	 * 拜访对象类型
	 * @author yeeda.tian
	 *
	 */
	public static enum VISIT_POP_TYPE{
//		STORE ("门店",1);
		STORE ("门店",(byte)(1)),
		DISTRIBUTOR ("经销商",(byte)(2));
		
		private final String cnName;
		private final Byte key;

		VISIT_POP_TYPE(String name,Byte key){
			this.cnName = name;
			this.key=key;
		}

		public String getCnName(String code){
			for(VISIT_DATA_CONTROL_TYPE item:VISIT_DATA_CONTROL_TYPE.values()){
				if(item.getCode().equals(code)){
					return item.cnName;
				}
			}
			return code;
		}

		public String getCode(){
			return this.name();
		}
		
		public Byte getKey() {  
	        return key;  
	    }
		
		public String getCnName(){
			return this.cnName;
		}
		
		
		@Override
		public String toString() {
			return this.name();
		}
	}
	
	
	/**
	 * 拜访任务类型
	 * @author yeeda.tian
	 *
	 */
	public static enum VISITING_TASK_TYPE {
		RC ("门店拜访", (byte) (1)),
		XF ("门店检核", (byte) (2)),
		DJ ("店内工作", (byte) (3));

		private final String	cnName;
		private final Byte		key;

		VISITING_TASK_TYPE(String name, Byte key) {
			this.cnName = name;
			this.key = key;
		}

		public String getCnName(String code){
			for ( VISITING_TASK_TYPE item : VISITING_TASK_TYPE.values () ) {
				if (item.getCode ().equals (code)) { return item.cnName; }
			}
			return code;
		}

		public String getCode(){
			return this.name ();
		}

		public Byte getKey(){
			return key;
		}

		public String getCnName(){
			return this.cnName;
		}

		@Override
		public String toString(){
			return this.name ();
		}
	}
	
	
	/**
	 * 拜访对象选择方式
	 * @author yeeda.tian
	 *
	 */
	public static enum TASK_STEP_SELECT_TYPE {
		JGZT ("价格账套", (byte) (1)),
		FX ("分销", (byte) (2));
		
		private final String	cnName;
		private final Byte		key;
		
		TASK_STEP_SELECT_TYPE(String name, Byte key) {
			this.cnName = name;
			this.key = key;
		}
		
		public String getCnName(String code){
			for ( TASK_STEP_SELECT_TYPE item : TASK_STEP_SELECT_TYPE.values () ) {
				if (item.getCode ().equals (code)) { return item.cnName; }
			}
			return code;
		}
		
		public String getCode(){
			return this.name ();
		}
		
		public Byte getKey(){
			return key;
		}
		
		public String getCnName(){
			return this.cnName;
		}
		
		@Override
		public String toString(){
			return this.name ();
		}
	}
	
	
	
	/**
	 * APP类型类型
	 * @author yeeda.tian
	 *
	 */
	public static enum MOBILE_VERSION_TYPE{
		AD ("Android", (byte) (1)),
		IP ("iPhone", (byte) (2)),
		IPD ("iPad", (byte) (3));

		private final String	cnName;
		private final Byte		key;

		MOBILE_VERSION_TYPE(String name, Byte key) {
			this.cnName = name;
			this.key = key;
		}

		public String getCnName(String code){
			for ( MOBILE_VERSION_TYPE item : MOBILE_VERSION_TYPE.values () ) {
				if (item.getCode ().equals (code)) { return item.cnName; }
			}
			return code;
		}

		public String getCode(){
			return this.name ();
		}

		public Byte getKey(){
			return key;
		}

		public String getCnName(){
			return this.cnName;
		}

		@Override
		public String toString(){
			return this.name ();
		}
	}
	
	/**
	 * 指定手机端SKU按哪种方式分组
	 * @author yeeda.tian
	 *
	 */
	public static enum MOBILE_SORT_BY {
		BRAND ("品牌", (byte) (1)),
		CATEGORY ("品类", (byte) (2)),
		PRODUCT ("产品", (byte) (3));
		
		private final String	cnName;
		private final Byte		key;
		
		MOBILE_SORT_BY(String name, Byte key) {
			this.cnName = name;
			this.key = key;
		}
		
		public String getCnName(String code){
			for ( MOBILE_SORT_BY item : MOBILE_SORT_BY.values () ) {
				if (item.getCode ().equals (code)) { return item.cnName; }
			}
			return code;
		}
		
		public String getCode(){
			return this.name ();
		}
		
		public Byte getKey(){
			return key;
		}
		
		public String getCnName(){
			return this.cnName;
		}
		
		@Override
		public String toString(){
			return this.name ();
		}
	}
	
	/**拜访类型
	 * @author Nany
	 * 2015年2月12日下午1:44:52
	 */
	public static enum VISIT_TYPE{
		MAIN_VISIT("拜访", (byte) (0)),
		INSPECT_VISIT("非拜访", (byte) (1));
		private String cnName;
		private Byte key;
		
		VISIT_TYPE(String name, Byte key) {
			this.cnName = name;
			this.key = key;
		}
		
		public String getCnName(String code){
			for ( VISIT_TYPE item : VISIT_TYPE.values () ) {
				if (item.getCode ().equals (code)) { return item.cnName; }
			}
			return code;
		}
		
		public String getCode(){
			return this.name ();
		}
		
		public String getCnName() {
			return cnName;
		}

		public Byte getKey() {
			return key;
		}
		
		@Override
		public String toString(){
			return this.name ();
		}
	}
	
	public static enum PAGE_TPYE{
		LIST("列表", (int) (1)),
		QUERY("查询", (int) (2)),
		ADD("新增", (int) (3)),
		EDIT("编辑", (int) (4)),
		SEE("查看",(int) (5)),
		IMPORT("导入",(int) (6));
		
		private String cnName;
		private Integer key;
		
		PAGE_TPYE(String name, Integer key) {
			this.cnName = name;
			this.key = key;
		}
		
		public String getCnName(String code){
			for ( VISIT_TYPE item : VISIT_TYPE.values () ) {
				if (item.getCode ().equals (code)) { return item.cnName; }
			}
			return code;
		}
		
		public String getCode(){
			return this.name ();
		}
		
		public String getCnName() {
			return cnName;
		}
		
		public Integer getKey() {
			return key;
		}
		
		@Override
		public String toString(){
			return this.name ();
		}
	}
	/**
	 * 账号状态
	 * @author yeeda.tian
	 *
	 */
	public static enum CLIENT_USER_ISACTIVATION {
		JY ("禁用", (byte) (0)),
		QY ("启用", (byte) (1)),
		UN_APP ("无法正常使用APP", (byte) (2));

		private final String	cnName;
		private final Byte		key;

		CLIENT_USER_ISACTIVATION(String name, Byte key) {
			this.cnName = name;
			this.key = key;
		}

		public String getCnName(String code){
			for ( VISITING_TASK_TYPE item : VISITING_TASK_TYPE.values () ) {
				if (item.getCode ().equals (code)) { return item.cnName; }
			}
			return code;
		}

		public String getCode(){
			return this.name ();
		}

		public Byte getKey(){
			return key;
		}

		public String getCnName(){
			return this.cnName;
		}

		@Override
		public String toString(){
			return this.name ();
		}
	}
	
	/**
	 * 在职状态
	 * @author yeeda.tian
	 *
	 */
	public static enum CLIENT_USER_STATUS {
		ZZ ("在职", (byte) (1)),
		LZ ("离职", (byte) (0));
		
		private final String	cnName;
		private final Byte		key;
		
		CLIENT_USER_STATUS(String name, Byte key) {
			this.cnName = name;
			this.key = key;
		}
		
		public String getCnName(String code){
			for ( VISITING_TASK_TYPE item : VISITING_TASK_TYPE.values () ) {
				if (item.getCode ().equals (code)) { return item.cnName; }
			}
			return code;
		}
		
		public String getCode(){
			return this.name ();
		}
		
		public Byte getKey(){
			return key;
		}
		
		public String getCnName(){
			return this.cnName;
		}
		
		@Override
		public String toString(){
			return this.name ();
		}
	}
	/**
	 * 拜访状态
	 * @author Administrator
	 *
	 */
	public static enum CALL_STATUS{
		NO_CALL_STATUS ("未拜访", (byte) (0)),
		IN_CALL_STATUS("拜访中", (byte) (1)),
		SUCCESS_CALL_STATUS("完成拜访",(byte)(2)),
		CANCEL_CALL_STATUS("取消拜访",(byte)(3));
		private final String	cnName;
		private final Byte		key;
		
		CALL_STATUS(String name, Byte key) {
			this.cnName = name;
			this.key = key;
		}
		
		public String getCnName(String code){
			for (VISIT_TYPE item : VISIT_TYPE.values () ) {
				if (item.getCode ().equals (code)) { return item.cnName; }
			}
			return code;
		}
		
		public String getCode(){
			return this.name ();
		}
		
		public Byte getKey(){
			return key;
		}
		
		public String getCnName(){
			return this.cnName;
		}
		
		@Override
		public String toString(){
			return this.name ();
		}
		
	}
	
	
	/**
	 * 拜访任务类型
	 * @author yeeda.tian
	 *
	 */
	public static enum ARG_TYPE {
		YE ("婴儿护理", (byte) (1)),
		SL ("生理护理", (byte) (2));

		private final String	cnName;
		private final Byte		key;

		ARG_TYPE(String name, Byte key) {
			this.cnName = name;
			this.key = key;
		}

		public String getCnName(String code){
			for ( VISITING_TASK_TYPE item : VISITING_TASK_TYPE.values () ) {
				if (item.getCode ().equals (code)) { return item.cnName; }
			}
			return code;
		}

		public String getCode(){
			return this.name ();
		}

		public Byte getKey(){
			return key;
		}

		public String getCnName(){
			return this.cnName;
		}

		@Override
		public String toString(){
			return this.name ();
		}
	}
	
	/**
	 * 周期类型
	 * @author LiuYong
	 */
	public static enum CYCLE_TYPE{
		DAY ("天",(byte)(1)),
		WEEK ("周",(byte)(2)),
		HALFMONTH ("半月",(byte)(3)),
		MONTH ("月",(byte)(4));
		private final String	cnName;
		private final Byte		key;
		
		CYCLE_TYPE(String name, Byte key) {
			this.cnName = name;
			this.key = key;
		}

		public String getCnName(String code){
			for ( CYCLE_TYPE item : CYCLE_TYPE.values () ) {
				if (item.getCode ().equals (code)) { return item.cnName; }
			}
			return code;
		}

		public String getCode(){
			return this.name ();
		}

		public Byte getKey(){
			return key;
		}

		public String getCnName(){
			return this.cnName;
		}

		@Override
		public String toString(){
			return this.name ();
		}
	}
	
	
	/**
	 * 
	* @ClassName: POSM_IN_OUT_TYPE 
	* @Description: POSM  物料进出 
	* @author  pengwei
	* @date 2015年10月9日 下午3:35:07 
	*
	 */
	public static enum POSM_IN_OUT_TYPE {
		ENTER ("收进", (byte) (1)),
		COME ("发出", (byte) (2));

		private final String	name;
		private final Byte		key;

		POSM_IN_OUT_TYPE(String name, Byte key) {
			this.name = name;
			this.key = key;
		}
		
		public String getCnName(String code){
			for ( POSM_IN_OUT_TYPE item : POSM_IN_OUT_TYPE.values () ) {
				if (item.getCode ().equals (code)) { return item.name; }
			}
			return code;
		}

		public String getCode(){
			return this.name ();
		}

		public Byte getKey(){
			return key;
		}

		public String getName(){
			return this.name;
		}

		@Override
		public String toString(){
			return this.name ();
		}
	}
	
	/**
	 * 
	* @ClassName: POSM_IN_OUT_TYPE 
	* @Description: POSM  物料进出 
	* @author  pengwei
	* @date 2015年10月9日 下午3:35:07 
	*
	 */
	public static enum POSM_MATERIAL_TYPE {
		ENTER ("无效", (byte) (0)),
		COME ("有效", (byte) (1));

		private final String	name;
		private final Byte		key;

		POSM_MATERIAL_TYPE(String name, Byte key) {
			this.name = name;
			this.key = key;
		}
		
		public String getCnName(String code){
			for ( POSM_MATERIAL_TYPE item : POSM_MATERIAL_TYPE.values () ) {
				if (item.getCode ().equals (code)) { return item.name; }
			}
			return code;
		}

		public String getCode(){
			return this.name ();
		}

		public Byte getKey(){
			return key;
		}

		public String getName(){
			return this.name;
		}

		@Override
		public String toString(){
			return this.name ();
		}
	}
 }
