package com.digitalcashbag.common_activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.shopping.activities.WebViewActivity;
import com.digitalcashbag.R;
import com.digitalcashbag.SMSReceiver;
import com.digitalcashbag.shopping.activities.MainContainer;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.AppConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.ResponseLogin;
import kkm.com.core.model.response.ResponseReferalName;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class MobileLogin extends BaseActivity implements
        SMSReceiver.OTPReceiveListener {

    @BindView(R.id.textSkip)
    TextView textSkip;
    @BindView(R.id.ed_mobile_no)
    EditText edMobileNo;
    @BindView(R.id.txtTerm_condition)
    TextView txtTermCondition;

    String term = "By proceeding you are agree to Cashbag " + "<br/><font color=\"#277819\"><b>"
            + "Privacy policy" + "</b></font>" + " and "
            + "<font color=\"#277819\"><b>" + "Terms & Conditions" + "</b></font>";
    @BindView(R.id.view)
    View view;
    @BindView(R.id.ed_Password)
    EditText edPassword;
    String from = "";
    SharedPreferences pref;
    int DELAY_TIME = 2000;
    //    AppSignatureHashHelper appSignatureHashHelper;
    TextView tv_resend_otp;
    EditText editTextConfirmOtp;
    String pin = "";
    private String mobileNo_st = "", password_st = "";

    //    private FirebaseAuth auth_user = FirebaseAuth.getInstance();
    private SMSReceiver smsReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_login);
        ButterKnife.bind(this);
