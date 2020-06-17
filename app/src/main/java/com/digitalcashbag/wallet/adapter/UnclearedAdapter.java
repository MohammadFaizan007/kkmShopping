package com.digitalcashbag.wallet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalcashbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.unclearLedger.PayoutLedgerSelectDetailsListItem;

public class UnclearedAdapter extends RecyclerView.Adapter<UnclearedAdapter.ViewHolder> {

    private final Context context;


    private List<PayoutLedgerSelectDetailsListItem> list;

    public UnclearedAdapter(Context context, List<PayoutLedgerSelectDetailsListItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_uncleared, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.transNarration.setText(list.get(position).getRemark());
        holder.amount.setText(list.get(position).getBusinessAmount());
        holder.comissionPercentage.setText("Comission(%) - " + list.get(position).getCommissionPercentage() + "%");
        holder.transDate.setText(list.get(position).getCurrentDate());
        holder.comissionAmt.setText("Commission Amt. " + list.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private void marquieeView(TextView tv, String data) {
        tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        if (data.toLowerCase().contains("null")) {
            tv.setText(" - - ");
        } else
            tv.setText(data);
        tv.setSelected(true);
        tv.setSingleLine(true);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.trans_narration)
        TextView transNarration;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.comission_percentage)
        TextView comissionPercentage;
        @BindView(R.id.comission_amt)
        TextView comissionAmt;
        @BindView(R.id.trans_date)
        TextView transDate;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}