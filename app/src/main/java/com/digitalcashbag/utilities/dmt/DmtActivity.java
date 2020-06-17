package com.digitalcashbag.utilities.dmt;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.utilities.dmt.addmoney.DmtRemitterFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.RequestCustomerVerification;
import kkm.com.core.model.request.utility.RequestReitterOtpValidate;
import kkm.com.core.model.request.utility.RequestRemitterRegister;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_TYPE_FIVE_DMT_CUSTOMER_DETAILS;
import static kkm.com.core.app.AppConfig.PAYLOAD_TYPE_TWENTY_FOUR_DMT_BENEFICIARY_UPDATE;
import static kkm.com.core.app.AppConfig.PAYLOAD_TYPE_ZERO_DMT_CUSTOMER_REGISTRATION;

public class DmtActivity extends BaseActivity {


    public Fragment currentFragment;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    private EditText editTextConfirmOtp;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dmt_activity_lay);
        ButterKnife.bind(this);
        sideMenu.setOnClickListener(v -> onBackPressed());
        title.setText("DMT");


        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getCustomerVerification();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    public void getCustomerVerification() {
        showLoading();

        RequestCustomerVerification requestCustomerVerification = new RequestCustomerVerification();
        requestCustomerVerification.setType(PAYLOAD_TYPE_FIVE_DMT_CUSTOMER_DETAILS);
        requestCustomerVerification.setNUMBER(PreferencesManager.getInstance(context).getMOBILE());
        requestCustomerVerification.setAmount("1.00");
        requestCustomerVerification.setAMOUNTALL("1.00");
        LoggerUtil.logItem(requestCustomerVerification);

        Call<JsonArray> objectCall = apiServices_utility.getCustomerVerification(requestCustomerVerification);
        objectCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
                try {
                    hideLoading();
                    LoggerUtil.logItem("customerVerification");
                    LoggerUtil.logItem(response.body());
                    JsonObject jobj = response.body().get(0).getAsJsonObject();
                    if ((jobj.get("error").getAsString()).equalsIgnoreCase("0") &&
                            (jobj.get("result").getAsString()).equalsIgnoreCase("0")) {
                        JSONObject addinfo = new JSONObject((Utils.replaceBackSlash(jobj.get("addinfo").getAsString())));
                        JSONObject data = addinfo.getJSONObject("data").getJSONObject("remitter");
                        LoggerUtil.logItem(data);
                        if (data.getString("is_verified").equalsIgnoreCase("1")) {
                            PreferencesManager.getInstance(context).setREMITTER_ID(data.getString("id"));
                            replaceFragmentWithHome(new DmtRemitterFragment(), "DMT");
                        } else {
                            confirmOtp(data.getString("id"));
                        }
                    } else {
                        getRemitterRegister();
                    }
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void getRemitterRegister() {

        showLoading();

        RequestRemitterRegister remitterRegister = new RequestRemitterRegister();
        remitterRegister.setType(PAYLOAD_TYPE_ZERO_DMT_CUSTOMER_REGISTRATION);
        remitterRegister.setNUMBER(PreferencesManager.getInstance(context).getMOBILE());
        remitterRegister.setFName(PreferencesManager.getInstance(context).getNAME());
        remitterRegister.setLName(PreferencesManager.getInstance(context).getLNAME());
        remitterRegister.setPin(PreferencesManager.getInstance(context).getPINCODE());
        remitterRegister.setAMOUNT("1.00");
        remitterRegister.setAMOUNTALL("1.00");
        LoggerUtil.logItem(remitterRegister);
        Call<JsonArray> objectCall = apiServices_utility.getRemitterRegistration(remitterRegister);
        objectCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
                try {
                    hideLoading();
                    LoggerUtil.logItem("getRemitterRegister");
                    LoggerUtil.logItem(response.body());
                    JsonObject jobj = response.body().get(0).getAsJsonObject();
                    if ((jobj.get("error").getAsString()).equalsIgnoreCase("0") && (jobj.get("result").getAsString()).equalsIgnoreCase("0")) {
                        JSONObject addinfo = new JSONObject((Utils.replaceBackSlash(jobj.get("addinfo").getAsString())));
                        JSONObject data = addinfo.getJSONObject("data").getJSONObject("remitter");
                        confirmOtp(data.getString("id"));
                    }
                } catch (Error | Exception e) {
                    hideLoading();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void confirmOtp(String remitterId) {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(context);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_otp_verification, null);
        //Initizliaing confirm button fo dialog box and edittext of dialog box
        Button buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
        Button btn_cancel = confirmDialog.findViewById(R.id.btn_cancel);
        editTextConfirmOtp = confirmDialog.findViewById(R.id.editTextOtp);
        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);
        //Creating an alert dialog
        alertDialog = alert.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        //Displaying the alert dialog
        alertDialog.show();
        btn_cancel.setOnClickListener(v -> {
            hideKeyboard();
            alertDialog.dismiss();
        });
        buttonConfirm.setOnClickListener(view -> {
            if (TextUtils.isEmpty(editTextConfirmOtp.getText().toString().trim())) {
                editTextConfirmOtp.setText("");
                editTextConfirmOtp.setError("Invalid OTP");
            } else if (editTextConfirmOtp.getText().toString().trim().length() < 4) {
                editTextConfirmOtp.setError("Invalid OTP");
            } else {
                hideKeyboard();
                getRemitterRegistrationValidationOTP(remitterId, editTextConfirmOtp.getText().toString().trim());
            }
        });
    }

    private void getRemitterRegistrationValidationOTP(String remId, String otp) {
        RequestReitterOtpValidate remitterRegister = new RequestReitterOtpValidate();
        remitterRegister.setType(PAYLOAD_TYPE_TWENTY_FOUR_DMT_BENEFICIARY_UPDATE);
        remitterRegister.setNUMBER(PreferencesManager.getInstance(context).getMOBILE());
        remitterRegister.setRemId(remId);
        remitterRegister.setOtc(otp);
        remitterRegister.setAMOUNT("1.0");
        remitterRegister.setAMOUNTALL("1.0");

        Call<JsonArray> objectCall = apiServices_utility.getRemitterRegistrationValidationOTP(remitterRegister);
        objectCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
                try {
                    LoggerUtil.logItem(response.body());
                    JsonObject jobj = response.body().get(0).getAsJsonObject();
                    if ((jobj.get("error").getAsString()).equalsIgnoreCase("0") && (jobj.get("result").getAsString()).equalsIgnoreCase("0")) {
                        alertDialog.dismiss();
                        PreferencesManager.getInstance(context).setREMITTER_ID(remId);
                        replaceFragmentWithHome(new DmtRemitterFragment(), "DMT");
                    } else {
                        editTextConfirmOtp.setText("");
                        editTextConfirmOtp.setError("Invalid OTP");
                    }
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        LoggerUtil.logItem(fm.getBackStackEntryCount());
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }

    public void replaceFragmentWithHome(Fragment setFragment, String title) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, setFragment, title);
        transaction.commit();
    }

    public void ReplaceFragmentAddBack(Fragment setFragment, String title) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().addToBackStack(title);
        transaction.replace(R.id.frame, setFragment, title);
        transaction.commit();
    }

}
