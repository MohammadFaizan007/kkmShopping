package com.digitalcashbag.utilities.recharges.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.digitalcashbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.constants.BaseActivity;

public class CreatedCoupons extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_gift_categories)
    RecyclerView rvGiftCategories;

    Bundle param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giftcard_categories);
        ButterKnife.bind(this);

        title.setText("Created Coupons");

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGiftCategories.setLayoutManager(manager);

//        if (NetworkUtils.getConnectivityStatus(context) != 0)
//            getThemeParkPackeges();
//        else
//            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
    }

    @OnClick(R.id.side_menu)
    public void onViewClicked() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
