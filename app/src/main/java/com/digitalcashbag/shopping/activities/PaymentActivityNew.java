package com.digitalcashbag.shopping.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.utilities.tatasky.TataskyBookingStatus;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.app.AppConfig;
import kkm.com.core.app.CheckErrorCode;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.utility.RequestBalanceAmount;
import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
import kkm.com.core.model.response.CreateOrderResponse;
import kkm.com.core.model.response.tatasky.response.ResponseTataskyBooking;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class PaymentActivityNew extends BaseActivity {

    private static DecimalFormat format = new DecimalFormat("0.00");
    @BindView(R.id.menu_img)
    ImageView menuImg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.order_amount)
    TextView orderAmount;
    @BindView(R.id.wallet_balance_check)
    TextView walletBalanceCheck;
    @BindView(R.id.wallet_balance_unchecked)
    TextView walletBalanceUnchecked;

    @BindView(R.id.textView44)
    TextView textView44;
    @BindView(R.id.tv_payment)
    Button tvPayment;

    private Float total_amount_shopping;
    private Float product_total;
    private Float rest_amount;
    private Float walletBalance;
    private Float courierPrice;
    private String from;
    private boolean withCourier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_new);
        ButterKnife.bind(this);
        getWalletBalance("");
    }


    @Override
    protected void onResume() {
        super.onResume();
//        walletBalanceUnchecked.setText(String.format("Available Balance ₹ %s", walletBalance));
    }

    //     Shopping Orders
    private void createOrder(String amount, String status) {

        showLoading();

//        {"ShippingId":"26","AddedBy":"13191","BillingId":"26","PaymentMode":"Wallet","TotalAmount":"216.00","WalletAmount":"216.00",
//          "GatewayAmount":"0","ShippingCharges":""}

        JsonObject json = new JsonObject();
        json.addProperty("ShippingId", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("SHIPPING_ID"));
        json.addProperty("BillingId", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("BILLING_ID"));
        json.addProperty("AddedBy", PreferencesManager.getInstance(context).getUSERID());
        json.addProperty("PaymentStatus", status);
        json.addProperty("PaymentMode", "WALLET");


        if (withCourier) {
            json.addProperty("ShippingCharges", courierPrice);
        } else {
            json.addProperty("ShippingCharges", "0");
        }

        if (rest_amount == 0) {
            json.addProperty("WalletAmount", amount);
            json.addProperty("GatewayAmount", "0");
        }
        json.addProperty("Mode", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("Mode"));

        String total_amount = String.valueOf(format.format(json.get("GatewayAmount").getAsDouble() + json.get("WalletAmount").getAsDouble()));
        json.addProperty("TotalAmount", total_amount);

        LoggerUtil.logItem(json);
        Call<CreateOrderResponse> createOrderCall = apiServices_shoopping.getCreateOrder(json);
        createOrderCall.enqueue(new Callback<CreateOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreateOrderResponse> call, @NonNull Response<CreateOrderResponse> response) {
                hideLoading();
                try {
                    LoggerUtil.logItem(response.body());
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                        try {

                            if (status.equalsIgnoreCase("success")) {
                                getDebitWalletBalance(response.body().getOrderNo(), amount, total_amount, response.body().getOrderNo());
                            } else {
                                Bundle param = new Bundle();
                                param.putString("status", status);
                                param.putString("AMOUNT", total_amount);
                                param.putString("SHIPPING_ID", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("SHIPPING_ID"));
                                param.putString("BILLING_ID", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("BILLING_ID"));
                                param.putString("TOTALITEMS", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("TOTALITEMS"));
                                param.putString("ORDER_ID", response.body().getOrderNo());
                                goToActivityWithFinish(context, OrderPlaced.class, param);
                            }


                        } catch (Exception e) {
                            hideLoading();
                            e.printStackTrace();
                            Toast.makeText(context, "Something went wrong.Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.body() != null && response.body().getResponse().equalsIgnoreCase("insufficient Balance")) {
                        Toast.makeText(context, "Order failed, due to insufficient balance.\nYour wallet balance is : " + response.body().getWalletAmmount(), Toast.LENGTH_SHORT).show();
                    } else if (response.body() != null) {
                        Toast.makeText(context, response.body().getResponse(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreateOrderResponse> call, @NonNull Throwable t) {
                LoggerUtil.logItem(t.getMessage());
                hideLoading();
            }
        });
    }

    //    GetDebitAmount
    private void getDebitWalletBalance(String orderNo, String amount, String totalAmount, String orderId) {
        JsonObject mainjson = new JsonObject();
        mainjson.addProperty("FromId", PreferencesManager.getInstance(context).getUSERID());
        mainjson.addProperty("ToId", PreferencesManager.getInstance(context).getUSERID());
        mainjson.addProperty("OrderNo", orderNo);
        mainjson.addProperty("DebitType", AppConfig.PAYLOAD_DEBIT_TYPE_SHOPPING);
        mainjson.addProperty("DebitAmount", amount);
        LoggerUtil.logItem(mainjson);

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(mainjson.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<JsonObject> debitWalletBalanceCall = apiServices_utilityV2.getDebitWalletBalance(body);
        debitWalletBalanceCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());

                try {

                    Bundle param = new Bundle();
                    param.putString("status", "Success");
                    param.putString("AMOUNT", String.valueOf(totalAmount));
                    param.putString("SHIPPING_ID", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("SHIPPING_ID"));
                    param.putString("BILLING_ID", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("BILLING_ID"));
                    param.putString("TOTALITEMS", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("TOTALITEMS"));
                    param.putString("ORDER_ID", orderId);
                    goToActivityWithFinish(context, OrderPlaced.class, param);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void getWalletBalance(String amountOrder) {
        try {
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
                            walletBalance = convertedObject.getBalanceAmount();
                            walletBalanceCheck.setText(String.format("₹ %s", walletBalance));

                            if (!TextUtils.isEmpty(amountOrder)) {
                                if (walletBalance >= Float.parseFloat(amountOrder)) {
                                    if (from.equalsIgnoreCase("shopping")) {
                                        createOrder(String.valueOf(total_amount_shopping), "Success");
                                    } else if (from.equalsIgnoreCase("tata_sky")) {
                                        getTataSkyBooking(String.valueOf(total_amount_shopping));
                                    }
                                } else {
                                    Toast.makeText(context, "Insufficient balance in bag.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            // First time call for get bag Balance
                            if (amountOrder.equalsIgnoreCase("")) {

                                Bundle bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
                                if (bundle != null) {

                                    total_amount_shopping = Float.parseFloat(bundle.getString("total", "0"));
                                    from = bundle.getString("from");

                                    if (from.equalsIgnoreCase("shopping")) {
                                        product_total = Float.parseFloat(bundle.getString("product_total"));
                                        courierPrice = Float.parseFloat(bundle.getString("courierPrice"));
                                        withCourier = bundle.getBoolean("with");

                                        LoggerUtil.logItem("--------------courierPrice--------------");
                                        LoggerUtil.logItem(courierPrice);
                                        LoggerUtil.logItem(withCourier);
                                    }

                                }

                                orderAmount.setText(String.format("₹ %s", String.valueOf(total_amount_shopping)));
                                try {
                                    tvPayment.setText(String.format("Pay Securely %s", total_amount_shopping));
                                    //to check wallet balance and change view accordingly


                                    if (walletBalance > 0) {
                                        rest_amount = (total_amount_shopping - walletBalance);
                                        if (rest_amount <= 0) {
                                            rest_amount = 0f;
                                            walletBalanceUnchecked.setVisibility(View.GONE);
                                        } else {
                                            walletBalanceUnchecked.setText("Insufficient balance in bag.");
                                            walletBalanceUnchecked.setVisibility(View.VISIBLE);
                                        }

                                    } else {
                                        rest_amount = 0f;
                                        walletBalanceUnchecked.setVisibility(View.VISIBLE);
                                        walletBalanceUnchecked.setText("Insufficient balance in bag.");
                                    }

                                    walletBalanceCheck.setText(String.format("₹ %s", walletBalance));


                                    tvPayment.setOnClickListener(v -> {

                                        if (rest_amount == 0) {
                                            // Wallet have Balance
                                            getWalletBalance(String.valueOf(total_amount_shopping));
                                        } else {
                                            showMessage("Insufficient balance in bag.");
                                        }

                                    });
                                    menuImg.setOnClickListener(v -> finish());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        } else {
                            showMessage("Something went wrong.Please try again.");
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

    ////////////////////////////////////////////////// Tatasky Booking////////////////////////////////////////////////////////////////
    private void getTataSkyBooking(String total) {
        try {
            showLoading();

            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String formattedDate = df.format(c);

            JsonObject mainjson = new JsonObject();
            mainjson.addProperty("Type", AppConfig.PAYLOAD_TYPE_FIVE_TATA_TRANSACTION);
            mainjson.addProperty("Amount", total);
            mainjson.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
            mainjson.addProperty("Amount_All", total);
            mainjson.addProperty("fName", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("FirstName"));
            mainjson.addProperty("lName", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("Lastname"));
            mainjson.addProperty("NUMBER", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("MobNo"));
            mainjson.addProperty("ProductId", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("product_id"));
            mainjson.addProperty("RegionId", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("region_id"));
            mainjson.addProperty("bookingDate", formattedDate);
            mainjson.addProperty("benAddressline1", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("Buildingno"));
            mainjson.addProperty("benAddressline2", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("StreetName"));
            mainjson.addProperty("benCity", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("City"));
            mainjson.addProperty("Pin", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("Pincode"));
            mainjson.addProperty("State", getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("State"));
            LoggerUtil.logItem(mainjson);

            JsonObject body = new JsonObject();
            try {
                body.addProperty("body", Cons.encryptMsg(mainjson.toString(), easypay_key));
                LoggerUtil.logItem(body);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Call<JsonObject> prepaidCall = apiServices_utilityV2.getTataskyBooking(body);
            prepaidCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.body() != null) {


                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                            List<ResponseTataskyBooking> list = Utils.getList(paramResponse, ResponseTataskyBooking.class);


                            if (list.get(0).getError().equalsIgnoreCase("0") && list.get(0).getResult().equalsIgnoreCase("0")) {
                                Bundle param = new Bundle();
                                param.putString("TRANS_STATUS", "Booked Successfully");
                                param.putString("AMT", total);
                                param.putString("Date", list.get(0).getDate());
                                goToActivityWithFinish(context, TataskyBookingStatus.class, param);
                            } else {
                                CheckErrorCode code = new CheckErrorCode();
                                code.isValidTransaction(context, list.get(0).getError());
                                Bundle param = new Bundle();
                                param.putString("TRANS_STATUS", "Booking Failed");
                                param.putString("AMT", total);
                                param.putString("Date", list.get(0).getDate());
                                goToActivityWithFinish(context, TataskyBookingStatus.class, param);
                            }
                        } else {
                            Toast.makeText(context, "Something went wrong. \nPlease try after some time.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Something went wrong. \nPlease try after some time.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

