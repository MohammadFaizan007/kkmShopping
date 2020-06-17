package com.digitalcashbag.shopping.adapter;

import android.content.Context;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.shopping.SubCategoryItem;
import kkm.com.core.retrofit.MvpView;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;


public class ShoppingSubCategoryFragmentAdapter extends RecyclerView.Adapter<ShoppingSubCategoryFragmentAdapter.ViewHolder> {

    private MvpView mvp;
    private Context mContext;
    private List<SubCategoryItem> categoryArrayList;

    public ShoppingSubCategoryFragmentAdapter(Context activity, List<SubCategoryItem> subCategoryList, MvpView mvpView) {
        mContext = activity;
        this.categoryArrayList = subCategoryList;
        this.mvp = mvpView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout_shopping_offers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        holder.androidGridviewText.setText(categoryArrayList.get(listPosition).getCategoryName());
        Glide.with(mContext).load(categoryArrayList.get(listPosition).getImages())
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available).error(R.drawable.image_not_available))
                .into(holder.androidGridviewImage);

    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.android_gridview_image)
        ImageView androidGridviewImage;
        @BindView(R.id.android_gridview_text)
        TextView androidGridviewText;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mvp.getClickPosition(Integer.parseInt(categoryArrayList.get(getAdapterPosition()).getPKCategoryId()), categoryArrayList.get(getAdapterPosition()).getCategoryName());
        }
    }


}