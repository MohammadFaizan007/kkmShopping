package com.digitalcashbag.utilities.recharges.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.utilities.recharges.activities.MoneyTransfer_TransferRupee;

import java.util.ArrayList;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class MoneyTransferContactListAdapter extends RecyclerView.Adapter<MoneyTransferContactListAdapter.MyViewHolder> {

    Bundle param = new Bundle();
    private ArrayList<String> nameSet;
    private ArrayList<String> numberSet;

    public MoneyTransferContactListAdapter(ArrayList<String> name, ArrayList<String> number) {
        this.nameSet = name;
        this.numberSet = number;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int listPosition) {

        TextView tv_name = holder.tv_name;
        TextView tv_number = holder.tv_number;
        TextView name_image = holder.name_image;
        LinearLayout contactlist_lo = holder.contactlist_lo;
        Context context = holder.context;

        tv_name.setText(nameSet.get(listPosition));
        tv_number.setText(numberSet.get(listPosition));
        name_image.setText(nameSet.get(listPosition).substring(0, 1).toUpperCase());

        contactlist_lo.setOnClickListener(view -> {
            Activity activity = (Activity) context;
            Intent intent_moneytransfer = new Intent(context, MoneyTransfer_TransferRupee.class);
            param.putString("money_transfer", context.getResources().getString(R.string.money_tgransfer));
            param.putString("name", nameSet.get(listPosition));
            param.putString("upi_id", numberSet.get(listPosition));
            intent_moneytransfer.putExtra(PAYLOAD_BUNDLE, param);
            activity.startActivity(intent_moneytransfer);
            activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        });
    }

    @Override
    public int getItemCount() {
        return nameSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        TextView tv_name, tv_number, name_image;
        LinearLayout contactlist_lo;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tv_name = itemView.findViewById(R.id.tv_name);
            this.tv_number = itemView.findViewById(R.id.tv_number);
            this.name_image = itemView.findViewById(R.id.name_image);
            this.contactlist_lo = itemView.findViewById(R.id.contactlist_lo);
            this.context = itemView.getContext();
        }
    }
}