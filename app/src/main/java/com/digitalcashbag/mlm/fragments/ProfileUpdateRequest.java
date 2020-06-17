package com.digitalcashbag.mlm.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.digitalcashbag.SMSReceiver;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.request.profilemlm.RequestProfileUpdate;
import kkm.com.core.model.response.ResponsePincodeDetail;
import kkm.com.core.model.response.updateProfile.ResponseGetProfile;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.FileUtils;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ProfileUpdateRequest extends BaseFragment implements IPickCancel, IPickResult, SMSReceiver.OTPReceiveListener {

    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    @BindView(R.id.et_pincode)
    EditText etPincode;
    @BindView(R.id.et_state)
    EditText etState;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.et_tehsil)
    EditText etTehsil;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.chk_terms)
    CheckBox chkTerms;
    @BindView(R.id.text_submit_update)
    TextView textSubmitUpdate;
    Unbinder unbinder;

    @BindView(R.id.documentPreview)
    ImageView documentPreview;
    @BindView(R.id.selectDocument)
    Button selectDocument;

    private PickImageDialog dialog;
    private File documentFile;
    private TextView tv_resend_otp;
    private EditText editTextConfirmOtp;
    private int DELAY_TIME = 2000;
    private String pin = "";
    private SMSReceiver smsReceiver;
    private String iamge = "";
    private String mobileno_st = PreferencesManager.getInstance(context).getMOBILE();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_update_request, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {


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

        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getProfileData();

    }

    private void getStateCityName(String pincode) {
        showLoading();
        String url = BuildConfig.PINCODEURL + pincode;
        LoggerUtil.logItem(url);
        Call<ResponsePincodeDetail> getCity = apiServices.getStateCity(url);
        getCity.enqueue(new Callback<ResponsePincodeDetail>() {
            @Override
            public void onResponse(@NonNull Call<ResponsePincodeDetail> call, @NonNull Response<ResponsePincodeDetail> response) {
                LoggerUtil.logItem(response.body());
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.selectDocument, R.id.et_tehsil, R.id.text_submit_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.selectDocument:
                showDialog();
                break;
            case R.id.text_submit_update:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        if (!etMobile.getText().toString().equalsIgnoreCase(PreferencesManager.getInstance(context).getMOBILE())) {
                            showAlert("Please wait....", R.color.green, R.drawable.alerter_ic_notifications);
                            pin = generatePin();
                            String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
                            getOTP(msg, mobileno_st);
                        } else {
                            updateProfile();
                        }
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                }
                break;
        }
    }

    private void disableEditText(EditText edt) {
        edt.setFocusable(false);
        edt.setFocusableInTouchMode(false);
        edt.setClickable(false);
        edt.setCursorVisible(false);
        edt.setEnabled(false);
        Utils.hideSoftKeyboard(context);
    }

    private boolean Validation() {

        LoggerUtil.logItem("Length " + PreferencesManager.getInstance(context).getPINCODE() + "  " + PreferencesManager.getInstance(context).getLNAME() + "  " + PreferencesManager.getInstance(context).getNAME()
                + "  " + PreferencesManager.getInstance(context).getAddress());
        if (etFirstName.getText().toString().length() == 0) {
            showError("Enter First Name", etFirstName);
            return false;
        } else if (etMobile.getText().toString().length() == 0 || etMobile.getText().toString().length() != 10) {
            showError(getResources().getString(R.string.valid_mob_no_err), etMobile);
            return false;
        } else if (etEmail.getText().toString().equalsIgnoreCase("")) {
            showError(getResources().getString(R.string.email_id_err), etEmail);
            return false;
        } else if (!Utils.isEmailAddress(etEmail.getText().toString())) {
            showError(getResources().getString(R.string.email_id_err), etEmail);
            return false;
        } else if (etPincode.getText().toString().length() < 6) {
            showError("Enter valid pin code.", etPincode);
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
        } else if (!etPincode.getText().toString().trim().equalsIgnoreCase(PreferencesManager.getInstance(context).getPINCODE().trim())
                || !etFirstName.getText().toString().trim().equalsIgnoreCase(PreferencesManager.getInstance(context).getNAME().trim())
                || !etLastName.getText().toString().trim().equalsIgnoreCase(PreferencesManager.getInstance(context).getLNAME().trim())
                || !etAddress.getText().toString().trim().equalsIgnoreCase(PreferencesManager.getInstance(context).getAddress().trim())) {
            if (iamge.length() == 0) {
                showMessage("Select Image");
                return false;
            }
        }
        return true;
    }

    private void showError(String error_st, EditText editText) {
        Dialog error_dialog = new Dialog(context);
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
            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    void showDialog() {
        PickSetup pickSetup = new PickSetup();
        pickSetup.setTitle("Choose Document");
        pickSetup.setGalleryIcon(com.vansuita.pickimage.R.mipmap.gallery_colored);
        pickSetup.setCameraIcon(com.vansuita.pickimage.R.mipmap.camera_colored);
        pickSetup.setCancelTextColor(R.color.colorAccent);
        dialog = PickImageDialog.build(pickSetup);
        dialog.setOnPickCancel(this);
        dialog.show(getFragmentManager());
    }

    @Override
    public void onCancelClick() {

    }

    @Override
    public void onPickResult(PickResult pickResult) {
        Log.e("RESULT", " = " + pickResult.getPath());
        if (pickResult.getError() == null) {
            CropImage.activity(pickResult.getUri())
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setAspectRatio(16, 9)
                    .start(context);
        } else {
            Log.e("RESULT", "ERROR = " + pickResult.getError());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                documentFile = FileUtils.getFile(context, result.getUri());
                Bitmap bitmap = Utils.getCompressedBitmap(documentFile.getAbsolutePath());
                documentPreview.setImageBitmap(bitmap);
                documentPreview.setVisibility(View.VISIBLE);
                iamge = "AddressUplaod";
                Log.e("Document File ", documentFile.toString());
            }
        }
    }

    public void getOTP(String msg, String mobile) {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        Log.e("==Registration====>> ", msg);
        String url = BuildConfig.SMS_URL + mobile + "&msg=" + msg.replace(" ", "%20");
        LoggerUtil.logItem(url);
        Call<String> call = apiServices.getOtp(url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
//                hideLoading();
                showMessage("An OTP has been sent on your registered mobile no *******" + mobile.substring(6) + ".");
                startSMSListener();
                new Handler().postDelayed(() -> confirmOtp(), DELAY_TIME);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
//                hideLoading();
                showAlert("Something went wrong, Try again.", R.color.red, R.drawable.error);
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
//                String msg = "Dear " + etName.getText().toString().trim() +
//                        ", One Time Password(OTP) for your request is " + generatePin()
//                        + ", OTP is valid for 5 min.http://cashbag.co.in/";
                String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
                alertDialog.dismiss();
                getOTP(msg, mobileno_st);
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
//                showAlert("Please wait....", R.color.green, R.drawable.alerter_ic_notifications);
//                TODO
                updateProfile();
                alertDialog.dismiss();
            } else {
                editTextConfirmOtp.setText("");
                editTextConfirmOtp.setError("Invalid OTP");
            }
        });
    }

    private void uploadBagRequestFile(File file) {

        showLoading();
//      creating request body for Profilefile
        RequestBody requestBody = null;
        if (file != null)
            requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody Fk_MemIdBody = RequestBody.create(MediaType.parse("text/plain"), "ClientsDocument");
//      TODO CHANGE ACTION AS PER REQUIREMENT
        RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "AddressUpdate");
        RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody uniquenoBody = RequestBody.create(MediaType.parse("text/plain"), "");
        MultipartBody.Part body = MultipartBody.Part.createFormData("", file.getName(), requestBody);
