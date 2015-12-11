/**
 * 
 */
package com.yanhao.main.yanhaoandroid.http;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.URLEncoder;

import org.json.JSONObject;


import android.text.TextUtils;

import com.yanhao.main.yanhaoandroid.network.ProtocolFieldAnnotation;

/**
 * the new Request Object for HttpTask Each
 * HttpRequest Message must extends it
 * 
 * @author lixiaohua01
 * 
 */
public abstract class NHttpRequest implements Serializable {

	// values of request method，default with post
	public static final String METHOD_POST = "POST";
	public static final String METHOD_GET = "GET";

	protected String mReqUrl;
	protected String mReqMethod;
	protected String mContentType;

	protected byte[] mPostBytes;// the bytes to upload
	protected Object[] mExtraValue;// the extra data pass to the callback
	protected boolean annotationReflected = true;// whether to use annotation reflection

	protected NHttpRequest(String url)
			throws IllegalArgumentException {
		if (TextUtils.isEmpty(url)) {
			throw new IllegalArgumentException(
					"the url of request can't be null or be with zero length");
		}
		this.mReqUrl = url;
		mReqMethod = METHOD_POST;
		mContentType = HttpHeaders.VALUE_CONTENT_TYPE_FORM;
	}

	protected NHttpRequest(String url, String contentType) {
		if (TextUtils.isEmpty(url)) {
			throw new IllegalArgumentException(
					"the url of request can't be null or be with zero length");
		}
		this.mReqUrl = url;
		this.mReqMethod = METHOD_POST;
		this.mContentType = contentType;
	}

	
	public void setReqMethod(String reqMethod) {
		mReqMethod = reqMethod;
	}

	public void setContentType(String contentType) {
		mContentType = contentType;
	}

	public Object[] getExtraValue() {
		return mExtraValue;
	}

	public void setExtraValue(Object[] extraValue) {
		this.mExtraValue = extraValue;
	}

	public String getReqMethod() {
		return mReqMethod;
	}

	public String getReqURL() {
		return mReqUrl;
	}

	public String getContentType() {
		return mContentType;
	}

	public byte[] getPostBytes() {
		return mPostBytes;
	}

	public void setPostBytes(byte[] postBytes) {
		this.mPostBytes = postBytes;
	}
	
	public boolean isAnnotationReflected() {
		return annotationReflected;
	}

	public void setAnnotationReflected(boolean annotationReflected) {
		this.annotationReflected = annotationReflected;
	}

	/**
	 * build the request to form content
	 * 
	 * @return
	 */
	private String buildToFormContent() {
		StringBuilder sb = new StringBuilder();
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			try {
				if (!fields[i]
						.isAnnotationPresent(ProtocolFieldAnnotation.class)) {
					continue;
				}
				fields[i].setAccessible(true);
				ProtocolFieldAnnotation pfa = fields[i]
						.getAnnotation(ProtocolFieldAnnotation.class);
				sb.append(pfa.name());
				sb.append("=");
				String value = "";
				if (fields[i].getGenericType().toString().equals("int")) {
					value = String.valueOf(fields[i].getInt(this));
				} else if (fields[i].getGenericType().toString().equals("byte")) {
					value = String.valueOf(fields[i].getByte(this));
				} else if (fields[i].getGenericType().toString()
						.equals("short")) {
					value = String.valueOf(fields[i].getShort(this));
				} else if (fields[i].getGenericType().toString().equals("long")) {
					value = String.valueOf(fields[i].getLong(this));
				} else if (fields[i].getGenericType().toString()
						.equals("float")) {
					value = String.valueOf(fields[i].getFloat(this));
				} else if (fields[i].getGenericType().toString()
						.equals("double")) {
					value = String.valueOf(fields[i].getDouble(this));
				} else if (fields[i].getGenericType().toString()
						.equals("class java.lang.String")) {
					value = (String) fields[i].get(this);
				}
				sb.append(URLEncoder.encode(value, "utf-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (i < fields.length - 1) {
				sb.append("&");
			}
		}
		return sb.toString();
	}

	/**
	 * create the content string using reflection
	 * @return
	 */
	private String buildToSimpleJsonContent(Object obj){
		JSONObject jsonObject = new JSONObject();
		Field[] fields = this.getClass().getDeclaredFields();
	    for(int i = 0 ;i<fields.length;i++){
	    	try{
	    		if(!fields[i].isAnnotationPresent(ProtocolFieldAnnotation.class)){
	    			continue;
	    		}	
	    		fields[i].setAccessible(true);
	    		ProtocolFieldAnnotation pfa = fields[i].getAnnotation(ProtocolFieldAnnotation.class);
	    	    if(fields[i].getGenericType().toString().equals("int")){
	    	    	jsonObject.put(pfa.name(), fields[i].getInt(this));
	    	    }else if(fields[i].getGenericType().toString().equals("byte")){
	    	    	jsonObject.put(pfa.name(), fields[i].getInt(this));
	    	    }else if(fields[i].getGenericType().toString().equals("short")){
	    	    	jsonObject.put(pfa.name(), fields[i].getInt(this));
	    	    }else if(fields[i].getGenericType().toString().equals("long")){
	    	    	jsonObject.put(pfa.name(), fields[i].getLong(this));
	    	    }else if(fields[i].getGenericType().toString().equals("float")){
	    	    	jsonObject.put(pfa.name(), fields[i].getDouble(this));
	    	    }else if(fields[i].getGenericType().toString().equals("double")){
	    	    	jsonObject.put(pfa.name(), fields[i].getDouble(this));
	    	    }else if(fields[i].getGenericType().toString().equals("class java.lang.String")){
	    	    	String value = (String)fields[i].get(this);
	    	    	jsonObject.put(pfa.name(), value);
	    	    }else {
	    	    	ParameterizedType  pType = (ParameterizedType)fields[i].getGenericType();
	    	    	System.out.println(pType.getActualTypeArguments()[0]);
	    	    }
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
		return jsonObject.toString();
	}
	
	
	protected String encodeParameter(String name, String value) {
		StringBuilder sb = new StringBuilder();
		sb.append(name).append("=");
		try {
			if (value == null) {
				sb.append("");
			} else {
				sb.append(URLEncoder.encode(value, "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * build the request to post content
	 * @return
	 */
	public String buildPostContent() {
		if (annotationReflected && HttpHeaders.VALUE_CONTENT_TYPE_FORM.equals(mContentType)) {
			return buildToFormContent();
		} else if (annotationReflected && HttpHeaders.VALUE_CONTENT_TYPE_JSON.equals(mContentType)) {
			return buildToSimpleJsonContent(this);
		} else{
			return buildToCustomContent();
		}
	}
	
	/**
	 * build the request to custom content ,if needed
	 * @return
	 */
	protected abstract String buildToCustomContent();
}
