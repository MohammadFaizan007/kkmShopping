package com.digitalcashbag.common_activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.digitalcashbag.SMSReceiver;
import com.digitalcashbag.mlm.activities.MainContainerMLM;
import com.digitalcashbag.shopping.activities.MainContainer;
import com.digitalcashbag.shopping.activities.WebViewActivity;
import com.digitalcashbag.utilities.themepark.ThemePark;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.AppConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.ResponseLogin;
import kkm.com.core.model.response.ResponsePincodeDetail;
import kkm.com.core.model.response.ResponseReferalName;
import kkm.com.core.model.response.ResponseRegistration;
import kkm.com.core.model.response.referal.ResponseReferalCode;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class RegistrationActivity extends BaseActivity implements
        SMSReceiver.OTPReceiveListener {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_invite_code)
    TextInputEditText et_invite_code;
    @BindView(R.id.et_mob_no)
    TextInputEditText etMobNo;
    @BindView(R.id.input_layout_mob_no)
    TextInputLayout inputLayoutMobNo;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.et_email)
    TextInputEditText etEmail;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.signup_btn)
    Button signupBtn;
    @BindView(R.id.sign_up)
    TextView signUp;
    @BindView(R.id.login_now)
    TextView loginNow;
    @BindView(R.id.et_first_name)
    TextInputEditText etName;
    @BindView(R.id.input_name_lo)
    TextInputLayout inputNameLo;
    @BindView(R.id.tv_ref_name)
    TextView tv_ref_name;
    @BindView(R.id.input_pin_lo)
    TextInputLayout inputPinLo;
    @BindView(R.id.et_pincode)
    TextInputEditText etPincode;
    @BindView(R.id.et_lastname)
    TextInputEditText etLastname;
    @BindView(R.id.et_state)
    TextInputEditText etState;
    @BindView(R.id.input_state_lo)
    TextInputLayout inputStateLo;
    @BindView(R.id.et_city)
    TextInputEditText etCity;
    @BindView(R.id.input_city_lo)
    TextInputLayout inputCityLo;
    @BindView(R.id.et_tehsil)
    TextInputEditText etTehsil;
    @BindView(R.id.input_tehsil_lo)
    TextInputLayout inputTehsilLo;
    @BindView(R.id.pincodeProgress)
    ProgressBar pincodeProgress;
    @BindView(R.id.et_pan)
    TextInputEditText etPan;
    private String mobile = "";
    private TextView tv_resend_otp;
    private SharedPreferences pref;
    private String etName_st = "", referral_mob_no_st = "", mobileno_st = "", pswd_st = "", email_id_st = "";
    private EditText editTextConfirmOtp;
    private int DELAY_TIME = 2000;
    private String from;
    private String pin = "";
    private SMSReceiver smsReceiver;
    Bundle bundleN = new Bundle();

    @BindView(R.id.chkbx_terms)
    CheckBox chkbx_terms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.kkm_registration));

        if (getIntent().getBundleExtra(PAYLOAD_BUNDLE) != null) {
            from = getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("from");
            mobile = getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("mobile");
            etMobNo.setText(mobile);
            LoggerUtil.logItem(from);
        }

        pref = getApplicationContext().getSharedPreferences(AppConfig.SHARED_PREF, 0);

        et_invite_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 9) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0)
                        getReferralNameCode();
                    else
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                } else {
                    tv_ref_name.setText("");
                }
            }
        });

        etMobNo.addTextChangedListener(new TextWatcher() {
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
                }
            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    if (Utils.isEmailAddress(etEmail.getText().toString())) {
                        getEmailValid();
                    }
                }
            }
        });

        etPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0 && s.length() == 6) {
                    hideKeyboard();
                    getStateCityName(etPincode.getText().toString().trim());
                } else {
                    etCity.setText("");
                    etState.setText("");
                    etTehsil.setText("");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });


        // This code requires one time to get Hash keys do comment and share key
