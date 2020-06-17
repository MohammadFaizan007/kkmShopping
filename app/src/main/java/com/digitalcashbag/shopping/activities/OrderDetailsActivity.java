package com.digitalcashbag.shopping.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.google.gson.JsonObject;
import com.kofigyan.stateprogressbar.StateProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.OrderListItem;
import kkm.com.core.model.response.OrderTracking;
import kkm.com.core.model.response.ResponseProductCancel;
import kkm.com.core.model.response.ResponseProductExchange;
import kkm.com.core.model.response.ResponseProductReturn;
import kkm.com.core.model.response.ResponseTrackOrder;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class OrderDetailsActivity extends BaseActivity {

    public static ResponseProductReturn viewReturnDialog;
    public static ResponseProductExchange viewExchangeDialog;
    public static OrderTracking trackOrder;
    @BindView(R.id.order_id)
    TextView orderId;
    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.seller_name)
    TextView sellerName;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.product_image)
    ImageView productImage;
    @BindView(R.id.shipping_amount)
    TextView shippingAmount;
    @BindView(R.id.product_price)
    TextView productPrice;
    @BindView(R.id.total_amount)
    TextView totalAmount;
    @BindView(R.id.tv_product_quantity)
    TextView tvProductQuantity;
    Dialog view_return_dialog;
    Dialog view_exchange_dialog;
    Dialog cancel_dialog;
    @BindView(R.id.order_status)
    TextView orderStatus;
    @BindView(R.id.step_view)
    StateProgressBar stepView;
    @BindView(R.id.btn_exchange)
    Button btnExchange;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_return)
    Button btnReturn;
    OrderListItem orderListItem;
    @BindView(R.id.order_date)
    TextView orderDate;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.address_type)
    TextView addressType;
    @BindView(R.id.title)
    TextView title;
    private String[] arrayComplete = {"Accepted", "Packed", "Shipped", "Delivered", "Refunded"};
    private String[] arrayCancelled = {"Accepted", "Packed", "Cancelled"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        try {
            Bundle bundle = this.getIntent().getExtras();
            orderListItem = bundle.getParcelable("order_item");
            Log.e("INSIDE==", "OrderDetailsActivity");
            LoggerUtil.logItem(orderListItem);

            title.setText(R.string.order_summary);


            orderId.setText(String.format("ORDER ID : %s", orderListItem.getOrderNumber()));
            productName.setText(orderListItem.getParoductName());
            sellerName.setText(String.format("Seller : %s", orderListItem.getFirmName()));
            status.setText(String.format("Status : %s", orderListItem.getOredrStatus()));
            shippingAmount.setText(String.format("Shipping amount : %s", orderListItem.getShippingPrice()));
            productPrice.setText(String.format("Product amount : %s", orderListItem.getPrice()));
            totalAmount.setText(String.format("Total Price : %s", orderListItem.getTotalPrice()));
            tvProductQuantity.setText(String.format("Quantity : %s", orderListItem.getProductQuantity()));
            orderDate.setText(String.format("Ordered on : %s", orderListItem.getOrderDate()));
            addressType.setText(String.format("Shipping Address : (%s)", orderListItem.getAddressType()));
            address.setText(String.format("%s,%s,%s\n%s\n%s", orderListItem.getAddress(), orderListItem.getCity(), orderListItem.getState(), orderListItem.getPincode(), orderListItem.getLandmark()));
            Glide.with(context).load(orderListItem.getProductImage()).apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available).error(R.drawable.image_not_available)).into(productImage);
            viewVisible();

            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getTrackOrder(orderListItem.getOrderItemId());
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.side_menu, R.id.btn_exchange, R.id.btn_cancel, R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                onBackPressed();
                break;
            case R.id.btn_exchange:
                getProductExchange(orderListItem.getVendorId(), orderListItem.getFkProductDetailId(), orderListItem.getOrderItemId(), orderListItem.getOrderId());
                break;
            case R.id.btn_cancel:
                cancelDialog();