//      LoggerUtil.logItem(reqId);
//      creating our api
        ApiServices apiServices = ServiceGenerator.createServiceFile(ApiServices.class);
//      creating a call and calling the upload image method
        Call<JsonObject> call = apiServices.uploadImage(String.valueOf(PreferencesManager.getInstance(context).getUSERID()), Fk_MemIdBody, action, typeBody, uniquenoBody, body);
//      finally performing the call
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                LoggerUtil.logItem(response.body());
                hideLoading();
                getProfileData();
//              onBackPressed();
//              TODO
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("***********", call.request().url().toString());
                Log.e("***********", "= " + t.getLocalizedMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void startSMSListener() {
        try {
            smsReceiver = new SMSReceiver();
            smsReceiver.setOTPListener(this);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
            context.registerReceiver(smsReceiver, intentFilter);

            SmsRetrieverClient client = SmsRetriever.getClient(context);

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
                LocalBroadcastManager.getInstance(context).unregisterReceiver(smsReceiver);
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

    }

    private void getProfileData() {
        String url = BuildConfig.BASE_URL_MLM + "GetPersonalDetail?LoginId=" + PreferencesManager.getInstance(context).getLoginID();
        showLoading();
        LoggerUtil.logItem(url);

        Call<ResponseGetProfile> call = apiServices.getProfile(url);
        call.enqueue(new Callback<ResponseGetProfile>() {
            @Override
            public void onResponse(@NotNull Call<ResponseGetProfile> call, @NotNull Response<ResponseGetProfile> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    assert response.body() != null;
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        if (response.body().getStatus().equalsIgnoreCase("Pending")) {
                            disableEditText(etFirstName);
                            disableEditText(etLastName);
                            disableEditText(etMobile);
                            disableEditText(etEmail);
                            disableEditText(etPincode);
                            disableEditText(etState);
                            disableEditText(etCity);
                            disableEditText(etState);
                            disableEditText(etTehsil);
                            disableEditText(etAddress);
                            textSubmitUpdate.setClickable(false);
                            textSubmitUpdate.setFocusable(false);
                            textSubmitUpdate.setTextColor(getResources().getColor(R.color.white));
                            textSubmitUpdate.setText("Pending For Approval");
                            textSubmitUpdate.setBackgroundColor(getResources().getColor(R.color.red));
                            etFirstName.setText(response.body().getPersonalDetailNew().getFirstName());
                            etLastName.setText(response.body().getPersonalDetailNew().getLastName());
                            etPincode.setText(response.body().getPersonalDetailNew().getPincode());
                            etMobile.setText(response.body().getPersonalDetailNew().getMobile());
                            etEmail.setText(response.body().getPersonalDetailNew().getEmail());
                            etAddress.setText(response.body().getPersonalDetailNew().getAddress());
                            Glide.with(context).load(response.body().getPersonalDetailNew().getImageUrl()).
                                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).error(R.drawable.wallet))
                                    .into(documentPreview);
                            documentPreview.setVisibility(View.VISIBLE);
                            selectDocument.setVisibility(View.GONE);
                        } else {
                            etFirstName.setText(response.body().getPersonalDetailOld().getFirstName());
                            etLastName.setText(response.body().getPersonalDetailOld().getLastName());
                            etPincode.setText(response.body().getPersonalDetailOld().getPincode());
                            etMobile.setText(response.body().getPersonalDetailOld().getMobile());
                            etEmail.setText(response.body().getPersonalDetailOld().getEmail());
                            etAddress.setText(response.body().getPersonalDetailOld().getAddress());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseGetProfile> call, @NotNull Throwable t) {

            }
        });
    }

    private void updateProfile() {
        RequestProfileUpdate update = new RequestProfileUpdate();
        update.setAddress(etAddress.getText().toString());
        update.setCity(etCity.getText().toString());
        update.setEmail(etEmail.getText().toString());
        update.setFirstName(etFirstName.getText().toString());
        update.setLastName(etLastName.getText().toString());
        update.setLoginId(PreferencesManager.getInstance(context).getLoginID());
        update.setMobile(etMobile.getText().toString());
        update.setPincode(etPincode.getText().toString());
        update.setState(etState.getText().toString());

        showLoading();
        LoggerUtil.logItem(update);

        Call<JsonObject> call = apiServices.getProfileUpdate(update);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    assert response.body() != null;
                    if (response.body().get("response").getAsString().equalsIgnoreCase("Success")) {
                        showMessage(response.body().get("message").getAsString());
                        if (iamge.length() != 0) {
                            uploadBagRequestFile(documentFile);
                        } else {
//                            TODO
                            getProfileData();
                        }
                    } else {
                        showMessage(response.body().get("message").getAsString());
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
}
