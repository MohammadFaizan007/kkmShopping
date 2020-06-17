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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import kkm.com.core.model.response.team.TeamStatusdetailsItem;
import kkm.com.core.retrofit.MvpView;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class TeamStatusLevelViewAdapter extends RecyclerView.Adapter<TeamStatusLevelViewAdapter.ViewHolder> implements Filterable {


    private Context mContext;
    private List<TeamStatusdetailsItem> list;
    private MvpView mvpView;
    private UserFilter userFilter;

    public TeamStatusLevelViewAdapter(Context context, List<TeamStatusdetailsItem> list, MvpView mvp) {
        mContext = context;
        this.list = list;
        mvpView = mvp;

    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_team_levels, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        holder.tvName.setText(list.get(listPosition).getMemberName());
        holder.tvCashbagId.setText(list.get(listPosition).getLoginId());
        holder.tv_member.setText(list.get(listPosition).getTotalMembers());
        Glide.with(mContext).load(list.get(listPosition).getProfilepic()).apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.white_user).error(R.drawable.white_user)).into(holder.imgUser);
        holder.txtReferral.setText(Html.fromHtml("Referral Code: <font color=\"#F0830A\"><b>" + list.get(listPosition).getInviteCode() + "</b></font>"));
//        TODO
        if (list.get(listPosition).getKycStatus().equalsIgnoreCase("Approved"))
            holder.img_status.setImageResource(R.drawable.green_circle);
        else
            holder.img_status.setImageResource(R.drawable.red_circle);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        if (userFilter == null)
            userFilter = new UserFilter(this, list);
        return userFilter;
    }

    private static class UserFilter extends Filter {
        TeamStatusLevelViewAdapter adapter;
        List<TeamStatusdetailsItem> myDirectResponses;
        List<TeamStatusdetailsItem> filteredList;

        private UserFilter(TeamStatusLevelViewAdapter adapter, List<TeamStatusdetailsItem> originalList) {
            super();
            this.adapter = adapter;
            this.myDirectResponses = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                filteredList.addAll(myDirectResponses);
            } else {
                final String filterPattern = constraint.toString().trim();
                for (final TeamStatusdetailsItem user : myDirectResponses) {
                    if (Pattern.compile(Pattern.quote(filterPattern), Pattern.CASE_INSENSITIVE).matcher(user.getMemberName()).find() ||
                            Pattern.compile(Pattern.quote(filterPattern), Pattern.CASE_INSENSITIVE).matcher(user.getLoginId()).find() ||
                            Pattern.compile(Pattern.quote(filterPattern), Pattern.CASE_INSENSITIVE).matcher(user.getMobile()).find()) {
                        filteredList.add(user);
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
            adapter.list.addAll((ArrayList<TeamStatusdetailsItem>) results.values);
            adapter.notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_user)
        CircleImageView imgUser;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_cashbag_id)
        TextView tvCashbagId;
        @BindView(R.id.tv_member)
        TextView tv_member;
        @BindView(R.id.txtReferral)
        TextView txtReferral;
        @BindView(R.id.imgCopy)
        ImageView imgCopy;
        @BindView(R.id.imgShare)
        ImageView imgShare;
        @BindView(R.id.img_status)
        ImageView img_status;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

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
                intent2.putExtra(Intent.EXTRA_TEXT, list.get(getAdapterPosition()).getMemberName() + "'s Referral Code: " + list.get(getAdapterPosition()).getInviteCode());
                mContext.startActivity(Intent.createChooser(intent2, "Share via"));
            });

        }
    }

}

