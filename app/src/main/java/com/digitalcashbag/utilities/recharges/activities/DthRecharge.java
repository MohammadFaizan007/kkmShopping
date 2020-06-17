package com.digitalcashbag.utilities.recharges.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.digitalcashbag.common_activities.FullScreenLogin;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.utilities.recharges.adapter.RecentMobileRechargesAdapter;
import com.digitalcashbag.utilities.tatasky.RegionList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.AppConfig;
import kkm.com.core.app.CheckErrorCode;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
import kkm.com.core.model.response.utility.ResponseDthRecharge;
import kkm.com.core.model.response.utility.ResponseRecentRecharges;
import kkm.com.core.retrofit.Dialog_dismiss;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class DthRecharge extends BaseActivity implements Dialog_dismiss {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_operator)
    TextInputEditText etOperator;
    @BindView(R.id.input_layout_operator)
    TextInputLayout inputLayoutOperator;
    @BindView(R.id.et_customerid)
    TextInputEditText etCustomerid;
    @BindView(R.id.input_layout_customer_id)
    TextInputLayout inputLayoutCustomerId;
    @BindView(R.id.et_amount)
    TextInputEditText etAmount;
    @BindView(R.id.input_layout_amount)
    TextInputLayout inputLayoutAmount;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.make_payment_btn)
    Button makePaymentBtn;
    @BindView(R.id.rv_recent)
    RecyclerView rv_recent;
    @BindView(R.id.et_plans_dthtype)
    TextView etPlansDthtype;
    @BindView(R.id.browse_plans_tv)
    TextView browsePlansTv;
    Bundle param;
    @BindView(R.id.tv_wallet_amount)
    TextView tv_wallet_amount;
    @BindView(R.id.tv_book_dth)
    TextView tvBookDth;
    private String operator_st = "", cust_id_st = "", amount_st = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dth_recharge);
        ButterKnife.bind(this);
        try {
            param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
            title.setText(param.getString("dth_rech"));

            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getWalletBalance();
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.side_menu, R.id.make_payment_btn, R.id.et_operator, R.id.tv_book_dth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.make_payment_btn:
                if (Validation()) {
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
//            case R.id.browse_plans_tv:
//                if (!etOperator.getText().toString().equalsIgnoreCase( "" )) {
//                    Toast.makeText( this, "Browse Plan", Toast.LENGTH_SHORT ).show();
//                } else {
//                    showError( getResources().getString( R.string.select_operator_err ), etOperator );
//                }
//                break;
            case R.id.et_operator:
                hideKeyboard();
                PopupMenu popup_operator = new PopupMenu(context, view);
                popup_operator.getMenuInflater().inflate(R.menu.menu_provider_dthrecharge, popup_operator.getMenu());
                popup_operator.setOnMenuItemClickListener(item -> {
                    etOperator.setText(item.getTitle());
                    return true;
                });
                popup_operator.show();
                break;
            case R.id.tv_book_dth:
                hideKeyboard();
                goToActivity(DthRecharge.this, RegionList.class, param);
                break;
        }
    }

    public void createAddBalanceDialog(Context context, String title, String msg) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setNegativeButton("Add Balance", (dialog, id) -> {
            dialog.dismiss();
            Bundle b = new Bundle();
            b.putString("total", amount_st);
            b.putString("from", "dth");

            goToActivity(DthRecharge.this, AddMoney.class, b);
        });

        android.support.v7.app.AlertDialog alert11 = builder1.create();
        alert11.show();
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
                                getDthRecharge();
                            } else
                                createAddBalanceDialog(context, "Insufficient bag balance", "You have insufficient balance in your bag, add money before making transactions.");
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


