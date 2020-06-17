package com.digitalcashbag.utilities.tatasky;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.shopping.activities.PaymentActivityNew;
import com.digitalcashbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.BuildConfig;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.ResponsePincodeDetail;
import kkm.com.core.retrofit.Dialog_dismiss;
import kkm.com.core.utils.LoggerUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class TataSkyBooking extends BaseActivity implements Dialog_dismiss {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_first_name)
    TextInputEditText etFirstName;
    @BindView(R.id.input_first_name)
    TextInputLayout inputFirstName;
    @BindView(R.id.et_lastname)
    TextInputEditText etLastname;
    @BindView(R.id.input_last_name)
    TextInputLayout inputLastName;
    @BindView(R.id.img_contact1)
    TextView imgContact1;
    @BindView(R.id.et_mob_no)
    TextInputEditText etMobNo;
    @BindView(R.id.input_layout_mob_no)
    TextInputLayout inputLayoutMobNo;
    @BindView(R.id.et_buildingno)
    TextInputEditText etBuildingno;
    @BindView(R.id.input_layout_buildingno)
    TextInputLayout inputLayoutBuildingno;
    @BindView(R.id.img_contact)
    TextView imgContact;
    @BindView(R.id.et_street_name)
    TextInputEditText etStreetName;
    @BindView(R.id.input_layout_street_name)
    TextInputLayout inputLayoutStreetName;
    @BindView(R.id.et_pincode)
    TextInputEditText etPincode;
    @BindView(R.id.input_pin_lo)
    TextInputLayout inputPinLo;
    @BindView(R.id.et_state)
    TextInputEditText etState;
    @BindView(R.id.input_state_lo)
    TextInputLayout inputStateLo;
    @BindView(R.id.et_city)
    TextInputEditText etCity;
    @BindView(R.id.input_city_lo)
    TextInputLayout inputCityLo;
    @BindView(R.id.submit)
    Button submit;
    String product_price = "", product_id = "", region_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tatasky_booking_form);
        ButterKnife.bind(this);
        try {
            title.setText("Tatasky Booking");
            Bundle bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
            product_price = bundle.getString("product_price");
            product_id = bundle.getString("product_id");
            region_id = bundle.getString("region_id");

            etPincode.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() != 0 && s.length() == 6) {
                        hideKeyboard();
                        getStateCityName(etPincode.getText().toString().trim());
                    } else {
                        etCity.setText("");
                        etState.setText("");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.side_menu, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                onBackPressed();
                break;
            case R.id.submit:
                if (Validation()) {
                    try {

//                        TataskyBookingPayment dialog = new TataskyBookingPayment();
//                        Bundle b = new Bundle();
//                        b.putString("product_price", product_price);
//                        b.putString("product_id", product_id);
//                        b.putString("region_id", region_id);
//                        b.putString("FirstName", etFirstName.getText().toString());
//                        b.putString("Lastname", etLastname.getText().toString());
//                        b.putString("MobNo", etMobNo.getText().toString());
//                        b.putString("Buildingno", etBuildingno.getText().toString());
//                        b.putString("StreetName", etStreetName.getText().toString());
//                        b.putString("Pincode", etPincode.getText().toString());
//                        b.putString("State", etState.getText().toString());
//                        b.putString("City", etCity.getText().toString());
//                        dialog.setArguments(b);
//                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                        dialog.show(ft, TataskyBookingPayment.TAG);

                        Bundle b = new Bundle();
                        b.putString("total", product_price);
                        b.putString("from", "tata_sky");

                        b.putString("product_id", product_id);
                        b.putString("region_id", region_id);
                        b.putString("FirstName", etFirstName.getText().toString());
                        b.putString("Lastname", etLastname.getText().toString());
                        b.putString("MobNo", etMobNo.getText().toString());
                        b.putString("Buildingno", etBuildingno.getText().toString());
                        b.putString("StreetName", etStreetName.getText().toString());
                        b.putString("Pincode", etPincode.getText().toString());
                        b.putString("State", etState.getText().toString());
                        b.putString("City", etCity.getText().toString());


                        goToActivity(context, PaymentActivityNew.class, b);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
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
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponsePincodeDetail> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private boolean Validation() {
        try {
            if (etFirstName.getText().toString().length() == 0) {
                showError("Please enter First Name", etFirstName);
                return false;
            } else if (etLastname.getText().toString().length() == 0) {
                showError("Please enter Last Name", etLastname);
                return false;
            } else if (etMobNo.getText().toString().length() == 0) {
                showError("Please enter Mobile Number", etMobNo);
                return false;
            } else if (etBuildingno.getText().toString().length() == 0) {
                showError("Please enter Building No/ Flat No", etBuildingno);
                return false;
            } else if (etStreetName.getText().toString().length() == 0) {
                showError("Please enter Street Name/ Colony Name", etStreetName);
                return false;
            } else if (etPincode.getText().toString().length() == 0) {
                showError("Please enter PinCode", etPincode);
                return false;
            } else if (etState.getText().toString().length() == 0) {
                showError("Please enter valid PinCode", etPincode);
                return false;
            } else if (etCity.getText().toString().length() == 0) {
                showError("Please enter valid PinCode", etPincode);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void showError(String error_st, EditText editText) {
        Dialog error_dialog = new Dialog(this);
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
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onDismiss() {

    }


}
