package com.digitalcashbag.shopping.adapter;

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
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.model.response.HomePageItem;
import kkm.com.core.retrofit.MvpView;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;
import static com.bumptech.glide.load.engine.DiskCacheStrategy.DATA;
import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;


public class DashboardGridShoppingOffersAdapter extends RecyclerView.Adapter<DashboardGridShoppingOffersAdapter.ViewHolder> {

    private MvpView mvp;
    private Context mContext;
    private List<HomePageItem> productList;

    public DashboardGridShoppingOffersAdapter(Context activity, List<HomePageItem> subCategoryList, MvpView mvpView) {
        mContext = activity;
        this.productList = subCategoryList;
        this.mvp = mvpView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.androidGridviewText.setText(String.format("%s", productList.get(position).getOfferName()));
        holder.aproductategory.setText(String.format("%s", productList.get(position).getBrandName()));
        holder.productOldPrice.setText((String.format("₹ %s", productList.get(position).getMrp())));
        holder.productOldPrice.setPaintFlags(holder.productOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productPrice.setText(String.format("₹ %s", productList.get(position).getOfferPrice()));

        holder.aproductategory.setSelected(true);
        holder.androidGridviewText.setSelected(true);
        Glide.with(mContext).load(productList.get(position).getProductImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.image_not_available)
                        .error(R.drawable.image_not_available))
                .into(holder.androidGridviewImage);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.android_gridview_image)
        ImageView androidGridviewImage;
        @BindView(R.id.android_gridview_text)
        TextView androidGridviewText;
        @BindView(R.id.aproductategory)
        TextView aproductategory;
        @BindView(R.id.product_price)
        TextView productPrice;
        @BindView(R.id.product_old_price)
        TextView productOldPrice;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PreferencesManager.getInstance(mContext).setCategoryName(productList.get(getAdapterPosition()).getOfferName());
            Activity activity = (Activity) mContext;
            Bundle bundle = new Bundle();
            bundle.putString("productId", productList.get(getAdapterPosition()).getProductId());
            Intent intent = new Intent(mContext, ShoppingDetailItemActivity.class);
            intent.putExtra(PAYLOAD_BUNDLE, bundle);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

}