//                getItemCancel(PreferencesManager.getInstance(context).getUSERID(), orderListItem.getOrderItemId(), "");
                break;
            case R.id.btn_return:
                getProductReturn(orderListItem.getVendorId(), orderListItem.getFkProductDetailId(), orderListItem.getOrderItemId(), orderListItem.getOrderId());
                break;
        }
    }

    private void cancelDialog() {
        try {
            hideKeyboard();
            cancel_dialog = new Dialog(context);
            cancel_dialog.setCanceledOnTouchOutside(true);
            cancel_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            cancel_dialog.setContentView(R.layout.cancel_remark_dialog);
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.80);
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.80);
            cancel_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            cancel_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            Button btn_submit = cancel_dialog.findViewById(R.id.btn_submit);
            EditText remark_et = cancel_dialog.findViewById(R.id.remark_et);

            btn_submit.setOnClickListener(v -> cancel_dialog.dismiss());
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideKeyboard();
                    if (!remark_et.getText().toString().equalsIgnoreCase("")) {
                        getItemCancel(PreferencesManager.getInstance(context).getUSERID(), orderListItem.getOrderItemId(), remark_et.getText().toString());
                        cancel_dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Please enter Remark", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            cancel_dialog.show();

        } catch (Exception e) {
            hideLoading();
        }

    }

    private void getItemCancel(String userid, String orderItemId, String remark) {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("CustomerId", userid);
        object.addProperty("OrderItemId", orderItemId);
        object.addProperty("Remark", remark);
        LoggerUtil.logItem(object);
        Call<ResponseProductCancel> responseProductCancelCall = apiServices_shoopping.getProductCancel(object);
        responseProductCancelCall.enqueue(new Callback<ResponseProductCancel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProductCancel> call, @NonNull Response<ResponseProductCancel> response) {
                hideLoading();
                LoggerUtil.logItem(response.toString());
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    Intent targetIntent = new Intent(context, OrderDetailsActivity.class);
                    // Add your data to intent
                    targetIntent.putExtra("order_item", orderListItem);
                    context.startActivity(targetIntent);
                    (context).finish();

                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseProductCancel> call, @NonNull Throwable t) {

            }
        });


    }

    private void viewVisible() {
        if (orderListItem.getIsExchangeable().equalsIgnoreCase("true")) {
            btnExchange.setVisibility(View.VISIBLE);
        } else if (orderListItem.getIsRefundable().equalsIgnoreCase("true")) {
            btnReturn.setVisibility(View.VISIBLE);
        }

    }

    public void getTrackOrder(String orderItemId) {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("OrderItemId", orderItemId);
        LoggerUtil.logItem(object);
        Call<ResponseTrackOrder> responseTrackOrderCall = apiServices_shoopping.getOrderTracking(object);
        responseTrackOrderCall.enqueue(new Callback<ResponseTrackOrder>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTrackOrder> call, @NonNull Response<ResponseTrackOrder> response) {
                LoggerUtil.logItem(response.body());
                hideLoading();
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    stepView.setVisibility(View.VISIBLE);
                    trackOrder = response.body().getOrderTracking();
                    btnCancel.setVisibility(View.VISIBLE);

                    //show number of steps
                    if (trackOrder.getIsCancelled().equalsIgnoreCase("true")) {
                        //populate array
                        stepView.setStateDescriptionData(arrayCancelled);
                        btnCancel.setVisibility(View.GONE);
                        stepView.setMaxStateNumber(StateProgressBar.StateNumber.THREE);
                        //set views
                        if (trackOrder.getIsAccepted().equalsIgnoreCase("true")) {
                            stepView.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                        }
                        if (trackOrder.getIsPacked().equalsIgnoreCase("true")) {
                            stepView.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        }
                        if (trackOrder.getIsCancelled().equalsIgnoreCase("true")) {
                            stepView.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                        }
                        stepView.setOnStateItemClickListener((stateProgressBar, stateItem, stateNumber, isCurrentState) -> {
                            if (stateNumber == 1 && !trackOrder.getAcceptRemark().equalsIgnoreCase(""))
                                showInformation(trackOrder.getAcceptedDate() + "\n" + trackOrder.getAcceptRemark());
                            else if (stateNumber == 2 && !trackOrder.getPackingRemark().equalsIgnoreCase(""))
                                showInformation(trackOrder.getPackedDate() + "\n" + trackOrder.getPackingRemark());
                            else if (stateNumber == 3)
                                showInformation("Cancelled on : " + trackOrder.getCancelledDate() + "\n" +
                                        "Remark : " + trackOrder.getCancelRemark() + "\n" +
                                        "Cancelled by : " + trackOrder.getCancelledBy());
                        });
                    } else {
                        //populate array
                        stepView.setStateDescriptionData(arrayComplete);
                        stepView.setMaxStateNumber(StateProgressBar.StateNumber.FOUR);
                        //set views
                        if (trackOrder.getIsAccepted().equalsIgnoreCase("true"))
                            stepView.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                        if (trackOrder.getIsPacked().equalsIgnoreCase("true"))
                            stepView.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        if (trackOrder.getIsShipped().equalsIgnoreCase("true")) {
                            btnCancel.setVisibility(View.GONE);
                            stepView.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                        }
                        if (trackOrder.getIsDelivered().equalsIgnoreCase("true")) {
                            btnCancel.setVisibility(View.GONE);
                            stepView.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                        }

                        stepView.setOnStateItemClickListener((stateProgressBar, stateItem, stateNumber, isCurrentState) -> {
                            if (stateNumber == 1 && !trackOrder.getAcceptRemark().equalsIgnoreCase(""))
                                showInformation(trackOrder.getAcceptedDate() + "\n" + trackOrder.getAcceptRemark());
                            else if (stateNumber == 2 && !trackOrder.getPackingRemark().equalsIgnoreCase(""))
                                showInformation(trackOrder.getPackedDate() + "\n" + trackOrder.getPackingRemark());
                            else if (stateNumber == 3 && !trackOrder.getShippingRemark().equalsIgnoreCase(""))
                                showInformation(trackOrder.getShippedDate() + "\n" + trackOrder.getShippingRemark());
                            else if (stateNumber == 4 && !trackOrder.getDeliveryRemark().equalsIgnoreCase(""))
                                showInformation(trackOrder.getDeliveredDate() + "\n" + trackOrder.getDeliveryRemark());
                        });
                    }
                    stepView.setAnimationDuration(3000);
                    stepView.setAnimationStartDelay(2000);
                    orderStatus.setVisibility(View.GONE);
                } else {
                    orderStatus.setVisibility(View.VISIBLE);
                    orderStatus.setText("Cancelled");
                    stepView.setVisibility(View.GONE);
                    showAlert("Tracking is not available for this order.", R.color.red, R.drawable.error);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTrackOrder> call, @NonNull Throwable t) {
                hideLoading();
            }
        });


    }

    public void getProductExchange(String vendId, String fkProdutId, String ordreIemId, String orderId) {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("CustomerId", PreferencesManager.getInstance(context).getUSERID());
        object.addProperty("VendorId", vendId);
        object.addProperty("Fk_ProductDetailId", fkProdutId);
        object.addProperty("OrderItemId", ordreIemId);
        object.addProperty("OrderId", orderId);
        LoggerUtil.logItem(object);
        Call<ResponseProductExchange> call = apiServices_shoopping.getProductExchange(object);
        call.enqueue(new Callback<ResponseProductExchange>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProductExchange> call, @NonNull Response<ResponseProductExchange> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        viewExchangeDialog = response.body();
                        exchangeDialog(response.body());
                    } else {

                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseProductExchange> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void exchangeDialog(ResponseProductExchange viewExchangeDialog) {
        try {
            hideKeyboard();
            view_exchange_dialog = new Dialog(context);
            view_exchange_dialog.setCanceledOnTouchOutside(true);
            view_exchange_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            view_exchange_dialog.setContentView(R.layout.exchange_dialog);
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.80);
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.80);
            view_exchange_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            view_exchange_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            Button close = view_exchange_dialog.findViewById(R.id.btn_close);
            TextView returnID = view_exchange_dialog.findViewById(R.id.returnID);
            TextView message = view_exchange_dialog.findViewById(R.id.message);

            returnID.setText(String.format("Replacement ID   %s", viewExchangeDialog.getReplacementId()));
            message.setText(viewExchangeDialog.getMessage());
            close.setOnClickListener(v -> view_exchange_dialog.dismiss());

            view_exchange_dialog.show();

        } catch (Exception e) {
            hideLoading();
        }

    }


    public void getProductReturn(String vendrId, String fkProductId, String ordreItemId, String ordreId) {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("CustomerId", PreferencesManager.getInstance(context).getUSERID());
        object.addProperty("VendorId", vendrId);
        object.addProperty("Fk_ProductDetailId", fkProductId);
        object.addProperty("OrderItemId", ordreItemId);
        object.addProperty("OrderId", ordreId);
        LoggerUtil.logItem(object.toString());
        Call<ResponseProductReturn> productReturnCall = apiServices_shoopping.getProductReturn(object);
        productReturnCall.enqueue(new Callback<ResponseProductReturn>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProductReturn> call, @NonNull Response<ResponseProductReturn> response) {
                hideLoading();
                LoggerUtil.logItem(response.toString());
                try {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        viewReturnDialog = response.body();
                        returnDialog(response.body());

                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseProductReturn> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }


    private void returnDialog(ResponseProductReturn viewReturnDialog) {
        try {
            hideKeyboard();
            view_return_dialog = new Dialog(context);
            view_return_dialog.setCanceledOnTouchOutside(true);
            view_return_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            view_return_dialog.setContentView(R.layout.exchange_dialog);
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.80);
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.80);
            view_return_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            view_return_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            Button close = view_return_dialog.findViewById(R.id.btn_close);
            TextView returnID = view_return_dialog.findViewById(R.id.returnID);
            TextView message = view_return_dialog.findViewById(R.id.message);
            returnID.setText(String.format("Return ID   %s", viewReturnDialog.getReturnId()));
            message.setText(viewReturnDialog.getMessage());
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view_return_dialog.dismiss();
                }
            });

            view_return_dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showInformation(String msg_st) {
        Dialog error_dialog = new Dialog(context);
        error_dialog.setCanceledOnTouchOutside(true);
        error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        error_dialog.setContentView(R.layout.error_dialoge);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        error_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView error_text = error_dialog.findViewById(R.id.error_text);
        Button ok_btn = error_dialog.findViewById(R.id.ok_btn);
        Button book_now_btn = error_dialog.findViewById(R.id.book_now_btn);
        book_now_btn.setVisibility(View.GONE);
        error_text.setText(msg_st);
        error_dialog.show();
        ok_btn.setOnClickListener(view -> {
            error_dialog.dismiss();
        });
    }

}
