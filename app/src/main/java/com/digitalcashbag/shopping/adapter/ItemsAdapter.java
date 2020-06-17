package com.digitalcashbag.shopping.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.digitalcashbag.shopping.activities.ShoppingDetailItemActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.ProductListItem;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;
import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {
    private Context context;
    private List<ProductListItem> productList;

    public ItemsAdapter(Context mContext, List<ProductListItem> productList) {
        this.context = mContext;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppingitem_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.itemtitle.setText(productList.get(position).getProductName());
        holder.itempriceoriginal.setText((String.format("₹ %s", productList.get(position).getProductOldPrice())));
        holder.itempriceoriginal.setPaintFlags(holder.itempriceoriginal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.itemprice.setText(String.format("₹ %s", productList.get(position).getProductPrice()));

        Glide.with(context).load(productList.get(position).getProductImage())
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available).error(R.drawable.image_not_available))
                .into(holder.itemthumbnail);
//        if (Integer.parseInt( Cons.productSizeItems.get(position).getProductQty()) <= 0) {
//            holder.itemAvailablity.setText((R.string.out_of_stock));
//            holder.itemAvailablity.setTextColor( ContextCompat.getColor(context, R.color.red));
//        } else {
//            holder.itemAvailablity.setText((R.string.in_stock));
//            holder.itemAvailablity.setTextColor(ContextCompat.getColor(context, R.color.green));
//        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.itemthumbnail)
        ImageView itemthumbnail;
        @BindView(R.id.itemtitle)
        TextView itemtitle;
        @BindView(R.id.itemdes)
        TextView itemdes;
        @BindView(R.id.itemAvailablity)
        TextView itemAvailablity;
        @BindView(R.id.itemprice)
        TextView itemprice;
        @BindView(R.id.itempriceoriginal)
        TextView itempriceoriginal;
        @BindView(R.id.img_add_cart_item)
        ImageView imgAddCartItem;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemthumbnail.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.itemthumbnail:
                    Activity activity = (Activity) context;
                    Bundle bundle = new Bundle();
                    bundle.putString("productId", productList.get(getAdapterPosition()).getProductId());
                    Intent intent = new Intent(context, ShoppingDetailItemActivity.class);
                    intent.putExtra(PAYLOAD_BUNDLE, bundle);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//                    ShoppingDetailItem fragment = new ShoppingDetailItem();
//                    fragment.setArguments( bundle );
//                    ((ShoppingActivity) context).ReplaceFragmentAddBack( fragment, "" );
                    break;
            }
        }
    }

}