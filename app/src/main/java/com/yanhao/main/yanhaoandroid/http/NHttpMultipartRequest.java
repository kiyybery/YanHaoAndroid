/**
 * 
 */
package com.yanhao.main.yanhaoandroid.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;

/**
 * @author rax
 *
 */
public abstract class NHttpMultipartRequest extends NHttpRequest {

	private static final String BOUNDARYSTR = "U3tY3oc19Lcq6TyEY2WtEsVeNK1TbOnYpN";
	private static final String BOUNDARY = "--"+BOUNDARYSTR;
	private static final String BOUNDARY_END = "--"+BOUNDARYSTR+"--";
	private static int sBytesToUpdateProgress = 10 * 1024;
	
	private List<MutilpartFileItem> mMutilpartFileList;
	private String mParameterBody;
	private Map<String,String> mMutilpartParametersMap;
	private long mContentLength;
	private NHttpProgressListener mProgressListener;// deal with the progressdetails
	
	public NHttpMultipartRequest(String url){
		super(url,HttpHeaders.VALUE_CONTENT_TYPE_MULTIPART+BOUNDARYSTR);
		mMutilpartFileList = new ArrayList<MutilpartFileItem>();
		mMutilpartParametersMap = new HashMap<String,String>();
	}

	public void setProgressListener(NHttpProgressListener progressListener) {
		mProgressListener = progressListener;
	}
	
	public void addMutilpartFile(MutilpartFileItem mutilpartFileItem){
		mMutilpartFileList.add(mutilpartFileItem);
	}
	
	public void addMutilpartParamters(String name,String value){
		if(value == null){
			mMutilpartParametersMap.put(name, "");
		}else{
			try {
				mMutilpartParametersMap.put(name, URLEncoder.encode(value, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class MutilpartFileItem{
		public File uploadFile;
		public String mimeType;
		public String paramterName;
		public String fileName;
		
		public long getFileSize(){
			return uploadFile.length();
		}
		
		public String getPartTextContent(){
			StringBuilder sb = new StringBuilder();
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data; name=\""+paramterName+"\"; filename=\""+fileName+"\"");
			sb.append("\r\n");
			sb.append("Content-Type: "+mimeType);
//			sb.append("\r\n");
//			sb.append("Content-Transfer-Encoding: binary");
			sb.append("\r\n\r\n");
			return sb.toString();
		}
	}
	
	public long getContentLength(){
		long contentLength = 0;
		try{
			if(!TextUtils.isEmpty(mParameterBody)){
				contentLength += mParameterBody.getBytes("utf-8").length;
			}
			for(int i = 0;i < mMutilpartFileList.size();i++){
				MutilpartFileItem fileItem = mMutilpartFileList.get(i);
				contentLength += fileItem.getPartTextContent().getBytes("utf-8").length;
				contentLength += fileItem.getFileSize();
				contentLength += "\r\n\r\n".getBytes("utf-8").length;
			}
			contentLength += BOUNDARY_END.getBytes("utf-8").length;
			contentLength += "\r\n".getBytes("utf-8").length;
		} catch(Exception e){
			e.printStackTrace();
		}
		mContentLength = contentLength;
		return contentLength;
	}
	
	public void buildParameterBody(){
		if(mMutilpartParametersMap == null){
			return;
		}
		StringBuilder sb = new StringBuilder();
		Iterator<String> iterator = mMutilpartParametersMap.keySet().iterator();
		while(iterator.hasNext()){
			String name = iterator.next();
			String value = mMutilpartParametersMap.get(name);
		    sb.append(BOUNDARY);
		    sb.append("\r\n");
		    sb.append("Content-Disposition: form-data; name=\""+name+"\"");
		    sb.append("\r\n\r\n");
		    sb.append(value);
		    sb.append("\r\n");
		}
		mParameterBody = sb.toString();
	}
	
	public void writeMutilpartData(OutputStream outputStream){
		BufferedOutputStream bos = new BufferedOutputStream(outputStream);
		int postBytes = 0;
		int segmentLength = 0;
		StringBuilder logSb = new StringBuilder();
		try{
			if(!TextUtils.isEmpty(mParameterBody)){
				bos.write(mParameterBody.getBytes("utf-8"));
				logSb.append(mParameterBody);
				int bodyLength = mParameterBody.getBytes("utf-8").length;
				postBytes += bodyLength;
				segmentLength += bodyLength;
				if(mProgressListener != null){
					if(segmentLength / sBytesToUpdateProgress == 1){
						mProgressListener.onUploadProgressUpdate(postBytes, mContentLength);
						segmentLength = 0;
					}
				}
			}
			for(int i = 0;i < mMutilpartFileList.size();i++){
				BufferedInputStream bis = null;
				try {
					//write part text
					MutilpartFileItem fileItem = mMutilpartFileList.get(i);
					bos.write(fileItem.getPartTextContent().getBytes("utf-8"));
					logSb.append(fileItem.getPartTextContent());
					int partTextLength = fileItem.getPartTextContent().getBytes("utf-8").length;
					postBytes += partTextLength;
					segmentLength += partTextLength;
					if(mProgressListener != null){
						if(segmentLength / sBytesToUpdateProgress == 1){
							mProgressListener.onUploadProgressUpdate(postBytes, mContentLength);
							segmentLength = 0;
						}
					}
					//write file
					bis = new BufferedInputStream(new FileInputStream(fileItem.uploadFile));
					byte[] buf = new byte[1024];
					int len = 0;
					while((len = bis.read(buf)) != -1){
						bos.write(buf,0,len);
						postBytes += len;
						segmentLength += len;
						if(mProgressListener != null){
							if(segmentLength / sBytesToUpdateProgress == 1){
								mProgressListener.onUploadProgressUpdate(postBytes, mContentLength);
								segmentLength = 0;
							}
						}
					}
					bos.write("\r\n\r\n".getBytes("utf-8"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch(Exception e){
					e.printStackTrace();
				} finally{
					if(bis != null){
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			bos.write(BOUNDARY_END.getBytes("utf-8"));
			bos.write("\r\n".getBytes("utf-8"));
			bos.flush();
			if(mProgressListener != null){
				mProgressListener.onUploadProgressUpdate(mContentLength, mContentLength);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
