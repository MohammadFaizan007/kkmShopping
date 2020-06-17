//package com.digitalcashbag.utilities.movie.activities;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.digitalcashbag.R;
//import com.digitalcashbag.utilities.movie.adapter.MoviePosterWallpaperAdapter;
//import com.digitalcashbag.utilities.recharges.activities.ItemOffsetDecoration;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import kkm.com.core.constants.BaseActivity;
//
//
//public class Movie_Book_Tickets extends BaseActivity /*YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener*/ {
//    //    public static final String API_KEY = "AIzaSyDizu4fJLJ7xgR_okCOotREDLOYg7gx9jo";
////    public static final String VIDEO_ID = "Ru4lEmhHTF4";
////    Bundle param;
//    @BindView(R.id.read_more_lo)
//    LinearLayout read_more_lo;
//    @BindView(R.id.aboutmovie_second)
//    TextView aboutmoviesecond;
//    @BindView(R.id.read_more)
//    TextView readmore;
//    @BindView(R.id.book_tickets)
//    TextView booktickets;
//    //    RecyclerView androidGridView;
//    int[] gridImageIdShoppingOffers = {R.drawable.zero, R.drawable.fryday, R.drawable.zero, R.drawable.fryday,};
//    private MoviePosterWallpaperAdapter moviePosterWallpaperAdapter;
//
//    public static void hideKeyboard(Activity activity) {
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        //Find the currently focused view, so we can grab the correct window token from it.
//        View view = activity.getCurrentFocus();
//        //If no view currently has focus, create a new one, just so we can grab a window token from it
//        if (view == null) {
//            view = new View(activity);
//        }
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.movie_book_ticket);
//        ButterKnife.bind(this);
//
//        // Initializing YouTube player view
////        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
////        youTubePlayerView.initialize(API_KEY, this);
//
//        RecyclerView gvshoppingoffers = findViewById(R.id.gv);
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
//        gvshoppingoffers.addItemDecoration(itemDecoration);
//        gvshoppingoffers.setHasFixedSize(true);
//        gvshoppingoffers.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
//        moviePosterWallpaperAdapter = new MoviePosterWallpaperAdapter(this, gridImageIdShoppingOffers);
//        gvshoppingoffers.setAdapter(moviePosterWallpaperAdapter);
//    }
//
//    @OnClick({R.id.book_tickets, R.id.read_more})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
////            case R.id.side_menu:
////                hideKeyboard( this );
////                finish();
////                break;
//            case R.id.read_more:
//                try {
//                    if (read_more_lo.getVisibility() == View.GONE) {
//                        read_more_lo.setVisibility(View.VISIBLE);
//                        readmore.setText("Read Less");
//                        readmore.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.uparrow_blue, 0);
//                    } else {
//                        read_more_lo.setVisibility(View.GONE);
//                        readmore.setText("Read More");
//                        readmore.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dwonarrow_blue, 0);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            case R.id.book_tickets:
//                Toast.makeText(getApplicationContext(), "BookTicket", Toast.LENGTH_SHORT).show();
//                break;
//
//        }
//    }
//
////    @Override
////    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
////        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
////    }
////
////    @Override
////    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
////        if (null == player) return;
////        // Start buffering
////        if (!wasRestored) {
////            player.cueVideo(VIDEO_ID);
////        }
////        // Add listeners to YouTubePlayer instance
////        player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
////            @Override
////            public void onAdStarted() {
////            }
////
////            @Override
////            public void onError(YouTubePlayer.ErrorReason arg0) {
////            }
////
////            @Override
////            public void onLoaded(String arg0) {
////            }
////
////            @Override
////            public void onLoading() {
////            }
////
////            @Override
////            public void onVideoEnded() {
////            }
////
////            @Override
////            public void onVideoStarted() {
////            }
////        });
////        player.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
////            @Override
////            public void onBuffering(boolean arg0) {
////            }
////
////            @Override
////            public void onPaused() {
////            }
////
////            @Override
////            public void onPlaying() {
////            }
////
////            @Override
////            public void onSeekTo(int arg0) {
////            }
////
////            @Override
////            public void onStopped() {
////            }
////        });
////    }
//
//    @Override
//    public void onBackPressed() {
//        hideKeyboard(this);
//        finish();
//        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
//    }
//}