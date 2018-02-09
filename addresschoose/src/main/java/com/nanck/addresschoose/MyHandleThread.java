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

import android.os.HandlerThread;

/**
 * Created by nanck on 2017/9/2.
 */

public class MyHandleThread extends HandlerThread {

    public MyHandleThread(String name) {
        super(name);
    }

    public MyHandleThread(String name, int priority) {
        super(name, priority);
    }


}
