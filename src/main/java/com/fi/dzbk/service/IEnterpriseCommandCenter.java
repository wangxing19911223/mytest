package com.fi.dzbk.service;

import net.sf.json.JSONObject;

/**
 * 总控中心
 * @author wxx1223
 *
 */
public interface IEnterpriseCommandCenter {
	
	/**
	 * 接受数据生产中心的请求,返回一个数据处理中心的标识
	 * @param sign
	 * @return
	 */
	public String receive(String url);
	
	/**
	 * 接受采集器的请求,返回一个数据处理中心的标识
	 * @param type
	 * @param name
	 * @return
	 */
	public String receive(int type,String name);
	
	/**
	 * 接受数据采集中心的数据信息,记录
	 * @param sign
	 * @param json
	 */
	public void receive(String sign,JSONObject json);
}
