/**   
* @Title: KeyValueVo.java 
* @Package cn.mobilizer.channel.posm.vo 
* @author 仪动信息技术（上海）有限公司
* @date 2015年10月9日 上午11:20:28 
* @version V1.0   
*/
package cn.mobilizer.channel.posm.vo;

/** 
 * @ClassName: KeyValueVo 
 * @Description: 
 * @author pengwei
 * @date 2015年10月9日 上午11:20:28 
 *  
 */
public class KeyValueVo {
	
	private Object name;
	
	private Object id;
	
	

	public KeyValueVo() {
		super();
	}

	public KeyValueVo(Object name, Object id) {
		super();
		this.name = name;
		this.id = id;
	}

	public Object getName() {
		return name;
	}

	public void setName(Object name) {
		this.name = name;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

}
