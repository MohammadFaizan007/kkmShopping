package com.digitalcashbag.shopping.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.common_activities.FullScreenLogin;
import com.digitalcashbag.shopping.adapter.ItemsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.RequestProducList;
import kkm.com.core.model.response.ResponseProductList;
import kkm.com.core.retrofit.Dialog_dismiss;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class ShoppingCategoryItemsActivity extends BaseActivity implements Dialog_dismiss {


    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.badge)
    ImageView badge;
    @BindView(R.id.actionbar_notifcation_textview)
    TextView cartCounter;
    @BindView(R.id.shoppingItemsrecyclerview)
    RecyclerView shoppingItemsrecyclerview;
    @BindView(R.id.pbProgress)
    ProgressBar pbProgress;
    @BindView(R.id.noRecord)
    TextView noRecord;

    private String CategoryId = "";
    private String ParentCategoryID = "";
    private ArrayList<Integer> brandArr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_items_activity);
        ButterKnife.bind(this);

        try {
            cartCounter.setText(String.valueOf(PreferencesManager.getInstance(context).getCartCount()));
            Bundle bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
            CategoryId = bundle.getString("CategoryId");
//            ParentCategoryID = bundle.getString("ParentCategoryID");
            String categoryName = bundle.getString("CategoryName");
            PreferencesManager.getInstance(context).setCategoryName(categoryName);
            title.setText(categoryName);
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                setProductList();
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }

        } catch (Exception e) {

        }
    }

    public void updateCounter() {
        cartCounter.setText(String.valueOf(PreferencesManager.getInstance(context).getCartCount()));
    }

    private void setProductList() {
        showLoading();
        brandArr = new ArrayList<>();
        String priceFrom = "", priceTo = "", parameter = "";
//        if (!categoryWise) {
//            Bundle bundle = getIntent().getExtras();
//            priceFrom = bundle.getString("priceFrom");
//            priceTo = bundle.getString("priceTo");
//            parameter = bundle.getString("parameter");
//        }
        RequestProducList requestProducList = new RequestProducList();
        requestProducList.setCategoryID(CategoryId);
        requestProducList.setParentCategoryID(ParentCategoryID);
        requestProducList.setPriceRangeFrom(priceFrom);
        requestProducList.setPriceRangeTo(priceTo);
        requestProducList.setBrandID("1");
        requestProducList.setParameter(parameter);
        requestProducList.setBrandArr(brandArr);
        LoggerUtil.logItem(requestProducList);
        Call<ResponseProductList> productListCall = apiServices_shoopping.getProductList(requestProducList);
        productListCall.enqueue(new Callback<ResponseProductList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProductList> call, @NonNull Response<ResponseProductList> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    ItemsAdapter adapter = new ItemsAdapter(context, response.body().getProductList());
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
                    shoppingItemsrecyclerview.setLayoutManager(mLayoutManager);
                    shoppingItemsrecyclerview.setItemAnimator(new DefaultItemAnimator());
                    shoppingItemsrecyclerview.setAdapter(adapter);
                } else {
                    noRecord.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseProductList> call, @NonNull Throwable t) {
                hideLoading();
                noRecord.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick({R.id.side_menu, R.id.badge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.badge:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                        FullScreenLogin dialog = new FullScreenLogin();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        dialog.show(ft, FullScreenLogin.TAG);
//                        dialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
//                            @Override
//                            public void onDismiss(DialogInterface dialog) {
//                                LoggerUtil.logItem("Dismiss");
//                                if (PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
//                                    goToActivity(context, ViewCartActivity.class, null);
//                                }
//                            }
//                        });
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
    protected void onResume() {
        super.onResume();
        updateCounter();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onDismiss() {
        goToActivity(this, ViewCartActivity.class, null);
    }

}
