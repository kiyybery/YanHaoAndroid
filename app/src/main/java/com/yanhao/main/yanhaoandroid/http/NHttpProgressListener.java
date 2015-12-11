/**
 * 
 */
package com.yanhao.main.yanhaoandroid.http;

/**
 * @author rax
 *
 */
public interface NHttpProgressListener {
	
    public void onUploadProgressUpdate(long postBytes, long totalBytes);
}
