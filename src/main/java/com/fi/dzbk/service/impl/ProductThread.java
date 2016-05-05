package com.fi.dzbk.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fi.common_mysql.bean.SeedLink;
import com.fi.dzbk.bean.Page;
import com.fi.dzbk.service.IEnterpriseCommandCenter;
import com.fi.http.HttpResponseWrapper;
import com.fi.http.IHttpClientService;
import com.fi.link.util.LinkExpressionParser;

/**
 * 生产数据的线程(供数据生产中心调用)
 * @author wxx1223
 *
 */
public class ProductThread extends Thread implements Runnable{


	private Log log = LogFactory.getLog(getClass());
	
	private IHttpClientService httpClientService;
	
	//总控中心
	private IEnterpriseCommandCenter enterpriseCommandCenter;
	
	private List<SeedLink> seedLinks;
	
	@Override
	public void run() {
		if(seedLinks  != null && seedLinks.size() > 0){
			for (SeedLink sl : seedLinks) {
				String url = sl.getLinkUrl();
				//conver 
				url = LinkExpressionParser.parseLinkRegexString(url, new Date());
				Page page = checkUpdate(url);
				if(page != null){
					//将page post 到总控中心获取数据处理中心的标识
					String sign = enterpriseCommandCenter.receive(url);
					
					if(sign != null){
						
					}
				}else{
					log.warn(String.format("%s is not update.......",url));
				}
			}
		}
	}
	
	
	/**
	 * 总控中心的url
	 */
	private String enterpriseCommandCenterUrl;
	public void setEnterpriseCommandCenterUrl(String enterpriseCommandCenterUrl) {
		this.enterpriseCommandCenterUrl = enterpriseCommandCenterUrl;
	}

//	/**
//	 * 将url post 到总控中心获取数据处理中心的标识
//	 * @param url
//	 * @return
//	 */
//	public String getSign(String url){
//		HttpResponseWrapper res = httpClientService.get(url, null, null);
//		if(res.getStatusCode() == 200){
//			
//		}
//		return null;
//	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	private Page checkUpdate(String url){
		
		HttpResponseWrapper res = httpClientService.get(url, null, null);
		int statusCode = res.getStatusCode();
		if(statusCode == 200){
			Page page = new Page();
			page.setContentHtml(new String(res.getContent()));
			page.setUrl(url);
			//从这产生的page没有parentUrl,有contentHtml
			return page;
		}
		return null;
	}
}
