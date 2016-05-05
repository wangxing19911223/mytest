package wts;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fi.http.HttpClientService;
import com.fi.http.HttpResponseWrapper;

public class Wts {
	private static Log log = LogFactory.getLog(Wts.class);
	public static void main(String[] args) {
		String url1 = "http://auto.news18a.com/";
		//1.鑾峰彇鎵€鏈夌殑kw
		List<String> kws = getTitles(url1);
		
		log.info("kws length is "+kws.size());
		
		if(kws != null && kws.size() > 0){
			BlockingQueue<String> kwsQueue = new ArrayBlockingQueue<String>(1000);
			for (String kw : kws) {
				kwsQueue.add(kw);
			}
//			for (int i = kws.size()-1; i >= 0; i--) {
//				kwsQueue.add(kws.get(i));
//			}
		}
		
		
		//鑾峰彇siteName
//		List<String[]> list = ExcelUtil.readExcel("d://wts1.xls");
//		if(list != null){
//			for (String[] s : list) {
//				String url = s[2];
//				log.info("get url "+url);
//				try {
//					HttpResponseWrapper res = httpService.get(url, null, null);
//					String html = new String(res.getContent());
//					Document doc = Jsoup.parse(html);
//					s[0] = converTitle(doc.title());
//				} catch (Exception e) {
//					log.error("error..........");
//				}
//			}
//		}
//		ExcelUtil.expert2Excel(list, "wts鏈€缁堢増");
		
	}

	/**
	 * 鍘婚櫎title涓殑_鍜�-
	 * @param title
	 * @return
	 */
	public static String converTitle(String title){
		int index = title.indexOf("-");
		if(index != -1){
			title = title.substring(0, index);
			return title;
		}
		index = title.indexOf("_");
		if(index != -1){
			title = title.substring(0, index);
			return title;
		}
		return title;
	}
	private static HttpClientService httpService = new HttpClientService();
	/**
	 * 鑾峰彇鎸囧畾url涓竴閮ㄥ垎鐨勬爣棰�
	 * @param url
	 * @return
	 */
	public static List<String> getTitles(String url){
		HttpResponseWrapper res = httpService.get(url, null, null);
		String html = new String(res.getContent());
		Document doc = Jsoup.parse(html);
		Element body = doc.body();
		Elements as = body.select("ul>li>a");
		if(as != null){
			List<String> titles = new ArrayList<String>();
			for (Element a : as) {
				String text = a.text();
				if(text!= null && text.length() > 10){
					
					String sub = text.substring(text.length()-8);
					if(!sub.matches("\\d{8}$")){
						titles.add(text);
					}
				}
			}
			return titles;
		}
		return null;
	}
}
