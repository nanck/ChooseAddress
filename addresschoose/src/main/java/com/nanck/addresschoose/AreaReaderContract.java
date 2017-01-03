package com.nanck.addresschoose;

import android.provider.BaseColumns;

/**
 * @author nanck 2016/12/1.
 */
final class AreaReaderContract {
    private AreaReaderContract() {
    }

    static class AreaEntry implements BaseColumns {
        static final String TABLE_NAME = "area";
        static final String NAME = "name";
        static final String ID = "id";
        static final String LEVEL = "level";
        static final String FATHER_ID = "fatherId";
        static final String CODE = "code";
    }
}
