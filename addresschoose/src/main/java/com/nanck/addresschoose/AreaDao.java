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
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nanck 2016/12/2.
 */

public class AreaDao {
    private static final String TAG = "AreaDao";
    private Context context;


    public AreaDao(Context context) {
        this.context = context;
    }

    public List<Area> fetchProvince() {
        String selection = AreaReaderContract.AreaEntry.LEVEL + " = ?";
        String[] selectionArgs = {"1"};
        return fetchAreaBySelection(selection, selectionArgs);
    }


    public List<Area> fetchSubAreaByFatherId(int fatherId) {
        String selection = AreaReaderContract.AreaEntry.FATHER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(fatherId)};
        return fetchAreaBySelection(selection, selectionArgs);
    }


    private List<Area> fetchAreaBySelection(String selection, String[] selectionArgs) {
//        DBManager db = DBHelper.getInstance(context);
        File path = context.getDatabasePath("area.db");
        Log.d(TAG, "database path : " + path);
        SQLiteDatabase db = new DBManager(context).openDatabase(path);

        String[] projection = {
                AreaReaderContract.AreaEntry.CODE,
                AreaReaderContract.AreaEntry.FATHER_ID,
                AreaReaderContract.AreaEntry.ID,
                AreaReaderContract.AreaEntry.LEVEL,
                AreaReaderContract.AreaEntry.NAME
        };


        Cursor c = db.query(
                AreaReaderContract.AreaEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        List<Area> areas = new ArrayList<>();

//        c.moveToFirst();
        while (c.moveToNext()) {
            Area area = new Area();

            String code = c.getString(c.getColumnIndex(AreaReaderContract.AreaEntry.CODE));
            area.setCode(code);

            String name = c.getString(c.getColumnIndex(AreaReaderContract.AreaEntry.NAME));
            area.setName(name);

            int id = c.getInt(c.getColumnIndex(AreaReaderContract.AreaEntry.ID));
            area.setId(id);

            int fatherId = c.getInt(c.getColumnIndex(AreaReaderContract.AreaEntry.FATHER_ID));
            area.setFatherId(fatherId);

            int level = c.getInt(c.getColumnIndex(AreaReaderContract.AreaEntry.LEVEL));
            area.setLevel(level);

            areas.add(area);
        }

        c.close();
        db.close();

        return areas;
    }
}
