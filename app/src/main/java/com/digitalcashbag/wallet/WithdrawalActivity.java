package com.digitalcashbag.wallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.wallet.adapter.WithdrawalAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
import kkm.com.core.model.response.wallet.ResponseBankDetails;
import kkm.com.core.model.response.wallet.ResponseWithdrawalDetails;
import kkm.com.core.model.response.wallet.ResponseWithdrawalRequest;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithdrawalActivity extends BaseActivity implements MvpView {

    private static DecimalFormat formatWallet = new DecimalFormat("0.00");
    @BindView(R.id.amount_to_withdraw)
    EditText amountToWithdraw;
    @BindView(R.id.withdraw_btn)
    Button withdrawBtn;
    @BindView(R.id.withdrawal_history_recycler)
    RecyclerView withdrawalHistoryRecycler;
    @BindView(R.id.wallet_balance)
    TextView walletBalance;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    WithdrawalAdapter withdrawalAdapter;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_acc_number)
    TextView tvAccoNumber;
    @BindView(R.id.tv_ifsc)
    TextView tvIfsc;
    @BindView(R.id.tv_acc_name)
    TextView tv_acc_name;
    @BindView(R.id.no_bag_balance)
    TextView no_bag_balance;
    @BindView(R.id.textView41)
    TextView textView41;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdrawal_activity);
        ButterKnife.bind(this);

        title.setText("Withdrawal to bank");
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        withdrawalHistoryRecycler.setLayoutManager(mLayoutManager);
        withdrawalHistoryRecycler.setHasFixedSize(true);

