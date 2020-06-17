package com.digitalcashbag.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.wallet.adapter.IncomeDetailsAdapter;
import com.google.gson.JsonObject;
import com.digitalcashbag.R;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.incentive.ListLevelWiseIncomeItem;
import kkm.com.core.model.response.incentive.ResponseLevelIncome;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class LevelIncomeDetail extends BaseActivity {

    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.tv_orders)
    TextView tvOrders;
    @BindView(R.id.tv_business)
    TextView tvBusiness;
    @BindView(R.id.detailsRecycler)
    RecyclerView detailsRecycler;

    ListLevelWiseIncomeItem incomeItem;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_earning_total)
    TextView tvEarningTotal;
    @BindView(R.id.tv_business_total)
    TextView tvBusinessTotal;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_wise_income_detail);
        ButterKnife.bind(this);

        Bundle param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        String levelId = "";
        if (param != null) {

            incomeItem = (ListLevelWiseIncomeItem) param.getSerializable("income");
            levelId = param.getString("levelId");


            tvLevel.setText(incomeItem.getLevel());
            tvIncome.setText(incomeItem.getIncome());
            tvOrders.setText(String.format("%s Orders", incomeItem.getOrders()));
            tvBusiness.setText(String.format("Value : %s", incomeItem.getBusiness()));

        }
        title.setText("INCOME DETAILS");

        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        detailsRecycler.setLayoutManager(manager);


        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getDetails(levelId);
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    private void getDetails(String levelId) {
        pbProgress.setVisibility(View.VISIBLE);
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        JsonObject param = new JsonObject();
        param.addProperty("LoginId", PreferencesManager.getInstance(context).getLoginID());
        param.addProperty("LevelId", levelId);
        LoggerUtil.logItem(param.toString());
        Call<ResponseLevelIncome> levelIncomeCall = apiServices.getLevelIncomeDetails(param);
        levelIncomeCall.enqueue(new Callback<ResponseLevelIncome>() {
            @Override
            public void onResponse(@NotNull Call<ResponseLevelIncome> call, @NotNull Response<ResponseLevelIncome> response) {
                try {
                    pbProgress.setVisibility(View.GONE);
                    LoggerUtil.logItem(response.body());

                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                        if (response.body().getListLevelIncomeDetails().size() > 0) {

                            if (response.body().getAmountTotal() != null)
                                tvEarningTotal.setText(response.body().getAmountTotal());
                            if (response.body().getBusinessAmountTotal() != null)
                                tvBusinessTotal.setText(response.body().getBusinessAmountTotal());

                            IncomeDetailsAdapter detailsAdapter = new IncomeDetailsAdapter(context, response.body().getListLevelIncomeDetails());
                            detailsRecycler.setAdapter(detailsAdapter);
                        } else
                            Toast.makeText(context, "No details found!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "No records found!", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseLevelIncome> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
