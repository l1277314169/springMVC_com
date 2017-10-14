package cn.mobilizer.channel.comm.mybatis;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Chain;
import cn.mobilizer.channel.base.po.Channel;
import cn.mobilizer.channel.base.po.Client;

/**
 * MyBatis的Dao基类
 * 
 * @author yidan.tian
 */
@Repository
public class MyBatisDao extends SqlSessionTemplateDaoSupport {

	private static final Log LOG = LogFactory.getLog(MyBatisDao.class);

	protected int maxRows = 1001;
	protected int maxCompareRows = 10001;
	protected int maxRowsForReport = 50000;
	private String namespaceName;

	public MyBatisDao() {
		
	}

	public MyBatisDao(String namespaceName) {
		super();
		this.namespaceName = namespaceName;
	}
	
	
	private String createStatementName(String id) {
		return namespaceName + "." + id;
	}

	protected int insert(String key, Object object) {
		if (object != null) {
			return getSqlSession().insert(createStatementName(key), object);
		}
		return 0;
	}
	
	protected int update(String key, Object object) {
		if (object != null) {
			return getSqlSession().update(createStatementName(key), object);
		}
		return 0;
	}

	protected int delete(String key, Serializable id) {
		if (id != null) {
			return getSqlSession().delete(createStatementName(key), id);
		}
		return 0;
	}

	protected int delete(String key, Object object) {
		if (object != null) {
			return getSqlSession().delete(createStatementName(key), object);
		}
		return 0;
	}

	@SuppressWarnings({ "unchecked" })
	protected <T> T get(String key, Object params) {
		if (params != null) {
			return (T) getSqlSession().selectOne(createStatementName(key), params);
		} else {
			return null;
		}
	}
	
	/**
	 * 重载一个无参数的get方法
	 * @author yidan.tian
	 * @param key
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	protected <T> T get(String key) {
		return (T) getSqlSession().selectOne(createStatementName(key));
	}

	protected <T> List<T> getList(String key) {
		return getSqlSession().selectList(createStatementName(key));
	}

	protected <T> List<T> getList(String key, Object params) {
		if (params != null) {
			return getSqlSession().selectList(createStatementName(key), params);
		} else {
			return null;
		}
	}

	// 允许参数传入null
	protected <T> List<T> getListFree(String key, Object params) {
		System.out.println(getSqlSession().selectList(createStatementName(key), params).size());
		return getSqlSession().selectList(createStatementName(key), params);
	}
	
	/**
	 * 此方法最多返回1001条数据
	 * @param statementName
	 * @return
	 * @throws DataAccessException
	 */
	protected <T> List<T> queryForList(String statementName) throws DataAccessException {
		return queryForList(statementName, null);
	}

	/**
	 * 此方法最多返回1001条数据
	 * @param statementName
	 * @param parameterObject
	 * @return
	 * @throws DataAccessException
	 */
	protected <T> List<T> queryForList(final String statementName, final Object parameterObject) throws DataAccessException {
		if (parameterObject != null) {
			List<T> result = getSqlSession().selectList(createStatementName(statementName), parameterObject, new RowBounds(0, maxRows));
			if ((result != null) && (result.size() == maxRows)) {
				LOG.warn("SQL Exception: result size is greater than the max rows, " + namespaceName + "." + statementName);
			}
			return result;
		} else {
			return getSqlSession().selectList(statementName);
		}
	}
	
	/**
	 * 此方法最多返回1001条数据
	 * @param statementName
	 * @param skipResults
	 * @param maxResults
	 * @return
	 * @throws DataAccessException
	 */
	protected <T> List<T> queryForList(String statementName, int skipResults, int maxResults) throws DataAccessException {

		if ((maxResults - skipResults) >= maxRows) {
			maxResults = skipResults + maxRows;
			LOG.warn("SQL Exception: result size is greater than the max rows, " + createStatementName(statementName));
		}

		return queryForList(statementName, null, skipResults, maxResults);
	}
	
