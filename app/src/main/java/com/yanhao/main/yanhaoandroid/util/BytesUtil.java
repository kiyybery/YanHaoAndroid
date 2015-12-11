/**
 *
 */
package com.yanhao.main.yanhaoandroid.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * util for bytes operation
 *
 * @author rax
 */
public class BytesUtil {

    /**
     * uncompress the given bytes with gzip
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static byte[] unCompressWithGZip(byte[] content) throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(content);
        GZIPInputStream zipIn = null;
        try {
            zipIn = new GZIPInputStream(in);
            byte[] orginal = new byte[2048];
            int len = 0;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while ((len = zipIn.read(orginal)) > 0) {
                out.write(orginal, 0, len);
            }
            out.flush();
            out.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new Exception("unGZip failed", e);
        } finally {
            if (zipIn != null) {
                zipIn.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * compress the given string with gzip
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static byte[] compressWithGZip(String content) throws Exception {
        try {
            return compressWithGZip(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new Exception("gzip failed", e);
        }
    }

    /**
     * compress the given bytes with gzip
     *
     * @param content
     * @return the bytes gzipped
     * @throws Exception
     */
    public static byte[] compressWithGZip(byte[] content) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream zipOut = null;
        try {
            zipOut = new GZIPOutputStream(out);
            zipOut.write(content);
            zipOut.finish();
            return out.toByteArray();
        } catch (Exception e) {
            throw new Exception("gzip failed", e);
        } finally {
            if (zipOut != null) {
                zipOut.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
