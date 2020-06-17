package com.digitalcashbag.utilities.dmt.addmoney;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.digitalcashbag.R;
import com.digitalcashbag.utilities.recharges.adapter.FundLogAdapter;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.response.wallet.ResponseFundLog;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transaction extends BaseFragment {

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beneficiary_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(manager);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                list.setVisibility(View.GONE);
                getTransactionList();
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }
        }
    }

    private void getTransactionList() {
        progressBar.setVisibility(View.VISIBLE);
        JsonObject param = new JsonObject();
        param.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
        LoggerUtil.logItem(param);
        Call<ResponseFundLog> listCall = apiServices_utility.getFundTransferLog(param);
        listCall.enqueue(new Callback<ResponseFundLog>() {
            @Override
            public void onResponse(@NonNull Call<ResponseFundLog> call, @NonNull Response<ResponseFundLog> response) {
                progressBar.setVisibility(View.GONE);
                LoggerUtil.logItem(response);
                list.setVisibility(View.VISIBLE);
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    FundLogAdapter logAdapter = new FundLogAdapter(context, response.body().getFundTransferLog());
                    list.setAdapter(logAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseFundLog> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                list.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
