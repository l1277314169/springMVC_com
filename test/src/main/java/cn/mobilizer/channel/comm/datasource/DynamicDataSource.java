package cn.mobilizer.channel.comm.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;

/**
 * 
 * @author yeeda.tian
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource{  
	  
    @Override  
    protected Object determineCurrentLookupKey() {  
        return CustomerContextHolder.getCustomerType();  
    }  
}  