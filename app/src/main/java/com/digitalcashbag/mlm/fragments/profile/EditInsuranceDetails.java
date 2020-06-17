package com.digitalcashbag.mlm.fragments.profile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.digitalcashbag.R;
import com.google.gson.JsonObject;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.constants.UrlConstants;
import kkm.com.core.model.request.profilemlm.RequestInsuranceUpdate;
import kkm.com.core.model.response.profile.ApiUserProfile;
import kkm.com.core.model.response.profile.ResponseProfileUpdate;
import kkm.com.core.model.response.profile.ResponseViewProfile;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditInsuranceDetails extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.et_insurance_number)
    TextInputEditText etInsuranceNumber;
    @BindView(R.id.input_layout_insurance_number)
    TextInputLayout inputLayoutInsuranceNumber;
    @BindView(R.id.et_policy_holder_name)
    TextInputEditText etPolicyHolderName;
    @BindView(R.id.input_layout_policy_holder_name)
    TextInputLayout inputLayoutPolicyHolderName;
    @BindView(R.id.et_premium)
    TextInputEditText etPremium;
    @BindView(R.id.input_layout_premium)
    TextInputLayout inputLayoutPremium;
    @BindView(R.id.et_company_name)
    TextInputEditText etCompanyName;
    @BindView(R.id.input_layout_company_name)
    TextInputLayout inputLayoutCompanyName;
    @BindView(R.id.et_insur_nominee_name)
    TextInputEditText etInsurNomineeName;
    @BindView(R.id.input_layout_insur_nominee_name)
    TextInputLayout inputLayoutInsurNomineeName;
    @BindView(R.id.two_wheeler_rb)
    RadioButton twoWheelerRb;
    @BindView(R.id.four_wheeler_rb)
    RadioButton fourWheelerRb;
    @BindView(R.id.rg_insu_type)
    RadioGroup rgInsuType;
    @BindView(R.id.et_policy_number)
    TextInputEditText etPolicyNumber;
    @BindView(R.id.input_layout_policy_number)
    TextInputLayout inputLayoutPolicyNumber;
    @BindView(R.id.et_vehicle_number)
    TextInputEditText etVehicleNumber;
    @BindView(R.id.input_vehicle_number)
    TextInputLayout inputVehicleNumber;
    @BindView(R.id.et_purchased_year)
    TextInputEditText etPurchasedYear;
    @BindView(R.id.input_layout_purchased_year)
    TextInputLayout inputLayoutPurchasedYear;
    @BindView(R.id.et_gen_insur_premium)
    TextInputEditText etGenInsurPremium;
    @BindView(R.id.input_layout_gen_insur_premium)
    TextInputLayout inputLayoutGenInsurPremium;
    @BindView(R.id.et_expiry_date)
    TextInputEditText etExpiryDate;
    @BindView(R.id.input_expiry_date)
    TextInputLayout inputExpiryDate;
    @BindView(R.id.et_gen_insu_company_name)
    TextInputEditText etGenInsuCompanyName;
    @BindView(R.id.input_layout_gen_insu_company_name)
    TextInputLayout inputLayoutGenInsuCompanyName;
    @BindView(R.id.submit)
    Button submit;
    Calendar cal = Calendar.getInstance();
    private int mYear, mMonth, mDay;
    private String vehicle_type = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_insurance_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        setData();

        rgInsuType.setOnCheckedChangeListener((radioGroup, i) -> {
            if (twoWheelerRb.isChecked()) {
                vehicle_type = "Two Wheeler";
            } else if (fourWheelerRb.isChecked()) {
                vehicle_type = "Four Wheeler";
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    void setData() {
        try {
            ApiUserProfile profile = UrlConstants.profile.getApiUserProfile();
            etInsuranceNumber.setText(profile.getInsuranceNo());
            etPolicyHolderName.setText(profile.getPolicyHolderName());
            etPremium.setText(profile.getPremium());
            etCompanyName.setText(profile.getInsCompanyName());
            etInsurNomineeName.setText(profile.getInsNomineeName());
            if (profile.getInsuranceType() != null) {
                if (profile.getInsuranceType().equalsIgnoreCase("Two Wheeler")) {
                    twoWheelerRb.setChecked(true);
                } else if (profile.getInsuranceType().equalsIgnoreCase("Four Wheeler")) {
                    fourWheelerRb.setChecked(true);
                }
                etPolicyNumber.setText(profile.getPolicyNo());
                etVehicleNumber.setText(profile.getVehicleNo());
                etPurchasedYear.setText(profile.getPurchasedYear());
                etGenInsurPremium.setText(profile.getGenPremium());
                etExpiryDate.setText(profile.getExpiryDate());
                etGenInsuCompanyName.setText(profile.getGenCompanyName());
            } else {
                twoWheelerRb.setChecked(false);
                fourWheelerRb.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.et_expiry_date, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_expiry_date:
                mYear = cal.get(Calendar.YEAR);
                mMonth = cal.get(Calendar.MONTH);
                mDay = cal.get(Calendar.DAY_OF_MONTH);
                datePicker(etExpiryDate);
                break;
            case R.id.submit:
                if (validate()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getInsuranceUpdate();
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                }
                break;
        }
    }

    void getInsuranceUpdate() {
        RequestInsuranceUpdate update = new RequestInsuranceUpdate();
        update.setFkMemId(PreferencesManager.getInstance(context).getUSERID());
        update.setInsuranceNo(etInsuranceNumber.getText().toString());
        update.setPolicyHolderName(etPolicyHolderName.getText().toString());
        update.setPremium(etPremium.getText().toString());
        update.setCompanyName(etCompanyName.getText().toString());
        update.setNomineeName(etInsurNomineeName.getText().toString());
        update.setInsuranceType(vehicle_type);
        update.setPolicyNo(etPolicyNumber.getText().toString());
        update.setVehicleNo(etVehicleNumber.getText().toString());
        update.setPurchasedYear(etPurchasedYear.getText().toString());
        update.setGenPremium(etGenInsurPremium.getText().toString());
        update.setExpiryDate(etExpiryDate.getText().toString());
        update.setGenCompanyName(etGenInsuCompanyName.getText().toString());
        showLoading();
        LoggerUtil.logItem(update);
        Call<ResponseProfileUpdate> call = apiServices.getInsuranceUpdate(update);
        call.enqueue(new Callback<ResponseProfileUpdate>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProfileUpdate> call, @NonNull Response<ResponseProfileUpdate> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        showMessage("Updated Successfully");
                        getUserProfile();
                    } else {
                        showMessage(response.body().getResponse());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseProfileUpdate> call, @NonNull Throwable t) {
                hideLoading();
            }
        });

    }

    private boolean validate() {
        if (etInsuranceNumber.getText().toString().trim().length() < 2) {
            etInsuranceNumber.setError("Enter valid insurance number");
            return false;
        } else if (etPolicyHolderName.getText().toString().trim().length() == 0) {
            etPolicyHolderName.setError("Enter valid policy holder name");
            return false;
        } else if (etPremium.getText().toString().trim().length() == 0) {
            etPremium.setError("Enter premium");
            return false;
        } else if (etCompanyName.getText().toString().trim().length() == 0) {
            etCompanyName.setError("Enter company name");
            return false;
        }
        return true;
    }

    private void datePicker(EditText e1) {
        try {
            Calendar cal = Calendar.getInstance();
            int mYear, mMonth, mDay;
            mYear = cal.get(Calendar.YEAR);
            mMonth = cal.get(Calendar.MONTH);
            mDay = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DatePickerDialogTheme, (view, year, monthOfYear, dayOfMonth) -> {
                e1.setText(Utils.changeDateFormat(dayOfMonth, monthOfYear, year));
            }, mYear, mMonth, mDay);
//            datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
            datePickerDialog.show();
        } catch (Error | Exception e) {
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
