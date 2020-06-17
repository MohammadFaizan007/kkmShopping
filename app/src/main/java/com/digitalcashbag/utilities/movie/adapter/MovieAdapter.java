//package com.digitalcashbag.utilities.movie.adapter;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.digitalcashbag.R;
//
//import java.util.ArrayList;
//
//public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
//    private ArrayList<String> movieNameList;
//
//    public MovieAdapter(ArrayList<String> name) {
//        this.movieNameList = name;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_adapter, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int listPosition) {
//        holder.movie_name.setText(movieNameList.get(listPosition));
//    }
//
//    @Override
//    public int getItemCount() {
//        return movieNameList.size();
//    }
//
//    class MyViewHolder extends RecyclerView.ViewHolder {
//        private final Context context;
//        ImageView movie_image;
//        TextView movie_name, movie_language_type;
//        Button book_now_button;
//
//        MyViewHolder(View itemView) {
//            super(itemView);
//            this.movie_image = itemView.findViewById(R.id.movie_image);
//            this.movie_name = itemView.findViewById(R.id.movie_name);
//            this.movie_language_type = itemView.findViewById(R.id.movie_language_type);
//
//            this.book_now_button = itemView.findViewById(R.id.book_now_button);
//            this.context = itemView.getContext();
//
//            book_now_button.setOnClickListener(view -> {
//                Activity activity = (Activity) context;
//                Intent intent_bookTicket = new Intent(context, Movie_Book_Tickets.class);
//                context.startActivity(intent_bookTicket);
//                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//            });
//
//        }
//    }
//}