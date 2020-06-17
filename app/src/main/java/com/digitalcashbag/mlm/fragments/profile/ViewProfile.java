package com.digitalcashbag.mlm.fragments.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.digitalcashbag.mlm.activities.MainContainerMLM;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.constants.UrlConstants;
import kkm.com.core.model.response.profile.ApiUserProfile;
import kkm.com.core.model.response.profile.ResponseViewProfile;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfile extends BaseFragment {

    @BindView(R.id.prof_image)
    ImageView profImage;
    @BindView(R.id.prof_name)
    TextView profName;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvUserId)
    TextView tvUserId;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.total_member)
    TextView totalMember;
    @BindView(R.id.login_date)
    TextView loginDate;

    @BindView(R.id.tv_insuranceNo)
    TextView tvInsuranceNo;
    @BindView(R.id.tv_policyHolderName)
    TextView tvPolicyHolderName;
    @BindView(R.id.tv_premium)
    TextView tvPremium;
    @BindView(R.id.tv_insCompanyName)
    TextView tvInsCompanyName;
    @BindView(R.id.tv_insNomineeName)
    TextView tvInsNomineeName;
    @BindView(R.id.tv_insuranceType)
    TextView tvInsuranceType;
    @BindView(R.id.tv_policyNo)
    TextView tvPolicyNo;
    @BindView(R.id.tv_vehicleNo)
    TextView tvVehicleNo;
    @BindView(R.id.tv_purchasedYear)
    TextView tvPurchasedYear;
    @BindView(R.id.tv_genPremium)
    TextView tv_genPremium;
    @BindView(R.id.tv_expiryDate)
    TextView tvExpiryDate;
    @BindView(R.id.tv_genCompanyName)
    TextView tvGenCompanyName;
    @BindView(R.id.editProfile)
    Button editProfile;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_profile_mlm, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        editProfile.setOnClickListener(v -> ((MainContainerMLM) context).addFragment(new EditProfileMlm(), "Edit Profile"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getUserProfile();
        } else {
            createInfoDialog(context, "Alert", getString(R.string.alert_internet));
        }
    }

    void getUserProfile() {
        JsonObject object = new JsonObject();
        object.addProperty("MemID", PreferencesManager.getInstance(context).getUSERID());
        showLoading();
        Call<ResponseViewProfile> call = apiServices.getUserProfileMlm(object);
        call.enqueue(new Callback<ResponseViewProfile>() {
            @Override
            public void onResponse(@NonNull Call<ResponseViewProfile> call, @NonNull Response<ResponseViewProfile> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        UrlConstants.profile = response.body();
                        setUserProfile();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseViewProfile> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    void setUserProfile() {
        try {
            ApiUserProfile profile = UrlConstants.profile.getApiUserProfile();
            tvName.setText(profile.getDisplayName());
            tvUserId.setText(profile.getLoginId());
            mobile.setText(profile.getMobile());
            email.setText(profile.getEmail());
            totalMember.setText(profile.getTotalMember());
            loginDate.setText(profile.getJoiningDate());

            tvInsuranceNo.setText(profile.getInsuranceNo());
            tvPolicyHolderName.setText(profile.getPolicyHolderName());
            tvPremium.setText(profile.getPremium());
            tvInsCompanyName.setText(profile.getInsCompanyName());
            tvInsNomineeName.setText(profile.getInsNomineeName());
            tvInsuranceType.setText(profile.getInsuranceType());
            tvPolicyNo.setText(profile.getPolicyNo());
            tvVehicleNo.setText(profile.getVehicleNo());
            tvPurchasedYear.setText(profile.getPurchasedYear());
            tv_genPremium.setText(profile.getGenPremium());
            tvExpiryDate.setText(profile.getExpiryDate());
            tvGenCompanyName.setText(profile.getGenCompanyName());

            PreferencesManager.getInstance(context).setNAME(profile.getFirstName());
            PreferencesManager.getInstance(context).setLNAME(profile.getLastName());
            PreferencesManager.getInstance(context).setPINCODE(profile.getPinCode());
            PreferencesManager.getInstance(context).setMOBILE(profile.getMobile());
            PreferencesManager.getInstance(context).setEMAIL(profile.getEmail());
            PreferencesManager.getInstance(context).setDOB(profile.getDob());
            PreferencesManager.getInstance(context).setState(profile.getState());
            PreferencesManager.getInstance(context).setCity(profile.getCity());
            PreferencesManager.getInstance(context).setAddress(profile.getAddress1());

            Glide.with(context).load(UrlConstants.profile.getApiUserProfile().getProfilepic()).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).placeholder(R.mipmap.ic_launcher_foreground).error(R.mipmap.ic_launcher_foreground)).into(profImage);

            PreferencesManager.getInstance(context).setPROFILEPIC(UrlConstants.profile.getApiUserProfile().getProfilepic());

        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

}
