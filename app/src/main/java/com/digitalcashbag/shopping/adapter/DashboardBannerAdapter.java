package com.digitalcashbag.shopping.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.digitalcashbag.shopping.activities.MainContainer;
import com.digitalcashbag.shopping.activities.ShoppingActivityMain;
import com.digitalcashbag.shopping.activities.WebViewActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kkm.com.core.model.response.shopping.ShoppingOffersItem;

public class DashboardBannerAdapter extends RecyclerView.Adapter<DashboardBannerAdapter.MzViewHolder> {

    private Context context;
    private List<ShoppingOffersItem> shoppingoffersitem;

    public DashboardBannerAdapter(Context context, List<ShoppingOffersItem> shoppingoffersitem) {
        this.context = context;
        this.shoppingoffersitem = shoppingoffersitem;
    }


    @NotNull
    @Override
    public DashboardBannerAdapter.MzViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MzViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_banner_item_image,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardBannerAdapter.MzViewHolder holder, final int position) {
        if (shoppingoffersitem == null || shoppingoffersitem.isEmpty())
            return;

        Glide.with(context).load(shoppingoffersitem.get(position).getImageURL())
                .apply(new RequestOptions().placeholder(R.drawable.image_not_available)
                        .error(R.drawable.image_not_available))
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if (shoppingoffersitem != null) {
            return shoppingoffersitem.size();
        }
        return 0;
    }


    class MzViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        MzViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageTimer);
            imageView.setOnClickListener(v -> {
                if (shoppingoffersitem.get(getAdapterPosition()).getOfferOn().equalsIgnoreCase("iiashopping")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("shopping", "Shopping");
                    ((MainContainer) context).goToActivity((Activity) context, ShoppingActivityMain.class, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", shoppingoffersitem.get(getAdapterPosition()).getOfferURL());
                    ((MainContainer) context).goToActivity((Activity) context, WebViewActivity.class, bundle);
                }

            });
        }
    }

}