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
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * <h3>目标</h3>
 * 1. 优化选择方式。返回结果更加优雅
 * 2. 支持使用其他 Module(如 app) 中的数据库文件
 * //
 * Context c = createPackageContext("chroya.demo", Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
 * //载入这个类
 * Class clazz = c.getClassLoader().loadClass("chroya.demo.Main");
 * //新建一个实例
 * Object owner = clazz.newInstance();
 * //获取print方法，传入参数并执行
 * Object obj = clazz.getMethod("print", String.class).invoke(owner, "Hello");
 * //
 * 3. 爬虫最新行政区划数据。
 */
public class ChooserActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "ChooserActivity";

    public static final String ART_OBJECT = "area";
    public static final String ART_ADDRESS = "address";
    public static final String ACTION = "com.nanck.addresschoose.CHOOSERACTIVITY_RESULT";

    public static String sAddress = "";

    private Area mArea;

    private AreaSelectorAdapter adapter;

    public static void start(Context context, @Nullable Area area) {
        Intent intent = new Intent(context, ChooserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ART_OBJECT, area);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.getInstance().put(this);

        setContentView(R.layout.activity_chooser);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_chooser);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
                finish();
            }
        });


        final ListView lv = (ListView) findViewById(R.id.lv_selector);
        lv.setOnItemClickListener(this);


        Bundle bundle = getIntent().getExtras();
        mArea = bundle.getParcelable(ART_OBJECT);

        adapter = new AreaSelectorAdapter(ChooserActivity.this);
        lv.setAdapter(adapter);

        final AreaDao dao = new AreaDao(this);
//        List<Area> listTemp;
//        if (mArea == null) {
//            listTemp = dao.fetchProvince();
//        } else {
//            sAddress = sAddress + mArea.getName() + " ";
//            listTemp = dao.fetchSubAreaByFatherId(mArea.getId());
//        }
//        adapter.setData(listTemp);
//        adapter.notifyDataSetChanged();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "handleMessage: " + msg.toString());
                Log.d(TAG, "threadName: " + Thread.currentThread().getName());
                ArrayList<Area> l = (ArrayList<Area>) msg.obj;
                adapter.setData(l);
                adapter.notifyDataSetChanged();
            }
        };


        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Area> listTemp;
                if (mArea == null) {
                    listTemp = dao.fetchProvince();
                } else {
                    sAddress = sAddress + mArea.getName() + " ";
                    listTemp = dao.fetchSubAreaByFatherId(mArea.getId());
                }
                Message msg = mHandler.obtainMessage();
                msg.obj = listTemp;
                mHandler.sendMessage(msg);
            }
        }).start();

    }


    ///////////////////////////////////////////////////////////////////////////
    // 这个类是用来干什么的呢？敬请关注。
    ///////////////////////////////////////////////////////////////////////////
    private Handler mHandler;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBack();
    }

    private void onBack() {
        if (mArea != null) {
            int end = sAddress.length() - (mArea.getName().length() + 1);
            // FIXME: StringIndexOutOfBoundsException 12-05 04:49:05.764 2256-2256/? E/MessageQueue-JNI: java.lang.StringIndexOutOfBoundsException: length=0; regionStart=0; regionLength=-3
            sAddress = sAddress.substring(0, end);
            if (mArea.getLevel() == 1) {
                sAddress = "";
            }
        }
    }

    private boolean isSelected = false;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (isSelected) {
            return;
        }
        Area area = (Area) adapter.getItem(i);
        if (area.getLevel() < 3) {
            start(this, (Area) adapter.getItem(i));
        } else {
            Log.d(TAG, "onItemClick: last " + area.toString());
            sAddress = sAddress + area.getName();
            Intent intent = new Intent();
            intent.setAction(ACTION);
            intent.putExtra(ART_ADDRESS, sAddress);
            sendBroadcast(intent);
            sAddress = "";
            Utils.getInstance().clearAll();
        }
        isSelected = true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        isSelected = false;
    }
}