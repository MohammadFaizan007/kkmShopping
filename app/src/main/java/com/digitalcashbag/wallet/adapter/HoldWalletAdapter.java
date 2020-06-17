package com.digitalcashbag.wallet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalcashbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.wallet.HoldwalletListItem;
import kkm.com.core.retrofit.MvpView;

public class HoldWalletAdapter extends RecyclerView.Adapter<HoldWalletAdapter.ViewHolder> {

    private final Context context;
    private List<HoldwalletListItem> list;
    private MvpView mvpView;

    public HoldWalletAdapter(Context context, List<HoldwalletListItem> list, MvpView mvpView) {
        this.context = context;
        this.list = list;
        this.mvpView = mvpView;
    }

    public void updateList(List<HoldwalletListItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_hold_bag, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvLoginId.setText(list.get(position).getLoginId());
        holder.tvRefAmt.setText(list.get(position).getAmount());
        holder.tvName.setText(list.get(position).getName());
        holder.tvDate.setText(list.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_loginId)
        TextView tvLoginId;
        @BindView(R.id.tv_ref_amt)
        TextView tvRefAmt;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View v) {
//            mvpView.getClickPosition(getAdapterPosition(), list.get(getAdapterPosition()).getTransactionId());
        }
    }

}
