package cn.mobilizer.channel.util;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerUtil {

private static Configuration config = null;
    
	static{   
        config = new Configuration();
        config.setClassForTemplateLoading(PDFCoreSupport.class,"template");
        config.setTemplateUpdateDelay(10);
		config.setDefaultEncoding("UTF-8");
    }   
       
    public static String generate(String template, Map<String,Object> variables) throws Exception{
    	BufferedWriter writer = null;
    	String htmlStr = null;
		try {
			Template tp = config.getTemplate(template);   
			StringWriter stringWriter = new StringWriter();     
			writer = new BufferedWriter(stringWriter);
			tp.process(variables, writer);     
			htmlStr = stringWriter.toString();   
			writer.flush();
		}finally{
			if(null!=writer){
				writer.close();
			}
		}
        return htmlStr;
    }
    
    public static String getMessage(String template) throws Exception{
    	return generate(template, null);
    }
    
    
	
}
