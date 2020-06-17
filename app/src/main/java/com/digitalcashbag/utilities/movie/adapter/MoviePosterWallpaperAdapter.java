//package com.digitalcashbag.utilities.movie.adapter;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.digitalcashbag.R;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//
//public class MoviePosterWallpaperAdapter extends RecyclerView.Adapter<MoviePosterWallpaperAdapter.ViewHolder> {
//
////    MvpView mvp;
//
//    //    private final String[] gridViewString;
//    private final int[] gridViewImageId;
//    private Context mContext;
//
//    public MoviePosterWallpaperAdapter(Context context, int[] gridViewImageId) {
//        this.mContext = context;
//        this.gridViewImageId = gridViewImageId;
////        this.gridViewString = gridViewString;
////        this.mvp = mvpView;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout_poster_wallpaper, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
////        holder.androidGridviewText.setText(gridViewString[listPosition]);
//        holder.androidGridviewImage.setImageResource(gridViewImageId[listPosition]);
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return gridViewImageId.length;
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.android_gridview_image)
//        ImageView androidGridviewImage;
//        //        @BindView(R.id.android_gridview_text)
////        TextView androidGridviewText;
//        @BindView(R.id.android_custom_gridview_layout)
//        LinearLayout androidCustomGridviewLayout;
//
//        ViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//            androidCustomGridviewLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    mvp.getClickPosition(getAdapterPosition(), "shopping");
//                }
//            });
//        }
//    }
//
//
//}