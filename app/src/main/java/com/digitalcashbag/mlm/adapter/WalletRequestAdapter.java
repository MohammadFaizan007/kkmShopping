package com.digitalcashbag.mlm.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.wallet.EWalletRequestlistItem;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class WalletRequestAdapter extends RecyclerView.Adapter<WalletRequestAdapter.ViewHolder> implements Filterable {

    private RequestNumberFilter requestNumberFilter;
    private Context mContext;
    private List<EWalletRequestlistItem> list;

    public WalletRequestAdapter(Context context, List<EWalletRequestlistItem> list) {
        mContext = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_request_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        holder.textRequestNo.setText(list.get(listPosition).getRequestNo());
        holder.textAmount.setText(list.get(listPosition).getRequestedAmount());
        holder.textRquestDate.setText(list.get(listPosition).getRequestedDate());
        holder.textDate.setText(list.get(listPosition).getApprovalDate());
        holder.textStatus.setText(list.get(listPosition).getPaymentStatus());

        if (list.get(listPosition).getPaymentStatus().equalsIgnoreCase("Approved"))
            holder.textStatus.setTextColor(mContext.getResources().getColor(R.color.green));
        else if (list.get(listPosition).getPaymentStatus().equalsIgnoreCase("Pending"))
            holder.textStatus.setTextColor(mContext.getResources().getColor(R.color.orange));
        else
            holder.textStatus.setTextColor(mContext.getResources().getColor(R.color.red));

        if (list.get(listPosition).getSlipUpload().equalsIgnoreCase("")) {
            holder.imageAttach.setVisibility(View.GONE);
        } else holder.imageAttach.setVisibility(View.VISIBLE);


    }


    @Override
    public Filter getFilter() {
        if (requestNumberFilter == null)
            requestNumberFilter = new RequestNumberFilter(this, list);
        return requestNumberFilter;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private static class RequestNumberFilter extends Filter {
        WalletRequestAdapter adapter;
        List<EWalletRequestlistItem> eWalletResponses;
        List<EWalletRequestlistItem> filteredList;

        private RequestNumberFilter(WalletRequestAdapter adapter, List<EWalletRequestlistItem> originalList) {
            super();
            this.adapter = adapter;
            this.eWalletResponses = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                filteredList.addAll(eWalletResponses);
            } else {
                final String filterPattern = constraint.toString().trim();
                for (final EWalletRequestlistItem eWalletRequestlistItem : eWalletResponses) {
                    if (Pattern.compile(Pattern.quote(filterPattern), Pattern.CASE_INSENSITIVE).matcher(eWalletRequestlistItem.getRequestNo()).find()) {
                        filteredList.add(eWalletRequestlistItem);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.list.clear();
            adapter.list.addAll((ArrayList<EWalletRequestlistItem>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textRequestNo)
        TextView textRequestNo;
        @BindView(R.id.textAmount)
        TextView textAmount;
        @BindView(R.id.textRquestDate)
        TextView textRquestDate;
        @BindView(R.id.textDate)
        TextView textDate;
        @BindView(R.id.textStatus)
        TextView textStatus;
        @BindView(R.id.imageAttach)
        ImageView imageAttach;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            imageAttach.setOnClickListener(v -> {
                showDialog(list.get(getAdapterPosition()).getSlipUpload());
            });

        }

        private void showDialog(String url) {
            Dialog birthday_dialog = new Dialog(mContext, R.style.FullScreenDialog);
            birthday_dialog.setCanceledOnTouchOutside(true);
            birthday_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            birthday_dialog.setContentView(R.layout.birthday_lay);
            int width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.95);
            int height = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.60);
            birthday_dialog.getWindow().setLayout(width, height);
            birthday_dialog.getWindow().setGravity(Gravity.CENTER);
            birthday_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            ImageView birthdayImage = birthday_dialog.findViewById(R.id.birthdayImage);
            ImageButton imgClose = birthday_dialog.findViewById(R.id.imgClose);

            Glide.with(mContext).load(url)
                    .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available).error(R.drawable.image_not_available))
                    .into(birthdayImage);

            imgClose.setOnClickListener(v1 -> birthday_dialog.dismiss());

            birthday_dialog.show();
        }
    }


}


