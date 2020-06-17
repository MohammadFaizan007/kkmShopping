package com.digitalcashbag.utilities.recharges.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.utilities.recharges.activities.MoneyTransfer_TransferRupee;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class MoneyTransferAccountListAdapter extends RecyclerView.Adapter<MoneyTransferAccountListAdapter.MyViewHolder> {

    private ArrayList<String> nameSet;
    private ArrayList<String> upiSet;


    public MoneyTransferAccountListAdapter(ArrayList<String> name, ArrayList<String> upi) {
        this.nameSet = name;
        this.upiSet = upi;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_list_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int listPosition) {

        holder.tv_name.setText(nameSet.get(listPosition));
        holder.tv_number.setText(String.format("UPI: %s", upiSet.get(listPosition)));

    }

    @Override
    public int getItemCount() {
        return nameSet.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        TextView tv_name, tv_number;
        ImageView name_image;
        LinearLayout account_lo;

        MyViewHolder(View itemView) {
            super(itemView);
            this.tv_name = itemView.findViewById(R.id.tv_name);
            this.tv_number = itemView.findViewById(R.id.tv_number);
            this.name_image = itemView.findViewById(R.id.name_image);
            this.account_lo = itemView.findViewById(R.id.account_lo);
            this.context = itemView.getContext();

            account_lo.setOnClickListener(view -> {
                Activity activity = (Activity) context;
                Intent intent_moneytransfer = new Intent(context, MoneyTransfer_TransferRupee.class);
                Bundle param = new Bundle();
                param.putString("money_transfer", context.getResources().getString(R.string.money_tgransfer));
                param.putString("name", nameSet.get(getAdapterPosition()));
                param.putString("upi_id", upiSet.get(getAdapterPosition()));
                intent_moneytransfer.putExtra(PAYLOAD_BUNDLE, param);
                activity.startActivity(intent_moneytransfer);
                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            });

        }
    }
}