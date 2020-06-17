package com.digitalcashbag.utilities.recharges.adapter;

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
import kkm.com.core.model.response.wallet.FundTransferLogItem;

public class FundLogAdapter extends RecyclerView.Adapter<FundLogAdapter.ViewHolder> {

    private Context mContext;
    private List<FundTransferLogItem> list;

    public FundLogAdapter(Context context, List<FundTransferLogItem> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fund_log_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        holder.txtAmount.setText(String.format("₹ %s", list.get(listPosition).getTransAmount()));
        holder.txtName.setText(list.get(listPosition).getBeneficiaryName());
        holder.txtTransId.setText(String.format("TransID: %s", list.get(listPosition).getTransNo()));
        holder.txtTransStatus.setText(list.get(listPosition).getTransStatus());
        holder.txtRemark.setText(String.format("Remark: %s", list.get(listPosition).getRemarks()));
        holder.txtDate.setText(list.get(listPosition).getTransdate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtAmount)
        TextView txtAmount;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtTransId)
        TextView txtTransId;
        @BindView(R.id.txtTransStatus)
        TextView txtTransStatus;
        @BindView(R.id.txtRemark)
        TextView txtRemark;
        @BindView(R.id.txtDate)
        TextView txtDate;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }


}
