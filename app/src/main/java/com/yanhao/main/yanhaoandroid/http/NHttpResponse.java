/**
 * 
 */

package com.yanhao.main.yanhaoandroid.http;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * The New Response Object for HttpTask
 * Each HttpResponse Message must extends it
 * @author lixiaohua01
 */
public abstract class NHttpResponse implements Serializable{

	//indicate response successfully received without any exception
    public static final int RESPONSE_STATUS_OK = 0;
    public static final int RESPONSE_STATUS_TIMEOUT = 1;
    public static final int RESPONSE_STATUS_NETWORK_SOCKETERROR = 2;
    public static final int RESPONSE_STATUS_IOERROR = 3;
    public static final int RESPONSE_STATUS_MALFORMEDURL =4;
    public static final int RESPONSE_STATUS_HTTPTASKERROR = 5;
    public static final int RESPONSE_STATUS_INVALIDFORMAT = 6;
	
    protected String mContentType;
    protected long mContentLength;
    protected String mContentEncoding;
	
    protected int mHttpRespCode;//http response code,such as 200,404,500
    protected int mRespStatus;
    
    public String getContentType() {
		return mContentType;
	}

	public void setContentType(String contentType) {
		this.mContentType = contentType;
	}
    
	public int getHttpRespCode() {
		return mHttpRespCode;
	}

	public void setHttpRespCode(int respCode) {
		this.mHttpRespCode = respCode;
	}

	public int getRespStatus() {
		return mRespStatus;
	}

	public void setRespStatus(int respStatus) {
		this.mRespStatus = respStatus;
	}

	public long getContentLength(){
		return mContentLength;
	}
	
	public void setContentLength(long length){
		mContentLength = length;
	}
	
	public String getContentEncoding(){
		return mContentEncoding;
	}
	
	public void setContentEncoding(String encoding){
		mContentEncoding = encoding;
	}
	protected String filter(String jsonMessage){
 	   String s=jsonMessage;
 	   s = s.replaceAll("\b", "");  
        s = s.replaceAll("\t", "");  
// 	   s = s.replaceAll("\n", "");
// 	   s = s.replaceAll("\r\n", "");
 	   s = s.replaceAll("\f", "");   
// 	   s = s.replaceAll("\r", "");
 	   return s;
    }
	
	private void unWrapByJson(String respContent) throws InvalidFormatException{
		throw new InvalidFormatException();
	}
	
	public void unWrapResponseContent(String respContent) throws InvalidFormatException
	{
		if (HttpHeaders.VALUE_CONTENT_TYPE_JSON.equals(mContentType)) {
			unWrapByJson(respContent);
		} else {
			unWrapByCustom(respContent);
		}
	}

	protected abstract void unWrapByCustom(String respContent) throws InvalidFormatException;
	/**
	 * invoke when debug mode used to build response without connect to server
	 * for response message,to overrride if need
	 */
	public abstract void testRespond();

	public void parseJson(JSONObject jsonObject) throws InvalidFormatException{}
}
