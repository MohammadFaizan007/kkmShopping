package com.digitalcashbag.mlm.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.digitalcashbag.mlm.fragments.team.DirectMember;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.team.DirectMembersItem;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;

public class DirectMemberAdapter extends RecyclerView.Adapter<DirectMemberAdapter.ViewHolder> {


    private Context mContext;
    private List<DirectMembersItem> list;
    private MvpView mvpView;

    public DirectMemberAdapter(Context context, List<DirectMembersItem> list, MvpView mvp) {
        mContext = context;
        this.list = list;
        mvpView = mvp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.direct_member_item_lay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        holder.txtMemberId.setText(list.get(listPosition).getMemberId());
        holder.txtMemberName.setText(list.get(listPosition).getMemberName());
        holder.txtJoiningDate.setText(list.get(listPosition).getJoiningDate());
        holder.txtReferral.setText(Html.fromHtml("Referral Code: <font color=\"#F0830A\"><b>" + list.get(listPosition).getInviteCode() + "</b></font>"));
        LoggerUtil.logItem(DirectMember.previousIds.size());
        if (DirectMember.previousIds.size() == 0)
            holder.tv_mob_no.setText(list.get(listPosition).getMobile());
        else {
            holder.textView11.setVisibility(View.GONE);
            holder.tv_mob_no.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView11)
        TextView textView11;
        @BindView(R.id.tv_mob_no)
        TextView tv_mob_no;
        @BindView(R.id.txtMemberId)
        TextView txtMemberId;
        @BindView(R.id.txtMemberName)
        TextView txtMemberName;
        @BindView(R.id.txtJoiningDate)
        TextView txtJoiningDate;
        @BindView(R.id.btn_view)
        Button btnView;
        @BindView(R.id.txtReferral)
        TextView txtReferral;
        @BindView(R.id.imgCopy)
        ImageView imgCopy;
        @BindView(R.id.imgShare)
        ImageView imgShare;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            btnView.setOnClickListener(v -> mvpView.getClickPositionDirectMember(getAdapterPosition(), list.get(getAdapterPosition()).getMemberName(), list.get(getAdapterPosition()).getMemberId()));

            imgCopy.setOnClickListener(v -> {
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Referral Code", list.get(getAdapterPosition()).getInviteCode());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            });

            imgShare.setOnClickListener(v -> {
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_TEXT, list.get(getAdapterPosition()).getMemberName() + "'s Referral Code: " +
                        list.get(getAdapterPosition()).getInviteCode());
                mContext.startActivity(Intent.createChooser(intent2, "Share via"));
            });
        }
    }
}

