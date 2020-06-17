package com.digitalcashbag.m2p.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalcashbag.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.m2p.transaction.ResultItem;

public class M2PCardAdapter extends RecyclerView.Adapter<M2PCardAdapter.ViewHolder>
//        implements Filterable
{

    private final Context context;


    //    private UserFilter userFilter;
    private List<ResultItem> list;

    public M2PCardAdapter(Context context, List<ResultItem> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(List<ResultItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_wallet_trans, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.transDate.setText(getDateCurrentTimeZone(list.get(position).getTransaction().getTime()));

        if (list.get(position).getTransaction().getExternalTransactionId().equalsIgnoreCase("") ||
                list.get(position).getTransaction().getExternalTransactionId().equalsIgnoreCase("NA")) {
            holder.transactionId.setVisibility(View.GONE);
            holder.transactionId.setText("");
        } else {
            holder.transactionId.setVisibility(View.VISIBLE);
            holder.transactionId.setText(String.format(list.get(position).getTransaction().getExternalTransactionId()));
        }


        if (list.get(position).getTransaction().getDescription().equalsIgnoreCase("") ||
                list.get(position).getTransaction().getDescription().equalsIgnoreCase("NA")) {
            holder.remark.setVisibility(View.INVISIBLE);
            holder.remark.setText("");
        } else {
            holder.remark.setVisibility(View.VISIBLE);
            holder.remark.setText(String.format(list.get(position).getTransaction().getDescription().trim()));
        }

        holder.mobile.setText(list.get(position).getTransaction().getTransactionType());
        holder.operatorName.setText(String.format(" (%s)", list.get(position).getTransaction().getTxnOrigin()));

        if (list.get(position).getTransaction().getType().equalsIgnoreCase("DEBIT")) {
            holder.amount.setText(String.format("- ₹ %s", list.get(position).getTransaction().getAmount()));
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else if (list.get(position).getTransaction().getType().equalsIgnoreCase("CREDIT")) {
            holder.amount.setText(String.format("+ ₹ %s", list.get(position).getTransaction().getAmount()));
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.green));
        }

        holder.button_cancel.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.trans_narration)
        TextView transNarration;
        @BindView(R.id.transaction_id)
        TextView transactionId;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.trans_date)
        TextView transDate;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.mobile)
        TextView mobile;
        @BindView(R.id.operator_name)
        TextView operatorName;
        @BindView(R.id.remark)
        TextView remark;
        @BindView(R.id.button_cancel)
        TextView button_cancel;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            button_cancel.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
        }
    }

    private String getDateCurrentTimeZone(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
