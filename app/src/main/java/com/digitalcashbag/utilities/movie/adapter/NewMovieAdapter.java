//package com.digitalcashbag.utilities.movie.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.digitalcashbag.R;
//import com.example.library.banner.BannerLayout;
//
//import java.util.List;
//
//public class NewMovieAdapter extends RecyclerView.Adapter<NewMovieAdapter.MzViewHolder> {
//
//    private Context context;
//    private List<Integer> urlList;
//    private BannerLayout.OnBannerItemClickListener onBannerItemClickListener;
//
//    public NewMovieAdapter(Context context, List<Integer> urlList) {
//        this.context = context;
//        this.urlList = urlList;
//    }
//
//    public void setOnBannerItemClickListener(BannerLayout.OnBannerItemClickListener onBannerItemClickListener) {
//        this.onBannerItemClickListener = onBannerItemClickListener;
//    }
//
//
//    @Override
//    public NewMovieAdapter.MzViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new MzViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_movie_adapter, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull NewMovieAdapter.MzViewHolder holder, final int position) {
//        if (urlList == null || urlList.isEmpty())
//            return;
//        final int P = position % urlList.size();
//        Integer url = urlList.get(P);
//        ImageView img = holder.imageView;
//        img.setImageResource(url);
//        img.setOnClickListener(v -> {
////            if (onBannerItemClickListener != null) {
////                onBannerItemClickListener.onItemClick( P );
////            }
//            Intent intent_bookTicket = new Intent(context, Movie_Book_Tickets.class);
//            context.startActivity(intent_bookTicket);
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        if (urlList != null) {
//            return urlList.size();
//        }
//        return 0;
//    }
//
//    class MzViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//
//        MzViewHolder(View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.imageTimer);
//
//        }
//    }
//}