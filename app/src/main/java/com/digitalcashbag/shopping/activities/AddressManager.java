package com.digitalcashbag.shopping.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.shopping.Cons;
import com.google.gson.JsonObject;
import com.digitalcashbag.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.AddNewAddressResponse;
import kkm.com.core.model.response.DeleteAddressResponse;
import kkm.com.core.model.response.GetAddressListItem;
import kkm.com.core.model.response.GetAddressListResponse;
import kkm.com.core.model.response.SetDefaultAddressResponse;
import kkm.com.core.model.response.register.ResponseDistrict;
import kkm.com.core.model.response.register.ResponseState;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

/**
 * Created by Abhishek Srivastava on 12/6/2017.
 */

public class AddressManager extends BaseActivity {

    String addressMode = "";
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.fullname_et)
    EditText fullname_et;
    @BindView(R.id.input_fullnmae)
    TextInputLayout input_fullnmae;
    @BindView(R.id.address_et)
    EditText address_et;
    @BindView(R.id.input_address)
    TextInputLayout input_address;
    @BindView(R.id.mobile_et)
    EditText mobile_et;
    @BindView(R.id.input_mobile)
    TextInputLayout input_mobile;
    @BindView(R.id.landmark_et)
    EditText landmark_et;
    @BindView(R.id.input_landmark)
    TextInputLayout input_landmark;
    @BindView(R.id.is_permanent_check)
    CheckBox is_permanent_check;
    @BindView(R.id.state_et)
    AutoCompleteTextView state_et;
    @BindView(R.id.input_state)
    TextInputLayout input_state;
    @BindView(R.id.city_et)
    AutoCompleteTextView city_et;
    @BindView(R.id.input_city)
    TextInputLayout input_city;
    @BindView(R.id.pincode_et)
    EditText pincode_et;
    @BindView(R.id.input_pincode)
    TextInputLayout input_pincode;
    @BindView(R.id.et_addressType)
    EditText et_addressType;
    @BindView(R.id.input_addresstype)
    TextInputLayout input_addresstype;
    @BindView(R.id.btn_add_address)
    TextView btn_add_address;
    @BindView(R.id.cv_add_address)
    CardView cv_add_address;
    @BindView(R.id.lv_saved_address)
    ListView lv_saved_address;
    @BindView(R.id.cv_savedaddr)
    CardView cv_savedaddr;
    @BindView(R.id.add_new_Address)
    TextView add_new_Address;
    @BindView(R.id.noAddress)
    TextView noAddress;
    boolean editAddress = false;
    private String fullnme_st, mobile_st, pincode_st, addresstype_st, address_st, state_st, city_st, isPermanent = "0";
    private List<GetAddressListItem> addressList;
    private String addressId = "";

    private List<String> stateName = new ArrayList<>();
    private List<String> cityName = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_manager);
        ButterKnife.bind(this);

        addressMode = PreferencesManager.getInstance(context).getAddressMode();
        add_new_Address.setTag(1);
        title.setText("Address");
        Bundle param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        if (param != null) {

            boolean bool = param.getBoolean("address");
            LoggerUtil.logItem(bool);
            if (!bool) {
                cv_add_address.setVisibility(View.VISIBLE);
                cv_savedaddr.setVisibility(View.GONE);
                lv_saved_address.setVisibility(View.GONE);
                add_new_Address.setVisibility(View.GONE);
                mobile_et.setText(PreferencesManager.getInstance(context).getMOBILE());
                address_et.setText(PreferencesManager.getInstance(context).getAddress());
                state_et.setText(PreferencesManager.getInstance(context).getSate());
                city_et.setText(PreferencesManager.getInstance(context).getCity());
                pincode_et.setText(PreferencesManager.getInstance(context).getPINCODE());
                fullname_et.setText(String.format("%s %s", PreferencesManager.getInstance(context).getNAME(), PreferencesManager.getInstance(context).getLNAME()));
            } else {
                setAddressAdapter();
            }
        } else {
            setAddressAdapter();
        }

        btn_add_address.setOnClickListener(this);
        add_new_Address.setOnClickListener(this);
        sideMenu.setOnClickListener(this);
        et_addressType.setOnClickListener(this);
        is_permanent_check = findViewById(R.id.is_permanent_check);
        is_permanent_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()) {
                isPermanent = "1";
            } else {
                isPermanent = "0";
            }
        });

        is_permanent_check.setChecked(true);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getState();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

        state_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                city_et.setText("");
                cityName.clear();
                city_et.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, cityName));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        state_et.setOnItemClickListener((parent, view, position, id) -> {
            city_et.setText("");
            cityName.clear();
            city_et.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, cityName));
            getCity(state_et.getText().toString().trim());
        });


    }

    void setAddressAdapter() {

        addressList = new ArrayList<>();

        if (Cons.GetAddressList_arrylst != null) {
            for (int i = 0; i < Cons.GetAddressList_arrylst.size(); i++) {
                LoggerUtil.logItem(addressMode);
                LoggerUtil.logItem(Cons.GetAddressList_arrylst.get(i).getAddressMode());

                if (Cons.GetAddressList_arrylst.get(i).getAddressMode().equalsIgnoreCase(addressMode)) {
                    addressList.add(Cons.GetAddressList_arrylst.get(i));
                }
            }
        }

        LoggerUtil.logItem(addressList.size());
        LoggerUtil.logItem("ADDRESS LIST");
        cv_add_address.setVisibility(View.GONE);
        lv_saved_address.setVisibility(View.VISIBLE);
        cv_savedaddr.setVisibility(View.VISIBLE);
        add_new_Address.setVisibility(View.VISIBLE);
        if (addressList != null && addressList.size() > 0) {
            Addresses_Adapter addresses_adapter = new Addresses_Adapter(context);
            lv_saved_address.setAdapter(addresses_adapter);
            addresses_adapter.notifyDataSetChanged();
            noAddress.setVisibility(View.GONE);
            lv_saved_address.setVisibility(View.VISIBLE);
        } else {
            lv_saved_address.setVisibility(View.GONE);
            noAddress.setVisibility(View.VISIBLE);
        }
    }

    private boolean Validation() {
        try {
            fullnme_st = fullname_et.getText().toString().trim();
            mobile_st = mobile_et.getText().toString().trim();
            pincode_st = pincode_et.getText().toString().trim();
            addresstype_st = et_addressType.getText().toString().trim();
            address_st = address_et.getText().toString().trim();
            state_st = state_et.getText().toString().trim();
            city_st = city_et.getText().toString().trim();

            /*fullname*/
            if (fullnme_st.length() == 0) {
                input_fullnmae.setError(getString(R.string.hint_fullname));
                requestFocus(fullname_et);
                return false;
            } else {
                input_fullnmae.setErrorEnabled(false);
            }
            /*Address*/
            if (address_st.length() == 0) {
                input_address.setError(getString(R.string.hint_address));
                requestFocus(address_et);
                return false;
            } else {
                input_address.setErrorEnabled(false);
            }
            /*mobile number*/
            if (mobile_st.length() == 0) {
                input_mobile.setError(getString(R.string.valid_mob_no_err));
                requestFocus(mobile_et);
                return false;
            } else {
                input_mobile.setErrorEnabled(false);
            }
            /*state*/
            if (state_st.length() == 0) {
                input_state.setError(getString(R.string.hint_state));
                requestFocus(state_et);
                return false;
            } else {
                input_state.setErrorEnabled(false);
            }
            /*City*/
            if (city_st.length() == 0) {
                input_city.setError(getString(R.string.hint_city));
                requestFocus(city_et);
                return false;
            } else {
                input_city.setErrorEnabled(false);
            }
            /*pincode*/
            if (pincode_st.length() == 0) {
                input_pincode.setError(getString(R.string.hint_pincode));
                requestFocus(pincode_et);
                return false;
            } else {
                input_pincode.setErrorEnabled(false);
            }
            /*address Type*/
            if (addresstype_st.length() == 0) {
                input_addresstype.setError("Please select Address type");
                requestFocus(et_addressType);
                return false;
            } else {
                input_addresstype.setErrorEnabled(false);
            }
        } catch (Error | Exception e) {
            hideLoading();
            e.printStackTrace();
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void updateAddress(GetAddressListItem item) {
        cv_add_address.setVisibility(View.VISIBLE);
        cv_savedaddr.setVisibility(View.GONE);
        lv_saved_address.setVisibility(View.GONE);
        add_new_Address.setVisibility(View.GONE);
        add_new_Address.setTag(2);

        fullname_et.setText(item.getFullName());
        address_et.setText(item.getAddress());
        mobile_et.setText(PreferencesManager.getInstance(context).getMOBILE());

        landmark_et.setText(String.format("%s %s, %s", item.getLandmark(), item.getCity(), item.getState()));
        state_et.setText(item.getState());
        city_et.setText(item.getCity());
        pincode_et.setText(item.getPincode());
        et_addressType.setText(item.getAddressType());
        editAddress = true;
        addressId = item.getShipAddressId();

        if (item.getIspermanent().equalsIgnoreCase("1")) {
            is_permanent_check.setChecked(true);
        } else {
            is_permanent_check.setChecked(false);
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_add_address:
                if (Validation()) {
                    if (editAddress) {
                        AddNewAddress(addressId);
                    } else {
                        AddNewAddress("0");
                    }

                }
                break;
            case R.id.add_new_Address:
                if (Integer.parseInt(add_new_Address.getTag().toString()) == 1) {
                    cv_add_address.setVisibility(View.VISIBLE);
                    cv_savedaddr.setVisibility(View.GONE);
                    lv_saved_address.setVisibility(View.GONE);
                    add_new_Address.setTag(2);
                    add_new_Address.setVisibility(View.GONE);
                    mobile_et.setText(PreferencesManager.getInstance(context).getMOBILE());
                } else {
                    cv_add_address.setVisibility(View.GONE);
                    lv_saved_address.setVisibility(View.VISIBLE);
                    cv_savedaddr.setVisibility(View.VISIBLE);
                    add_new_Address.setTag(1);
                }
                break;
            case R.id.side_menu:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.et_addressType:
                PopupMenu popup = new PopupMenu(context, et_addressType);
                popup.getMenuInflater().inflate(R.menu.addresstypemenu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        et_addressType.setText(item.getTitle());
                        return true;
                    }
                });
                popup.show();
                break;
        }
    }

    private void GetAddressList(final String from) {
        try {
            showLoading();
            JsonObject json = new JsonObject();
            json.addProperty("PK_CustID", PreferencesManager.getInstance(context).getUSERID());
            LoggerUtil.logItem(json);

            Call<GetAddressListResponse> chargeCall = apiServices_shoopping.getAddressList(json);
            chargeCall.enqueue(new Callback<GetAddressListResponse>() {
                @Override
                public void onResponse(@NonNull Call<GetAddressListResponse> call, @NonNull Response<GetAddressListResponse> response) {
                    hideLoading();
                    try {
                        LoggerUtil.logItem(response.body());
                        Cons.GetAddressList_arrylst = new ArrayList<>();
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {
                            try {
                                Cons.GetAddressList_arrylst = response.body().getGetAddressList();
                                if (from.equalsIgnoreCase("deliver")) {
                                    finish();
                                } else {
                                    lv_saved_address.setVisibility(View.VISIBLE);
                                    setAddressAdapter();
                                }
                            } catch (Exception e) {
                                hideLoading();
                                e.printStackTrace();
                                lv_saved_address.setVisibility(View.VISIBLE);
                                setAddressAdapter();
                            }

                        } else {
                            cv_add_address.setVisibility(View.VISIBLE);
                            cv_savedaddr.setVisibility(View.GONE);
                            lv_saved_address.setVisibility(View.GONE);

                            mobile_et.setText(PreferencesManager.getInstance(context).getMOBILE());
                            address_et.setText(PreferencesManager.getInstance(context).getAddress());
                            state_et.setText(PreferencesManager.getInstance(context).getSate());
                            city_et.setText(PreferencesManager.getInstance(context).getCity());
                            pincode_et.setText(PreferencesManager.getInstance(context).getPINCODE());
                            fullname_et.setText(String.format("%s %s", PreferencesManager.getInstance(context).getNAME(), PreferencesManager.getInstance(context).getLNAME()));

                            Toast.makeText(context, "No Records Found.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GetAddressListResponse> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            Log.e("", "");

        }
    }

    private void AddNewAddress(String addressID) {
        showLoading();
        JsonObject json = new JsonObject();
        json.addProperty("PK_CustID", PreferencesManager.getInstance(context).getUSERID());
        json.addProperty("AddressId", addressID);
        json.addProperty("Pincode", pincode_et.getText().toString().trim());
        json.addProperty("FullName", fullname_et.getText().toString().trim());
        json.addProperty("Address", address_et.getText().toString().trim());
        json.addProperty("Landmark", landmark_et.getText().toString().trim());
        json.addProperty("City", city_et.getText().toString().trim());
        json.addProperty("State", state_et.getText().toString().trim());
        json.addProperty("MobileNo", mobile_et.getText().toString().trim());
        json.addProperty("AlternateMobileNo", "");
        json.addProperty("AddressType", et_addressType.getText().toString().trim());
        json.addProperty("Ispermanent", isPermanent);//  1-> id default   0-> nothing
        json.addProperty("AddressMode", addressMode);//  S-> Shipping Address   B-> Billing Address
        LoggerUtil.logItem(json);
        Call<AddNewAddressResponse> chargeCall = apiServices_shoopping.addNewAddress(json);
        chargeCall.enqueue(new Callback<AddNewAddressResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddNewAddressResponse> call, @NonNull Response<AddNewAddressResponse> response) {
                hideLoading();
                try {
                    LoggerUtil.logItem(response.body());
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        cv_add_address.setVisibility(View.GONE);
                        add_new_Address.setTag(1);
                        pincode_et.setText("");
                        fullname_et.setText("");
                        address_et.setText("");
                        landmark_et.setText("");
                        city_et.setText("");
                        state_et.setText("");
                        mobile_et.setText("");
                        et_addressType.setText("");
                        addressId = "";
                        editAddress = false;
                        mobile_et.setText(PreferencesManager.getInstance(context).getMOBILE());
                        address_et.setText(PreferencesManager.getInstance(context).getAddress());
                        state_et.setText(PreferencesManager.getInstance(context).getSate());
                        city_et.setText(PreferencesManager.getInstance(context).getCity());

                        if (addressID.equalsIgnoreCase(""))
                            GetAddressList("");
                        else
                            GetAddressList("deliver");

                    } else {
                        Toast.makeText(context, response.body().getResponse(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(@NonNull Call<AddNewAddressResponse> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void DeleteAddress(int i) {
        showLoading();
        JsonObject json = new JsonObject();
        json.addProperty("PK_CustID", PreferencesManager.getInstance(context).getUSERID());
        json.addProperty("Ship_AddressID", addressList.get(i).getShipAddressId());
        json.addProperty("AddressMode", addressList.get(i).getAddressMode());
        Call<DeleteAddressResponse> chargeCall = apiServices_shoopping.getDeleteAddress(json);
        chargeCall.enqueue(new Callback<DeleteAddressResponse>() {
            @Override
            public void onResponse(@NonNull Call<DeleteAddressResponse> call, @NonNull Response<DeleteAddressResponse> response) {
                hideLoading();
                try {
                    LoggerUtil.logItem(response.body());
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        GetAddressList("");
                    } else {
                        Toast.makeText(context, response.body().getResponse(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeleteAddressResponse> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void setDefaultAddress(final String from, int position) {
        showLoading();
        JsonObject json = new JsonObject();
        json.addProperty("PK_CustID", PreferencesManager.getInstance(context).getUSERID());
        json.addProperty("Ship_AddressID", addressList.get(position).getShipAddressId());
        json.addProperty("AddressMode", addressList.get(position).getAddressMode());
        Call<SetDefaultAddressResponse> chargeCall = apiServices_shoopping.getSetDefaultAddress(json);
        chargeCall.enqueue(new Callback<SetDefaultAddressResponse>() {
            @Override
            public void onResponse(@NonNull Call<SetDefaultAddressResponse> call, @NonNull Response<SetDefaultAddressResponse> response) {
                hideLoading();
                try {
                    LoggerUtil.logItem(response.body());
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                        GetAddressList(from);
                    } else {
                        Toast.makeText(context, "No Records Found.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SetDefaultAddressResponse> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void getState() {
        JsonObject param = new JsonObject();
        param.addProperty("StateName", "");
        LoggerUtil.logItem(param);
        Call<ResponseState> call = apiServices.getstate(param);
        call.enqueue(new Callback<ResponseState>() {
            @Override
            public void onResponse(@NonNull Call<ResponseState> call, @NonNull Response<ResponseState> response) {
                LoggerUtil.logItem(response.body());
                if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                    stateName = new ArrayList<>();
                    for (int i = 0; i < response.body().getStateNamelist().size(); i++) {
                        stateName.add(i, response.body().getStateNamelist().get(i).getStateName());
                    }
                    state_et.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, stateName));
                    state_et.setThreshold(1);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseState> call, @NonNull Throwable t) {
//                hideLoading();
            }
        });
    }

    private void getCity(final String stateName) {
        JsonObject param = new JsonObject();
        param.addProperty("StateName", stateName);
        LoggerUtil.logItem(param);
        Call<ResponseDistrict> call = apiServices.getDistrict(param);
        call.enqueue(new Callback<ResponseDistrict>() {
            @Override
            public void onResponse(@NonNull Call<ResponseDistrict> call, @NonNull Response<ResponseDistrict> response) {
                LoggerUtil.logItem(response.body());
                if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                    cityName = new ArrayList<>();
                    for (int i = 0; i < response.body().getDistrictList().size(); i++) {
                        cityName.add(i, response.body().getDistrictList().get(i).getDistrictName());
                    }
                    city_et.setThreshold(1);
                    city_et.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, cityName));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseDistrict> call, @NonNull Throwable t) {
                LoggerUtil.logItem(t.getMessage());
            }
        });
    }

    public class Addresses_Adapter extends BaseAdapter {
        LayoutInflater mInflater;

        Addresses_Adapter(Context con) {
            mInflater = LayoutInflater.from(con);
        }

        @Override
        public int getCount() {
            return addressList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            try {
                if (convertView == null)
                    convertView = mInflater.inflate(R.layout.address_adapter, null);

                TextView fullName = convertView.findViewById(R.id.fullname_bill);
                TextView address = convertView.findViewById(R.id.address_bill);
                TextView landmark = convertView.findViewById(R.id.landmark_bill);
                TextView pinCode = convertView.findViewById(R.id.pinCode_bill);
                TextView addressType = convertView.findViewById(R.id.addressType_bill);
                Button btn_remove_address = convertView.findViewById(R.id.btn_remove_address);
                Button btn_selected = convertView.findViewById(R.id.btn_selected);
                Button btn_edit_address = convertView.findViewById(R.id.btn_edit_address);

                btn_remove_address.setTag(position);
                btn_selected.setTag(position);
                btn_edit_address.setTag(position);

                fullName.setText(addressList.get(position).getFullName());
                pinCode.setText(String.format("Pin: %s", addressList.get(position).getPincode()));
                landmark.setText(String.format("%s %s,%s", addressList.get(position).getLandmark(),
                        addressList.get(position).getCity(),
                        addressList.get(position).getState()).trim());
                address.setText(addressList.get(position).getAddress());
                addressType.setText(addressList.get(position).getAddressType());

                if (addressList.get(position).getIspermanent().equalsIgnoreCase("1")) {
                    btn_selected.setText("Selected");
                    btn_selected.setTextColor(ContextCompat.getColor(context, R.color.white));
                    btn_selected.setBackgroundResource(R.drawable.rect_btn_bg_darkgreen);
                } else {
                    btn_selected.setText("Select");
                    btn_selected.setTextColor(ContextCompat.getColor(context, R.color.text_color));
                    btn_selected.setBackgroundResource(R.drawable.size_border);
                }

                btn_remove_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag();
                        DeleteAddress(pos);
                    }
                });

                btn_selected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag();
                        setDefaultAddress("deliver", pos);

                    }
                });

                btn_edit_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = (int) view.getTag();
//                        setDefaultAddress("none", pos);
                        updateAddress(addressList.get(position));
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }
}
