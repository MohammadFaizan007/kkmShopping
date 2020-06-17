package com.digitalcashbag.shopping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.shopping.activities.OrderDetailsActivity;
import com.digitalcashbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.OrderListItem;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class ShoppingOrderAdapter extends RecyclerView.Adapter<ShoppingOrderAdapter.MyViewHolder> {
    private Context context;
    private List<OrderListItem> productList;

    public ShoppingOrderAdapter(Context mContext, List<OrderListItem> productList) {
        this.context = mContext;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvProductName.setText(productList.get(position).getParoductName());
        holder.tvProductStatus.setText(String.format("%s %s", context.getResources().getString(R.string.order_status), productList.get(position).getOredrStatus()));

        if (productList.get(position).getOredrStatus().equalsIgnoreCase("Cancelled")) {
            holder.tvProductStatus.setTextColor(ContextCompat.getColor(context, R.color.color_red));
        } else {
            holder.tvProductStatus.setTextColor(ContextCompat.getColor(context, R.color.text_color_light));
        }

        holder.tvOredrId.setText(String.format("%s %s", context.getResources().getString(R.string.order_id), productList.get(position).getOrderNumber()));
//        holder.tvOredrId.setText(String.format("%s", productList.get(position).getOrderNumber()));
        Glide.with(context).load(productList.get(position).getProductImage()).apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available).error(R.drawable.image_not_available)).into(holder.imgProduct);
        holder.tvProductTotalPrice.setText(String.format("%s%s", context.getResources().getString(R.string.total), productList.get(position).getTotalPrice()));
        holder.tv_date.setText(productList.get(position).getOrderDate());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_product)
        ImageView imgProduct;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_oredrId)
        TextView tvOredrId;
        @BindView(R.id.tv_product_status)
        TextView tvProductStatus;
        @BindView(R.id.tv_product_total_price)
        TextView tvProductTotalPrice;

        @BindView(R.id.tv_date)
        TextView tv_date;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> {
                OrderListItem orderListItem = productList.get(getAdapterPosition());
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("order_item", orderListItem);
                intent.putExtras(b);
                context.startActivity(intent);
            });
        }
    }
}