//        appSignatureHashHelper = new AppSignatureHashHelper(this);
//        Log.e("App Signature : ", appSignatureHashHelper.getAppSignatures().get(0));
        LoggerUtil.logItem("7PBvEkzm5sp");

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, pendingDynamicLinkData -> {
                    // Get deep link from result (may be null if no link is found)
                    Uri deepLink = null;
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.getLink();
                    }
                    //
                    // If the user isn't signed in and the pending Dynamic Link is
                    // an invitation, sign in the user anonymously, and record the
                    // referrer's UID.
                    //
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user == null
                            && deepLink != null
                            && deepLink.getBooleanQueryParameter("invitedby", false)) {
                        String referrerUid = deepLink.getQueryParameter("invitedby");
                        et_invite_code.setText(referrerUid);
                    }
                });

        chkbx_terms.setOnClickListener(v -> {
            //is chkIos checked?
            if (((CheckBox) v).isChecked()) {
                signupBtn.setVisibility(View.VISIBLE);
            } else
                signupBtn.setVisibility(View.GONE);

        });
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

    @Override
    public void onOTPReceived(String otp) {
        try {
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
//        showToast("OTP Time out");
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

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.side_menu, R.id.signup_btn, R.id.login_now, R.id.et_tehsil, R.id.tv_terms, R.id.tv_privacy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                if (from.equalsIgnoreCase("mobile")) {
                    goToActivityWithFinish(context, Permission.class, null);
                } else {
                    Intent intent3 = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent3);
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    finish();
                }
                break;
            case R.id.signup_btn:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        showAlert("Please wait....", R.color.green, R.drawable.alerter_ic_notifications);
                        pin = generatePin();
                        String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
                        getOTP(msg, mobileno_st);
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                }
                break;
            case R.id.login_now:
                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                finish();
                break;

            case R.id.tv_terms:
                bundleN.putString("from", "Main");
                bundleN.putString("link", "https://cashbag.co.in/Home/TermAndConditions");
                goToActivity(context, WebViewActivity.class, bundleN);
                break;
            case R.id.tv_privacy:
                bundleN.putString("from", "Main");
                bundleN.putString("link", "https://cashbag.co.in/Home/PrivacyandPolicys");
                goToActivity(context, WebViewActivity.class, bundleN);
                break;
        }
    }

    private boolean Validation() {
        try {
            referral_mob_no_st = et_invite_code.getText().toString().trim();
            mobileno_st = etMobNo.getText().toString().trim();
            pswd_st = etPassword.getText().toString().trim();
            email_id_st = etEmail.getText().toString().trim();
            etName_st = etName.getText().toString().trim();

            if (etName_st.length() == 0) {
                showError("Enter First Name", etName);
                return false;
            } else if (etLastname.getText().toString().trim().length() == 0) {
                showError("Enter last name", etLastname);
                return false;
            } else if (tv_ref_name.getText().toString().length() == 0) {
                showError(getResources().getString(R.string.referral_mob_no_err), et_invite_code);
                return false;
            } else if (mobileno_st.length() == 0 || mobileno_st.length() != 10) {
                showError(getResources().getString(R.string.valid_mob_no_err), etMobNo);
                return false;
            } else if (pswd_st.length() < 6) {
                showError(getResources().getString(R.string.enter_pswd), etPassword);
                return false;
            } else if (email_id_st.equalsIgnoreCase("")) {
                showError(getResources().getString(R.string.email_id_err), etEmail);
                return false;
            } else if (!Utils.isEmailAddress(email_id_st)) {
                showError(getResources().getString(R.string.email_id_err), etEmail);
                return false;
            } else if (etPan.getText().toString().trim().length() > 0 && !Utils.validatePan(etPan.getText().toString().trim())) {
                showError("Invalid PAN number", etPan);
                return false;
            } else if (etPincode.getText().toString().length() < 6) {
                showError("Enter valid pin code.", etMobNo);
                return false;
            } else if (etState.getText().toString().trim().length() == 0) {
                etState.setError("Please enter a valid state");
                etState.requestFocus();
                return false;
            } else if (etCity.getText().toString().trim().length() == 0) {
                etCity.setError("Please enter a valid city");
                etCity.requestFocus();
                return false;
            } else if (etTehsil.getText().toString().trim().length() == 0) {
                etTehsil.setError("Please select tehsil");
                etTehsil.requestFocus();
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

    private void getRegistration() {
//
//        {
//                "FName": "Vivek",
//                "LName": "Verma",
//                "MobileNo": "",
//                "InvitedBy": "",
//                "Password": "",
//                "Email": "",
//                "State": "",
//                "CreatedBy": "",
//                "State": "UP",
//                "City": "Lucknow",
//                "Tehsil": "",
//        }
//
//        showLoading();
        JsonObject param = new JsonObject();
        param.addProperty("MobileNo", mobileno_st);
        param.addProperty("InvitedBy", referral_mob_no_st);
        param.addProperty("Password", pswd_st);
        param.addProperty("Email", email_id_st);
        param.addProperty("CreatedBy", "100");
        param.addProperty("FName", etName_st);
        param.addProperty("LName", etLastname.getText().toString().trim());
        param.addProperty("Pincode", etPincode.getText().toString());
        param.addProperty("State", etState.getText().toString());
        param.addProperty("City", etCity.getText().toString());
        param.addProperty("Tehsil", etTehsil.getText().toString());
//        param.addProperty("PANNO", etPan.getText().toString().trim());
        LoggerUtil.logItem(param);

        Call<ResponseRegistration> registrationCall = apiServices.getRegistration(param);
        registrationCall.enqueue(new Callback<ResponseRegistration>() {
            @Override
            public void onResponse(@NonNull Call<ResponseRegistration> call, @NonNull Response<ResponseRegistration> response) {
//                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        showSuccess();
                        String msg = "Dear " + etName_st + " you have successfully registered with CASHBAG, your login id- "
                                + mobileno_st + " and password-" + pswd_st + " and Trans. Password -"
                                + pswd_st + ". Login http://cashbag.co.in/";
                        sendCredentials(msg, mobileno_st);
                    } else {
                        showAlert(response.body().getMessage(), R.color.red, R.drawable.error);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseRegistration> call, @NonNull Throwable t) {
//                hideLoading();
            }
        });
    }

    private void showSuccess() {
        Dialog error_dialog = new Dialog(this);
        error_dialog.setCanceledOnTouchOutside(false);
        error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        error_dialog.setContentView(R.layout.error_dialoge);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        error_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView error_text = error_dialog.findViewById(R.id.error_text);
        Button ok_btn = error_dialog.findViewById(R.id.ok_btn);
        error_text.setText(String.format("Account created successfully.\nPlease Login using these credentials :\nID : %s\nPassword : %s", etMobNo.getText().toString().trim(), etPassword.getText().toString().trim()));

        error_dialog.show();
        ok_btn.setOnClickListener(view -> {
            error_dialog.dismiss();
            getLogin(etMobNo.getText().toString().trim(), etPassword.getText().toString().trim());
        });
    }

    private void getLogin(String id, String pswd) {

        showLoading();
        JsonObject param = new JsonObject();
        param.addProperty("LoginID", id);
        param.addProperty("Password", pswd);
        param.addProperty("DeviceId", pref.getString("regId", ""));
        param.addProperty("AndroidId", PreferencesManager.getInstance(context).getANDROIDID());

        Call<ResponseLogin> loginCall = apiServices.getLogin(param);
        Log.e("Splash ", PreferencesManager.getInstance(context).getSavedLOGINID());
        LoggerUtil.logItem(param);
        loginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLogin> call, @NonNull Response<ResponseLogin> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                        allowLogin(response, id, pswd);
                    } else {
                        PreferencesManager.getInstance(context).clear();
                        Intent intent1 = new Intent(context, LoginActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);
                        finish();
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

    private void allowLogin(Response<ResponseLogin> response, String mobileNo_st, String password_st) {
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

        PreferencesManager.getInstance(context).setENTITY_ID(response.body().getAuthonticate().getEntityId());
        PreferencesManager.getInstance(context).setKIT_NO(response.body().getAuthonticate().getKitno());
        PreferencesManager.getInstance(context).setM2PStatus(response.body().getAuthonticate().getM2pStatus());

        PreferencesManager.getInstance(context).setSavedLOGINID(mobileNo_st);
        PreferencesManager.getInstance(context).setSavedPASSWORD(password_st);
        PreferencesManager.getInstance(context).setSPOID(response.body().getAuthonticate().getPbid());
        PreferencesManager.getInstance(context).setSPO_USERNAME(response.body().getAuthonticate().getPbUserName());
        PreferencesManager.getInstance(context).setSPO_PASSWORD(response.body().getAuthonticate().getPbPassword());

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

    @Override
    public void onBackPressed() {
        if (from.equalsIgnoreCase("mobile")) {
            goToActivityWithFinish(context, Permission.class, null);
        } else if (from.equalsIgnoreCase("login")) {
            Intent intent3 = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent3);
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    public void getOTP(String msg, String mobile) {
        showLoading();
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
                getOTP(msg, mobileno_st);
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }
        });

        buttonCncel.setOnClickListener(v -> alertDialog.dismiss());

        buttonConfirm.setOnClickListener(view -> {
            if (TextUtils.isEmpty(editTextConfirmOtp.getText().toString())) {
                editTextConfirmOtp.setText("");
                editTextConfirmOtp.setError("Invalid OTP");
            } else if (editTextConfirmOtp.getText().toString().length() < 4) {
                editTextConfirmOtp.setError("Invalid OTP");
            } else if (editTextConfirmOtp.getText().toString().equalsIgnoreCase(pin)) {
                showAlert("Please wait....", R.color.green, R.drawable.alerter_ic_notifications);
                getRegistration();
                alertDialog.dismiss();
            } else {
                editTextConfirmOtp.setText("");
                editTextConfirmOtp.setError("Invalid OTP");
            }
        });

        //Displaying the alert dialog
        alertDialog.show();


    }

    private void sendCredentials(String msg, String mobile) {
        String url_base = BuildConfig.SMS_URL + mobile + "&msg=" + msg;
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        Log.e("==Registration====>> ", msg);
        String url = url_base + mobile + "&msg=" + msg.replace(" ", "%20");
        LoggerUtil.logItem(url);
        Call<String> call = apiServices.getOtp(url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                hideLoading();
                showAlert("Your login details has been send to your registered mobile no *******" + mobile.substring(6) + ".",
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

    void getReferralNameCode() {
//        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("InviteCode", et_invite_code.getText().toString().trim());

        Call<ResponseReferalCode> call = apiServices.getReferalNameFronCode(object);
        call.enqueue(new Callback<ResponseReferalCode>() {
            @Override
            public void onResponse(@NonNull Call<ResponseReferalCode> call, @NonNull Response<ResponseReferalCode> response) {
//                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    tv_ref_name.setText(response.body().getReferalName());
                } else {
                    et_invite_code.setError("Invalid invite code.");
                    tv_ref_name.setText("");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseReferalCode> call, @NonNull Throwable t) {
//                hideLoading();
            }
        });


    }

    private void getStateCityName(String pincode) {
        pincodeProgress.setVisibility(View.VISIBLE);
        String url = BuildConfig.PINCODEURL + pincode;
        LoggerUtil.logItem(url);
        Call<ResponsePincodeDetail> getCity = apiServices.getStateCity(url);
        getCity.enqueue(new Callback<ResponsePincodeDetail>() {
            @Override
            public void onResponse(@NonNull Call<ResponsePincodeDetail> call, @NonNull Response<ResponsePincodeDetail> response) {
                LoggerUtil.logItem(response.body());
                pincodeProgress.setVisibility(View.GONE);
                hideLoading();
                if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                    etCity.setText(response.body().getCityName());
                    etState.setText(response.body().getStateName());
                    etTehsil.setText(response.body().getTaluk());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponsePincodeDetail> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    void getMobileValid() {
        JsonObject object = new JsonObject();
        object.addProperty("RefMobile", etMobNo.getText().toString().trim());
        object.addProperty("email", "");

        Call<ResponseReferalName> call = apiServices.getReferalName(object);
        call.enqueue(new Callback<ResponseReferalName>() {
            @Override
            public void onResponse(@NonNull Call<ResponseReferalName> call, @NonNull Response<ResponseReferalName> response) {
                LoggerUtil.logItem(response.body());
                hideLoading();
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    etMobNo.setError("Mobile no already exist");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseReferalName> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    void getEmailValid() {
        JsonObject object = new JsonObject();
        object.addProperty("RefMobile", "");
        object.addProperty("Email", etEmail.getText().toString().trim());
        LoggerUtil.logItem(object);
        Call<ResponseReferalName> call = apiServices.getReferalName(object);
        call.enqueue(new Callback<ResponseReferalName>() {
            @Override
            public void onResponse(@NonNull Call<ResponseReferalName> call, @NonNull Response<ResponseReferalName> response) {
                LoggerUtil.logItem(response.body());
                hideLoading();
                try {
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                        etEmail.setError("Email already exist");
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

}
