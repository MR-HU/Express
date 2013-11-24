package com.zjut.express.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * {@code HttpPostRequest} HTTP POST请求<br>
 * {@link #getDataFromWebServer}
 * 
 * @author HuGuojun
 * @date 2013-11-20 下午3:50:42
 * @modify
 * @version 1.0.0
 */
public class HttpPostRequest {
	
	public static HttpPost getHttpPost(String url){
		HttpPost request = new HttpPost(url);
		return request;
	}
	
	public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException,IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	
	/**
	 * 获取网络数据
	 * @param url
	 * @return String
	 * @exception
	 */
	public static String getDataFromWebServer(String url){
		HttpPost request = HttpPostRequest.getHttpPost(url);
		String result = null;
		try{
			HttpResponse response = HttpPostRequest.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode() == 200){
				result = EntityUtils.toString(response.getEntity());
			}
		}catch(ClientProtocolException e){
			result = "net_err";
		}catch(IOException e){
			result = "net_err";
		}catch(Exception e){
			result = "net_err";
		}
		return result;
	}
	
}
