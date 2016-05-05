package com.fi.dzbk.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.fi.common_mysql.bean.SeedLink;
import com.fi.common_mysql.seedlink.service.ISeedLinkService;
import com.fi.dzbk.service.IProduceDataCenter;

/**
 * 定时加载url进行数据的生产
 * @author wxx1223
 *
 */
public class ProduceDataCenter extends TimerTask implements IProduceDataCenter,InitializingBean{
	
	private Log log = LogFactory.getLog(getClass());
	
	//巡逻时间(每隔多长时间loadUrl一次)
	//分钟
	private int patrolTime = 1;
	
	public void setPatrolTime(int patrolTime) {
		this.patrolTime = patrolTime;
	}

	public void afterPropertiesSet() throws Exception {
		new Timer().schedule(this, 0, patrolTime*60*1000);
	}

	@Override
	public void run() {
		log.info("load url....");
		loadUrl();
	}

	//起止时间
	private Date sdate;
	private Date edate;
	private ISeedLinkService seedLinkService;
	public void setSeedLinkService(ISeedLinkService seedLinkService) {
		this.seedLinkService = seedLinkService;
	}
	private List<SeedLink> seedLinks = new ArrayList<SeedLink>();
	/**
	 * load url
	 */
	public void loadUrl() {
		//设置起始时间
		if(sdate == null){
			Calendar can = Calendar.getInstance();
			can.set(Calendar.YEAR, 1970);
			sdate = can.getTime();
		}
		if(edate == null){
			edate = new Date();
		}
		//查找新加入数据库的seedlink
		seedLinks = seedLinkService.find(sdate, edate);
		//重置起始时间
		sdate = edate;
		edate = null;
	}
}
