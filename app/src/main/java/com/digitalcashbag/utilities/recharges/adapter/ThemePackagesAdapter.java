package com.digitalcashbag.utilities.recharges.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalcashbag.R;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.themeParkResponse.themeParkDetails.AddinfoItem;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.Utils;

public class ThemePackagesAdapter extends RecyclerView.Adapter<ThemePackagesAdapter.ViewHolder> {

    private Context mContext;
    private List<AddinfoItem> list;
    private MvpView mvpView;

    public ThemePackagesAdapter(Context context, List<AddinfoItem> list, MvpView mvp) {
        mContext = context;
        this.list = list;
        mvpView = mvp;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_themeparks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        holder.tvName.setText(list.get(listPosition).getName());
        holder.tvCity.setText(list.get(listPosition).getCity());
        holder.tvCountry.setText(list.get(listPosition).getCountry());
        holder.tvAmount.setText(String.format("₹ %s", list.get(listPosition).getPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_city)
        TextView tvCity;
        @BindView(R.id.tv_country)
        TextView tvCountry;
        @BindView(R.id.tv_amount)
        TextView tvAmount;
        @BindView(R.id.tv_check_availability)
        TextView tv_check_availability;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            tvName.setOnClickListener(v -> mvpView.getGiftCardCategoryId(list.get(getAdapterPosition()).getProductId(), list.get(getAdapterPosition()).getName()));
            tv_check_availability.setOnClickListener(v -> {
                try {
                    Calendar cal = Calendar.getInstance();
                    int mYear, mMonth, mDay;
                    mYear = cal.get(Calendar.YEAR);
                    mMonth = cal.get(Calendar.MONTH);
                    mDay = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.DatePickerDialogTheme, (view1, year, monthOfYear, dayOfMonth) -> {
                        mvpView.checkAvailability(list.get(getAdapterPosition()).getProductId(), Utils.changeDateFormatTheme(dayOfMonth, monthOfYear, year), list.get(getAdapterPosition()).getName(), list.get(getAdapterPosition()).getPrice());
                    }, mYear, mMonth, mDay);

                    datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                    datePickerDialog.show();
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
