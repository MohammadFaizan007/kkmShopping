package com.digitalcashbag.utilities.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class LocationSearchAdapter extends ArrayAdapter {

    private List<String> dataList;
    private Context mContext;
    private int searchResultItemLayout;

    public LocationSearchAdapter(Context context, int resource, List<String> storeSourceDataLst) {
        super(context, resource, storeSourceDataLst);
        dataList = storeSourceDataLst;
        mContext = context;
        searchResultItemLayout = resource;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public String getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(searchResultItemLayout, parent, false);
        }

        TextView resultItem = view.findViewById(android.R.id.text1);
        resultItem.setText(getItem(position));
        return view;
    }
}