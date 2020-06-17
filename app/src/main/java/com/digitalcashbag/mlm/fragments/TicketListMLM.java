package com.digitalcashbag.mlm.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.mlm.adapter.SupportTicketsAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.request.RequestTicket;
import kkm.com.core.model.response.ResponseTicket;
import kkm.com.core.model.response.SupportTicketsItem;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketListMLM extends BaseFragment implements MvpView {
    public LinearLayoutManager layoutManager;
    Unbinder unbinder;
    @BindView(R.id.universal_recycler)
    RecyclerView universal_recycler;

    @BindView(R.id.txtNoData)
    TextView txtNoData;
    private List<SupportTicketsItem> items;
    private SupportTicketsAdapter supportTicketsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_history, container, false);
        unbinder = ButterKnife.bind(this, view);
        items = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        universal_recycler.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        universal_recycler.setItemAnimator(new DefaultItemAnimator());

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getTicketList();
        } else {
            txtNoData.setVisibility(View.VISIBLE);
            txtNoData.setText("No internet connection.");
            createInfoDialog(context, "Alert", getString(R.string.alert_internet));
        }
    }

    public void getTicketList() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        RequestTicket requestTicket = new RequestTicket();
        requestTicket.setFkMemId(PreferencesManager.getInstance(context).getUSERID());
        LoggerUtil.logItem(requestTicket);
        showLoading();
        Call<ResponseTicket> call = apiServices.getTicketCall(requestTicket);
        call.enqueue(new Callback<ResponseTicket>() {
            @Override
            public void onResponse(@NotNull Call<ResponseTicket> call, @NotNull Response<ResponseTicket> response) {
                hideLoading();
                if (response.isSuccessful()) {
                    LoggerUtil.logItem(response.body());
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                        items.addAll(response.body().getSupportTickets());
                        supportTicketsAdapter = new SupportTicketsAdapter(context, items);
                        universal_recycler.setAdapter(supportTicketsAdapter);
                        txtNoData.setVisibility(View.GONE);
                    } else {
                        txtNoData.setVisibility(View.VISIBLE);
                        txtNoData.setText("No data found !");
                    }
                } else {
                    txtNoData.setVisibility(View.VISIBLE);
                    txtNoData.setText("No data found !");
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseTicket> call, @NotNull Throwable t) {
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

