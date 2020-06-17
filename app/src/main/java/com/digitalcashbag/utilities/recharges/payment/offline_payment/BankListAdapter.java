package com.digitalcashbag.utilities.recharges.payment.offline_payment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import kkm.com.core.model.response.wallet.CompanyBankMasterSelectlistItem;
import kkm.com.core.retrofit.MvpView;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.ViewHolder> {
    private Context mContext;
    private List<CompanyBankMasterSelectlistItem> list;
    private MvpView mvpView;
    private int mSelectedItem = -1;

    BankListAdapter(Context context, List<CompanyBankMasterSelectlistItem> list, MvpView mvp) {
        mContext = context;
        this.list = list;
        mvpView = mvp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bank_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        holder.tvName.setText(list.get(listPosition).getCompanyName());
        holder.accntNo.setText(list.get(listPosition).getAccountNo());
        holder.ifscCode.setText(list.get(listPosition).getIfscCode());
        holder.bankName.setText(list.get(listPosition).getBankName());
        Glide.with(mContext).load(list.get(listPosition).getBankLogoURL())
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available).error(R.drawable.image_not_available))
                .into(holder.imgUser);
        holder.radioButton.setChecked(listPosition == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_user)
        CircleImageView imgUser;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.accnt_no)
        TextView accntNo;
        @BindView(R.id.ifsc_code)
        TextView ifscCode;
        @BindView(R.id.bank_name)
        TextView bankName;
        @BindView(R.id.radioButton)
        RadioButton radioButton;
        @BindView(R.id.bank_lo)
        ConstraintLayout bankLo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            radioButton.setOnClickListener(view1 -> {
                mvpView.getClickPositionDirectMember(getAdapterPosition(), list.get(getAdapterPosition()).getAccountNo(), list.get(getAdapterPosition()).getBankName());
                mSelectedItem = getAdapterPosition();
                notifyItemRangeChanged(0, list.size());
            });

            bankLo.setOnClickListener(view12 -> {
                mvpView.getClickPositionDirectMember(getAdapterPosition(), list.get(getAdapterPosition()).getAccountNo(), list.get(getAdapterPosition()).getBankName());
                mSelectedItem = getAdapterPosition();
                notifyItemRangeChanged(0, list.size());
            });
        }
    }
}

