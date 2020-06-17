package com.digitalcashbag.utilities.billpayment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.utilities.billpayment.adapter.RecentBillPayment;
import com.digitalcashbag.utilities.recharges.activities.AddMoney;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
import kkm.com.core.model.response.bill.ResponseBillPayment;
import kkm.com.core.model.response.bill.ResponseBroadBandVerification;
import kkm.com.core.model.response.bill.ResponseGasVerification;
import kkm.com.core.model.response.bill.ResponseInsuranceVerify;
import kkm.com.core.model.response.bill.ResponseProviderList;
import kkm.com.core.model.response.bill.ResponseWaterVerification;
import kkm.com.core.model.response.utility.ResponseRecentRecharges;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class OtherBillPayment extends BaseActivity {

    private static DecimalFormat formatWallet = new DecimalFormat("0.00");
    Bundle param;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_board)
    TextInputEditText etBoard;
    @BindView(R.id.input_layout_board)
    TextInputLayout inputLayoutBoard;
    @BindView(R.id.verify)
    TextView verify;
    @BindView(R.id.et_service_number)
    TextInputEditText etServiceNumber;
    @BindView(R.id.input_layout_service_num)
    TextInputLayout inputLayoutServiceNum;
    @BindView(R.id.make_payment_btn_ele_board)
    Button makePaymentBtnEleBoard;
    @BindView(R.id.instr_txt)
    TextView instrTxt;
    @BindView(R.id.electricityboard_lo)
    ConstraintLayout electricityboardLo;
    @BindView(R.id.rv_recent)
    RecyclerView rv_recent;
    @BindView(R.id.service_number_lo)
    RelativeLayout serviceNumberLo;
    @BindView(R.id.et_account)
    TextInputEditText etAccount;
    @BindView(R.id.input_layout_account)
    TextInputLayout inputLayoutAccount;
    @BindView(R.id.et_authenticator)
    TextInputEditText etAuthenticator;
    @BindView(R.id.input_layout_authenticator)
    TextInputLayout inputLayoutAuthenticator;
    @BindView(R.id.et_amount)
    TextInputEditText etAmount;
    @BindView(R.id.input_layout_amount)
    TextInputLayout inputLayoutAmount;
    String totalAmount = "";
    InputFilter filter = (source, start, end, dest, dstart, dend) -> {
        for (int i = start; i < end; ++i) {
            if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                return "";
            }
        }

        return null;
    };

    String keys = BuildConfig.CASHBAG_KEY;
    SecretKey easypay_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_bill_payment);
        ButterKnife.bind(this);
        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);

        easypay_key = new SecretKeySpec(keys.getBytes(), "AES");

        title.setText(param.getString("bill"));

        if (title.getText().toString().equalsIgnoreCase(Cons.INSURANCE_BILL_PAYMENT)) {
            etBoard.setHint("Select Company");
            inputLayoutBoard.setHint("Select Company");
        } else if (title.getText().toString().equalsIgnoreCase(Cons.BROADBAND_BILL_PAYMENT)) {
            etBoard.setHint("Select Provider");
            inputLayoutBoard.setHint("Select Provider");
        }

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getAllProvider();
            getRecentList(title.getText().toString());
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

