package com.yanhao.main.yanhaoandroid.http;

import java.io.InputStream;

/**
 * A Download Stream to be read
 * The HttpResponse Message to receive large bytes should implement it,
 * for the details control.
 * @author lixiaohua01
 *
 */
public interface NHttpStreamDownload {

	/**
	 * read bytes from the inpustream
	 * @param stream
	 * 			the inputstream
	 * @param contentLength
	 * 			the total bytes number
	 */
	public void readStream(InputStream stream, long contentLength);
}
