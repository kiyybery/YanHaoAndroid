/**
 *
 */

package com.yanhao.main.yanhaoandroid.http;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.conn.ConnectTimeoutException;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.yanhao.main.yanhaoandroid.LogTags;
import com.yanhao.main.yanhaoandroid.task.PoolAsyncTask;
import com.yanhao.main.yanhaoandroid.util.BytesUtil;
import com.yanhao.main.yanhaoandroid.util.LogUtil.LogRecord;
import com.yanhao.main.yanhaoandroid.util.NetworkUtil;


/**
 * asynchronous task for http request
 *
 * @author lixiaohua01
 */
public class NHttpAsyncTask extends PoolAsyncTask<NHttpRequest, Long, NHttpResponse> {

    private static final String TAG = NHttpAsyncTask.class.getSimpleName();

    public static final long HTTP_PROGRESS_CONNECTING = 1;
    public static final long HTTP_PROGRESS_READING = 2;
    public static final long HTTP_PROGRESS_FINISHED = 3;
    public static final long HTTP_PROGRESS_UPLOAD = 4;

    private static int sConnectTimeOut = 60000;
    private static int sReadTimeOut = 20000;
    private static int sBytesToUpdateProgress = 10 * 1024;

    private Context mContext;

    private NHttpRequest mReq;
    private NHttpCallback mCallback;
    private boolean mDoCallbackOnMainThread = true;

    private boolean mPublishProgressStatus;

    //if the value is true,start debug mode,simulate the server response without network connecting
    private boolean mDebugMode = false;

    public void setCallback(NHttpCallback callback, boolean doCallbackOnMainThread) {
        mCallback = callback;
        mDoCallbackOnMainThread = doCallbackOnMainThread;
    }

    public void setDebugMode(boolean debugMode) {
        mDebugMode = debugMode;
    }

    public NHttpAsyncTask(Context context) {
        super();
        mContext = context;
    }

    public void setPublishProgressStatus(boolean publishProgressStatus) {
        this.mPublishProgressStatus = publishProgressStatus;
    }


