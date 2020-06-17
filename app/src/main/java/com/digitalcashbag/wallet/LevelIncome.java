package com.digitalcashbag.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.wallet.adapter.MyIncomeAdapter;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.incentive.ResponseGetLevelWiseIncome;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LevelIncome extends BaseActivity implements MvpView {

    @BindView(R.id.member_recycler)
    RecyclerView memberRecycler;
    @BindView(R.id.txtPaidIncome)
    TextView txtPaidIncome;
    @BindView(R.id.txtTds)
    TextView txtTds;
    @BindView(R.id.txtTotalIncome)
    TextView txtTotalIncome;
    @BindView(R.id.tv_unclear_bal)
    TextView tv_unclear_bal;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_income);
        ButterKnife.bind(this);

        sideMenu.setOnClickListener(v -> onBackPressed());
        title.setText("INCOME");

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        memberRecycler.setLayoutManager(manager);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            GetLevelWiseIncome();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    private void GetLevelWiseIncome() {
        JsonObject object = new JsonObject();
        object.addProperty("LoginId", PreferencesManager.getInstance(context).getLoginID());
        showLoading();

        LoggerUtil.logItem(object);

        Call<ResponseGetLevelWiseIncome> call = apiServices.GetLevelWiseIncome(object);
        call.enqueue(new Callback<ResponseGetLevelWiseIncome>() {
            @Override
            public void onResponse(@NotNull Call<ResponseGetLevelWiseIncome> call, @NotNull Response<ResponseGetLevelWiseIncome> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());

                if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                    MyIncomeAdapter incomeAdapter = new MyIncomeAdapter(context, response.body().getListLevelWiseIncome());
                    memberRecycler.setAdapter(incomeAdapter);
                    txtPaidIncome.setText(response.body().getPaidIncome());
                    tv_unclear_bal.setText(response.body().getUnclearBalance());
                    txtTds.setText(response.body().getTds());
                    txtTotalIncome.setText(response.body().getGrossIncome());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseGetLevelWiseIncome> call, @NotNull Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
