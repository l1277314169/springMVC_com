package cn.mobilizer.channel.survey.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public interface HistoryDataImportService {

	public Object ImportColgateHistoryData(MultipartFile fileField,HttpServletRequest req,HttpServletResponse resp,Integer clientId,Integer clientUserId,String fileName,String month) throws RuntimeException;
	
	public Object saveImportColgateDisPlayHistoryData(MultipartFile fileField,HttpServletRequest req,HttpServletResponse resp,Integer clientId,Integer clientUserId,String excelName,String month) throws RuntimeException;

	public Object ImportSupplementaryHistoryData(MultipartFile fileField,HttpServletRequest req,HttpServletResponse resp,Integer clientId,Integer clientUserId,String excelName,String month) throws RuntimeException;
}
