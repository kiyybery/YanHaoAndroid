/**
 * 
 */

package com.yanhao.main.yanhaoandroid.http;


import android.content.Context;
import android.text.TextUtils;

import com.yanhao.main.yanhaoandroid.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author lixiaohua01
 */
public abstract class NHttpProxy {
	
	private final static String TAG = NHttpProxy.class.getSimpleName();	
	private static Map<Class,String> urlMap;
    public static Map<Class, String> getUrlMap() {
		return urlMap;
	}

	private static Map<Class,Class> respMap;
    
    public static void sendRequest(NHttpRequest request,
            NHttpCallback callback, Context context,boolean mainCallback,boolean debugMode) {  
    	
        NHttpAsyncTask task = new NHttpAsyncTask(context);
        task.setCallback(callback,mainCallback);
        task.setDebugMode(debugMode);
        task.execute(request);
    }
    
    public static NHttpResponse sendRequestSync(NHttpRequest request,
            Context context) {

    	NHttpAsyncTask task = new NHttpAsyncTask(context);
    	return task.doInBackground(request);
    }
    
    private static String filter(String jsonMessage){
  	   String s=jsonMessage;
  	   s = s.replaceAll("\b", "");  
       s = s.replaceAll("\t", "");  
  	   s = s.replaceAll("\f", "");   
  	   return s;
     }
    
    public static void buildResponse(NHttpRequest req,NHttpResponse resp,int httpRespCode,String contentType,byte[] content) {
         try {
        	resp.setHttpRespCode(httpRespCode);
         	if(httpRespCode != 200){
         		return;
         	}
            if(isHtmlOrWml(contentType)){
            	if(LogUtil.DDBG){
            		LogUtil.d(TAG, "invalid response format,contentType ="+contentType);
            	}
            	resp.setRespStatus(NHttpResponse.RESPONSE_STATUS_INVALIDFORMAT);
             	return;
            }
            String s = new String(content, "utf-8");
            if (LogUtil.DDBG) {
            	LogUtil.d(TAG, "response string:"+s);
            }
//            authFilter(resp,s);
            resp.unWrapResponseContent(s);
         } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
             if(LogUtil.DDBG){
         		LogUtil.d(TAG, "invalid response encoding");
         	 }
          	 return ;
         } catch (InvalidFormatException e) {
             e.printStackTrace();
             if(LogUtil.DDBG){
          		LogUtil.d(TAG, "invalid response format,json paser error");
          	 }
             return;
         }
    	 return;
    }
    
    public static void buildResponse(NHttpRequest req, NHttpResponse resp,int httpRespCode,String contentType,InputStream inputStream,long contentLength) {
    	if(resp == null){
    		return;
    	}
         try {
        	 resp.setHttpRespCode(httpRespCode);
         	if(httpRespCode != 200){
         		return;
         	}
            if(isHtmlOrWml(contentType)){
            	if(LogUtil.DDBG){
            		LogUtil.d(TAG, "invalid response format,contentType ="+contentType);
            	}
            	resp.setRespStatus(NHttpResponse.RESPONSE_STATUS_INVALIDFORMAT);
             	return;
            }
            ((NHttpStreamDownload)resp).readStream(inputStream,contentLength);
         }catch (Exception e) {
             e.printStackTrace();
             if(LogUtil.DDBG){
          		LogUtil.d(TAG, "build error");
          	 }
             return;
         }
    	 return;
    }
    
    public static NHttpResponse buildDebugResponse(NHttpResponse resp){
    	resp.testRespond();
    	return resp;
    }
    
    /**
     * create a response message matched with the request
     * @param req
     * @return
     * @throws MatchResponseFailedException
     */
    public static NHttpResponse matchResponse(NHttpRequest req) throws MatchResponseFailedException{
    	Class<?> cls = respMap.get(req.getClass());
		if(cls == null)
			throw new MatchResponseFailedException("no response match with the request,check the mapping config");
		try {
			return (NHttpResponse)cls.newInstance();
		} catch (Exception e) {
			throw new MatchResponseFailedException("match response failed",e);
		} 
    }
    
	public static boolean isHtmlOrWml(String contentType){
		if(!TextUtils.isEmpty(contentType)){
			if(contentType.indexOf("text/html")!=-1||contentType.indexOf("text/vnd.wap.wml")!=-1)
				return true;
		}
		return false;
	}
	
	static{
		urlMap = new HashMap<Class,String>();
		//add new url map here,request class as key,url as value
		//sample : urlMap.put(GetUserInfoReqMessage.class,"http://y.sdo.com/getUserInfo");
		
		respMap = new HashMap<Class,Class>();
		//add response map here,request class as key, response class as value
		//sample：respMap.put(NFriendListReqMessage.class, NFriendListRespMessage.class);
        }
}
