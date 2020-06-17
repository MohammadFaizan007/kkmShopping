package com.digitalcashbag.shopping.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.common_activities.FullScreenLogin;
import com.digitalcashbag.shopping.fragment.ShoppingCategoryFragment;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.cart.ResponseCartList;
import kkm.com.core.retrofit.Dialog_dismiss;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingActivityMain extends BaseActivity implements Dialog_dismiss {

    @BindView(R.id.title)
    public TextView title;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.badge)
    ImageView badge;
    @BindView(R.id.actionbar_notifcation_textview)
    TextView actionbarNotifcationTextview;
    @BindView(R.id.shopping_frame)
    FrameLayout shoppingFrame;
    Fragment currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_activity_main);
        ButterKnife.bind(this);
        ReplaceFragment_AddBack(new ShoppingCategoryFragment(), "Shopping", 0);

    }

    @OnClick({R.id.side_menu, R.id.badge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                onBackPressed();
                break;
            case R.id.badge:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                        FullScreenLogin dialog = new FullScreenLogin();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        dialog.show(ft, FullScreenLogin.TAG);
                    } else {
                        goToActivity(this, ViewCartActivity.class, null);
                    }
                } else {
                    showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        LoggerUtil.logItem("BACK_STACK");
        LoggerUtil.logItem(fm.getBackStackEntryCount());

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }


    }

    public void getCartItems() {
        JsonObject object = new JsonObject();
        object.addProperty("UserId", PreferencesManager.getInstance(context).getUSERID());
        LoggerUtil.logItem(object);

        Call<ResponseCartList> call = apiServices_shoopping.getCartItems(object);
        call.enqueue(new Callback<ResponseCartList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCartList> call, @NonNull Response<ResponseCartList> response) {
                LoggerUtil.logItem(response.body());

                try {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        PreferencesManager.getInstance(context).setCartCount(response.body().getCartItemList().size());
                        updateCounter();
                    } else {
                        PreferencesManager.getInstance(context).setCartCount(0);
                        updateCounter();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LoggerUtil.logItem("");

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCartList> call, @NonNull Throwable t) {

            }

        });
    }

    public void updateCounter() {
        actionbarNotifcationTextview.setText(String.valueOf(PreferencesManager.getInstance(context).getCartCount()));
    }

    public void ReplaceFragment_AddBack(Fragment setFragment, String title_st, int id) {
        currentFragment = setFragment;
        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(id));
        bundle.putString("title", title_st);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().addToBackStack(null);
        setFragment.setArguments(bundle);
        transaction.replace(R.id.shopping_frame, setFragment, title_st);
        title.setText(title_st);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getCartItems();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    @Override
    public void onDismiss() {
        goToActivity(this, ViewCartActivity.class, null);
    }

}
