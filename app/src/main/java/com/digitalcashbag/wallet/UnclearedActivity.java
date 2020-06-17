package com.digitalcashbag.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.wallet.adapter.UnclearedAdapter;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.unclearLedger.ResponseUnclearBalance;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnclearedActivity extends BaseActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.notiList)
    RecyclerView notiList;
    @BindView(R.id.txtNoNotification)
    TextView txtNoNotification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_lay);
        ButterKnife.bind(this);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        notiList.setLayoutManager(manager);

        title.setText("Uncleared Bag Ledger");

        sideMenu.setOnClickListener(v -> onBackPressed());

        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getLedger();

    }

    private void getLedger() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Fk_MemId", PreferencesManager.getInstance(context).getUSERID());
        LoggerUtil.logItem(object);

        Call<ResponseUnclearBalance> call = apiServices.getUnclearLedger(object);
        call.enqueue(new Callback<ResponseUnclearBalance>() {
            @Override
            public void onResponse(@NotNull Call<ResponseUnclearBalance> call, @NotNull Response<ResponseUnclearBalance> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        UnclearedAdapter unclearedAdapter = new UnclearedAdapter(context, response.body().getPayoutLedgerSelectDetailsList());
                        notiList.setAdapter(unclearedAdapter);
                        txtNoNotification.setVisibility(View.GONE);
                    } else {
                        txtNoNotification.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseUnclearBalance> call, @NotNull Throwable t) {
                hideLoading();
            }
        });
    }

}
