package com.digitalcashbag.wallet;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.utilities.recharges.activities.AddMoney;
import com.digitalcashbag.wallet.adapter.WalletAdapter;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.digitalcashbag.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.trasactions.AllTransactionListItem;
import kkm.com.core.model.response.trasactions.ResponseTransactions;
import kkm.com.core.model.response.utility.ResponseRecentRecharges;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllTransaction extends BaseActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_wallet_transaction)
    RecyclerView rvWalletTransaction;
    @BindView(R.id.transProgres)
    ProgressBar pbProgress;

    WalletAdapter walletTransactionadapter;
    List<AllTransactionListItem> list = new ArrayList<>();
    @BindView(R.id.img_open_filters)
    ImageView imgOpenFilters;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_page);
        ButterKnife.bind(this);
        title.setText("Bag Transaction");

        sideMenu.setOnClickListener(v -> finish());

        imgOpenFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomDialog();
            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvWalletTransaction.setLayoutManager(mLayoutManager);
        walletTransactionadapter = new WalletAdapter(context, list, AllTransaction.this);
        rvWalletTransaction.setAdapter(walletTransactionadapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getAllTransactions("", "", "");
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }


    private void getAllTransactions(String from_date, String to_date, String pay_type) {
        if (pbProgress != null)
            pbProgress.setVisibility(View.VISIBLE);
        JsonObject object = new JsonObject();
        object.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
        object.addProperty("FromDate", from_date);
        object.addProperty("ToDate", to_date);
        object.addProperty("ActionType", pay_type);

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(object.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoggerUtil.logItem(object);
        Call<JsonObject> call_all_transaction = apiServices_utilityV2.getAllTrasactions(body);
        call_all_transaction.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                try {
                    if (pbProgress != null)
                        pbProgress.setVisibility(View.GONE);
                    LoggerUtil.logItem(response.body());

                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    ResponseTransactions convertedObject = new Gson().fromJson(paramResponse, ResponseTransactions.class);
                    LoggerUtil.logItem(convertedObject);

                    if (response.body() != null && convertedObject.getResponse().equalsIgnoreCase("Success")) {
                        if (convertedObject.getAllTransactionList().size() > 0) {
                            list = convertedObject.getAllTransactionList();
                            walletTransactionadapter.updateList(list);
                            rvWalletTransaction.setVisibility(View.VISIBLE);
                        } else {
                            rvWalletTransaction.setVisibility(View.GONE);
                            showAlert("No Transaction found.", R.color.red, R.drawable.error);
                        }
                    } else {
                        rvWalletTransaction.setVisibility(View.GONE);
                        showAlert("No Transaction found.", R.color.red, R.drawable.error);
                    }
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                if (pbProgress != null)
                    pbProgress.setVisibility(View.GONE);
            }
        });
    }

    private void openBottomDialog() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.dialog_filter, null);
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("BBPS");
        typeList.add("DataCard");
        typeList.add("DTH");
        typeList.add("GiftCard");
        typeList.add("Shopping");
        typeList.add("Recharge");
        typeList.add("Transfer");
        typeList.add("Add Wallet");
        typeList.add("PrepaidMobile");
        typeList.add("TataSky");
        typeList.add("ThemePark");
        typeList.add("UPICollect");
        typeList.add("UPIRefund");

        TextView txtFromDate = customView.findViewById(R.id.txtFromDate);
        TextView txtToDate = customView.findViewById(R.id.txtToDate);
        TextView txtType = customView.findViewById(R.id.txtType);
        Button btnSearch = customView.findViewById(R.id.btnSearch);
        ListView recyclerView = customView.findViewById(R.id.recyclerView);

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, typeList);
        recyclerView.setAdapter(itemsAdapter);

        BottomDialog dialog = new BottomDialog.Builder(context)
                .setTitle("Search Here")
                .setContent("")
                .setCustomView(customView)
                .build();

        btnSearch.setOnClickListener(v -> {
            dialog.dismiss();
            getAllTransactions(txtFromDate.getText().toString(), txtToDate.getText().toString(), txtType.getText().toString());
        });

        txtFromDate.setOnClickListener(v -> datePicker(txtFromDate));

        txtToDate.setOnClickListener(v -> datePicker(txtToDate));

        recyclerView.setOnItemClickListener((parent, view, position, id) -> txtType.setText(typeList.get(position)));


        dialog.show();

        // You can also show the custom view with some padding in DP (left, top, right, bottom)
        //.setCustomView(customView, 20, 20, 20, 0)

    }

    private void datePicker(final TextView et) {
        Calendar cal = Calendar.getInstance();
        int mYear, mMonth, mDay;
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DatePickerDialogTheme, (view, year, monthOfYear, dayOfMonth) -> {
            et.setText(Utils.changeDateFormat(dayOfMonth, monthOfYear, year));
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }


    @OnClick(R.id.add_to_bag)
    public void onViewClicked() {
        Bundle b = new Bundle();
        b.putString("total", "");
        b.putString("from", "wallet");
        goToActivity(AllTransaction.this, AddMoney.class, b);
    }
}
