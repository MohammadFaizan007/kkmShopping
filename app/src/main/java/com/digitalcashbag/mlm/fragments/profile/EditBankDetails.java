package com.digitalcashbag.mlm.fragments.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.digitalcashbag.mlm.fragments.SELECTION;
import com.google.gson.JsonObject;
import com.lid.lib.LabelImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.constants.UrlConstants;
import kkm.com.core.model.request.profilemlm.RequestBankUpdate;
import kkm.com.core.model.response.profile.ApiUserProfile;
import kkm.com.core.model.response.profile.ResponseProfileUpdate;
import kkm.com.core.model.response.profile.ResponseViewProfile;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.FileUtils;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class EditBankDetails extends BaseFragment implements IPickCancel, IPickResult {
    private final String BLANK_CHEQUE_PIC = "Cheque";
    @BindView(R.id.account_holder_et)
    EditText accountHolderEt;
    @BindView(R.id.account_no_et)
    EditText accountNoEt;
    @BindView(R.id.ifsc_et)
    EditText ifscEt;
    @BindView(R.id.et_bank)
    EditText etBank;
    @BindView(R.id.branch_et)
    EditText branchEt;
    @BindView(R.id.submit)
    Button submit;
    Unbinder unbinder;
    @BindView(R.id.imgAddressFront)
    LabelImageView imgbankDetail;
    private File Chequefile;
    private ProgressDialog pd;

    private SELECTION selection;
    private PickImageDialog dialog;


    private void showProgressDialog() {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Upload Documents...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.show();
    }

    void showDialog() {
        PickSetup pickSetup = new PickSetup();
        switch (selection) {
            case CHEQUE_DETAIL:
                pickSetup.setTitle("Choose Photocopy of Cheque");
                break;

        }

        pickSetup.setGalleryIcon(com.vansuita.pickimage.R.mipmap.gallery_colored);
        pickSetup.setCameraIcon(com.vansuita.pickimage.R.mipmap.camera_colored);
        pickSetup.setCancelTextColor(R.color.colorAccent);

        // If show system dialog..
        // pickSetup.setSystemDialog(true);

        dialog = PickImageDialog.build(pickSetup);
        dialog.setOnPickCancel(this);
        dialog.show(getFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_bank_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        setData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (Validation()) {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                updateBank();
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }
        }
    }

    void setData() {
        try {
            ApiUserProfile profile = UrlConstants.profile.getApiUserProfile();
            accountHolderEt.setText(profile.getAccountHolderName());
            accountNoEt.setText(profile.getAccountNo());
            ifscEt.setText(profile.getIfscCode());
            etBank.setText(profile.getBankName());
            branchEt.setText(profile.getBranchName());

            if (UrlConstants.profile.getApiUserProfile().getCancelledChequeAttach() == null) {
                Glide.with(context).load(R.drawable.camera_upload).into(imgbankDetail);
            } else {
                Glide.with(context).load(UrlConstants.profile.getApiUserProfile().getCancelledChequeAttach()).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                .placeholder(R.drawable.camera_upload)
                                .error(R.drawable.camera_upload))
                        .into(imgbankDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        imgbankDetail.setOnClickListener(v -> {
            selection = SELECTION.CHEQUE_DETAIL;
            showDialog();
        });


        ifscEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 10) {
                    getBankName(s.toString());
                } else {
                    etBank.setText("");
                    branchEt.setText("");
                }
            }
        });
    }

    void updateBank() {
        RequestBankUpdate update = new RequestBankUpdate();
        update.setFkMemId(PreferencesManager.getInstance(context).getUSERID());
        update.setAccountNo(accountNoEt.getText().toString());
        update.setBankName(etBank.getText().toString());
        update.setBranchName(branchEt.getText().toString());
        update.setAccountHolderName(accountHolderEt.getText().toString());
        update.setIFSCCode(ifscEt.getText().toString());
//        update.setUPI(upi_et.getText().toString());

        LoggerUtil.logItem(update);
        showLoading();

        Call<ResponseProfileUpdate> call = apiServices.getBankUpdate(update);
        call.enqueue(new Callback<ResponseProfileUpdate>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProfileUpdate> call, @NonNull Response<ResponseProfileUpdate> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                Log.e("res", response.body().toString());
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    showMessage("Updated Successfully");
                    if (Chequefile != null) {
                        showProgressDialog();
                        uploadFile(Chequefile, BLANK_CHEQUE_PIC, "", "");
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

    private boolean Validation() {
        if (accountHolderEt.getText().toString().trim().length() < 2) {
            accountHolderEt.setError("Enter name");
            return false;
        } else if (accountNoEt.getText().toString().trim().length() < 0) {
            accountNoEt.setError("Enter valid account number");
            return false;
        } else if (etBank.getText().toString().trim().length() < 2) {
            etBank.setError("Enter Bank name");
            return false;
        } else if (branchEt.getText().toString().trim().length() < 2) {
            branchEt.setError("Enter Branch name");
            return false;
        } else if (ifscEt.getText().toString().trim().length() < 11) {
            ifscEt.setError("Enter IFSC");
            return false;
        }
        return true;
    }

    private void getBankName(String ifscCode) {

        String url = "https://ifsc.razorpay.com/" + ifscCode;
        Call<JsonObject> getCity = apiServices.getBankName(url);
        getCity.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    Log.e("Response", response.body().toString());
                    try {
                        etBank.setText(response.body().get("BANK").getAsString());
                        branchEt.setText(response.body().get("BRANCH").getAsString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void onCancelClick() {
        dialog.dismiss();
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        Log.e("RESULT", "= " + pickResult.getPath());
        if (pickResult.getError() == null) {
            switch (selection) {
                case CHEQUE_DETAIL:
                    CropImage.activity(pickResult.getUri()).setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setAspectRatio(16, 9)
                            .start(context);
                    break;
            }
        } else {
            Log.e("RESULT", "ERROR = " + pickResult.getError());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Cheque File ", "Bank Fragment");
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            switch (selection) {
                case CHEQUE_DETAIL:
                    if (resultCode == RESULT_OK) {
                        Chequefile = FileUtils.getFile(context, result.getUri());
                        Glide.with(context).load(result.getUri()).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                        .placeholder(R.mipmap.ic_launcher_foreground)
                                        .error(R.mipmap.ic_launcher_foreground))
                                .into(imgbankDetail);
                        Log.e("BANK DETAIL File ", Chequefile.getAbsolutePath());
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                        LoggerUtil.logItem(error.getMessage());
                    }
                    break;

            }
        }
    }

    private void uploadFile(File file, final String uploadDocument, String type, String uniqueno) {
        try {
            Log.e("***********", "file is : " + file.length());
            Log.e("***********", "uploadDocument is " + uploadDocument);

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
                    switch (uploadDocument) {
                        case BLANK_CHEQUE_PIC:
                            pd.dismiss();
                            getUserProfile();
                            break;
                    }
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
}
