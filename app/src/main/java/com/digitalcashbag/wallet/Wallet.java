
//package com.digitalcashbag.wallet;
//
//import android.app.DatePickerDialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.digitalcashbag.m2p.M2PKYCRegistration;
//import com.digitalcashbag.m2p.M2PKitRequest;
//import com.digitalcashbag.utilities.recharges.activities.AddMoney;
//import com.digitalcashbag.m2p.adapter.M2PCardAdapter;
//import com.digitalcashbag.wallet.adapter.WalletAdapter;
//import com.github.clans.fab.FloatingActionButton;
//import com.github.clans.fab.FloatingActionMenu;
//import com.github.javiersantos.bottomdialogs.BottomDialog;
//import com.google.gson.JsonObject;
//import com.digitalcashbag.R;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import butterknife.Unbinder;
//import kkm.com.core.app.PreferencesManager;
//import kkm.com.core.constants.BaseFragment;
//import kkm.com.core.model.request.RequestUnclearedBalance;
//import kkm.com.core.model.request.utility.RequestBalanceAmount;
//import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
//import kkm.com.core.model.response.ResponseUnclearedBalance;
//import kkm.com.core.model.response.m2p.transaction.ResponseAllTransaction;
//import kkm.com.core.model.response.m2p.transaction.ResultItem;
//import kkm.com.core.model.response.trasactions.AllTransactionListItem;
//import kkm.com.core.model.response.trasactions.ResponseTransactions;
//import kkm.com.core.retrofit.ApiServices;
//import kkm.com.core.retrofit.MvpView;
//import kkm.com.core.retrofit.ServiceGenerator;
//import kkm.com.core.utils.LoggerUtil;
//import kkm.com.core.utils.NetworkUtils;
//import kkm.com.core.utils.Utils;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
//public class Wallet extends BaseFragment implements MvpView {
//
//    private static DecimalFormat formatWallet = new DecimalFormat("0.00");
//    @BindView(R.id.available_balance)
//    TextView availableBalance;
//    @BindView(R.id.rv_wallet_transaction)
//    RecyclerView rvWalletTransaction;
//    Unbinder unbinder;
//    @BindView(R.id.pbProgress)
//    ProgressBar pbProgress;
//    M2PCardAdapter m2PCardAdapter;
//    WalletAdapter walletTransactionadapter;
//    @BindView(R.id.txtUncleared)
//    TextView txtUncleared;
//    @BindView(R.id.requestBankKit)
//    TextView requestBankKit;
//    @BindView(R.id.fab_bag)
//    FloatingActionButton fabBag;
//    @BindView(R.id.fab_card)
//    FloatingActionButton fabCard;
//    @BindView(R.id.material_design_android_floating_action_menu)
//    FloatingActionMenu materialDesignFAM;
//    private String kitNo, m2pStatus;
//
//    List<AllTransactionListItem> list = new ArrayList<>();
//    List<ResultItem> list_card = new ArrayList<>();
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.wallet_activity, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        return view;
//    }
//
//    @Override
//    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
//        try {
//            hideKeyboard();
//            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
//            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//            mLayoutManager.setAutoMeasureEnabled(false);
//            rvWalletTransaction.setLayoutManager(mLayoutManager);
//            rvWalletTransaction.setHasFixedSize(true);
//            rvWalletTransaction.setNestedScrollingEnabled(false);
//            walletTransactionadapter = new WalletAdapter(context, list, Wallet.this);
//            rvWalletTransaction.setAdapter(walletTransactionadapter);
//            rvWalletTransaction.setItemViewCacheSize(10);
//        } catch (Error | Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @OnClick(R.id.fab_bag)
//    public void fab_bag_click() {
//        materialDesignFAM.close(true);
//        if (NetworkUtils.getConnectivityStatus(context) != 0) {
//            getAllTransactions("", "", "");
//        } else {
//            createInfoDialog(context, "Alert", getString(R.string.alert_internet));
//        }
//    }
//
//    @OnClick(R.id.fab_card)
//    public void fab_card_click() {
//        materialDesignFAM.close(true);
//        if (NetworkUtils.getConnectivityStatus(context) != 0) {
//            getM2pCard_allTransaction();
//        } else {
//            createInfoDialog(context, "Alert", getString(R.string.alert_internet));
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (NetworkUtils.getConnectivityStatus(context) != 0) {
//            getUnclearedBalanceAmount();
//            getBalanceAmount();
//        } else {
//            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
//        }
//    }
//
//    private void getUnclearedBalanceAmount() {
//        RequestUnclearedBalance requestUnclearedBalance = new RequestUnclearedBalance();
//        requestUnclearedBalance.setFKToId(PreferencesManager.getInstance(context).getUSERID());
//        LoggerUtil.logItem(requestUnclearedBalance);
//        Call<ResponseUnclearedBalance> responseUnclearedBalanceCall = apiServices.responseUnclearedBalanceCall(requestUnclearedBalance);
//        responseUnclearedBalanceCall.enqueue(new Callback<ResponseUnclearedBalance>() {
//            @Override
//            public void onResponse(@NotNull Call<ResponseUnclearedBalance> call, @NotNull Response<ResponseUnclearedBalance> response) {
//                Log.e("==========", "getUnclearedBalanceAmount");
//                LoggerUtil.logItem(response.body());
//                try {
//                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
//                        PreferencesManager.getInstance(context).setUNCLEARED_BALANCE(response.body().getUnclearedamount());
//                        PreferencesManager.getInstance(context).setKIT_NO(response.body().getKitNo());
//                        txtUncleared.setText(response.body().getUnclearedamount());
//                        kitNo = response.body().getKitNo();
//                        m2pStatus = response.body().getM2pStatus();
//
////                        if (kitNo.equalsIgnoreCase("") && m2pStatus.equalsIgnoreCase("0")) {
////                            initializeView("Request For Bank Kit", R.drawable.ic_m2p_rrequest, R.drawable.blue_box, R.color.blue_text_dark);
////                        } else if (kitNo.equalsIgnoreCase("") && m2pStatus.equalsIgnoreCase("1")) {
////                            initializeView("Pending approval from admin", R.drawable.pending, R.drawable.yellow_box, R.color.red);
////                        } else if ((!kitNo.equalsIgnoreCase("")) && m2pStatus.equalsIgnoreCase("2")) {
////                            initializeView("Update your KYC to link your wallet to Bank Wallet", R.drawable.warning_red, R.drawable.yellow_box, R.color.red);
////                        } else if ((!kitNo.equalsIgnoreCase("")) && m2pStatus.equalsIgnoreCase("3")) {
////                            requestBankKit.setVisibility(View.GONE);
//////                            initializeView("Link your Cashbag card with your account.", R.drawable.link_connect, R.drawable.rect_btn_bg_darkgreen, R.color.white);
////                        }
//
//                    } else {
//                        PreferencesManager.getInstance(context).setUNCLEARED_BALANCE("00.0000");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<ResponseUnclearedBalance> call, @NotNull Throwable t) {
//
//            }
//        });
//    }
//
//    private void initializeView(String msg, int left_icon, int bg_drawable, int text_color) {
//        requestBankKit.setVisibility(View.VISIBLE);
//        requestBankKit.setText(msg);
//        requestBankKit.setCompoundDrawablesWithIntrinsicBounds(left_icon, 0, 0, 0);
//        requestBankKit.setBackgroundResource(bg_drawable);
//        requestBankKit.setTextColor(getResources().getColor(text_color));
//    }
//
//    @Override
//    public void onPause() {
//        if (call_all_transaction != null) {
//            call_all_transaction.cancel();
//            call_all_transaction = null;
//        }
//        super.onPause();
//    }
//
//    @OnClick({R.id.add_balance_button, R.id.withdraw_money_bank, R.id.tv_view_ledger, R.id.img_open_filters, R.id.requestBankKit})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.add_balance_button:
//                Bundle b = new Bundle();
//                b.putString("total", "");
//                b.putString("from", "wallet");
//                goToActivity(AddMoney.class, b);
//                break;
//            case R.id.withdraw_money_bank:
//                showAlert("Under Progress", R.color.red, R.drawable.error);
////                ((MainContainer) context).goToActivity(context, IncentiveLedger.class, null);
//                break;
//            case R.id.tv_view_ledger:
//                goToActivity(LevelIncome.class, null);
//                break;
//            case R.id.img_open_filters:
//                openBottomDialog();
//                break;
//            case R.id.requestBankKit:
//                //blank ; 0 = not requested yet
//                //blank ; 1 = requested, pending for approval from admin
//                //!blank ; 2 = Kit Number assigned, approved by admin, waiting for KYC
//                //!blank ; 3 = KYC done, waiting for linking card
//                if (kitNo.equalsIgnoreCase("") && m2pStatus.equalsIgnoreCase("0")) {
//                    goToActivity(M2PKitRequest.class, null);
//                } else if (kitNo.equalsIgnoreCase("") && m2pStatus.equalsIgnoreCase("1")) {
////                    goToActivity(RequestBankKit.class, null);
//                } else if ((!kitNo.equalsIgnoreCase("")) && m2pStatus.equalsIgnoreCase("2")) {
//                    goToActivity(M2PKYCRegistration.class, null);
//                } else if ((!kitNo.equalsIgnoreCase("")) && m2pStatus.equalsIgnoreCase("3")) {
//
//                }
//                break;
//        }
//    }
//
//    private void getBalanceAmount() {
//        showLoading();
//        ApiServices services = ServiceGenerator.createServiceUtility(ApiServices.class);
//        RequestBalanceAmount amount = new RequestBalanceAmount();
//        amount.setMemberId(PreferencesManager.getInstance(context).getUSERID());
//        LoggerUtil.logItem(amount);
//        Call<ResponseBalanceAmount> call = services.getbalanceAmount(amount);
//        call.enqueue(new Callback<ResponseBalanceAmount>() {
//            @Override
//            public void onResponse(@NonNull Call<ResponseBalanceAmount> call, @NonNull Response<ResponseBalanceAmount> response) {
//
//                try {
//                    hideLoading();
//                    getAllTransactions("", "", "");
//                    LoggerUtil.logItem(response.body());
//                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase("Success")) {
////                        PreferencesManager.getInstance(context).setWALLET_BALANCE(Float.parseFloat(String.valueOf(response.body().getBalanceAmount())));
//                        String amount = formatWallet.format(Double.parseDouble(String.valueOf(response.body().getBalanceAmount())));
//                        availableBalance.setText(String.format("%s  ", amount));
//                    }
//                } catch (Error | Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//            @Override
//            public void onFailure(@NonNull Call<ResponseBalanceAmount> call, @NonNull Throwable t) {
//                LoggerUtil.logItem(t.getMessage());
//                hideLoading();
//            }
//        });
//    }
//
//    Call<ResponseTransactions> call_all_transaction;
//
//    private void datePicker(final TextView et) {
//        Calendar cal = Calendar.getInstance();
//        int mYear, mMonth, mDay;
//        mYear = cal.get(Calendar.YEAR);
//        mMonth = cal.get(Calendar.MONTH);
//        mDay = cal.get(Calendar.DAY_OF_MONTH);
//        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DatePickerDialogTheme, (view, year, monthOfYear, dayOfMonth) -> {
//            et.setText(Utils.changeDateFormat(dayOfMonth, monthOfYear, year));
//        }, mYear, mMonth, mDay);
//        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
//        datePickerDialog.show();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//    private void openBottomDialog() {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View customView = inflater.inflate(R.layout.dialog_filter, null);
//        ArrayList<String> typeList = new ArrayList<>();
//        typeList.add("BBPS");
//        typeList.add("DataCard");
//        typeList.add("DTH");
//        typeList.add("GiftCard");
//        typeList.add("Shopping");
//        typeList.add("Recharge");
//        typeList.add("Transfer");
//        typeList.add("Add Wallet");
//        typeList.add("PrepaidMobile");
//        typeList.add("TataSky");
//        typeList.add("ThemePark");
//        typeList.add("UPICollect");
//        typeList.add("UPIRefund");
//
//        TextView txtFromDate = customView.findViewById(R.id.txtFromDate);
//        TextView txtToDate = customView.findViewById(R.id.txtToDate);
//        TextView txtType = customView.findViewById(R.id.txtType);
//        Button btnSearch = customView.findViewById(R.id.btnSearch);
//        ListView recyclerView = customView.findViewById(R.id.recyclerView);
//
//        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, typeList);
//        recyclerView.setAdapter(itemsAdapter);
//
//        BottomDialog dialog = new BottomDialog.Builder(context)
//                .setTitle("Search Here")
//                .setContent("")
//                .setCustomView(customView)
//                .build();
//
//        btnSearch.setOnClickListener(v -> {
//            dialog.dismiss();
//            getAllTransactions(txtFromDate.getText().toString(), txtToDate.getText().toString(), txtType.getText().toString());
//        });
//
//        txtFromDate.setOnClickListener(v -> datePicker(txtFromDate));
//
//        txtToDate.setOnClickListener(v -> datePicker(txtToDate));
//
//        recyclerView.setOnItemClickListener((parent, view, position, id) -> txtType.setText(typeList.get(position)));
//
////        txtType.setOnClickListener(v -> {
////            PopupMenu popup_paymentType = new PopupMenu(context, v);
////            popup_paymentType.getMenuInflater().inflate(R.menu.payment_type_menu, popup_paymentType.getMenu());
////            popup_paymentType.setOnMenuItemClickListener(item -> {
////                txtType.setText(item.getTitle());
////                return true;
////            });
////            popup_paymentType.show();
////        });
//
//        dialog.show();
//
//        // You can also show the custom view with some padding in DP (left, top, right, bottom)
//        //.setCustomView(customView, 20, 20, 20, 0)
//
//    }
//
//
//    @Override
//    public void getClickPosition(int position, String tag) {
//        super.getClickPosition(position, tag);
//        cancelOfflineRequest(tag);
//    }
//
//    private void cancelOfflineRequest(String transNumber) {
//        showLoading();
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("TransactionNo", transNumber);
//        LoggerUtil.logItem(jsonObject);
//        Call<JsonObject> call = apiServices.getCancelWalletRequest(jsonObject);
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
//                hideLoading();
//                LoggerUtil.logItem(response.body());
//                getAllTransactions("", "", "");
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
//                hideLoading();
//            }
//        });
//    }
//
//    private void getAllTransactions(String from_date, String to_date, String pay_type) {
//        if (pbProgress != null)
//            pbProgress.setVisibility(View.VISIBLE);
//        JsonObject object = new JsonObject();
//        object.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
//        object.addProperty("FromDate", from_date);
//        object.addProperty("ToDate", to_date);
//        object.addProperty("ActionType", pay_type);
//        LoggerUtil.logItem(object);
//        call_all_transaction = apiServices_utility.getAllTrasactions(object);
//        call_all_transaction.enqueue(new Callback<ResponseTransactions>() {
//            @Override
//            public void onResponse(@NotNull Call<ResponseTransactions> call, @NotNull Response<ResponseTransactions> response) {
//                try {
//                    if (pbProgress != null)
//                        pbProgress.setVisibility(View.GONE);
//                    LoggerUtil.logItem(response.body());
//                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
//                        if (response.body().getAllTransactionList().size() > 0) {
//                            list = response.body().getAllTransactionList();
//                            walletTransactionadapter.updateList(list);
//                            rvWalletTransaction.setVisibility(View.VISIBLE);
//                        } else {
//                            rvWalletTransaction.setVisibility(View.GONE);
//                            showAlert("No Transaction found.", R.color.red, R.drawable.error);
//                        }
//                    } else {
//                        rvWalletTransaction.setVisibility(View.GONE);
//                        showAlert("No Transaction found.", R.color.red, R.drawable.error);
//                    }
//                } catch (Error | Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<ResponseTransactions> call, @NotNull Throwable t) {
//                if (pbProgress != null)
//                    pbProgress.setVisibility(View.GONE);
//            }
//        });
//    }
//
//    private void getM2pCard_allTransaction() {
//        Call<ResponseAllTransaction> allTransactionCall = apiServicesM2P.gettransactions(PreferencesManager.getInstance(context).getLoginID());
//        showLoading();
//        allTransactionCall.enqueue(new Callback<ResponseAllTransaction>() {
//            @Override
//            public void onResponse(@NotNull Call<ResponseAllTransaction> call, @NotNull Response<ResponseAllTransaction> response) {
//                hideLoading();
//                LoggerUtil.logItem(response.body());
//                if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
//                    if (response.body().getResult().getResult().size() > 0) {
//                        list_card = response.body().getResult().getResult();
//                        m2PCardAdapter.updateList(list_card);
//                        rvWalletTransaction.setVisibility(View.VISIBLE);
//                    } else {
//                        rvWalletTransaction.setVisibility(View.GONE);
//                        showAlert("No Transaction found.", R.color.red, R.drawable.error);
//                    }
//                } else {
//                    rvWalletTransaction.setVisibility(View.GONE);
//                    showAlert("No Transaction found.", R.color.red, R.drawable.error);
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<ResponseAllTransaction> call, @NotNull Throwable t) {
//                hideLoading();
//            }
//        });
//    }
//}
