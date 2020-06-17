package com.digitalcashbag.utilities.billpayment.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digitalcashbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.utility.RecentActivityItem;
import kkm.com.core.retrofit.MvpView;

public class RecentBillPayment extends RecyclerView.Adapter<RecentBillPayment.ViewHolder> {
    private Context mContext;
    private List<RecentActivityItem> list;
    private MvpView mvpView;


    public RecentBillPayment(Activity context, List<RecentActivityItem> addinfo, MvpView mvp) {
        mContext = context;
        this.list = addinfo;
        mvpView = mvp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recent_bill, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        holder.orderNo.setText(String.format("Order No %s", list.get(listPosition).getTransactionId()));
        holder.amount.setText(String.format("₹ %s", list.get(listPosition).getAmount()));
        holder.date.setText(list.get(listPosition).getPaymentDate());
//        holder.rechargeNumber.setText("Recharge of " + list.get(listPosition).getOperator() + " Mobile " + list.get(listPosition).getMobileNo());
        holder.rechargeNumber.setText("Bill payment of" + " service no " + list.get(listPosition).getMobileNo());

        if (list.get(listPosition).getError().equalsIgnoreCase("0") && list.get(listPosition).getResult().equalsIgnoreCase("0")) {
            holder.status.setText("Your order is successful");
            holder.status.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.status.setText("Your order is failed");
            holder.status.setTextColor(mContext.getResources().getColor(R.color.red));
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_no)
        TextView orderNo;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.operator_name)
        ImageView operatorName;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.repeat)
        TextView repeat;
        @BindView(R.id.recharge_number)
        TextView rechargeNumber;
        @BindView(R.id.status_lo)
        RelativeLayout status_lo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            repeat.setOnClickListener(view1 -> mvpView.checkAvailability(list.get(getAdapterPosition()).getMobileNo(), list.get(getAdapterPosition()).getOperator(), list.get(getAdapterPosition()).getAmount(), ""));
        }
    }

}
