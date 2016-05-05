package com.fi.dzbk.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.fi.dzbk.service.IHarvesterService;
import com.fi.selenium.pool.util.ThreadUtil;

/**
 * 
 * @author wxx1223
 *
 */
public class HarvesterGroup implements InitializingBean{
	
	private List<IHarvesterService> harvesters;

	public void setHarvesters(List<IHarvesterService> harvesters) {
		this.harvesters = harvesters;
	}

	private Log log = LogFactory.getLog(getClass());
	public void afterPropertiesSet() throws Exception {
		if(harvesters == null || harvesters.size() == 0){
			log.warn("请配置harvester的工作线程。。。。。。");
			return;
		}
		
		ThreadUtil.sleep(1000*10);
		
		for (int i = 0; i < harvesters.size(); i++) {
//			harvesters.get(i).
			log.info("开始工作!!!!!"+harvesters.get(i));
		}
		
	}
}
