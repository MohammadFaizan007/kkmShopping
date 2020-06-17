package com.digitalcashbag.shopping.adapter;

import android.annotation.SuppressLint;
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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.notification.NotificationListItem;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    List<NotificationListItem> list;
    private Context context;

    public NotificationAdapter(Context context, List<NotificationListItem> notificationList) {
        list = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.noti_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtSubject.setText(list.get(position).getNotificationTitle());
        holder.txtBody.setText(list.get(position).getNotificationMessage());
        holder.txtDate.setText(String.format("%s %s", list.get(position).getNotificationDate(), list.get(position).getNotificationTime()));

        if (list.get(position).getImage().equalsIgnoreCase("")) {
            holder.imgAttach.setVisibility(View.GONE);
        } else holder.imgAttach.setVisibility(View.VISIBLE);

        if (list.get(position).getIsRead().equalsIgnoreCase("False"))
            holder.img_show.setVisibility(View.VISIBLE);
        else holder.img_show.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void showDialog(String url) {
        Dialog birthday_dialog = new Dialog(context, R.style.FullScreenDialog);
        birthday_dialog.setCanceledOnTouchOutside(true);
        birthday_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        birthday_dialog.setContentView(R.layout.birthday_lay);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.60);
        Objects.requireNonNull(birthday_dialog.getWindow()).setLayout(width, height);
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

    private void showFullDialog(NotificationListItem item) {
        Dialog notDialog = new Dialog(context);
        notDialog.setCanceledOnTouchOutside(true);
        notDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        notDialog.setContentView(R.layout.notification_details);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.60);
        notDialog.getWindow().setLayout(width, height);
        notDialog.getWindow().setGravity(Gravity.CENTER);
        notDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView txtSubject = notDialog.findViewById(R.id.txtSubject);
        TextView txtBody = notDialog.findViewById(R.id.txtBody);
        TextView txtDate = notDialog.findViewById(R.id.txtDate);
        ImageView imgClose = notDialog.findViewById(R.id.imgClose);

        txtSubject.setText(item.getNotificationTitle());
        txtBody.setText(item.getNotificationMessage());
        txtDate.setText(String.format("%s %s", item.getNotificationDate(), item.getNotificationTime()));

        imgClose.setOnClickListener(v1 -> notDialog.dismiss());

        notDialog.show();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtSubject)
        TextView txtSubject;
        @BindView(R.id.txtBody)
        TextView txtBody;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.imgAttach)
        ImageView imgAttach;
        @BindView(R.id.img_show)
        ImageView img_show;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            imgAttach.setOnClickListener(v -> {
                showDialog(list.get(getAdapterPosition()).getImage());
            });

//            img_show.setOnClickListener(v -> showFullDialog(list.get(getAdapterPosition())));
        }
    }
}

