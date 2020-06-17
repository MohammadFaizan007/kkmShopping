package com.digitalcashbag.utilities.tatasky;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.utilities.tatasky.adapter.ProductListRegion2Adapter;
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
import kkm.com.core.model.response.tatasky.productlisttwo.ProductListTwo;
import kkm.com.core.model.response.tatasky.response.AddinfoItem;
import kkm.com.core.model.response.tatasky.response.ResponseRegionList;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class RegionList extends BaseActivity implements MvpView {
    Bundle param;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_region)
    TextInputEditText etRegion;
    @BindView(R.id.input_layout_region)
    TextInputLayout inputLayoutRegion;
    @BindView(R.id.rv_gift_categories)
    RecyclerView rvGiftCategories;
    String list, region_id;
    PopupMenu region_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regionlist);
        ButterKnife.bind(this);
        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        title.setText("Tatasky Booking");

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGiftCategories.setLayoutManager(manager);
        region_menu = new PopupMenu(RegionList.this, etRegion);
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getRegionLIst();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    @OnClick({R.id.side_menu, R.id.et_region})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.et_region:
                region_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        etRegion.setText(menuItem.getTitle());
                        region_id = String.valueOf(Cons.addinfoItems.get(menuItem.getItemId() - 1).getRegionId());
                        getProductListRegion2();
                        return true;
                    }
                });
                region_menu.show();
//
                break;
        }
    }

    private void getRegionLIst() {
        showLoading();


        JsonObject param = new JsonObject();
        param.addProperty("Type", AppConfig.PAYLOAD_TYPE_ONE_TATA_REGION_LIST);
        param.addProperty("Amount_All", "1.00");
        param.addProperty("Amount", "1.00");
        LoggerUtil.logItem(param);

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(param.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<JsonObject> call = apiServices_utilityV2.getRegion(body);
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
                                Cons.addinfoItems = Utils.getList(addinfo, AddinfoItem.class);
                                for (int i = 0; i < Cons.addinfoItems.size(); i++) {
                                    region_menu.getMenu().add(0, (Cons.addinfoItems.get(i).getRegionId()), i, Cons.addinfoItems.get(i).getRegionName());
                                }

                            } else {
                                Gson gson = new GsonBuilder().create();
                                ResponseRegionList responseAvailabilityObject = gson.fromJson(addinfo, ResponseRegionList.class);
                                for (int i = 0; i < responseAvailabilityObject.getAddinfo().size(); i++) {
                                    region_menu.getMenu().add(0, responseAvailabilityObject.getAddinfo().get(i).getRegionId(), i, responseAvailabilityObject.getAddinfo().get(i).getRegionName());
                                }
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

    private void getProductListRegion2() {
        showLoading();
//        {"Type":"2","Amount":"1.00","Amount_All":"1.00","RegionId":"2","NUMBER":""}
        JsonObject object = new JsonObject();
        object.addProperty("Type", AppConfig.PAYLOAD_TYPE_TWO_TATA_GET_PRODUCTS);
        object.addProperty("Amount", "1.00");
        object.addProperty("Amount_All", "1.00");
        object.addProperty("RegionId", region_id);
        object.addProperty("NUMBER", "");
        LoggerUtil.logItem(object);

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(object.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<JsonObject> call = apiServices_utilityV2.getProductListRegion(body);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                try {

                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    JsonArray convertedObject = new Gson().fromJson(paramResponse, JsonArray.class);


                    LoggerUtil.logItem(response.body());
                    JsonObject object = convertedObject.get(0).getAsJsonObject();
                    if (object.get("error").getAsString().equalsIgnoreCase("0") && object.get("result").getAsString().equalsIgnoreCase("0")) {
                        try {
                            String addinfo = Utils.replaceBackSlash(object.get("addinfo").getAsString());
                            String firstChar = String.valueOf(addinfo.charAt(0));
                            if (firstChar.equalsIgnoreCase("[")) {
                                List<kkm.com.core.model.response.tatasky.productlisttwo.AddinfoItem> addinfoItems = Utils.getList(addinfo, kkm.com.core.model.response.tatasky.productlisttwo.AddinfoItem.class);
                                ProductListRegion2Adapter adapter = new ProductListRegion2Adapter(context, addinfoItems, RegionList.this);
                                rvGiftCategories.setAdapter(adapter);
                            } else {
                                Gson gson = new GsonBuilder().create();
                                ProductListTwo responseProductLists = gson.fromJson(addinfo, ProductListTwo.class);
                                ProductListRegion2Adapter adapter = new ProductListRegion2Adapter(context, responseProductLists.getAddinfo(), RegionList.this);
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
    public void getClickPosition(int position, String tag) {
        super.getClickPosition(position, tag);
        LoggerUtil.logItem(tag);
        LoggerUtil.logItem(position);
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            Bundle bundle = new Bundle();
            bundle.putString("product_id", tag);
            bundle.putString("region_id", region_id);
            goToActivity(RegionList.this, ProductList.class, bundle);
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}





