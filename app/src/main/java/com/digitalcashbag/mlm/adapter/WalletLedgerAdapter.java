package com.digitalcashbag.mlm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalcashbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.wallet.EWalletLedgerlistItem;

public class WalletLedgerAdapter extends RecyclerView.Adapter<WalletLedgerAdapter.ViewHolder> {

    List<EWalletLedgerlistItem> list;
    private Context mContext;


    public WalletLedgerAdapter(Context context, List<EWalletLedgerlistItem> list) {
        mContext = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_ledger_item_lay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        holder.txtAmount.setText(list.get(listPosition).getDrAmount());
//        holder.txtDrCr.setText(list.get(listPosition).getDrAmount());
        holder.txtTransDate.setText(list.get(listPosition).getTransDate());
        holder.txtBalance.setText(list.get(listPosition).getBalance());
        holder.txtRemark.setText(list.get(listPosition).getNarration());

        if (Float.parseFloat(list.get(listPosition).getDrAmount()) > 0) {
            holder.txtDrCr.setText("DR");
            holder.txtDrCr.setBackgroundResource(R.drawable.dr_bg);
            holder.txtAmount.setText(String.format("₹%s", list.get(listPosition).getDrAmount()));
            holder.txtAmount.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        } else {
            holder.txtDrCr.setText("CR");
            holder.txtDrCr.setBackgroundResource(R.drawable.cr_bg);
            holder.txtAmount.setText(String.format("₹%s", list.get(listPosition).getCrAmount()));
            holder.txtAmount.setTextColor(ContextCompat.getColor(mContext, R.color.success));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDrCr)
        TextView txtDrCr;
        @BindView(R.id.txtAmount)
        TextView txtAmount;
        @BindView(R.id.txtTransDate)
        TextView txtTransDate;
        @BindView(R.id.txtBalance)
        TextView txtBalance;
        @BindView(R.id.txtRemark)
        TextView txtRemark;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }


}