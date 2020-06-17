package com.digitalcashbag.mlm.fragments.team;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.mlm.adapter.DownLineAdapter;
import com.google.gson.JsonObject;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.response.team.ResponseDownline;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Downline extends BaseFragment {

    @BindView(R.id.txtFromDate)
    TextView txtFromDate;
    @BindView(R.id.txtToDate)
    TextView txtToDate;
    @BindView(R.id.btnSearch)
    ImageView btnSearch;
    @BindView(R.id.downline_recycler)
    RecyclerView downLineRecycler;
    Unbinder unbinder;
    @BindView(R.id.txtNoRecord)
    TextView txtNoRecord;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.downline, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        downLineRecycler.setLayoutManager(manager);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getDownLine();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void getDownLine() {
        JsonObject param = new JsonObject();
        param.addProperty("LoginId", PreferencesManager.getInstance(context).getLoginID());
        param.addProperty("FromDate", txtFromDate.getText().toString());
        param.addProperty("ToDate", txtFromDate.getText().toString());
        showLoading();
        LoggerUtil.logItem(param);
        Call<ResponseDownline> directCall = apiServices.getDownline(param);

        directCall.enqueue(new Callback<ResponseDownline>() {
            @Override
            public void onResponse(@NonNull Call<ResponseDownline> call, @NonNull Response<ResponseDownline> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        DownLineAdapter adapter = new DownLineAdapter(context, response.body().getDownlineList());
                        downLineRecycler.setAdapter(adapter);
                        downLineRecycler.setVisibility(View.VISIBLE);
                        txtNoRecord.setVisibility(View.GONE);
                    } else {
                        if (downLineRecycler != null) {
                            downLineRecycler.setVisibility(View.GONE);
                            txtNoRecord.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Error | Exception e) {
                    hideLoading();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseDownline> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void datePicker(final TextView et) {
        Calendar cal = Calendar.getInstance();
        int mYear, mMonth, mDay;
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerDialogTheme, (view, year, monthOfYear, dayOfMonth) -> et.setText(Utils.changeDateFormatYYYY_MM_DD(dayOfMonth, monthOfYear, year)), mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick({R.id.txtFromDate, R.id.txtToDate, R.id.btnSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtFromDate:
                datePicker(txtFromDate);
                break;
            case R.id.txtToDate:
                datePicker(txtToDate);
                break;
            case R.id.btnSearch:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    getDownLine();
                } else {
                    showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                }
                break;
        }
    }

}
