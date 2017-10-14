package cn.mobilizer.channel.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;

public class PDFCoreSupport {

    public static void createPDF(Map<String,Object> variables,String template,OutputStream os) throws Exception{
    	try {
			String htmlStr = FreemarkerUtil.generate(template, variables);
			ITextRenderer renderer = new ITextRenderer();
			String fontDir = PropertiesHelper.getInstance().getProperty(PropertiesHelper.FONT);
			renderer.getFontResolver().addFont(fontDir,BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.setDocumentFromString(htmlStr);
			renderer.layout();
			renderer.createPDF(os);
			renderer.finishPDF();
		}finally{
			if(null!=os){
				os.close();
			}
		}
    }
    
    public static void createPDF(Map<String,Object> variables,String template,String path) throws Exception{
    	FileOutputStream os = null;
    	try {
    		os = new FileOutputStream(new File(path));
			String htmlStr = FreemarkerUtil.generate(template, variables);
			ITextRenderer renderer = new ITextRenderer();
			String fontDir = "C:/Windows/Fonts/ARIALUNI.TTF";
			renderer.getFontResolver().addFont(fontDir,BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.setDocumentFromString(htmlStr);
			renderer.layout();
			renderer.createPDF(os);
			renderer.finishPDF();
		}finally{
			if(null!=os){
				os.close();
			}
		}
    }
    
    public static void main(String[] args) throws Exception{
    	Map<String,Object> variables = new HashMap<String,Object>();   
        variables.put("storeName", "这里是测试模板");
        PDFCoreSupport.createPDF(variables, "wyeth_contract.ftl", "D:\\sample.pdf");
        System.out.println("PDF打印成功");
	}
}
