/**
 * 
 */
package com.yanhao.main.yanhaoandroid.http;

import java.io.InputStream;

/**
 * A Upload Stream to be written
 * HttpRequest Message to post large bytes must implement it,
 * for details control
 * @author lixiaohua
 *
 */
public interface NHttpStreamUpload {

	/**
	 * get the count of the bytes
	 * @return the number of the bytes
	 */
	public long getContentLength();
	
	/**
	 * open a inputstream for uploading bytes
	 * @return the inputstream from which the bytes is read
	 */
	public InputStream openStream();
	
	/**
	 * close the inputstream
	 */
	public void closeStream();
}