    @Override
    protected NHttpResponse doInBackground(NHttpRequest... params) {
        NHttpResponse resp = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            mReq = params[0];
            resp = NHttpProxy.matchResponse(mReq);
            if (mDebugMode) {
                NHttpProxy.buildDebugResponse(resp);
                return resp;
            }

            URL url = new URL(mReq.getReqURL());
            if (Log.isLoggable(LogTags.TAG_NETWORK, Log.DEBUG)) {
                LogRecord lr = new LogRecord("set url");
                lr.addValue("url", url.toString());
                Log.d(TAG, lr.toString());
            }
            if (mPublishProgressStatus)
                publishProgress(HTTP_PROGRESS_CONNECTING);

            checkProxySettings();

            System.setProperty("http.keepAlive", "false");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            if (NHttpRequest.METHOD_POST.equals(mReq.getReqMethod())) {
                conn.setDoOutput(true);
            }
            conn.setUseCaches(false);
            conn.setRequestMethod(mReq.getReqMethod());
            conn.setConnectTimeout(sConnectTimeOut);
            conn.setReadTimeout(sReadTimeOut);
            HttpURLConnection.setFollowRedirects(true);
            conn.setRequestProperty(HttpHeaders.KEY_ACCEPT_ENCODING, HttpHeaders.VALUE_GZIP_DEFLATE);
            conn.setRequestProperty(HttpHeaders.KEY_CONTENT_TYPE, mReq.getContentType());
            if (Log.isLoggable(LogTags.TAG_NETWORK, Log.DEBUG)) {
                LogRecord lr = new LogRecord("set properties");
                lr.addValue("method", mReq.getReqMethod());
                lr.addValue("contentType", mReq.getContentType());
                Log.d(TAG, lr.toString());
            }
            if (mReq.getReqMethod().equals(NHttpRequest.METHOD_POST)) {
                if (mReq instanceof NHttpStreamUpload) {
                    long uploadContentLength = ((NHttpStreamUpload) mReq).getContentLength();
                    conn.setRequestProperty(HttpHeaders.KEY_CONTENT_LENGTH, String.valueOf(uploadContentLength));
                    bos = new BufferedOutputStream(conn.getOutputStream());
                    byte[] buffer = new byte[1024];
                    BufferedInputStream bufis = null;
                    try {
                        bufis = new BufferedInputStream(((NHttpStreamUpload) mReq).openStream());
                        int len = 0;
                        long postBytes = 0;
                        int accumulatedLength = 0;
                        while ((len = bufis.read(buffer)) != -1) {
                            bos.write(buffer, 0, len);
                            postBytes += len;
                            accumulatedLength += len;
                            if (accumulatedLength / sBytesToUpdateProgress == 1) {
                                //update upload progress for progressDialog,etc.
                                this.publishProgress(HTTP_PROGRESS_UPLOAD, postBytes, uploadContentLength);
                                accumulatedLength = 0;
                            }
                        }
                        //update upload progress when upload finished
                        this.publishProgress(HTTP_PROGRESS_UPLOAD, postBytes, uploadContentLength);
                        bos.flush();
                        if (Log.isLoggable(LogTags.TAG_NETWORK, Log.DEBUG)) {
                            LogRecord lr = new LogRecord("stream upload");
                            lr.addValue("length", String.valueOf(uploadContentLength));
                            Log.d(TAG, lr.toString());
                        }
                    } finally {
                        buffer = null;
                        if (bufis != null) {
                            try {
                                bufis.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else if (mReq instanceof NHttpMultipartRequest) {
                    ((NHttpMultipartRequest) mReq).buildParameterBody();
                    long contentLength = ((NHttpMultipartRequest) mReq).getContentLength();
                    conn.setRequestProperty(HttpHeaders.KEY_CONTENT_LENGTH, String.valueOf(contentLength));
                    if (Log.isLoggable(LogTags.TAG_NETWORK, Log.DEBUG)) {
                        LogRecord lr = new LogRecord("post muitlpart");
                        lr.addValue("length", String.valueOf(contentLength));
                        Log.d(TAG, lr.toString());
                    }
                    bos = new BufferedOutputStream(conn.getOutputStream());
                    ((NHttpMultipartRequest) mReq).writeMutilpartData(bos);
                } else {
                    byte[] buffer = null;
                    if (mReq.getPostBytes() != null) {
                        buffer = mReq.getPostBytes();
                        if (Log.isLoggable(LogTags.TAG_NETWORK, Log.DEBUG)) {
                            LogRecord lr = new LogRecord("post bytes");
                            lr.addValue("length", String.valueOf(mReq.getPostBytes().length));
                            Log.d(TAG, lr.toString());
                        }
                    } else {
                        String postContent = mReq.buildPostContent();
                        if (TextUtils.isEmpty(postContent)) {
                            buffer = new byte[0];
                        } else {
                            buffer = postContent.getBytes("utf-8");
                            if (Log.isLoggable(LogTags.TAG_NETWORK, Log.DEBUG)) {
                                LogRecord lr = new LogRecord("post request content");
                                lr.addValue("content", postContent);
                                Log.d(TAG, lr.toString());
                            }
                        }
                    }
                    conn.setRequestProperty(HttpHeaders.KEY_CONTENT_LENGTH, String.valueOf(buffer.length));
                    bos = new BufferedOutputStream(conn.getOutputStream());
                    bos.write(buffer);
                    bos.flush();
                    buffer = null;
                }
            }

            int respCode = conn.getResponseCode();
            long contentLength = conn.getContentLength();
            String contentEncoding = conn.getContentEncoding();
            String contentType = conn.getContentType();
            if (Log.isLoggable(LogTags.TAG_NETWORK, Log.DEBUG)) {
                LogRecord lr = new LogRecord("get response");
                lr.addValue("responseCode", String.valueOf(respCode));
                lr.addValue("contentLength", contentLength);
                Log.d(TAG, lr.toString());
            }
            if (respCode == HttpURLConnection.HTTP_OK) {
                if (mPublishProgressStatus)
                    publishProgress(HTTP_PROGRESS_READING);
            } else {
                if (mPublishProgressStatus)
                    publishProgress(HTTP_PROGRESS_FINISHED);
            }
            resp.setHttpRespCode(respCode);
            resp.setContentType(contentType);
            resp.setContentLength(contentLength);
            resp.setContentEncoding(contentEncoding);
            if (resp instanceof NHttpStreamDownload) {
                if (Log.isLoggable(LogTags.TAG_NETWORK, Log.DEBUG)) {
                    LogRecord lr = new LogRecord("stream download resp");
                    Log.d(TAG, lr.toString());
                }
                bis = new BufferedInputStream(conn.getInputStream());
                NHttpProxy.buildResponse(mReq, resp, respCode, conn.getContentType(), bis, contentLength);
            } else {
                bis = new BufferedInputStream(conn.getInputStream());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    byte[] buf = new byte[1024];
                    int len = 0;
                    while ((len = bis.read(buf)) != -1) {
                        baos.write(buf, 0, len);
                    }
                    buf = null;
                    byte[] content = baos.toByteArray();
                    if (!TextUtils.isEmpty(contentEncoding) && "gzip".equals(contentEncoding)) {
                        content = BytesUtil.unCompressWithGZip(baos.toByteArray());    //use for gzip response
                    }
                    if (Log.isLoggable(LogTags.TAG_NETWORK, Log.DEBUG)) {
                        LogRecord lr = new LogRecord("content resp");
                        lr.addValue("content", new String(content, "utf-8"));
                        Log.d(TAG, lr.toString());
                    }
                    NHttpProxy.buildResponse(mReq, resp, respCode, conn.getContentType(), content);
                } finally {
                    if (baos != null) {
                        baos.close();
                    }
                }
            }
        } catch (MatchResponseFailedException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            resp.setRespStatus(NHttpResponse.RESPONSE_STATUS_MALFORMEDURL);
            e.printStackTrace();
        } catch (java.net.SocketException e) {
            resp.setRespStatus(NHttpResponse.RESPONSE_STATUS_NETWORK_SOCKETERROR);
            e.printStackTrace();
        } catch (java.net.SocketTimeoutException e) {
            resp.setRespStatus(NHttpResponse.RESPONSE_STATUS_TIMEOUT);
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            resp.setRespStatus(NHttpResponse.RESPONSE_STATUS_TIMEOUT);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            resp.setRespStatus(NHttpResponse.RESPONSE_STATUS_IOERROR);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setRespStatus(NHttpResponse.RESPONSE_STATUS_HTTPTASKERROR);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        if (mCallback != null && !mDoCallbackOnMainThread) {
            //runs on the background thread
            try {
                mCallback.onRespond(mReq, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resp;
    }

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#onProgressUpdate(Progress[])
     */
    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        if (this.mCallback == null)
            return;
        if (mPublishProgressStatus) {
            if (values[0] == HTTP_PROGRESS_UPLOAD) {
                mCallback.onPostProgressUpdate(values[1], values[2]);
            } else {
                mCallback.onProgressStatusUpdate(values[0]);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(NHttpResponse result) {
        try {
            if (mCallback != null && mDoCallbackOnMainThread) {
                mCallback.onRespond(mReq, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mPublishProgressStatus)
            publishProgress(HTTP_PROGRESS_FINISHED);
    }

    @Override
    protected void onPreExecute() {
        //
        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            this.cancel(true);
            if (mCallback != null) {
                mCallback.onNetworkDisable();
            }
        }
    }

    private void checkProxySettings() {
        //check proxy settings
        System.getProperties().remove("http.proxyHost");
        System.getProperties().remove("http.proxyPort");
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {// wifi disable
            // proxy model
            String proxyHost = android.net.Proxy.getDefaultHost();
            int proxyPort = android.net.Proxy.getDefaultPort();
            if (TextUtils.isEmpty(proxyHost)) {
                System.getProperties().setProperty("http.proxyHost", proxyHost);
                System.getProperties().setProperty("http.proxyPort", String.valueOf(proxyPort));
            }
        }
    }
}
