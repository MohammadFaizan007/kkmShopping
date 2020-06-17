package com.digitalcashbag.common_activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
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

import com.digitalcashbag.shopping.activities.WebViewActivity;
import com.digitalcashbag.R;
import com.digitalcashbag.SMSReceiver;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.AppConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.model.request.RequestForgotPass;
import kkm.com.core.model.response.ResponseForgotPass;
import kkm.com.core.model.response.ResponseLogin;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.Dialog_dismiss;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.DialogUtil;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class FullScreenLogin extends DialogFragment implements
        SMSReceiver.OTPReceiveListener {

    public static String TAG = "FullScreenLoginDialog";
    public Dialog_dismiss dialog_dismiss;
    @BindView(R.id.et_reg_mob_no)
    TextInputEditText etRegMobNo;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.register)
    TextView register;
    Unbinder unbinder;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    private SharedPreferences pref;
    private int DELAY_TIME = 2000;
    //    AppSignatureHashHelper appSignatureHashHelper;
    private TextView tv_resend_otp;
    private EditText editTextConfirmOtp;
    private ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
    private String pin = "";
    private String mobileNo_st = "", password_st = "";
    private ProgressDialog mProgressDialog;
    private SMSReceiver smsReceiver;
//    private FirebaseAuth auth_user = FirebaseAuth.getInstance();

    @BindView(R.id.tv_terms)
    TextView tv_terms;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        pref = getActivity().getSharedPreferences(AppConfig.SHARED_PREF, 0);
