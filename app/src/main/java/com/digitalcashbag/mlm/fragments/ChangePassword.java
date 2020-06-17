package com.digitalcashbag.mlm.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.digitalcashbag.R;
import com.digitalcashbag.common_activities.LoginActivity;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.request.RequestChangePassword;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends BaseFragment {

    @BindView(R.id.btn_reset)
    Button btnReset;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.et_old_pswd)
    TextInputEditText etOldPswd;
    @BindView(R.id.et_new_pswd)
    TextInputEditText etNewPswd;
    @BindView(R.id.et_confrm_pswd)
    TextInputEditText etConfrmPswd;
    private Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

        btnSubmit.setOnClickListener(view12 -> {
            if (NetworkUtils.getConnectivityStatus(context) == 0)
                showMessage(getResources().getString(R.string.alert_internet));
            else if (Validation())
                getChangedPassword();
        });

        btnReset.setOnClickListener(view1 -> reset());

        etConfrmPswd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    if (!etConfrmPswd.getText().toString().equals(etNewPswd.getText().toString())) {
                        etConfrmPswd.setError("Password not matched");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void getChangedPassword() {
        RequestChangePassword requestChangePswd = new RequestChangePassword();
        requestChangePswd.setMobile(PreferencesManager.getInstance(context).getMOBILE());
        requestChangePswd.setOldPassword(etOldPswd.getText().toString().trim());
        requestChangePswd.setNewPassword(etNewPswd.getText().toString().trim());
        requestChangePswd.setFormname("ChangeMemberPassword");
        requestChangePswd.setUpdatedBy(PreferencesManager.getInstance(context).getUSERID());
        showLoading();
        Call<JsonObject> call = apiServices.ChangePassword(requestChangePswd);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                Log.e("Resp", response.body().toString());
                if (response.isSuccessful()) {
                    if (response.body().get("response").getAsString().equalsIgnoreCase("Success")) {
                        showMessage("Updated Successfully, Kindly, login again to continue.");
                        reset();

                        try {
                            int WAIT_TIME = 2000;
                            new Handler().postDelayed(() -> {
                                boolean name = PreferencesManager.getInstance(context).getfirst_visit();
                                PreferencesManager.getInstance(context).clear();
                                PreferencesManager.getInstance(context).setfirst_visit(name);

                                Intent intent1 = new Intent(context, LoginActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent1);
                                context.finish();
                            }, WAIT_TIME);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        showMessage(response.body().get("response").getAsString());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private boolean Validation() {
        if (etOldPswd.getText().toString().length() == 0) {
            etOldPswd.setError("Please enter old password");
            return false;
        } else if (etNewPswd.getText().toString().length() == 0) {
            etNewPswd.setError("Please enter new password");
            return false;
        }/* else if (!etNewPswd.getText().toString().equals(etConfrmPswd.getText().toString())) {
            etConfrmPswd.setError("Password not matched");
            etNewPswd.setError("Password not matched");
            return false;
        }*/
        return true;
    }

    private void reset() {
        etOldPswd.setText("");
        etNewPswd.setText("");
        etConfrmPswd.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
