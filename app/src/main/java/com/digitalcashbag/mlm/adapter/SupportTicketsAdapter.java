package com.digitalcashbag.mlm.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.SupportTicketsItem;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class SupportTicketsAdapter extends RecyclerView.Adapter<SupportTicketsAdapter.SupportTicketsHolder> {
    private final Context context;
    private final List<SupportTicketsItem> items;

    public SupportTicketsAdapter(Activity context, List<SupportTicketsItem> items) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public SupportTicketsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.adapter_support_tickets, parent, false);
        return new SupportTicketsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupportTicketsHolder holder, int position) {
        holder.ticketNo.setText(items.get(position).getTicketNo());
        holder.requestDate.setText(items.get(position).getRequestDate());
        holder.replyOn.setText(items.get(position).getReplyOn());
        if (items.get(position).getStatus().equalsIgnoreCase("Pending"))
            holder.imgStatus.setImageResource(R.drawable.pending);
        else
            holder.imgStatus.setImageResource(R.drawable.tick);

        holder.subject.setText(items.get(position).getSubject());
        holder.body.setText(items.get(position).getMessage());
        holder.tvReply.setText(items.get(position).getReplyMessage());

        if (items.get(position).getAttachment().equalsIgnoreCase("")) {
            holder.imageAttachment.setVisibility(View.GONE);
        } else holder.imageAttachment.setVisibility(View.VISIBLE);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void showDialog(String url) {
        Dialog birthday_dialog = new Dialog(context, R.style.FullScreenDialog);
        birthday_dialog.setCanceledOnTouchOutside(true);
        birthday_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        birthday_dialog.setContentView(R.layout.birthday_lay);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.60);
        birthday_dialog.getWindow().setLayout(width, height);
        birthday_dialog.getWindow().setGravity(Gravity.CENTER);
        birthday_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView birthdayImage = birthday_dialog.findViewById(R.id.birthdayImage);
        ImageButton imgClose = birthday_dialog.findViewById(R.id.imgClose);

        Glide.with(context).load(url)
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available).error(R.drawable.image_not_available))
                .into(birthdayImage);

        imgClose.setOnClickListener(v1 -> birthday_dialog.dismiss());

        birthday_dialog.show();
    }

    class SupportTicketsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ticket_no)
        TextView ticketNo;
        @BindView(R.id.subject)
        TextView subject;
        @BindView(R.id.request_date)
        TextView requestDate;
        @BindView(R.id.body)
        TextView body;
        @BindView(R.id.tv_reply)
        TextView tvReply;
        @BindView(R.id.reply_on)
        TextView replyOn;
        @BindView(R.id.img_status)
        ImageView imgStatus;
        @BindView(R.id.imageView10)
        ImageView imageAttachment;

        SupportTicketsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imageAttachment.setOnClickListener(v -> {
                showDialog(items.get(getAdapterPosition()).getAttachment());
            });
        }
    }
}
