package com.digitalcashbag.m2p;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.SMSReceiver;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.utilities.recharges.activities.AddMoney;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.m2p.RequestKit;
import kkm.com.core.model.request.utility.RequestBalanceAmount;
import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
import kkm.com.core.model.response.m2p.requestkit.ResponseKit;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class M2PKitRequest extends BaseActivity implements
        SMSReceiver.OTPReceiveListener {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_user_id)
    TextView etUserId;
    @BindView(R.id.et_mobile)
    TextView etMobile;
    @BindView(R.id.et_otp)
    EditText etOtp;
    @BindView(R.id.tv_get_otp)
    TextView tvGetOtp;
    @BindView(R.id.tv_resend_otp)
    TextView tvResendOtp;
    @BindView(R.id.signup_btn)
    Button signupBtn;
    String msg = "";
    int DELAY_TIME = 3000;
    SharedPreferences pref;
    @BindView(R.id.kit_details_rg_card_type)
    RadioGroup kitDetailsRgCardType;
    @BindView(R.id.virtual)
    RadioButton virtual;
    @BindView(R.id.physical)
    RadioButton physical;
    private SMSReceiver smsReceiver;
    private String pin = "", cardType = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_kit_request);
        ButterKnife.bind(this);
        title.setText("Request for Bank Kit");
        etMobile.setText(PreferencesManager.getInstance(context).getMOBILE());
        etUserId.setText(PreferencesManager.getInstance(context).getLoginID());

        kitDetailsRgCardType.setOnCheckedChangeListener((radioGroup, i) -> {
            if (virtual.isChecked()) {
                cardType = "V";
            } else if (physical.isChecked()) {
                cardType = "P";
            }
        });
    }

    @OnClick({R.id.side_menu, R.id.tv_get_otp, R.id.tv_resend_otp, R.id.signup_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                onBackPressed();
                break;
            case R.id.tv_get_otp:
                if (cardType.equalsIgnoreCase("")) {
                    showAlert("Please select your card type.", R.color.red, R.drawable.error);
                } else if (PreferencesManager.getInstance(context).getIsKycVerified().equalsIgnoreCase("Approved")) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getBalanceAmount();
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                } else {
                    showAlert("Your Kyc is not submitted or not approved by admin.", R.color.red, R.drawable.error);
                }
                break;
            case R.id.tv_resend_otp:
                getOTPNew(msg, etMobile.getText().toString().trim());
                break;
            case R.id.signup_btn: {
                if (TextUtils.isEmpty(etOtp.getText().toString())) {
                    etOtp.setText("");
                    etOtp.setError("Enter OTP");
                } else if (etOtp.getText().toString().length() < 4) {
                    etOtp.setError("Invalid OTP");
                } else if (etOtp.getText().toString().equalsIgnoreCase(pin)) {
                    getKitCall();
                } else {
                    etOtp.setText("");
                    etOtp.setError("Invalid OTP");
                }
            }
            break;
        }
    }

    private void getBalanceAmount() {
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

                    if (response.body() != null && convertedObject.getStatus().equalsIgnoreCase("Success")) {
                        if (cardType.equalsIgnoreCase("P")) {
                            float physical_card_amount = 414;
                            checkBalanceForCardRequest(physical_card_amount, convertedObject.getBalanceAmount());
                        } else if (cardType.equalsIgnoreCase("V")) {
                            tvGetOtp.setVisibility(View.GONE);
                            pin = generatePin();
                            msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
                            getOTPNew(msg, etMobile.getText().toString().trim());
                            tvResendOtp.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                LoggerUtil.logItem(t.getMessage());
            }
        });
    }

    private void checkBalanceForCardRequest(float card_amount, float bagAmount) {
        if (bagAmount >= card_amount) {
            tvGetOtp.setVisibility(View.GONE);
            pin = generatePin();
            msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
            getOTPNew(msg, etMobile.getText().toString().trim());
            tvResendOtp.setVisibility(View.VISIBLE);
        } else {
            createAddBalanceDialog(context, "Insufficient bag balance", "You must have ₹ " + card_amount +
                    " in your wallet to avail this request, add money before card request.");
        }
    }

    public void createAddBalanceDialog(Context context, String title, String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setNegativeButton("Add Balance", (dialog, id) -> {
            dialog.cancel();
            Bundle b = new Bundle();
            b.putString("total", "");
            b.putString("from", "kit_req");
            goToActivity(this, AddMoney.class, b);
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void getKitCall() {
        hideKeyboard();
        showLoading();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginId", PreferencesManager.getInstance(context).getLoginID());
        jsonObject.addProperty("CardType", cardType);

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(jsonObject.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LoggerUtil.logItem(body);
        Call<JsonObject> responseKitCall = apiServicesM2PV2.responseKitCall(body);
        responseKitCall.enqueue(new Callback<JsonObject>() {
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
                ResponseKit response_new;
                try {
                    response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key), ResponseKit.class);
                    Log.e("============ ", String.valueOf(response_new));
                    if (response.body() != null && response_new.getResponse().equalsIgnoreCase("Success")) {
                        showAlert("Your request have been submitted for approval. Thank you", R.color.colorPrimary, R.drawable.ic_success);
                        new Handler().postDelayed(() -> finish(), DELAY_TIME);
                    } else {
                        showAlert(response_new.getResult().getException(), R.color.red, R.drawable.error);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void startSMSListener() {
        try {
            smsReceiver = new SMSReceiver();
            smsReceiver.setOTPListener(this);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
            this.registerReceiver(smsReceiver, intentFilter);

            SmsRetrieverClient client = SmsRetriever.getClient(this);

            Task<Void> task = client.startSmsRetriever();
            task.addOnSuccessListener(aVoid -> {
                // API successfully started
            });

            task.addOnFailureListener(e -> {
                // Fail to start API
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getOTPNew(String msg, String mobile) {
        showLoading();
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        Log.e("==Registration====>> ", msg);
        String url = BuildConfig.SMS_URL + mobile + "&msg=" + msg.replace(" ", "%20");
        LoggerUtil.logItem(url);
        Call<String> call = apiServices.getOtp(url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                hideLoading();
                showAlert("An OTP has been sent on your registered mobile no *******" + mobile.substring(6) + ".",
                        R.color.green, R.drawable.alerter_ic_notifications);
                startSMSListener();
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
//                hideLoading();
                showAlert("Something went wrong, Try again.",
                        R.color.red, R.drawable.error);
            }
        });
    }

    @Override
    public void onOTPReceived(String otp) {
        try {
//            showToast("OTP Received: " + otp);
            if (smsReceiver != null) {
                LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);
            }
            String[] bits = otp.split(" ");
            String lastWord = bits[bits.length - 2];
            etOtp.setText(lastWord);
            hideLoading();
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOTPTimeOut() {
    }

    @Override
    public void onOTPReceivedError(String error) {
        showMessage(error);
    }


}
