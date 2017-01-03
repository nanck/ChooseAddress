package com.nanck.addresschoose;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class ChooserActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "ChooserActivity";

    public static final String ART_PARAM1 = "area";
    public static final String ART_ADDRESS = "address";
    public static final String ACTION = "broadcast_action";

    public static String sAddress = "";

    private Area mArea;
    private AreaSelectorAdapter adapter;

    public static void start(Activity context, @Nullable Area area) {
        Intent intent = new Intent(context, ChooserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ART_PARAM1, area);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_chooser);
        toolbar.setTitle("");
        // setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
                finish();
            }
        });


        ListView lv = (ListView) findViewById(R.id.lv_selector);

        Bundle bundle = getIntent().getExtras();
        mArea = bundle.getParcelable(ART_PARAM1);

        List<Area> list;
        if (mArea == null) {
            list = new AreaDao(this).fetchProvince();
        } else {
            sAddress = sAddress + mArea.getName() + " ";
            list = new AreaDao(this).fetchSubAreaByFatherId(mArea.getId());
        }
        adapter = new AreaSelectorAdapter(this, list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);
        Utils.getInstance().put(this);
    }

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
            start(ChooserActivity.this, (Area) adapter.getItem(i));
        } else {
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