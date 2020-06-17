package com.digitalcashbag.m2p;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.m2p.scanPay.ScanPayResult;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.utilities.recharges.activities.AddMoney;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
import kkm.com.core.model.response.m2p.addfund.ResponseAddFund;
import kkm.com.core.utils.LoggerUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class AddMoneyToCard extends BaseActivity {


    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_ben_name)
    TextView tvBenName;
    @BindView(R.id.tv_ben_mobile)
    TextView tvBenMobile;
    @BindView(R.id.et_amount)
    EditText etAmount;
    @BindView(R.id.et_remark)
    EditText etRemark;

    String entityId;
    private static DecimalFormat format = new DecimalFormat("0.00");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanned_activity);
        ButterKnife.bind(this);
        title.setText("Add Money");
        sideMenu.setOnClickListener(v -> onBackPressed());

//        bundle.putString("name", response.body().get("name").getAsString());
//        bundle.putString("entityId", response.body().get("entityId").getAsString());
//        bundle.putString("mobileno", mobileNo);

        Bundle b = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        if (b != null) {
            tvBenName.setText(b.getString("name", ""));
            tvBenMobile.setText(b.getString("mobileno", ""));
            entityId = b.getString("entityId", "");
        }
    }

    @OnClick(R.id.btn_pay)
    public void onViewClicked() {
        if (validation()) {
            getBalanceAmount(Float.parseFloat(etAmount.getText().toString().trim())
                    , etRemark.getText().toString().trim());
        }
    }

    private boolean validation() {

        if (TextUtils.isEmpty(etAmount.getText().toString().trim())) {
            etAmount.setError("Please enter amount");
            return false;
        } else if (Float.parseFloat(etAmount.getText().toString().trim()) <= 0) {
            etAmount.setError("Please enter valid amount");
            return false;
        }
        return true;
    }

    private void getBalanceAmount(float reqamount, String remark) {
        showLoading();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("MemberId", PreferencesManager.getInstance(context).getUSERID());
        LoggerUtil.logItem(jsonObject);

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(jsonObject.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<JsonObject> walletBalanceCall = apiServices_utilityV2.getbalanceAmount(body);
        walletBalanceCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());

//
                try {

                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    ResponseBalanceAmount convertedObject = new Gson().fromJson(paramResponse, ResponseBalanceAmount.class);

                    if (response.body() != null && convertedObject.getStatus().equalsIgnoreCase("Success")) {
                        float bagamount = convertedObject.getBalanceAmount();
                        if (bagamount >= reqamount) {
                            addFund(bagamount, remark);
                        } else {
                            createAddBalanceDialog(context, "Insufficient bag balance", "You have insufficient balance in your bag, add money before making transactions.", String.valueOf(reqamount));
                        }
                    } else {
                        showMessage("Something went wrong");
                    }

                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                LoggerUtil.logItem(t.getMessage());
                hideLoading();
            }
        });
    }

    public void createAddBalanceDialog(Context context, String title, String msg, String amountPackage) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setNegativeButton("Add Balance", (dialog, id) -> {
            dialog.dismiss();
            Bundle b = new Bundle();
            b.putString("total", amountPackage + "");
            b.putString("from", "dmt");

            goToActivity((Activity) context, AddMoney.class, b);
        });

        android.support.v7.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    private void addFund(float amount, String remark) {
        String reqamount = format.format(Double.parseDouble(String.valueOf(amount)));

        JsonObject oldFormat = new JsonObject();
        oldFormat.addProperty("amount", Double.parseDouble(reqamount));
        oldFormat.addProperty("description", remark);
        oldFormat.addProperty("externalTransactionId", String.valueOf(System.currentTimeMillis()));
        oldFormat.addProperty("productId", "GENERAL");
        oldFormat.addProperty("toEntityId", entityId);
        oldFormat.addProperty("fromEntityId", PreferencesManager.getInstance(context).getLoginID());
        oldFormat.addProperty("transactionOrigin", "MOBILE");
        oldFormat.addProperty("transactionType", BuildConfig.M2P_TRANSFEROTHER);
        oldFormat.addProperty("yapcode", BuildConfig.M2P_YAPCODE);
        oldFormat.addProperty("businessEntityId", BuildConfig.M2P_BUSINESSTYPE);
        oldFormat.addProperty("business", BuildConfig.M2P_BUSINESSTYPE);

//        RequestAddFund requestAddFund = new RequestAddFund();
//        requestAddFund.setAmount(Double.parseDouble(reqamount));
//        requestAddFund.setDescription(remark);
//        requestAddFund.setExternalTransactionId(String.valueOf(System.currentTimeMillis()));
//        requestAddFund.setProductId("GENERAL");
//        requestAddFund.setToEntityId(entityId);
//        requestAddFund.setFromEntityId(PreferencesManager.getInstance(context).getLoginID());
//        requestAddFund.setTransactionOrigin("MOBILE");
//        requestAddFund.setTransactionType(BuildConfig.M2P_TRANSFEROTHER);
//        requestAddFund.setYapcode(BuildConfig.M2P_YAPCODE);
//        requestAddFund.setBusinessEntityId(BuildConfig.M2P_BUSINESSTYPE);
//        requestAddFund.setBusiness(BuildConfig.M2P_BUSINESSTYPE);

        LoggerUtil.logItem(oldFormat);

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(oldFormat.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<JsonObject> call = apiServicesM2PV2.getAddFund(body);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.message());
                LoggerUtil.logItem(response.code());
                LoggerUtil.logItem(response.errorBody());
                LoggerUtil.logItem(response.headers());
                LoggerUtil.logItem(response.raw().body());
                LoggerUtil.logItem(call.request().url());
                ResponseAddFund response_new;
                try {
                    response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key), ResponseAddFund.class);
                    Log.e("============ ", String.valueOf(response_new));
                    if (response.body() != null && response_new.getResponse().equalsIgnoreCase("Success")) {
                        Bundle b = new Bundle();
                        b.putString("from", "AddFundToOther");
                        b.putString("response", response_new.toString());
                        goToActivityWithFinish(context, ScanPayResult.class, b);
                    } else {
                        showMessage("Something went wrong");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                hideLoading();
                showMessage("Something went wrong");
            }
        });


    }
}
