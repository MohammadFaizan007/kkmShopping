package com.digitalcashbag.mlm.fragments.ticketview;

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
import kkm.com.core.model.request.ticketAllotment.TrainingItems;

public class AllotedTicketAdapter extends RecyclerView.Adapter<AllotedTicketAdapter.ViewHolder> {

    private Context mContext;
    private List<TrainingItems> list;
    private MyTrainings trainings;

    public AllotedTicketAdapter(Context context, List<TrainingItems> list, MyTrainings myTrainings) {
        mContext = context;
        this.list = list;
        this.trainings = myTrainings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_training_allotment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        holder.name.setText(list.get(listPosition).getName());
        holder.allotedTickets.setText(list.get(listPosition).getAssignTicket());
        holder.mobileNo.setText(list.get(listPosition).getMobile());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.mobile_no)
        TextView mobileNo;
        @BindView(R.id.alloted_tickets)
        TextView allotedTickets;
        @BindView(R.id.delete)
        TextView delete;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            delete.setOnClickListener(v -> {
                trainings.updateTickets(list.get(getAdapterPosition()).getAssignTicket());
                list.remove(getAdapterPosition());
                notifyDataSetChanged();
            });

        }
    }
}


