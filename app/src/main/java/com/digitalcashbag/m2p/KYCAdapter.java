package com.digitalcashbag.m2p;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.request.m2p.KYCDocument;

class KYCAdapter extends RecyclerView.Adapter<KYCAdapter.ViewHolder> {

    private final Context context;

    private ArrayList<KYCDocument> itemsList;

    public KYCAdapter(Activity context, ArrayList<KYCDocument> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }


    public void updateList(ArrayList<KYCDocument> itemsList) {
        this.itemsList = itemsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.add_kyc_doc, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.kycTvAdd.setText("Delete");
            holder.kycTvAdd.setBackgroundColor(ContextCompat.getColor(context, R.color.color_red));
            holder.kycEtDocType.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            holder.kycEtDocType.setText(itemsList.get(position).getDocType());
            holder.kycEtDocNumber.setText(String.format("%s y", itemsList.get(position).getDocNo()));
            if (itemsList.get(position).getDocType().equalsIgnoreCase("PAN")) {
                holder.textViewEx.setVisibility(View.GONE);
                holder.kycEtExpiryDate.setVisibility(View.GONE);
            } else {
                holder.kycEtExpiryDate.setVisibility(View.VISIBLE);
                holder.kycEtExpiryDate.setText(itemsList.get(position).getDocExpDate());
            }

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.kyc_et_doc_type)
        TextView kycEtDocType;
        @BindView(R.id.kyc_et_doc_number)
        EditText kycEtDocNumber;
        @BindView(R.id.kyc_et_expiry_date)
        TextView kycEtExpiryDate;
        @BindView(R.id.textViewEx)
        TextView textViewEx;
        @BindView(R.id.kyc_tv_add)
        Button kycTvAdd;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.setIsRecyclable(false);

            kycTvAdd.setOnClickListener(view1 -> {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Remove Document");
                builder1.setMessage("Do you really want to remove " + kycEtDocType.getText().toString().trim() + " ?");
                builder1.setCancelable(true);

                builder1.setNegativeButton(
                        "Cancel",
                        (dialog, id) -> {
                            dialog.cancel();
                        });

                builder1.setPositiveButton(
                        "Yes",
                        (dialog, id) -> {
                            Toast.makeText(context, "Document Removed.", Toast.LENGTH_LONG).show();
                            itemsList.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            });
        }
    }


}
