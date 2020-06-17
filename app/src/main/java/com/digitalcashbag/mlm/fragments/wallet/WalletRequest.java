package com.digitalcashbag.mlm.fragments.wallet;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.mlm.activities.MainContainerMLM;
import com.digitalcashbag.mlm.adapter.WalletRequestAdapter;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.response.wallet.ResponseWalletRequest;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletRequest extends BaseFragment {

    @BindView(R.id.btn_newRequest)
    Button btnNewRequest;

    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.walletRequestRecycler)
    RecyclerView walletRequestRecycler;
    Unbinder unbinder;
    WalletRequestAdapter adapter;
    @BindView(R.id.txtNoRequest)
    TextView txtNoRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallet_request, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        walletRequestRecycler.setLayoutManager(manager);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getWalletRequest();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (adapter != null)
                    adapter.getFilter().filter(s);
            }
        });
    }

    private void getWalletRequest() {
        JsonObject param = new JsonObject();
        param.addProperty("Fk_MemId", PreferencesManager.getInstance(context).getUSERID());
        showLoading();
        LoggerUtil.logItem(param);
        Call<ResponseWalletRequest> directCall = apiServices.getEWalletRequest(param);

        directCall.enqueue(new Callback<ResponseWalletRequest>() {
            @Override
            public void onResponse(@NonNull Call<ResponseWalletRequest> call, @NonNull Response<ResponseWalletRequest> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        adapter = new WalletRequestAdapter(context, response.body().getEWalletRequestlist());
                        walletRequestRecycler.setAdapter(adapter);
                        walletRequestRecycler.setVisibility(View.VISIBLE);
                        txtNoRequest.setVisibility(View.GONE);
                    } else {
                        walletRequestRecycler.setVisibility(View.GONE);
                        txtNoRequest.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    txtNoRequest.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseWalletRequest> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_newRequest)
    public void onViewClicked() {
        confirmTransactionPswdDialog();
    }

    private void confirmTransactionPswdDialog() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(context);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_transaction_pswd, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        AppCompatButton buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
        EditText et_trans_pswd = confirmDialog.findViewById(R.id.et_trans_pswd);

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(true);

        //Displaying the alert dialog
        alertDialog.show();
        buttonConfirm.setOnClickListener(view -> {
            if (et_trans_pswd.getText().toString().trim().length() != 0) {
                if (PreferencesManager.getInstance(context).getTransactionPass().equals(et_trans_pswd.getText().toString().trim())) {
                    alertDialog.dismiss();
                    ((MainContainerMLM) getActivity()).addFragment(new E_WalletRequest(), "Bag Request");
                } else {
                    showMessage("Invalid Password");
                }
            } else {
                et_trans_pswd.setError("Enter Password");
            }
        });
    }

}
