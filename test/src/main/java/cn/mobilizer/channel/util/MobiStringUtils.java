package cn.mobilizer.channel.util;

/**
 * String工具类
 * @author Fred.Chung
 * 2014/10/14
 */
public final class MobiStringUtils {

	private MobiStringUtils() {
	}
	static String string2Json(String s) {     
	    StringBuffer sb = new StringBuffer ();     
	    for (int i=0; i<s.length(); i++) {     
	   
	        char c = s.charAt(i);     
	        switch (c) {     
		        case '\"':     
		            sb.append("\\\"");     
		            break;     
		        case '\\':     
		            sb.append("\\\\");     
		            break;     
		        case '/':     
		            sb.append("\\/");     
		            break;     
		        case '\b':     
		            sb.append("\\b");     
		            break;     
		        case '\f':     
		            sb.append("\\f");     
		            break;     
		        case '\n':     
		            sb.append("\\n");     
		            break;     
		        case '\r':     
		            sb.append("\\r");     
		            break;     
		        case '\t':     
		            sb.append("\\t");     
		            break;     
		        default:     
		            sb.append(c);     
		            break;     
	        }
	    }
	    return sb.toString();    
	 }  

	public static boolean isEmpty(String s) {
		if (null == s)
			return true;
		if (0 == s.trim().length())
			return true;
		return false;
	}
	
	public static boolean contains(String[] strArray, String key){
		for (int i = 0; i < strArray.length; i++) {
			if(!isEmpty(strArray[i]) && strArray[i].equals(key)){
				return true;
			}
		}
		return false;
	}
}