//        appSignatureHashHelper = new AppSignatureHashHelper(this);
        pref = getApplicationContext().getSharedPreferences(AppConfig.SHARED_PREF, 0);

        txtTermCondition.setText(Html.fromHtml(term));

        if (getIntent().getBundleExtra(PAYLOAD_BUNDLE) != null) {
            from = getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("from");
        }

        edMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0 && s.length() == 10) {
                    hideKeyboard();
                    getMobileValid();
                } else {
                    edPassword.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick({R.id.textSkip, R.id.go_button, R.id.txtTerm_condition})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textSkip:
                PreferencesManager.getInstance(context).setloginSkip(false);
                if (PreferencesManager.getInstance(context).getfirst_visit()) {
                    goToActivityWithFinish(MobileLogin.this, Permission.class, null);
                } else {
                    goToActivityWithFinish(MobileLogin.this, MainContainer.class, null);
                }
                break;

            case R.id.go_button:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getLogin();
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                }
                break;

            case R.id.txtTerm_condition:
                Bundle bundleN = new Bundle();
                bundleN.putString("link", "http://iiashopping.com/KYCDocument/finalT&C.pdf");
                goToActivity(context, WebViewActivity.class, bundleN);
                break;
        }
    }

    void getMobileValid() {
        PreferencesManager.getInstance(context).setloginSkip(false);
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("RefMobile", edMobileNo.getText().toString().trim());
        object.addProperty("Email", "");
        Call<ResponseReferalName> call = apiServices.getReferalName(object);

        call.enqueue(new Callback<ResponseReferalName>() {
            @Override
            public void onResponse(@NonNull Call<ResponseReferalName> call, @NonNull Response<ResponseReferalName> response) {
                LoggerUtil.logItem(response.body());
                hideLoading();
                try {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        edPassword.setVisibility(View.VISIBLE);
                        edPassword.requestFocus();
                        view.setVisibility(View.VISIBLE);
                    } else {
                        edPassword.setVisibility(View.GONE);
                        view.setVisibility(View.GONE);

                        Bundle bundle = new Bundle();
                        bundle.putString("from", "mobile");
                        bundle.putString("mobile", edMobileNo.getText().toString().trim());
                        goToActivityWithFinish(RegistrationActivity.class, bundle);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseReferalName> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void getLogin() {
        Log.e("INSIDE", "============ > getLogin");
        showLoading();
        JsonObject param = new JsonObject();
        param.addProperty("LoginID", edMobileNo.getText().toString().trim());
        param.addProperty("Password", edPassword.getText().toString().trim());
        param.addProperty("DeviceId", pref.getString("regId", ""));
        param.addProperty("AndroidId", PreferencesManager.getInstance(context).getANDROIDID());
        Call<ResponseLogin> loginCall = apiServices.getLogin(param);
        LoggerUtil.logItem(param);
        loginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLogin> call, @NonNull Response<ResponseLogin> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                        if (response.body().getAuthonticate().getMessage().equalsIgnoreCase(
                                "Device Id not Matched")) {
                            pin = generatePin();
                            String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
                            getOTP(msg, mobileNo_st);
                        } else if (response.body().getAuthonticate().getMessage().equalsIgnoreCase(
                                "Device Id Matched")) {
                            allowLogin(response);
                        }
                    } else {
//                        This User Already Logged in with another device
                        if (response.body().getMessage().contains("another device")) {
                            String message = response.body().getMessage();
                            String[] fkMemId = message.split(":");
                            LoggerUtil.logItem(fkMemId[0]);
                            LoggerUtil.logItem(fkMemId[1]);
                            LoggerUtil.logItem(fkMemId[2]);
                            showErrorNew("You are already active in some other mobile," +
                                    " Kindly logout from that device.", fkMemId[1], fkMemId[2]);
                        } else if (response.body() == null) {
                            goToActivityWithFinish(context, MaintenancePage.class, null);
                        } else if (response.body().getMessage().contains("website is under maintenance")) {
                            goToActivityWithFinish(context, MaintenancePage.class, null);
                        } else if (response.body().getMessage().contains("stop")) {
                            Bundle b = new Bundle();
                            b.putString("msg", response.body().getMessage());
                            goToActivityWithFinish(context, MaintenancePage.class, b);
                        } else if (response.body().getMessage().contains("not Valid")) {
                            showError("Mobile Number/Email or password is not valid.", edPassword);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseLogin> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private boolean Validation() {
        try {
            mobileNo_st = edMobileNo.getText().toString().trim();
            password_st = edPassword.getText().toString().trim();

            if (mobileNo_st.length() == 0 /*|| mobileNo_st.length() != 10*/) {
                mobileNo_st = "";
                showError(getResources().getString(R.string.valid_mob_no_err), edMobileNo);
                return false;
            } else if (edPassword.isShown() && password_st.length() == 0) {
                password_st = "";
                showError(getResources().getString(R.string.enter_pswd_err), edPassword);
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
    public void onOTPReceived(String otp) {
        try {
//            showToast("OTP Received: " + otp);
            if (smsReceiver != null) {
                LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);
            }
            String[] bits = otp.split(" ");
            String lastWord = bits[bits.length - 2];
            editTextConfirmOtp.setText(lastWord);
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
        showToast(error);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);
        }
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

    public void getOTP(String msg, String mobile) {
        showLoading();
//        String url_base = BuildConfig.SMS_URL + mobile + "&msg=" + msg;
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
                        R.color.green, R.drawable.successful);
                startSMSListener();
                new Handler().postDelayed(() -> confirmOtp(), DELAY_TIME);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                hideLoading();
                showAlert("Something went wrong, Try again.",
                        R.color.red, R.drawable.error);
            }
        });
    }

    private void confirmOtp() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(context);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.otp_lay, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        AppCompatButton buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
        AppCompatButton buttonCncel = confirmDialog.findViewById(R.id.buttonCncel);
        tv_resend_otp = confirmDialog.findViewById(R.id.tv_resend_otp);
        editTextConfirmOtp = confirmDialog.findViewById(R.id.editTextOtp);


        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        tv_resend_otp.setOnClickListener(v -> {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {

                String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
                alertDialog.dismiss();
                getOTP(msg, mobileNo_st);
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }
        });

        buttonCncel.setOnClickListener(v -> alertDialog.dismiss());

        //Displaying the alert dialog
        alertDialog.show();
        buttonConfirm.setOnClickListener(view -> {
            if (TextUtils.isEmpty(editTextConfirmOtp.getText().toString())) {
                editTextConfirmOtp.setText("");
                editTextConfirmOtp.setError("Invalid OTP");
            } else if (editTextConfirmOtp.getText().toString().length() < 4) {
                editTextConfirmOtp.setError("Invalid OTP");
            } else if (editTextConfirmOtp.getText().toString().equalsIgnoreCase(pin)) {
                otpVerifiedLogin();
                alertDialog.dismiss();
            } else {
                editTextConfirmOtp.setText("");
                editTextConfirmOtp.setError("Invalid OTP");
            }
        });
    }

    private void otpVerifiedLogin() {
        Log.e("INSIDE", "============ > otpVerifiedLogin");
        showLoading();
        JsonObject param = new JsonObject();
        param.addProperty("LoginID", mobileNo_st);
        param.addProperty("Password", password_st);
        param.addProperty("DeviceId", pref.getString("regId", ""));
        param.addProperty("AndroidId", PreferencesManager.getInstance(context).getANDROIDID());
        Call<ResponseLogin> loginCall = apiServices.getOtpVerifiedLogin(param);
        LoggerUtil.logItem(param);
        loginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLogin> call, @NonNull Response<ResponseLogin> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                        if (response.body().getAuthonticate().getMessage().equalsIgnoreCase(
                                "Device Id not Matched")) {
                            allowLogin(response);
                        } else if (response.body().getAuthonticate().getMessage().equalsIgnoreCase(
                                "Device Id Matched")) {
                            allowLogin(response);
                        }
                    } else {
                        if (response.body().getMessage().contains("another device")) {
                            showError("You are already active in some other mobile," +
                                    " Kindly logout from that device.", edMobileNo);
                        } else if (response.body().getMessage().contains("not Valid")) {
                            showError("Mobile Number/Email or password is not valid.", edMobileNo);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseLogin> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void allowLogin(Response<ResponseLogin> response) {
        if (response.body().getResponse().equalsIgnoreCase("Success")) {
            PreferencesManager.getInstance(context).setUSERID(response.body().getAuthonticate().getFkMemID());
            PreferencesManager.getInstance(context).setLoginID(response.body().getAuthonticate().getLoginId());
            PreferencesManager.getInstance(context).setMOBILE(response.body().getAuthonticate().getMobile());
            PreferencesManager.getInstance(context).setNAME(response.body().getAuthonticate().getFirstName());
            PreferencesManager.getInstance(context).setLNAME(response.body().getAuthonticate().getLastName());
            PreferencesManager.getInstance(context).setTransactionPass(response.body().getAuthonticate().getEwalletPassword());
            PreferencesManager.getInstance(context).setPINCODE(response.body().getAuthonticate().getPinCode());

            PreferencesManager.getInstance(context).setState(response.body().getAuthonticate().getState());
            PreferencesManager.getInstance(context).setCity(response.body().getAuthonticate().getCity());
            PreferencesManager.getInstance(context).setAddress(response.body().getAuthonticate().getAddress());

            PreferencesManager.getInstance(context).setEMAIL(response.body().getAuthonticate().getEmailID());
            PreferencesManager.getInstance(context).setIsKycVerified(response.body().getAuthonticate().getKycStatus());
            PreferencesManager.getInstance(context).setLoginPin(response.body().getAuthonticate().getGeneratePin());
            PreferencesManager.getInstance(context).setLastLogin(response.body().getAuthonticate().getLastLoginDate());
            PreferencesManager.getInstance(context).setInviteCode(response.body().getAuthonticate().getInviteCode());
            PreferencesManager.getInstance(context).setPROFILEPIC(response.body().getAuthonticate().getProfilepic());
            PreferencesManager.getInstance(context).setDOB(response.body().getAuthonticate().getDob());
            PreferencesManager.getInstance(context).setDOB_IMAGE(response.body().getBithdayUrl());

            PreferencesManager.getInstance(context).setSavedLOGINID(edMobileNo.getText().toString().trim());
            PreferencesManager.getInstance(context).setSavedPASSWORD(edPassword.getText().toString().trim());

            PreferencesManager.getInstance(context).setENTITY_ID(response.body().getAuthonticate().getEntityId());
            PreferencesManager.getInstance(context).setKIT_NO(response.body().getAuthonticate().getKitno());
            PreferencesManager.getInstance(context).setM2PStatus(response.body().getAuthonticate().getM2pStatus());

            PreferencesManager.getInstance(context).setSPOID(response.body().getAuthonticate().getPbid());
            PreferencesManager.getInstance(context).setSPO_USERNAME(response.body().getAuthonticate().getPbUserName());
            PreferencesManager.getInstance(context).setSPO_PASSWORD(response.body().getAuthonticate().getPbPassword());

            if (PreferencesManager.getInstance(context).getfirst_visit()) {
                goToActivityWithFinish(MobileLogin.this, Permission.class, null);
            } else {
                goToActivityWithFinish(MobileLogin.this, MainContainer.class, null);
            }

        } else {
            showAlert(response.body().getMessage(), R.color.red, R.drawable.error);
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void showErrorNew(String error_st, String loginId, String Mobile) {
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
        TextView logout_all = error_dialog.findViewById(R.id.logout_all);
        logout_all.setVisibility(View.VISIBLE);
//        logout_all.setText("Logout from all devices");
        logout_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin = generatePin();
                error_dialog.dismiss();
                String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
                getOTPNew(msg, Mobile, loginId);
            }
        });
        error_text.setText(error_st);
        error_dialog.show();
        ok_btn.setOnClickListener(view -> {
            PreferencesManager.getInstance(context).clear();
            Intent intent1 = new Intent(context, LoginActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
            error_dialog.dismiss();
            finish();
        });
    }

    public void getOTPNew(String msg, String mobile, String loginId) {
        showLoading();
//        String url_base = BuildConfig.SMS_URL + mobile + "&msg=" + msg;
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        Log.e("==Registration====>> ", msg);
        String url = BuildConfig.SMS_URL + mobile + "&msg=" + msg.replace(" ", "%20");
        LoggerUtil.logItem(url);
        Call<String> call = apiServices.getOtp(url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                hideLoading();
                showToast("An OTP has been sent on your registered mobile no *******" + mobile.substring(6) + ".");
                startSMSListener();
                new Handler().postDelayed(() -> confirmOtpNew(mobile, loginId), DELAY_TIME);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
//                hideLoading();
                showAlert("Something went wrong, Try again.",
                        R.color.red, R.drawable.error);
            }
        });
    }

    private void confirmOtpNew(String mobileno_st, String loginId) {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(context);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.otp_lay, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        AppCompatButton buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
        AppCompatButton buttonCncel = confirmDialog.findViewById(R.id.buttonCncel);
        tv_resend_otp = confirmDialog.findViewById(R.id.tv_resend_otp);
        editTextConfirmOtp = confirmDialog.findViewById(R.id.editTextOtp);


        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        tv_resend_otp.setOnClickListener(v -> {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
//                String msg = "Dear " + etName.getText().toString().trim() +
//                        ", One Time Password(OTP) for your request is " + generatePin()
//                        + ", OTP is valid for 5 min.http://cashbag.co.in/";
                String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
                alertDialog.dismiss();
                getOTPNew(msg, mobileno_st, loginId);
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }
        });

        buttonCncel.setOnClickListener(v -> alertDialog.dismiss());

        //Displaying the alert dialog
        alertDialog.show();
        buttonConfirm.setOnClickListener(view -> {
            if (TextUtils.isEmpty(editTextConfirmOtp.getText().toString())) {
                editTextConfirmOtp.setText("");
                editTextConfirmOtp.setError("Invalid OTP");
            } else if (editTextConfirmOtp.getText().toString().length() < 4) {
                editTextConfirmOtp.setError("Invalid OTP");
            } else if (editTextConfirmOtp.getText().toString().equalsIgnoreCase(pin)) {
                getSignout(loginId);
                alertDialog.dismiss();
            } else {
                editTextConfirmOtp.setText("");
                editTextConfirmOtp.setError("Invalid OTP");
            }
        });
    }

    private void getSignout(String loginId) {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Fk_MemId", loginId);

        Call<JsonObject> call = apiServices.getUserSignout(object);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                assert response.body() != null;
                if (response.body().get("response").getAsString().equalsIgnoreCase("Success")) {
                    getLogin();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

}
