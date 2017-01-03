package com.nanck.addresschoose;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author nanck 2016/12/1.
 */

public final class DBManager {
    private static final String TAG = "DBManager";

    private SQLiteDatabase database;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
    }

    public SQLiteDatabase openDatabase(File destFile) {
        boolean isParentExists = true;
        try {
            File p = destFile.getParentFile();
            if (!p.exists()) {
                isParentExists = p.mkdirs();
            }
            if (!destFile.exists()) {
                InputStream is = context.getResources().openRawResource(R.raw.area);
                if (isParentExists) FileUtils.copyToFileOrThrow(is, destFile);
            }

            database = SQLiteDatabase.openOrCreateDatabase(destFile, null);

        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return database;
    }

}
