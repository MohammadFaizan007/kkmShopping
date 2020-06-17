package com.digitalcashbag.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.common_activities.LoginActivity;
import com.digitalcashbag.common_activities.SearchActivity;
import com.digitalcashbag.shopping.activities.ShoppingActivityMain;
import com.digitalcashbag.utilities.billpayment.activity.OtherBillPayment;
import com.digitalcashbag.utilities.recharges.activities.AddMoney;
import com.digitalcashbag.utilities.recharges.activities.DthRecharge;
import com.digitalcashbag.utilities.recharges.activities.GiftCardCategories;
import com.digitalcashbag.utilities.recharges.activities.MobileRecharge;
import com.digitalcashbag.utilities.recharges.activities.MoneyTransfer;
import com.digitalcashbag.utilities.themepark.ThemePark;
import com.digitalcashbag.utilities.travel.activities.TravelMain;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.model.response.searchResponse.KeySearchlistItem;
import kkm.com.core.model.response.searchResponse.RecentlistItem;
import kkm.com.core.model.response.searchResponse.ResponseSearchItems;
import kkm.com.core.model.response.searchResponse.TrendinglistItem;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private int size;
    private Bundle param = new Bundle();
    private Context mContext;
    private ResponseSearchItems items;
    private List<RecentlistItem> recentlist;
    private List<KeySearchlistItem> keySearchlist;
    private List<TrendinglistItem> trendinglist;
    private MvpView mvpView;

    public SearchAdapter(Context context, ResponseSearchItems items, MvpView mvp) {
        mContext = context;
        this.items = items;
        recentlist = items.getRecentlist();
        keySearchlist = items.getKeySearchlist();
        trendinglist = items.getTrendinglist();
        size = recentlist.size() + keySearchlist.size() + trendinglist.size();
        mvpView = mvp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_search_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        if (recentlist.size() > 0) {
            holder.tv_search_text.setText(recentlist.get(listPosition).getCategoryName());
            holder.tv_search_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.pending, 0);
            holder.tvCatId.setText(recentlist.get(listPosition).getCategoryId());
            holder.tvSearchClass.setText(String.format("%s", recentlist.get(listPosition).getClassName()));
        } else if (trendinglist.size() > 0) {
            holder.tv_search_text.setText(trendinglist.get(listPosition).getCategoryName());
            holder.tv_search_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.trending, 0);
            holder.tvCatId.setText(trendinglist.get(listPosition).getCategoryId());
            holder.tvSearchClass.setText(String.format("%s", trendinglist.get(listPosition).getClassName()));
        } else if (keySearchlist.size() > 0) {
            holder.tv_search_text.setText(keySearchlist.get(listPosition).getCategoryName());
            holder.tv_search_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search, 0);
            holder.tvCatId.setText(keySearchlist.get(listPosition).getCategoryId());
            holder.tvSearchClass.setText(String.format("%s", keySearchlist.get(listPosition).getClassName()));
        }

        holder.tv_search_text.setOnClickListener(view -> {
            LoggerUtil.logItem(holder.tvSearchClass.getText().toString());
            if (holder.tvSearchClass.getText().toString().length() > 0) {
                param.clear();
                switch (holder.tvSearchClass.getText().toString()) {
                    case "MobileRecharge":
                        param.putString("mobile_recharge", mContext.getResources().getString(R.string.mobile_postpaid));
                        ((SearchActivity) mContext).goToActivity((Activity) mContext, MobileRecharge.class, param);
                        break;
                    case "DthRecharge":
                        param.putString("dth_rech", mContext.getResources().getString(R.string.dth_recharge));
                        ((SearchActivity) mContext).goToActivity((Activity) mContext, DthRecharge.class, param);
                        break;
                    case "OtherBillPayment":
                        param.putString("bill", "Electricity Bill Payment");
                        ((SearchActivity) mContext).goToActivity((Activity) mContext, OtherBillPayment.class, param);
                        break;
//                    case "MetroRecharge":
//                        param.putString("Metro", mContext.getResources().getString(R.string.metro));
//                        ((SearchActivity) mContext).goToActivity((Activity) mContext, MetroRecharge.class, param);
//                        break;
                    case "TravelMain":
                        param.putString("travel", mContext.getResources().getString(R.string.travel));
                        param.putString("travel_type", "Train");
                        ((SearchActivity) mContext).goToActivity((Activity) mContext, TravelMain.class, param);
                        break;
                    case "ShoppingCategoryActivity":
                        param.putString("shopping", "Shopping");
                        ((SearchActivity) mContext).goToActivity((Activity) mContext, ShoppingActivityMain.class, param);
                        break;
                    case "ThemePark":
                        param.putString("from", mContext.getResources().getString(R.string.theme_park));
                        checkLogin("Theme Park");
                        break;
                    case "GiftCardCategories":
                        checkLogin("Gift Card Categories");
                        break;
                    case "AddMoney":
                        checkLogin("Add Money");
                        break;
                    case "MoneyTransfer":
                        checkLogin("Money Transfer");
                        break;
//                    case "MovieTickets":
//                        param.putString("movie_tickets", "Select Location");
//                        ((SearchActivity) mContext).goToActivity((Activity) mContext, MovieTickets.class, param);
//                        break;
                }
            } else if (holder.tvCatId.getText().toString().length() > 0) {
                mvpView.openSearchCategory(holder.tvCatId.getText().toString(), holder.tv_search_text.getText().toString());
            }
        });


    }

    void checkLogin(String title) {
        if (PreferencesManager.getInstance(mContext).getUSERID().equalsIgnoreCase("")) {
            ((SearchActivity) mContext).goToActivity((Activity) mContext, LoginActivity.class, null);
        } else {
            switch (title) {
                case "Theme Park":
                    ((SearchActivity) mContext).goToActivity((Activity) mContext, ThemePark.class, null);
                    break;
                case "Gift Card Categories":
                    param.putString("from", "Gift Card Categories");
                    ((SearchActivity) mContext).goToActivity((Activity) mContext, GiftCardCategories.class, param);
                    break;
                case "Add Money":
                    param.putString("from", mContext.getResources().getString(R.string.add_money));
                    ((SearchActivity) mContext).goToActivity((Activity) mContext, AddMoney.class, param);
                    break;
                case "Money Transfer":
                    param.putString("from", mContext.getResources().getString(R.string.money_tgransfer));
                    ((SearchActivity) mContext).goToActivity((Activity) mContext, MoneyTransfer.class, param);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_search_text)
        TextView tv_search_text;

        @BindView(R.id.tv_cat_id)
        TextView tvCatId;
        @BindView(R.id.tv_product_id)
        TextView tvProductId;
        @BindView(R.id.tv_search_class)
        TextView tvSearchClass;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
//            view.setOnClickListener( v -> {
//
//            } );
        }
    }


}