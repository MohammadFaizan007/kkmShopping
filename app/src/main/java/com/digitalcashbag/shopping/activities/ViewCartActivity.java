package com.digitalcashbag.shopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.shopping.adapter.ViewCartActivityAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.digitalcashbag.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.cart.CartItemListItem;
import kkm.com.core.model.response.cart.ResponseCartList;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digitalcashbag.shopping.Cons.cartItemList;

public class ViewCartActivity extends BaseActivity {

    @BindView(R.id.tvemty)
    TextView tvemty;
    @BindView(R.id.gotoDashboard)
    TextView gotoDashboard;
    @BindView(R.id.view_cart_list)
    RecyclerView rvCartList;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.tv_product_price_item_count)
    TextView tvProductPriceItemCount;
    @BindView(R.id.tv_product_total_price)
    TextView tvProductTotalPrice;
    @BindView(R.id.tv_product_shipping_charges)
    TextView tvProductShippingCharges;
    @BindView(R.id.tv_total_amount)
    TextView tvTotalAmount;
    @BindView(R.id.cv_price_det)
    CardView cvPriceDet;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.removeall)
    Button removeall;

    @BindView(R.id.cv_amount)
    CardView cvAmount;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.badge)
    ImageView badge;
    @BindView(R.id.actionbar_notifcation_textview)
    TextView cartCounter;

    ViewCartActivityAdapter cartAdapter;
    float finalAmount, courierPrice = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_cart_activity);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.cart));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCartList.setLayoutManager(manager);
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getCartItems();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }


    @OnClick({R.id.side_menu, R.id.badge, R.id.gotoDashboard, R.id.removeall, R.id.tv_continue_to_checkout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.badge:
                break;
            case R.id.gotoDashboard:
                context.onBackPressed();
                break;
            case R.id.removeall:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    removeAll();
                } else {
                    showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                }
                break;
            case R.id.tv_continue_to_checkout:
                try {
                    LoggerUtil.logItem(courierPrice);
                    Intent intent = new Intent(context, Checkout.class);
                    intent.putExtra("TOTAL", String.valueOf(finalAmount));
                    intent.putExtra("SIZE", String.valueOf(cartItemList.size()));
                    intent.putExtra("courierPrice", String.valueOf(courierPrice));
                    startActivity(intent);
                    this.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void getCartItems() {
        JsonObject object = new JsonObject();
        object.addProperty("UserId", PreferencesManager.getInstance(context).getUSERID());
        LoggerUtil.logItem(object);
        showLoading();
        Call<ResponseCartList> call = apiServices_shoopping.getCartItems(object);
        call.enqueue(new Callback<ResponseCartList>() {

            @Override
            public void onResponse(@NonNull Call<ResponseCartList> call, @NonNull Response<ResponseCartList> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                cartItemList = new ArrayList<>();
                try {
                    cartItemList = response.body().getCartItemList();
                    courierPrice = 0;
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        for (int i = 0; i < cartItemList.size(); i++) {
                            courierPrice = courierPrice + Float.parseFloat(cartItemList.get(i).getCourierPrice()) * Integer.parseInt(cartItemList.get(i).getProductQuantity());
                        }
                        LoggerUtil.logItem(courierPrice);
                        PreferencesManager.getInstance(context).setCartCount(response.body().getCartItemList().size());
                        updateCounter();
                        showCard(true, response.body());
                    } else {
                        PreferencesManager.getInstance(context).setCartCount(0);
                        updateCounter();
                        showCard(false, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LoggerUtil.logItem("");

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCartList> call, @NonNull Throwable t) {
                hideLoading();
            }

        });
    }

    public void updateCounter() {
        cartCounter.setText(String.valueOf(PreferencesManager.getInstance(context).getCartCount()));
    }

    public void showCard(boolean show, ResponseCartList cartList) {
        if (show) {
            cartAdapter = new ViewCartActivityAdapter(context, cartList.getCartItemList());
            rvCartList.setAdapter(cartAdapter);
            rvCartList.setHasFixedSize(true);
            cartItemList = cartList.getCartItemList();
            calculateTotalAmount(cartList.getCartItemList());
            cvPriceDet.setVisibility(View.VISIBLE);
            cvAmount.setVisibility(View.VISIBLE);
            rvCartList.setVisibility(View.VISIBLE);
            tvemty.setVisibility(View.GONE);
            gotoDashboard.setVisibility(View.GONE);
        } else {
            cvPriceDet.setVisibility(View.GONE);
            cvAmount.setVisibility(View.GONE);
            rvCartList.setVisibility(View.GONE);
            tvemty.setVisibility(View.VISIBLE);
            gotoDashboard.setVisibility(View.VISIBLE);
        }
    }

    private void calculateTotalAmount(List<CartItemListItem> cartList) {

        float ttlAmt = 0.00f;
        for (int i = 0; i < cartList.size(); i++) {
            float amount = Float.parseFloat(cartList.get(i).getProductPrice());
            int quantity = Integer.parseInt(cartList.get(i).getProductQuantity());
            float totalAmount = amount * quantity;
            Log.e("AMOUNT", "= " + amount);
            Log.e("quantity", "= " + quantity);

            ttlAmt += totalAmount;

        }
        finalAmount = ttlAmt;
        tvProductPriceItemCount.setText(String.format(Locale.getDefault(), "Subtotal (%d Items)", cartList.size()));
        tvProductTotalPrice.setText(String.format("₹ %s", ttlAmt));
        tvTotalAmount.setText(String.format("₹ %s", ttlAmt));
//        getShipmentAmount(ttlAmt);
    }

//    private void getShipmentAmount(float totalAmount) {
//
//        JsonObject param = new JsonObject();
//
//        Call<ResponseShippingCharge> chargeCall = apiServices_shoopping.getShippingCharge(param);
//        chargeCall.enqueue(new Callback<ResponseShippingCharge>() {
//            @Override
//            public void onResponse(@NonNull Call<ResponseShippingCharge> call, @NonNull Response<ResponseShippingCharge> response) {
//                LoggerUtil.logItem(response.body());
//                if (response.body().getResponse().equalsIgnoreCase("Success")) {
//                    finalAmount = 0;
//                    if ((totalAmount) > Float.parseFloat(response.body().getShippingCharges().get(0).getThresholdAmt())) {
//                        finalAmount = (totalAmount + Float.parseFloat((response.body().getShippingCharges().get(0).getShippingAmt())));
//                        tvProductShippingCharges.setText(String.format("₹ %s", response.body().getShippingCharges().get(0).getShippingAmt()));
//                    } else {
//                        finalAmount = (totalAmount);
//                        tvProductShippingCharges.setText(getString(R.string.no_shipping_charge));
//                    }
//                    tvTotalAmount.setText(String.format("₹ %s", finalAmount));
////                    totalAmountPay.setText(String.format("₹ %s", finalAmount));
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ResponseShippingCharge> call, @NonNull Throwable t) {
//
//            }
//        });
//    }

    public void removeAll() {
        showLoading();

        JsonObject object = new JsonObject();

        object.addProperty("UserID", PreferencesManager.getInstance(context).getUSERID());
        JsonArray productArr = new JsonArray();
        for (int i = 0; i < cartItemList.size(); i++) {
            productArr.add(Integer.parseInt(cartItemList.get(i).getProductId()));
        }
        object.add("ProductArr", productArr);
        LoggerUtil.logItem(object);
        Call<JsonObject> jsonObjectCall = apiServices_shoopping.addCartItemDeleteAll(object);
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response);
                if (response.body().get("response").getAsString().equalsIgnoreCase("Success")) {
                    cartItemList = new ArrayList<>();
                    getCartItems();

                }

            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
