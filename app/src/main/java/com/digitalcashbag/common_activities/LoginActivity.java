package com.digitalcashbag.common_activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatButton;
import android.text.Html;
import android.text.TextUtils;
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

import com.digitalcashbag.SMSReceiver;
import com.digitalcashbag.m2p.scanPay.ScanPayResult;
import com.digitalcashbag.mlm.activities.MainContainerMLM;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.shopping.activities.MainContainer;
import com.digitalcashbag.shopping.activities.WebViewActivity;
import com.digitalcashbag.utilities.themepark.ThemePark;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.digitalcashbag.R;

import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.AppConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.RequestForgotPass;
import kkm.com.core.model.response.ResponseForgotPass;
import kkm.com.core.model.response.ResponseLogin;
import kkm.com.core.model.response.m2p.addfund.ResponseAddFund;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class LoginActivity extends BaseActivity /*implements
        SMSReceiver.OTPReceiveListener */ {

    @BindView(R.id.et_reg_mob_no)
    TextInputEditText etRegMobNo;
    @BindView(R.id.input_layout_reg_no)
    TextInputLayout inputLayoutRegNo;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.forgot_password)
    TextView forgotPassword;
    @BindView(R.id.register)
    TextView register;

    @BindView(R.id.tv_terms)
    TextView tv_terms;

    private int DELAY_TIME = 2000;
    private SMSReceiver smsReceiver;
    private TextView tv_resend_otp;
    private EditText editTextConfirmOtp;

    private String mobileNo_st = "", password_st = "", from = "";
    private SharedPreferences pref;
    private String pin = "";

    private void getToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this, instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            Log.e("newToken", newToken);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("regId", newToken);
            editor.apply();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        pref = getApplicationContext().getSharedPreferences(AppConfig.SHARED_PREF, 0);
        getToken();
        if (getIntent().getBundleExtra(PAYLOAD_BUNDLE) != null) {
            from = getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("from");
        }

        String account = getColoredSpanned(getResources().getString(R.string.new_to_kkm), "#000000");
        String signup = getColoredSpanned("<b>" + getResources().getString(R.string.register_now) + "</b>", "#0281d5");
        register.setText(Html.fromHtml(account + " " + signup));

        // This code requires one time to get Hash keys do comment and share key
//        appSignatureHashHelper = new AppSignatureHashHelper(this);
        Log.e("App Signature : ", BuildConfig.SMS_RETRIEVER);
