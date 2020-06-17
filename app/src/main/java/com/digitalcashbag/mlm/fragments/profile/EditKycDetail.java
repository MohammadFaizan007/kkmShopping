package com.digitalcashbag.mlm.fragments.profile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import kkm.com.core.model.response.profile.ResponseViewProfile;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.FileUtils;
import kkm.com.core.utils.LoggerUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class EditKycDetail extends BaseFragment implements IPickCancel, IPickResult {

    private final String PAN_PIC = "Pan";
    private final String ID_PROOF_PIC_FRONT = "AddressFront";
    private final String ID_PROOF_PIC_BACK = "AddressBack";

    Unbinder unbinder;
    @BindView(R.id.imgPanCard)
    LabelImageView imgPanCard;

    @BindView(R.id.imgAddressFront)
    LabelImageView imgAddressFront;

    @BindView(R.id.imgAddressBack)
    LabelImageView imgAddressBack;

    @BindView(R.id.txtAddressType)
    TextView txtAddressType;

    @BindView(R.id.address_id_no)
    EditText addressIdNo;

    @BindView(R.id.btnUploadDocuments)
    Button btnUploadDocuments;


    private Dialog warning_dialog;
    private ProgressDialog pd;
    private String pan_pic = "";
    private String idProof_pic_front = "";
    private String idProof_pic_back = "";
    private SELECTION selection;
    private PickImageDialog dialog;
    private File PANFile, ID_ProofFile_front, ID_ProofFile_back;

    private void showProgressDialog() {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Upload Documents...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    public void onCancelClick() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_kyc, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    void showDialog() {
        PickSetup pickSetup = new PickSetup();
        switch (selection) {
            case PAN:
                pickSetup.setTitle("Choose PAN");
                break;
            case ID_PROOF_FRONT:
                pickSetup.setTitle("Choose Address Proof");
                break;
            case ID_PROOF_BACK:
                pickSetup.setTitle("Choose Address");
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

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        try {
            String pan_status, address_status;
            {

                txtAddressType.setText(UrlConstants.profile.getApiUserProfile().getAddressProofType());
                addressIdNo.setText(UrlConstants.profile.getApiUserProfile().getUniqueNo());
                //pan
                if (UrlConstants.profile.getApiUserProfile().getPanCardAttach() == null) {
                    Glide.with(context).load(R.drawable.camera_upload).into(imgPanCard);
                } else {
                    Glide.with(context).load(UrlConstants.profile.getApiUserProfile().getPanCardAttach()).
                            apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                    .placeholder(R.drawable.camera_upload)
                                    .error(R.drawable.camera_upload))
                            .into(imgPanCard);
                }
                //id proof front
                if (UrlConstants.profile.getApiUserProfile().getAdhaarCardAttach() == null) {
                    Glide.with(context).load(R.drawable.camera_upload).into(imgAddressFront);
                } else {
                    Glide.with(context).load(UrlConstants.profile.getApiUserProfile().getAdhaarCardAttach()).
                            apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                    .placeholder(R.drawable.camera_upload)
                                    .error(R.drawable.camera_upload))
                            .into(imgAddressFront);
                }
                //id proof back
                if (UrlConstants.profile.getApiUserProfile().getAddressBackAttach() == null) {
                    Glide.with(context).load(R.drawable.camera_upload).into(imgAddressBack);
                } else {
                    Glide.with(context).load(UrlConstants.profile.getApiUserProfile().getAddressBackAttach()).
                            apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                    .placeholder(R.drawable.camera_upload)
                                    .error(R.drawable.camera_upload))
                            .into(imgAddressBack);
                }
                if (UrlConstants.profile.getApiUserProfile().getIsApprovedPanCard().equalsIgnoreCase("False")) {
                    pan_status = "Pending";
                    imgPanCard.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.orange));
                } else {
                    pan_status = "Approved";
                    imgPanCard.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                }

                if (UrlConstants.profile.getApiUserProfile().getIsApprovedAdhaar().equalsIgnoreCase("False")) {
                    address_status = "Pending";
                    imgAddressFront.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.orange));
                    imgAddressBack.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.orange));
                } else {
                    address_status = "Approved";
                    imgAddressFront.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                    imgAddressBack.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                }

                imgPanCard.setLabelText(pan_status);
                imgAddressFront.setLabelText(address_status);
                imgAddressBack.setLabelText(address_status);

            }


            //pan
            if (UrlConstants.profile.getApiUserProfile().getIsApprovedPanCard().equalsIgnoreCase("False")) {
                imgPanCard.setClickable(true);
            } else {
                imgPanCard.setClickable(false);
                imgPanCard.setLabelText("Approved");
                imgPanCard.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.green));
                imgPanCard.setClickable(false);
            }

            //id proof Front and back
            if (UrlConstants.profile.getApiUserProfile().getIsApprovedAdhaar().equalsIgnoreCase("False")) {
                imgAddressFront.setClickable(true);
                imgAddressBack.setClickable(true);
            } else {
                imgAddressFront.setClickable(false);
                imgAddressFront.setLabelText("Approved");
                imgAddressFront.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.green));
                imgAddressFront.setClickable(false);

                imgAddressBack.setClickable(false);
                imgAddressBack.setLabelText("Approved");
                imgAddressBack.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.green));
                imgAddressBack.setClickable(false);
            }

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.imgPanCard, R.id.imgAddressFront, R.id.imgAddressBack, R.id.btnUploadDocuments, R.id.txtAddressType})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgPanCard:
                selection = SELECTION.PAN;
                showDialog();
                break;
            case R.id.imgAddressFront:
                selection = SELECTION.ID_PROOF_FRONT;
                showDialog();
                break;
            case R.id.imgAddressBack:
                selection = SELECTION.ID_PROOF_BACK;
                showDialog();
                break;
            case R.id.txtAddressType:
                PopupMenu popup = new PopupMenu(context, txtAddressType);
                popup.getMenuInflater().inflate(R.menu.menu_address_type, popup.getMenu());
                popup.setOnMenuItemClickListener(item -> {
                    txtAddressType.setText(item.getTitle());
                    return true;
                });
                popup.show();
                break;
            case R.id.btnUploadDocuments:
                if ((!idProof_pic_front.equalsIgnoreCase("")) || (!idProof_pic_back.equalsIgnoreCase(""))) {
                    if (!txtAddressType.getText().toString().trim().equalsIgnoreCase("")) {
                        if (!addressIdNo.getText().toString().trim().equalsIgnoreCase("")) {
                            uploadDocumentNo(PAN_PIC);
                        } else {
                            showMessage("Please enter " + txtAddressType.getText().toString() + " number");
                        }
                    } else {
                        showMessage("Please select address proof type");
                    }

                } else {
                    uploadDocumentNo(PAN_PIC);
                }
                break;
        }
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        Log.e("RESULT", "= " + pickResult.getPath());
        if (pickResult.getError() == null) {
            switch (selection) {
                case PAN:
                    CropImage.activity(pickResult.getUri()).setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setAspectRatio(16, 9)
                            .start(context);
                    break;
                case ID_PROOF_FRONT:
                    CropImage.activity(pickResult.getUri()).setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setAspectRatio(16, 9)
                            .start(context);
                    break;
                case ID_PROOF_BACK:
                    CropImage.activity(pickResult.getUri()).setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setAspectRatio(16, 9)
                            .start(context);
                    break;
            }
        } else {
            Log.e("RESULT", "ERROR = " + pickResult.getError());
        }
    }

