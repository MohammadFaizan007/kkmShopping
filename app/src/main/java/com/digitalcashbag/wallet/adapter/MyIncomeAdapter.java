package com.digitalcashbag.wallet.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.wallet.LevelIncomeDetail;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.incentive.ListLevelWiseIncomeItem;
import kkm.com.core.utils.Utils;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class MyIncomeAdapter extends RecyclerView.Adapter<MyIncomeAdapter.ViewHolder> {


    private Context mContext;
    private List<ListLevelWiseIncomeItem> list;

    public MyIncomeAdapter(Context context, List<ListLevelWiseIncomeItem> list) {
        mContext = context;
        this.list = list;

    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_income_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        holder.tvLevel.setText(list.get(listPosition).getLevel());
        holder.tvIncome.setText(list.get(listPosition).getIncome());
        holder.tvOrders.setText(String.format("%s Orders", list.get(listPosition).getOrders()));
        holder.tvBusiness.setText(String.format("Value : %s", list.get(listPosition).getBusiness()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void getDetails(String levelId, int pos) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("income", list.get(pos));
        bundle.putString("levelId", levelId);
        goToActivity((Activity) mContext, LevelIncomeDetail.class, bundle);

    }

    public void goToActivity(Activity activity, Class<?> classActivity, Bundle bundle) {
        Utils.hideSoftKeyboard(activity);
        Intent intent = new Intent(activity, classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(kkm.com.core.R.anim.slide_from_right, kkm.com.core.R.anim.slide_to_left);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_level)
        TextView tvLevel;
        @BindView(R.id.tv_income)
        TextView tvIncome;
        @BindView(R.id.tv_orders)
        TextView tvOrders;
        @BindView(R.id.tv_business)
        TextView tvBusiness;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> getDetails(list.get(getAdapterPosition()).getLevelId(), getAdapterPosition()));
        }
    }


}

