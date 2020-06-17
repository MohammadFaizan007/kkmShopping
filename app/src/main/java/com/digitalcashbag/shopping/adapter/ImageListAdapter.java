package com.digitalcashbag.shopping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.ProductImageItem;
import kkm.com.core.retrofit.MvpView;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder> {
    private Context context;
    private List<ProductImageItem> productImageItems;
    private MvpView mvpView;
    private int pos = 0;

    public ImageListAdapter(Context mContext, List<ProductImageItem> productImageItems, MvpView mvpView) {
        this.context = mContext;
        this.productImageItems = productImageItems;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(productImageItems.get(position).getImages())
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available)
                        .error(R.drawable.image_not_available))
                .into(holder.itemthumbnail);

        if (pos == position) {
            holder.itemthumbnail.setBackgroundResource(R.drawable.size_border);
        } else {
            holder.itemthumbnail.setBackgroundResource(0);
        }

    }

    @Override
    public int getItemCount() {
        return productImageItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.itemthumbnail)
        ImageView itemthumbnail;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mvpView.getClickPosition(getAdapterPosition(), "image");
            pos = getAdapterPosition();
            notifyDataSetChanged();
        }
    }
}