//    public void showKycDialog(String title) {
//        try {
//            warning_dialog = new Dialog(context);
//            warning_dialog.setCanceledOnTouchOutside(false);
//            warning_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            warning_dialog.setContentView(R.layout.kyc_edit_dialog);//
//            warning_dialog.setCanceledOnTouchOutside(true);
//            warning_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//            warning_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            ImageView camera = (ImageView) warning_dialog.findViewById(R.id.camera);
//            ImageView gallery = (ImageView) warning_dialog.findViewById(R.id.gallery);
//
//            camera.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (title.equalsIgnoreCase("Profile")) {
//                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(cameraIntent, 1337);
//
//                    } else if (title.equalsIgnoreCase("Pan")) {
//                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(cameraIntent, 1339);
//
//                    } else if (title.equalsIgnoreCase("Aadhar")) {
//                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(cameraIntent, 1341);
//
//                    } else if (title.equalsIgnoreCase("Bank")) {
//                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(cameraIntent, 1343);
//
//                    }
//                    warning_dialog.dismiss();
//
//                }
//            });
//
//            gallery.setOnClickListener(view -> {
//                if (title.equalsIgnoreCase("Profile")) {
//                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.setType("image/*");
//                    startActivityForResult(intent, 1338);
//
//                } else if (title.equalsIgnoreCase("Pan")) {
//                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.setType("image/*");
//                    startActivityForResult(intent, 1340);
//
//                } else if (title.equalsIgnoreCase("Aadhar")) {
//                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.setType("image/*");
//                    startActivityForResult(intent, 1342);
//
//                } else if (title.equalsIgnoreCase("Bank")) {
//                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.setType("image/*");
//                    startActivityForResult(intent, 1344);
//
//                }
//                warning_dialog.dismiss();
//
//            });
//
//            warning_dialog.show();
//        } catch (Exception e) {
//
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("PRofile File ", "KYC Activity");
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            switch (selection) {
                case PAN:
                    if (resultCode == RESULT_OK) {
                        PANFile = FileUtils.getFile(context, result.getUri());
                        Glide.with(context).load(result.getUri()).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                        .placeholder(R.mipmap.ic_launcher_foreground)
                                        .error(R.mipmap.ic_launcher_foreground))
                                .into(imgPanCard);

                        pan_pic = "Pan";
                        Log.e("PANFile ", PANFile.getAbsolutePath());
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                        LoggerUtil.logItem(error.getMessage());
                    }
                    break;
                case ID_PROOF_FRONT:
                    if (resultCode == RESULT_OK) {
                        ID_ProofFile_front = FileUtils.getFile(context, result.getUri());
                        Glide.with(context).load(result.getUri()).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                        .placeholder(R.mipmap.ic_launcher_foreground)
                                        .error(R.mipmap.ic_launcher_foreground))
                                .into(imgAddressFront);

                        idProof_pic_front = "Aadhar";
                        Log.e("idProof_pic_front ", ID_ProofFile_front.getAbsolutePath());
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                        LoggerUtil.logItem(error.getMessage());
                    }
                    break;
                case ID_PROOF_BACK:
                    if (resultCode == RESULT_OK) {
                        ID_ProofFile_back = FileUtils.getFile(context, result.getUri());
                        Glide.with(context).load(result.getUri()).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                        .placeholder(R.mipmap.ic_launcher_foreground)
                                        .error(R.mipmap.ic_launcher_foreground))
                                .into(imgAddressBack);
                        idProof_pic_back = "Aadhar";
                        Log.e("idProof_pic_back ", ID_ProofFile_back.getAbsolutePath());
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                        LoggerUtil.logItem(error.getMessage());
                    }
                    break;
            }

        }
    }


    private void uploadDocumentNo(String DOC_TYPE) {
        Log.e("UPLOAD", DOC_TYPE);
        switch (DOC_TYPE) {
            case PAN_PIC:
                showProgressDialog();
                if (!pan_pic.equalsIgnoreCase("")) {
                    uploadFile(PANFile, PAN_PIC, "", "");
                    pan_pic = "";
                } else if (!idProof_pic_front.equalsIgnoreCase("")) {
                    uploadFile(ID_ProofFile_front, ID_PROOF_PIC_FRONT, txtAddressType.getText().toString(), addressIdNo.getText().toString());
                    idProof_pic_front = "";
                } else if (!idProof_pic_back.equalsIgnoreCase("")) {
                    uploadFile(ID_ProofFile_back, ID_PROOF_PIC_BACK, txtAddressType.getText().toString(), addressIdNo.getText().toString());
                    idProof_pic_back = "";
                } else {
                    showMessage("KYC Update Successfully");
                    pd.dismiss();
                    getUserProfile();
                }
                break;
            case ID_PROOF_PIC_FRONT:
                if (!idProof_pic_front.equalsIgnoreCase("")) {
                    uploadFile(ID_ProofFile_front, ID_PROOF_PIC_FRONT, txtAddressType.getText().toString(), addressIdNo.getText().toString());
                    idProof_pic_front = "";
                } else if (!idProof_pic_back.equalsIgnoreCase("")) {
                    uploadFile(ID_ProofFile_back, ID_PROOF_PIC_BACK, txtAddressType.getText().toString(), addressIdNo.getText().toString());
                    idProof_pic_back = "";
                } else {
                    showMessage("KYC Update Successfully");
                    pd.dismiss();
                    getUserProfile();
                }
                break;
            case ID_PROOF_PIC_BACK:
                pd.setTitle("Finishing...");
                if (!idProof_pic_back.equalsIgnoreCase("")) {
                    uploadFile(ID_ProofFile_back, ID_PROOF_PIC_BACK, txtAddressType.getText().toString(), addressIdNo.getText().toString());
                    idProof_pic_back = "";
                } else {
                    showMessage("KYC Update Successfully");
                    pd.dismiss();
                    getUserProfile();
                }
                break;
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
                        case PAN_PIC:
                            if (pan_pic.equalsIgnoreCase("")) {
                                uploadDocumentNo(ID_PROOF_PIC_FRONT);
                            } else {
                                uploadDocumentNo(PAN_PIC);
                            }
                            break;
                        case ID_PROOF_PIC_FRONT:
                            uploadDocumentNo(ID_PROOF_PIC_BACK);
                            break;
                        case ID_PROOF_PIC_BACK:
                            // Complete...
                            showMessage("KYC Update Successfully");
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

