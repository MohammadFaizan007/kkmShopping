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
import com.digitalcashbag.shopping.activities.ShoppingActivityMain;
import com.digitalcashbag.shopping.activities.ShoppingSiteActivity;
import com.digitalcashbag.shopping.activities.WebViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.shopping.ShoppingOffersItem;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;


public class ShoppingCategoryActivityAdapter extends RecyclerView.Adapter<ShoppingCategoryActivityAdapter.ViewHolder> {

    List<ShoppingOffersItem> shoppingoffersitem;
    private Context mContext;

    public ShoppingCategoryActivityAdapter(Context activity, List<ShoppingOffersItem> shoppingoffersitem) {
        mContext = activity;
        this.shoppingoffersitem = shoppingoffersitem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_site_lay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        Glide.with(mContext).load(shoppingoffersitem.get(listPosition).getSmallimages())
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available).error(R.drawable.image_not_available))
                .into(holder.imgShopping);

    }

    @Override
    public int getItemCount() {
        return shoppingoffersitem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_shopping)
        ImageView imgShopping;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (shoppingoffersitem.get(getAdapterPosition()).getOfferOn().equalsIgnoreCase("iiashopping")) {
                Bundle bundle = new Bundle();
                bundle.putString("shopping", "Shopping");
                ((ShoppingSiteActivity) mContext).goToActivity((Activity) mContext, ShoppingActivityMain.class, bundle);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("link", shoppingoffersitem.get(getAdapterPosition()).getOfferURL());
                ((ShoppingSiteActivity) mContext).goToActivity((Activity) mContext, WebViewActivity.class, bundle);
            }
        }
    }


}