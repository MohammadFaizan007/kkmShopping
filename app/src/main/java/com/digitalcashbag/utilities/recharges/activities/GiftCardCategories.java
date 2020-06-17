package com.digitalcashbag.utilities.recharges.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.utilities.recharges.adapter.GiftCardCategoriesAdapter;
import com.google.gson.JsonObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.CheckErrorCode;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.giftCardResponse.ResponseGiftCardcategory;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class GiftCardCategories extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_gift_categories)
    RecyclerView rvGiftCategories;

    Bundle param;
    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giftcard_categories);
        ButterKnife.bind(this);

        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        title.setText(param.getString("from"));

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGiftCategories.setLayoutManager(manager);

        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getGiftCardCategories();
        else
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
    }

    @OnClick(R.id.side_menu)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();
//        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void getGiftCardCategories() {
        showLoading();
//        RequestGiftCardCategories categories = new RequestGiftCardCategories();
//        categories.setAmount("10.00");
//        categories.setAmountAll("10.00");
//        categories.setType("1");

        JsonObject param = new JsonObject();
        param.addProperty("Amount", "10.00");
        param.addProperty("Amount_All", "10.00");
        param.addProperty("Type", "1");

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(param.toString(), easypay_key));
            Log.e("REQUEST", body.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<JsonObject> call = apiServices_utilityV2.getGiftCardCategories(body);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {

                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    LoggerUtil.logItem(paramResponse);
                    List<ResponseGiftCardcategory> list = Utils.getList(paramResponse, ResponseGiftCardcategory.class);

                    if (response.body() != null && list.size() > 0 && list.get(0).getError().equalsIgnoreCase("0") &&
                            list.get(0).getResult().equalsIgnoreCase("0")) {
                        GiftCardCategoriesAdapter adapter = new GiftCardCategoriesAdapter(context, list.get(0).getAddinfo(), GiftCardCategories.this);
                        rvGiftCategories.setAdapter(adapter);
                        txtNoData.setVisibility(View.GONE);
                    } else {
                        txtNoData.setVisibility(View.VISIBLE);
                        CheckErrorCode checkErrorCode = new CheckErrorCode();
                        checkErrorCode.isValidTransaction(context, list.get(0).getError());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    txtNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void getGiftCardCategoryId(String id, String name) {
        param = new Bundle();
        param.putString("giftId", id);
        param.putString("giftName", name);
        goToActivity(context, GiftCoupons.class, param);
    }
}