//////////////////////////////////////////////////// Wallet Balance & Debit Wallet ////////////////////////////////////////////////////////////////////

    private void getWalletBalance() {
        try {
            showLoading();
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
                        LoggerUtil.logItem(response.body());
                        if (convertedObject.getStatus().equalsIgnoreCase("Success")) {
                            tv_wallet_amount.setVisibility(View.VISIBLE);
                            tv_wallet_amount.setText(String.format("%s %s", getResources().getString(R.string.wallet_amt), convertedObject.getBalanceAmount()));
                        } else {
                            tv_wallet_amount.setText(String.format("%s %s", getResources().getString(R.string.wallet_amt), "0"));
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
            hideLoading();
        }
    }

////////////////////////////////////////////////////Recent Dth Recharge List////////////////////////////////////////////////////////////////

    private void getDthRecentList(String action_type) {
        try {
            if (!PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                JsonObject mainjson = new JsonObject();
                mainjson.addProperty("Action", action_type);
                mainjson.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
                mainjson.addProperty("Type", "");
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

                            if (convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                LinearLayoutManager manager = new LinearLayoutManager(context);
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                rv_recent.setLayoutManager(manager);
                                RecentMobileRechargesAdapter adapter = new RecentMobileRechargesAdapter(context, convertedObject.getRecentActivity(), DthRecharge.this);
                                rv_recent.setAdapter(adapter);
                            } else {
//                            showAlert("No records found.", R.color.red, R.drawable.alerter_ic_notifications);
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
    public void checkAvailability(String mobile_no, String operator, String amt, String others) {
        etCustomerid.setText(mobile_no);
        etOperator.setText(operator);
        etAmount.setText(amt);
    }


    private boolean Validation() {
        try {
            operator_st = etOperator.getText().toString();
            cust_id_st = etCustomerid.getText().toString();
            amount_st = etAmount.getText().toString();

            if (operator_st.length() == 0) {
                showError(getResources().getString(R.string.select_operator_err), etOperator);
                return false;
            } else if (cust_id_st.length() == 0) {
                showError(getResources().getString(R.string.customer_id_err), etCustomerid);
                return false;
            } else if (amount_st.length() == 0) {
                showError(getResources().getString(R.string.enter_amt_err), etAmount);
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
    protected void onResume() {
        super.onResume();
        etAmount.setText("");
        etCustomerid.setText("");
        etOperator.setText("");
        getDthRecentList("DTH");
    }

    @Override
    public void onDismiss() {
        if (!PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
            getWalletBalanceCheckRunTime(etAmount.getText().toString());
        }
    }

    private void getDthRecharge() {
        try {
            showLoading();
//            ApiServices apiServices = ServiceGenerator.createServiceUtility(ApiServices.class);
            JsonObject mainjson = new JsonObject();
            mainjson.addProperty("NUMBER", cust_id_st);
            mainjson.addProperty("ACCOUNT", AppConfig.PAYLOAD_ACCOUNT_RECHARGE_TWO);
            mainjson.addProperty("AMOUNT", amount_st);
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

            Call<JsonObject> call = apiServices_utilityV2.getDthRecharge(body);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(response.body());
                        if (response.body() != null) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                            List<ResponseDthRecharge> list = Utils.getList(paramResponse, ResponseDthRecharge.class);
                            LoggerUtil.logItem(list);
                            if (list.get(0).getError().equalsIgnoreCase("0")
                                    && (list.get(0).getResult().equalsIgnoreCase("0"))) {
                                param.putString("TRAK_ID", list.get(0).getTransid());
                                param.putString("TRANS_STATUS", "Recharged Successfully.");
                                param.putString("OPERATOR__TYPE", operator_st);
                                param.putString("MOBNO", cust_id_st);
                                param.putString("AMT", amount_st);
                                param.putString("Date", list.get(0).getDate());
                                goToActivity(context, RechargeStatus.class, param);
                            } else {
                                CheckErrorCode code = new CheckErrorCode();
                                code.isValidTransaction(context, list.get(0).getError());
                                param.putString("TRAK_ID", list.get(0).getTransid());
                                param.putString("TRANS_STATUS", "Failed");
                                param.putString("OPERATOR__TYPE", operator_st);
                                param.putString("MOBNO", cust_id_st);
                                param.putString("AMT", amount_st);
                                param.putString("Date", list.get(0).getDate());
                                goToActivity(context, RechargeStatus.class, param);
                            }
                        } else {
                            Toast.makeText(context, "Something went wrong. \nPlease try after some time.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Something went wrong. \nPlease try after some time.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
