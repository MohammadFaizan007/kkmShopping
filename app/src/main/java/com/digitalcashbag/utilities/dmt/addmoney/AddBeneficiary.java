package com.digitalcashbag.utilities.dmt.addmoney;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.digitalcashbag.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kkm.com.core.app.CheckErrorCode;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.request.utility.RequestBeneficiaryRegistration;
import kkm.com.core.model.request.utility.ResponseAddBeneficiaryDetails;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_TYPE_FOUR_DMT_BENEFICIARY_REGISTRATION;

public class AddBeneficiary extends BaseFragment {

    @BindView(R.id.et_beneficiary_name)
    EditText etBeneficiaryName;
    @BindView(R.id.et_account_number)
    EditText etAccountNumber;
    @BindView(R.id.et_ifsc_code)
    EditText etIfscCode;
    Unbinder unbinder;
    @BindView(R.id.btn_add_beneficiary)
    Button btnAddBeneficiary;
    @BindView(R.id.et_beneficiary_lname)
    EditText etBeneficiaryLname;
    @BindView(R.id.et_bank_name)
    EditText etBankName;
    @BindView(R.id.et_beneficiary_mobile)
    EditText etBeneficiaryMobile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_beneficiary, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

        LoggerUtil.logItem(PreferencesManager.getInstance(context).getREMITTER_ID());

        etIfscCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 11) {
                    getBankName(s.toString());
                } else {
                    etBankName.setText("");
                }
            }
        });
    }

    private void getBankName(String ifscCode) {

        String url = "https://ifsc.razorpay.com/" + ifscCode;
        LoggerUtil.logItem(url);
        Call<JsonObject> getCity = apiServices.getBankName(url);
        getCity.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                LoggerUtil.logItem(response.body());
                if (response.body() != null) {
//                    Log.e("Response", response.body().toString());
                    try {
                        etBankName.setText(response.body().get("BANK").getAsString());
                        etBankName.setHint("");
//                        branchEt.setText(response.body().get("BRANCH").getAsString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        etBankName.setText("");
                        etBankName.setHint("Not found!");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_add_beneficiary)
    public void onViewClicked() {
        if (Validate()) {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getBeneficiaryRegistration();
//                getBeneficiaryAccountVerification();
            } else {
                showMessage(getResources().getString(R.string.alert_internet));
            }
        }
    }

    private boolean Validate() {
        if (etBeneficiaryMobile.getText().toString().trim().length() < 10) {
            etBeneficiaryMobile.setError("Invalid Mobile Number");
            return false;
        } else if (etBeneficiaryName.getText().toString().trim().length() < 3) {
            etBeneficiaryName.setError("Enter Beneficiary Name");
            return false;
        } else if (etBeneficiaryLname.getText().toString().trim().length() < 3) {
            etBeneficiaryLname.setError("Enter Beneficiary Last Name");
            return false;
        } else if (etAccountNumber.getText().toString().trim().length() < 9) {
            etAccountNumber.setError("Enter Valid Bank Account Number");
            return false;
        } else if (etIfscCode.getText().toString().trim().length() < 11) {
            etIfscCode.setError("Enter Valid IFSC Code");
            return false;
        } /*else if (etBankName.getText().toString().equalsIgnoreCase("")) {
            etIfscCode.setError("Enter Valid IFSC Code");
            return false;
        }*/
        return true;
    }

    private void getBeneficiaryRegistration() {

//        {
//                "Type": "4",
//                "NUMBER": "7784084103",
//                "AMOUNT": "1.00",
//                "AMOUNT_ALL": "1.00",
//                "remId": "2441357",
//                "lName": "Hashmi",
//                "fName": "Azeem",
//                "benAccount": "15842193000023",
//                "benIFSC": "OBRC0101584"
//        }
        showLoading();
        RequestBeneficiaryRegistration requestObject = new RequestBeneficiaryRegistration();
        requestObject.setType(PAYLOAD_TYPE_FOUR_DMT_BENEFICIARY_REGISTRATION);
        requestObject.setNUMBER(etBeneficiaryMobile.getText().toString());
        requestObject.setRemId(PreferencesManager.getInstance(context).getREMITTER_ID());
        requestObject.setFName(etBeneficiaryName.getText().toString());
        requestObject.setLName(etBeneficiaryLname.getText().toString());
        requestObject.setAMOUNT("1.0");
        requestObject.setAMOUNTALL("1.0");
        requestObject.setBenAccount(etAccountNumber.getText().toString());
        requestObject.setBenIFSC(etIfscCode.getText().toString());

        LoggerUtil.logItem(requestObject);

        Call<JsonArray> objectCall = apiServices_utility.getBeneficiaryRegistration(requestObject);
        objectCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().size() > 0) {
                    JsonObject object = response.body().get(0).getAsJsonObject();
                    if (object.get("error").getAsString().equalsIgnoreCase("0") &&
                            object.get("result").getAsString().equalsIgnoreCase("0")) {
                        try {
                            JSONObject addinfo = new JSONObject((Utils.replaceBackSlash(object.get("addinfo").getAsString())));
                            JSONObject data = addinfo.getJSONObject("data");
                            JSONObject beneficiary = data.getJSONObject("beneficiary");
//                        confirmOtp(beneficiary.getString("id"));
                            getAddBeneficiaryDetilsLog(beneficiary.getString("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        CheckErrorCode checkErrorCode = new CheckErrorCode();
                        checkErrorCode.isValidTransaction(context, object.get("error").getAsString());
                    }
                } else {
                    showAlert("Something went wrong.", R.color.red, R.drawable.alerter_ic_notifications);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

//    private void getBeneficiaryOTPvalidation(String benId, String otc) {
////        {
////            "Type": "2",
////                "NUMBER": "9821680560",
////                "benId": "3877631",
////                "otc": "961033",
////                "remId": "2972582",
////                "AMOUNT": "10.00",
////                "AMOUNT_ALL": "1.00"
////        }
//        showLoading();
//        ApiServices apiServices = ServiceGenerator.createServiceUtility(ApiServices.class);
//        RequestBeneficiaryOTPvalidation requestObject = new RequestBeneficiaryOTPvalidation();
//        requestObject.setAMOUNT("1.0");
//        requestObject.setAMOUNTALL("1.0");
//        requestObject.setBenId(benId);
//        requestObject.setNUMBER(PreferencesManager.getInstance(context).getMOBILE());
//        requestObject.setRemId(PreferencesManager.getInstance(context).getREMITTER_ID());
//        requestObject.setOtc(otc);
//        requestObject.setType(PAYLOAD_TYPE_TWO_DMT_BENEFICIARY_REGISTRATION_VALIDATE_OTP);
//
//        Call<JsonArray> call = apiServices.getBeneficiaryOTPvalidation(requestObject);
//        call.enqueue(new Callback<JsonArray>() {
//            @Override
//            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
//                hideLoading();
//                LoggerUtil.logItem(response.body());
//                JsonObject object = response.body().get(0).getAsJsonObject();
//                if (object.get("error").getAsString().equalsIgnoreCase("0") &&
//                        object.get("result").getAsString().equalsIgnoreCase("0")) {
//                    //TODO
//                    getBeneficiaryAccountVerification(benId);
//
//                } else {
//                    CheckErrorCode checkErrorCode = new CheckErrorCode();
//                    checkErrorCode.isValidTransaction(context, object.get("error").getAsString());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {
//                hideLoading();
//            }
//        });
//    }
//
//    private void getBeneficiaryResendOTPvalidation(String benId) {
//
////        {
////            "Type": "9",
////                "NUMBER": "9821680560",
////                "lName": "Mishra",
////                "fName": "Savita",
////                "benId": "3877631",
////                "remId": "2972582",
////                "AMOUNT": "1.00",
////                "AMOUNT_ALL": "1.00",
////                "benAccount": "9411193172",
////                "benIFSC": "KKBK0005028"
////        }
//
//        showLoading();
//        ApiServices apiServices = ServiceGenerator.createServiceUtility(ApiServices.class);
//        RequestBeneficiaryResendOTPvalidation requestObject = new RequestBeneficiaryResendOTPvalidation();
//        requestObject.setAMOUNT("1.0");
//        requestObject.setAMOUNTALL("1.0");
//        requestObject.setBenAccount(etAccountNumber.getText().toString());
//        requestObject.setBenId(benId);
//        requestObject.setBenIFSC(etIfscCode.getText().toString());
//        requestObject.setFName(etBeneficiaryName.getText().toString());
//        requestObject.setLName(etBeneficiaryLname.getText().toString());
//        requestObject.setNUMBER(PreferencesManager.getInstance(context).getMOBILE());
//        requestObject.setRemId(PreferencesManager.getInstance(context).getREMITTER_ID());
//        requestObject.setType(PAYLOAD_TYPE_NINE_DMT_BENEFICIARY_REGISTRATION_RESEND_OTP);
//
//        Call<JsonArray> call = apiServices.getBeneficiaryResendOTPvalidation(requestObject);
//        call.enqueue(new Callback<JsonArray>() {
//            @Override
//            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
//                hideLoading();
//                LoggerUtil.logItem(response.body());
//                JsonObject object = response.body().get(0).getAsJsonObject();
//                if (object.get("error").getAsString().equalsIgnoreCase("0") &&
//                        object.get("result").getAsString().equalsIgnoreCase("0")) {
//                    //TODO
//                    alertDialog.dismiss();
//                } else {
//                    CheckErrorCode checkErrorCode = new CheckErrorCode();
//                    checkErrorCode.isValidTransaction(context, object.get("error").getAsString());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {
//                hideLoading();
//            }
//        });
//    }
//
//    private void confirmOtp(String benId) {
//        //Creating a LayoutInflater object for the dialog box
//        LayoutInflater li = LayoutInflater.from(context);
//        //Creating a view to get the dialog box
//        View confirmDialog = li.inflate(R.layout.dialog_otp_verification, null);
//
//        //Initizliaing confirm button fo dialog box and edittext of dialog box
//        AppCompatButton buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
//        EditText editTextConfirmOtp = confirmDialog.findViewById(R.id.editTextOtp);
//        TextView tv_resend_otp = confirmDialog.findViewById(R.id.tv_resend_otp);
//        tv_resend_otp.setVisibility(View.VISIBLE);
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
//        alertDialog.setCanceledOnTouchOutside(true);
//
//        //Displaying the alert dialog
//        alertDialog.show();
//        buttonConfirm.setOnClickListener(view -> {
//            if (editTextConfirmOtp.getText().toString().trim().length() == 6) {
//                getBeneficiaryOTPvalidation(benId, editTextConfirmOtp.getText().toString());
//            } else
//                showMessage("Enter Valid OTP");
//        });
//
//        tv_resend_otp.setOnClickListener(view -> {
////            if (editTextConfirmOtp.getText().toString().trim().length() == 6) {
//            getBeneficiaryResendOTPvalidation(benId);
////            } else
////                showMessage("Enter Valid OTP");
//        });
//    }
//
//    private void getBeneficiaryAccountVerification(String benId) {
//
////        {
////            "Type": "10",
////                "NUMBER": "9918703130",
////                "AMOUNT": "1.00",
////                "AMOUNT_ALL": "1.00",
////                "benAccount": "31890100006215",
////                "benIFSC": "BARB0KURSIX"
////        }
//
//        showLoading();
//        ApiServices apiServices = ServiceGenerator.createServiceUtility(ApiServices.class);
//        RequestBeneficiaryAccountVerification verification = new RequestBeneficiaryAccountVerification();
//        verification.setAMOUNT("1.00");
//        verification.setAMOUNTALL("1.00");
//        verification.setNUMBER(PreferencesManager.getInstance(context).getMOBILE());
//        verification.setBenAccount(etAccountNumber.getText().toString());
//        verification.setBenIFSC(etIfscCode.getText().toString());
//        verification.setType(PAYLOAD_TYPE_TEN_DMT_BENEFICIARY_ACCOUNT_VERIFICATION);
//
//        Call<JsonArray> call = apiServices.getBeneficiaryAccountVerification(verification);
//        call.enqueue(new Callback<JsonArray>() {
//            @Override
//            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
//                hideLoading();
//                LoggerUtil.logItem(response.body());
//                JsonObject object = response.body().get(0).getAsJsonObject();
//                if (object.get("error").getAsString().equalsIgnoreCase("0") &&
//                        object.get("result").getAsString().equalsIgnoreCase("0")) {
//                    JSONObject addinfo = null;
//                    try {
//                        addinfo = new JSONObject((Utils.replaceBackSlash(object.get("addinfo").getAsString())));
//                        JSONObject data = addinfo.getJSONObject("data");
//                        if (data.getString("verification_status").equalsIgnoreCase("VERIFIED")) {
////                            showAlert("This account has already been verified under " + data.getString("benename"), R.color.red, R.drawable.alerter_ic_notifications);
//                            String name = etBeneficiaryName.getText().toString().trim() + " " + etBeneficiaryLname.getText().toString().trim();
//                            if (data.getString("benename").equalsIgnoreCase(name)) {
//                                getAddBeneficiaryDetilsLog(benId);
//                            } else {
//                                showAlert("Name does not matched with account details.", R.color.red, R.drawable.alerter_ic_notifications);
//                            }
//                        } else {
//                            showAlert("Invalid Account Number.", R.color.red, R.drawable.alerter_ic_notifications);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    CheckErrorCode checkErrorCode = new CheckErrorCode();
//                    checkErrorCode.isValidTransaction(context, object.get("error").getAsString());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {
//
//            }
//        });
//
//    }

    private void getAddBeneficiaryDetilsLog(String id) {
//        {
//            "BeneficiaryFirstName": "Vanita",
//                "BeneficiaryLastName": "Mishra",
//                "RemitterId": "2972582",
//                "BeneficiaryId": "3877631",
//                "BeneficiaryAccountNo": "31890100006215",
//                "BeneficiaryIFSC": "BARB0KURSIX",
//                "BeneficiaryBankName": "ICICI Bank"
//        }

//        {
//            "status": "0",
//                "msg": "Success"
//        }

//        showLoading();
        ResponseAddBeneficiaryDetails details = new ResponseAddBeneficiaryDetails();

        details.setBeneficiaryAccountNo(etAccountNumber.getText().toString());
        details.setBeneficiaryBankName(etBankName.getText().toString());
        details.setBeneficiaryFirstName(etBeneficiaryName.getText().toString());
        details.setBeneficiaryLastName(etBeneficiaryLname.getText().toString());
        details.setBeneficiaryId(id);
        details.setBeneficiaryIFSC(etIfscCode.getText().toString());
        details.setBeneficiaryMobileNo(etBeneficiaryMobile.getText().toString());
        details.setRemitterId(PreferencesManager.getInstance(context).getREMITTER_ID());

        LoggerUtil.logItem(details);

        Call<JsonObject> objectCall = apiServices_utility.getAddBeneficiaryDetils(details);
        objectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                try {
                    if (response.body().get("status").getAsString().equalsIgnoreCase("0")) {
                        showMessage("Beneficiary Added Successfully");
                        reset();
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

    private void reset() {
        etBeneficiaryName.setText("");
        etBeneficiaryLname.setText("");
        etAccountNumber.setText("");
        etBankName.setText("");
        etIfscCode.setText("");
        etBeneficiaryName.setText("");
        etBeneficiaryMobile.setText("");

    }

}
