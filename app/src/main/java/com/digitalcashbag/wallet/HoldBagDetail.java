package com.digitalcashbag.wallet;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.wallet.adapter.HoldWalletAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.wallet.HoldwalletListItem;
import kkm.com.core.model.response.wallet.ResponseHoldWallet;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HoldBagDetail extends BaseActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;


    HoldWalletAdapter holdWalletAdapter;
    List<HoldwalletListItem> list = new ArrayList<>();
    @BindView(R.id.rv_hold_bag)
    RecyclerView rvHoldBag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hold_bag_detail);
        ButterKnife.bind(this);
        title.setText("Hold Bag");

        sideMenu.setOnClickListener(v -> finish());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHoldBag.setLayoutManager(mLayoutManager);
        holdWalletAdapter = new HoldWalletAdapter(context, list, HoldBagDetail.this);
        rvHoldBag.setAdapter(holdWalletAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getHoldWalletDetail();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }


    private void getHoldWalletDetail() {
        showLoading();
        String url = BuildConfig.BASE_URL_MLM + "GetHoldWalletDetailsById?MemberId=" + PreferencesManager.getInstance(context).getUSERID();
        Log.e("URL===== ", url);
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        Call<ResponseHoldWallet> call = apiServices.getholdWalletDetail(url);
        call.enqueue(new Callback<ResponseHoldWallet>() {
            @Override
            public void onResponse(Call<ResponseHoldWallet> call, Response<ResponseHoldWallet> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                            holdWalletAdapter = new HoldWalletAdapter(context,
                                    response.body().getHoldwalletList(), HoldBagDetail.this);
                            rvHoldBag.setAdapter(holdWalletAdapter);
                            Log.e("SIZEEEE AA- ", "" + (response.body().getHoldwalletList()).size());
                        } else {
                            rvHoldBag.setVisibility(View.GONE);
                            showAlert("No Transaction found.", R.color.red, R.drawable.error);
                            new Handler().postDelayed(() -> finish(), 2000);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseHoldWallet> call, Throwable t) {
                hideLoading();
            }
        });
    }
}
