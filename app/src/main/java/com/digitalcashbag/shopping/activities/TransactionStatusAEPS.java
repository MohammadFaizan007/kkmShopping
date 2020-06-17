package com.digitalcashbag.shopping.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.digitalcashbag.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class TransactionStatusAEPS extends BaseActivity {

    String response, from, amount;

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

    private JSONObject parseSuccessJson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_status);
        ButterKnife.bind(this);

        title.setText("Transaction Status");
        sideMenu.setOnClickListener(v -> onBackPressed());

        Bundle bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        if (bundle != null) {
            response = bundle.getString("response");
            from = bundle.getString("from");
            amount = bundle.getString("amount");
        }

        try {
            parseSuccessJson = new JSONObject(response);
            Log.e("AUTH ", parseSuccessJson.toString());


//                        {
//                            "result": {
//                                  "payload": {
//                                                  "metadata": "{\"refno\":\"CAS8122711000000\"}",
//                                              "aeps": {
//                                            "orderId": "3325448226845304",
//                                            "amount": 0,
//                                            "orderStatus": "FAILURE",
//                                            "paymentStatus": "FAILURE",
//                                            "processingCode": "310000",
//                                            "accountBalance": "0.00",
//                                            "bankResponseMsg": "There is some internal issues at our end.Please try again later.",
//                                            "bankResponseMessage": "There is some internal issues at our end.Please try again later.",
//                                            "dateTime": 1556258572534,
//                                            "statusCode": "401",
//                                            "commissionAmt": 0,
//                                            "gstAmt": 0,
//                                            "tdsAmt": 0,
//                                            "isWalletFailed": false,
//                                            "walletFailed": false
//                                }
//                            }
//                        }
//                        }


            imgStatus.setVisibility(View.VISIBLE);
            txtNarration.setText("Transaction Pending");
            transAmount.setText(String.format("₹%s", amount));
            transID.setText("NA");

            imgPayment.setImageResource(R.drawable.aeps);


            if (parseSuccessJson != null && parseSuccessJson.getJSONObject("result").getJSONObject("payload").getJSONObject("aeps")
                    .getString("orderStatus").equalsIgnoreCase("FAILURE")) {
                aepsPaymentStatus(TRANSACTION.TRANSACTION_FAILED, parseSuccessJson.getJSONObject("result").getJSONObject("payload").getJSONObject("aeps")
                        .getString("orderId"));
            } else if (parseSuccessJson != null && parseSuccessJson.getJSONObject("result").getJSONObject("payload").getJSONObject("aeps")
                    .getString("orderStatus").equalsIgnoreCase("SUCCESS")) {
                aepsPaymentStatus(TRANSACTION.TRANSACTION_SUCCESS, parseSuccessJson.getJSONObject("result").getJSONObject("payload").getJSONObject("aeps")
                        .getString("orderId"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

//
//

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            submitAepsTransaction();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

        done.setOnClickListener(v -> onBackPressed());

        imgCopy.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("TransId", transID.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
        });

        txtDateTime.setText(Utils.getTodayDatetime());
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
        super.onBackPressed();
    }

    private void submitAepsTransaction() {

//        {
//                "Fk_MemId": "100",
//                "ReferenceNo": "12345",
//                "OrderId": "435332055530",
//                "Amount": "20",
//                "OrderStatus": "Success",
//                "PaymentStatus": "Success",
//                "ProcessingCode": "0212",
//                "AccountBalance": "5002",
//                "BankResponseMsg": "transaction success",
//                "BankResponseMessage": "transaction success",
//                "Date": "2019-04-21",
//                "Time":"11:46:49",
//                "StatusCode": "1",
//                "CommissionAmt": "20",
//                "GSTAmount": "20",
//                "TDSAmount": "20",
//                "IsWalletFailed": "1",
//                "WalletFailed": "1"
//        }


        //                        {
//                            "result": {
//                                  "payload": {
//                                                  "metadata": "{\"refno\":\"CAS8122711000000\"}",
//                                              "aeps": {
//                                            "orderId": "3325448226845304",
//                                            "amount": 0,
//                                            "orderStatus": "FAILURE",
//                                            "paymentStatus": "FAILURE",
//                                            "processingCode": "310000",
//                                            "accountBalance": "0.00",
//                                            "bankResponseMsg": "There is some internal issues at our end.Please try again later.",
//                                            "bankResponseMessage": "There is some internal issues at our end.Please try again later.",
//                                            "dateTime": 1556258572534,
//                                            "statusCode": "401",
//                                            "commissionAmt": 0,
//                                            "gstAmt": 0,
//                                            "tdsAmt": 0,
//                                            "isWalletFailed": false,
//                                            "walletFailed": false
//                                }
//                            }
//                        }
//                        }
        try {
            JSONObject aeps = parseSuccessJson.getJSONObject("result").getJSONObject("payload").getJSONObject("aeps");
            String metadata = parseSuccessJson.getJSONObject("result").getJSONObject("payload").getString("metadata");
            metadata = Utils.replaceBackSlash(metadata);
            JSONObject object = new JSONObject(metadata);

//            "Date": "2019-04-21",
//            "Time":"11:46:49",

            String date = Utils.getDateTimeFromTimeStamp(Long.parseLong(aeps.getString("dateTime")), "yyyy-MM-dd hh:mm:ss");
            LoggerUtil.logItem(date.split(" ")[0]);
            LoggerUtil.logItem(date.split(" ")[1]);

            showLoading();

            JsonObject param = new JsonObject();
            param.addProperty("Fk_MemId", PreferencesManager.getInstance(context).getUSERID());
            param.addProperty("ReferenceNo", object.getString("refno"));
            param.addProperty("OrderId", aeps.getString("orderId"));
            param.addProperty("Amount", aeps.getString("amount"));
            param.addProperty("OrderStatus", aeps.getString("orderStatus"));
            param.addProperty("PaymentStatus", aeps.getString("paymentStatus"));
            param.addProperty("ProcessingCode", aeps.getString("processingCode"));
            param.addProperty("AccountBalance", aeps.getString("accountBalance"));
            param.addProperty("BankResponseMsg", aeps.getString("bankResponseMsg"));
            param.addProperty("BankResponseMessage", aeps.getString("bankResponseMessage"));
            param.addProperty("Date", date.split(" ")[0]);
            param.addProperty("Time", date.split(" ")[1]);
            param.addProperty("StatusCode", aeps.getString("statusCode"));
            param.addProperty("CommissionAmt", aeps.getString("commissionAmt"));
            param.addProperty("GSTAmount", aeps.getString("gstAmt"));
            param.addProperty("TDSAmount", aeps.getString("tdsAmt"));
            param.addProperty("IsWalletFailed", aeps.getString("isWalletFailed"));
            param.addProperty("WalletFailed", aeps.getString("walletFailed"));

            Call<JsonObject> objectCall = apiServices.paymentAeps(param);
            objectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void aepsPaymentStatus(TRANSACTION status, String transId) {

        switch (status) {
            case TRANSACTION_SUCCESS:
                bgView.setBackgroundResource(R.drawable.trans_success);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.trans_done);

                transID.setText(transId);
                txtNarration.setText("Transaction Successful");

                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                imageTimer.setVisibility(View.INVISIBLE);
                break;
            case TRANSACTION_PENDING:
                bgView.setBackgroundResource(R.drawable.trans_pending);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.warning);
                imgStatus.setColorFilter(ContextCompat.getColor(context, R.color.orange), android.graphics.PorterDuff.Mode.MULTIPLY);

                transID.setText(transId);
                txtNarration.setText("Transaction Pending");
                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.orange));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.orange));
                imageTimer.setVisibility(View.INVISIBLE);
                break;
            case TRANSACTION_FAILED:
                bgView.setBackgroundResource(R.drawable.trans_failed);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.rchg_failed);
                imgStatus.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);

                transID.setText(transId);
                txtNarration.setText("Transaction Failed");
                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.red));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.red));
                imageTimer.setVisibility(View.INVISIBLE);
                break;
            case ERROR:
                bgView.setBackgroundResource(R.drawable.trans_failed);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.rchg_failed);
                imgStatus.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                transID.setText(transId);
                txtNarration.setText("Transaction Failed");
                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.red));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.red));
                imageTimer.setVisibility(View.INVISIBLE);
                break;
        }
    }

}
