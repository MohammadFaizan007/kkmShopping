package com.digitalcashbag.shopping.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
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
import com.digitalcashbag.mlm.fragments.profile.EditProfileMlm;
import com.digitalcashbag.shopping.activities.ProfileActivity;
import com.google.gson.JsonObject;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.personal_info_lo)
    ConstraintLayout personalInfoLo;
    @BindView(R.id.personal_info_img)
    ImageView personalInfoImg;
    @BindView(R.id.bank_details_lo)
    ConstraintLayout bankDetailsLo;
    @BindView(R.id.bank_detail_img)
    ImageView bankDetailImg;
    @BindView(R.id.insurance_detail_lo)
    ConstraintLayout insuranceDetailLo;
    @BindView(R.id.insurance_img)
    ImageView insuranceImg;
    @BindView(R.id.user_image)
    RoundedImageView userImage;
    @BindView(R.id.additional_detail)
    TextView additionalDetail;
    @BindView(R.id.first_name)
    TextView FirstName;
    @BindView(R.id.last_name)
    TextView LastName;
    @BindView(R.id.dob)
    TextView Dob;
    @BindView(R.id.edu_qualification)
    TextView EduQualification;
    @BindView(R.id.gst_number)
    TextView GstNumber;
    @BindView(R.id.father_name)
    TextView FatherName;
    @BindView(R.id.nominee_name)
    TextView NomineeName;
    @BindView(R.id.nominee_relation)
    TextView NomineeRelation;
    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.married_atatus)
    TextView marriedatatus;
    @BindView(R.id.acco_number)
    TextView AccoNumber;
    @BindView(R.id.bank_name)
    TextView BankName;
    @BindView(R.id.ifsc_code)
    TextView IfscCode;
    @BindView(R.id.branch_name)
    TextView BranchName;
    @BindView(R.id.insurance_number)
    TextView InsuranceNumber;
    @BindView(R.id.policy_holder_name)
    TextView PolicyHolderName;
    @BindView(R.id.premium)
    TextView Premium;
    @BindView(R.id.company_name)
    TextView CompanyName;
    @BindView(R.id.insur_nominee_name)
    TextView InsurNomineeName;
    @BindView(R.id.policy_number)
    TextView PolicyNumber;
    @BindView(R.id.insu_type)
    TextView insu_type;
    @BindView(R.id.vehicle_number)
    TextView VehicleNumber;
    @BindView(R.id.purchased_year)
    TextView PurchasedYear;
    @BindView(R.id.gen_insur_premium)
    TextView GenInsurPremium;
    @BindView(R.id.expiry_date)
    TextView ExpiryDate;
    @BindView(R.id.gen_insu_company_name)
    TextView GenInsuCompanyName;
    @BindView(R.id.edit_profile_btn)
    Button edit_profile_btn;
    Unbinder unbinder;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        hideKeyboard();
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
                Log.e("Profile View==========", "");
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                        UrlConstants.profile = response.body();
                        setUserProfile();
                    } else {
                        edit_profile_btn.setVisibility(View.GONE);
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
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                            .placeholder(R.mipmap.ic_launcher_foreground)
                            .error(R.mipmap.ic_launcher_foreground))
                    .into(userImage);

            PreferencesManager.getInstance(context).setPROFILEPIC(UrlConstants.profile.getApiUserProfile().getProfilepic());

            FirstName.setText(profile.getFirstName());
            LastName.setText(profile.getLastName());
            Dob.setText(profile.getDob());
            gender.setText(profile.getGender());
            marriedatatus.setText(profile.getMarritalStatus());
            FatherName.setText(profile.getEmail()); // Father Name change to Email
            NomineeName.setText(profile.getNomineeName());
            NomineeRelation.setText(profile.getRelationwithNominee());
            AccoNumber.setText(profile.getAccountNo());
            BankName.setText(profile.getBankName());
            IfscCode.setText(profile.getIfscCode());
            BranchName.setText(profile.getBranchName());
            InsuranceNumber.setText(profile.getInsuranceNo());
            PolicyHolderName.setText(profile.getPolicyHolderName());
            Premium.setText(profile.getPremium());
            CompanyName.setText(profile.getInsCompanyName());
            InsurNomineeName.setText(profile.getInsNomineeName());
            insu_type.setText(profile.getInsuranceType());
            PolicyNumber.setText(profile.getPolicyNo());
            VehicleNumber.setText(profile.getVehicleNo());
            PurchasedYear.setText(profile.getPurchasedYear());
            GenInsurPremium.setText(profile.getGenPremium());
            ExpiryDate.setText(profile.getExpiryDate());
            GenInsuCompanyName.setText(profile.getGenCompanyName());

            edit_profile_btn.setVisibility(View.VISIBLE);

        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.user_image, R.id.personal_info_img, R.id.bank_detail_img, R.id.insurance_img, R.id.edit_profile_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_image:
                break;
            case R.id.personal_info_img:
                if (personalInfoLo.getVisibility() == View.VISIBLE) {
                    personalInfoLo.setVisibility(View.GONE);
                    personalInfoImg.setBackgroundResource(R.drawable.pluse_ic);
                } else {
                    personalInfoLo.setVisibility(View.VISIBLE);
                    personalInfoImg.setBackgroundResource(R.drawable.minus_ic);
                }
                break;
            case R.id.bank_detail_img:
                if (bankDetailsLo.getVisibility() == View.VISIBLE) {
                    bankDetailsLo.setVisibility(View.GONE);
                    bankDetailImg.setBackgroundResource(R.drawable.pluse_ic);
                } else {
                    bankDetailsLo.setVisibility(View.VISIBLE);
                    bankDetailImg.setBackgroundResource(R.drawable.minus_ic);
                }
                break;
            case R.id.insurance_img:
                if (insuranceDetailLo.getVisibility() == View.VISIBLE) {
                    insuranceDetailLo.setVisibility(View.GONE);
                    insuranceImg.setBackgroundResource(R.drawable.pluse_ic);
                } else {
                    insuranceDetailLo.setVisibility(View.VISIBLE);
                    insuranceImg.setBackgroundResource(R.drawable.minus_ic);
                }
                break;
            case R.id.edit_profile_btn:
                ((ProfileActivity) context).ReplaceFragmentAddBack(new EditProfileMlm(), "Edit Profile");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