//        walletBalance.setText(String.format(formatWallet.format(PreferencesManager.getInstance(context).getWALLET_BALANCE())));

        if (Integer.parseInt(walletBalance.getText().toString()) >= 500) {
            withdrawBtn.setVisibility(View.VISIBLE);
            amountToWithdraw.setVisibility(View.VISIBLE);
            textView41.setVisibility(View.VISIBLE);
            no_bag_balance.setVisibility(View.INVISIBLE);
        } else {
            withdrawBtn.setVisibility(View.GONE);
            textView41.setVisibility(View.GONE);
            amountToWithdraw.setVisibility(View.GONE);
            no_bag_balance.setVisibility(View.VISIBLE);
        }

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            loadWithdrawalRecords();
            getBankDetails();
        } else {
            txtNoData.setVisibility(View.VISIBLE);
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    @OnClick({R.id.side_menu, R.id.withdraw_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.withdraw_btn:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        if (Integer.parseInt(walletBalance.getText().toString()) >= 500) {
                            if (Float.parseFloat(amountToWithdraw.getText().toString()) <= Float.parseFloat(walletBalance.getText().toString()))
                                makeWithdrawRequest();
                            else
                                showAlert("Bag Balance should be greater than " + amountToWithdraw.getText().toString(), R.color.red, R.drawable.error);
                        } else
                            showAlert("Bag Balance should be greater than 500", R.color.red, R.drawable.error);
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                }
                break;
            case R.id.side_menu:
                hideKeyboard();
                finish();
                break;
        }
    }

    private boolean Validation() {
        try {
            if (amountToWithdraw.getText().toString().trim().length() == 0) {
                amountToWithdraw.setError("Withdrawal amount can't be empty.");
                amountToWithdraw.requestFocus();
//                showAlert("Withdrawal amount can't be empty.", R.color.red, R.drawable.error);
                return false;
            }
        } catch (Error e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private void makeWithdrawRequest() {
        showLoading();
        JsonObject param = new JsonObject();
        param.addProperty("LoginID", PreferencesManager.getInstance(context).getLoginID());
        param.addProperty("RequestedAmount", amountToWithdraw.getText().toString().trim());

        Call<ResponseWithdrawalRequest> responseWithdrawalRequestCall = apiServices.sendWithdrawalRequest(param);
        LoggerUtil.logItem(param);
        responseWithdrawalRequestCall.enqueue(new Callback<ResponseWithdrawalRequest>() {
            @Override
            public void onResponse(@NotNull Call<ResponseWithdrawalRequest> call, @NotNull Response<ResponseWithdrawalRequest> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    showAlert(response.body().getResponse(), R.color.green, R.drawable.alerter_ic_notifications);
                    loadWithdrawalRecords();
                    getBalanceAmount();
                } else {
                    showAlert(response.body().getResponse(), R.color.red, R.drawable.error);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseWithdrawalRequest> call, @NotNull Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void getPayoutWithdrawalId(String requestId) {
        cancelPendingRequest(requestId);
    }

    private void loadWithdrawalRecords() {
        showLoading();
        JsonObject param = new JsonObject();
        param.addProperty("LoginID", PreferencesManager.getInstance(context).getLoginID());
        Call<ResponseWithdrawalDetails> responseWithdrawalDetailsCall = apiServices.getWithdrawalList(param);
        LoggerUtil.logItem(param);
        responseWithdrawalDetailsCall.enqueue(new Callback<ResponseWithdrawalDetails>() {
            @Override
            public void onResponse(@NotNull Call<ResponseWithdrawalDetails> call, @NotNull Response<ResponseWithdrawalDetails> response) {

                hideLoading();
                try {
                    LoggerUtil.logItem(response.body());
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        if (response.body().getGetPayoutRequestDetails().size() > 0) {
                            withdrawalAdapter = new WithdrawalAdapter(context, response.body().getGetPayoutRequestDetails(), WithdrawalActivity.this);
                            withdrawalHistoryRecycler.setAdapter(withdrawalAdapter);
                            withdrawalHistoryRecycler.setVisibility(View.VISIBLE);
                            txtNoData.setVisibility(View.GONE);
                        } else {
                            txtNoData.setVisibility(View.VISIBLE);
                            withdrawalHistoryRecycler.setVisibility(View.GONE);
                            showAlert("No Transaction found.", R.color.red, R.drawable.error);
                        }
                    } else {
                        txtNoData.setVisibility(View.VISIBLE);
                        withdrawalHistoryRecycler.setVisibility(View.GONE);
                        showAlert(response.body().getResponse(), R.color.red, R.drawable.error);
                    }
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseWithdrawalDetails> call, @NotNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void cancelPendingRequest(String reqId) {

        JsonObject object = new JsonObject();
        object.addProperty("CreatedBy", PreferencesManager.getInstance(context).getUSERID());
        object.addProperty("RequestNo", reqId);
        LoggerUtil.logItem(object);

        Call<JsonObject> call = apiServices.getPayoutCancelled(object);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                LoggerUtil.logItem(response);
                try {
                    if (response.body().get("response").getAsString().equalsIgnoreCase("Success")) {
                        showMessage("Request Cancelled.");
                        loadWithdrawalRecords();
                        getBalanceAmount();
                    } else {
                        showMessage(response.body().get("response").getAsString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {

            }
        });

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
                    if (convertedObject.getStatus().equalsIgnoreCase("Success")) {
                        String amount = formatWallet.format(Double.parseDouble(String.valueOf(convertedObject.getBalanceAmount())));
                        walletBalance.setText(amount);
                    }
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
            }
        });
    }

    private void marquieeView(TextView tv, String data) {
        tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        if (data.toLowerCase().contains("null")) {
            tv.setText(" - - ");
        } else
            tv.setText(data);
        tv.setSelected(true);
        tv.setSingleLine(true);
    }

    private void getBankDetails() {
        JsonObject object = new JsonObject();
        object.addProperty("FK_MemId", PreferencesManager.getInstance(context).getUSERID());

        Call<ResponseBankDetails> call = apiServices.getBankDetails(object);
        call.enqueue(new Callback<ResponseBankDetails>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBankDetails> call, @NotNull Response<ResponseBankDetails> response) {
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    tvAccoNumber.setText(String.format("A/C No.: %s", response.body().getLstOutputGetBankDetailsByLoginId().get(0).getMemberAccNo()));
                    tvBankName.setText(response.body().getLstOutputGetBankDetailsByLoginId().get(0).getMemberBankName());
                    tvIfsc.setText(String.format("IFSC: %s", response.body().getLstOutputGetBankDetailsByLoginId().get(0).getIfscCode()));
                    tv_acc_name.setText(response.body().getLstOutputGetBankDetailsByLoginId().get(0).getBankHolderName());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBankDetails> call, @NotNull Throwable t) {

            }
        });
    }
}
