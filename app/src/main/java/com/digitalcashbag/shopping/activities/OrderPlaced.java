package com.digitalcashbag.shopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;

import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class OrderPlaced extends BaseActivity {

    private TextView fullname;
    private TextView address;
    private TextView landmark;
    private TextView state;
    private TextView city;
    private TextView pincode;
    private TextView addresstype;
    private TextView total_price_item;
    private Button btn_continue;
    private TextView order_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_status);
        PreferencesManager.getInstance(context).setCartCount(0);
        TextView status = findViewById(R.id.status);
        LinearLayout status_loOne = findViewById(R.id.status_loOne);
        RelativeLayout status_loTwo = findViewById(R.id.status_loTwo);
        total_price_item = findViewById(R.id.total_price_item);
        TextView tv_tryagain = findViewById(R.id.tv_tryagain);
        fullname = findViewById(R.id.fullname_bill);
        address = findViewById(R.id.address_bill);
        landmark = findViewById(R.id.landmark_bill);
        state = findViewById(R.id.state_bill);
        city = findViewById(R.id.city_bill);
        btn_continue = findViewById(R.id.btn_continue);
        pincode = findViewById(R.id.pinCode_bill);
        addresstype = findViewById(R.id.addressType_bill);
        order_id = findViewById(R.id.order_id);
        btn_continue.setOnClickListener(this);
        ImageView img_back = findViewById(R.id.img_back);
        ImageView img_back_fail = findViewById(R.id.img_back_fail);
        img_back_fail.setOnClickListener(this);
        img_back.setOnClickListener(this);
        tv_tryagain.setOnClickListener(this);
        Bundle param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        if (param != null) {
            if ("success".equalsIgnoreCase(param.getString("status"))) {
                status_loOne.setVisibility(View.VISIBLE);
                status_loTwo.setVisibility(View.GONE);
                setDefaultAddress(param);
            } else {
                status_loOne.setVisibility(View.GONE);
                status_loTwo.setVisibility(View.VISIBLE);
                status.setText(param.getString("status"));
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_back:
            case R.id.tv_tryagain:
            case R.id.img_back_fail:
            case R.id.btn_continue:
                onBackPressed();
                break;
        }
    }

    public void setDefaultAddress(Bundle param) {
        try {
            String totalItems = param.getString("TOTALITEMS");
            String totalAmount = param.getString("AMOUNT");
            total_price_item.setText(String.format("%s %s %s%s", getResources().getString(R.string.total_price_for), totalItems, getResources().getString(R.string.items_rs), totalAmount));
            for (int position = 0; position < Cons.GetAddressList_arrylst.size(); position++) {
                if (Cons.GetAddressList_arrylst.get(position).getShipAddressId()
                        .equalsIgnoreCase(param.getString("SHIPPING_ID"))) {
                    fullname.setText(Cons.GetAddressList_arrylst.get(position).getFullName());
                    pincode.setText(Cons.GetAddressList_arrylst.get(position).getPincode());
                    landmark.setText(Cons.GetAddressList_arrylst.get(position).getLandmark());
                    address.setText(Cons.GetAddressList_arrylst.get(position).getAddress());
                    state.setText(Cons.GetAddressList_arrylst.get(position).getState());
                    city.setText(Cons.GetAddressList_arrylst.get(position).getCity());
                    addresstype.setText(Cons.GetAddressList_arrylst.get(position).getAddressType());
                    order_id.setText(String.format("Order Id: %s", param.getString("ORDER_ID")));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainContainer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }

}