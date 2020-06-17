package com.digitalcashbag.wallet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.digitalcashbag.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.m2p.transaction.ResultItem;
import kkm.com.core.retrofit.MvpView;

public class M2PCardAdapter extends RecyclerView.Adapter<M2PCardAdapter.ViewHolder>
//        implements Filterable
{

    private final Context context;


    //    private UserFilter userFilter;
    private List<ResultItem> list;
    private MvpView mvpView;

    public M2PCardAdapter(Context context, List<ResultItem> list, MvpView mvpView) {
        this.context = context;
        this.list = list;
        this.mvpView = mvpView;
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
//        if (list.get(position).getActionType().equalsIgnoreCase("UPICollect")) {
//            holder.transNarration.setText("Money added to bag.");
//        } else if (list.get(position).getActionType().equalsIgnoreCase("Fund Transfer")) {
//            holder.transNarration.setText(String.format("%s to %s", list.get(position).getActionType(), list.get(position).getOperator()));
//        } else if (list.get(position).getActionType().equalsIgnoreCase("Offline Payment") &&
//                list.get(position).getTransactionStatus().equalsIgnoreCase("3")) {
//            holder.button_cancel.setVisibility(View.VISIBLE);
//        } else holder.transNarration.setText(list.get(position).getActionType());
//
//        if (list.get(position).getTransactionStatus().equalsIgnoreCase("0") ||
//                list.get(position).getTransactionStatus().equalsIgnoreCase("Failed")) {
//            holder.tvStatus.setText("Failed");
//            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.color_red));
//        } else if (list.get(position).getTransactionStatus().equalsIgnoreCase("Cancel")) {
//            holder.tvStatus.setText("Cancelled");
//            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.color_red));
//        } else if (list.get(position).getTransactionStatus().equalsIgnoreCase("3")) {
//            holder.tvStatus.setText("Pending");
//            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.orange));
//        } else if (list.get(position).getTransactionStatus().equalsIgnoreCase("2") ||
//                list.get(position).getTransactionStatus().equalsIgnoreCase("Declined")) {
//            holder.tvStatus.setText("Declined");
//            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.color_red));
//        } else if (list.get(position).getTransactionStatus().equalsIgnoreCase("7") ||
//                list.get(position).getTransactionStatus().equalsIgnoreCase("Success")) {
//            holder.tvStatus.setText("Success");
//            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
//        } else {
//            holder.tvStatus.setText(list.get(position).getTransactionStatus());
//            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.text_color));
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    @Override
//    public Filter getFilter() {
//        if (userFilter == null)
//            userFilter = new UserFilter(this, list);
//        return userFilter;
//    }


//    private static class UserFilter extends Filter {
//        M2PCardAdapter adapter;
//        List<ResultItem> myDirectResponses;
//        List<ResultItem> filteredList;
//
//        private UserFilter(M2PCardAdapter adapter, List<ResultItem> originalList) {
//            super();
//            this.adapter = adapter;
//            this.myDirectResponses = new LinkedList<>(originalList);
//            this.filteredList = new ArrayList<>();
//        }
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            filteredList.clear();
//            final FilterResults results = new FilterResults();
//            if (constraint.length() == 0) {
//                filteredList.addAll(myDirectResponses);
//            } else {
//                final String filterPattern = constraint.toString().trim();
//                for (final ResultItem user : myDirectResponses) {
//                    if (Pattern.compile(Pattern.quote(filterPattern), Pattern.CASE_INSENSITIVE).matcher(user.getPaymentDate()).find() ||
//                            Pattern.compile(Pattern.quote(filterPattern), Pattern.CASE_INSENSITIVE).matcher(user.getCrAmount()).find() ||
//                            Pattern.compile(Pattern.quote(filterPattern), Pattern.CASE_INSENSITIVE).matcher(user.getDrAmount()).find()) {
//                        filteredList.add(user);
//                    }
//                }
//            }
//            results.values = filteredList;
//            results.count = filteredList.size();
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            adapter.list.clear();
//            adapter.list.addAll((ArrayList<ResultItem>) results.values);
//            adapter.notifyDataSetChanged();
//        }
//    }

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
//            mvpView.getClickPosition(getAdapterPosition(), list.get(getAdapterPosition()).getTransactionId());
        }
    }

    public String getDateCurrentTimeZone(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
        }
        return "";
    }

}
