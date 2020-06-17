package com.digitalcashbag.utilities.dmt.addmoney;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.digitalcashbag.R;
import com.digitalcashbag.utilities.dmt.DmtActivity;
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

public class DmtRemitterFragment extends BaseFragment {

    @BindView(R.id.dmt_button)
    Button dmtButton;
    @BindView(R.id.dmt_transaction)
    RecyclerView dmtTransaction;
    @BindView(R.id.dmt_progress)
    ProgressBar dmtProgress;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dmt_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        dmtTransaction.setLayoutManager(manager);
        if (!PreferencesManager.getInstance(context).getREMITTER_ID().equalsIgnoreCase("")) {
            showTransaction();
        }

        dmtButton.setOnClickListener(v -> ((DmtActivity) getActivity()).ReplaceFragmentAddBack(new AddMoneyContainer(), "DMT"));

    }

    public void showTransaction() {
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            dmtTransaction.setVisibility(View.GONE);
            getTransactionList();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    private void getTransactionList() {
        dmtProgress.setVisibility(View.VISIBLE);
        JsonObject param = new JsonObject();
        param.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
        LoggerUtil.logItem(param);
        Call<ResponseFundLog> listCall = apiServices_utility.getFundTransferLog(param);
        listCall.enqueue(new Callback<ResponseFundLog>() {
            @Override
            public void onResponse(@NonNull Call<ResponseFundLog> call, @NonNull Response<ResponseFundLog> response) {
                if (dmtProgress != null)
                    dmtProgress.setVisibility(View.GONE);
                LoggerUtil.logItem(response);
                dmtTransaction.setVisibility(View.VISIBLE);
                try {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        FundLogAdapter logAdapter = new FundLogAdapter(context, response.body().getFundTransferLog());
                        dmtTransaction.setAdapter(logAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseFundLog> call, @NonNull Throwable t) {
                try {
                    dmtProgress.setVisibility(View.GONE);
                    dmtTransaction.setVisibility(View.VISIBLE);
                } catch (Error | Exception e) {
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

}
