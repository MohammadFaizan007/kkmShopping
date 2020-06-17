package com.digitalcashbag.common_activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.PinLockView;
import com.digitalcashbag.PinEncryptionTests;
import com.digitalcashbag.SMSReceiver;
import com.digitalcashbag.shopping.activities.MainContainer;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;
import com.digitalcashbag.R;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.AppConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.ResponseLogin;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class
SplashActivity extends BaseActivity implements
        SMSReceiver.OTPReceiveListener {

    @BindView(R.id.progressBar2)
    ProgressBar progressBar;
    TextView tv_resend_otp;
    EditText editTextConfirmOtp;
    private EditText editTextpin;
    private AlertDialog alertDialog, verifyDialog;
    private Dialog pin_dialog;
    private PinLockView mPinLockView;
    private SharedPreferences pref;
    private int DELAY_TIME = 2000;
    private SMSReceiver smsReceiver;
    private AppUpdater updater;
    private String pin = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        PinEncryptionTests tests = new PinEncryptionTests();
        try {
            String test = tests.encryptionPinBlock("890000011", "1391");
            Log.e("enc : ", test);
            LoggerUtil.logItem("enc : " + test);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getLogin() {
        progressBar.setVisibility(View.VISIBLE);
        JsonObject param = new JsonObject();
        param.addProperty("LoginID", PreferencesManager.getInstance(context).getSavedLOGINID());
        param.addProperty("Password", PreferencesManager.getInstance(context).getSavedPASSWORD());
        param.addProperty("DeviceId", pref.getString("regId", ""));
        param.addProperty("AndroidId", PreferencesManager.getInstance(context).getANDROIDID());
        Call<ResponseLogin> loginCall = apiServices.getLogin(param);
        Log.e("Splash ", PreferencesManager.getInstance(context).getSavedLOGINID());
        LoggerUtil.logItem(param);
        loginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLogin> call, @NonNull Response<ResponseLogin> response) {
                if (progressBar != null)
                    progressBar.setVisibility(View.GONE);
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                        if (response.body().getAuthonticate().getMessage().equalsIgnoreCase(
                                "Device Id not Matched")) {
                            pin = generatePin();
                            String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
                            getOTP(msg, PreferencesManager.getInstance(context).getSavedLOGINID());
                        } else if (response.body().getAuthonticate().getMessage().equalsIgnoreCase(
                                "Device Id Matched")) {
                            allowLogin(response);
                        }
                    } else {
                        if (response.body() != null && response.body().getMessage().contains("another device")) {
                            String message = response.body().getMessage();
                            String[] fkMemId = message.split(":");
                            LoggerUtil.logItem(fkMemId[0]);
                            LoggerUtil.logItem(fkMemId[1]);
                            LoggerUtil.logItem(fkMemId[2]);
                            showErrorNew("You are already active in some other mobile," + " Kindly logout from that device.", fkMemId[1], fkMemId[2]);
                        } else if (response.body() == null) {
                            goToActivityWithFinish(context, MaintenancePage.class, null);
                        } else if (response.body().getMessage().contains("website is under maintenance")) {
                            goToActivityWithFinish(context, MaintenancePage.class, null);
                        } else if (response.body().getMessage().contains("stop")) {
                            Bundle b = new Bundle();
                            b.putString("msg", response.body().getMessage());
                            goToActivityWithFinish(context, MaintenancePage.class, b);
                        } else {
                            goToActivityWithFinish(context, LoginActivity.class, null);
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

//    private void dialogCreatePIN() {
//        //Creating a LayoutInflater object for the dialog box
//        LayoutInflater li = LayoutInflater.from(context);
//        //Creating a view to get the dialog box
//        View confirmDialog = li.inflate(R.layout.dialog_pin_generation, null);
//
//        //Initizliaing confirm button fo dialog box and edittext of dialog box
//        Button buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
//        Button btn_cancel = confirmDialog.findViewById(R.id.btn_cancel);
//        editTextpin = confirmDialog.findViewById(R.id.editTextpin);
//
//        //Creating an alertdialog builder
//        AlertDialog.Builder alert = new AlertDialog.Builder(context);
//
//        //Adding our dialog box to the view of alert dialog
//        alert.setView(confirmDialog);
//
//        //Creating an alert dialog
//        alertDialog = alert.create();
//        alertDialog.setCancelable(false);
//        alertDialog.setCanceledOnTouchOutside(false);
//
//        //Displaying the alert dialog
//        alertDialog.show();
//
//        btn_cancel.setOnClickListener(v -> {
//            hideKeyboard();
//            alertDialog.dismiss();
////            if (pin)
//            goToActivityWithFinish(SplashActivity.this, MainContainer.class, null);
//        });
//
//        buttonConfirm.setOnClickListener(view -> {
//            if (editTextpin.getText().toString().trim().length() < 4) {
//                editTextpin.setError("Enter 4 digit PIN");
//            } else {
//                hideKeyboard();
//                if (NetworkUtils.getConnectivityStatus(context) != 0) {
//                    createPin(editTextpin.getText().toString().trim());
//                } else {
//                    showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
//                }
//            }
//        });
//    }

//    private void createPin(String pin) {
//        RequestCreateLoginPIN loginPIN = new RequestCreateLoginPIN();
//        loginPIN.setFKMemId(PreferencesManager.getInstance(context).getUSERID());
//        loginPIN.setGeneratePin(pin);
//        progressBar.setVisibility(View.VISIBLE);
//        Call<ResponseProfileUpdate> call = apiServices.createLoginPIN(loginPIN);
//        call.enqueue(new Callback<ResponseProfileUpdate>() {
//            @Override
//            public void onResponse(@NonNull Call<ResponseProfileUpdate> call, @NonNull Response<ResponseProfileUpdate> response) {
//                if (progressBar != null)
//                    progressBar.setVisibility(View.GONE);
//                if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
//                    goToActivityWithFinish(SplashActivity.this, MainContainer.class, null);
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ResponseProfileUpdate> call, @NonNull Throwable t) {
//            }
//        });
//    }

//    private void enterPinDialog() {
//        pin_dialog = new Dialog(context, R.style.FullScreenDialog);
//        pin_dialog.setContentView(R.layout.dialog_pin);
//        mPinLockView = pin_dialog.findViewById(R.id.pin_lock_view);
//        TextView foorgotPin = pin_dialog.findViewById(R.id.forgotPin);
//        IndicatorDots mIndicatorDots = pin_dialog.findViewById(R.id.indicator_dots);
//
//        mPinLockView.attachIndicatorDots(mIndicatorDots);
//        mPinLockView.setPinLockListener(mPinLockListener);
//        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
//        //mPinLockView.enableLayoutShuffling();
//
//        pin_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                dialog.dismiss();
////                finish();
//            }
//        });
//
//        mPinLockView.setPinLength(4);
//        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));
//
//        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FIXED);
//
//        foorgotPin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pin_dialog.cancel();
//                dialogVerifyPIN();
//            }
//        });
//        pin_dialog.show();
//
//    }

//    private void dialogVerifyPIN() {
//        //Creating a LayoutInflater object for the dialog box
//        LayoutInflater li = LayoutInflater.from(context);
//        //Creating a view to get the dialog box
//        View confirmDialog = li.inflate(R.layout.dialog_pin_verify, null);
//
//        //Initizliaing confirm button fo dialog box and edittext of dialog box
//        Button btn_confirm = confirmDialog.findViewById(R.id.btn_confirm);
//        Button btn_cancel = confirmDialog.findViewById(R.id.btn_cancel);
//        EditText et_mobile = confirmDialog.findViewById(R.id.et_mobile);
//        EditText et_pan = confirmDialog.findViewById(R.id.et_pan);
//
//        //Creating an alertdialog builder
//        AlertDialog.Builder alert = new AlertDialog.Builder(context);
//
//        //Adding our dialog box to the view of alert dialog
//        alert.setView(confirmDialog);
//
//        //Creating an alert dialog
//        verifyDialog = alert.create();
//        verifyDialog.setCancelable(false);
//        verifyDialog.setCanceledOnTouchOutside(false);
//
//        //Displaying the alert dialog
//        verifyDialog.show();
//
//        btn_cancel.setOnClickListener(v -> {
//            hideKeyboard();
//            verifyDialog.dismiss();
//            finish();
//        });
//
//        btn_confirm.setOnClickListener(view -> {
//            if (TextUtils.isEmpty(et_mobile.getText().toString()) && et_mobile.getText().toString().length() < 10) {
//                et_mobile.setError("Enter valid mobile number.");
//            } else if (!Utils.validatePan(et_pan.getText().toString().trim())) {
//                et_pan.setError("Invalid PAN number");
//                et_pan.requestFocus();
//            } else {
//                verifyDialog.dismiss();
//                verifyPin(et_mobile.getText().toString(), et_pan.getText().toString());
//                hideKeyboard();
//
//            }
//        });
//    }

//    private void verifyPin(String mobile, String pan) {
//
//        JsonObject param = new JsonObject();
//
////        {"LoginId":"111000","MobileNo":"95551231000","PanNo":"ABCVF1234s"}
//
//        param.addProperty("LoginId", PreferencesManager.getInstance(context).getLoginID());
//        param.addProperty("MobileNo", mobile);
//        param.addProperty("PanNo", pan);
//        LoggerUtil.logItem(param);
//
//        Call<JsonObject> objectCall = apiServices.verifyKycPin(param);
//        objectCall.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
//                LoggerUtil.logItem(response.body());
//                try {
//                    if (response.body() != null && response.body().get("response").getAsString().equalsIgnoreCase("Success")) {
//                        pin = generatePin();
//                        String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
//                        getOTPPin(msg, mobile);
//                    } else {
//                        showMessage(response.body().get("response").getAsString());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    showMessage("Something went wrong.");
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
//
//            }
//        });
//
//
//    }

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
//        showLoading();
        progressBar.setVisibility(View.VISIBLE);
//        String url_base = BuildConfig.SMS_URL + mobile + "&msg=" + msg;
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        Log.e("==Registration====>> ", msg);
        String url = BuildConfig.SMS_URL + mobile + "&msg=" + msg.replace(" ", "%20");
        LoggerUtil.logItem(url);
        Call<String> call = apiServices.getOtp(url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
//                hideLoading();
                if (progressBar != null)
                    progressBar.setVisibility(View.GONE);
                showAlert("An OTP has been sent on your registered mobile no *******" + mobile.substring(6) + ".",
                        R.color.green, R.drawable.successful);
                startSMSListener();
                new Handler().postDelayed(() -> confirmOtp(), DELAY_TIME);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
//                hideLoading();
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
                getOTP(msg, PreferencesManager.getInstance(context).getSavedLOGINID());
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
//        showLoading();
        progressBar.setVisibility(View.VISIBLE);
        JsonObject param = new JsonObject();
        param.addProperty("LoginID", PreferencesManager.getInstance(context).getSavedLOGINID());
        param.addProperty("Password", PreferencesManager.getInstance(context).getSavedPASSWORD());
        param.addProperty("DeviceId", pref.getString("regId", ""));
        param.addProperty("AndroidId", PreferencesManager.getInstance(context).getANDROIDID());
        Call<ResponseLogin> loginCall = apiServices.getOtpVerifiedLogin(param);
        LoggerUtil.logItem(param);
        loginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLogin> call, @NonNull Response<ResponseLogin> response) {
//                hideLoading();
                if (progressBar != null)
                    progressBar.setVisibility(View.GONE);
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                        if (response.body().getAuthonticate().getMessage().equalsIgnoreCase("Device Id not Matched")) {
                            allowLogin(response);
                        } else if (response.body().getAuthonticate().getMessage().equalsIgnoreCase(
                                "Device Id Matched")) {
                            allowLogin(response);
                        }
                    } else {
                        if (response.body().getMessage().contains("another device")) {
                            showAlert("You are already active in some other mobile," +
                                    " Kindly logout from that device.", R.color.red, R.drawable.error);
                        } else if (response.body().getMessage().contains("not Valid")) {
                            showAlert("Mobile Number/Email or password is not valid.", R.color.red, R.drawable.error);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseLogin> call, @NonNull Throwable t) {
//                hideLoading();
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
            PreferencesManager.getInstance(context).setDOB(response.body().getAuthonticate().getDob());
            LoggerUtil.logItem(response.body().getAuthonticate().getDob());
            PreferencesManager.getInstance(context).setDOB_IMAGE(response.body().getBithdayUrl());

            PreferencesManager.getInstance(context).setENTITY_ID(response.body().getAuthonticate().getEntityId());
            PreferencesManager.getInstance(context).setKIT_NO(response.body().getAuthonticate().getKitno());
            PreferencesManager.getInstance(context).setSPOID(response.body().getAuthonticate().getPbid());
            PreferencesManager.getInstance(context).setSPO_USERNAME(response.body().getAuthonticate().getPbUserName());
            PreferencesManager.getInstance(context).setSPO_PASSWORD(response.body().getAuthonticate().getPbPassword());

//            if (response.body().getAuthonticate().getKycStatus().equalsIgnoreCase("Approved")) {
//                if (response.body().getAuthonticate().getGeneratePin().length() == 0) {
//                    dialogCreatePIN();
//                } else {
//                    enterPinDialog();
//                }
//            } else {
//            }
            goToActivityWithFinish(SplashActivity.this, MainContainer.class, null);

        } else {
            PreferencesManager.getInstance(context).clear();
            Intent intent1 = new Intent(context, LoginActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
            finish();
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

//    private void showError(String error_st) {
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

    private void checkUpdate() {
        progressBar.setVisibility(View.VISIBLE);
        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this).setUpdateFrom(UpdateFrom.GOOGLE_PLAY).
                withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("Latest Version", update.getLatestVersion());
                        Log.d("Latest Version Code", "=" + update.getLatestVersionCode());
                        Log.d("Release notes", update.getReleaseNotes());
                        Log.d("URL", "=" + update.getUrlToDownload());
                        Log.d("Is update available?", Boolean.toString(isUpdateAvailable));
                        if (isUpdateAvailable) {
                            if (updater == null) {
                                updater = new AppUpdater(SplashActivity.this).setDisplay(Display.DIALOG);
                                updater.setContentOnUpdateAvailable("Update " + update.getLatestVersion() + " is available to download. Download the latest version to get latest" + "features, improvements and bug fixes.");
                                updater.setButtonDoNotShowAgain("");
                                updater.setButtonDismiss(" ");
                                updater.setCancelable(false);
                                updater.start();
                            } else {
                                updater.start();
                            }
                        } else {
//                            appSignatureHashHelper = new AppSignatureHashHelper(context);
                            pref = getApplicationContext().getSharedPreferences(AppConfig.SHARED_PREF, 0);
                            getToken();
                            try {
                                int SPLASH_TIME_OUT = 2000;
                                new Handler().postDelayed(() -> {
                                    if (PreferencesManager.getInstance(context).getfirst_visit()) {
                                        if (PreferencesManager.getInstance(context).getloginSkip())
                                            goToActivityWithFinish(SplashActivity.this, MobileLogin.class, null);
                                        else {
                                            goToActivityWithFinish(SplashActivity.this, Permission.class, null);
                                        }
                                    } else if (PreferencesManager.getInstance(context).getSavedLOGINID().length() != 0) {
                                        if (NetworkUtils.getConnectivityStatus(context) != 0) {
                                            getLogin();
                                        } else {
                                            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                                        }
                                    } else {
                                        LoggerUtil.logItem("Maintannce");
                                        goToActivityWithFinish(SplashActivity.this, MainContainer.class, null);
                                    }
                                }, SPLASH_TIME_OUT);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {
                        Log.d("AppUpdater Error", "Something went wrong");
                        progressBar.setVisibility(View.GONE);

                    }
                });
        appUpdaterUtils.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            checkUpdate();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
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

        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        Log.e("==Registration====>> ", msg);
        String url = BuildConfig.SMS_URL + mobile + "&msg=" + msg.replace(" ", "%20");
        LoggerUtil.logItem(url);
        Call<String> call = apiServices.getOtp(url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (progressBar != null)
                    progressBar.setVisibility(View.GONE);
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
//        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Fk_MemId", loginId);

        Call<JsonObject> call = apiServices.getUserSignout(object);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
//                hideLoading();
                if (response.body() != null && response.body().get("response").getAsString().equalsIgnoreCase("Success")) {
                    getLogin();
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

            }
        });
    }

    private void getToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivity.this, instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            Log.e("newToken", newToken);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("regId", newToken);
            editor.apply();
        });
    }

    public void getOTPPin(String msg, String mobile) {

        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        Log.e("==Registration====>> ", msg);
        String url = BuildConfig.SMS_URL + mobile + "&msg=" + msg.replace(" ", "%20");
        LoggerUtil.logItem(url);
        Call<String> call = apiServices.getOtp(url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (progressBar != null)
                    progressBar.setVisibility(View.GONE);
                showToast("An OTP has been sent on your registered mobile no *******" + mobile.substring(6) + ".");
                startSMSListener();
                new Handler().postDelayed(() -> confirmOtpPin(mobile), DELAY_TIME);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
//                hideLoading();
                showAlert("Something went wrong, Try again.",
                        R.color.red, R.drawable.error);
            }
        });
    }

    private void confirmOtpPin(String mobileno_st) {
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
                getOTPPin(msg, mobileno_st);
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
                // DO SOME THING
                alertDialog.dismiss();
//                dialogCreatePIN();
                goToActivityWithFinish(SplashActivity.this, MainContainer.class, null);

            } else {
                editTextConfirmOtp.setText("");
                editTextConfirmOtp.setError("Invalid OTP");
            }
        });
    }


}