//        appSignatureHashHelper = new AppSignatureHashHelper(getActivity());
//        Log.e("App Signature : ", BuildConfig.SMS_RETRIEVER);
//        LoggerUtil.logItem(BuildConfig.SMS_RETRIEVER);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the EditNameDialogListener so we can send events to the host
            dialog_dismiss = ((Dialog_dismiss) getActivity());
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString() + " must implement DialogDismiss");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            LoggerUtil.logItem("Dialog");
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardView.setCardBackgroundColor(Color.WHITE);

        String account = getColoredSpanned(getResources().getString(R.string.new_to_kkm), "#000000");
        String signup = getColoredSpanned("<b>" + getResources().getString(R.string.register_now) + "</b>", "#0281d5");
        register.setText(Html.fromHtml(account + " " + signup));
    }

    public void showAlert(String msg, int color, int icon) {
//        Alerter.create(getActivity()).setText(msg).setTextAppearance(kkm.com.core.R.style.alertTextColor).setBackgroundColorRes(color).setIcon(icon).show();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private String getColoredSpanned(String text, String color) {
        return "<font color=" + color + ">" + text + "</font>";
    }

    public void goToActivityWithFinish(Activity activity, Class<?> classActivity, Bundle bundle) {
        Intent intent = new Intent(getActivity(), classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        Utils.hideSoftKeyboard(activity);
        activity.startActivity(intent);
//        activity.finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.login_btn, R.id.forgot_password, R.id.register, R.id.tv_terms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(getActivity()) != 0) {
                        getLogin();
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
                bundle.putString("from", "fullscreen");
                bundle.putString("mobile", "");
                goToActivityWithFinish(getActivity(), RegistrationActivity.class, bundle);
                dismiss();
                break;

            case R.id.tv_terms:
                Bundle bundleN = new Bundle();
                bundleN.putString("from", "Main");
                bundleN.putString("link", "https://cashbag.co.in/Home/TermAndConditions");
                goToActivity(getActivity(), WebViewActivity.class, bundleN);
                break;
        }
    }

    public void goToActivity(Activity activity, Class<?> classActivity, Bundle bundle) {
        Utils.hideSoftKeyboard(activity);
        Intent intent = new Intent(activity, classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        activity.startActivity(intent);
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
        Dialog error_dialog = new Dialog(getActivity());
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
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void showLoading() {
        mProgressDialog = DialogUtil.showLoadingDialog(getActivity(), "Base Activity");
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void getLogin() {
        showLoading();
        JsonObject param = new JsonObject();
        param.addProperty("LoginID", mobileNo_st);
        param.addProperty("Password", password_st);
        param.addProperty("DeviceId", pref.getString("regId", ""));
        param.addProperty("AndroidId", PreferencesManager.getInstance(getActivity()).getANDROIDID());
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);

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
                        if (response.body().getMessage().contains("another device")) {
                            String message = response.body().getMessage();
                            String[] fkMemId = message.split(":");
                            LoggerUtil.logItem(fkMemId[0]);
                            LoggerUtil.logItem(fkMemId[1]);
                            LoggerUtil.logItem(fkMemId[2]);

                            showErrorNew("You are already active in some other mobile," +
                                    " Kindly logout from that device.", fkMemId[1], fkMemId[2]);
                        } else if (response.body() == null) {
                            goToActivityWithFinish(getActivity(), MaintenancePage.class, null);
                        } else if (response.body().getMessage().contains("website is under maintenance")) {
                            goToActivityWithFinish(getActivity(), MaintenancePage.class, null);
                        } else if (response.body().getMessage().contains("stop")) {
                            Bundle b = new Bundle();
                            b.putString("msg", response.body().getMessage());
                            goToActivityWithFinish(getActivity(), MaintenancePage.class, b);
                        } else if (response.body().getMessage().contains("not Valid")) {
                            showError("Mobile Number/Email or password is not valid.", etPassword);
                        }
                    }
                } catch (Exception | Error e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseLogin> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void openForgotPasswordDialog() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(getActivity());
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.forgot_pass_dialog, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        AppCompatButton button_getpassword = confirmDialog.findViewById(R.id.button_getpassword);
        EditText mobile_number = confirmDialog.findViewById(R.id.mobile_number);
        EditText user_id = confirmDialog.findViewById(R.id.user_id);

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

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
                } else*/
                {
                    if (mobileNo_st.length() == 0 || mobileNo_st.length() != 10) {
                        mobileNo_st = "";
                        showError(getResources().getString(R.string.valid_mob_no_err), mobile_number);
                    } else {
                        if (NetworkUtils.getConnectivityStatus(getActivity()) != 0) {
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
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        Call<ResponseForgotPass> responseForgotPassCall = apiServices.getForgotPassword(requestForgotPass);
        LoggerUtil.logItem(requestForgotPass);
        responseForgotPassCall.enqueue(new Callback<ResponseForgotPass>() {
            @Override
            public void onResponse(@NonNull Call<ResponseForgotPass> call, @NonNull Response<ResponseForgotPass> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                    showAlert(response.body().getMessage(), R.color.green, R.drawable.alerter_ic_notifications);
                    String msg = "Dear " + response.body().getName() + " you have successfully registered with CASHBAG, your login id- " + response.body().getMobileNo() + " and password-" + response.body().getPassword() + " and Trans. Password -" + response.body().getEwalletPassword() + ". Login http://cashbag.co.in/";
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
                showAlert("Your password details has been send to your registered mobile no *******" + mobile.substring(6) + ".", R.color.green, R.drawable.successful);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                hideLoading();
                showAlert("Something went wrong, Try again.", R.color.red, R.drawable.error);
            }
        });
    }

    public String generatePin() {
        Random random = new Random();
        @SuppressLint("DefaultLocale") String randomPIN = String.format("%04d", random.nextInt(10000));
        return randomPIN;
    }


    @Override
    public void onOTPReceived(String otp) {
        try {
//            showToast("OTP Received: " + otp);
            if (smsReceiver != null) {
                LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(smsReceiver);
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
    public void onDestroy() {
        super.onDestroy();
        if (smsReceiver != null) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(smsReceiver);
        }
    }

    private void startSMSListener() {
        try {
            smsReceiver = new SMSReceiver();
            smsReceiver.setOTPListener(this);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
            getActivity().registerReceiver(smsReceiver, intentFilter);

            SmsRetrieverClient client = SmsRetriever.getClient(getActivity());

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
        LayoutInflater li = LayoutInflater.from(getActivity());
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.otp_lay, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        AppCompatButton buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
        AppCompatButton buttonCncel = confirmDialog.findViewById(R.id.buttonCncel);
        tv_resend_otp = confirmDialog.findViewById(R.id.tv_resend_otp);
        editTextConfirmOtp = confirmDialog.findViewById(R.id.editTextOtp);


        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        tv_resend_otp.setOnClickListener(v -> {
            if (NetworkUtils.getConnectivityStatus(getActivity()) != 0) {
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
                alertDialog.dismiss();
                otpVerifiedLogin();
            } else {
                editTextConfirmOtp.setText("");
                editTextConfirmOtp.setError("Invalid OTP");
            }
        });
    }

    private void otpVerifiedLogin() {
        showLoading();
        JsonObject param = new JsonObject();
        param.addProperty("LoginID", mobileNo_st);
        param.addProperty("Password", password_st);
        param.addProperty("DeviceId", pref.getString("regId", ""));
        param.addProperty("AndroidId", PreferencesManager.getInstance(getActivity()).getANDROIDID());
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
                                    " Kindly logout from that device.", etPassword);
                        } else if (response.body().getMessage().contains("not Valid")) {
                            showError("Mobile Number/Email or password is not valid.", etPassword);
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
        hideLoading();
        LoggerUtil.logItem(response.body());
        if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
            PreferencesManager.getInstance(getActivity()).setUSERID(response.body().getAuthonticate().getFkMemID());
            PreferencesManager.getInstance(getActivity()).setLoginID(response.body().getAuthonticate().getLoginId());
            PreferencesManager.getInstance(getActivity()).setMOBILE(response.body().getAuthonticate().getMobile());
            PreferencesManager.getInstance(getActivity()).setNAME(response.body().getAuthonticate().getFirstName());
            PreferencesManager.getInstance(getActivity()).setLNAME(response.body().getAuthonticate().getLastName());
            PreferencesManager.getInstance(getActivity()).setTransactionPass(response.body().getAuthonticate().getEwalletPassword());
            PreferencesManager.getInstance(getActivity()).setPINCODE(response.body().getAuthonticate().getPinCode());
            PreferencesManager.getInstance(getActivity()).setEMAIL(response.body().getAuthonticate().getEmailID());
            PreferencesManager.getInstance(getActivity()).setIsKycVerified(response.body().getAuthonticate().getKycStatus());
            PreferencesManager.getInstance(getActivity()).setLoginPin(response.body().getAuthonticate().getGeneratePin());
            PreferencesManager.getInstance(getActivity()).setLastLogin(response.body().getAuthonticate().getLastLoginDate());
            PreferencesManager.getInstance(getActivity()).setInviteCode(response.body().getAuthonticate().getInviteCode());
            PreferencesManager.getInstance(getActivity()).setPROFILEPIC(response.body().getAuthonticate().getProfilepic());
            PreferencesManager.getInstance(getActivity()).setDOB(response.body().getAuthonticate().getDob());
            PreferencesManager.getInstance(getActivity()).setDOB_IMAGE(response.body().getBithdayUrl());

            PreferencesManager.getInstance(getActivity()).setENTITY_ID(response.body().getAuthonticate().getEntityId());
            PreferencesManager.getInstance(getActivity()).setKIT_NO(response.body().getAuthonticate().getKitno());
            PreferencesManager.getInstance(getActivity()).setM2PStatus(response.body().getAuthonticate().getM2pStatus());

            PreferencesManager.getInstance(getActivity()).setSPO_USERNAME(response.body().getAuthonticate().getPbUserName());
            PreferencesManager.getInstance(getActivity()).setSPO_PASSWORD(response.body().getAuthonticate().getPbPassword());

            PreferencesManager.getInstance(getActivity()).setSavedLOGINID(mobileNo_st);
            PreferencesManager.getInstance(getActivity()).setSavedPASSWORD(password_st);

            PreferencesManager.getInstance(getActivity()).setSPOID(response.body().getAuthonticate().getPbid());

            dialog_dismiss.onDismiss();
            dismiss();

        } else {
            showAlert(response.body().getMessage(), R.color.red, R.drawable.error);
        }
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


    private void showErrorNew(String error_st, String loginId, String Mobile) {
        Dialog error_dialog = new Dialog(getActivity());
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
        logout_all.setOnClickListener(v -> {
            pin = generatePin();
            error_dialog.dismiss();
            String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
            getOTPNew(msg, Mobile, loginId);
        });
        error_text.setText(error_st);
        error_dialog.show();
        ok_btn.setOnClickListener(view -> {
            PreferencesManager.getInstance(getActivity()).clear();
            Intent intent1 = new Intent(getActivity(), LoginActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent1);
            error_dialog.dismiss();
            dismiss();
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
        LayoutInflater li = LayoutInflater.from(getActivity());
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.otp_lay, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        AppCompatButton buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
        AppCompatButton buttonCncel = confirmDialog.findViewById(R.id.buttonCncel);
        tv_resend_otp = confirmDialog.findViewById(R.id.tv_resend_otp);
        editTextConfirmOtp = confirmDialog.findViewById(R.id.editTextOtp);


        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        tv_resend_otp.setOnClickListener(v -> {
            if (NetworkUtils.getConnectivityStatus(getActivity()) != 0) {
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
                alertDialog.dismiss();
                getSignout(loginId);

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
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                assert response.body() != null;
                if (response.body().get("response").getAsString().equalsIgnoreCase("Success")) {

                    getLogin();
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

            }
        });
    }
}
