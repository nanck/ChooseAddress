package com.nanck.addresschoose;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author nanck 2016/12/12.
 *         SDK android.os.FileUtils
 */

public final class FileUtils {
    private static final String TAG = "FileUtils";

    public static void copyFileOrThrow(File srcFile, File destFile) throws IOException {
        InputStream in = new FileInputStream(srcFile);
        copyToFileOrThrow(in, destFile);
    }


    public static void copyToFileOrThrow(InputStream inputStream, File destFile)
            throws IOException {
        Log.d(TAG, inputStream.toString());
        boolean isDeleteS = true;
        if (destFile.exists()) {
            isDeleteS = destFile.delete();
        }
        FileOutputStream out = new FileOutputStream(destFile);
        if (isDeleteS) {
            try {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) >= 0) {
                    out.write(buffer, 0, bytesRead);
                }
            } finally {
                try {
                    out.flush();
                    out.getFD().sync();
                    out.close();
                } catch (Exception e) {
                    // TODO: 2017/1/1
                }
            }
        }
    }

}
