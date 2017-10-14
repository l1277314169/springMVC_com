package cn.mobilizer.channel.sync.po;

public interface Constants {

	public static String PAGE_SIZE = "10";//列表默认10条记录
	public final static short NORMAL = 0;//已删除	
	public final static short IS_DELETE = 1;//已删除
	
	public final short NO_ACTIVE = 0;
	public final short IS_ACTIVE = 1;
	
	public static final String DEFAULT_SYNC_TIME  = "1980-01-01 00:00:00";
	//syncdata opetion code
	public static final int OPTION_CODE_GET = 1;
	public static final int OPTION_CODE_UPLOAD = 2;
	public static final int OPTION_CODE_UPLOAD_GET = 3;
	
	//column type, (1-int,2-float,3-日期时间,4-字符,5-bit,6-guid,7-decimal)
	public static final int COLUMN_TYPE_INT = 1;
	public static final int COLUMN_TYPE_FLOAT = 2;
	public static final int COLUMN_TYPE_DATE = 3;
	public static final int COLUMN_TYPE_CHAR = 4;
	public static final int COLUMN_TYPE_BIT = 5;
	public static final int COLUMN_TYPE_GUID= 6;
	public static final int COLUMN_TYPE_DECIMAL = 7;
	
	public static final int RESULT_SUCCESS = 100;
	public static final String RESULT_SUCCESS_DESC = "SUCCESS";
	public static final int RESULT_FAILED = -1;
	public static final String RESULT_FAILED_DESC = "FAILTED";
	public static final String RESULT_FAILED_ILLEGAL_PARA = "Illegal Parameters!";

}
