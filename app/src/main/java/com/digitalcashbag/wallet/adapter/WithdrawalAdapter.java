package com.digitalcashbag.wallet.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.wallet.GetPayoutRequestDetailsItem;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.Utils;

public class WithdrawalAdapter extends RecyclerView.Adapter<WithdrawalAdapter.ViewHolder> {
    private final Context context;
    private List<GetPayoutRequestDetailsItem> list;
    private MvpView mvpView;

    public WithdrawalAdapter(Activity context, List<GetPayoutRequestDetailsItem> list, MvpView mvpView) {
        this.context = context;
        this.list = list;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_withdrawal_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.date.setText(Utils.changeDateFormatDDMMYYYY(list.get(position).getRequestedDate()));
        holder.transAmount.setText(list.get(position).getRequestedAmount());

        if (list.get(position).getApprovalStatus().equalsIgnoreCase("P")) {
            holder.imgStatus.setBackgroundResource(R.drawable.pending_icon);
            holder.statusOn.setVisibility(View.GONE);
            holder.tvStatusBy.setText("Admin approval pending");
        } else if (list.get(position).getApprovalStatus().equalsIgnoreCase("C")) {
            holder.imgStatus.setBackgroundResource(R.drawable.cancelled_icon);
            holder.statusOn.setVisibility(View.VISIBLE);
            holder.statusOn.setText(String.format("Cancelled on %s", list.get(position).getApprovalDate()));
            holder.tvStatusBy.setText(Html.fromHtml("Cancelled By <font color=\"#F3B232\"><b>" + list.get(position).getApprovedBy() + "</b></font>"));
        } else if (list.get(position).getApprovalStatus().equalsIgnoreCase("A")) {
            holder.imgStatus.setBackgroundResource(R.drawable.approved_icon);
            holder.statusOn.setVisibility(View.VISIBLE);
            holder.statusOn.setText(String.format("Pain On: %s", list.get(position).getApprovalDate()));
            holder.tvStatusBy.setText(Html.fromHtml("Paid By " + "<font color=\"#F3B232\"><b>" + list.get(position).getPaymentMode() + "</b></font>" + " <i>" + list.get(position).getRequestNo() + "</i>"));
        }

        if (list.get(position).getApprovalStatus().equalsIgnoreCase("P"))
            holder.tvCancelReq.setVisibility(View.VISIBLE);
        else
            holder.tvCancelReq.setVisibility(View.GONE);

//        holder.tvCancelReq.setOnClickListener(v -> {
//            LoggerUtil.logItem(list.get(position).getPKRequestId());
//            mvpView.getPayoutWithdrawalId(list.get(position).getPKRequestId());
//        });
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_status)
        ImageView imgStatus;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.tv_status_by)
        TextView tvStatusBy;
        @BindView(R.id.trans_amount)
        TextView transAmount;
        @BindView(R.id.status_on)
        TextView statusOn;
        @BindView(R.id.tv_cancel_req)
        TextView tvCancelReq;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            tvCancelReq.setOnClickListener(v -> {
                LoggerUtil.logItem(list.get(getAdapterPosition()).getPKRequestId());
                mvpView.getPayoutWithdrawalId(list.get(getAdapterPosition()).getPKRequestId());
            });
        }
    }

}
