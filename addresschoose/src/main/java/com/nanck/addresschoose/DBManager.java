/*
 * Copyright (C) 2017 nanck
 *
 * 1999 Free Software Foundation, Inc. 59 Temple Place, Suite 330, Boston,
 * MA 02111-1307 USA Everyone is > > permitted to copy and distribute verbatim copies of this license document,
 * but changing it is not allowed.
 * [This is the first released version of the Lesser GPL.
 * It also counts as the successor of the GNU Library Public License, > > version 2,
 * hence the version number 2.1.]
 */

package com.nanck.addresschoose;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.ScriptGroup;
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
//                InputStream is = context.getResources().openRawResource(R.raw.area);
                InputStream is = context.getAssets().open("area.db");
//                InputStream is = context.getResources().openRawResource(context.R.raw.area);
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
