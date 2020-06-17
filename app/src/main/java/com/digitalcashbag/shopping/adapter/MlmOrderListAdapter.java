package com.digitalcashbag.shopping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.OrderListItem;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class MlmOrderListAdapter extends RecyclerView.Adapter<MlmOrderListAdapter.MyViewHolder> {
    private Context context;
    private List<OrderListItem> productList;


    public MlmOrderListAdapter(Context mContext, List<OrderListItem> productList) {
        this.context = mContext;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

//        PreferencesManager.getInstance( context ).setVendorId( productList.get( position ).getVendorId() );

        holder.tvProductName.setText(productList.get(position).getParoductName());
        holder.tvProductStatus.setText(context.getResources().getString(R.string.order_status) + " " + productList.get(position).getOredrStatus());
        holder.tvOredrId.setText(context.getResources().getString(R.string.order_id) + " " + productList.get(position).getOrderNumber());
        holder.tvProductPrice.setText(context.getResources().getString(R.string.price) + productList.get(position).getPrice());
        holder.tvPproductShiping.setText(context.getResources().getString(R.string.shipping) + productList.get(position).getShippingPrice());
        holder.tvProductQuantity.setText(productList.get(position).getProductQuantity());
        holder.tvProductTotalPrice.setText(context.getResources().getString(R.string.total) + productList.get(position).getTotalPrice());

        Glide.with(context).load(productList.get(position).getProductImage()).apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available).error(R.drawable.image_not_available)).into(holder.imgProduct);

        if (productList.get(position).getIsExchangeable().equalsIgnoreCase("False") || (productList.get(position).getIsRefundable().equalsIgnoreCase("False"))) {
            holder.btnRefundProduct.setVisibility(View.VISIBLE);
            holder.btnExchangeProduct.setVisibility(View.VISIBLE);

        } else {
            holder.btnRefundProduct.setVisibility(View.GONE);
            holder.btnExchangeProduct.setVisibility(View.GONE);
        }

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
        @BindView(R.id.tv_product_status)
        TextView tvProductStatus;
        @BindView(R.id.tv_oredrId)
        TextView tvOredrId;
        @BindView(R.id.tv_product_price)
        TextView tvProductPrice;
        @BindView(R.id.tv_product_total_price)
        TextView tvProductTotalPrice;
        @BindView(R.id.tv_product_shiping)
        TextView tvPproductShiping;
        @BindView(R.id.tv_product_quantity)
        TextView tvProductQuantity;
        @BindView(R.id.btn_refund_product)
        Button btnRefundProduct;
        @BindView(R.id.btn_exchange_product)
        Button btnExchangeProduct;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}

