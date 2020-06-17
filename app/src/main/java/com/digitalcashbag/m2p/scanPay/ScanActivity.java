package com.digitalcashbag.m2p.scanPay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.digitalcashbag.shopping.Cons;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.digitalcashbag.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.m2p.RequestPayAmount;
import kkm.com.core.model.request.utility.RequestBalanceAmount;
import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
import kkm.com.core.model.response.cashbagCardDetails.ResponseCradDetails;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanActivity extends BaseActivity {

    private static DecimalFormat formatWallet = new DecimalFormat("0.00");
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_amount)
    EditText etAmount;
    @BindView(R.id.tv_name_letters)
    TextView tvNameLetters;
    @BindView(R.id.tv_ben_name)
    TextView tvBenName;
    @BindView(R.id.tv_ben_mobile)
    TextView tvBenMobile;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.tv_payer_name)
    TextView tvPayerName;
    @BindView(R.id.tv_payer_mobile)
    TextView tvPayerMobile;
    @BindView(R.id.tv_wallet_balance)
    TextView tv_wallet_balance;
    @BindView(R.id.btn_pay)
    Button btnPay;
    String cardNumber = "", upi = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanned_activity);
        ButterKnife.bind(this);

        title.setText("Pay Here");

        tvPayerName.setText(String.format("%s %s", PreferencesManager.getInstance(context).getNAME(), PreferencesManager.getInstance(context).getLNAME()));
        tvPayerMobile.setText(PreferencesManager.getInstance(context).getMOBILE());
        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getWalletBalanceCheckRunTime();


        new IntentIntegrator(context)
                .setOrientationLocked(false)
                .setBeepEnabled(true)
                .setCaptureActivity(Custom_Scanner.class)
                .initiateScan();
    }

    private void getCardDetails() {

        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("entityId", PreferencesManager.getInstance(context).getENTITY_ID());
        LoggerUtil.logItem(object);


        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(object.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<JsonObject> call = apiServicesM2PV2.getCardDetails(body);
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
                ResponseCradDetails response_new = null;
                try {
                    response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key), ResponseCradDetails.class);
                    Log.e("============ ", String.valueOf(response_new));
                    if (response_new != null && response_new.getResponse().equalsIgnoreCase("Success")) {
                        if (response_new.getResponse().equalsIgnoreCase("success")) {
                            cardNumber = response_new.getResult().getResult().getCardList();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

            }
        });
    }

    private void getWalletBalanceCheckRunTime() {
        try {
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
                    try {
                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                        ResponseBalanceAmount convertedObject = new Gson().fromJson(paramResponse, ResponseBalanceAmount.class);
                        if (convertedObject.getStatus().equalsIgnoreCase("Success")) {
                            tv_wallet_balance.setText(String.valueOf(formatWallet.format(convertedObject.getBalanceAmount())));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                try {
                    Log.e("SCANNED", result.getContents());
                    upi = result.getContents();
                    tvBenName.setText(between(upi, "pn=", "&mc").replace("%20", " "));
                    tvBenMobile.setText(between(upi, "pa=", "@"));
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getCardDetails();
                    } else {
                        showMessage(getString(R.string.alert_internet));
                    }
                } catch (Error | Exception e) {
                    e.printStackTrace();
                    finishActivity(context);
                }
            } else {
                finishActivity(context);
            }
        } else {
            finishActivity(context);
        }
    }

    @OnClick({R.id.side_menu, R.id.btn_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                onBackPressed();
                break;
            case R.id.btn_pay:
                if (cardNumber.equalsIgnoreCase("")) {
                    if (!(etAmount.getText().toString().trim().equalsIgnoreCase("")) &&
                            Double.parseDouble(etAmount.getText().toString().trim()) > 0) {
                        if (NetworkUtils.getConnectivityStatus(context) != 0)
                            getAmountPaid(upi);
                        else
                            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    } else {
                        showAlert("Invalid amount.", R.color.red, R.drawable.error);
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getAmountPaid(String upi) {
        RequestPayAmount amount = new RequestPayAmount();
        amount.setMerchantData(upi);
        amount.setFromEntityId(PreferencesManager.getInstance(context).getLoginID());
        amount.setToEntityId("");
        amount.setProductId("GENERAL");
        amount.setDescription(etRemark.getText().toString().trim());
        amount.setAmount(etAmount.getText().toString().trim());
        amount.setTransactionType("PURCHASE");
        amount.setTransactionOrigin("MOBILE");
        amount.setExternalTransactionId(String.valueOf(System.currentTimeMillis()));
        amount.setBusinessType(BuildConfig.M2P_BUSINESSTYPE);
        amount.setBusinessEntityId(BuildConfig.M2P_BUSINESSTYPE);
        amount.setFirstName(PreferencesManager.getInstance(context).getNAME());
        amount.setLastName(PreferencesManager.getInstance(context).getLNAME());
        amount.setContactNo(PreferencesManager.getInstance(context).getMOBILE());
        amount.setSenderCardNo(cardNumber);
        amount.setBillRefNo("CB-" + String.valueOf(System.currentTimeMillis()));
        amount.setAdditionalData("");
        amount.setLoginId(PreferencesManager.getInstance(context).getLoginID());

        LoggerUtil.logItem(amount);
        showLoading();

        Call<JsonObject> call = apiServicesM2PV2.getAmountPaid(amount);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                Bundle b = new Bundle();
                b.putString("from", "ScanActivity");
                b.putString("response", response.body().toString());
                b.putString("name", tvBenName.getText().toString());
                b.putString("mobile", tvBenMobile.getText().toString());
                b.putString("amount", String.valueOf(amount));
                goToActivityWithFinish(context, ScanPayResult.class, b);
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                hideLoading();
            }
        });
    }

    static String between(String value, String a, String b) {
        // Return a substring between the two strings.
        int posA, posB = 0, adjustedPosA = 0;
        try {

            posA = value.indexOf(a);
            if (posA == -1) {
                return "";
            }
            posB = value.lastIndexOf(b);
            if (posB == -1) {
                return "";
            }
            adjustedPosA = posA + a.length();
            if (adjustedPosA >= posB) {
                return "";
            }
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
        return value.substring(adjustedPosA, posB);
    }
}
