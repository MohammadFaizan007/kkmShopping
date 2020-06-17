package com.digitalcashbag.utilities.recharges.payment.offline_payment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.activities.MainContainer;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.RequestWallet;
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

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class OfflineBagRequest extends BaseActivity implements IPickCancel, IPickResult {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bell_icon)
    ImageView bellIcon;

    @BindView(R.id.et_RequestAmount)
    TextInputEditText etRequestAmount;
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
    TextView etCompanyName;
    String bankId = "";
    @BindView(R.id.etPaymentDate)
    TextInputEditText etPaymentDate;
    @BindView(R.id.radio_neft)
    RadioButton radioNeft;
    @BindView(R.id.radio_imps)
    RadioButton radioImps;
    @BindView(R.id.radio_rtgs)
    RadioButton radioRtgs;
    @BindView(R.id.radio_upi)
    RadioButton radioUpi;
    @BindView(R.id.radioGroup_payment)
    RadioGroup radioGroupPayment;
    String payMode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_bag_request_new);
        ButterKnife.bind(this);

        title.setText("Bag Request");
        bellIcon.setVisibility(View.GONE);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        try {
            if (getIntent().getBundleExtra(PAYLOAD_BUNDLE) != null) {
                etCompanyName.setText(getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("CompanyName"));
                tvBankName.setText(getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("BankName"));
                tvBranch.setText(getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("BranchName"));
                tvAccoNumber.setText(getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("AccountNo"));
                tvIfsc.setText(getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("IfscCode"));
                bankId = getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("BankId");
                etRequestAmount.setText(getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("amount"));
                etRequestAmount.setSelection(etRequestAmount.getText().toString().length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        radioNeft.setOnClickListener(view -> {
            payMode = "NEFT";
            radioNeft.setChecked(true);
            radioImps.setChecked(false);
            radioRtgs.setChecked(false);
            radioUpi.setChecked(false);
        });
        radioImps.setOnClickListener(view -> {
            payMode = "IMPS";
            radioNeft.setChecked(false);
            radioImps.setChecked(true);
            radioRtgs.setChecked(false);
            radioUpi.setChecked(false);
        });
        radioRtgs.setOnClickListener(view -> {
            payMode = "RTGS";
            radioNeft.setChecked(false);
            radioImps.setChecked(false);
            radioRtgs.setChecked(true);
            radioUpi.setChecked(false);
        });
        radioUpi.setOnClickListener(view -> {
            payMode = "UPI";
            radioNeft.setChecked(false);
            radioImps.setChecked(false);
            radioRtgs.setChecked(false);
            radioUpi.setChecked(true);
        });


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

    @OnClick({R.id.selectDocument, R.id.btn_submit, R.id.btn_reset, R.id.etPaymentDate, R.id.side_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.selectDocument:
                showDialog();
                break;
            case R.id.btn_submit:
                if (validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        Log.e("Mobile Number == ", PreferencesManager.getInstance(context).getMOBILE());
                        if (PreferencesManager.getInstance(context).getMOBILE().equalsIgnoreCase("na")) {
                            showAlert("Mobile number not found. Kindly, update it from profile.", R.color.red, R.drawable.error);
                        } else {
                            setRequestForBagRequest();
                        }
                    }
                }
                break;

            case R.id.etPaymentDate:
                datePicker(etPaymentDate);
                break;

            case R.id.side_menu:
                finish();
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
        dialog.show(getSupportFragmentManager());
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
                Log.e("Document File ", documentFile.toString());
            }
        }
    }

    @Override
    public void onCancelClick() {
    }

    private void setRequestForBagRequest() {

        final RequestWallet req = new RequestWallet();

        req.setLoginId(PreferencesManager.getInstance(context).getLoginID());
        req.setRequestedAmount(etRequestAmount.getText().toString().trim());
        req.setPaymentMode(payMode);
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
                LoggerUtil.logItem(response.body());
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    showMessage(response.body().getMessage());
                    uploadBagRequestFile(documentFile, response.body().getpK_RequestId());
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

    private void uploadBagRequestFile(File file, String reqId) {

        showLoading();
        //creating request body for Profilefile
        RequestBody requestBody = null;
        if (file != null)
            requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody Fk_MemIdBody = RequestBody.create(MediaType.parse("text/plain"), "ClientsDocument");
        // TODO CHANGE ACTION AS PER REQUIREMENT
        RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "SlipUpload");
        RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody uniquenoBody = RequestBody.create(MediaType.parse("text/plain"), reqId);
        MultipartBody.Part body = MultipartBody.Part.createFormData("", file.getName(), requestBody);
//        LoggerUtil.logItem(reqId);

        //creating our api
        ApiServices apiServices = ServiceGenerator.createServiceFile(ApiServices.class);
        //creating a call and calling the upload image method
        Call<JsonObject> call = apiServices.uploadImage(String.valueOf(PreferencesManager.getInstance(context).getUSERID()), Fk_MemIdBody, action, typeBody, uniquenoBody, body);
        //finally performing the call
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                LoggerUtil.logItem(response.body());
                hideLoading();
//                onBackPressed();
                Bundle bundle = new Bundle();
                bundle.putString("from", "Bag");
                Intent intent1 = new Intent(context, MainContainer.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra(PAYLOAD_BUNDLE, bundle);
                startActivity(intent1);
                finish();
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
            etRequestAmount.setError("Request amount cannot be empty.");
            requestFocus(etRequestAmount);
            return false;
        } else if (payMode.equalsIgnoreCase("")) {
            showMessage("Please choose any payment mode.");
            return false;
        } else if (etPaymentDate.getText().toString().equalsIgnoreCase("")) {
            showMessage("Please select payment date");
            return false;
        } else if (etTransactionNumber.getText().toString().equalsIgnoreCase("")) {
            showMessage("Please enter transaction number");
            requestFocus(etTransactionNumber);
            return false;
        } else if (etTransactionNumber.getText().toString().length() < 6) {
            showMessage("Invalid transaction number");
            requestFocus(etTransactionNumber);
            return false;
        } else if (documentFile == null) {
            showMessage("Please select file..");
            return false;
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
