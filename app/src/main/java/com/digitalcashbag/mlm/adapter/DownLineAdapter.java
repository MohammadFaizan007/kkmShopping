package com.digitalcashbag.mlm.adapter;

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
import kkm.com.core.model.response.team.DownlineListItem;

import static kkm.com.core.utils.Utils.changeDateFormatDownline;

public class DownLineAdapter extends RecyclerView.Adapter<DownLineAdapter.ViewHolder> {

    private Context mContext;
    private List<DownlineListItem> list;

    public DownLineAdapter(Context context, List<DownlineListItem> list) {
        mContext = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.downline_item_lay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        holder.txtMemberID.setText(list.get(listPosition).getMemberId());
        holder.txtMemberName.setText(list.get(listPosition).getMemberName());
        holder.sponsorDetails.setText(String.format("%s %s", list.get(listPosition).getSponsorName(), list.get(listPosition).getSponsorId()));
        holder.txtDate.setText(changeDateFormatDownline(list.get(listPosition).getJoiningDate()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtMemberID)
        TextView txtMemberID;
        @BindView(R.id.txtMemberName)
        TextView txtMemberName;
        @BindView(R.id.sponsor_details)
        TextView sponsorDetails;
        @BindView(R.id.txtDate)
        TextView txtDate;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }


}