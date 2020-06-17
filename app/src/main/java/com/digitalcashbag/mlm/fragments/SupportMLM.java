package com.digitalcashbag.mlm.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.digitalcashbag.mlm.activities.MainContainerMLM;
import com.google.gson.JsonObject;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.request.RequestSendQuery;
import kkm.com.core.model.response.ResponseSendQuery;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportMLM extends BaseFragment implements MvpView, IPickCancel, IPickResult {
    Unbinder unbinder;
    @BindView(R.id.et_subject)
    EditText etSubject;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.view_ticket)
    Button viewTicket;
    PopupMenu popup_support;
    @BindView(R.id.txtFileName)
    TextView txtFileName;
    ArrayList<String> fileList = new ArrayList<>();
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    private PickImageDialog dialog;
    private File attachFile;

    public SupportMLM() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_support, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getSupportContactDetails();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    @OnClick({R.id.et_subject, R.id.submit, R.id.cancel, R.id.view_ticket, R.id.add_atachment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        hideKeyboard();
                        sendQuery();
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                }
                break;
            case R.id.cancel:
                etSubject.setText("");
                etMessage.setText("");
                break;
            case R.id.view_ticket:
                ((MainContainerMLM) context).addFragment(new TicketListMLM(), "Ticket's History");
                break;

            case R.id.et_subject:
                hideKeyboard();
                popup_support = new PopupMenu(context, view);
                popup_support.getMenuInflater().inflate(R.menu.menu_support, popup_support.getMenu());
                popup_support.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.action_general:
//                            emailTo = BuildConfig.Support_general;
                            etSubject.setText(menuItem.getTitle());
                            return true;
                        case R.id.action_profile:
//                            emailTo = BuildConfig.Support_profile;
                            etSubject.setText(menuItem.getTitle());
                            return true;
                        case R.id.action_referral:
//                            emailTo = BuildConfig.Support_referral;
                            etSubject.setText(menuItem.getTitle());
                            return true;
                        case R.id.action_shopping:
//                            emailTo = BuildConfig.Support_shopping;
                            etSubject.setText(menuItem.getTitle());
                            return true;
                        case R.id.action_recharge:
//                            emailTo = BuildConfig.Support_bagrecharge;
                            etSubject.setText(menuItem.getTitle());
                            return true;
                        case R.id.action_bill:
//                            emailTo = BuildConfig.Support_billpayment;
                            etSubject.setText(menuItem.getTitle());
                            return true;
                        default:
                            return false;


                    }
                });
                popup_support.show();
                break;
            case R.id.add_atachment:
                showDialog();
                break;


        }
    }

    void showDialog() {
        PickSetup pickSetup = new PickSetup();
        pickSetup.setTitle("Add Attachment");
        pickSetup.setGalleryIcon(com.vansuita.pickimage.R.mipmap.gallery_colored);
        pickSetup.setCameraIcon(com.vansuita.pickimage.R.mipmap.camera_colored);
        pickSetup.setCancelTextColor(R.color.colorAccent);

        dialog = PickImageDialog.build(pickSetup);
        dialog.setOnPickCancel(this);
        dialog.show(getFragmentManager());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void sendQuery() {
        RequestSendQuery requestSendQuery = new RequestSendQuery();
        requestSendQuery.setFkMemId(PreferencesManager.getInstance(context).getUSERID());
        requestSendQuery.setSubject(etSubject.getText().toString().trim());
        requestSendQuery.setMessage(etMessage.getText().toString().trim());
        if (attachFile != null) {
            requestSendQuery.setIsAttachment("1");
        } else {
            requestSendQuery.setIsAttachment("0");
        }
        LoggerUtil.logItem(requestSendQuery);
        showLoading();
        Call<ResponseSendQuery> call = apiServices.getResponseSendQueryCall(requestSendQuery);
        call.enqueue(new Callback<ResponseSendQuery>() {
            @Override
            public void onResponse(@NotNull Call<ResponseSendQuery> call, @NotNull Response<ResponseSendQuery> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                            if (attachFile != null) {
                                showMessage("Please wait...");
                                uploadFile(attachFile, response.body().getMessageId());
                            } else {
                                etSubject.setText("");
                                etMessage.setText("");
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseSendQuery> call, @NotNull Throwable t) {
                hideLoading();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private boolean Validation() {
        try {
            if ((etSubject.getText().toString().trim()).length() == 0) {
                showAlert("Subject can't be empty.", R.color.red, R.drawable.error);
                return false;
            } else if ((etMessage.getText().toString().trim()).length() == 0) {
                showAlert("Please provide some detailed information inorder to serve you better.", R.color.red, R.drawable.error);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onCancelClick() {
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {
            fileList = new ArrayList<>();
            attachFile = new File(pickResult.getPath());
            txtFileName.setText(attachFile.getName());
            fileList.add(attachFile.getAbsolutePath());

        }
    }

    private void uploadFile(File file, String message_id) {
        try {
            Log.e("***********", "attachFile is : " + file.length());
            showLoading();
            //creating request body for Profile attachFile
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), "ClientsDocument");
            RequestBody imgType = RequestBody.create(MediaType.parse("text/plain"), "SupportFileUpload");
            RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody uniqueNoBody = RequestBody.create(MediaType.parse("text/plain"), "");

            MultipartBody.Part body = MultipartBody.Part.createFormData("", file.getName(), requestBody);
            //creating our api
            ApiServices apiServices = ServiceGenerator.createServiceFile(ApiServices.class);
            //creating a call and calling the upload image method
            Call<JsonObject> call = apiServices.uploadImage(message_id, descBody, imgType, typeBody, uniqueNoBody, body);
            //finally performing the call
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    LoggerUtil.logItem(response.body());
                    hideLoading();
                    try {
                        attachFile = null;
                        txtFileName.setText("");
                        etSubject.setText("");
                        etMessage.setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                    Log.e("***********", call.request().url().toString());
                    Log.e("***********", "= " + t.getMessage());
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSupportContactDetails() {

        showLoading();
        String url = BuildConfig.BASE_URL_UTILITY + "getCashbagSupport";
        Call<JsonObject> call = apiServices_utility.getSupportData(url);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
//                {
//                    "email": "support@cashbag.co.in",
//                        "mobile": "1800120000044",
//                        "response": "Success"
//                }
                try {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body() != null && response.body().get("response").getAsString().equalsIgnoreCase("Success")) {
                        tvEmail.setText(String.format("Mail Us : %s", response.body().get("email").getAsString()));
                        tvNumber.setText(String.format("Call Us : %s", response.body().get("mobile").getAsString()));
                    } else {
                        tvEmail.setText(String.format("Mail Us : %s", "support@cashbag.co.in"));
                        tvNumber.setText(String.format("Call Us : %s", "1800120000044"));
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

