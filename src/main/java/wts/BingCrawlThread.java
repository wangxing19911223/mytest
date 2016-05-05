package wts;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fi.http.HttpClientService;
import com.fi.http.HttpResponseWrapper;

public class BingCrawlThread {
	private static HttpClientService httpService = new HttpClientService();
	private static Log log = LogFactory.getLog(Wts.class);
	private static List<String> urls = new ArrayList<String>();
	public List<String> crawl(String url) {
		try {
			HttpResponseWrapper res = httpService.get(url, null, null);
			if(res != null && res.getContent() != null && res.getContent().length > 0){
				String html = new String(res.getContent());
				Document doc = Jsoup.parse(html);
				//
				List<String> list = getContent(doc);
				urls.addAll(list);
				//鑾峰彇涓嬩竴椤电殑url
				Elements ns = doc.getElementsByClass("sb_pagN");
				if(ns != null && ns.size() > 0){
					for (Element n : ns) {
						String text = n.attr("title");
						if("下一页".endsWith(text)){
							String next = n.attr("href");
							log.info(" bing crawl next page ..... ");
							next = "http://cn.bing.com"+next;
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							crawl(next);
						}
					}
				}
			}
		} catch (Exception e) {
			try {
				Thread.sleep(1000*2);
				crawl(url);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		return urls;
	}
	private List<String> getContent(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}
}
