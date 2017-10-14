package cn.mobilizer.channel.survey.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.survey.po.SurveyFeedbackDetail;
import cn.mobilizer.channel.util.ExcelReader;

public class HistoryDataImportUtil {
	
	public final static Integer CLIENTID = 15;
	
	
	public static SurveyFeedbackDetail createZHJ(Integer objectId,String value,String feedbackId){
		if(StringUtil.isEmptyString(value)){
			return null;
		}else{
			SurveyFeedbackDetail d1 = new SurveyFeedbackDetail();
			d1.setClientId(CLIENTID);
			d1.setDetailId(UUID.randomUUID().toString());
			d1.setValue(value);
			d1.setFeedbackId(feedbackId);
			d1.setSubSurveyId(4);
			d1.setObjectId(objectId); 
			d1.setSurveyParameterId(6); 
			return d1;
		}
	}
	
	public static SurveyFeedbackDetail createZJCL(Integer objectId,String value,String feedbackId){
		if(StringUtil.isEmptyString(value)){
			return null;
		}else{
			SurveyFeedbackDetail d1 = new SurveyFeedbackDetail();
			d1.setClientId(CLIENTID);
			d1.setDetailId(UUID.randomUUID().toString());
			d1.setValue(value);
			d1.setFeedbackId(feedbackId);
			d1.setSubSurveyId(5);
			d1.setObjectId(objectId); 
			d1.setSurveyParameterId(7); 
			return d1;
		}
	}
	
	public static SurveyFeedbackDetail create(Integer objectId,Integer subSurveyId,Integer parameterId,String value,String feedbackId){
		if(StringUtil.isEmptyString(value)){
			return null;
		}else{
			SurveyFeedbackDetail d1 = new SurveyFeedbackDetail();
			d1.setClientId(CLIENTID);
			d1.setDetailId(UUID.randomUUID().toString());
			d1.setValue(value);
			d1.setFeedbackId(feedbackId);
			d1.setSubSurveyId(subSurveyId);
			d1.setObjectId(objectId); 
			d1.setSurveyParameterId(parameterId); 
			return d1;
		}
	}
	
	
	public static void add(List<SurveyFeedbackDetail> details,SurveyFeedbackDetail object){
		if(object!=null){
			details.add(object);
		}
	}
	
	public static List<SurveyFeedbackDetail> loadOtherResults(List<String[]> datas,String feedbackId){
		List<SurveyFeedbackDetail> details = new ArrayList<SurveyFeedbackDetail>();
		for (int i = 3; i < datas.size(); i++) {
			String[] values = datas.get(i);
			String Id = values[1];
			String storeNo = values[2];
			String storeName = values[3];
			
			//主货架装饰
			SurveyFeedbackDetail d1 = createZHJ(139, values[5], feedbackId); //主货架装饰条
			SurveyFeedbackDetail d2 = createZHJ(140, values[6], feedbackId);//牙刷尝试小架
			SurveyFeedbackDetail d3 = createZHJ(141, values[7], feedbackId);//漱口水货架托盘
			SurveyFeedbackDetail d4 = createZHJ(142, values[8], feedbackId);////抗敏牙膏货架托盘
			HistoryDataImportUtil.add(details, d1);
			HistoryDataImportUtil.add(details, d2);
			HistoryDataImportUtil.add(details, d3);
			HistoryDataImportUtil.add(details, d4);
			
			//是否货架中间陈列
			SurveyFeedbackDetail a1 = createZHJ(143, values[9], feedbackId);//高露洁牙膏
			SurveyFeedbackDetail a2 = createZHJ(144, values[10], feedbackId);//高露洁牙刷
			SurveyFeedbackDetail a3 = createZHJ(145, values[11], feedbackId);//高露洁贝齿漱口水
			HistoryDataImportUtil.add(details, a1);
			HistoryDataImportUtil.add(details, a2);
			HistoryDataImportUtil.add(details, a3);
			
			//是否有陈列
			SurveyFeedbackDetail c1 = create(178, 12, 35, values[12], feedbackId); //牙刷陈列中心
			SurveyFeedbackDetail c2 = create(179, 12, 35, values[13], feedbackId); //牙刷落地架
			HistoryDataImportUtil.add(details, c1);
			HistoryDataImportUtil.add(details, c2);
			
			//陈列是否达标
			SurveyFeedbackDetail e1 = create(178, 12, 36, values[17], feedbackId); //牙刷陈列中心
			SurveyFeedbackDetail e2 = create(179, 12, 36, values[18], feedbackId); //牙刷落地架
			HistoryDataImportUtil.add(details, e1);
			HistoryDataImportUtil.add(details, e2);
			
			//促销员检查
			SurveyFeedbackDetail f1 = create(180, 13, 37, values[22], feedbackId);//本店有无高露洁促销员
			SurveyFeedbackDetail f2 = create(181, 13, 37, values[23], feedbackId);//促销员仪容仪表标准
			SurveyFeedbackDetail f3 = create(182, 13, 37, values[24], feedbackId);//本店有无舒客促销员
			SurveyFeedbackDetail f4 = create(183, 15, 59, values[25], feedbackId);//有几个舒客促销员
			HistoryDataImportUtil.add(details, f1);
			HistoryDataImportUtil.add(details, f2);
			HistoryDataImportUtil.add(details, f3);
			HistoryDataImportUtil.add(details, f4);
			
			//收银台数量
			SurveyFeedbackDetail g1 = create(146, 6, 8, values[26], feedbackId);
			HistoryDataImportUtil.add(details, g1);
			System.out.println(values[3]);
		}
		return details;
	}

	public static void main(String[] args) {
		String path = "D://Supplementary_Questionnaire_Feb 2015_20150004043207.xlsx";
	}
	
}