//        LoggerUtil.logItem(BuildConfig.SMS_RETRIEVER);

    }

    private String getColoredSpanned(String text, String color) {
        return "<font color=" + color + ">" + text + "</font>";
    }

    @OnClick({R.id.login_btn, R.id.forgot_password, R.id.register, R.id.tv_terms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getLoginbyEncryption();
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                }
                break;
            case R.id.forgot_password:
                openForgotPasswordDialog();
                break;
            case R.id.register:
                Bundle bundle = new Bundle();
                bundle.putString("from", "login");
                bundle.putString("mobile", "");
                goToActivityWithFinish(LoginActivity.this, RegistrationActivity.class, bundle);
                break;

            case R.id.tv_terms:
                Bundle bundleN = new Bundle();
                bundleN.putString("from", "Main");
                bundleN.putString("link", "https://cashbag.co.in/Home/TermAndConditions");
                goToActivity(context, WebViewActivity.class, bundleN);
                break;
        }
    }

    private boolean Validation() {
        try {
            mobileNo_st = etRegMobNo.getText().toString().trim();
            password_st = etPassword.getText().toString().trim();

            if (mobileNo_st.length() == 0 /*|| mobileNo_st.length() != 10*/) {
                mobileNo_st = "";
                showError(getResources().getString(R.string.loginid_err), etRegMobNo);
                return false;
            }
            if (password_st.length() == 0) {
                password_st = "";
                showError(getResources().getString(R.string.enter_pswd_err), etPassword);
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

//    private void getLogin() {
//        JsonObject param = new JsonObject();
//        param.addProperty("LoginID", mobileNo_st);
//        param.addProperty("Password", password_st);
//        param.addProperty("DeviceId", pref.getString("regId", ""));
//        param.addProperty("AndroidId", PreferencesManager.getInstance(context).getANDROIDID());
//        Call<ResponseLogin> loginCall = apiServices.getLogin(param);
//        Log.e("Splash ", PreferencesManager.getInstance(context).getSavedLOGINID());
//        LoggerUtil.logItem(param);
//        loginCall.enqueue(new Callback<ResponseLogin>() {
//            @Override
//            public void onResponse(@NonNull Call<ResponseLogin> call, @NonNull Response<ResponseLogin> response) {
//                LoggerUtil.logItem(response.body());
//                try {
//                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
//                        if (response.body().getAuthonticate().getMessage().equalsIgnoreCase(
//                                "Device Id not Matched")) {
//                            pin = generatePin();
//                            String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
//                            getOTP(msg, mobileNo_st);
//                        } else if (response.body().getAuthonticate().getMessage().equalsIgnoreCase(
//                                "Device Id Matched")) {
//                            allowLogin(response);
//                        }
//                    } else {
//                        if (response.body().getMessage().contains("another device")) {
//                            String message = response.body().getMessage();
//                            String[] fkMemId = message.split(":");
//                            LoggerUtil.logItem(fkMemId[0]);
//                            LoggerUtil.logItem(fkMemId[1]);
//                            LoggerUtil.logItem(fkMemId[2]);
//                            LoggerUtil.logItem(message);
//
//                            showErrorNew("You are already active in some other mobile," +
//                                    " Kindly logout from that device.", fkMemId[1], fkMemId[2]);
//                        } else if (response.body() == null) {
//                            goToActivityWithFinish(context, MaintenancePage.class, null);
//                        } else if (response.body().getMessage().contains("website is under maintenance")) {
//                            goToActivityWithFinish(context, MaintenancePage.class, null);
//                        } else if (response.body().getMessage().contains("stop")) {
//                            Bundle b = new Bundle();
//                            b.putString("msg", response.body().getMessage());
//                            goToActivityWithFinish(context, MaintenancePage.class, b);
//                        } else if (response.body().getMessage().contains("not Valid")) {
//                            showError("Mobile Number/Email or password is not valid.", etPassword);
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ResponseLogin> call, @NonNull Throwable t) {
//                hideLoading();
//            }
//        });
//    }

    private void getLoginbyEncryption() {
        showLoading();
        JsonObject param = new JsonObject();
        param.addProperty("LoginID", mobileNo_st);
        param.addProperty("Password", password_st);
        param.addProperty("DeviceId", pref.getString("regId", ""));
        param.addProperty("AndroidId", PreferencesManager.getInstance(context).getANDROIDID());

// TODO VIVEK V2 API Encyrpt
        String keys = BuildConfig.CASHBAG_KEY;
        SecretKey easypay_key = new SecretKeySpec(keys.getBytes(), "AES");

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(param.toString(), easypay_key));
            Log.e("BODY= Login == ", body.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<JsonObject> loginCall = apiServicesMLMV2.getLoginEnc(body);
        LoggerUtil.logItem(param);
        loginCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.message());
                LoggerUtil.logItem(response.code());
                LoggerUtil.logItem(response.errorBody());
                LoggerUtil.logItem(response.headers());
                LoggerUtil.logItem(response.raw().body());
                LoggerUtil.logItem(call.request().url());
                ResponseLogin response_new;
                try {
                    response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key), ResponseLogin.class);
                    Log.e("============ ", String.valueOf(response_new));
                    if (response.body() != null && response_new.getResponse().equalsIgnoreCase("Success")) {
                        if (response_new.getAuthonticate().getMessage().equalsIgnoreCase("Device Id not Matched")) {
                            pin = generatePin();
                            String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
//                            getOTP(msg, mobileNo_st);
                        } else if (response_new.getAuthonticate().getMessage().equalsIgnoreCase("Device Id Matched")) {
                            allowLogin(response_new);
                        }
                    } else {
                        showMessage("Something went wrong");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                try {
//                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
//                        if (response.body().getAuthonticate().getMessage().equalsIgnoreCase(
//                                "Device Id not Matched")) {
//
//                            pin = generatePin();
//
//                            String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
//                            getOTP(msg, mobileNo_st);
//                        } else if (response.body().getAuthonticate().getMessage().equalsIgnoreCase(
//                                "Device Id Matched")) {
//                            allowLogin(response);
//                        }
//                    } else {
//                        if (response.body().getMessage().contains("another device")) {
//
//                            String message = response.body().getMessage();
//                            String[] fkMemId = message.split(":");
//                            LoggerUtil.logItem(fkMemId[0]);
//                            LoggerUtil.logItem(fkMemId[1]);
//                            LoggerUtil.logItem(fkMemId[2]);
//                            LoggerUtil.logItem(message);
//
//                            showErrorNew("You are already active in some other mobile," +
//                                    " Kindly logout from that device.", fkMemId[1], fkMemId[2]);
//                        } else if (response.body() == null) {
//                            goToActivityWithFinish(context, MaintenancePage.class, null);
//                        } else if (response.body().getMessage().contains("website is under maintenance")) {
//                            goToActivityWithFinish(context, MaintenancePage.class, null);
//                        } else if (response.body().getMessage().contains("stop")) {
//                            Bundle b = new Bundle();
//                            b.putString("msg", response.body().getMessage());
//                            goToActivityWithFinish(context, MaintenancePage.class, b);
//                        } else if (response.body().getMessage().contains("not Valid")) {
//                            showError("Mobile Number/Email or password is not valid.", etPassword);
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideLoading();
                LoggerUtil.logItem(t.getMessage());
            }
        });
    }


    private void openForgotPasswordDialog() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(context);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.forgot_pass_dialog, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        AppCompatButton button_getpassword = confirmDialog.findViewById(R.id.button_getpassword);
        EditText mobile_number = confirmDialog.findViewById(R.id.mobile_number);
        EditText user_id = confirmDialog.findViewById(R.id.user_id);

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(true);

        //Displaying the alert dialog
        alertDialog.show();
        button_getpassword.setOnClickListener(view -> {
            try {
                String mobileNo_st = mobile_number.getText().toString().trim();
                String user_id_st = user_id.getText().toString().trim();
               /* if (user_id_st.length() == 0) {
                    user_id_st = "";
                    showError(getResources().getString(R.string.loginid_err), user_id);
                } else */
                {
                    if (mobileNo_st.length() == 0 || mobileNo_st.length() != 10) {
                        mobileNo_st = "";
                        showError(getResources().getString(R.string.valid_mob_no_err), mobile_number);
                    } else {
                        if (NetworkUtils.getConnectivityStatus(context) != 0) {
                            alertDialog.dismiss();
                            getForgotPass(mobileNo_st);
                        } else {
                            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void getForgotPass(String mobileNo_st) {
        showLoading();
        RequestForgotPass requestForgotPass = new RequestForgotPass();
        requestForgotPass.setMobile(mobileNo_st);
        Call<ResponseForgotPass> responseForgotPassCall = apiServices.getForgotPassword(requestForgotPass);
        LoggerUtil.logItem(requestForgotPass);
        responseForgotPassCall.enqueue(new Callback<ResponseForgotPass>() {
            @Override
            public void onResponse(@NonNull Call<ResponseForgotPass> call, @NonNull Response<ResponseForgotPass> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
//                    showAlert(response.body().getMessage(), R.color.green, R.drawable.alerter_ic_notifications);
                    String msg = "Dear " + response.body().getName() + " you have successfully registered with CASHBAG, your login id- "
                            + response.body().getMobileNo() + " and password-" + response.body().getPassword() + " and Trans. Password -"
                            + response.body().getEwalletPassword() + ". Login http://cashbag.co.in/";
                    sendCredentials(msg, response.body().getMobileNo());
                } else {
                    showAlert(response.body().getMessage(), R.color.red, R.drawable.error);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseForgotPass> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void sendCredentials(String msg, String mobile) {
        String url_base = BuildConfig.SMS_URL + mobile + "&msg=" + msg;
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        Log.e("==ForgotPassword====>> ", msg);
        String url = url_base + mobile + "&msg=" + msg.replace(" ", "%20");
        LoggerUtil.logItem(url);
        Call<String> call = apiServices.getOtp(url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                hideLoading();
                showAlert("Your password details has been send to your registered mobile no *******" + mobile.substring(6) + ".",
                        R.color.green, R.drawable.successful);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                hideLoading();
                showAlert("Something went wrong, Try again.",
                        R.color.red, R.drawable.error);
            }
        });
    }


//    @Override
//    public void onOTPReceived(String otp) {
//        try {
////            showToast("OTP Received: " + otp);
//            if (smsReceiver != null) {
//                LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);
//            }
//            String[] bits = otp.split(" ");
//            String lastWord = bits[bits.length - 2];
//            editTextConfirmOtp.setText(lastWord);
//            hideLoading();
//        } catch (Error | Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onOTPTimeOut() {
////        showToast("OTP Time out");
//    }
//
//    @Override
//    public void onOTPReceivedError(String error) {
//        showToast(error);
//    }
//

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);
        }
    }

//    private void startSMSListener() {
//        try {
//            smsReceiver = new SMSReceiver();
//            smsReceiver.setOTPListener(this);
//
//            IntentFilter intentFilter = new IntentFilter();
//            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
//            this.registerReceiver(smsReceiver, intentFilter);
//
//            SmsRetrieverClient client = SmsRetriever.getClient(this);
//
//            Task<Void> task = client.startSmsRetriever();
//            task.addOnSuccessListener(aVoid -> {
//                // API successfully started
//            });
//
//            task.addOnFailureListener(e -> {
//                // Fail to start API
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void getOTP(String msg, String mobile) {
//        showLoading();
////        String url_base = BuildConfig.SMS_URL + mobile + "&msg=" + msg;
//        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
//        Log.e("==Registration====>> ", msg);
//        String url = BuildConfig.SMS_URL + mobile + "&msg=" + msg.replace(" ", "%20");
//        LoggerUtil.logItem(url);
//        Call<String> call = apiServices.getOtp(url);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
//                hideLoading();
//                showAlert("An OTP has been sent on your registered mobile no *******" + mobile.substring(6) + ".",
//                        R.color.green, R.drawable.successful);
//                startSMSListener();
//                new Handler().postDelayed(() -> confirmOtp(), DELAY_TIME);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
//                hideLoading();
//                showAlert("Something went wrong, Try again.",
//                        R.color.red, R.drawable.error);
//            }
//        });
//    }

//    private void confirmOtp() {
//        //Creating a LayoutInflater object for the dialog box
//        LayoutInflater li = LayoutInflater.from(context);
//        //Creating a view to get the dialog box
//        View confirmDialog = li.inflate(R.layout.otp_lay, null);
//
//        //Initizliaing confirm button fo dialog box and edittext of dialog box
//        AppCompatButton buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
//        AppCompatButton buttonCncel = confirmDialog.findViewById(R.id.buttonCncel);
//        tv_resend_otp = confirmDialog.findViewById(R.id.tv_resend_otp);
//        editTextConfirmOtp = confirmDialog.findViewById(R.id.editTextOtp);
//
//
//        //Creating an alertdialog builder
//        AlertDialog.Builder alert = new AlertDialog.Builder(context);
//
//        //Adding our dialog box to the view of alert dialog
//        alert.setView(confirmDialog);
//
//        //Creating an alert dialog
//        final AlertDialog alertDialog = alert.create();
//        alertDialog.setCancelable(false);
//        alertDialog.setCanceledOnTouchOutside(false);
//
//        tv_resend_otp.setOnClickListener(v -> {
//            if (NetworkUtils.getConnectivityStatus(context) != 0) {
////                String msg = "Dear " + etName.getText().toString().trim() +
////                        ", One Time Password(OTP) for your request is " + generatePin()
////                        + ", OTP is valid for 5 min.http://cashbag.co.in/";
//                String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
//                alertDialog.dismiss();
//                getOTP(msg, mobileNo_st);
//            } else {
//                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
//            }
//        });
//
//        buttonCncel.setOnClickListener(v -> alertDialog.dismiss());
//
//        //Displaying the alert dialog
//        alertDialog.show();
//        buttonConfirm.setOnClickListener(view -> {
//            if (TextUtils.isEmpty(editTextConfirmOtp.getText().toString())) {
//                editTextConfirmOtp.setText("");
//                editTextConfirmOtp.setError("Invalid OTP");
//            } else if (editTextConfirmOtp.getText().toString().length() < 4) {
//                editTextConfirmOtp.setError("Invalid OTP");
//            } else if (editTextConfirmOtp.getText().toString().equalsIgnoreCase(pin)) {
//                otpVerifiedLogin();
//                alertDialog.dismiss();
//            } else {
//                editTextConfirmOtp.setText("");
//                editTextConfirmOtp.setError("Invalid OTP");
//            }
//        });
//    }

//    private void otpVerifiedLogin() {
//        showLoading();
//        JsonObject param = new JsonObject();
//        param.addProperty("LoginID", mobileNo_st);
//        param.addProperty("Password", password_st);
//        param.addProperty("DeviceId", pref.getString("regId", ""));
//        param.addProperty("AndroidId", PreferencesManager.getInstance(context).getANDROIDID());
//        Call<ResponseLogin> loginCall = apiServices.getOtpVerifiedLogin(param);
//        LoggerUtil.logItem(param);
//        loginCall.enqueue(new Callback<ResponseLogin>() {
//            @Override
//            public void onResponse(@NonNull Call<ResponseLogin> call, @NonNull Response<ResponseLogin> response) {
//                hideLoading();
//                LoggerUtil.logItem(response.body());
//                try {
//                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
//                        if (response.body().getAuthonticate().getMessage().equalsIgnoreCase(
//                                "Device Id not Matched")) {
//                            allowLogin(response);
//                        } else if (response.body().getAuthonticate().getMessage().equalsIgnoreCase(
//                                "Device Id Matched")) {
//                            allowLogin(response);
//                        }
//                    } else {
//                        if (response.body().getMessage().contains("another device")) {
//                            showError("You are already active in some other mobile," +
//                                    " Kindly logout from that device.", etPassword);
//                        } else if (response.body().getMessage().contains("not Valid")) {
//                            showError("Mobile Number/Email or password is not valid.", etPassword);
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ResponseLogin> call, @NonNull Throwable t) {
//                hideLoading();
//            }
//        });
//    }

    private void allowLogin(ResponseLogin response) {
        PreferencesManager.getInstance(context).setUSERID(response.getAuthonticate().getFkMemID());
        PreferencesManager.getInstance(context).setLoginID(response.getAuthonticate().getLoginId());
        PreferencesManager.getInstance(context).setMOBILE(response.getAuthonticate().getMobile());
        PreferencesManager.getInstance(context).setNAME(response.getAuthonticate().getFirstName());
        PreferencesManager.getInstance(context).setLNAME(response.getAuthonticate().getLastName());
        PreferencesManager.getInstance(context).setTransactionPass(response.getAuthonticate().getEwalletPassword());
        PreferencesManager.getInstance(context).setPINCODE(response.getAuthonticate().getPinCode());

        PreferencesManager.getInstance(context).setState(response.getAuthonticate().getState());
        PreferencesManager.getInstance(context).setCity(response.getAuthonticate().getCity());
        PreferencesManager.getInstance(context).setAddress(response.getAuthonticate().getAddress());

        PreferencesManager.getInstance(context).setEMAIL(response.getAuthonticate().getEmailID());
        PreferencesManager.getInstance(context).setIsKycVerified(response.getAuthonticate().getKycStatus());
        PreferencesManager.getInstance(context).setLoginPin(response.getAuthonticate().getGeneratePin());
        PreferencesManager.getInstance(context).setLastLogin(response.getAuthonticate().getLastLoginDate());
        PreferencesManager.getInstance(context).setInviteCode(response.getAuthonticate().getInviteCode());
        PreferencesManager.getInstance(context).setPROFILEPIC(response.getAuthonticate().getProfilepic());
        PreferencesManager.getInstance(context).setDOB(response.getAuthonticate().getDob());
        PreferencesManager.getInstance(context).setDOB_IMAGE(response.getBithdayUrl());

        PreferencesManager.getInstance(context).setENTITY_ID(response.getAuthonticate().getEntityId());
        PreferencesManager.getInstance(context).setKIT_NO(response.getAuthonticate().getKitno());
        PreferencesManager.getInstance(context).setM2PStatus(response.getAuthonticate().getM2pStatus());

        PreferencesManager.getInstance(context).setSavedLOGINID(mobileNo_st);
        PreferencesManager.getInstance(context).setSavedPASSWORD(password_st);
        PreferencesManager.getInstance(context).setSPOID(response.getAuthonticate().getPbid());
        PreferencesManager.getInstance(context).setSPO_USERNAME(response.getAuthonticate().getPbUserName());
        PreferencesManager.getInstance(context).setSPO_PASSWORD(response.getAuthonticate().getPbPassword());

        Bundle b = new Bundle();
        switch (from) {
            case "wallet":
                b.putString("from", "wallet");
                goToActivityWithFinish(MainContainer.class, b);
                break;
            case "payout":
                b.putString("from", "payout");
                goToActivityWithFinish(MainContainer.class, b);
                break;
            case "business":
                goToActivityWithFinish(MainContainerMLM.class, null);
                break;
            case "Theme Park":
                goToActivityWithFinish(ThemePark.class, null);
                break;
            default:
                goToActivityWithFinish(MainContainer.class, null);
                break;
        }

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

//    private void showErrorNew(String error_st, String loginId, String Mobile) {
//        Dialog error_dialog = new Dialog(this);
//        error_dialog.setCanceledOnTouchOutside(false);
//        error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        error_dialog.setContentView(R.layout.error_dialoge);
//        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
//        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
//        error_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
//        error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        TextView error_text = error_dialog.findViewById(R.id.error_text);
//        Button ok_btn = error_dialog.findViewById(R.id.ok_btn);
//        TextView logout_all = error_dialog.findViewById(R.id.logout_all);
//        logout_all.setVisibility(View.VISIBLE);
////        logout_all.setText("Logout from all devices");
//        logout_all.setOnClickListener(v -> {
//            pin = generatePin();
//            error_dialog.dismiss();
//            String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
//            getOTPNew(msg, Mobile, loginId);
//        });
//        error_text.setText(error_st);
//        error_dialog.show();
//        ok_btn.setOnClickListener(view -> {
//            PreferencesManager.getInstance(context).clear();
//            Intent intent1 = new Intent(context, LoginActivity.class);
//            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent1);
//            error_dialog.dismiss();
//            finish();
//        });
//    }

//    public void getOTPNew(String msg, String mobile, String loginId) {
//        showLoading();
////        String url_base = BuildConfig.SMS_URL + mobile + "&msg=" + msg;
//        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
//        Log.e("==Registration====>> ", msg);
//        String url = BuildConfig.SMS_URL + mobile + "&msg=" + msg.replace(" ", "%20");
//        LoggerUtil.logItem(url);
//        Call<String> call = apiServices.getOtp(url);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
//                hideLoading();
//                showToast("An OTP has been sent on your registered mobile no *******" + mobile.substring(6) + ".");
//                startSMSListener();
//                new Handler().postDelayed(() -> confirmOtpNew(mobile, loginId), DELAY_TIME);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
////                hideLoading();
//                showAlert("Something went wrong, Try again.",
//                        R.color.red, R.drawable.error);
//            }
//        });
//    }

//    private void confirmOtpNew(String mobileno_st, String loginId) {
//        //Creating a LayoutInflater object for the dialog box
//        LayoutInflater li = LayoutInflater.from(context);
//        //Creating a view to get the dialog box
//        View confirmDialog = li.inflate(R.layout.otp_lay, null);
//
//        //Initizliaing confirm button fo dialog box and edittext of dialog box
//        AppCompatButton buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
//        AppCompatButton buttonCncel = confirmDialog.findViewById(R.id.buttonCncel);
//        tv_resend_otp = confirmDialog.findViewById(R.id.tv_resend_otp);
//        editTextConfirmOtp = confirmDialog.findViewById(R.id.editTextOtp);
//
//
//        //Creating an alertdialog builder
//        AlertDialog.Builder alert = new AlertDialog.Builder(context);
//
//        //Adding our dialog box to the view of alert dialog
//        alert.setView(confirmDialog);
//
//        //Creating an alert dialog
//        final AlertDialog alertDialog = alert.create();
//        alertDialog.setCancelable(false);
//        alertDialog.setCanceledOnTouchOutside(false);
//
//        tv_resend_otp.setOnClickListener(v -> {
//            if (NetworkUtils.getConnectivityStatus(context) != 0) {
////                String msg = "Dear " + etName.getText().toString().trim() +
////                        ", One Time Password(OTP) for your request is " + generatePin()
////                        + ", OTP is valid for 5 min.http://cashbag.co.in/";
//                String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
//                alertDialog.dismiss();
//                getOTPNew(msg, mobileno_st, loginId);
//            } else {
//                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
//            }
//        });
//
//        buttonCncel.setOnClickListener(v -> alertDialog.dismiss());
//
//        //Displaying the alert dialog
//        alertDialog.show();
//        buttonConfirm.setOnClickListener(view -> {
//            if (TextUtils.isEmpty(editTextConfirmOtp.getText().toString())) {
//                editTextConfirmOtp.setText("");
//                editTextConfirmOtp.setError("Invalid OTP");
//            } else if (editTextConfirmOtp.getText().toString().length() < 4) {
//                editTextConfirmOtp.setError("Invalid OTP");
//            } else if (editTextConfirmOtp.getText().toString().equalsIgnoreCase(pin)) {
//                getSignout(loginId);
//                alertDialog.dismiss();
//            } else {
//                editTextConfirmOtp.setText("");
//                editTextConfirmOtp.setError("Invalid OTP");
//            }
//        });
//    }

//    private void getSignout(String loginId) {
//        showLoading();
//        JsonObject object = new JsonObject();
//        object.addProperty("Fk_MemId", loginId);
//
//        Call<JsonObject> call = apiServices.getUserSignout(object);
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
//                hideLoading();
//                assert response.body() != null;
//                if (response.body().get("response").getAsString().equalsIgnoreCase("Success")) {
//                    getLoginbyEncryption();
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
//
//            }
//        });
//    }
}
