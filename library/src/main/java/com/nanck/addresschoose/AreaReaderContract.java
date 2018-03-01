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

import android.provider.BaseColumns;

/**
 * @author nanck 2016/12/1.
 */

final class AreaReaderContract {
    private static final String TAG = "AreaReaderContract";

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
