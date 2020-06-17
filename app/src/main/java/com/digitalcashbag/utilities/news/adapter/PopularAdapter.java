package com.digitalcashbag.utilities.news.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.retrofit.MvpView;


public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    private final String[] newsString;
    MvpView mvp;
    private Context mContext;

    public PopularAdapter(Context context, String[] newsString, MvpView mvp) {
        mContext = context;
        this.newsString = newsString;
        this.mvp = mvp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        holder.tvNewsHeading.setText(newsString[listPosition]);
    }

    @Override
    public int getItemCount() {
        return newsString.length;
    }

    @OnClick(R.id.main_lo)
    public void onViewClicked() {
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView3)
        ImageView imageView3;
        @BindView(R.id.tv_news_heading)
        TextView tvNewsHeading;
        @BindView(R.id.tv_read_count)
        TextView tvReadCount;
        @BindView(R.id.tv_liked_count)
        TextView tvLikedCount;
        @BindView(R.id.main_lo)
        ConstraintLayout mainLo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mainLo.setOnClickListener(v -> mvp.getClickPosition(getAdapterPosition(), "menu"));
        }
    }

}