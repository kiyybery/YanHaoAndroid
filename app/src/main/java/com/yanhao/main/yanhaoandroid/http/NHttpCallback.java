/**
 * 
 */
package com.yanhao.main.yanhaoandroid.http;

/**
 * 
 * @author lixiaohua01
 * 
 */
public interface NHttpCallback {

       public void onRespond(NHttpRequest req, NHttpResponse resp);
       
       //when no network
       public void onNetworkDisable();
       
       //get the progress as connecting,reading or finish
       public void onProgressStatusUpdate(long status);
       
       public void onPostProgressUpdate(long postBytes, long totalBytes);
}
