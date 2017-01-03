package com.nanck.addresschoose;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nanck 2016/12/2.
 */

 class AreaSelectorAdapter extends BaseAdapter {
    private Context context;
    private List<Area> data = new ArrayList<>();

    public AreaSelectorAdapter(Context context) {
        this.context = context;
    }

     AreaSelectorAdapter(Context context, List<Area> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        Area area = data.get(i);
        vh.textView.setText(area.getName());
        return view;
    }

    class ViewHolder {
        private TextView textView;

        public ViewHolder(View view) {
            this.textView = (TextView) view.findViewById(android.R.id.text1);
        }
    }
}
