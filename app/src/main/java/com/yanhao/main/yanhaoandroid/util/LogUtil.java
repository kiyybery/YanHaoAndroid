
package com.yanhao.main.yanhaoandroid.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


public class LogUtil {

    public static final boolean VDBG = false;
    public static final boolean DDBG = false;
    public static final boolean IDBG = false;
    public static final boolean EDBG = false;
    public static final boolean WDBG = false;
    public static final boolean XDBG = false;

    public static final boolean DEVELOPER_MODE = false;

    // set false when send to tester please
    public static final boolean TRACEVIEW_DBG = false;

    /**
     * Send a {@link #VERBOSE} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        if (VDBG) {
            Log.v(tag, msg);
        }
    }

    /**
     * Send a {@link #VERBOSE} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void v(String tag, String msg, Throwable tr) {
        if (VDBG) {
            Log.v(tag, msg, tr);
        }
    }

    /**
     * Send a {@link #DEBUG} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
        if (DDBG) {
            Log.d(tag, String.valueOf(TimeUtil.getTimeString(System.currentTimeMillis())));
            Log.d(tag, msg);
        }
    }

    /**
     * Send a {@link #DEBUG} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (DDBG) {
            Log.d(tag, msg, tr);
        }
    }

    /**
     * Send an {@link #INFO} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {
        if (IDBG) {
            Log.i(tag, String.valueOf(TimeUtil.getTimeString(System.currentTimeMillis())));
            Log.i(tag, msg);
        }
    }

    /**
     * Send a {@link #INFO} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void i(String tag, String msg, Throwable tr) {
        if (IDBG) {
            Log.i(tag, msg, tr);
        }
    }

    /**
     * Send a {@link #WARN} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
        if (WDBG) {
            Log.w(tag, String.valueOf(TimeUtil.getTimeString(System.currentTimeMillis())));
            Log.w(tag, msg);
        }
    }

    /**
     * Send a {@link #WARN} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (WDBG) {
            Log.w(tag, msg, tr);
        }
    }

    /*
     * Send a {@link #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    public static void w(String tag, Throwable tr) {
        if (WDBG) {
            Log.w(tag, tr);
        }
    }

    /**
     * Send an {@link #ERROR} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        if (EDBG) {
            Log.e(tag, String.valueOf(TimeUtil.getTimeString(System.currentTimeMillis())));
            Log.e(tag, msg);
        }
    }

    /**
     * Send a {@link #ERROR} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (EDBG) {
            Log.e(tag, msg, tr);
        }
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen.
     * The error will always be logged at level ASSERT with the call stack.
     * Depending on system configuration, a report may be added to the
     * {@link android.os.DropBoxManager} and/or the process may be terminated
     * immediately with an error dialog.
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
/*    public static void wtf(String tag, String msg) {
        Log.wtf(tag, msg);
    }
*/
    /**
     * What a Terrible Failure: Report an exception that should never happen.
     * Similar to {@link #wtf(String, String)}, with an exception to log.
     * @param tag Used to identify the source of a log message.
     * @param tr An exception to log.
     */
/*    public static void wtf(String tag, Throwable tr) {
        Log.wtf(tag, tr);
    }*/

    /**
     * What a Terrible Failure: Report an exception that should never happen.
     * Similar to {@link #wtf(String, Throwable)}, with a message as well.
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     * @param tr An exception to log.  May be null.
     */
/*    public static void wtf(String tag, String msg, Throwable tr) {
        Log.wtf(tag, msg, tr);
    }
    */

    /**
     * Low-level logging call.
     *
     * @param priority The priority/type of this log message
     * @param tag      Used to identify the source of a log message.  It usually identifies
     *                 the class or activity where the log call occurs.
     * @param msg      The message you would like logged.
     * @return The number of bytes written.
     */
    public static void println(int priority, String tag, String msg) {
        Log.println(priority, tag, msg);
    }

    public static void x(String tag, String msg) {
        if (XDBG) {
            LogUtil.d(tag, msg);
            //out(tag, msg);
        }
    }

    private synchronized static void out(String tag, String msg) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            FileOutputStream output = null;
            File file = new File(Environment.getExternalStorageDirectory(),
                    "xlog.txt");
            try {
                long size = file.length();
                if (size > 1024 * 1024 * 16) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            try {
                output = new FileOutputStream(file, true);
                Date date = new Date();
                String xtime = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
                String msgdata = xtime + " " + tag + " " + msg + "\r\n";
                output.write(msgdata.getBytes("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    output.close();
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            }
        }
    }

    public static class LogRecord {

        private StringBuilder sb;

        public LogRecord(String logMessage) {
            sb = new StringBuilder();
            if (TextUtils.isEmpty(logMessage)) {
                sb.append(logMessage);
            }
        }

        public void addValue(String key, String value) {
            sb.append("{").append(key).append(" = ").append(value).append("}");
        }

        public void addValue(String key, int value) {
            sb.append("{").append(key).append(" = ").append(value).append("}");
        }

        public void addValue(String key, long value) {
            sb.append("{").append(key).append(" = ").append(value).append("}");
        }

        public void addValue(String key, boolean value) {
            sb.append("{").append(key).append(" = ").append(value).append("}");
        }


        @Override
        public String toString() {
            return sb.toString();
        }


    }
}
