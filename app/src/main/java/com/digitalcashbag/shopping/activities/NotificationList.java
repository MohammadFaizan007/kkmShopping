package com.digitalcashbag.shopping.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.adapter.NotificationAdapter;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.notification.ResponseNotification;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationList extends BaseActivity {

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

        title.setText("Notification");
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        notiList.setLayoutManager(manager);

        sideMenu.setOnClickListener(v -> onBackPressed());

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getNotification();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }


    }

//    GetNotificationsList

    private void getNotification() {
//        {"Fk_MemId":"100"}
        showLoading();
        JsonObject param = new JsonObject();
        param.addProperty("Fk_MemId", PreferencesManager.getInstance(context).getUSERID());
        Call<ResponseNotification> notificationCall = apiServices.getGetNotificationsList(param);
        notificationCall.enqueue(new Callback<ResponseNotification>() {
            @Override
            public void onResponse(@NotNull Call<ResponseNotification> call, @NotNull Response<ResponseNotification> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                    NotificationAdapter notificationAdapter = new NotificationAdapter(context, response.body().getNotificationList());
                    notiList.setAdapter(notificationAdapter);
                    txtNoNotification.setVisibility(View.GONE);
                } else {
                    txtNoNotification.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseNotification> call, @NotNull Throwable t) {
                hideLoading();
            }
        });

    }

}
