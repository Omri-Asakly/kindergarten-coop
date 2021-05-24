package com.uni.haifa.kgco_op;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ChildrenAdapter extends ArrayAdapter<Child> {
    private List<Child> dataList = null;
    private Context context = null;

    public ChildrenAdapter(Context context, List<Child> dataList) {
        super(context, R.layout.selected_parent);
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Child getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.selected_parent, null, false);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.parent);
        final Child c = dataList.get(position);
        txtTitle.setText(c.getName());
        return rowView;
    }
}

