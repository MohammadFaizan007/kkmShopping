package com.digitalcashbag.mlm.fragments.ticketview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.mlm.adapter.TrainingBookingsAdapter;
import com.digitalcashbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.RequestTrainingBookings;
import kkm.com.core.model.response.trainingBookings.ResponseTrainingBookings;
import kkm.com.core.utils.LoggerUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class TrainingBookings extends BaseActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.bookings_list)
    RecyclerView bookingsList;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_bookings);
        ButterKnife.bind(this);
        title.setText("My Bookings");
        sideMenu.setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        bookingsList.setLayoutManager(manager);

        Bundle param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        Log.e("eventId==>", param.getString("eventId"));
        getBookings(param.getString("eventId"));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.side_menu:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
        }
    }

    private void getBookings(String eventId) {
        RequestTrainingBookings bookings = new RequestTrainingBookings();
        bookings.setFkMemId(PreferencesManager.getInstance(context).getUSERID());
        bookings.setEventId(eventId);

        showLoading();
        LoggerUtil.logItem(bookings);

        Call<ResponseTrainingBookings> call = apiServices.getTrainingBookings(bookings);
        call.enqueue(new Callback<ResponseTrainingBookings>() {
            @Override
            public void onResponse(Call<ResponseTrainingBookings> call, Response<ResponseTrainingBookings> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    TrainingBookingsAdapter adapter = new TrainingBookingsAdapter(context, response.body().getEventDetailList());
                    bookingsList.setAdapter(adapter);
                } else {
                    showAlert("No records found.", R.color.red, R.drawable.error);
                    new Handler().postDelayed(() -> {
                        finish();
                        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    }, 2000);
                }
            }

            @Override
            public void onFailure(Call<ResponseTrainingBookings> call, Throwable t) {
                hideLoading();
            }
        });
    }
}
