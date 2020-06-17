package com.digitalcashbag.utilities.recharges.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.shopping.activities.PaypalWebView;
import com.digitalcashbag.shopping.activities.TransactionStatus;
import com.digitalcashbag.shopping.activities.TransactionStatusAEPS;
import com.digitalcashbag.shopping.activities.WebViewActivity;
import com.digitalcashbag.utilities.recharges.payment.offline_payment.BankList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
import kkm.com.core.model.response.aeps_login.ResponseAepsLogin;
import kkm.com.core.model.response.aeps_login.ResponseBankList;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.paymonk.com.aepsredirection.AepsAuthenication;
import www.paymonk.com.aepsredirection.CommonNameEnums;
import www.paymonk.com.aepsredirection.Login;
import www.paymonk.com.aepsredirection.ResultCode;

import static kkm.com.core.BuildConfig.PAYPAL_PAYMENT_URL;
import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;
import static kkm.com.core.utils.Utils.AssetJSONFile;


public class AddMoney extends BaseActivity {

    private static DecimalFormat format = new DecimalFormat("0.00");
    private final int AEPS_LOGIN_REQUEST = 103;
    private final int AEPS_AUTH_REQUEST = 104;
    Bundle param;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.wallet_amount)
    TextView walletAmount;
    @BindView(R.id.et_amount)
    TextInputEditText etAmount;
    @BindView(R.id.input_layout_amount)
    TextInputLayout inputLayoutAmount;
    @BindView(R.id.radioGroup_payment)
    RadioGroup radioGroupPayment;
    @BindView(R.id.et_paypal)
    EditText etPaypal;
    @BindView(R.id.et_aeps)
    EditText etAeps;
    @BindView(R.id.et_paytm)
    EditText etPaytm;
    @BindView(R.id.et_upi)
    EditText etUpi;
    @BindView(R.id.et_ccavenue)
    EditText etCcavenue;
    @BindView(R.id.radio_easypay)
    RadioButton radioEasypay;
    @BindView(R.id.et_easypay)
    EditText etEasypay;
    private String amount_st = "0", from = "Add Money", upi = "";
    private String aepsAmount = "";
    String aepsDevice = "";
    String bankId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_add_balance_new);
        ButterKnife.bind(this);
        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        title.setText("Add Money");


        radioGroupPayment.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int position = radioGroupPayment.indexOfChild(buttonView.findViewById(buttonView.getCheckedRadioButtonId()));

            disableEditText(etPaypal);
            disableEditText(etAeps);
            disableEditText(etPaytm);
            disableEditText(etUpi);
            disableEditText(etCcavenue);
            disableEditText(etEasypay);

            switch (position) {
                case 0: // PAYPAL

                    break;
                case 1: // AEPS

                    break;
                case 2: // PAYTM

                    break;
                case 3: // UPI
                    enableEditText(etUpi);
                    etUpi.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.edit, 0);
                    break;
                case 4: // CCAVENUE

                    break;
                case 5: // icici

                    break;
            }

        });

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getWalletBalance();
            getUpi();
        } else
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);

        if (param != null) {
            if (param.getString("from").equalsIgnoreCase("AEPS")) {
                amount_st = param.getString("amount");
                proceedToAeps(amount_st);
            }
        }
    }

    private void disableEditText(EditText edt) {
        edt.setFocusable(false);
        edt.setFocusableInTouchMode(false);
        edt.setClickable(false);
        edt.setCursorVisible(false);
        Utils.hideSoftKeyboard(context);
        edt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    private void enableEditText(EditText edt) {
        edt.setFocusable(true);
        edt.setFocusableInTouchMode(true);
        edt.setClickable(true);
        edt.setCursorVisible(true);
        edt.setSelection(edt.getText().toString().length());
        requestFocus(edt);
        edt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    @OnClick({R.id.side_menu, R.id.addmoney_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                Utils.hideSoftKeyboard(context);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.addmoney_btn:
                if (Validation()) {
                    Utils.hideSoftKeyboard(context);
                    int btnId = radioGroupPayment.getCheckedRadioButtonId();
                    switch (btnId) {
                        case R.id.radio_Paypal:
                            proceedToPaypal(amount_st);
                            break;
                        case R.id.radio_aeps:
                            proceedToAeps(amount_st);
                            break;
                        case R.id.radio_Paytm:
                            proceedTopaytm(amount_st);
                            break;
                        case R.id.radio_upi:
                            proceedToupi(amount_st);
                            break;
                        case R.id.radio_ccavenue:
                            proceedToccavenue(amount_st);
                            break;
                        case R.id.radio_offline:
                            Bundle bundle = new Bundle();
                            bundle.putString("amount", etAmount.getText().toString());
                            goToActivity(context, BankList.class, bundle);
                            break;
                        case R.id.radio_easypay:
                            proceedToeasypay(amount_st);
                            break;
                    }
                }
                break;

        }
    }

    private void proceedToeasypay(String amount) {
//        amount = "1";
        LoggerUtil.logItem(amount);
        try {

            Bundle bundle = new Bundle();

            String refNo = String.valueOf(System.currentTimeMillis());
            String submerchentId = String.valueOf(System.currentTimeMillis());

            String link = BuildConfig.EASYPAY_URL1 +
                    encryptMsg(refNo + "|" + submerchentId + "|" + amount + "|" +
                            PreferencesManager.getInstance(context).getMOBILE() + "|" +
                            PreferencesManager.getInstance(context).getNAME() +
                            PreferencesManager.getInstance(context).getLNAME(), easypay_key) +
                    BuildConfig.EASYPAY_URL2 + encryptMsg(refNo, easypay_key) +
                    "&submerchantid=" + encryptMsg(submerchentId, easypay_key)
                    + "&transaction amount=" + encryptMsg(amount, easypay_key) +
                    "&paymode=" + encryptMsg("9", easypay_key);

            Log.e("Encrypted URL - ", link);
//
//            Log.e("Plain URL - ", BuildConfig.EASYPAY_URL1 +
//                    refNo + "|" + submerchentId + "|" + amount + "|" +
//                    PreferencesManager.getInstance(context).getMOBILE() + "|" +
//                    PreferencesManager.getInstance(context).getNAME() +
//                    PreferencesManager.getInstance(context).getLNAME() +
//                    "&optional fields=" +
//                    "test|test@" +
//                    "&returnurl=" +
//                    "https://cashbag.co.in/return" +
//                    "=&Reference No=" + refNo + "&submerchantid=" + submerchentId
//                    + "&transaction amount=" + amount +
//                    "&paymode=9");

            bundle.putString("link", link);
            bundle.putString("from", from);

            goToActivity(context, WebViewActivity.class, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void proceedToPaypal(String amount) {
        LoggerUtil.logItem(amount);
        String url = PAYPAL_PAYMENT_URL + "UPI&currency=INR&price=" + amount + "&quantity=1&sku=sku&subtotal=" + amount +
                "&fk_memid=" + PreferencesManager.getInstance(context).getUSERID() +
                "&transtype=Wallet&fk_orderid=" + "";

        Bundle bundle = new Bundle();
        bundle.putString("link", url);
        bundle.putString("from", from);
        goToActivity(context, PaypalWebView.class, bundle);
    }

    private void proceedToAeps(String amount) {
        LoggerUtil.logItem(amount);
//        Toast.makeText(context, "Under Development", Toast.LENGTH_SHORT).show();

        aepsAmount = amount;
        aepsLogin(generateReferenceNumber());
    }

    private void proceedTopaytm(String amount) {
        LoggerUtil.logItem(amount);
        Toast.makeText(context, "Under Development", Toast.LENGTH_SHORT).show();
    }

    private void proceedToupi(String amount) {
        LoggerUtil.logItem(amount);
        if (!TextUtils.isEmpty(etUpi.getText().toString().trim()) &&
                etUpi.getText().toString().contains("@")) {

            if (!upi.equalsIgnoreCase(etUpi.getText().toString().trim())) {
                saveUpi(etUpi.getText().toString().trim());
            }

            Bundle bundle = new Bundle();
            bundle.putString("amount", etAmount.getText().toString().trim());
            bundle.putString("upi", etUpi.getText().toString().trim());
            bundle.putString("from", "add_money");
            goToActivity(context, TransactionStatus.class, bundle);
        } else {
            Toast.makeText(context, "Please enter valid UPI ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void proceedToccavenue(String amount) {
        LoggerUtil.logItem(amount);
        Toast.makeText(context, "Under Development", Toast.LENGTH_SHORT).show();
    }

    private boolean Validation() {
        try {
            amount_st = etAmount.getText().toString().trim();
            if (amount_st.equalsIgnoreCase("")) {
                showError("Please enter amount", etAmount);
                return false;
            } else if (Integer.parseInt(amount_st) < 10) {
                showError("Minimum transaction amount is 10", etAmount);
                return false;
            } else if (radioGroupPayment.getCheckedRadioButtonId() == -1) {
                Toast.makeText(context, "Please select any payment method to place order.", Toast.LENGTH_SHORT).show();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void showError(String error_st, EditText editText) {
        Dialog error_dialog = new Dialog(this);
        error_dialog.setCanceledOnTouchOutside(false);
        error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        error_dialog.setContentView(R.layout.error_dialoge);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        error_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView error_text = error_dialog.findViewById(R.id.error_text);
        Button ok_btn = error_dialog.findViewById(R.id.ok_btn);
        error_text.setText(error_st);
        error_dialog.show();
        ok_btn.setOnClickListener(view -> {
            error_dialog.dismiss();
            requestFocus(editText);
        });
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void getWalletBalance() {
//        RequestBalanceAmount amount = new RequestBalanceAmount();
//        amount.setMemberId(PreferencesManager.getInstance(context).getUSERID());


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
                        walletAmount.setText(String.format(Locale.ENGLISH, "₹%s", format.format(Double.parseDouble(String.valueOf(convertedObject.getBalanceAmount())))));
                    } else {
                        walletAmount.setText(String.format(Locale.ENGLISH, "₹%s", format.format(Double.parseDouble(String.valueOf(convertedObject.getBalanceAmount())))));

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
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void saveUpi(String upi) {

        JsonObject param = new JsonObject();
        param.addProperty("FK_MemId", PreferencesManager.getInstance(context).getUSERID());
        param.addProperty("UPIAddress", upi);

        Call<JsonObject> saveUpiCall = apiServices.saveUpi(param);
        saveUpiCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
            }
        });

    }

    private void getUpi() {
        showLoading();
        JsonObject param = new JsonObject();
        param.addProperty("FK_MemId", PreferencesManager.getInstance(context).getUSERID());

        Call<JsonObject> getUpiCall = apiServices.getUpi(param);
        getUpiCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                try {
                    if (response.body() != null && response.body().get("response").getAsString().equalsIgnoreCase("Success")) {
                        etUpi.setText(response.body().get("upiAdress").getAsString());
                        upi = response.body().get("upiAdress").getAsString();
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

    private String generateReferenceNumber() {
        Random rand = new Random();
        return "CAS" + rand.nextInt(9000000) + 1000000;
    }

    private List<ResponseBankList> getBankList() {
        String data = null;
        try {
            data = AssetJSONFile("bank_list.json", context);
            LoggerUtil.logItem(data);
            Type type = new TypeToken<List<ResponseBankList>>() {
            }.getType();
            return new Gson().fromJson(data, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    private void showAepsDialog(ResponseAepsLogin param) {

        bankId = "";
        aepsDevice = "";

        Dialog d = new Dialog(context);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.aeps_dialog);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        d.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        EditText aadharNumber = d.findViewById(R.id.editText_aadhar);
        AutoCompleteTextView bank_name = d.findViewById(R.id.autoCompleteTxtBank);
        Button submit = d.findViewById(R.id.btn_submit);
        TextView txtChooseDevice = d.findViewById(R.id.txtChooseDevice);

        txtChooseDevice.setOnClickListener(v -> {
            PopupMenu popup_deliveryOption = new PopupMenu(context, v);
            popup_deliveryOption.getMenuInflater().inflate(R.menu.menu_aeps_device, popup_deliveryOption.getMenu());
            popup_deliveryOption.setOnMenuItemClickListener(item -> {
                txtChooseDevice.setText(item.getTitle());
                switch (item.getTitle().toString()) {
                    case "SECUGEN":
                        aepsDevice = CommonNameEnums.deviceName.SECUGEN.name();
                        break;
                    case "MANTRA":
                        aepsDevice = CommonNameEnums.deviceName.MANTRA.name();
                        break;
                    case "MORPHO":
                        aepsDevice = CommonNameEnums.deviceName.MORPHO.name();
                        break;
                    case "STARTEK":
                        aepsDevice = CommonNameEnums.deviceName.STARTEK.name();
                        break;
                }

                return true;
            });
            popup_deliveryOption.show();
        });

//        aadharNumber.setText(PreferencesManager.getInstance(context).get);

        ArrayList<String> bankName = new ArrayList<>();
        List<ResponseBankList> bankLists = getBankList();

        for (int i = 0; i < bankLists.size(); i++) {
            bankName.add(bankLists.get(i).getBankName());
        }

        bank_name.setOnItemClickListener((parent, view, position, id) -> {

                    try {
                        String bank_Name = (String) parent.getItemAtPosition(position);
                        LoggerUtil.logItem(bank_Name);

                        for (int i = 0; i < bankLists.size(); i++) {
                            if (bankLists.get(i).getBankName().equalsIgnoreCase(bank_Name)) {
                                bankId = String.valueOf(bankLists.get(i).getId());
                                LoggerUtil.logItem(bankId);
                                break;
                            }

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );


        bank_name.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, bankName));
        submit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(aadharNumber.getText().toString()) && aadharNumber.getText().toString().length() < 12) {
                showMessage("Invalid aadhar number.");
            } else if (!Utils.validateAadharNumber(aadharNumber.getText().toString())) {
                showMessage("Invalid aadhar number.");
            } else if (TextUtils.isEmpty(bank_name.getText().toString())) {
                showMessage("Invalid bank name.");
            } else if (TextUtils.isEmpty(aepsDevice)) {
                showMessage("Choose device");
            } else {
                d.dismiss();
                aepsAuth(param, aadharNumber.getText().toString(), aepsDevice);
            }
        });

        d.show();

    }

    private void aepsLogin(String referenceNumber) {

        String timeStamp = String.valueOf(System.currentTimeMillis());// TIMESTAMP
        String payload = BuildConfig.AEPS_MID + "|" + BuildConfig.AEPS_AGENT_CODE + "|" + timeStamp; // CREATE PAYLOAD
        String secret = BuildConfig.AEPS_MID_SECRET_KEY; //SECRET KEY
        String checkSum = Cons.calculateHmac(payload, secret); // CHECKSUM

        LoggerUtil.logItem(referenceNumber);
        LoggerUtil.logItem(checkSum);
        LoggerUtil.logItem(secret);
        LoggerUtil.logItem(timeStamp);
        LoggerUtil.logItem(payload);

        Intent intent = new Intent(AddMoney.this, Login.class);
        intent.putExtra("serverName", CommonNameEnums.ServerName.PRODUCTION.name());//server
        intent.putExtra("agentCode", BuildConfig.AEPS_AGENT_CODE);//your agent code
        intent.putExtra("refrenceNumber", referenceNumber);//your refrence number unique code
        intent.putExtra("timeStamp", timeStamp);//timestamp
        intent.putExtra("checkSum", checkSum);//checksum
        intent.putExtra("merchantID", BuildConfig.AEPS_MID);//merchant ID
        startActivityForResult(intent, AEPS_LOGIN_REQUEST);

    }

    private void aepsAuth(ResponseAepsLogin param, String aadharNumber, String deviceName) {

        Intent intent = new Intent(context, AepsAuthenication.class);
        intent.putExtra("token", param.getResult().getPayload().getToken());//your token
        intent.putExtra("ownerId", String.valueOf(param.getResult().getPayload().getOwnerInfo().getIdentificationId()));//your owner id
        intent.putExtra("ownerType", CommonNameEnums.ownerType.RETAILER.name());
        intent.putExtra("environment", CommonNameEnums.environmentType.P.name());
        //set environment
        intent.putExtra("mobileNumber", PreferencesManager.getInstance(context).getMOBILE());
        //TODO change amount value
        intent.putExtra("amount", aepsAmount);
        intent.putExtra("deviceType", deviceName);//setdevicetype
        intent.putExtra("aadharNumber", aadharNumber);//your aadhar number
        intent.putExtra("bankID", bankId);//your bank id
        intent.putExtra("aepsTxnType", CommonNameEnums.aepsTxnType.CASHWITHDRAWAL.name());
        startActivityForResult(intent, AEPS_AUTH_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCode.RESULT_ERROR) {
            switch (requestCode) {
                case AEPS_LOGIN_REQUEST: {// ERROR
                    String errorResponse = data.getStringExtra("error");
                    LoggerUtil.logItem(errorResponse);
                    int statusCode = data.getIntExtra("statusCode", 0);
                    break;
                }
                case AEPS_AUTH_REQUEST: { // ERROR
                    String errorResponse = data.getStringExtra("error");
                    LoggerUtil.logItem(errorResponse);
                    int statusCode = data.getIntExtra("statusCode", 0);
                    LoggerUtil.logItem(statusCode);
                    try {
                        JSONObject parseSuccessJson = new JSONObject(errorResponse);
                        Type type = new TypeToken<ResponseAepsLogin>() {
                        }.getType();
                        ResponseAepsLogin loginResponse = new Gson().fromJson(parseSuccessJson.toString(), type);
                        Log.e("LOGIN RESPONSE", parseSuccessJson.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        } else if (resultCode == ResultCode.RESULT_SUCCESS) {
            switch (requestCode) {
                case AEPS_LOGIN_REQUEST: {// SUCCESS
                    String successResponse = data.getStringExtra("success");
                    try {
                        JSONObject parseSuccessJson = new JSONObject(successResponse);
                        Type type = new TypeToken<ResponseAepsLogin>() {
                        }.getType();
                        ResponseAepsLogin loginResponse = new Gson().fromJson(parseSuccessJson.toString(), type);
                        Log.e("LOGIN RESPONSE", parseSuccessJson.toString());
                        showAepsDialog(loginResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showMessage("Something went wrong.");
                    }
                    break;
                }
                case AEPS_AUTH_REQUEST: { // SUCCESS
                    String successResponse = data.getStringExtra("success");

                    Bundle bundle = new Bundle();
                    bundle.putString("response", successResponse);
                    bundle.putString("from", "add_money");
                    bundle.putString("amount", aepsAmount);
                    goToActivity(AddMoney.this, TransactionStatusAEPS.class, bundle);
//                    try {
//                        JSONObject parseSuccessJson = new JSONObject(successResponse);
//                        Log.e("AUTH ", parseSuccessJson.toString());
//
////                        {
////                            "result": {
////                            "responseCode": 401,
////                                    "responseMessage": "There is some internal issues at our end.Please try again later.",
////                                    "status": "FAILURE"
////                        }
////                        }
//
//
////                        {
////                            "result": {
////                            "payload": {
////                                "metadata": "{\"refno\":\"CAS8122711000000\"}",
////                                        "aeps": {
////                                    "orderId": "3325448226845304",
////                                            "amount": 0,
////                                            "orderStatus": "FAILURE",
////                                            "paymentStatus": "FAILURE",
////                                            "processingCode": "310000",
////                                            "accountBalance": "0.00",
////                                            "bankResponseMsg": "There is some internal issues at our end.Please try again later.",
////                                            "bankResponseMessage": "There is some internal issues at our end.Please try again later.",
////                                            "dateTime": 1556258572534,
////                                            "statusCode": "401",
////                                            "commissionAmt": 0,
////                                            "gstAmt": 0,
////                                            "tdsAmt": 0,
////                                            "isWalletFailed": false,
////                                            "walletFailed": false
////                                }
////                            }
////                        }
////                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                    break;
                }
            }
        }

    }

    public String encryptMsg(String message, SecretKey secret)
            throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] cipherText = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeToString(cipherText, Base64.NO_WRAP);
    }

//    public String decryptMsg(String cipherText, SecretKey secret)
//            throws Exception {
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, secret);
//        byte[] decode = Base64.decode(cipherText, Base64.NO_WRAP);
//        String decryptString = new String(cipher.doFinal(decode), StandardCharsets.UTF_8);
//        return decryptString;
//    }

    public void showInfoDialog() {

    }

}

