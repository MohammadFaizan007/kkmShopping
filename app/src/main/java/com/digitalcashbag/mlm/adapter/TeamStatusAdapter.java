package com.digitalcashbag.mlm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.team.TeamStatusItem;
import kkm.com.core.retrofit.MvpView;

public class TeamStatusAdapter extends RecyclerView.Adapter<TeamStatusAdapter.ViewHolder> {


    private Context mContext;
    private List<TeamStatusItem> list;
    private MvpView mvpView;

    public TeamStatusAdapter(Context context, List<TeamStatusItem> list, MvpView mvp) {
        mContext = context;
        this.list = list;
        mvpView = mvp;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teamstatus_item_lay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        holder.levelName.setText(list.get(listPosition).getLevelName());
        holder.levelMembers.setText(list.get(listPosition).getTeamSize());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.level_name)
        TextView levelName;
        @BindView(R.id.level_members)
        TextView levelMembers;
        @BindView(R.id.level_card)
        CardView levelCard;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            levelCard.setOnClickListener(v -> {
                if (!levelMembers.getText().toString().trim().equalsIgnoreCase("0"))
                    mvpView.getClickPosition(getAdapterPosition(), list.get(getAdapterPosition()).getLevelName());
                else
                    Toast.makeText(mContext, "No members found at this level.", Toast.LENGTH_SHORT).show();
            });


        }
    }


}