	/**
	 * 此方法最多返回1001条数据
	 * @param statementName
	 * @param parameterObject
	 * @param skipResults
	 * @param maxResults
	 * @return
	 * @throws DataAccessException
	 */
	protected <T> List<T> queryForList(final String statementName, final Object parameterObject, final int skipResults, final int maxResults) throws DataAccessException {

		int tempMaxResults = maxResults;
		if ((maxResults - skipResults) >= maxRows) {
			tempMaxResults = skipResults + maxRows;
			LOG.warn("SQL Exception: result size is greater than the max rows, " + createStatementName(statementName));
		}
		return getSqlSession().selectList(createStatementName(statementName), parameterObject, new RowBounds(skipResults, tempMaxResults));
	}

	/**
	 * 数据量比较大的报表导出请用这个接口
	 * @param statementName
	 * @return
	 * @throws DataAccessException
	 */
	protected <T> List<T> queryForListForReport(String statementName) throws DataAccessException {
		return queryForListForReport(statementName, null);
	}

	/**
	 * 此方法最多返回50000条数据
	 * 数据量比较大的报表导出请用这个接口
	 * @param statementName
	 * @param parameterObject
	 * @return
	 * @throws DataAccessException
	 */
	protected <T> List<T> queryForListForReport(final String statementName, final Object parameterObject) throws DataAccessException {

		List<T> result = getSqlSession().selectList(createStatementName(statementName), parameterObject, new RowBounds(0, maxRowsForReport));

		if ((result != null) && (result.size() == maxRowsForReport)) {
			LOG.warn("SQL Exception: result size is greater than the max rows, " + statementName);
		}
		return result;
	}

	/**
	 * 数据量比较大的报表导出请用这个接口
	 * @param statementName
	 * @param parameterObject
	 * @param isForReportExport
	 * @return
	 * @throws DataAccessException
	 */
	protected <T> List<T> queryForList(final String statementName, final Object parameterObject, final boolean isForReportExport) throws DataAccessException {

		int maxRowsTemp = maxRows;
		if (isForReportExport) {
			maxRowsTemp = maxRowsForReport;
		}

		List<T> result = getSqlSession().selectList(createStatementName(statementName), parameterObject, new RowBounds(0, maxRowsTemp));
		if ((result != null) && (result.size() == maxRowsTemp)) {
			LOG.warn("SQL Exception: result size is greater than the max rows, " + statementName);
		}
		return result;
	}
	
	/**
	 * 此方法最多返回10001条数据
	 * 
	 * @param statementName		xml里SQL的ID
	 * @param parameterObject	绑定到SQL中的参数
	 * @param mapKey			Map 的key 必须是数据表中的某列
	 * @return					
	 * <pre>
	 * 		流程	
	 * 			1. 根据 statementName 在配置文件中查找相应的SQL 语句
	 * 			2. 将参数绑定到 SQL 语句中
	 * 			3. 查询返回
	 * 				返回一个 MAP ，Kye： mapKey
	 * 	
	 * </pre>
	 * @throws DataAccessException
	 */
	protected <K, V> Map<K, V> queryForMap(final String statementName, final Object parameterObject, final String mapKey) throws DataAccessException {
		int maxRowsTemp = maxCompareRows;
		Map<K, V> result = getSqlSession().selectMap(statementName, parameterObject, mapKey);
		if ((result != null) && (result.size() == maxRowsTemp)) {
			LOG.warn("SQL Exception: result size is greater than the max rows, " + statementName);
		}
		return result;
	}
	
	protected <T>  List<T> queryForListPosition(final String statementName){
		List<T> result = getSqlSession().selectList(createStatementName(statementName));
		if(result !=null && result.size()>0){
			return result;
		}
		return null;
	}
	
	protected List<Channel> queryForListChannel(final String statementName,Object parameterObject){
		List<Channel> result = getSqlSession().selectList(createStatementName(statementName),parameterObject);
		if(result !=null && result.size()>0){
			return result;
		}
		return null;
		
	}
	
	protected List<Chain> queryForListChain(final String statementName,Object parameterObject){
		List<Chain> result = getSqlSession().selectList(createStatementName(statementName),parameterObject);
		if(result !=null && result.size()>0){
			return result;
		}
		return null;
		
	}
	
}
