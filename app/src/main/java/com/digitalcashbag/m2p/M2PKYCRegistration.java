package com.digitalcashbag.m2p;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.AppConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.m2p.KYCDocument;
import kkm.com.core.model.request.m2p.RequestM2PRegistration;
import kkm.com.core.model.response.ResponseLogin;
import kkm.com.core.model.response.ResponsePincodeDetail;
import kkm.com.core.model.response.m2p.transaction.ResponseAllTransaction;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class M2PKYCRegistration extends BaseActivity {

    @BindView(R.id.button_next_calculate)
    Button buttonNextCalculate;
    @BindView(R.id.step1)
    TextView step1;
    @BindView(R.id.step2)
    TextView step2;
    @BindView(R.id.step3)
    TextView step3;
    @BindView(R.id.m2p_customer_registration)
    View m2p_customer_registration;
    @BindView(R.id.m2p_customer_kycdetails)
    View m2p_customer_kycdetails;
    @BindView(R.id.m2p_customer_kitdetails)
    View m2p_customer_kitdetails;
    @BindView(R.id.view12)
    View view12;
    @BindView(R.id.view23)
    View view23;
    @BindView(R.id.titleName)
    TextView title;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView actiontitle;
    @BindView(R.id.bell_icon)
    ImageView bellIcon;
    @BindView(R.id.m2p_mr)
    RadioButton m2pMr;
    @BindView(R.id.m2p_mrs)
    RadioButton m2pMrs;
    @BindView(R.id.m2p_ms)
    RadioButton m2pMs;
    @BindView(R.id.m2p_rg_title)
    RadioGroup m2pRgTitle;
    @BindView(R.id.m2p_et_first_name)
    EditText m2pEtFirstName;
    @BindView(R.id.m2p_et_last_name)
    EditText m2pEtLastName;
    @BindView(R.id.m2p_male)
    RadioButton m2pMale;
    @BindView(R.id.m2p_female)
    RadioButton m2pFemale;
    @BindView(R.id.m2p_rg_gender)
    RadioGroup m2pRgGender;
    @BindView(R.id.m2p_et_mobile)
    EditText m2pEtMobile;
    @BindView(R.id.m2p_et_email)
    EditText m2pEtEmail;
    @BindView(R.id.m2p_et_pincode)
    EditText m2pEtPincode;
    @BindView(R.id.m2p_et_city)
    EditText m2pEtCity;
    @BindView(R.id.m2p_et_state)
    EditText m2pEtState;
    @BindView(R.id.m2p_et_country)
    TextView m2pEtCountry;
    @BindView(R.id.m2p_et_address1)
    EditText m2pEtAddress1;
    @BindView(R.id.m2p_et_address2)
    EditText m2pEtAddress2;
    @BindView(R.id.kyc_et_aadhar)
    EditText kycEtAadhar;
    @BindView(R.id.kit_details_et_user_id)
    TextView kitDetailsEtUserId;
    @BindView(R.id.kit_details_et_mobile)
    TextView kitDetailsEtMobile;
    @BindView(R.id.kit_details_et_kit_no)
    TextView kitDetailsEtKitNo;
    //    @BindView(R.id.virtual)
//    RadioButton virtual;
//    @BindView(R.id.physical)
//    RadioButton physical;
//    @BindView(R.id.kit_details_rg_card_type)
//    RadioGroup kitDetailsRgCardType;
    @BindView(R.id.m2p_et_bday)
    EditText m2pEtBday;
    @BindView(R.id.rv_documents)
    RecyclerView rvDocuments;


    private KYCAdapter kycAdapter;
    private ArrayList<KYCDocument> kycDocuments = new ArrayList<KYCDocument>();
    private String cardType = "P";
    boolean emailStatus = false;
    private String title_tag = "", gender_tag = "";
    private boolean passportAlredyAdded = false, panAlredyAdded = false;
    private int DELAY_TIME = 3000;
    private SharedPreferences pref;
    int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kit_activation_frag);
        ButterKnife.bind(this);


        actiontitle.setText("KIT Activation");
        resetToStepOne();
        initializeStepOne();

        rvDocuments.setLayoutManager(new LinearLayoutManager(context));
        kycAdapter = new KYCAdapter(context, kycDocuments);
        rvDocuments.setAdapter(kycAdapter);

        step1.setOnClickListener(v -> resetToStepOne());
        step2.setOnClickListener(v -> {
            if (page == 1) {
                if (ValidationOne()) {
                    initializeStepTwo();
                    moveToStepTwo();
                }
            } else if (page == 3) {
                moveToStepTwo();
            }
        });

        buttonNextCalculate.setOnClickListener(v -> {
            if (page == 1) {
                if (ValidationOne()) {
                    hideKeyboard();
                    moveToStepTwo();
                }
            } else if (page == 2) {
                if (ValidationTwo()) {
                    hideKeyboard();
                    moveToStepThree();
                }
            } else if (page == 3) {
                hideKeyboard();
                registerUser();
            }
        });
        sideMenu.setOnClickListener(v -> onBackPressed());

        m2pEtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    if (!Utils.isEmailAddress(m2pEtEmail.getText().toString())) {
                        emailStatus = false;
                    } else {
                        checkUserData(m2pEtEmail.getText().toString(), "EM");
                    }
                }
            }
        });

