package com.tcl.shbc.mina.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	private static String charset = "utf-8";
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String doPost(String url,Map<String,String> map){
		logger.info("请求的url:"+url);
		CloseableHttpClient httpClient = null;
        HttpPost httpPost = null; 
        String result = null;
        try{  
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);  
            //设置参数 
            if(null != map){
            	List<NameValuePair> list = new ArrayList<NameValuePair>();  
                Iterator iterator = map.entrySet().iterator();  
                while(iterator.hasNext()){
                    Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                    list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
                }  
                if(list.size() > 0){
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
                    httpPost.setEntity(entity);
                }
            }
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
            	logger.info("请求结果状态码:"+response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
        	logger.error("HttpClientUtil exception",ex);
        }finally {//释放
            if (httpClient != null) {
                try {  
                	httpClient.close();
                } catch (Exception e) {  
                	logger.error("HttpClientUtil exception",e);
                }
            }
        }
        return result;
    }
	
	public static String doGet(String url,String param){
		CloseableHttpClient httpClient = null;
        HttpGet httpGet = null; 
        String result = null;
        try{  
            httpClient = HttpClients.createDefault();
            //String sendUrl = url+"?"+URLEncoder.encode(param, "utf-8");
            String sendUrl =url+"?"+param;
            logger.info("请求的url:"+sendUrl);
            
            httpGet = new HttpGet(sendUrl);
            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
            	logger.info("请求结果状态码:"+response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
        	logger.error("HttpClientUtil exception",ex);
        }finally {//释放
            if (httpClient != null) {
                try {
                	httpClient.close();
                } catch (Exception e) {
                	logger.error("HttpClientUtil exception",e);
                }
            }
        }
        return result;
    }
	
	public static String doPostForJson(String url,String jsonStr){
		logger.info("请求的url:"+url);
		CloseableHttpClient httpClient = null;
        HttpPost httpPost = null; 
        String result = null;
        try{  
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);  
            
            StringEntity entity = new StringEntity(jsonStr,"utf-8"); 
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");  
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){
            	logger.info("请求结果状态码:"+response.getStatusLine());
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
        	logger.error("HttpClientUtil exception",ex);
        }finally {//释放
            if (httpClient != null) {
                try {  
                	httpClient.close();
                } catch (Exception e) {  
                	logger.error("HttpClientUtil exception",e);
                }  
            }
        }
        return result;  
    }
}
