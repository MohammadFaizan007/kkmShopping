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
import com.digitalcashbag.utilities.recharges.adapter.BeneficiaryListAdapter;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.response.utility.ResponseBenficiarylist;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeneficiaryList extends BaseFragment implements MvpView {

    Unbinder unbinder;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

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
                getBeneficiaryList();
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }
        }
    }

    private void getBeneficiaryList() {
        progressBar.setVisibility(View.VISIBLE);
        JsonObject param = new JsonObject();
        param.addProperty("RemitterId", PreferencesManager.getInstance(context).getREMITTER_ID());
        LoggerUtil.logItem(param);
        Call<List<ResponseBenficiarylist>> listCall = apiServices_utility.getBeneficiaryDetils(param);
        listCall.enqueue(new Callback<List<ResponseBenficiarylist>>() {
            @Override
            public void onResponse(@NonNull Call<List<ResponseBenficiarylist>> call, @NonNull Response<List<ResponseBenficiarylist>> response) {
                if (progressBar != null)
                    progressBar.setVisibility(View.GONE);

                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(call.request().url());
                try {
                    if (response.body().size() > 0) {
                        list.setVisibility(View.VISIBLE);
                        BeneficiaryListAdapter listAdapter = new BeneficiaryListAdapter(context, response.body(), BeneficiaryList.this);
                        list.setAdapter(listAdapter);
                    } else {
                        showMessage("No Beneficiary found!");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    list.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ResponseBenficiarylist>> call, @NonNull Throwable t) {
                try {
                    if (progressBar != null)
                        progressBar.setVisibility(View.GONE);
                    list.setVisibility(View.GONE);
                    LoggerUtil.logItem(t);
                    LoggerUtil.logItem(call.execute().body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getClickPosition(int position, String tag) {
        super.getClickPosition(position, tag);

        switch (tag) {
            case "delete":
                if (NetworkUtils.getConnectivityStatus(context) != 0)
                    getBeneficiaryList();
                break;
        }

    }


}
