package com.utils;


import com.comm.ResultMessage;

public final class Constants {

	private Constants() {
	}

	public static final  class Isdelete{
		public final static Byte YES = 1;//删除
		public final static Byte NO = 0;//未删除
		static {
			System.out.println("isdelete");
		}
	}
	
	public static final  class OptionName{
		public final static String MESSAGE_TYPE  = "message_type";
		public final static String USER_STATUS = "user_status";
		public final static String USER_ACTIVATION = "user_activation";
		public final static String USER_SEX = "user_sex";
	}
	static {
		System.out.println("hh");
	}
	public static void main(String[] args) {

		System.out.println(Constants.OptionName.MESSAGE_TYPE);


	}

}