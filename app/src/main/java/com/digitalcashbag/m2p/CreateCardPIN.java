package com.digitalcashbag.m2p;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.digitalcashbag.PinEncryptionTests;
import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.cashbagCardDetails.ResponseCradDetails;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCardPIN extends BaseActivity {

    @BindView(R.id.indicator_dots)
    IndicatorDots indicatorDots;
    @BindView(R.id.pin_lock_view)
    PinLockView pinLockView;
    String expDate = "";
    @BindView(R.id.tv_card_number)
    TextView tvCardNumber;
    @BindView(R.id.tv_valid_to)
    TextView tvValidTo;
    @BindView(R.id.tv_name)
    TextView tvName;

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
//            TODO
            confirmDialog(context, pin);
        }

        @Override
        public void onEmpty() {
            Log.e("PIN", "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.e("PIN", "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.cashbag_card_pin_generation);
            ButterKnife.bind(this);

            pinLockView.attachIndicatorDots(indicatorDots);
            pinLockView.setPinLockListener(mPinLockListener);

            pinLockView.setPinLength(4);
            pinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

            indicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FIXED);

            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getCardDetails();
            } else {
                showMessage(getString(R.string.alert_internet));
            }
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    public void confirmDialog(Context context, String pin) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Cashbag Card PIN");
        builder1.setMessage("Are you sure you want to proceed with the entered PIN...");
        builder1.setCancelable(true);

        builder1.setPositiveButton("Yes", (dialog, id) -> {
//            TODO
            createPIN(pin);
            dialog.dismiss();
        });

        builder1.setNegativeButton("No", (dialog, id) -> dialog.dismiss());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void createPIN(String pin) {
        String encryptedPin = "";
        PinEncryptionTests tests = new PinEncryptionTests();
        try {
            encryptedPin = tests.encryptionPinBlock(PreferencesManager.getInstance(context).getKIT_NO(), pin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pin", encryptedPin);
        jsonObject.addProperty("entityId", PreferencesManager.getInstance(context).getENTITY_ID());
        jsonObject.addProperty("kitNo", PreferencesManager.getInstance(context).getKIT_NO());
        jsonObject.addProperty("expiryDate", expDate);
        jsonObject.addProperty("dob", dob);

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(jsonObject.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<JsonObject> call = apiServicesM2PV2.createCardPIN(body);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().get("response").getAsString().equalsIgnoreCase("success")) {
                    showMessage("PIN generated sugetSignccessfully");
                    finish();
                } else {
                    showMessage("Please try later.");
                    LoggerUtil.logItem(response.message());
                    LoggerUtil.logItem(response.code());
                    LoggerUtil.logItem(response.errorBody());
                    LoggerUtil.logItem(response.headers());
                    LoggerUtil.logItem(response.raw().body());
                    LoggerUtil.logItem(call.request().url());
                    JsonObject response_new;
                    try {
                        response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key), JsonObject.class);
                        Log.e("============ ", String.valueOf(response_new));
                        if (response.body() != null && response_new.get("response").getAsString().equalsIgnoreCase("Success")) {
                            showMessage("PIN generated successfully");
                            finish();
                        } else {
                            showMessage("Please try later.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                hideLoading();
                showMessage("Please try later.");
            }
        });
    }

    String dob = "";

    private void getCardDetails() {
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
                    if (response.body() != null && response_new.getResponse().equalsIgnoreCase("Success")) {
                        String expiry = response_new.getResult().getResult().getExpiryDateList().substring(0, 2) + "/" + response_new.getResult().getResult().getExpiryDateList().substring(2, 4);
                        tvValidTo.setText(expiry);
//                    tvValidTo.setText(response.body().getResult().getResult().getExpiryDateList());

                        String cardNu = response_new.getResult().getResult().getCardList();
                        String formatted = cardNu.substring(0, 4) + "  " +
                                cardNu.substring(4, 8) + "  " +
                                cardNu.substring(8, 12) + "  " +
                                cardNu.substring(12, 16);
                        tvCardNumber.setText(formatted);
                        dob = response_new.getResult().getResult().getDob();
                        tvName.setText(response_new.getResult().getResult().getName());
                        expDate = response_new.getResult().getResult().getExpiryDateList();
                    } else {
                        showMessage("Please try later.");
                        finish();
                    }
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                hideLoading();
                showMessage("Please try later.");
                finish();
            }
        });
    }


}
