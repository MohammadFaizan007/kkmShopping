package com.digitalcashbag.utilities.tatasky.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalcashbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.tatasky.productlist.AddinfoItem;
import kkm.com.core.retrofit.MvpView;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private Context mContext;
    private List<AddinfoItem> list;
    private MvpView mvpView;

    public ProductListAdapter(Context context, List<AddinfoItem> list, MvpView mvp) {
        mContext = context;
        this.list = list;
        mvpView = mvp;
    }

    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_tatasky_product_des, parent, false);
        return new ProductListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductListAdapter.ViewHolder holder, int listPosition) {

        holder.tvName.setText(list.get(listPosition).getName());
        holder.tv_duration.setText(list.get(listPosition).getPackduration());
        holder.tvAmount.setText(String.format("₹ %s", list.get(listPosition).getPrice()));
        holder.tvDescripstion.setText(list.get(listPosition).getDescription());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_amount)
        TextView tvAmount;
        @BindView(R.id.tv_duration)
        TextView tv_duration;
        @BindView(R.id.tv_descripstion)
        TextView tvDescripstion;
        @BindView(R.id.product_lo)
        ConstraintLayout product_lo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            product_lo.setOnClickListener(v -> mvpView.getClickPosition(Integer.parseInt(list.get(getAdapterPosition()).getPrice()), list.get(getAdapterPosition()).getProductId()));
        }
    }
}