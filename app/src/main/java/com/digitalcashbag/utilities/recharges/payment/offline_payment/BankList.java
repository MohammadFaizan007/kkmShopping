package com.digitalcashbag.utilities.recharges.payment.offline_payment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.wallet.CompanyBankMasterSelectlistItem;
import kkm.com.core.model.response.wallet.ResponseCompanyName;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class BankList extends BaseActivity implements MvpView {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bell_icon)
    ImageView bellIcon;
    @BindView(R.id.bankList_recycler)
    RecyclerView bankList_recycler;
    List<CompanyBankMasterSelectlistItem> companyBankMasterSelectlist;
    @BindView(R.id.proceed_btn)
    Button proceedBtn;
    Bundle b;
    String amount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_list);
        ButterKnife.bind(this);

        title.setText("Select Bank");
        bellIcon.setVisibility(View.GONE);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        bankList_recycler.setLayoutManager(manager);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getCompanyName();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
        if (getIntent().getBundleExtra(PAYLOAD_BUNDLE) != null) {
            amount = getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("amount");
        }

    }

    private void getCompanyName() {

        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Pk_BankId", "");

        Call<ResponseCompanyName> call = apiServices.getCompanyList(object);
        call.enqueue(new Callback<ResponseCompanyName>() {
            @Override
            public void onResponse(@NotNull Call<ResponseCompanyName> call, @NotNull Response<ResponseCompanyName> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        companyBankMasterSelectlist = response.body().getCompanyBankMasterSelectlist();

                        BankListAdapter adapter = new BankListAdapter(context, companyBankMasterSelectlist, BankList.this);
                        bankList_recycler.setAdapter(adapter);

                    } else {
                        Toast.makeText(BankList.this, "No Record Found", Toast.LENGTH_SHORT).show();
                        proceedBtn.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseCompanyName> call, Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void getClickPositionDirectMember(int position, String tag, String memberId) {
        super.getClickPosition(position, tag);
        b = new Bundle();
        b.putString("CompanyName", companyBankMasterSelectlist.get(position).getCompanyName());
        b.putString("BankName", companyBankMasterSelectlist.get(position).getBankName());
        b.putString("BranchName", companyBankMasterSelectlist.get(position).getBranchName());
        b.putString("AccountNo", companyBankMasterSelectlist.get(position).getAccountNo());
        b.putString("IfscCode", companyBankMasterSelectlist.get(position).getIfscCode());
        b.putString("BankId", companyBankMasterSelectlist.get(position).getPKBankId());
        b.putString("amount", amount);

    }

    @OnClick({R.id.side_menu, R.id.proceed_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                finish();
                break;
            case R.id.proceed_btn:
                if (b != null) {
                    goToActivity(context, OfflineBagRequest.class, b);
                } else {
                    Toast.makeText(BankList.this, "Please select Bank", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
