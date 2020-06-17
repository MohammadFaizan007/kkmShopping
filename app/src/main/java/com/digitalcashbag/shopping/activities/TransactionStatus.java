package com.digitalcashbag.shopping.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.billpayment.RequestCollectPay;
import kkm.com.core.model.request.utility.RequestBalanceAmount;
import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
import kkm.com.core.model.response.bill.ResponseCollectUpi;
import kkm.com.core.model.response.jioPrepaid.ResponseJioPrepaidRecharge;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class TransactionStatus extends BaseActivity {

    private static DecimalFormat format = new DecimalFormat("0.00");
    @BindView(R.id.imageView11)
    ImageView imageView11;
    @BindView(R.id.imageTimer)
    ImageView imageTimer;
    @BindView(R.id.transAmount)
    TextView transAmount;
    @BindView(R.id.imgStatus)
    ImageView imgStatus;
    @BindView(R.id.txtNarration)
    TextView txtNarration;
    @BindView(R.id.txtDateTime)
    TextView txtDateTime;
    @BindView(R.id.imgPayment)
    ImageView imgPayment;
    @BindView(R.id.transID)
    TextView transID;
    @BindView(R.id.imgCopy)
    ImageView imgCopy;
    @BindView(R.id.done)
    Button done;
    @BindView(R.id.txtWalletBalance)
    TextView txtWalletBalance;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bg_view)
    ConstraintLayout bgView;
    private String total_amount;
    private String upi;
    private boolean backBool = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_status);
        ButterKnife.bind(this);
        title.setText("Transaction Status");
        sideMenu.setOnClickListener(v -> onBackPressed());
        sideMenu.setVisibility(View.GONE);
        done.setVisibility(View.GONE);


        Bundle bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        if (bundle != null) {
            total_amount = format.format(Double.parseDouble(bundle.getString("amount")));
            upi = bundle.getString("upi");
            String from = bundle.getString("from");
            LoggerUtil.logItem(total_amount);
        }

        imgStatus.setVisibility(View.VISIBLE);
        txtNarration.setText("Transaction Pending");
        transAmount.setText(String.format("₹%s", String.valueOf(total_amount)));
        transID.setText("NA");

        showMessage("Do not press back button while payment is in process.");

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            backBool = false;
            animate();
            makeUpiPayment();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

        txtDateTime.setText(Utils.getTodayDatetime());
    }


    private void animate() {
        Drawable drawable = imageTimer.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

    private void stopAnimate() {
        Drawable drawable = imageTimer.getDrawable();
        if (drawable instanceof Animatable) {
            if (((Animatable) drawable).isRunning()) {
                ((Animatable) drawable).stop();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (createOrderCall != null) {
            createOrderCall.cancel();
        }
        super.onDestroy();

    }

    Call<JsonObject> createOrderCall;

    private void makeUpiPayment() {
//        RequestCollectPay collectPay = new RequestCollectPay();
//        collectPay.setFk_MemId(PreferencesManager.getInstance(context).getUSERID());
//        collectPay.setAMOUNT(String.valueOf(total_amount));
//        collectPay.setAMOUNTALL(String.valueOf(total_amount));
//        collectPay.setBillNumber("WA" + System.currentTimeMillis());
//        collectPay.setCOMMENT("Add wallet balance");
//        collectPay.setNUMBER(upi);
//        collectPay.setType("1");

        JsonObject object = new JsonObject();
        object.addProperty("Fk_MemId",PreferencesManager.getInstance(context).getUSERID());
        object.addProperty("AMOUNT",String.valueOf(total_amount));
        object.addProperty("AMOUNT_ALL",String.valueOf(total_amount));
        object.addProperty("BillNumber","WA" + System.currentTimeMillis());
        object.addProperty("COMMENT","Add wallet balance");
        object.addProperty("NUMBER",upi);
        object.addProperty("Type","1");

        LoggerUtil.logItem(object);

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(object.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        createOrderCall = apiServices_utilityV2.getCollectPay(body);
        createOrderCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                LoggerUtil.logItem(response.body());
                try {

                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    List<ResponseCollectUpi> list = Utils.getList(paramResponse, ResponseCollectUpi.class);

                    if (response.body() != null && list.get(0).getError().equalsIgnoreCase("0") &&
                            (list.get(0).getResult().equalsIgnoreCase("0"))
                            && (list.get(0).getTrnxstatus().equalsIgnoreCase("7"))) {
                        upiPaymentStatus(TRANSACTION.TRANSACTION_SUCCESS, list.get(0));
                    } else if (response.body() != null && list.get(0).getError().equalsIgnoreCase("0") &&
                            (list.get(0).getResult().equalsIgnoreCase("0"))
                            && (list.get(0).getTrnxstatus().equalsIgnoreCase("3"))) {
                        upiPaymentStatus(TRANSACTION.TRANSACTION_PENDING, list.get(0));
                    } else {
                        upiPaymentStatus(TRANSACTION.TRANSACTION_FAILED, list.get(0));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    upiPaymentStatus(TRANSACTION.ERROR, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                LoggerUtil.logItem("Failure");
                LoggerUtil.logItem(t.getMessage());
                upiPaymentStatus(TRANSACTION.ERROR, null);
            }
        });
    }


    private void upiPaymentStatus(TRANSACTION status, ResponseCollectUpi collectUpi) {
        switch (status) {
            case TRANSACTION_SUCCESS: {
                bgView.setBackgroundResource(R.drawable.trans_success);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.trans_done);
                imgStatus.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                transID.setText(collectUpi.getTransid());
                txtNarration.setText("Transaction Successful");
                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                stopAnimate();
                imageTimer.setVisibility(View.INVISIBLE);
                break;
            }
            case TRANSACTION_PENDING: {
                bgView.setBackgroundResource(R.drawable.trans_pending);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.warning);
                imgStatus.setColorFilter(ContextCompat.getColor(context, R.color.orange), android.graphics.PorterDuff.Mode.MULTIPLY);
                transID.setText(collectUpi.getTransid());
                txtNarration.setText("Transaction Pending");
                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.orange));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.orange));
                stopAnimate();
                imageTimer.setVisibility(View.INVISIBLE);
                break;
            }
            case TRANSACTION_FAILED: {
                bgView.setBackgroundResource(R.drawable.trans_failed);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.rchg_failed);
                imgStatus.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                transID.setText(collectUpi.getTransid());
                txtNarration.setText("Transaction Failed");
                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.red));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.red));
                stopAnimate();
                imageTimer.setVisibility(View.INVISIBLE);

                break;
            }
            case ERROR: {
                bgView.setBackgroundResource(R.drawable.trans_failed);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.rchg_failed);
                imgStatus.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                if (collectUpi != null && collectUpi.getTransid() != null) {
                    transID.setText(collectUpi.getTransid());
                } else {
                    transID.setText("NA");
                }
                txtNarration.setText("Transaction Failed");
                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.red));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.red));
                stopAnimate();
                imageTimer.setVisibility(View.INVISIBLE);

                break;
            }
        }
        sideMenu.setVisibility(View.VISIBLE);
        done.setVisibility(View.VISIBLE);
        backBool = true;
        getBalanceAmount();
    }

    @OnClick({R.id.imgCopy, R.id.done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgCopy:
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Referral Code", transID.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
                break;
            case R.id.done:
                onBackPressed();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (backBool) {
            super.onBackPressed();
        } else {
            showMessage("Do not press back button while payment is in process.");
        }

    }

    private void getBalanceAmount() {
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
                try {
                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    ResponseBalanceAmount convertedObject = new Gson().fromJson(paramResponse, ResponseBalanceAmount.class);
                    if (response.body() != null && convertedObject.getStatus().equalsIgnoreCase("Success")) {
                        txtWalletBalance.setText(String.format("₹%s", Float.parseFloat(String.valueOf(convertedObject.getBalanceAmount()))));
                    }
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }
}
