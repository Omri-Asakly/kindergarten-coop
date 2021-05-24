package com.uni.haifa.kgco_op;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<Parent> {
    private List<Parent> dataList = null;
    private Context context = null;

    public UserListAdapter(Context context, List<Parent> dataList) {
        super(context, R.layout.simplerow);
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Parent getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.simplerow, null, false);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.parent);
        TextView mail = (TextView) rowView.findViewById(R.id.textView);
        final Parent p = dataList.get(position);
        txtTitle.setText(p.getUserName());
        mail.setText(p.getEmail());

        return rowView;
    }
}
