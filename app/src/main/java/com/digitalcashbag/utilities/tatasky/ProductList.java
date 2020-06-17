package com.digitalcashbag.utilities.tatasky;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.utilities.tatasky.adapter.ProductListAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.AppConfig;
import kkm.com.core.app.CheckErrorCode;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.tatasky.productlist.AddinfoItem;
import kkm.com.core.model.response.tatasky.productlist.ResponseProductLists;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class ProductList extends BaseActivity implements MvpView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_gift_categories)
    RecyclerView rvGiftCategories;
    String product_id = "", region_id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giftcard_categories);
        ButterKnife.bind(this);
        title.setText("Tatasky Products");
        Bundle bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        product_id = bundle.getString("product_id");
        region_id = bundle.getString("region_id");

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGiftCategories.setLayoutManager(manager);

        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getProductList(product_id);
        else
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
    }

    @OnClick(R.id.side_menu)
    public void onViewClicked() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void getProductList(String productId) {
        showLoading();
//        {"Type":"3","Amount":"1.00","Amount_All":"1.00","ProductId":"001","NUMBER":"9918703130"}
//        RequestProductListTwo categories = new RequestProductListTwo();
//        categories.setType(AppConfig.PAYLOAD_TYPE_THREE_TATA_PRODUCT_DETAIL);
//        categories.setAmount("1.00");
//        categories.setAmountAll("1.00");
//        categories.setProductId(productId);
//        categories.setNUMBER("");
//        LoggerUtil.logItem(categories);

        JsonObject param = new JsonObject();
        param.addProperty("Type", AppConfig.PAYLOAD_TYPE_THREE_TATA_PRODUCT_DETAIL);
        param.addProperty("Amount", "1.00");
        param.addProperty("Amount_All", "1.00");
        param.addProperty("ProductId", productId);
        param.addProperty("NUMBER", "");

        LoggerUtil.logItem(param);

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(param.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<JsonObject> call = apiServices_utilityV2.getGetProductList(body);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                try {
                    LoggerUtil.logItem(response.body());

                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    JsonArray convertedObject = new Gson().fromJson(paramResponse, JsonArray.class);

                    JsonObject object = convertedObject.get(0).getAsJsonObject();
                    if (object.get("error").getAsString().equalsIgnoreCase("0") && object.get("result").getAsString().equalsIgnoreCase("0")) {
                        try {
                            String addinfo = Utils.replaceBackSlash(object.get("addinfo").getAsString());
                            String firstChar = String.valueOf(addinfo.charAt(0));
                            if (firstChar.equalsIgnoreCase("[")) {
                                List<AddinfoItem> addinfoItems = Utils.getList(addinfo, AddinfoItem.class);
                                ProductListAdapter adapter = new ProductListAdapter(context, addinfoItems, ProductList.this);
                                rvGiftCategories.setAdapter(adapter);
                            } else {
                                Gson gson = new GsonBuilder().create();
                                ResponseProductLists responseProductLists = gson.fromJson(addinfo, ResponseProductLists.class);
                                ProductListAdapter adapter = new ProductListAdapter(context, responseProductLists.getAddinfo(), ProductList.this);
                                rvGiftCategories.setAdapter(adapter);
                                Log.e("Object==", "Fail");
                            }
                        } catch (Error | Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        CheckErrorCode checkErrorCode = new CheckErrorCode();
                        checkErrorCode.isValidTransaction(context, object.get("error").getAsString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void getClickPosition(int price, String pro_id) {
        super.getClickPosition(price, pro_id);
        LoggerUtil.logItem(price);
        LoggerUtil.logItem(pro_id);
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            Bundle bundle = new Bundle();
            bundle.putString("product_price", String.valueOf(price));
            bundle.putString("product_id", pro_id);
            bundle.putString("region_id", region_id);
            goToActivity(ProductList.this, TataSkyBooking.class, bundle);
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

}
