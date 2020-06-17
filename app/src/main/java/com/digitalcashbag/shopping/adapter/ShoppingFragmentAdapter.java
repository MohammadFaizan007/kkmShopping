package com.digitalcashbag.shopping.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.shopping.CategoryGroupWiseItem;
import kkm.com.core.retrofit.MvpView;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;


public class ShoppingFragmentAdapter extends RecyclerView.Adapter<ShoppingFragmentAdapter.ViewHolder> {

    boolean aBoolean;
    private MvpView mvp;
    private Context mContext;
    private List<CategoryGroupWiseItem> categoryArrayList;

    public ShoppingFragmentAdapter(Context activity, List<CategoryGroupWiseItem> subCategoryList, MvpView mvpView, boolean bool) {
        mContext = activity;
        this.categoryArrayList = subCategoryList;
        this.mvp = mvpView;
        aBoolean = bool;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout_shopping_offers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        Log.e("Here", "========");
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
            if (aBoolean)
                mvp.getClickPosition(Integer.parseInt(categoryArrayList.get(getAdapterPosition()).getPKCategoryId()), categoryArrayList.get(getAdapterPosition()).getCategoryName());
            else
                mvp.getClickPosition(getAdapterPosition(), "Shopping offer");
        }
    }


}