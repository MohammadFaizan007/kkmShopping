package com.digitalcashbag.mlm.fragments.wallet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;
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
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.request.RequestWallet;
import kkm.com.core.model.response.wallet.CompanyBankMasterSelectlistItem;
import kkm.com.core.model.response.wallet.ResponseCompanyName;
import kkm.com.core.model.response.wallet.ResponseNewWalletRequest;
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

public class E_WalletRequest extends BaseFragment implements IPickCancel, IPickResult {

    @BindView(R.id.et_RequestAmount)
    TextInputEditText etRequestAmount;
    @BindView(R.id.etPaymentMode)
    TextInputEditText etPaymentMode;
    @BindView(R.id.documentPreview)
    ImageView documentPreview;
    @BindView(R.id.selectDocument)
    Button selectDocument;

    @BindView(R.id.et_TransactionNumber)
    TextInputEditText etTransactionNumber;
    @BindView(R.id.otherOptionsLayout)
    LinearLayout otherOptionsLayout;

    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.btn_reset)
    Button btnReset;

    Unbinder unbinder;

    @BindView(R.id.inputNumber)
    TextInputLayout inputNumber;
    PickImageDialog dialog;
    File documentFile;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_branch)
    TextView tvBranch;
    @BindView(R.id.tv_acco_number)
    TextView tvAccoNumber;
    @BindView(R.id.tv_ifsc)
    TextView tvIfsc;
    @BindView(R.id.etCompanyName)
    TextInputEditText etCompanyName;
    PopupMenu popup_company;
    List<CompanyBankMasterSelectlistItem> companyBankMasterSelectlist;
    String bankId = "";
    @BindView(R.id.etPaymentDate)
    TextInputEditText etPaymentDate;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_e_wallet_request, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        popup_company = new PopupMenu(context, etCompanyName);
        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getCompanyName();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void datePicker(final EditText et) {
        Calendar cal = Calendar.getInstance();
        int mYear, mMonth, mDay;
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DatePickerDialogTheme, (view, year, monthOfYear, dayOfMonth) -> {
            et.setText(Utils.changeDateFormatSlash(dayOfMonth, monthOfYear, year));

        }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick({R.id.selectDocument, R.id.etPaymentMode, R.id.btn_submit, R.id.btn_reset, R.id.etCompanyName, R.id.etPaymentDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.selectDocument:
                showDialog();
                break;
            case R.id.etPaymentMode:
                PopupMenu popup = new PopupMenu(context, etPaymentMode);
                popup.getMenuInflater().inflate(R.menu.payment_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(item -> {
                    try {
                        switch (item.getItemId()) {
                            case R.id.option1:
                            case R.id.option2:
                            case R.id.option3:
                            case R.id.option4:
                                inputNumber.setHint("*Transaction Number");
                                etPaymentMode.setText(item.getTitle());
                                otherOptionsLayout.setVisibility(View.VISIBLE);
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
                popup.show();
                break;
            case R.id.btn_submit:
                if (validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        Log.e("Mobile Number == ", PreferencesManager.getInstance(context).getMOBILE());
                        if (PreferencesManager.getInstance(context).getMOBILE().equalsIgnoreCase("na")) {
                            showAlert("Mobile number not found. Kindly, update it from profile.", R.color.red, R.drawable.error);
                        } else {
                            setRequestForEWallet();
                        }
                    }
                }
                break;

            case R.id.etCompanyName:
                popup_company.setOnMenuItemClickListener(item -> {
                    etCompanyName.setText(item.getTitle());
                    int pos = item.getItemId();
                    tvBankName.setText(companyBankMasterSelectlist.get(pos).getBankName());
                    tvBranch.setText(companyBankMasterSelectlist.get(pos).getBranchName());
                    tvAccoNumber.setText(companyBankMasterSelectlist.get(pos).getAccountNo());
                    tvIfsc.setText(companyBankMasterSelectlist.get(pos).getIfscCode());
                    bankId = companyBankMasterSelectlist.get(pos).getPKBankId();
                    popup_company.dismiss();
                    return true;
                });
                popup_company.show();
                break;
            case R.id.etPaymentDate:
                datePicker(etPaymentDate);
                break;
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
    public void onPickResult(PickResult pickResult) {
        Log.e("RESULT", " = " + pickResult.getPath());
        if (pickResult.getError() == null) {
            CropImage.activity(pickResult.getUri())
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setAspectRatio(16, 9)
                    .start(getActivity());
        } else {
            Log.e("RESULT", "ERROR = " + pickResult.getError());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                documentFile = FileUtils.getFile(context, result.getUri());
                Bitmap bitmap = Utils.getCompressedBitmap(documentFile.getAbsolutePath());
                documentPreview.setImageBitmap(bitmap);
                Log.e("Document File ", documentFile.toString());
            }
        }
    }

    @Override
    public void onCancelClick() {

    }

    private void setRequestForEWallet() {

        final RequestWallet req = new RequestWallet();

        req.setLoginId(PreferencesManager.getInstance(context).getLoginID());
        req.setRequestedAmount(etRequestAmount.getText().toString().trim());
        req.setPaymentMode(etPaymentMode.getText().toString().trim());
        req.setTransactionNo(etTransactionNumber.getText().toString());
        req.setFk_BankId(bankId);
        req.setRequestRemark("");
        req.setPaymentDate(etPaymentDate.getText().toString());
        req.setSlipUpload("");

        LoggerUtil.logItem(req);
        showLoading();

        Call<ResponseNewWalletRequest> call = apiServices.getNewWalletRequest(req);

        call.enqueue(new Callback<ResponseNewWalletRequest>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewWalletRequest> call, @NonNull Response<ResponseNewWalletRequest> response) {
                hideLoading();
                LoggerUtil.logItem(response);
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    showMessage(response.body().getResponse());
                    eWalletRequest(documentFile);
                } else {
                    showMessage(response.body().getResponse());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewWalletRequest> call, @NonNull Throwable t) {
                hideLoading();
            }
        });

    }

    private void eWalletRequest(File file) {

        showLoading();
        //creating request body for Profilefile
        RequestBody requestBody = null;
        if (file != null)
            requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody Fk_MemIdBody = RequestBody.create(MediaType.parse("text/plain"), "ClientsDocument");
        // TODO CHANGE ACTION AS PER REQUIREMENT
        RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "SlipUpload");
        RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody uniquenoBody = RequestBody.create(MediaType.parse("text/plain"), "");
        MultipartBody.Part body = MultipartBody.Part.createFormData("", file.getName(), requestBody);

        //creating our api
        ApiServices apiServices = ServiceGenerator.createServiceFile(ApiServices.class);
        //creating a call and calling the upload image method
        Call<JsonObject> call = apiServices.uploadImage(String.valueOf(PreferencesManager.getInstance(context).getUSERID()), Fk_MemIdBody, action, typeBody, uniquenoBody, body);
        //finally performing the call
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                LoggerUtil.logItem(response);
                hideLoading();
                getActivity().onBackPressed();
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("***********", call.request().url().toString());
                Log.e("***********", "= " + t.getLocalizedMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private boolean validation() {

        if (etRequestAmount.getText().toString().equalsIgnoreCase("")) {
            etRequestAmount.setError("Request Amount cannot be empty.");
            return false;
        } else if (tvBankName.getText().toString().length() < 0) {
            etCompanyName.setError("Please select Company Name.");
            return false;
        } else if (etPaymentMode.getText().toString().equalsIgnoreCase("Select Here")) {
            etPaymentMode.setError("Please select payment mode.");
            return false;
        } else if (etTransactionNumber.getText().toString().equalsIgnoreCase("")) {
            etTransactionNumber.setError("Transaction Number cannot be empty.");
            return false;
        } else if (documentFile == null) {
            showMessage("Please Select file..");
            return false;
        }

        return true;
    }

    private void getCompanyName() {

        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Pk_BankId", "");

        Call<ResponseCompanyName> call = apiServices.getCompanyList(object);
        call.enqueue(new Callback<ResponseCompanyName>() {
            @Override
            public void onResponse(@NotNull Call<ResponseCompanyName> call, @NotNull Response<ResponseCompanyName> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        popup_company.getMenu().clear();
                        companyBankMasterSelectlist = response.body().getCompanyBankMasterSelectlist();
                        for (int i = 0; i < response.body().getCompanyBankMasterSelectlist().size(); i++) {
                            popup_company.getMenu().add(0, 0, i, response.body().getCompanyBankMasterSelectlist().get(i).getCompanyName());
                        }
                    } else {
                        popup_company.getMenu().clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseCompanyName> call, Throwable t) {
                hideLoading();
            }
        });
    }

}
