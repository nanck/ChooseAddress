package com.nanck.chooseaddressexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nanck.addresschoose.ChooserActivity;
import com.squareup.leakcanary.LeakCanary;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private static final String ARG_NAME = "name";
    private static final String ARG_PHONE = "phone";
    private static final String ARG_ADDRESS = "address";

    private EditTextWithDel editName;
    private EditTextWithDel editPhone;
    private EditTextWithDel editDetail;
    private TextView tvEchoRegion;
    private TextView tvSave;


    private TextWatcher editTextIsEmptyListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            setSaveTextButtonEnable();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private BroadcastReceiver echoRegionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ChooserActivity.ACTION)) {
                String region = intent.getStringExtra(ChooserActivity.ART_ADDRESS);
                tvEchoRegion.setText(region);
                setSaveTextButtonEnable();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        tvEchoRegion = (TextView) findViewById(R.id.tv_address_new_region);

        editName = (EditTextWithDel) findViewById(R.id.edit_address_new_name);
        editName.addTextChangedListener(editTextIsEmptyListener);

        editPhone = (EditTextWithDel) findViewById(R.id.edit_address_new_phone);
        editPhone.addTextChangedListener(editTextIsEmptyListener);

        editDetail = (EditTextWithDel) findViewById(R.id.edit_address_new_detail);
        editDetail.addTextChangedListener(editTextIsEmptyListener);
        editDetail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (EditorInfo.IME_ACTION_DONE == actionId) {
                    if (!checkWhetherCanBeSaved()) {
                        return false;
                    }
                    // TODO: 2017/1/1 Save address to your servers.
                }
                return true;
            }
        });

        tvSave = (TextView) findViewById(R.id.tv_address_new_save);
        tvSave.setOnClickListener(this);

        LinearLayout llRegion = (LinearLayout) findViewById(R.id.ll_address_new_region);
        llRegion.setOnClickListener(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ChooserActivity.ACTION);

        registerReceiver(echoRegionReceiver, intentFilter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_address_new_region:
                ChooserActivity
                        .start(MainActivity.this, null);
                break;

            case R.id.tv_address_new_save:
                // TODO: 2017/1/1 Save address to your server.
                break;

            default:
                break;
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ARG_NAME)) {
                String name = savedInstanceState.getString(ARG_NAME);
                editName.setText(name);
            }

            if (savedInstanceState.containsKey(ARG_PHONE)) {
                String phone = savedInstanceState.getString(ARG_PHONE);
                editPhone.setText(phone);
            }

            if (savedInstanceState.containsKey(ARG_ADDRESS)) {
                String address = savedInstanceState.getString(ARG_ADDRESS);
                editPhone.setText(address);
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_NAME, Utils.getText(editName));
        outState.putString(ARG_PHONE, Utils.getText(editPhone));
        outState.putString(ARG_ADDRESS, Utils.getText(editDetail));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(echoRegionReceiver);
    }

    private boolean checkWhetherCanBeSaved() {
        TextView[] views = {editName, editPhone, editDetail, tvEchoRegion};
        return !Utils.isEmptyForViews(views);
    }

    /**
     * 设置保存按钮是否可用
     * 文本发生变化的时候均要调用{TextView echo change and EditText text change}
     */
    private void setSaveTextButtonEnable() {
        boolean f = checkWhetherCanBeSaved();
        tvSave.setEnabled(f);
    }

}
