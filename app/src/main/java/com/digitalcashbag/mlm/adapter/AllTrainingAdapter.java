package com.digitalcashbag.mlm.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.digitalcashbag.mlm.fragments.ticketview.TrainingBookings;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.getMyTraining.EventDetailsItem;
import kkm.com.core.retrofit.MvpView;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class AllTrainingAdapter extends RecyclerView.Adapter<AllTrainingAdapter.ViewHolder> {


    private Context mContext;
    private List<EventDetailsItem> list;
    private MvpView mvp;

    public AllTrainingAdapter(Context context, List<EventDetailsItem> list, MvpView mvp) {
        mContext = context;
        this.list = list;
        this.mvp = mvp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_training_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        holder.placeName.setSelected(true);
        holder.venue.setSelected(true);
        holder.placeName.setText(list.get(listPosition).getLocation());
        holder.venue.setText(list.get(listPosition).getVenue());
        holder.timings.setText(String.format("%s, %s-%s", list.get(listPosition).getEventDate(), list.get(listPosition).getFromtime(), list.get(listPosition).getToTime()));
        holder.trainingAmount.setText(String.format("₹ %s", list.get(listPosition).getTicketAmount()));
        holder.trainingName.setText(list.get(listPosition).getEventName());
        Glide.with(mContext).load(list.get(listPosition).getImageUrl())
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.banner_c).error(R.drawable.banner_c))
                .into(holder.place_img);

        holder.alloted_tickets.setText(String.format("Allotted Tickets %s", list.get(listPosition).getAvailableTickets()));

        holder.my_bookings.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("eventId", list.get(listPosition).getPkEventNameId());
            ((BaseActivity) mContext).goToActivity((Activity) mContext, TrainingBookings.class, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView16)
        ImageView place_img;
        @BindView(R.id.place_name)
        TextView placeName;
        @BindView(R.id.venue)
        TextView venue;
        @BindView(R.id.training_name)
        TextView trainingName;
        @BindView(R.id.timings)
        TextView timings;
        @BindView(R.id.book_now)
        TextView book_now;
        @BindView(R.id.training_amount)
        TextView trainingAmount;
        @BindView(R.id.my_bookings)
        TextView my_bookings;
        @BindView(R.id.transfer_now)
        TextView transfer_now;
        @BindView(R.id.alloted_tickets)
        TextView alloted_tickets;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            book_now.setOnClickListener(v -> {
                if (Integer.parseInt(list.get(getAdapterPosition()).getAvailableTickets()) > 0) {
                    mvp.getTrainingID(list.get(getAdapterPosition()).getTicketAmount(),
                            list.get(getAdapterPosition()).getEventName(),
                            list.get(getAdapterPosition()).getPkEventNameId());
                } else {
                    mvp.showMessage("Tickets Not Available.");
                }
            });


            transfer_now.setOnClickListener(v -> {
                if (Integer.parseInt(list.get(getAdapterPosition()).getAvailableTickets()) > 0) {
                    mvp.setTransferTicket(list.get(getAdapterPosition()).getTicketAmount(),
                            list.get(getAdapterPosition()).getEventName(),
                            list.get(getAdapterPosition()).getPkEventNameId(),
                            list.get(getAdapterPosition()).getAvailableTickets(),
                            list.get(getAdapterPosition()).getEventDate());
                } else {
                    mvp.showMessage("Tickets Not Available.");
                }
            });
        }
    }

}