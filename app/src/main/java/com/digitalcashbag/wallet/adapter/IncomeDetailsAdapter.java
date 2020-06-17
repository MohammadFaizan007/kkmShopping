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
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.incentive.ListLevelIncomeDetailsItem;

public class IncomeDetailsAdapter extends RecyclerView.Adapter<IncomeDetailsAdapter.ViewHolder> {

    private final Context context;


    private List<ListLevelIncomeDetailsItem> list;

    public IncomeDetailsAdapter(Context context, List<ListLevelIncomeDetailsItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.income_details_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textViewNumber.setText(String.format(Locale.ENGLISH, "%d", position + 1));
        holder.tvIncome.setText(list.get(position).getAmount());
        holder.textbusiness.setText(list.get(position).getBusinessAmount());
        holder.tvLevel.setText(list.get(position).getUtilityName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_level)
        TextView tvLevel;
        @BindView(R.id.tv_income)
        TextView tvIncome;
        @BindView(R.id.textbusiness)
        TextView textbusiness;
        @BindView(R.id.textViewNumber)
        TextView textViewNumber;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}