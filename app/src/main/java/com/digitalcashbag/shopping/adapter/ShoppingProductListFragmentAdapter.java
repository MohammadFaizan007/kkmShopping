package com.digitalcashbag.shopping.adapter;

import android.content.Context;
import android.graphics.Paint;
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
import kkm.com.core.model.response.ProductListItem;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;


public class ShoppingProductListFragmentAdapter extends RecyclerView.Adapter<ShoppingProductListFragmentAdapter.ViewHolder> {

    private MvpView mvp;
    private Context mContext;
    private List<ProductListItem> categoryArrayList;

    public ShoppingProductListFragmentAdapter(Context activity, List<ProductListItem> subCategoryList, MvpView mvpView) {
        mContext = activity;
        this.categoryArrayList = subCategoryList;
        this.mvp = mvpView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppingitem_adapter_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        LoggerUtil.logItem(categoryArrayList);
        holder.itemtitle.setText(categoryArrayList.get(listPosition).getProductName());
        holder.itempriceold.setText((String.format("₹%s", categoryArrayList.get(listPosition).getProductOldPrice())));
        holder.itempriceold.setPaintFlags(holder.itempriceold.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.itemprice.setText(String.format("₹%s", categoryArrayList.get(listPosition).getProductPrice()));

        Glide.with(mContext).load(categoryArrayList.get(listPosition).getProductImage())
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available).error(R.drawable.image_not_available))
                .into(holder.itemthumbnail);
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.itemthumbnail)
        ImageView itemthumbnail;
        @BindView(R.id.itemtitle)
        TextView itemtitle;
        @BindView(R.id.itemprice)
        TextView itemprice;
        @BindView(R.id.itempriceold)
        TextView itempriceold;
        @BindView(R.id.btn_add_to_cart)
        Button btn_add_to_cart;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mvp.getClickPosition(Integer.parseInt(categoryArrayList.get(getAdapterPosition()).getProductId()), categoryArrayList.get(getAdapterPosition()).getProductName());
        }
    }


}