//        kitDetailsRgCardType.setOnCheckedChangeListener((radioGroup, i) -> {
//            if (virtual.isChecked()) {
//                cardType = "V";
//            } else if (physical.isChecked()) {
//                cardType = "P";
//            }
//        });

    }

    private void checkUserData(String data, String datatype) {
        try {
            showLoading();
            String url = BuildConfig.BASE_URL_M2PV2 + "checkCustomerData?data=" + Cons.encryptMsg(data, easypay_key) + "&datatype=" + Cons.encryptMsg(datatype, easypay_key);
            Log.e("URL======    ", url);
            ApiServices apiServicesM2P = ServiceGenerator.createServiceM2P(ApiServices.class);
            Call<JsonObject> call = apiServicesM2P.checkCustomerData(url);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.message());
                    LoggerUtil.logItem(response.code());
                    LoggerUtil.logItem(response.errorBody());
                    LoggerUtil.logItem(response.headers());
                    LoggerUtil.logItem(response.raw().body());
                    LoggerUtil.logItem(call.request().url());
                    JsonObject response_new;
                    try {
                        response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key), JsonObject.class);
//
                        Log.e("============ ", String.valueOf(response_new));
                        if (response.body() != null && response_new.get("response").getAsString().equalsIgnoreCase("Success")) {
                            if (datatype.equals("EM")) {
                                emailStatus = true;
                            }
                        } else if (response.body() != null && response_new.get("response").getAsString().equalsIgnoreCase("error")) {
                            if (datatype.equals("EM")) {
                                emailStatus = false;
                                showAlert(response_new.getAsJsonObject("exception").get("detailMessage").getAsString(), R.color.red, R.drawable.error);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                    LoggerUtil.logItem(t.getMessage());
                }
            });
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    private boolean ValidationOne() {
        if (m2pRgTitle.getCheckedRadioButtonId() == -1) {
            Toast.makeText(context, "Please select your title.", Toast.LENGTH_LONG).show();
        } else {
            int selectedId = m2pRgTitle.getCheckedRadioButtonId();
            if (selectedId == R.id.m2p_mr) {
                title_tag = "Mr";
            } else if (selectedId == R.id.m2p_mrs) {
                title_tag = "Mrs";
            } else if (selectedId == R.id.m2p_ms) {
                title_tag = "Ms";
            }
        }
        if (m2pRgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(context, "Please select your gender.", Toast.LENGTH_LONG).show();
        } else {
            int selectedId = m2pRgGender.getCheckedRadioButtonId();
            if (selectedId == R.id.m2p_male) {
                gender_tag = "M";
            } else if (selectedId == R.id.m2p_female) {
                gender_tag = "F";
            }
        }
        if (m2pEtBday.getText().toString().length() == 0) {
            m2pEtBday.setError("Please enter your date of birth.");
            m2pEtBday.requestFocus();
            return false;
        } else if (m2pEtEmail.getText().toString().length() == 0) {
            m2pEtEmail.setError("Please enter your emailId.");
            m2pEtEmail.requestFocus();
            m2pEtBday.setError(null);
            return false;
        } else if (!Utils.isEmailAddress(m2pEtEmail.getText().toString())) {
            m2pEtEmail.setError("Please enter a valid email address.");
            m2pEtEmail.requestFocus();
            m2pEtBday.setError(null);
            return false;
        } else if (m2pEtPincode.getText().toString().length() == 0) {
            m2pEtPincode.setError("Please enter your Pincode.");
            m2pEtPincode.requestFocus();
            m2pEtEmail.setError(null);
            return false;
        } else if (m2pEtState.getText().toString().length() == 0) {
            m2pEtState.setError("Please enter your State.");
            m2pEtState.requestFocus();
            m2pEtPincode.setError(null);
            return false;
        } else if (m2pEtCity.getText().toString().length() == 0) {
            m2pEtCity.setError("Please enter your City.");
            m2pEtCity.requestFocus();
            m2pEtState.setError(null);
            return false;
        } else if (m2pEtAddress1.getText().toString().length() == 0) {
            m2pEtAddress1.setError("Please enter your House/Flat no.");
            m2pEtAddress1.requestFocus();
            m2pEtCity.setError(null);
            return false;
        } else if (m2pEtAddress2.getText().toString().length() == 0) {
            m2pEtAddress2.setError("Please enter your Street/Village");
            m2pEtAddress2.requestFocus();
            m2pEtCity.setError(null);
            return false;
        }
        return true;
    }

    private boolean ValidationTwo() {
        if (!Utils.validateAadharNumber(kycEtAadhar.getText().toString().trim())) {
            kycEtAadhar.setError("Please enter your Aadhar card number.");
            kycEtAadhar.requestFocus();
            return false;
        }
        return true;
    }

    private void initializeStepOne() {
        try {

            m2pEtPincode.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() != 0 && s.length() == 6) {
                        hideKeyboard();
                        getStateCityName(m2pEtPincode.getText().toString().trim());
                    } else {
                        m2pEtCity.setText("");
                        m2pEtState.setText("");
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


            page = 1;
            m2pEtFirstName.setText(PreferencesManager.getInstance(context).getNAME());
            m2pEtLastName.setText(PreferencesManager.getInstance(context).getLNAME());
            m2pEtBday.setText(PreferencesManager.getInstance(context).getDOB());
            m2pEtMobile.setText(PreferencesManager.getInstance(context).getMOBILE());
            m2pEtEmail.setText(PreferencesManager.getInstance(context).getEMAIL());
            m2pEtPincode.setText(PreferencesManager.getInstance(context).getPINCODE());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeStepTwo() {
        page = 2;

    }

    private void moveToStepTwo() {
        page = 2;
        buttonNextCalculate.setText("Next");
        title.setText("KYC Details");
        step1.setBackground(getResources().getDrawable(R.drawable.incentive_steps_success));
        view12.setBackgroundResource(R.color.colorPrimaryDark);
        step2.setBackground(getResources().getDrawable(R.drawable.incentive_steps_success));
        view23.setBackgroundResource(R.color.yellow);
        step3.setBackground(getResources().getDrawable(R.drawable.incentive_steps_progress));
        m2p_customer_registration.setVisibility(View.GONE);
        m2p_customer_kycdetails.setVisibility(View.VISIBLE);
        m2p_customer_kitdetails.setVisibility(View.GONE);
    }

    private void moveToStepThree() {
        buttonNextCalculate.setText("DONE");
        title.setText("KIT Details");
        page = 3;
        step1.setBackground(getResources().getDrawable(R.drawable.incentive_steps_success));
        view12.setBackgroundResource(R.color.colorPrimaryDark);
        step2.setBackground(getResources().getDrawable(R.drawable.incentive_steps_success));
        view23.setBackgroundResource(R.color.colorPrimaryDark);
        step3.setBackground(getResources().getDrawable(R.drawable.incentive_steps_success));
        m2p_customer_registration.setVisibility(View.GONE);
        m2p_customer_kycdetails.setVisibility(View.GONE);
        m2p_customer_kitdetails.setVisibility(View.VISIBLE);
        kitDetailsEtUserId.setText(PreferencesManager.getInstance(context).getLoginID());
        kitDetailsEtMobile.setText(PreferencesManager.getInstance(context).getMOBILE());
        kitDetailsEtKitNo.setText(PreferencesManager.getInstance(context).getKIT_NO());
    }

    private void resetToStepOne() {
        page = 1;
        title.setText("Personal Details");
        buttonNextCalculate.setText("Next");
        step1.setBackground(getResources().getDrawable(R.drawable.incentive_steps_success));
        view12.setBackgroundResource(R.color.yellow);
        step2.setBackground(getResources().getDrawable(R.drawable.incentive_steps_progress));
        view23.setBackgroundResource(R.color.yellow);
        step3.setBackground(getResources().getDrawable(R.drawable.incentive_steps_progress));
        m2p_customer_registration.setVisibility(View.VISIBLE);
        m2p_customer_kycdetails.setVisibility(View.GONE);
        m2p_customer_kitdetails.setVisibility(View.GONE);
    }

    @OnClick({R.id.m2p_et_bday, R.id.kyc_tv_add_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m2p_et_bday:
                datePickerPAST(m2pEtBday);
                break;
            case R.id.kyc_tv_add_more:
                addDocuments();
                break;

        }
    }

    private void datePickerPAST(final TextView et) {
        int mYear, mMonth, mDay;
        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DatePickerDialogTheme,
                (view, year, monthOfYear, dayOfMonth) -> et.setText(String.format(Locale.ENGLISH,
                        "%d-%d-%d", year, monthOfYear + 1, dayOfMonth)), mYear, mMonth, mDay);
        cal.add(Calendar.YEAR, -18);
        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    private void datePickerFUTURE(final TextView et) {
        int mYear, mMonth, mDay;
        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DatePickerDialogTheme,
                (view, year, monthOfYear, dayOfMonth) -> et.setText(String.format(Locale.ENGLISH,
                        "%d-%d-%d", year, monthOfYear + 1, dayOfMonth)), mYear, mMonth, mDay);
//        cal.add(Calendar.YEAR, -18);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        datePickerDialog.show();
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
                    m2pEtCity.setText(response.body().getCityName());
                    m2pEtState.setText(response.body().getStateName());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponsePincodeDetail> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }


    private void addDocuments() {
        LayoutInflater li = LayoutInflater.from(context);
        View pswdDialog = li.inflate(R.layout.add_kyc_doc, null);

        TextView kyc_et_doc_type = pswdDialog.findViewById(R.id.kyc_et_doc_type);
        EditText kyc_et_doc_number = pswdDialog.findViewById(R.id.kyc_et_doc_number);
        TextView kyc_et_expiry_date = pswdDialog.findViewById(R.id.kyc_et_expiry_date);
        TextView textViewEx = pswdDialog.findViewById(R.id.textViewEx);
        Button kyc_tv_add = pswdDialog.findViewById(R.id.kyc_tv_add);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(pswdDialog);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

        kyc_et_doc_type.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, kyc_et_doc_type);
            popup.getMenuInflater().inflate(R.menu.menu_doc_type, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                try {
                    switch (item.getItemId()) {
                        case R.id.doc1:
                            kyc_et_expiry_date.setVisibility(View.GONE);
                            textViewEx.setVisibility(View.GONE);
                            break;
                        case R.id.doc2:
                            kyc_et_expiry_date.setVisibility(View.VISIBLE);
                            textViewEx.setVisibility(View.VISIBLE);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                kyc_et_doc_type.setText(item.getTitle());

                return true;
            });
            popup.show();
        });
        kyc_et_expiry_date.setOnClickListener(v -> datePickerFUTURE(kyc_et_expiry_date));

        kyc_tv_add.setOnClickListener(v -> {
            hideKeyboard();
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                try {
                    KYCDocument kycDocument = new KYCDocument();
                    if (!(kyc_et_doc_type.getText().toString().trim().equalsIgnoreCase("")) &&
                            !(kyc_et_doc_number.getText().toString().trim().equalsIgnoreCase(""))) {

                        if (kyc_et_doc_type.getText().toString().trim().equalsIgnoreCase("PASSPORT")) {
                            if (kycDocuments.size() > 0) {
                                for (int i = 0; i < kycDocuments.size(); i++) {
                                    if (kycDocuments.get(i).getDocType().equalsIgnoreCase("PASSPORT")) {
                                        showMessage("PASSPORT already added");
                                        passportAlredyAdded = true;
                                        break;
                                    }
                                }
                                if (!passportAlredyAdded) {
                                    if (kyc_et_expiry_date.getText().toString().trim().equalsIgnoreCase("")) {
                                        showAlert("Please fill all of the above information.", R.color.red, R.drawable.error);
                                    } else {
                                        addPASSPORT(kycDocument, kyc_et_doc_type, kyc_et_doc_number, kyc_et_expiry_date);
                                        alertDialog.dismiss();
                                    }
                                }
                            } else {
                                if (kyc_et_expiry_date.getText().toString().trim().equalsIgnoreCase("")) {
                                    showAlert("Please fill all of the above information.", R.color.red, R.drawable.error);
                                } else {
                                    addPASSPORT(kycDocument, kyc_et_doc_type, kyc_et_doc_number, kyc_et_expiry_date);
                                    alertDialog.dismiss();
                                }
                            }
                        } else {

                            if (kycDocuments.size() > 0) {
                                for (int i = 0; i < kycDocuments.size(); i++) {
                                    if (kycDocuments.get(i).getDocType().equalsIgnoreCase("PAN")) {
                                        showMessage("PAN already added");
                                        panAlredyAdded = true;
                                        break;
                                    }
                                }
                                if (!panAlredyAdded) {
                                    if (kyc_et_expiry_date.getText().toString().trim().equalsIgnoreCase("")) {
                                        showAlert("Please fill all of the above information.", R.color.red, R.drawable.error);
                                    } else {
                                        if (!Utils.validatePan(kyc_et_doc_number.getText().toString().trim())) {
                                            showMessage("Invalid PAN Number");
                                        } else {
                                            addPAN(kycDocument, kyc_et_doc_type, kyc_et_doc_number, kyc_et_expiry_date);
                                            alertDialog.dismiss();
                                        }
                                    }
                                }
                            } else {
                                if (!Utils.validatePan(kyc_et_doc_number.getText().toString().trim())) {
                                    showMessage("Invalid PAN Number");
                                } else {
                                    addPAN(kycDocument, kyc_et_doc_type, kyc_et_doc_number, kyc_et_expiry_date);
                                    alertDialog.dismiss();
                                }
                            }
                        }
                        LoggerUtil.logItem(kycDocument);
                    } else {
                        showAlert("Please fill all of the above information.", R.color.red, R.drawable.error);
                    }
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }

            } else
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        });
    }

    private void addPAN(KYCDocument kycDocument, TextView kyc_et_doc_type, EditText kyc_et_doc_number, TextView kyc_et_expiry_date) {
        kycDocument.setDocType(kyc_et_doc_type.getText().toString());
        kycDocument.setDocNo(kyc_et_doc_number.getText().toString());
        kycDocument.setDocExpDate("");
        kycDocuments.add(kycDocument);
        kycAdapter.updateList(kycDocuments);
        kyc_et_doc_type.setText("");
        kyc_et_doc_number.setText("");
        kyc_et_expiry_date.setText("");
    }

    private void addPASSPORT(KYCDocument kycDocument, TextView kyc_et_doc_type, EditText kyc_et_doc_number, TextView kyc_et_expiry_date) {
        kycDocument.setDocType(kyc_et_doc_type.getText().toString());
        kycDocument.setDocNo(kyc_et_doc_number.getText().toString());
        kycDocument.setDocExpDate(kyc_et_expiry_date.getText().toString());
        kycDocuments.add(kycDocument);
        kycAdapter.updateList(kycDocuments);
        kyc_et_doc_type.setText("");
        kyc_et_doc_number.setText("");
        kyc_et_expiry_date.setText("");
    }

    private void registerUser() {
        JsonObject oldFormat = new JsonObject();
        oldFormat.addProperty("documents", kycDocuments.toString());
        oldFormat.addProperty("entityId", PreferencesManager.getInstance(context).getLoginID());
        oldFormat.addProperty("entityType", "CUSTOMER");
        oldFormat.addProperty("businessType", BuildConfig.M2P_BUSINESSTYPE);
        oldFormat.addProperty("businessId", "+91" + m2pEtMobile.getText().toString());
        oldFormat.addProperty("countryofIssue", "IND");
        oldFormat.addProperty("cardType", cardType);
        oldFormat.addProperty("kitNo", PreferencesManager.getInstance(context).getKIT_NO());
        oldFormat.addProperty("title", title_tag);
        oldFormat.addProperty("firstName", m2pEtFirstName.getText().toString().trim());
        oldFormat.addProperty("lastName", m2pEtLastName.getText().toString());
        oldFormat.addProperty("gender", gender_tag);
        oldFormat.addProperty("specialDate", m2pEtBday.getText().toString());
        oldFormat.addProperty("contactNo", m2pEtMobile.getText().toString());
        oldFormat.addProperty("emailAddress", m2pEtEmail.getText().toString());
        oldFormat.addProperty("address", m2pEtAddress1.getText().toString());
        oldFormat.addProperty("address2", m2pEtAddress2.getText().toString());
        oldFormat.addProperty("city", m2pEtCity.getText().toString());
        oldFormat.addProperty("state", m2pEtState.getText().toString());
        oldFormat.addProperty("country", m2pEtCountry.getText().toString());
        oldFormat.addProperty("pincode", m2pEtPincode.getText().toString());
        oldFormat.addProperty("idType", "AADHAAR");
        oldFormat.addProperty("idNumber", kycEtAadhar.getText().toString());
        oldFormat.addProperty("idExpiry", "");
        oldFormat.addProperty("eKycRefNo", "");
        oldFormat.addProperty("kycStatus", "");
        oldFormat.addProperty("countryCode", "+91");
        oldFormat.addProperty("programType", BuildConfig.M2P_BUSINESSTYPE);
        oldFormat.addProperty("LoginId", PreferencesManager.getInstance(context).getLoginID());

        LoggerUtil.logItem(oldFormat);
        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(oldFormat.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        showLoading();
        Call<JsonObject> call = apiServicesM2PV2.getM2pRegister(body);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.message());
                LoggerUtil.logItem(response.code());
                LoggerUtil.logItem(response.errorBody());
                LoggerUtil.logItem(response.headers());
                LoggerUtil.logItem(response.raw().body());
                LoggerUtil.logItem(call.request().url());
                JsonObject response_new;
                try {
                    response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key), JsonObject.class);
