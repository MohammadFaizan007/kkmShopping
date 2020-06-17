package com.digitalcashbag.shopping.fragment.myorders_all;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.adapter.ShoppingOrderAdapter;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.response.ResponseOrderList;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IIShoppingOrders extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.rv_orderlist)
    RecyclerView rvorderlist;
    @BindView(R.id.noRecord)
    TextView noRecord;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderlist, container, false);
        unbinder = ButterKnife.bind(this, view);

        rvorderlist.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        hideKeyboard();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getOrderList();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    private void getOrderList() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("UserId", PreferencesManager.getInstance(context).getUSERID());
        LoggerUtil.logItem(object);
        Call<ResponseOrderList> productListCall = apiServices_shoopping.getOrderList(object);
        productListCall.enqueue(new Callback<ResponseOrderList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseOrderList> call, @NonNull Response<ResponseOrderList> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.isSuccessful()) {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        ShoppingOrderAdapter orderListAdapter = new ShoppingOrderAdapter(context, response.body().getOrderList());

                        rvorderlist.setAdapter(orderListAdapter);
                        rvorderlist.setHasFixedSize(true);

                    } else {
                        noRecord.setVisibility(View.VISIBLE);
                        rvorderlist.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseOrderList> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
