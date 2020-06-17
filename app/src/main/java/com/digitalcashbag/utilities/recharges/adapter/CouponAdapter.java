package com.digitalcashbag.utilities.recharges.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;

public class CouponAdapter extends BaseAdapter {

    private final String[] gridViewCouponString;
    private final int[] gridViewCouponImage;
    private Context mContext;

    public CouponAdapter(Context context, String[] gridViewCouponString, int[] gridViewCouponImage) {
        mContext = context;
        this.gridViewCouponImage = gridViewCouponImage;
        this.gridViewCouponString = gridViewCouponString;
    }

    @Override
    public int getCount() {
        return gridViewCouponString.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            gridViewAndroid = inflater.inflate(R.layout.coupon_adapter, null);

            TextView tv_discount_amount = gridViewAndroid.findViewById(R.id.tv_discount_amount);
            ImageView coupon_img = gridViewAndroid.findViewById(R.id.coupon_img);
            tv_discount_amount.setText(gridViewCouponString[i]);
            coupon_img.setImageResource(gridViewCouponImage[i]);
        } else {
            gridViewAndroid = convertView;
        }

        return gridViewAndroid;
    }
}