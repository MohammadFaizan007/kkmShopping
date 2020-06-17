package com.digitalcashbag.mlm.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.digitalcashbag.mlm.adapter.DashBoardNewsInfoAdapter;
import com.digitalcashbag.mlm.adapter.DashboardMLMBannerAdapter;
import com.example.library.banner.BannerLayout;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.response.mlmDashboardNew.ResponseNewMLMDashboard;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardMLM extends BaseFragment implements MvpView {
    Unbinder unbinder;
    @BindView(R.id.txtPan)
    TextView txtPan;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.txtBank)
    TextView txtBank;
    @BindView(R.id.txtLink)
    TextView txtLink;
    //    New Ids
    @BindView(R.id.recycler)
    BannerLayout recyclerBanner;
    @BindView(R.id.img_user)
    ImageView imgUser;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.last_login_date)
    TextView lastLoginDate;
    @BindView(R.id.tv_userid)
    TextView tvUserid;
    @BindView(R.id.tv_doj)
    TextView tvDoj;
    @BindView(R.id.mySwitch)
    Switch mySwitch;
    @BindView(R.id.unclear_bal)
    TextView unclearBal;
    @BindView(R.id.self_income)
    TextView selfIncome;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.tv_teamsize)
    TextView tvTeamsize;
    @BindView(R.id.ad_recycler)
    BannerLayout adRecycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_mlm, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtLink.setOnClickListener(v -> showMessage("Coming Soon"));

        tvName.setText(String.format("%s %s", PreferencesManager.getInstance(context).getNAME(), PreferencesManager.getInstance(context).getLNAME()));
        tvUserid.setText(String.format(": %s", PreferencesManager.getInstance(context).getLoginID()));
        Glide.with(context).load(PreferencesManager.getInstance(context).getPROFILEPIC()).
                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                        .apply(RequestOptions.circleCropTransform())
                        .placeholder(R.drawable.bag_circle)
                        .error(R.drawable.bag_circle))
                .into(imgUser);


        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getDashboardDataNew();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
    }


    private void getDashboardDataNew() {
        String url = BuildConfig.BASE_URL_MLM + "DashboardDetail?FK_memId=" + PreferencesManager.getInstance(context).getUSERID();

        LoggerUtil.logItem(url);
        showLoading();

        Call<ResponseNewMLMDashboard> call = apiServices.getDashboardDataNew(url);
        call.enqueue(new Callback<ResponseNewMLMDashboard>() {
            @Override
            public void onResponse(@NotNull Call<ResponseNewMLMDashboard> call, @NotNull Response<ResponseNewMLMDashboard> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    assert response.body() != null;
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        setDashboardNewData(response.body());
                    } else {
                        showMessage("Something went wrong");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseNewMLMDashboard> call, @NotNull Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    void setDashboardNewData(ResponseNewMLMDashboard data) {
        try {
            lastLoginDate.setText(String.format("Last login : %s", data.getDashboardMlm().getFirstLoginDate()));
            tvDoj.setText(String.format(": %s", data.getDashboardMlm().getDoj()));

            unclearBal.setText(data.getDashboardMlm().getUncleared());
            selfIncome.setText(data.getDashboardMlm().getSelfincome());
            tvTeamsize.setText(data.getDashboardMlm().getTeamsize());
            mobile.setText(PreferencesManager.getInstance(context).getMOBILE());


            if (data.getDashboardMlm().getAdharStatus().equalsIgnoreCase("0")) {
                txtAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
                txtAddress.setText("Address Not Verified");
                txtAddress.setTextColor(getResources().getColor(R.color.red));
            } else if (data.getDashboardMlm().getAdharStatus().equalsIgnoreCase("1")) {
                txtAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pending, 0, 0, 0);
                txtAddress.setText("Address Pending");
                txtAddress.setTextColor(getResources().getColor(R.color.orange));
            } else if (data.getDashboardMlm().getAdharStatus().equalsIgnoreCase("2")) {
                txtAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
                txtAddress.setText("Address Verified");
                txtAddress.setTextColor(getResources().getColor(R.color.green));
            }
            if (data.getDashboardMlm().getBankStatus().equalsIgnoreCase("0")) {
                txtBank.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
                txtBank.setText("Bank Not Verified");
                txtBank.setTextColor(getResources().getColor(R.color.red));
            } else if (data.getDashboardMlm().getBankStatus().equalsIgnoreCase("1")) {
                txtBank.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pending, 0, 0, 0);
                txtBank.setText("Bank Pending");
                txtBank.setTextColor(getResources().getColor(R.color.orange));
            } else if (data.getDashboardMlm().getBankStatus().equalsIgnoreCase("2")) {
                txtBank.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
                txtBank.setText("Bank Verified");
                txtBank.setTextColor(getResources().getColor(R.color.green));
            }
            if (data.getDashboardMlm().getPanCardStatus().equalsIgnoreCase("0")) {
                txtPan.setText("Pan Not Verified");
                txtPan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
                txtPan.setTextColor(getResources().getColor(R.color.red));
            } else if (data.getDashboardMlm().getPanCardStatus().equalsIgnoreCase("1")) {
                txtPan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pending, 0, 0, 0);
                txtPan.setText("Pan Pending");
                txtPan.setTextColor(getResources().getColor(R.color.orange));
            } else if (data.getDashboardMlm().getPanCardStatus().equalsIgnoreCase("2")) {
                txtPan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
                txtPan.setText("Pan Verified");
                txtPan.setTextColor(getResources().getColor(R.color.green));
            }

            DashboardMLMBannerAdapter webBannerAdapter = new DashboardMLMBannerAdapter(context, data.getBanners());
            adRecycler.setAdapter(webBannerAdapter);

            DashBoardNewsInfoAdapter webnewsAdapter = new DashBoardNewsInfoAdapter(context, data.getNewsAndInfo());
            recyclerBanner.setAdapter(webnewsAdapter);
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

}

