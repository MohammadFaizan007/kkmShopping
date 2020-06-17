package com.digitalcashbag.utilities.recharges.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.BuildConfig;
import kkm.com.core.model.response.utility.RecentActivityItem;
import kkm.com.core.retrofit.MvpView;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class RecentMobileRechargesAdapter extends RecyclerView.Adapter<RecentMobileRechargesAdapter.ViewHolder> {
    private Context mContext;
    private List<RecentActivityItem> list;
    private MvpView mvpView;


    public RecentMobileRechargesAdapter(Activity context, List<RecentActivityItem> addinfo, MvpView mvp) {
        mContext = context;
        this.list = addinfo;
        mvpView = mvp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recent_recharges, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        holder.orderNo.setText(String.format("Order No %s", list.get(listPosition).getTransactionId()));
        holder.amount.setText(String.format("₹ %s", list.get(listPosition).getAmount()));
        holder.date.setText(list.get(listPosition).getPaymentDate());
//        holder.operatorName.setText( list.get(listPosition).getOperator());
        holder.rechargeNumber.setText(String.format("Recharge of %s Mobile %s", list.get(listPosition).getOperator(), list.get(listPosition).getMobileNo()));

        if (list.get(listPosition).getError().equalsIgnoreCase("0") && list.get(listPosition).getResult().equalsIgnoreCase("0")) {
            holder.status.setText("Your order is successful");
            holder.status.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.status.setText("Your order is failed");
            holder.status.setTextColor(mContext.getResources().getColor(R.color.red));
        }

        if (list.get(listPosition).getOperator().equalsIgnoreCase("Airtel")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/airtel.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("Idea")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/idea.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("BSNLTopUp")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/bsnl.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("TATADocomoFlexi")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/tatadocomo.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("Vodafone")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/vodafone.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("TATADocomoSpecial")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/tatadocomo.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("BSNLValiditySpecial")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/bsnl.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("MTNLTopUP")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/mts.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("MTNLValiditySpecial")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/mts.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("RelianceDigitalTV")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/reliancedigitaltv.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("SUNTV")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/suntv.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("VideoconD2H")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/videocondh.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("DISHTV")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/dishtv.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("Jio")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/jio.png", holder.operatorName);
        }
//        else if (list.get( listPosition ).getOperator().equalsIgnoreCase( "TATASKYB2B" )) {
//            holder.operatorName.setBackground( mContext.getResources().getDrawable( R.drawable.medcine ) );
//
//        }
//        else if (list.get( listPosition ).getOperator().equalsIgnoreCase( "TATASKYB2C" )) {
//            holder.operatorName.setBackground( mContext.getResources().getDrawable( R.drawable.medcine ) );
//
//        }
    }

    private void setProviderImage(String path, ImageView imageView) {
        Glide.with(mContext).load(path)
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.in_utility)
                        .error(R.drawable.cross))
                .into(imageView);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_no)
        TextView orderNo;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.operator_name)
        ImageView operatorName;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.repeat)
        TextView repeat;
        @BindView(R.id.recharge_number)
        TextView rechargeNumber;
        @BindView(R.id.status_lo)
        RelativeLayout status_lo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            repeat.setOnClickListener(view1 -> mvpView.checkAvailability(list.get(getAdapterPosition()).getMobileNo(), list.get(getAdapterPosition()).getOperator(), list.get(getAdapterPosition()).getAmount(), ""));
        }
    }

}
