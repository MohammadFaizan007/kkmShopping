package com.digitalcashbag.mlm.fragments.profile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.constants.UrlConstants;
import kkm.com.core.model.request.profilemlm.RequestPersonalUpdate;
import kkm.com.core.model.response.ResponsePincodeDetail;
import kkm.com.core.model.response.profile.ApiUserProfile;
import kkm.com.core.model.response.profile.ResponseProfileUpdate;
import kkm.com.core.model.response.profile.ResponseViewProfile;
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

public class EditPersonalDetails extends BaseFragment {

    @BindView(R.id.first_name_et)
    EditText firstNameEt;
    @BindView(R.id.last_name_et)
    EditText lastNameEt;
    @BindView(R.id.dob_et)
    EditText dobEt;
    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.gender_et)
    EditText genderEt;
    @BindView(R.id.father_name_et)
    EditText fatherNameEt;
    @BindView(R.id.et_nominee_name)
    EditText etNomineeName;
    @BindView(R.id.et_nominee_relation)
    EditText etNomineeRelation;
    @BindView(R.id.marital_status)
    EditText maritalStatus;
    @BindView(R.id.address_et)
    EditText addressEt;
    @BindView(R.id.pin_code_et)
    EditText pinCodeEt;
    @BindView(R.id.city_et)
    TextInputEditText cityEt;
    @BindView(R.id.state_et)
    TextInputEditText stateEt;
    @BindView(R.id.mobile_et)
    EditText mobile_et;
    @BindView(R.id.pan_et)
    EditText panEt;
    @BindView(R.id.aadhar_et)
    EditText aadharEt;
    @BindView(R.id.submit)
    Button submit;
    Unbinder unbinder;
    @BindView(R.id.imgProfle)
    ImageView imgProfle;

    @BindView(R.id.et_tehsil)
    TextInputEditText etTehsil;
    @BindView(R.id.pincodeProgress)
    ProgressBar pincodeProgress;

    private ProgressDialog pd;
    private File Profilefile;
    private final int TAKE_PHOTO_CODE = 1113;
    private final int PICK_IMAGE_REQUEST = 1115;
    private final String PROFILE_PIC = "Profile";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_personal_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        pinCodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0 && s.length() == 6) {
                    getStateCityName(pinCodeEt.getText().toString().trim());
                } else {
                    cityEt.setText("");
                    stateEt.setText("");
                    etTehsil.setText("");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        setData();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.dob_et, R.id.gender_et, R.id.marital_status, R.id.submit, R.id.imgProfle, R.id.et_tehsil})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dob_et:
                datePicker(dobEt);
                break;
            case R.id.gender_et:
                getPopUpForGender();
                break;
            case R.id.marital_status:
                getPopUpMaritalStatus();
                break;
            case R.id.imgProfle:
                showPicker();
                break;
            case R.id.submit:
                if (validate()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getPersonalUpdate();
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                }
                break;
        }
    }

    private void capturePic() {
        Intent cameraIntent = new Intent(context, CameraFragmentMainActivity.class);
        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }

    void setData() {
        try {
            ApiUserProfile profile = UrlConstants.profile.getApiUserProfile();
            firstNameEt.setText(profile.getFirstName());
            lastNameEt.setText(profile.getLastName());
            if (!profile.getDob().equalsIgnoreCase("NA"))
                dobEt.setText(profile.getDob());
            emailEt.setText(profile.getEmail());
            genderEt.setText(profile.getGender());
            fatherNameEt.setText(profile.getFathersName());
            etNomineeName.setText(profile.getNomineeName());
            etNomineeRelation.setText(profile.getRelationwithNominee());
            maritalStatus.setText(profile.getMarritalStatus());
            addressEt.setText(String.format("%s%s%s", profile.getAddress1(), profile.getAddress2(), profile.getAddress3()));
            pinCodeEt.setText(profile.getPinCode());
            cityEt.setText(profile.getCity());
            stateEt.setText(profile.getState());
            mobile_et.setText(profile.getMobile());
            panEt.setText(profile.getPanCard());
            aadharEt.setText(profile.getAadhaarNo());
            etTehsil.setText(profile.getTehsil());

            if (UrlConstants.profile.getApiUserProfile().getProfilepic() == null) {
                Glide.with(context).load(R.drawable.camera_upload).into(imgProfle);
            } else {
                Glide.with(context).load(UrlConstants.profile.getApiUserProfile().getProfilepic()).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                .placeholder(R.mipmap.ic_launcher_foreground)
                                .error(R.mipmap.ic_launcher_foreground))
                        .into(imgProfle);
            }
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    private void getPopUpForGender() {
        PopupMenu popupg = new PopupMenu(context, genderEt);
        popupg.getMenuInflater().inflate(R.menu.menu_gender, popupg.getMenu());
        popupg.setOnMenuItemClickListener(item -> {
            try {
                genderEt.setText(item.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });
        popupg.show();
    }

    private void getPopUpMaritalStatus() {
        PopupMenu popupg = new PopupMenu(context, maritalStatus);
        popupg.getMenuInflater().inflate(R.menu.menu_marital_status, popupg.getMenu());
        popupg.setOnMenuItemClickListener(item -> {
            try {
                maritalStatus.setText(item.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });
        popupg.show();
    }

    private void datePicker(final EditText et) {
        int mYear, mMonth, mDay;
        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DatePickerDialogTheme,
                (view, year, monthOfYear, dayOfMonth) ->
                        et.setText(String.format(Locale.ENGLISH, "%d/%d/%d", dayOfMonth, monthOfYear + 1, year))
                , mYear, mMonth, mDay);
        et.setError("");
        cal.add(Calendar.YEAR, -18);
        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    private boolean validate() {
        if (firstNameEt.getText().toString().trim().length() < 2) {
            firstNameEt.setError("Enter name");
            return false;
        } else if (dobEt.getText().toString().trim().length() == 0) {
            dobEt.setError("Date of birth can not be blank.");
            return false;
        } else if (!Utils.validatePan(panEt.getText().toString().trim())) {
            panEt.setError("Invalid PAN number");
            panEt.requestFocus();
            return false;
        } else if (!Utils.validateAadharNumber(aadharEt.getText().toString().trim())) {
            aadharEt.setError("Invalid aadhar number");
            aadharEt.requestFocus();
            return false;
        } else if (stateEt.getText().toString().trim().length() == 0) {
            stateEt.setError("Please enter a valid state");
            stateEt.requestFocus();
            return false;
        } else if (cityEt.getText().toString().trim().length() == 0) {
            cityEt.setError("Please enter a valid city");
            cityEt.requestFocus();
            return false;
        } else if (etTehsil.getText().toString().trim().length() == 0) {
            etTehsil.setError("Please select tehsil");
            etTehsil.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(pinCodeEt.getText().toString().trim()) && pinCodeEt.getText().toString().trim().length() < 6) {
            pinCodeEt.setError("Invalid pin code");
            pinCodeEt.requestFocus();
            return false;
        }
        return true;
    }

    void getPersonalUpdate() {
        RequestPersonalUpdate update = new RequestPersonalUpdate();
        update.setFkMemId(PreferencesManager.getInstance(context).getUSERID());
        update.setDOB(dobEt.getText().toString());
        update.setGender(genderEt.getText().toString());
        update.setFathersName(fatherNameEt.getText().toString());
        update.setNomineeName(etNomineeName.getText().toString());
        update.setRelationwithNominee(etNomineeRelation.getText().toString());
        update.setMobile(PreferencesManager.getInstance(context).getMOBILE());
        update.setFirstName(firstNameEt.getText().toString());
        update.setLastName(lastNameEt.getText().toString());
        update.setMarritalStatus(maritalStatus.getText().toString());
        update.setEmail(emailEt.getText().toString());
        update.setAddress1(addressEt.getText().toString());
        update.setAddress2("");
        update.setAddress3("");
        update.setPinCode(pinCodeEt.getText().toString());
        update.setCity(cityEt.getText().toString());
        update.setState(stateEt.getText().toString());
        update.setTehsil(etTehsil.getText().toString());
        update.setPanCard(panEt.getText().toString());
        update.setAadharNo(aadharEt.getText().toString());

        LoggerUtil.logItem(update);

        showLoading();

        Call<ResponseProfileUpdate> call = apiServices.getPersonalUpdate(update);
        call.enqueue(new Callback<ResponseProfileUpdate>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProfileUpdate> call, @NonNull Response<ResponseProfileUpdate> response) {
                hideLoading();
                Log.e("res", response.body().toString());
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    showMessage("Updated Successfully");
                    if (Profilefile != null) {
                        uploadFile(Profilefile, PROFILE_PIC, "", "");
                    } else {
                        getUserProfile();
                    }

                } else {
                    showMessage(response.body().getResponse());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseProfileUpdate> call, @NonNull Throwable t) {
                hideLoading();
            }
        });

    }

    private void showProgressDialog() {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Upload Profile Pic...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.show();
    }

    private void uploadFile(File file, final String uploadDocument, String type, String uniqueno) {
        try {
            Log.e("***********", "file is : " + file.length());
            Log.e("***********", "uploadDocument is " + uploadDocument);
            showProgressDialog();
            //creating request body for Profile file
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), "ClientsDocument");
            RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), type);
            RequestBody uniquenoBody = RequestBody.create(MediaType.parse("text/plain"), uniqueno);
            RequestBody imgType = RequestBody.create(MediaType.parse("text/plain"), uploadDocument);
            MultipartBody.Part body = MultipartBody.Part.createFormData("", file.getName(), requestBody);
            //creating our api
            ApiServices apiServices = ServiceGenerator.createServiceFile(ApiServices.class);
            //creating a call and calling the upload image method
            Call<JsonObject> call = apiServices.uploadImage(PreferencesManager.getInstance(context).getUSERID(), descBody, imgType, typeBody, uniquenoBody, body);
            //finally performing the call
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    LoggerUtil.logItem(response);
                    Log.e("***********", call.request().url().toString());
                    pd.dismiss();
                    getUserProfile();
//
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    Log.e("***********", call.request().url().toString());
                    Log.e("***********", "= " + t.getMessage());
                    Log.e("***********", "= " + t.getLocalizedMessage());
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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

                try {
                    pincodeProgress.setVisibility(View.GONE);
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                        cityEt.setText(response.body().getCityName());
                        stateEt.setText(response.body().getStateName());
                        etTehsil.setText(response.body().getTaluk());
                    } else {
                        cityEt.setText("");
                        stateEt.setText("");
                        etTehsil.setText("");
                    }
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponsePincodeDetail> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case TAKE_PHOTO_CODE:
                    CropImage.activity(Uri.fromFile(new File(data.getStringExtra("data")))).setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setAspectRatio(1, 1)
                            .start(context);
                    break;
                case PICK_IMAGE_REQUEST:
                    Uri uri = data.getData();
                    CropImage.activity(uri).setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setAspectRatio(1, 1)
                            .start(context);
                    break;
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE: {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Profilefile = FileUtils.getFile(context, result.getUri());
                    Glide.with(context).load(result.getUri()).
                            apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                    .placeholder(R.mipmap.ic_launcher_foreground)
                                    .error(R.mipmap.ic_launcher_foreground))
                            .into(imgProfle);
                    break;
                }
