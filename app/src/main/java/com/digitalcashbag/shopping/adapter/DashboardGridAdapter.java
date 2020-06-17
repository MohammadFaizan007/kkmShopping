package com.digitalcashbag.shopping.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.retrofit.MvpView;


public class DashboardGridAdapter extends RecyclerView.Adapter<DashboardGridAdapter.ViewHolder> {

    private final String[] gridViewString;
    private final String[] gridViewImageId;
    private Context mContext;
    private MvpView mvp;

    public DashboardGridAdapter(Context context, String[] gridViewString, String[] gridViewImageId, MvpView mvp) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewString = gridViewString;
        this.mvp = mvp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        holder.androidGridviewText.setText(gridViewString[listPosition]);
        Glide.with(mContext).load(gridViewImageId[listPosition]).
                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                        .error(R.drawable.wallet))
                .into(holder.androidGridviewImage);

    }

    @Override
    public int getItemCount() {
        return gridViewString.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.android_gridview_image)
        ImageView androidGridviewImage;
        @BindView(R.id.android_gridview_text)
        TextView androidGridviewText;
        @BindView(R.id.android_custom_gridview_layout)
        LinearLayout androidCustomGridviewLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            androidCustomGridviewLayout.setOnClickListener(v -> mvp.getClickPosition(getAdapterPosition(), gridViewString[getAdapterPosition()]));
        }
    }

}