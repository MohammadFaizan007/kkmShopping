package com.digitalcashbag.shopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.shopping.Cons;
import com.google.gson.JsonObject;
import com.digitalcashbag.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.GetAddressListResponse;
import kkm.com.core.model.response.transportMode.GetTransportDetailsItem;
import kkm.com.core.model.response.transportMode.ResponseTransportModes;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Abhishek Srivastava on 12/6/2017.
 */


public class Checkout extends BaseActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.fullname_bill)
    TextView fullName_bill;
    @BindView(R.id.address_bill)
    TextView address_bill;
    @BindView(R.id.landmark_bill)
    TextView landmark_bill;
    @BindView(R.id.pinCode_bill)
    TextView pinCode_bill;
    @BindView(R.id.addressType_bill)
    TextView addressType_bill;
    @BindView(R.id.cv_address_default_bill)
    CardView cv_address_default_bill;
    @BindView(R.id.same_as_billing)
    CheckBox same_as_billing;
    @BindView(R.id.fullname_ship)
    TextView fullName_ship;
    @BindView(R.id.address_ship)
    TextView address_ship;
    @BindView(R.id.landmark_ship)
    TextView landmark_ship;
    @BindView(R.id.pinCode_ship)
    TextView pinCode_ship;
    @BindView(R.id.addressType_ship)
    TextView addressType_ship;
    @BindView(R.id.cv_address_default_ship)
    CardView cv_address_default_ship;
    @BindView(R.id.delivery_option)
    TextView delivery_option;
    @BindView(R.id.courierPrice)
    TextView courier_price;
    @BindView(R.id.delivery_option_txt)
    TextView delivery_option_txt;
    @BindView(R.id.tv_payment)
    TextView tv_payment;
    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;
    @BindView(R.id.change_billing)
    TextView changeBilling;
    @BindView(R.id.change_shipping)
    TextView changeShipping;
    @BindView(R.id.add_new_address)
    TextView addNewAddress;
    @BindView(R.id.add_shipping_address)
    TextView add_shipping_address;


    private String total = "0", totalItems = "0", subTotal = "0", courierPrice = "0";


    private String shipAddress_Id = "";
    private String billAddress_Id = "";

    PopupMenu popup_deliveryOption;
    private String transportId = "0";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_items);
        ButterKnife.bind(this);

        title.setText("Checkout");
        sideMenu.setOnClickListener(this);
        tv_payment.setOnClickListener(this);
        delivery_option.setOnClickListener(this);
        changeBilling.setOnClickListener(this);
        changeShipping.setOnClickListener(this);

        same_as_billing.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                shipAddress_Id = billAddress_Id;
                fullName_ship.setText(fullName_bill.getText().toString());
                address_ship.setText(address_bill.getText().toString());
                landmark_ship.setText(String.format("%s", landmark_bill.getText().toString()).trim());
                pinCode_ship.setText(String.format("%s", pinCode_bill.getText().toString()));
                addressType_ship.setText(addressType_bill.getText().toString());
                cv_address_default_ship.setVisibility(View.VISIBLE);
                checkPinCode(pinCode_ship.getText().toString().substring(pinCode_ship.getText().toString().length() - 6));
                add_shipping_address.setVisibility(View.GONE);
            } else {
                setDefaultAddress();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            total = intent.getStringExtra("TOTAL");
            totalItems = intent.getStringExtra("SIZE");
            courierPrice = intent.getStringExtra("courierPrice");
            tv_total_amount.setText(String.format("₹%s", total));
        }

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            GetAddressList();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

        addNewAddress.setOnClickListener(v -> {
            PreferencesManager.getInstance(context).setAddressMode("B");
            Bundle bundle = new Bundle();
            bundle.putBoolean("address", false);
            goToActivity(context, AddressManager.class, bundle);
        });

        add_shipping_address.setOnClickListener(v -> {
            PreferencesManager.getInstance(context).setAddressMode("S");
            goToActivity(context, AddressManager.class, null);
        });

        popup_deliveryOption = new PopupMenu(context, delivery_option);
        getTransportModes();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.change_billing:
                PreferencesManager.getInstance(context).setAddressMode("B");
                goToActivity(context, AddressManager.class, null);
                break;
            case R.id.change_shipping:
                PreferencesManager.getInstance(context).setAddressMode("S");
                goToActivity(context, AddressManager.class, null);
                break;
            case R.id.side_menu:
                onBackPressed();
                break;
            case R.id.tv_payment:
                proceedToFinalPayment();
                break;
            case R.id.delivery_option:
                popup_deliveryOption.setOnMenuItemClickListener(item -> {
                    delivery_option.setText(item.getTitle());
                    if (item.getTitle().toString().equalsIgnoreCase("By Courier")) {
                        if (!courierPrice.equalsIgnoreCase("")) {
                            subTotal = String.valueOf(Float.parseFloat(total) + Float.parseFloat(courierPrice));
                            tv_total_amount.setText(String.format("₹%s", subTotal));
                            courier_price.setText(String.format("Courier Price: %s", courierPrice));
                            courier_price.setVisibility(View.VISIBLE);
                            transportId = items.get(item.getOrder() - 1).getPkTransportId();
                        } else {
                            courier_price.setText(String.format("Courier Price: %s", "0"));
                        }
                    } else {
                        subTotal = total;
                        courier_price.setVisibility(View.GONE);
                        tv_total_amount.setText(String.format("₹%s", total));
                        transportId = items.get(item.getOrder() - 1).getPkTransportId();
                    }
                    return true;
                });
                popup_deliveryOption.show();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity(context);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void GetAddressList() {
        showLoading();
        JsonObject mainjson = new JsonObject();
        mainjson.addProperty("PK_CustID", PreferencesManager.getInstance(context).getUSERID());
        LoggerUtil.logItem(mainjson);
        Call<GetAddressListResponse> addressListCall = apiServices_shoopping.getAddressList(mainjson);
        addressListCall.enqueue(new Callback<GetAddressListResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetAddressListResponse> call, @NonNull Response<GetAddressListResponse> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                Cons.GetAddressList_arrylst = new ArrayList<>();
                if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                    Cons.GetAddressList_arrylst = response.body().getGetAddressList();
                    setDefaultAddress();
                } else {
                    PreferencesManager.getInstance(context).setAddressMode("B");
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("address", false);
                    goToActivity(context, AddressManager.class, bundle);
//                    Toast.makeText(context, "No Records Found.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<GetAddressListResponse> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    public void setDefaultAddress() {
        try {
            boolean billing = false;
            boolean shipping = false;
            clearAllAddress();
            if (Cons.GetAddressList_arrylst.size() == 0) {
                cv_address_default_bill.setVisibility(View.GONE);
                cv_address_default_ship.setVisibility(View.GONE);
                addNewAddress.setVisibility(View.VISIBLE);
                add_shipping_address.setVisibility(View.VISIBLE);

            } else {
                addNewAddress.setVisibility(View.GONE);
                add_shipping_address.setVisibility(View.GONE);

                for (int position = 0; position < Cons.GetAddressList_arrylst.size(); position++) {
                    if (Cons.GetAddressList_arrylst.get(position).getIspermanent().equalsIgnoreCase("1") &&
                            Cons.GetAddressList_arrylst.get(position).getAddressMode().equalsIgnoreCase("B")) {
                        billAddress_Id = Cons.GetAddressList_arrylst.get(position).getShipAddressId();
                        fullName_bill.setText(Cons.GetAddressList_arrylst.get(position).getFullName());
                        pinCode_bill.setText(String.format("Pin: %s", Cons.GetAddressList_arrylst.get(position).getPincode()));
                        landmark_bill.setText(String.format("%s %s,%s", Cons.GetAddressList_arrylst.get(position).getLandmark(),
                                Cons.GetAddressList_arrylst.get(position).getCity(),
                                Cons.GetAddressList_arrylst.get(position).getState()).trim());
                        address_bill.setText(Cons.GetAddressList_arrylst.get(position).getAddress());
                        addressType_bill.setText(Cons.GetAddressList_arrylst.get(position).getAddressType());
                        cv_address_default_bill.setVisibility(View.VISIBLE);

                        billing = true;

                    } else if (Cons.GetAddressList_arrylst.get(position).getIspermanent().equalsIgnoreCase("1") &&
                            Cons.GetAddressList_arrylst.get(position).getAddressMode().equalsIgnoreCase("S")) {
                        shipping = true;
                        if (same_as_billing.isChecked()) {
                            shipAddress_Id = billAddress_Id;
                            fullName_ship.setText(fullName_bill.getText().toString());
                            address_ship.setText(address_bill.getText().toString());
                            landmark_ship.setText(landmark_bill.getText().toString());
                            pinCode_ship.setText(String.format("%s", pinCode_bill.getText().toString()));
                            addressType_ship.setText(addressType_bill.getText().toString());
                            cv_address_default_ship.setVisibility(View.VISIBLE);
                            checkPinCode(pinCode_ship.getText().toString().substring(pinCode_ship.getText().toString().length() - 6));
                        } else {
                            shipAddress_Id = Cons.GetAddressList_arrylst.get(position).getShipAddressId();
                            fullName_ship.setText(Cons.GetAddressList_arrylst.get(position).getFullName());
                            pinCode_ship.setText(String.format("Pin :%s", Cons.GetAddressList_arrylst.get(position).getPincode()));
                            landmark_ship.setText(String.format("%s %s,%s", Cons.GetAddressList_arrylst.get(position).getLandmark(),
                                    Cons.GetAddressList_arrylst.get(position).getCity(),
                                    Cons.GetAddressList_arrylst.get(position).getState()).trim());
                            address_ship.setText(String.format("Pin: %s", Cons.GetAddressList_arrylst.get(position).getAddress()));
                            addressType_ship.setText(Cons.GetAddressList_arrylst.get(position).getAddressType());
                            cv_address_default_ship.setVisibility(View.VISIBLE);

                            checkPinCode(Cons.GetAddressList_arrylst.get(position).getPincode());
                        }
                    }
                }
                if (billing) {
                    same_as_billing.setVisibility(View.VISIBLE);
                } else {
                    same_as_billing.setVisibility(View.GONE);
                    addNewAddress.setVisibility(View.VISIBLE);
                }

                if (shipping) {
                    add_shipping_address.setVisibility(View.GONE);
                } else {
                    add_shipping_address.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void clearAllAddress() {
        billAddress_Id = "";
        shipAddress_Id = "";
        fullName_bill.setText("");
        pinCode_bill.setText("");
        landmark_bill.setText("");
        address_bill.setText("");
        addressType_bill.setText("");
        addNewAddress.setVisibility(View.GONE);
        same_as_billing.setVisibility(View.GONE);
        fullName_ship.setText("");
        pinCode_ship.setText("");
        landmark_ship.setText("");
        address_ship.setText("");
        addressType_ship.setText("");
        cv_address_default_ship.setVisibility(View.GONE);
        cv_address_default_bill.setVisibility(View.GONE);
        same_as_billing.setChecked(false);
    }

    private void proceedToFinalPayment() {
        if (total != null) {
            if (!billAddress_Id.equalsIgnoreCase("")) {
                if (!shipAddress_Id.equalsIgnoreCase("")) {
                    if (!transportId.equalsIgnoreCase("0")) {
                        Bundle b = new Bundle();
//                        InvoiceNo,Mode
                        b.putString("total", subTotal);
                        b.putString("courierPrice", courierPrice);
                        b.putString("product_total", total);
                        if (delivery_option.getText().toString().equalsIgnoreCase("By Courier")) {
                            b.putBoolean("with", true);
                        } else {
                            b.putBoolean("with", false);
                        }
                        b.putString("from", "shopping");
                        b.putString("SHIPPING_ID", shipAddress_Id);
                        b.putString("BILLING_ID", billAddress_Id);
                        b.putString("TOTALITEMS", totalItems);
                        b.putString("Mode", transportId);
                        goToActivity(context, PaymentActivityNew.class, b);

                    } else {
                        Toast.makeText(this, "Please select delivery option.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please select shipping address.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please select billing address.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Total amount is zero.", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setDefaultAddress();
    }

    private ArrayList<GetTransportDetailsItem> items = new ArrayList<>();

    private void getTransportModes() {
        Call<ResponseTransportModes> call = apiServices_shoopping.getTransportModes();
        call.enqueue(new Callback<ResponseTransportModes>() {
            @Override
            public void onResponse(@NotNull Call<ResponseTransportModes> call, @NotNull Response<ResponseTransportModes> response) {
                LoggerUtil.logItem(response.body());
                if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                    items = (ArrayList<GetTransportDetailsItem>) response.body().getGetTransportDetails();
                    popup_deliveryOption.getMenu().clear();
                    popup_deliveryOption.getMenu().add(1, 0, 0, "Select Delivery Option");
                    popup_deliveryOption.getMenu().getItem(0).setEnabled(false);
                    for (int i = 0; i < items.size(); i++) {
                        popup_deliveryOption.getMenu().add(1, 0, i + 1, items.get(i).getTransportMode());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseTransportModes> call, @NotNull Throwable t) {

            }
        });
    }

    private void checkPinCode(String pincode) {

        JsonObject param = new JsonObject();
        param.addProperty("pincode", pincode);

        LoggerUtil.logItem(param);

        Call<JsonObject> call = apiServices_shoopping.checkPinCode(param);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                LoggerUtil.logItem(response.body());
                if (response.body() != null && response.body().get("response").getAsString().equalsIgnoreCase("Success")) {
                    delivery_option_txt.setVisibility(View.VISIBLE);
                    if (response.body().get("isDelhi").getAsBoolean()) {
                        delivery_option_txt.setText("Delivery Time: 1-2 days.");
                    } else {
                        delivery_option_txt.setText("Delivery Time: 3-4 days.");
                    }
                } else {
                    delivery_option_txt.setVisibility(View.GONE);
                }
            }


            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

            }
        });
    }

}