//                case CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE:
//                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
//                    CropImage.activity(result.getUri()).setCropShape(CropImageView.CropShape.RECTANGLE)
//                            .setAspectRatio(1, 1)
//                            .start(context);
//                    break;
            }

        }
    }

    void getUserProfile() {
        JsonObject object = new JsonObject();
        object.addProperty("MemID", PreferencesManager.getInstance(context).getUSERID());
        showLoading();
        Call<ResponseViewProfile> call = apiServices.getUserProfileMlm(object);
        call.enqueue(new Callback<ResponseViewProfile>() {
            @Override
            public void onResponse(@NonNull Call<ResponseViewProfile> call, @NonNull Response<ResponseViewProfile> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        UrlConstants.profile = response.body();
                        PreferencesManager.getInstance(context).setMOBILE(UrlConstants.profile.getApiUserProfile().getMobile());
                        PreferencesManager.getInstance(context).setNAME(UrlConstants.profile.getApiUserProfile().getFirstName());
                        PreferencesManager.getInstance(context).setLNAME(UrlConstants.profile.getApiUserProfile().getLastName());
                        PreferencesManager.getInstance(context).setPINCODE(UrlConstants.profile.getApiUserProfile().getPinCode());
                        PreferencesManager.getInstance(context).setState(UrlConstants.profile.getApiUserProfile().getState());
                        PreferencesManager.getInstance(context).setCity(UrlConstants.profile.getApiUserProfile().getCity());
                        PreferencesManager.getInstance(context).setAddress(UrlConstants.profile.getApiUserProfile().getAddress1());
                        PreferencesManager.getInstance(context).setEMAIL(UrlConstants.profile.getApiUserProfile().getEmail());
                        PreferencesManager.getInstance(context).setDOB(UrlConstants.profile.getApiUserProfile().getDob());
                        PreferencesManager.getInstance(context).setPROFILEPIC(UrlConstants.profile.getApiUserProfile().getProfilepic());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseViewProfile> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void showPicker() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.picker_dialog);

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView txtGallery = dialog.findViewById(R.id.txtGallery);
        TextView txtCamera = dialog.findViewById(R.id.txtCamera);
        TextView txtCancel = dialog.findViewById(R.id.txtCancel);

        txtGallery.setOnClickListener(v -> {
            Intent intent = new Intent();
            // Show only images, no videos or anything else
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            // Always show the chooser (if there are multiple options available)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            dialog.dismiss();
        });

        txtCamera.setOnClickListener(v -> {

            capturePic();
            dialog.dismiss();
        });

        txtCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();

    }


}
