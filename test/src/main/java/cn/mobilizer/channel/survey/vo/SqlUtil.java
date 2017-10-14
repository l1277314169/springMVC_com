package cn.mobilizer.channel.survey.vo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import cn.mobilizer.channel.survey.po.SurveyFeedback;
import cn.mobilizer.channel.survey.po.SurveyFeedbackDetail;
import cn.mobilizer.channel.util.DateTimeUtils;

public class SqlUtil {
	
	protected static  Logger log = Logger.getLogger(SqlUtil.class);

	private final static String MAIN_TALBE_NAME 	= "survey_feedback";
	private final static String DETAIL_TABLE_NAME 	= "survey_feedback_detail";
	
	public static void printSql(List<SurveyFeedbackDetail> detail,String fileName) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" insert into ");
			sql.append(DETAIL_TABLE_NAME);
			sql.append(" (detail_id,client_id,feedback_id,sub_survey_id,object_id,survey_parameter_id,col1,is_delete,value) values ");
			for (SurveyFeedbackDetail d : detail) {
				sql.append("('").append(d.getDetailId()).append("',");
				sql.append(d.getClientId()).append(",");
				sql.append("'").append(d.getFeedbackId()).append("',");
				sql.append(d.getSubSurveyId()).append(",");
				sql.append(d.getObjectId()).append(",");
				sql.append(d.getSurveyParameterId()).append(",");
				sql.append("'").append(d.getCol1()).append("',");
				sql.append(d.getIsDelete()).append(",");
				sql.append("'").append(d.getValue()).append("'),");
			}
			if (sql.toString().endsWith(",")) {
				sql = sql.deleteCharAt(sql.length() - 1);
				sql.append(";");
			}
			writer("D://colgate//"+fileName+".txt", sql.toString());
			log.info("SQL size: "+detail.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void printMainSql(List<SurveyFeedback> feedbacks,String fileName){
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" insert into ");
			sql.append(MAIN_TALBE_NAME);
			sql.append(" (feedback_id,client_id,client_user_id,feedback_no,feedback_date,survey_id,remark,pop_id,last_update_time) values ");
			for (SurveyFeedback s : feedbacks) {
				sql.append("('").append(s.getFeedbackId()).append("',");
				sql.append(s.getClientId()).append(",");
				sql.append(s.getClientUserId()).append(",");
				sql.append("'").append(s.getFeedbackNo()).append("',");
				sql.append("'").append(DateTimeUtils.formatTime(s.getFeedbackDate(), DateTimeUtils.yyyyMMddHHmmss)).append("',");
				sql.append(s.getSurveyId()).append(",");
				sql.append("'").append(s.getRemark()).append("',");
				sql.append("'").append(s.getPopId()).append("',");
				sql.append("'").append(DateTimeUtils.formatTime(s.getLastUpdateTime(), DateTimeUtils.yyyyMMddHHmmss)).append("'),");
			}
			if (sql.toString().endsWith(",")) {
				sql = sql.deleteCharAt(sql.length() - 1);
				sql.append(";");
			}
			writer("D://colgate//"+fileName+"_main.txt", sql.toString());
			log.info("Main SQL size: "+feedbacks.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 追加文件：使用FileWriter
	 * 
	 * @param fileName
	 * @param content
	 * @throws IOException 
	 */
	public static void writer(String fileName, String content) throws IOException {
		FileWriter writer = null;
		try {
			writer = new FileWriter(fileName, true);
			writer.write(content);
		}finally {
			writer.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		SqlUtil.writer("D://dev/test.txt", "测试文件");
	}
	
}
