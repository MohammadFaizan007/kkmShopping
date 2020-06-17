package com.digitalcashbag.m2p;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.m2p.adapter.M2PCardAdapter;
import com.digitalcashbag.m2p.scanPay.ScanPayResult;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.utilities.recharges.activities.AddMoney;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
import kkm.com.core.model.response.cashbagCardDetails.ResponseCradDetails;
import kkm.com.core.model.response.cvvResponse.ResponseCvvDetails;
import kkm.com.core.model.response.m2p.addfund.ResponseAddFund;
import kkm.com.core.model.response.m2p.transaction.ResponseAllTransaction;
import kkm.com.core.model.response.m2p.transaction.ResultItem;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class M2PCardDetail extends BaseActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.flip)
    TextView flip;
    boolean isFlip = true;
    @BindView(R.id.card_recycler)
    RecyclerView cardRecycler;


    M2PCardAdapter m2PCardAdapter;
    List<ResultItem> list_card = new ArrayList<>();
    @BindView(R.id.tv_card_number)
    TextView tvCardNumber;
    @BindView(R.id.tv_valid_to)
    TextView tvValidTo;
    @BindView(R.id.tv_name)
    TextView tvName;

    String cvvNumber = "";
    private static DecimalFormat format = new DecimalFormat("0.00");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_two_p_card_detail);
        ButterKnife.bind(this);
        title.setText("Card Detail");
        sideMenu.setOnClickListener(v -> onBackPressed());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cardRecycler.setLayoutManager(mLayoutManager);
        cardRecycler.setHasFixedSize(true);
        cardRecycler.setNestedScrollingEnabled(true);
        m2PCardAdapter = new M2PCardAdapter(context, list_card);
        cardRecycler.setAdapter(m2PCardAdapter);


        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getCardDetails();
        } else {
            showMessage(getString(R.string.alert_internet));
        }


        flip.setOnClickListener(v -> {
            if (isFlip) {
                isFlip = false;
                flip.setText(cvvNumber);
            } else {
                isFlip = true;
                flip.setText(getResources().getString(R.string.flip));
            }
        });

    }

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
                ResponseCradDetails response_new;
                try {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getM2pCard_allTransaction();
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }

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
                        tvName.setText(response_new.getResult().getResult().getName());


                        if (!response_new.getResult().getResult().getDob().equalsIgnoreCase("")) {
                            String expiryDate = response_new.getResult().getResult().getExpiryDateList().substring(2, 4) +
                                    response_new.getResult().getResult().getExpiryDateList().substring(0, 2);
                            getCvv(response_new.getResult().getResult().getDob(), expiryDate);
                        }

                    } else {
                        showMessage("Please try later.");
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                showMessage("Please try later.");
                finish();
            }
        });
    }


    private void getCvv(String dob, String exp) {
        JsonObject oldFormat = new JsonObject();
        oldFormat.addProperty("dob", dob);
        oldFormat.addProperty("entityId", PreferencesManager.getInstance(context).getENTITY_ID());
        oldFormat.addProperty("expiryDate", exp);
        oldFormat.addProperty("kitNo", PreferencesManager.getInstance(context).getKIT_NO());

        LoggerUtil.logItem(oldFormat);

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(oldFormat.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<JsonObject> call = apiServicesM2PV2.getCVVDetails(body);
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
                ResponseCvvDetails response_new;
                try {
                    response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key), ResponseCvvDetails.class);
                    Log.e("============ ", String.valueOf(response_new));
                    if (response.body() != null && response_new.getResponse().equalsIgnoreCase("Success")) {
                        cvvNumber = response_new.getResult().getResult().getCvv();
                    } else {
                        showMessage("Something went wrong");
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

    private void getM2pCard_allTransaction() {
        try {
            Call<JsonObject> allTransactionCall = apiServicesM2PV2.gettransactions(Cons.encryptMsg(PreferencesManager.getInstance(context).getLoginID(), easypay_key));
            showLoading();
            allTransactionCall.enqueue(new Callback<JsonObject>() {
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
                    ResponseAllTransaction response_new;
                    try {
                        response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key), ResponseAllTransaction.class);
//
                        Log.e("============ ", String.valueOf(response_new));
                        if (response.body() != null && response_new.getResponse().equalsIgnoreCase("Success")) {
                            if (response_new.getResult().getResult().size() > 0) {
                                list_card = response_new.getResult().getResult();
                                m2PCardAdapter.updateList(list_card);
                                cardRecycler.setVisibility(View.VISIBLE);

                            } else {
                                cardRecycler.setVisibility(View.GONE);
                                showAlert("No Transaction found.", R.color.red, R.drawable.error);
                            }
                        } else {
                            cardRecycler.setVisibility(View.GONE);
                            showAlert("No Transaction found.", R.color.red, R.drawable.error);
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
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.tv_add_fund, R.id.tv_generate_pin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_fund:
                openTransferAmountDialog();
                break;
            case R.id.tv_generate_pin:
                goToActivity(context, CreateCardPIN.class, null);
                break;
        }
    }

    Dialog dialog_amt_remark_m2p;

    private void openTransferAmountDialog() {
        hideKeyboard();
        dialog_amt_remark_m2p = new Dialog(context);
        dialog_amt_remark_m2p.setCanceledOnTouchOutside(true);
        dialog_amt_remark_m2p.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        dialog_amt_remark_m2p.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_amt_remark_m2p.setContentView(R.layout.dialog_amt_remark_m2p);
        dialog_amt_remark_m2p.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog_amt_remark_m2p.show();

        Button buttonConfirm = dialog_amt_remark_m2p.findViewById(R.id.buttonConfirm);
        Button btn_cancel = dialog_amt_remark_m2p.findViewById(R.id.btn_cancel);
        EditText et_amount = dialog_amt_remark_m2p.findViewById(R.id.et_amount);
        EditText et_remark = dialog_amt_remark_m2p.findViewById(R.id.et_remark);
        buttonConfirm.setOnClickListener(v -> {
            if (!(et_amount.getText().toString().trim().equalsIgnoreCase("")) &&
                    Double.parseDouble(et_amount.getText().toString().trim()) > 0) {
                getBalanceAmount(Float.parseFloat(et_amount.getText().toString().trim())
                        , et_remark.getText().toString().trim());
                dialog_amt_remark_m2p.dismiss();
                showMessage("Please wait.");
            } else {
                showMessage("Please enter amount to transfer in your card.");
            }
        });
        btn_cancel.setOnClickListener(v -> {
            dialog_amt_remark_m2p.dismiss();
        });
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
                try {

                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    ResponseBalanceAmount convertedObject = new Gson().fromJson(paramResponse, ResponseBalanceAmount.class);
                    if (response.body() != null && convertedObject.getStatus().equalsIgnoreCase("Success")) {
                        float bagamount = convertedObject.getBalanceAmount();
                        if (bagamount >= reqamount) {
                            addFund(reqamount, remark);
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
        oldFormat.addProperty("toEntityId", PreferencesManager.getInstance(context).getLoginID());
        oldFormat.addProperty("fromEntityId", PreferencesManager.getInstance(context).getLoginID());
        oldFormat.addProperty("transactionOrigin", "MOBILE");
        oldFormat.addProperty("transactionType", BuildConfig.M2P_TRANSFEROTHER);
        oldFormat.addProperty("yapcode", BuildConfig.M2P_YAPCODE);
        oldFormat.addProperty("businessEntityId", BuildConfig.M2P_BUSINESSTYPE);
        oldFormat.addProperty("business", BuildConfig.M2P_BUSINESSTYPE);

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
                LoggerUtil.logItem(response.body());
                ResponseAddFund response_new;
                try {
                    response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key), ResponseAddFund.class);
                    if (response.body() != null && response_new.getResponse().equalsIgnoreCase("Success")) {
                        Bundle b = new Bundle();
                        b.putString("from", "AddFundToSelf");
                        b.putString("response", response.body().toString());
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

            }
        });
    }
}
