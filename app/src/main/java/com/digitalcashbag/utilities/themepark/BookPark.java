package com.digitalcashbag.utilities.themepark;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.google.gson.JsonObject;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.ResponsePincodeDetail;
import kkm.com.core.model.response.themeParkResponse.ResponseBookTemePark;
import kkm.com.core.model.response.themeParkResponse.themeParkAvailabilityArray.CategoriesItem;
import kkm.com.core.model.response.themeParkResponse.themeParkAvailabilityArray.TourOptionsItem;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookPark extends BaseActivity {

    @BindView(R.id.first_name_et)
    TextInputEditText firstNameEt;
    @BindView(R.id.last_name_et)
    TextInputEditText lastNameEt;
    @BindView(R.id.mobile_edt_no)
    TextInputEditText mobileEdtNo;
    @BindView(R.id.email_et)
    TextInputEditText emailEt;
    @BindView(R.id.address_et1)
    TextInputEditText addressEt1;
    @BindView(R.id.address_et2)
    TextInputEditText addressEt2;
    @BindView(R.id.pin_et)
    TextInputEditText pinEt;
    @BindView(R.id.city_et)
    TextInputEditText cityEt;
    @BindView(R.id.state_et)
    TextInputEditText stateEt;
    @BindView(R.id.tv_tour_package)
    TextView tv_tour_package;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.qtyBox)
    NumberPicker qtyBox;

    private PopupMenu tour_menu, tour_category;
    private String tourItemId = "";
    private List<TourOptionsItem> tourItem = new ArrayList<>();
    private List<CategoriesItem> cateItem = new ArrayList<>();
    private String productId = "";
    private String pricingId = "";
    private String pricingName = "";
    private int catePosition;
    private float totalAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_park_book_form);
        ButterKnife.bind(this);

        title.setText("Theme Park Booking");

        productId = getIntent().getStringExtra("productId");
        Log.e("Product", "= " + pricingId);

        tour_menu = new PopupMenu(context, tv_tour_package);
        tour_category = new PopupMenu(context, tvCategory);

        tourItem = Cons.responseAvailabilityArrays.get(0).getData().getTourOptions();
        LoggerUtil.logItem(tourItem);

        for (int i = 0; i < tourItem.size(); i++) {
            tour_menu.getMenu().add(0, Integer.valueOf(tourItem.get(i).getId()), i, tourItem.get(i).getTitle());
        }

        pinEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0 && s.length() == 6) {
                    hideKeyboard();
                    getStateCityName(pinEt.getText().toString().trim());
                } else {
                    stateEt.setText("");
                    cityEt.setText("");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        qtyBox.setValueChangedListener((value, action) -> LoggerUtil.logItem(value));
    }

    @OnClick({R.id.tv_tour_package, R.id.btn_make_payment, R.id.side_menu, R.id.tv_category})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tour_package:
                tour_menu.setOnMenuItemClickListener(menuItem -> {
                    tourItemId = String.valueOf(menuItem.getItemId());
                    tv_tour_package.setText(menuItem.getTitle());
                    cateItem = tourItem.get(menuItem.getOrder()).getPricing().getCategories();
                    tvCategory.setText("");
                    pricingName = "";
                    pricingId = "";
                    tour_category.getMenu().clear();
                    for (int i = 0; i < cateItem.size(); i++) {
                        tour_category.getMenu().add(0, Integer.valueOf(cateItem.get(i).getId()), i, cateItem.get(i).getName());
                    }
                    return true;
                });
                tour_menu.show();
                break;
            case R.id.btn_make_payment:
                if (validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getParkBooked(productId, totalAmount, Cons.responseAvailabilityArrays.get(0).getData().getAvailabilities().getVisitDate());
                    }
                }
                break;
            case R.id.side_menu:
                onBackPressed();
                break;
            case R.id.tv_category:
                tour_category.setOnMenuItemClickListener(menuItem -> {

                    pricingId = String.valueOf(menuItem.getItemId());
                    pricingName = String.valueOf(menuItem.getTitle());
                    tvCategory.setText(pricingName);
                    catePosition = menuItem.getOrder();
                    qtyBox.setVisibility(View.VISIBLE);
                    qtyBox.setValue(0);
                    qtyBox.setMax(Integer.parseInt(cateItem.get(menuItem.getOrder()).getScale().get(0).getScaleMaxParticipant()));
                    qtyBox.setMin(0);

                    return true;
                });
                tour_category.show();
                break;
        }
    }

    private void getParkBooked(String productId, float amount, String date) {
        showLoading();
//        RequestThemeParkBook book = new RequestThemeParkBook();
//        book.setFK_MemID(PreferencesManager.getInstance(context).getUSERID());
//        book.setProductId(productId);
////        amount = 10; // For testing purpose otherwise amount is dynamic..
//        book.setAmount(String.valueOf(amount));
//        book.setAmountall(String.valueOf(amount));
//        book.setBenAddressline1(addressEt1.getText().toString());
//        book.setBenAddressline2(addressEt2.getText().toString());
//        book.setBenCity(cityEt.getText().toString());
//        book.setBenCountryid("IND");
//        book.setBenfName(firstNameEt.getText().toString());
//        book.setBenlName(lastNameEt.getText().toString());
//        book.setBenMobile(mobileEdtNo.getText().toString());
//        book.setBenPostcode(pinEt.getText().toString());
//        book.setDateOfTour(date);
//        book.setEmail(emailEt.getText().toString());
//        book.setNUMBER(PreferencesManager.getInstance(context).getMOBILE());
//        book.setPricingId(pricingId);
//        book.setPricingName(pricingName);
//        book.setPricingQty(String.valueOf(qtyBox.getValue()));
//        book.setState(stateEt.getText().toString());
//        book.setTime("10:00 AM");
//        book.setTourOptionsId(tourItemId);
//        book.setTourOptionsTitle(tv_tour_package.getText().toString());
//        book.setType("5");
//        LoggerUtil.logItem(book);

        JsonObject param = new JsonObject();
        param.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
        param.addProperty("ProductId", productId);
        param.addProperty("AMOUNT", String.valueOf(amount));
        param.addProperty("AMOUNT_ALL", String.valueOf(amount));
        param.addProperty("benAddressline1", addressEt1.getText().toString().trim());
        param.addProperty("benAddressline2", addressEt2.getText().toString().trim());
        param.addProperty("benCity", cityEt.getText().toString());
        param.addProperty("benCountryid", "IND");
        param.addProperty("benfName", firstNameEt.getText().toString().trim());
        param.addProperty("benlName", lastNameEt.getText().toString().trim());
        param.addProperty("benMobile", mobileEdtNo.getText().toString().trim());
        param.addProperty("benPostcode", pinEt.getText().toString().trim());
        param.addProperty("DateOfTour", date);
        param.addProperty("Email", emailEt.getText().toString());
        param.addProperty("NUMBER", PreferencesManager.getInstance(context).getMOBILE());
        param.addProperty("pricing_id", pricingId);
        param.addProperty("pricing_name", pricingName);
        param.addProperty("pricing_qty", String.valueOf(qtyBox.getValue()));
        param.addProperty("State", stateEt.getText().toString());
        param.addProperty("time", "10:00 AM");
        param.addProperty("tour_options_Id", tourItemId);
        param.addProperty("tour_options_title", tv_tour_package.getText().toString());
        param.addProperty("Type", "5");
        LoggerUtil.logItem(param);

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(param.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<JsonObject> call = apiServices_utilityV2.getThemeParkBooking(body);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    LoggerUtil.logItem(paramResponse);
                    List<ResponseBookTemePark> list = Utils.getList(paramResponse, ResponseBookTemePark.class);

                    if (response.body() != null && list.get(0).getResult().equalsIgnoreCase("0") &&
                            list.get(0).getError().equalsIgnoreCase("0") &&
                            list.get(0).getTrnxstatus().equalsIgnoreCase("7")) {
                        showMessage("Booking Confirm");
                        onBackPressed();
                    } else if (response.body() != null && list.get(0).getResult().equalsIgnoreCase("0") &&
                            list.get(0).getError().equalsIgnoreCase("0") &&
                            list.get(0).getTrnxstatus().equalsIgnoreCase("3")) {
                        showMessage("Booking Pending");
                        onBackPressed();
                    } else {
                        showMessage("Booking Failed");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideLoading();
            }
        });

    }


    private boolean validation() {
        if (Objects.requireNonNull(firstNameEt.getText()).toString().trim().length() == 0) {
            firstNameEt.setError("Enter beneficiary first name");
            return false;
        } else if (Objects.requireNonNull(lastNameEt.getText()).toString().trim().length() == 0) {
            lastNameEt.setError("Enter beneficiary last name");
            return false;
        } else if (Objects.requireNonNull(mobileEdtNo.getText()).toString().trim().length() != 10) {
            mobileEdtNo.setError("Enter beneficiary mobile number");
            return false;
        } else if (!Utils.isEmailAddress(emailEt.getText().toString().trim())) {
            emailEt.setError("Enter beneficiary email id");
            return false;
        } else if (Objects.requireNonNull(addressEt1.getText()).toString().trim().length() < 5) {
            addressEt1.setError("Enter beneficiary mobile number");
            return false;
        } else if (Objects.requireNonNull(cityEt.getText()).toString().trim().length() == 6) {
            pinEt.setError("Enter valid pin code");
            return false;
        } else if (tourItemId.equalsIgnoreCase("")) {
            tv_tour_package.setError("Choose tour");
            return false;
        } else if (pricingId.equalsIgnoreCase("")) {
            tvCategory.setError("Select Category");
            return false;
        } else if (qtyBox.getValue() < Integer.valueOf(cateItem.get(catePosition).getScale().get(0).getScaleMinParticipant())) {
            showMessage("Please select minimum number of category");
            return false;
        }

        totalAmount = qtyBox.getValue() * Float.parseFloat(cateItem.get(catePosition).getScale().get(0).getPrice());
        LoggerUtil.logItem("Total");
        LoggerUtil.logItem(totalAmount);
        LoggerUtil.logItem(qtyBox.getValue());
        return true;
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
                    cityEt.setText(response.body().getCityName());
                    stateEt.setText(response.body().getStateName());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponsePincodeDetail> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

}