//        etServiceNumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                makePaymentBtnEleBoard.setVisibility(View.GONE);
//                etAmount.setText("");
//                inputLayoutAmount.setVisibility(View.GONE);
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            String board = data.getStringExtra("data");
            LoggerUtil.logItem(board);
            etBoard.setText(board);
            inputLayoutAccount.setVisibility(View.GONE);
            inputLayoutAuthenticator.setVisibility(View.GONE);
            makePaymentBtnEleBoard.setVisibility(View.GONE);
            inputLayoutAccount.setHint("Account");
            etAccount.setText("");
            etAuthenticator.setText("");

            etAuthenticator.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(10)});
            etAccount.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(10)});
            etAmount.setText("");
            inputLayoutAmount.setVisibility(View.GONE);
            serviceNumberLo.setVisibility(View.VISIBLE);
            etServiceNumber.setText("");
            switch (board.trim()) {
                case "Mahanagar Gas Limited":
                    inputLayoutAccount.setVisibility(View.VISIBLE);
                    inputLayoutAccount.setHint("Enter Bill Group Number");
                    etAccount.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(8)});
                    break;
                case "Ludhiana  Muncipal Corpoartion water Tax":
                    inputLayoutAccount.setVisibility(View.VISIBLE);
                    inputLayoutAccount.setHint("Mobile Number (10 digits)");
                    etAccount.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(10)});
                    inputLayoutAuthenticator.setHint("Email ID  (1-50 alphanumeric)");
                    etAuthenticator.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(50)});
                    inputLayoutAuthenticator.setVisibility(View.VISIBLE);
                    break;
            }

        }
    }

    @OnClick({R.id.side_menu, R.id.et_board, R.id.verify, R.id.make_payment_btn_ele_board})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.et_board:
                if (Cons.allProviderlistItemList.size() > 0) {
                    Intent intent = new Intent(context, BillPaymentBoard.class);
                    intent.putExtra("bill", title.getText().toString());
                    startActivityForResult(intent, 100);
                }
                break;
            case R.id.verify:
                if (etServiceNumber.getText().toString().length() > 0 && etBoard.getText().toString().length() > 0) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        switch (title.getText().toString()) {
                            case Cons.GAS_BILL_PAYMENT:
                                verifyGasNumber();
                                break;
                            case Cons.WATER_BILL_PAYMENT:
                                verifyWaterNumber();
                                break;
                            case Cons.INSURANCE_BILL_PAYMENT:
                                verifyInsuranceNumber();
                                break;
                            case Cons.BROADBAND_BILL_PAYMENT:
                                verifyBroadBandNumber();
                                break;
                        }
                    }
                } else
                    Toast.makeText(this, "Enter valid Service Number", Toast.LENGTH_SHORT).show();
                break;
            case R.id.make_payment_btn_ele_board:
                if (ValidationEleBoard()) {
                    hideKeyboard();
                    getWalletBalance();
                }
                break;
        }
    }

    private void getAllProvider() {
        showLoading();
        Call<JsonObject> providerListCall = apiServices_utilityV2.getAllProvider(new JsonObject());

        providerListCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                Cons.allProviderlistItemList = new ArrayList<>();
                try {
                    LoggerUtil.logItem(response.body());

                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    ResponseProviderList convertedObject = new Gson().fromJson(paramResponse, ResponseProviderList.class);
                    LoggerUtil.logItem(convertedObject);

                    if (convertedObject.getResponse().equalsIgnoreCase("Success")) {
                        Cons.allProviderlistItemList = convertedObject.getAllProviderlist();
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

    private JsonObject bodyParam(JsonObject param) {

        JsonObject body = new JsonObject();

        try {
            body.addProperty("body", Cons.encryptMsg(param.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    private void getBillPayment() {
        totalAmount = formatWallet.format(Double.parseDouble(totalAmount));
        showLoading();
//        RequestBillPayment billPayment = new RequestBillPayment();
//        billPayment.setFK_MemID(PreferencesManager.getInstance(context).getUSERID());
//        billPayment.setACCOUNT(etAccount.getText().toString());
//        billPayment.setAMOUNT(totalAmount);
//        billPayment.setAMOUNTALL(totalAmount);
//        billPayment.setAuthenticator(etAuthenticator.getText().toString());
//        billPayment.setNUMBER(etServiceNumber.getText().toString());
//        billPayment.setProvider(etBoard.getText().toString());

        JsonObject param = new JsonObject();
        param.addProperty("Authenticator", etAuthenticator.getText().toString().trim());
        param.addProperty("AMOUNT", totalAmount);
        param.addProperty("AMOUNT_ALL", totalAmount);
        param.addProperty("ACCOUNT", etAccount.getText().toString().trim());
        param.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
        param.addProperty("NUMBER", etServiceNumber.getText().toString().trim());
        param.addProperty("Provider", etBoard.getText().toString().trim());

        Call<JsonObject> arrayCall = null;

        switch (title.getText().toString()) {
            case Cons.GAS_BILL_PAYMENT:
//                billPayment.setType(Cons.GAS_BILL_PAYMENT);
                param.addProperty("Type", Cons.GAS_BILL_PAYMENT);
                arrayCall = apiServices_utilityV2.gasbillPayment(bodyParam(param));
                break;
            case Cons.WATER_BILL_PAYMENT:
//                billPayment.setType(Cons.WATER_BILL_PAYMENT);
                param.addProperty("Type", Cons.WATER_BILL_PAYMENT);
                arrayCall = apiServices_utilityV2.waterBillPayment(bodyParam(param));
                break;
            case Cons.INSURANCE_BILL_PAYMENT:
//                billPayment.setType(Cons.INSURANCE_BILL_PAYMENT);
                param.addProperty("Type", Cons.INSURANCE_BILL_PAYMENT);
                arrayCall = apiServices_utilityV2.insurancebillPayment(bodyParam(param));
                break;
            case Cons.BROADBAND_BILL_PAYMENT:
//                billPayment.setType(Cons.BROADBAND_BILL_PAYMENT);
                param.addProperty("Type", Cons.BROADBAND_BILL_PAYMENT);
                arrayCall = apiServices_utilityV2.broadBandBillPayment(bodyParam(param));
                break;
        }

        LoggerUtil.logItem(param);


        arrayCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {

                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    LoggerUtil.logItem(paramResponse);
                    List<ResponseBillPayment> list = Utils.getList(paramResponse, ResponseBillPayment.class);
                    LoggerUtil.logItem(list);

                    if (response.body() != null && list.size() > 0) {
                        ResponseBillPayment billPayment1 = list.get(0);
                        if (billPayment1.getError().equalsIgnoreCase("0") &&
                                billPayment1.getResult().equalsIgnoreCase("0")) {
                            Toast.makeText(context, getResources().getString(R.string.payment_done_to), Toast.LENGTH_SHORT).show();
                            getRecentList(title.getText().toString());
                        } else {
                            showMessage(list.get(0).getErrmsg());
                        }

                    } else {
                        showMessage("Something went wrong !");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                onBackPressed();

            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                LoggerUtil.logItem(t.getMessage());
                hideLoading();
            }
        });

    }


    private void verifyWaterNumber() {
        showLoading();
//        RequestWaterVerification verification = new RequestWaterVerification();
//        verification.setAMOUNT("10.00");
//        verification.setAMOUNTALL("10.00");
//        verification.setFKMemId(PreferencesManager.getInstance(context).getUSERID());
//        verification.setProvider(etBoard.getText().toString());
//        verification.setNUMBER(etServiceNumber.getText().toString());
//        verification.setType(Cons.WATER_BILL_PAYMENT);


        JsonObject param = new JsonObject();
        param.addProperty("AMOUNT", "10.00");
        param.addProperty("AMOUNT_ALL", "10.00");
        param.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
        param.addProperty("NUMBER", etServiceNumber.getText().toString().trim());
        param.addProperty("Provider", etBoard.getText().toString().trim());
        param.addProperty("Type", Cons.WATER_BILL_PAYMENT);
        LoggerUtil.logItem(param);

        Call<JsonObject> call = apiServices_utilityV2.getWaterBillVerification(bodyParam(param));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
//                    ResponseWaterVerification

                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    LoggerUtil.logItem(paramResponse);
                    List<ResponseWaterVerification> list = Utils.getList(paramResponse, ResponseWaterVerification.class);

                    if (response.body() != null && list.get(0).getPrice().length() != 0) {
                        etAmount.setText(formatWallet.format(Double.parseDouble(list.get(0).getPrice())));
                        inputLayoutAmount.setVisibility(View.VISIBLE);
                        makePaymentBtnEleBoard.setVisibility(View.VISIBLE);
                    } else {
                        showAlert(list.get(0).getErrmsg(), R.color.red, R.drawable.error);
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

    private void verifyGasNumber() {
        showLoading();
//        RequestGasVerification verification = new RequestGasVerification();
//        verification.setAMOUNT("10.00");
//        verification.setAMOUNTALL("10.00");
////      billgroupnumber
//        verification.setAccount(etAccount.getText().toString().trim());
//        verification.setFKMemId(PreferencesManager.getInstance(context).getUSERID());
//        verification.setNUMBER(etServiceNumber.getText().toString());
//        verification.setProvider(etBoard.getText().toString());
//        verification.setType(Cons.GAS_BILL_PAYMENT);

        JsonObject param = new JsonObject();
        param.addProperty("AMOUNT", "10.00");
        param.addProperty("AMOUNT_ALL", "10.00");
        param.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
        param.addProperty("NUMBER", etServiceNumber.getText().toString().trim());
        param.addProperty("Provider", etBoard.getText().toString().trim());
        param.addProperty("Type", Cons.GAS_BILL_PAYMENT);
        LoggerUtil.logItem(param);

        Call<JsonObject> call = apiServices_utilityV2.getGasPaymentVerification(bodyParam(param));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
//
                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    LoggerUtil.logItem(paramResponse);
                    List<ResponseGasVerification> list = Utils.getList(paramResponse, ResponseGasVerification.class);

                    if (response.body() != null && list.get(0).getPrice().length() != 0) {
                        etAmount.setText(formatWallet.format(Double.parseDouble(list.get(0).getPrice())));
                        inputLayoutAmount.setVisibility(View.VISIBLE);
                        makePaymentBtnEleBoard.setVisibility(View.VISIBLE);
                    } else {
                        showAlert(list.get(0).getErrmsg(), R.color.red, R.drawable.error);
                    }
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void verifyBroadBandNumber() {
        showLoading();
//        RequestBroadBandVerification verification = new RequestBroadBandVerification();
//        verification.setAMOUNT("1.00");
//        verification.setAMOUNTALL("1.00");
//        verification.setFKMemId(PreferencesManager.getInstance(context).getUSERID());
//        verification.setProvider(etBoard.getText().toString());
//        verification.setNUMBER(etServiceNumber.getText().toString());
//        verification.setType("Broad Band Payment");

        JsonObject param = new JsonObject();
        param.addProperty("AMOUNT", "10.00");
        param.addProperty("AMOUNT_ALL", "10.00");
        param.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
        param.addProperty("NUMBER", etServiceNumber.getText().toString().trim());
        param.addProperty("Provider", etBoard.getText().toString().trim());
        param.addProperty("Type", Cons.BROADBAND_BILL_PAYMENT);
        LoggerUtil.logItem(param);

        Call<JsonObject> call = apiServices_utilityV2.getBroadBandVerify(bodyParam(param));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {

                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    LoggerUtil.logItem(paramResponse);
                    List<ResponseBroadBandVerification> list = Utils.getList(paramResponse, ResponseBroadBandVerification.class);

                    if (response.body() != null && list.get(0).getPrice().length() != 0) {
                        etAmount.setText(formatWallet.format(Double.parseDouble(list.get(0).getPrice())));
                        inputLayoutAmount.setVisibility(View.VISIBLE);
                        makePaymentBtnEleBoard.setVisibility(View.VISIBLE);
                    } else {
                        showAlert(list.get(0).getErrmsg(), R.color.red, R.drawable.error);
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

    private void verifyInsuranceNumber() {
        showLoading();
//        RequestInsuranceVerify verify = new RequestInsuranceVerify();
//        verify.setAMOUNT("1.00");
//        verify.setAMOUNTALL("1.00");
//        verify.setFKMemId(PreferencesManager.getInstance(context).getUSERID());
//        verify.setNUMBER(etServiceNumber.getText().toString());
//        verify.setProvider(etBoard.getText().toString());
//        verify.setType(Cons.INSURANCE_BILL_PAYMENT);


        JsonObject param = new JsonObject();
        param.addProperty("AMOUNT", "10.00");
        param.addProperty("AMOUNT_ALL", "10.00");
        param.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
        param.addProperty("NUMBER", etServiceNumber.getText().toString().trim());
        param.addProperty("Provider", etBoard.getText().toString().trim());
        param.addProperty("Type", Cons.INSURANCE_BILL_PAYMENT);

        Call<JsonObject> call = apiServices_utilityV2.getInsuranceVerify(bodyParam(param));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
//                    ResponseInsuranceVerify
                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    LoggerUtil.logItem(paramResponse);
                    List<ResponseInsuranceVerify> list = Utils.getList(paramResponse, ResponseInsuranceVerify.class);

                    if (response.body() != null && list.get(0).getPrice().length() != 0) {
                        etAmount.setText(formatWallet.format(Double.parseDouble(list.get(0).getPrice())));
                        inputLayoutAmount.setVisibility(View.VISIBLE);
                        makePaymentBtnEleBoard.setVisibility(View.VISIBLE);
                    } else {
                        showAlert(list.get(0).getErrmsg(), R.color.red, R.drawable.error);
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

    private boolean ValidationEleBoard() {
        totalAmount = etAmount.getText().toString();
        if (etBoard.getText().toString().trim().length() == 0) {
            etBoard.setError(getString(R.string.select_board_err));
            return false;
        } else if (etServiceNumber.getText().toString().trim().length() == 0) {
            etServiceNumber.setError(getString(R.string.service_number_err));
            return false;
        } else if (inputLayoutAccount.isShown() && etAccount.getText().toString().trim().length() == 0) {
            etAccount.setError(getString(R.string.service_number_err));
            return false;
        } else if (inputLayoutAuthenticator.isShown() && etAuthenticator.getText().toString().trim().length() == 0) {
            etAuthenticator.setError(getString(R.string.service_number_err));
            return false;
        } else if (etAmount.getText().toString().trim().length() == 0) {
            etAmount.setError(getString(R.string.service_number_err));
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void getWalletBalance() {
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
                            if (convertedObject.getBalanceAmount() >= Float.parseFloat(totalAmount)) {
                                getBillPayment();
                            } else
                                createAddBalanceDialog(context, "Insufficient bag balance", "You have insufficient balance in your bag, add money before making transactions.");
                        }
                    } catch (Exception e) {
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


    public void createAddBalanceDialog(Context context, String title, String msg) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setNegativeButton("Add Balance", (dialog, id) -> {
            dialog.cancel();
            Bundle b = new Bundle();
            b.putString("total", totalAmount);
            b.putString("from", "billpayment");

            goToActivity(OtherBillPayment.this, AddMoney.class, b);
        });

        android.support.v7.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void getRecentList(String action_type) {
        try {
            if (!PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                JsonObject mainjson = new JsonObject();
                mainjson.addProperty("Action", "BBPS");
                mainjson.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
                mainjson.addProperty("Type", action_type);
                LoggerUtil.logItem(mainjson);

                JsonObject body = new JsonObject();
                body.addProperty("body", Cons.encryptMsg(mainjson.toString(), easypay_key));
                Log.e("REQUEST", body.toString());


                Call<JsonObject> walletBalanceCall = apiServices_utilityV2.getRecentRecharges(body);
                walletBalanceCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        hideLoading();
                        LoggerUtil.logItem(response.body());

                        try {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                            ResponseRecentRecharges convertedObject = new Gson().fromJson(paramResponse, ResponseRecentRecharges.class);
                            LoggerUtil.logItem(convertedObject);

                            if (convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                LinearLayoutManager manager = new LinearLayoutManager(context);
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                rv_recent.setLayoutManager(manager);
                                rv_recent.setHasFixedSize(true);
                                RecentBillPayment adapter = new RecentBillPayment(context, convertedObject.getRecentActivity(), OtherBillPayment.this);
                                rv_recent.setAdapter(adapter);
                            } else {
//                            showAlert("No records found.", R.color.red, R.drawable.alerter_ic_notifications);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        hideLoading();
                    }
                });
            } else {
                hideLoading();
            }
        } catch (Exception e) {
            e.printStackTrace();
            hideLoading();
        }
    }


    @Override
    public void checkAvailability(String mobile_no, String operator, String amt, String others) {
        etAmount.setText(amt);
    }

}
