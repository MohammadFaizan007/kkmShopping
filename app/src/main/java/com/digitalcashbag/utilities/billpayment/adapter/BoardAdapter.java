package com.digitalcashbag.utilities.billpayment.adapter;

import android.app.Activity;
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
import kkm.com.core.model.response.bill.BillPaymentProviderListItem;
import kkm.com.core.retrofit.MvpView;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {


    private Context mContext;
    private MvpView mvpView;
    private List<BillPaymentProviderListItem> listItem;

    public BoardAdapter(Activity context, MvpView view, List<BillPaymentProviderListItem> item) {
        mContext = context;
        mvpView = view;
        listItem = item;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int listPosition) {

        holder.label.setText(listItem.get(listPosition).getProviderName());
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.label)
        TextView label;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mvpView.showMessage(listItem.get(getAdapterPosition()).getProviderName());
                }
            });
        }
    }
}