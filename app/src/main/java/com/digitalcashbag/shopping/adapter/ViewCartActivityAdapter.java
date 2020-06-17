package com.digitalcashbag.shopping.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.digitalcashbag.shopping.activities.ShoppingDetailItemActivity;
import com.digitalcashbag.shopping.activities.ViewCartActivity;
import com.google.gson.JsonObject;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.model.request.RequestAddCart;
import kkm.com.core.model.response.cart.CartItemListItem;
import kkm.com.core.utils.LoggerUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;
import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class ViewCartActivityAdapter extends RecyclerView.Adapter<ViewCartActivityAdapter.MyViewHolder> {
    private Context context;
    private List<CartItemListItem> productList;

    public ViewCartActivityAdapter(Context mContext, List<CartItemListItem> productList) {
        this.context = mContext;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_adapter_item_new, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvProductName.setText(productList.get(position).getProductName());
        if (!productList.get(position).getProductAvailability().equalsIgnoreCase("")) {
//            holder.qtyBox.setMax(Integer.parseInt(productList.get(position).getProductAvailability()));
        }
        holder.qtyBox.setValue(Integer.parseInt(productList.get(position).getProductQuantity()));

        if (holder.qtyBox.getMax() <= 0) {
            holder.tvProductStatus.setText(R.string.out_of_stock);
            holder.tvProductStatus.setTextColor(ContextCompat.getColor(context, R.color.color_red));
        } else {
            holder.tvProductStatus.setText(R.string.in_stock);
            holder.tvProductStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
        }

        Glide.with(context).load(productList.get(position).getProductImage()).apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available).error(R.drawable.image_not_available)).into(holder.imgProduct);

        holder.tvProductCurrentPrice.setText(String.format("₹ %s", holder.qtyBox.getValue() * Float.parseFloat(productList.get(position).getProductPrice())));
        holder.tvProductOriginalPrice.setText(String.format("₹ %s", holder.qtyBox.getValue() * Float.parseFloat(productList.get(position).getProductOldPrice())));
        holder.tvProductOriginalPrice.setPaintFlags(holder.tvProductOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


//        holder.tvProductSize.setText(productList.get(position).getSizeName());


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private void quantityIncrease(int pos) {
//        CartItemInsert
        try {
            LoggerUtil.logItem(" UNIT POS= " + pos);

            ((ViewCartActivity) context).showLoading();
            RequestAddCart cart = new RequestAddCart();
            cart.setAddedBy(PreferencesManager.getInstance(context).getUSERID());
            cart.setProductAmt(productList.get(pos).getProductPrice());
            cart.setProductId(productList.get(pos).getProductId());
            cart.setProductQty(String.valueOf(1));
            cart.setSizeID(productList.get(pos).getSizeID());
            cart.setPkProductDetailId(productList.get(pos).getFkProductDetailId());
            cart.setSrNo("0");

            LoggerUtil.logItem(cart);

            Call<JsonObject> cartItemCall = ((ViewCartActivity) context).apiServices_shoopping.addCartItem(cart);
            cartItemCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    ((ViewCartActivity) context).hideLoading();
                    LoggerUtil.logItem(response.body());
                    ((ViewCartActivity) context).getCartItems();
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    ((ViewCartActivity) context).hideLoading();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void quantityDecrease(int pos, int qty) {
        try {
            ((ViewCartActivity) context).showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("ProductId", productList.get(pos).getFkProductDetailId());
            param.addProperty("ProductQty", String.valueOf(qty));
            param.addProperty("UserId", PreferencesManager.getInstance(context).getUSERID());
            Log.e("CartItemDelete", param.toString());
            Call<JsonObject> objectCall = ((ViewCartActivity) context).apiServices_shoopping.addCartItemDelete(param);
            objectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    LoggerUtil.logItem(response.body());
                    ((ViewCartActivity) context).hideLoading();
                    ((ViewCartActivity) context).getCartItems();
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    ((ViewCartActivity) context).hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_product)
        ImageView imgProduct;
        @BindView(R.id.tv_remove)
        TextView tvRemove;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_product_status)
        TextView tvProductStatus;
        //        @BindView(R.id.tv_product_size)
//        TextView tvProductSize;
        @BindView(R.id.tv_product_current_price)
        TextView tvProductCurrentPrice;
        @BindView(R.id.tv_product_original_price)
        TextView tvProductOriginalPrice;
        @BindView(R.id.qtyBox)
        NumberPicker qtyBox;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            tvRemove.setOnClickListener(this);
            imgProduct.setOnClickListener(this);

            qtyBox.setValueChangedListener((value, action) -> {
                if (action == ActionEnum.INCREMENT) {
                    quantityIncrease(getAdapterPosition());
                } else if (action == ActionEnum.DECREMENT) {
                    quantityDecrease(getAdapterPosition(), 1);
                }
            });
        }


        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.tv_remove:
                    quantityDecrease(getAdapterPosition(), qtyBox.getValue());
                    break;
                case R.id.img_product:
                    Activity activity = (Activity) context;
                    Bundle bundle = new Bundle();
                    bundle.putString("productId", productList.get(getAdapterPosition()).getProductId());
                    Intent intent = new Intent(context, ShoppingDetailItemActivity.class);
                    intent.putExtra(PAYLOAD_BUNDLE, bundle);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    activity.finish();
                    break;
            }
        }
    }

}

