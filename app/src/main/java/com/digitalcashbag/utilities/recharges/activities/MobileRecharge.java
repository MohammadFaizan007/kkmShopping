package com.digitalcashbag.utilities.recharges.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.digitalcashbag.common_activities.FullScreenLogin;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.utilities.recharges.adapter.RecentMobileRechargesAdapter;
import com.digitalcashbag.utilities.recharges.browse_plan.BrowsePlanJio;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.AppConfig;
import kkm.com.core.app.CheckErrorCode;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
import kkm.com.core.model.response.jioPrepaid.ResponseJioPrepaidRecharge;
import kkm.com.core.model.response.utility.ResponseDatacardRecharge;
import kkm.com.core.model.response.utility.ResponsePostpaidRecharge;
import kkm.com.core.model.response.utility.ResponsePrepaidRecharge;
import kkm.com.core.model.response.utility.ResponseRecentRecharges;
import kkm.com.core.retrofit.Dialog_dismiss;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class MobileRecharge extends BaseActivity implements Dialog_dismiss, MvpView {

    protected static final int PERMISSION_CONTACTS_REQUEST_CODE = 13;
    private static DecimalFormat format = new DecimalFormat("0.00");
    @BindView(R.id.et_amount)
    public TextInputEditText etAmount;
    public String jio_plan_id = "";
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rg_rechagretype)
    RadioGroup rgRechagretype;
    @BindView(R.id.et_mob_no)
    TextInputEditText etMobNo;
    @BindView(R.id.input_layout_mob_no)
    TextInputLayout inputLayoutMobNo;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.make_payment_btn)
    Button makePaymentBtn;
    @BindView(R.id.rv_recent)
    RecyclerView rv_recent;
    @BindView(R.id.rb_prepaid)
    RadioButton rbPrepaid;
    @BindView(R.id.rb_postpaid)
    RadioButton rbPostpaid;
    @BindView(R.id.cardView2)
    CardView cardView2;
    @BindView(R.id.rb_data_card)
    RadioButton rbDataCard;
    @BindView(R.id.et_operator)
    TextInputEditText etOperator;
    @BindView(R.id.input_layout_operator)
    TextInputLayout inputLayoutOperator;
    @BindView(R.id.input_layout_amount)
    TextInputLayout inputLayoutAmount;
    @BindView(R.id.et_datacard_number)
    TextInputEditText etDatacardNumber;
    @BindView(R.id.input_layout_datacard_number)
    TextInputLayout inputLayoutDatacardNumber;
    Bundle param = new Bundle();
    Bundle sendParam = new Bundle();
    @BindView(R.id.tv_wallet_amount)
    TextView tv_wallet_amount;
    @BindView(R.id.browse_plans_tv)
    TextView browsePlansTv;
    private boolean once = false;
    private String mobileno_st = "";
    private String operator_st = "";
    private String amount_st = "0";
    private String action_type_st;
    private String from_st;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_recharge);
        ButterKnife.bind(this);
        try {
            param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
            title.setText(param.getString("mobile_recharge"));
            etMobNo.setText("+91 ");
            etMobNo.setSelection(4);

            if (param != null && param.getString("mobile_recharge", "").equalsIgnoreCase(getResources().getString(R.string.mobile_postpaid))) {
                rbPostpaid.setChecked(true);
                rbPrepaid.setChecked(false);
                title.setText(getResources().getString(R.string.mobile_postpaid));
                action_type_st = "BBPS";
                from_st = "postpaid";
                getRecentRechargeList("BBPS", "Postpaid");
            } else {
                rbPostpaid.setChecked(false);
                rbPrepaid.setChecked(true);
                title.setText(getResources().getString(R.string.mob_prepaid));
                action_type_st = "PrepaidMobile";
                from_st = "prepaid";
                getRecentRechargeList("PrepaidMobile", "");
            }

            rgRechagretype.setOnCheckedChangeListener((radioGroup, i) -> {
                if (rbPostpaid.isChecked()) {
                    title.setText(getResources().getString(R.string.mobile_postpaid));
                    inputLayoutDatacardNumber.setVisibility(View.GONE);
                    inputLayoutMobNo.setVisibility(View.VISIBLE);
                    etMobNo.setText("+91 ");
                    etMobNo.setSelection(4);
                    etOperator.setText("");
                    etAmount.setText("");
                    getRecentRechargeList("BBPS", "Postpaid");
                    action_type_st = "BBPS";
                    from_st = "postpaid";
                } else if (rbPrepaid.isChecked()) {
                    title.setText(getResources().getString(R.string.mob_prepaid));
                    inputLayoutDatacardNumber.setVisibility(View.GONE);
                    inputLayoutMobNo.setVisibility(View.VISIBLE);
                    etMobNo.setText("+91 ");
                    etMobNo.setSelection(4);
                    etOperator.setText("");
                    etAmount.setText("");
                    getRecentRechargeList("PrepaidMobile", "");
                    action_type_st = "PrepaidMobile";
                    from_st = "prepaid";
                } else if (rbDataCard.isChecked()) {
                    title.setText(getResources().getString(R.string.data_card));
                    inputLayoutDatacardNumber.setVisibility(View.VISIBLE);
                    inputLayoutMobNo.setVisibility(View.GONE);
                    etOperator.setText("");
                    etAmount.setText("");
                    etDatacardNumber.setText("");
                    getRecentRechargeList("DataCard", "");
                    action_type_st = "DataCard";
                    from_st = "datacard";
                }
            });

            etMobNo.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 3) {
                        etMobNo.setText("+91 ");
                        etMobNo.setSelection(4);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });

            etMobNo.setOnTouchListener((v, event) -> {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etMobNo.getRight() - etMobNo.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                                == PackageManager.PERMISSION_GRANTED) {
                            hideKeyboard();
                            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                            startActivityForResult(contactPickerIntent, 1);
                        } else {
                            requestContactPermission();
                        }
                        return true;
                    }
                }
                int inType = etMobNo.getInputType();
                etMobNo.setInputType(InputType.TYPE_NULL);
                etMobNo.onTouchEvent(event);
                etMobNo.setInputType(inType);
                return true;
            });

            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getWalletBalance("pre_login");
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.side_menu, R.id.make_payment_btn, R.id.et_mob_no, R.id.et_operator, R.id.browse_plans_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.make_payment_btn:
                if (Validation()) {
                    once = true;
                    hideKeyboard();
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        if (PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                            FullScreenLogin dialog = new FullScreenLogin();
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            dialog.show(ft, FullScreenLogin.TAG);
                        } else {
                            getWalletBalanceCheckRunTime(etAmount.getText().toString());
                        }
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                }
                break;
            case R.id.et_mob_no:
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(etMobNo.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                etMobNo.requestFocus();
                break;
            case R.id.et_operator:
                hideKeyboard();
                PopupMenu popup_operator = new PopupMenu(this, view);
                if (rbPrepaid.isChecked()) {
                    popup_operator.getMenuInflater().inflate(R.menu.menu_provider_prepaid, popup_operator.getMenu());
                } else if (rbPostpaid.isChecked()) {
                    popup_operator.getMenuInflater().inflate(R.menu.menu_provider_postpaid, popup_operator.getMenu());
                } else {
                    popup_operator.getMenuInflater().inflate(R.menu.menu_provider_datacard, popup_operator.getMenu());
                }
                popup_operator.setOnMenuItemClickListener(item -> {
                    etOperator.setText(item.getTitle());
                    if (item.getTitle().equals("Jio")) {
                        browsePlansTv.setVisibility(View.VISIBLE);
                    } else {
                        browsePlansTv.setVisibility(View.GONE);
                    }
                    return true;
                });
                popup_operator.show();
                break;
            case R.id.browse_plans_tv:
                if (!etMobNo.getText().toString().replace("+91 ", "").equalsIgnoreCase("")) {
                    BrowsePlanJio dialog = new BrowsePlanJio();
                    Bundle b = new Bundle();
                    b.putString("MobNo", etMobNo.getText().toString().replace("+91 ", ""));
                    dialog.setArguments(b);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    dialog.show(ft, BrowsePlanJio.TAG);
                } else {
                    showError(getResources().getString(R.string.valid_mob_no_err), etMobNo);
                }
                break;
        }
    }

    private void getWalletBalanceCheckRunTime(String productAmount) {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("MemberId", PreferencesManager.getInstance(context).getUSERID());
            LoggerUtil.logItem(jsonObject);

            JsonObject body = new JsonObject();
            try {
                body.addProperty("body", Cons.encryptMsg(jsonObject.toString(), easypay_key));
                LoggerUtil.logItem(body);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Call<JsonObject> walletBalanceCall = apiServices_utilityV2.getbalanceAmount(body);
            walletBalanceCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                        ResponseBalanceAmount convertedObject = new Gson().fromJson(paramResponse, ResponseBalanceAmount.class);

                        if (convertedObject.getStatus().equalsIgnoreCase("Success")) {
                            if (Float.parseFloat(String.valueOf(convertedObject.getBalanceAmount())) >= Float.parseFloat(productAmount)) {
                                sendParam.putString("mobile_recharge", param.getString("mobile_recharge"));
                                sendParam.putString("from", param.getString("mobile_recharge"));
                                if (rbPrepaid.isChecked()) {
                                    if (operator_st.equalsIgnoreCase("Jio")) {
                                        if (!jio_plan_id.equalsIgnoreCase("")) {
                                            getJioPrepaidRecharge();
                                        } else {
                                            showAlert("Please select plan from Browse Plan.", R.color.red, R.drawable.error);
                                        }
                                    } else {
                                        getPrepaidRecharge();
                                    }
                                } else if (rbPostpaid.isChecked()) {
                                    getPostpaidRecharge();
                                } else if (rbDataCard.isChecked()) {
                                    getDatacardRecharge();
                                }


                            } else {
                                createAddBalanceDialog(context, "Insufficient bag balance", "You have insufficient balance in your bag, add money before making transactions.");
                            }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//////////////////////////////////////////////////// Wallet Balance ////////////////////////////////////////////////////////////////////

    public void createAddBalanceDialog(Context context, String title, String msg) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setNegativeButton("Add Balance", (dialog, id) -> {
            dialog.dismiss();
            Bundle b = new Bundle();
            b.putString("total", amount_st);
            b.putString("from", "mobileRecharge");
            goToActivity(MobileRecharge.this, AddMoney.class, b);
        });

        android.support.v7.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }

////////////////////////////////////////////////////Recent Prepaid Recharge List////////////////////////////////////////////////////////////////

    private void getWalletBalance(String tagLogin) {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("MemberId", PreferencesManager.getInstance(context).getUSERID());
            LoggerUtil.logItem(jsonObject);

            JsonObject body = new JsonObject();
            try {
                body.addProperty("body", Cons.encryptMsg(jsonObject.toString(), easypay_key));
                LoggerUtil.logItem(body);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Call<JsonObject> walletBalanceCall = apiServices_utilityV2.getbalanceAmount(body);
            walletBalanceCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                        ResponseBalanceAmount convertedObject = new Gson().fromJson(paramResponse, ResponseBalanceAmount.class);
                        if (convertedObject.getStatus().equalsIgnoreCase("Success")) {
                            tv_wallet_amount.setVisibility(View.VISIBLE);
                            tv_wallet_amount.setText(String.format("%s %s", getResources().getString(R.string.wallet_amt), convertedObject.getBalanceAmount()));
                            if (tagLogin.equalsIgnoreCase("post_login")) {
                                hideKeyboard();
                                if (Float.parseFloat(String.valueOf(convertedObject.getBalanceAmount())) >= Float.parseFloat(amount_st)) {
                                    sendParam.putString("mobile_recharge", param.getString("mobile_recharge"));
                                    sendParam.putString("from", param.getString("mobile_recharge"));
                                    if (rbPrepaid.isChecked()) {
                                        if (operator_st.equalsIgnoreCase("Jio")) {
                                            if (!jio_plan_id.equalsIgnoreCase("")) {
                                                getJioPrepaidRecharge();
                                            } else {
                                                showAlert("Please select plan from Browse Plan.", R.color.red, R.drawable.error);
                                            }
                                        } else {
                                            getPrepaidRecharge();
                                        }
                                    } else if (rbPostpaid.isChecked()) {
                                        getPostpaidRecharge();
                                    } else if (rbDataCard.isChecked()) {
                                        getDatacardRecharge();
                                    }
                                } else {
                                    createAddBalanceDialog(context, "Insufficient bag balance", "You have insufficient balance in your bag, add money before making transactions.");
                                }
                            }
                        } else {
                            tv_wallet_amount.setText(String.format("%s 0", getResources().getString(R.string.wallet_amt)));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getRecentRechargeList(String action_type, String type) {
        try {
            if (!PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                showLoading();
                JsonObject mainjson = new JsonObject();
                mainjson.addProperty("Action", action_type);
                mainjson.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
                mainjson.addProperty("Type", type);
                LoggerUtil.logItem(mainjson);

                JsonObject body = new JsonObject();
                body.addProperty("body", Cons.encryptMsg(mainjson.toString(), easypay_key));
                Log.e("REQUEST", body.toString());


                Call<JsonObject> walletBalanceCall = apiServices_utilityV2.getRecentRecharges(body);
                walletBalanceCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        hideLoading();
                        LoggerUtil.logItem(response.body());

                        try {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                            ResponseRecentRecharges convertedObject = new Gson().fromJson(paramResponse, ResponseRecentRecharges.class);
                            LoggerUtil.logItem(convertedObject);


                            if (response.body() != null && convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                LinearLayoutManager manager = new LinearLayoutManager(context);
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                rv_recent.setVisibility(View.VISIBLE);
                                rv_recent.setLayoutManager(manager);
                                RecentMobileRechargesAdapter adapter = new RecentMobileRechargesAdapter(context, convertedObject.getRecentActivity(), MobileRecharge.this);
                                rv_recent.setAdapter(adapter);
                            } else {
//                            showAlert("No records found.", R.color.red, R.drawable.alerter_ic_notifications);
                                rv_recent.setVisibility(View.GONE);
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
            } else {
                hideLoading();
            }
        } catch (Exception e) {
            e.printStackTrace();
            hideLoading();
        }

    }

    @Override
    public void checkAvailability(String mobile_no, String operator, String amt, String id) {
        etMobNo.setText("+91 " + mobile_no);
        etMobNo.setSelection(14);
        etOperator.setText(operator);
        etAmount.setText(amt);
        etAmount.setSelection(etAmount.getText().toString().length());
        etAmount.requestFocus();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        try {
            switch (requestCode) {
                case 1:
                    Uri contactData = data.getData();
                    assert contactData != null;
                    Cursor cur = getContentResolver().query(contactData, null, null, null, null);
                    assert cur != null;
                    if (cur.getCount() > 0) {// thats mean some resutl has been found
                        if (cur.moveToNext()) {
                            String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                            String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            Log.d("Names", name);
                            if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                                assert phones != null;
                                while (phones.moveToNext()) {
                                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                                    String onlyNumber = phoneNumber.replaceAll( "[^0-9]", "" );
                                    Log.d("Number", phoneNumber);
                                    etMobNo.setText("");
                                    etMobNo.setText(String.format("+91 %s", modifyNumber(phoneNumber.replaceAll(" ", ""))));
                                    etMobNo.setSelection(etMobNo.getText().toString().length());
                                }
                                phones.close();
                            }
                        }
                    }
                    cur.close();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            etMobNo.setText("");
            showMessage("Invalid number.");
        }
    }

    private boolean Validation() {
        try {
            mobileno_st = etMobNo.getText().toString().replace("+91 ", "");
            String datacard_st = etDatacardNumber.getText().toString();
            operator_st = etOperator.getText().toString();
            amount_st = etAmount.getText().toString();
            if (rbDataCard.isChecked()) {
                if (datacard_st.length() == 0) {
                    showError(getResources().getString(R.string.datacard_err), etDatacardNumber);
                    return false;
                } else if (operator_st.length() == 0) {
                    showError(getResources().getString(R.string.select_operator_err), etOperator);
                    return false;
                } else if (amount_st.length() == 0) {
                    showError(getResources().getString(R.string.enter_amount), etAmount);
                    return false;
                }
            } else if (rbPostpaid.isChecked() || rbPrepaid.isChecked()) {
                if (mobileno_st.length() == 0 || mobileno_st.length() != 10) {
                    showError(getResources().getString(R.string.valid_mob_no_err), etMobNo);
                    return false;
                } else if (operator_st.length() == 0) {
                    showError(getResources().getString(R.string.select_operator_err), etOperator);
                    return false;
                } else if (amount_st.length() == 0) {
                    showError(getResources().getString(R.string.enter_amount), etAmount);
                    return false;
                }
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

    public void requestContactPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
            Utils.createSimpleDialog1(this, getString(R.string.alert_text), getString(R.string.permission_camera_rationale11), getString(R.string.reqst_permission), () -> ActivityCompat.requestPermissions(MobileRecharge.this, new String[]{
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_CONTACTS},
                    PERMISSION_CONTACTS_REQUEST_CODE));

        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_CONTACTS},
                    PERMISSION_CONTACTS_REQUEST_CODE);
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private String modifyNumber(String num) {
        if (num.startsWith("91")) {
            num = num.replaceFirst("91", "");
        } else if (num.startsWith("+91")) {
            num = num.replaceFirst("\\+(91)", "");
        } else if (num.startsWith("0")) {
            num = num.replaceFirst("0", "");
        }
        return num;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (once) {
            getWalletBalance("pre_login");
            if (from_st.equalsIgnoreCase("postpaid")) {
                getRecentRechargeList("BBPS", "Postpaid");
            } else {
                getRecentRechargeList(action_type_st, "");
            }
        }
    }

    @Override
    public void onDismiss() {
        if (!PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
            getWalletBalance("post_login");
        }
    }

    private void getJioPrepaidRecharge() {
        try {
            showLoading();
//            ApiServices apiServices = ServiceGenerator.createServiceUtility(ApiServices.class);
            JsonObject mainjson = new JsonObject();
            mainjson.addProperty("NUMBER", mobileno_st);
            mainjson.addProperty("PlanOffer", jio_plan_id);
            mainjson.addProperty("AMOUNT", format.format(Double.parseDouble(amount_st)));
            mainjson.addProperty("Provider", "Jio");
            mainjson.addProperty("Type", "Prepaid");
            mainjson.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
            LoggerUtil.logItem(mainjson);

            JsonObject body = new JsonObject();
            try {
                body.addProperty("body", Cons.encryptMsg(mainjson.toString(), easypay_key));
                LoggerUtil.logItem(body);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Call<JsonObject> prepaidCall = apiServices_utilityV2.getJioPrepaidRecharge(body);
            prepaidCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    Log.e("jioResponse>>", response.body().toString());
                    try {
                        if (response.body() != null) {
                            once = true;

                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                            List<ResponseJioPrepaidRecharge> list = Utils.getList(paramResponse, ResponseJioPrepaidRecharge.class);
                            LoggerUtil.logItem(list);
                            if (list.get(0).getError().equalsIgnoreCase("0") && (list.get(0).getResult().equalsIgnoreCase("0"))) {
                                sendParam.putString("TRAK_ID", list.get(0).getTransid());
                                sendParam.putString("TRANS_STATUS", "Recharged Successfully");
                                sendParam.putString("OPERATOR__TYPE", operator_st);
                                sendParam.putString("MOBNO", mobileno_st);
                                sendParam.putString("AMT", format.format(Double.parseDouble(amount_st)));
                                sendParam.putString("Date", list.get(0).getDate());
                                goToActivity(context, RechargeStatus.class, sendParam);
                            } else {
                                CheckErrorCode code = new CheckErrorCode();
                                code.isValidTransaction(context, list.get(0).getError());
                                sendParam.putString("TRAK_ID", list.get(0).getTransid());
                                sendParam.putString("TRANS_STATUS", "Failed");
                                sendParam.putString("OPERATOR__TYPE", operator_st);
                                sendParam.putString("MOBNO", mobileno_st);
                                sendParam.putString("AMT", format.format(Double.parseDouble(amount_st)));
                                sendParam.putString("Date", list.get(0).getDate());
                                goToActivity(context, RechargeStatus.class, sendParam);
                            }
                        } else {
                            Toast.makeText(context, "Something went wrong. \nPlease try after some time.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Something went wrong. \nPlease try after some time.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPrepaidRecharge() {
        try {
            showLoading();
//            ApiServices apiServices = ServiceGenerator.createServiceUtility(ApiServices.class);
            JsonObject mainjson = new JsonObject();
            mainjson.addProperty("NUMBER", mobileno_st);
            mainjson.addProperty("ACCOUNT", AppConfig.PAYLOAD_ACCOUNT_RECHARGE_TWO);
            mainjson.addProperty("AMOUNT", format.format(Double.parseDouble(amount_st)));
            mainjson.addProperty("Provider", operator_st);
            mainjson.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
            LoggerUtil.logItem(mainjson);

            JsonObject body = new JsonObject();
            try {
                body.addProperty("body", Cons.encryptMsg(mainjson.toString(), easypay_key));
                LoggerUtil.logItem(body);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Call<JsonObject> prepaidCall = apiServices_utilityV2.getPrepaidRecharge(body);
            prepaidCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.body() != null) {
                            once = true;
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                            List<ResponsePrepaidRecharge> list = Utils.getList(paramResponse, ResponsePrepaidRecharge.class);
                            LoggerUtil.logItem(list);
                            if (list.get(0).getError().equalsIgnoreCase("0") && (list.get(0).getResult().equalsIgnoreCase("0"))) {
                                sendParam.putString("TRAK_ID", list.get(0).getTransid());
                                sendParam.putString("TRANS_STATUS", "Recharged Successfully");
                                sendParam.putString("OPERATOR__TYPE", operator_st);
                                sendParam.putString("MOBNO", mobileno_st);
                                sendParam.putString("AMT", format.format(Double.parseDouble(amount_st)));
                                sendParam.putString("Date", list.get(0).getDate());
                                goToActivity(context, RechargeStatus.class, sendParam);
                            } else {
                                CheckErrorCode code = new CheckErrorCode();
                                code.isValidTransaction(context, list.get(0).getError());
                                sendParam.putString("TRAK_ID", list.get(0).getTransid());
                                sendParam.putString("TRANS_STATUS", "Failed");
                                sendParam.putString("OPERATOR__TYPE", operator_st);
                                sendParam.putString("MOBNO", mobileno_st);
                                sendParam.putString("AMT", format.format(Double.parseDouble(amount_st)));
                                sendParam.putString("Date", list.get(0).getDate());
                                goToActivity(context, RechargeStatus.class, sendParam);
                            }
                        } else {
                            Toast.makeText(context, "Something went wrong. \nPlease try after some time.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Something went wrong. \nPlease try after some time.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPostpaidRecharge() {
        try {
            showLoading();
//            ApiServices apiServices = ServiceGenerator.createServiceUtility(ApiServices.class);
            JsonObject mainjson = new JsonObject();
            mainjson.addProperty("NUMBER", mobileno_st);
            mainjson.addProperty("AMOUNT_ALL", format.format(Double.parseDouble(amount_st)));
            mainjson.addProperty("AMOUNT", format.format(Double.parseDouble(amount_st)));
            mainjson.addProperty("Provider", operator_st);
            mainjson.addProperty("Type", "Postpaid");
            mainjson.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
            LoggerUtil.logItem(mainjson);

            JsonObject body = new JsonObject();
            try {
                body.addProperty("body", Cons.encryptMsg(mainjson.toString(), easypay_key));
                LoggerUtil.logItem(body);
            } catch (Exception e) {
                e.printStackTrace();
            }


            Call<JsonObject> postpaidCall = apiServices_utilityV2.getPostpaidRecharge(body);
            postpaidCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.body() != null) {
                            once = true;
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                            List<ResponsePostpaidRecharge> list = Utils.getList(paramResponse, ResponsePostpaidRecharge.class);

                            if (list.get(0).getError().equalsIgnoreCase("0") && (list.get(0).getResult().equalsIgnoreCase("0"))) {
                                sendParam.putString("TRAK_ID", list.get(0).getTransid());
                                sendParam.putString("TRANS_STATUS", "Recharged Successfully");
                                sendParam.putString("OPERATOR__TYPE", operator_st);
                                sendParam.putString("MOBNO", mobileno_st);
                                sendParam.putString("AMT", format.format(Double.parseDouble(amount_st)));
                                sendParam.putString("Date", list.get(0).getDate());
                                goToActivity(context, RechargeStatus.class, sendParam);
                            } else {
                                CheckErrorCode code = new CheckErrorCode();
                                code.isValidTransaction(context, list.get(0).getError());
                                sendParam.putString("TRAK_ID", list.get(0).getTransid());
                                sendParam.putString("TRANS_STATUS", "Failed");
                                sendParam.putString("OPERATOR__TYPE", operator_st);
                                sendParam.putString("MOBNO", mobileno_st);
                                sendParam.putString("AMT", format.format(Double.parseDouble(amount_st)));
                                sendParam.putString("Date", list.get(0).getDate());
                                goToActivity(context, RechargeStatus.class, sendParam);
                            }
                        } else {
                            Toast.makeText(context, "Something went wrong. \nPlease try after some time.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Something went wrong. \nPlease try after some time.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDatacardRecharge() {
        try {
            showLoading();
//            ApiServices apiServices = ServiceGenerator.createServiceUtility(ApiServices.class);
            JsonObject mainjson = new JsonObject();
            mainjson.addProperty("NUMBER", mobileno_st);
            mainjson.addProperty("ACCOUNT", format.format(Double.parseDouble(amount_st)));
            mainjson.addProperty("AMOUNT", AppConfig.PAYLOAD_ACCOUNT_RECHARGE_TWO);
            mainjson.addProperty("Provider", operator_st);
            mainjson.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
            LoggerUtil.logItem(mainjson);

            JsonObject body = new JsonObject();
            try {
                body.addProperty("body", Cons.encryptMsg(mainjson.toString(), easypay_key));
                LoggerUtil.logItem(body);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Call<JsonObject> datacardRechargeCall = apiServices_utilityV2.getDatacardRecharge(body);
            datacardRechargeCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        once = true;
                        if (response.body() != null) {

                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                            List<ResponseDatacardRecharge> list = Utils.getList(paramResponse, ResponseDatacardRecharge.class);
                            LoggerUtil.logItem(list);

                            if (list.get(0).getError().equalsIgnoreCase("0") && (list.get(0).getResult().equalsIgnoreCase("0"))) {
                                sendParam.putString("TRAK_ID", list.get(0).getTransid());
                                sendParam.putString("TRANS_STATUS", "Recharged Successfully");
                                sendParam.putString("OPERATOR__TYPE", operator_st);
                                sendParam.putString("MOBNO", mobileno_st);
                                sendParam.putString("AMT", format.format(Double.parseDouble(amount_st)));
                                sendParam.putString("Date", list.get(0).getDate());
                                goToActivity(context, RechargeStatus.class, sendParam);
                            } else {
                                CheckErrorCode code = new CheckErrorCode();
                                code.isValidTransaction(context, list.get(0).getError());
                                sendParam.putString("TRAK_ID", list.get(0).getTransid());
                                sendParam.putString("TRANS_STATUS", "Failed");
                                sendParam.putString("OPERATOR__TYPE", operator_st);
                                sendParam.putString("MOBNO", mobileno_st);
                                sendParam.putString("AMT", format.format(Double.parseDouble(amount_st)));
                                sendParam.putString("Date", list.get(0).getDate());
                                goToActivity(context, RechargeStatus.class, sendParam);
                            }
                        } else {
                            Toast.makeText(context, "Something went wrong. \nPlease try after some time.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Something went wrong. \nPlease try after some time.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