//
                    Log.e("============ ", String.valueOf(response_new));
                    if (response.isSuccessful()) {
                        if (response.body() != null && response_new.get("response").getAsString().equalsIgnoreCase("error")) {
                            showAlert(response.body().get("result").getAsJsonObject().get("exception").getAsString(), R.color.red, R.drawable.error);
                        } else {
                            showMessage("Successfully Registered");
                            new Handler().postDelayed(() -> finish(), DELAY_TIME);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideLoading();
                LoggerUtil.logItem(t);
                LoggerUtil.logItem(call);
            }
        });
    }

    private void silentLogin() {
        pref = getApplicationContext().getSharedPreferences(AppConfig.SHARED_PREF, 0);
        showLoading();
        JsonObject param = new JsonObject();
        param.addProperty("LoginID", PreferencesManager.getInstance(context).getSavedLOGINID());
        param.addProperty("Password", PreferencesManager.getInstance(context).getSavedPASSWORD());
        param.addProperty("DeviceId", pref.getString("regId", ""));
        param.addProperty("AndroidId", PreferencesManager.getInstance(context).getANDROIDID());
        Call<ResponseLogin> loginCall = apiServices.getLogin(param);
        LoggerUtil.logItem(param);
        loginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLogin> call, @NonNull Response<ResponseLogin> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
//                        if (response.body().getAuthonticate().getMessage().equalsIgnoreCase(
//                                "Device Id not Matched")) {
//                            pin = generatePin();
//                            String msg = "<%23> CASHBAG: Your verification code is " + pin + " " + BuildConfig.SMS_RETRIEVER;
//                            getOTP(msg, mobileNo_st);
//                        } else if (response.body().getAuthonticate().getMessage().equalsIgnoreCase(
//                                "Device Id Matched")) {
//                            allowLogin(response);
//                        }
                        allowLogin(response);
                    } else {
//                        if (response.body().getMessage().contains("another device")) {
//
//                            String message = response.body().getMessage();
//                            String[] fkMemId = message.split(":");
//                            LoggerUtil.logItem(fkMemId[0]);
//                            LoggerUtil.logItem(fkMemId[1]);
//                            LoggerUtil.logItem(fkMemId[2]);
//                            LoggerUtil.logItem(message);
//
//                            showErrorNew("You are already active in some other mobile," +
//                                    " Kindly logout from that device.", fkMemId[1], fkMemId[2]);
//                        } else if (response.body().getMessage().contains("not Valid")) {
//                            showError("Mobile Number/Email or password is not valid.", etPassword);
//                        }
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
        assert response.body() != null;
        PreferencesManager.getInstance(context).setUSERID(response.body().getAuthonticate().getFkMemID());
        PreferencesManager.getInstance(context).setLoginID(response.body().getAuthonticate().getLoginId());
        PreferencesManager.getInstance(context).setMOBILE(response.body().getAuthonticate().getMobile());
        PreferencesManager.getInstance(context).setNAME(response.body().getAuthonticate().getFirstName());
        PreferencesManager.getInstance(context).setLNAME(response.body().getAuthonticate().getLastName());
        PreferencesManager.getInstance(context).setTransactionPass(response.body().getAuthonticate().getEwalletPassword());
        PreferencesManager.getInstance(context).setPINCODE(response.body().getAuthonticate().getPinCode());
        PreferencesManager.getInstance(context).setEMAIL(response.body().getAuthonticate().getEmailID());
        PreferencesManager.getInstance(context).setIsKycVerified(response.body().getAuthonticate().getKycStatus());
        PreferencesManager.getInstance(context).setLoginPin(response.body().getAuthonticate().getGeneratePin());
        PreferencesManager.getInstance(context).setLastLogin(response.body().getAuthonticate().getLastLoginDate());
        PreferencesManager.getInstance(context).setInviteCode(response.body().getAuthonticate().getInviteCode());
        PreferencesManager.getInstance(context).setPROFILEPIC(response.body().getAuthonticate().getProfilepic());
        PreferencesManager.getInstance(context).setDOB(response.body().getAuthonticate().getDob());
        PreferencesManager.getInstance(context).setDOB_IMAGE(response.body().getBithdayUrl());

        PreferencesManager.getInstance(context).setENTITY_ID(response.body().getAuthonticate().getEntityId());
        PreferencesManager.getInstance(context).setKIT_NO(response.body().getAuthonticate().getKitno());
        PreferencesManager.getInstance(context).setM2PStatus(response.body().getAuthonticate().getM2pStatus());

        PreferencesManager.getInstance(context).setSPOID(response.body().getAuthonticate().getPbid());
        PreferencesManager.getInstance(context).setSPO_USERNAME(response.body().getAuthonticate().getPbUserName());
        PreferencesManager.getInstance(context).setSPO_PASSWORD(response.body().getAuthonticate().getPbPassword());

        registerUser();
    }
}