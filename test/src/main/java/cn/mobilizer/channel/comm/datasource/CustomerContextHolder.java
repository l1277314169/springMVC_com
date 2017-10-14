package cn.mobilizer.channel.comm.datasource;

/**
 * 
 * @author yeeda.tian
 *
 */
public class CustomerContextHolder {  
	  
    public static final String BASE = "base";  
    public static final String REPORT = "report";  
    public static final String LOG = "log";  
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
      
    public static void setCustomerType(String customerType) {  
        contextHolder.set(customerType);  
    }  
      
    public static String getCustomerType() {  
        return contextHolder.get();  
    }  
      
    public static void clearCustomerType() {  
        contextHolder.remove();  
    }  
}  