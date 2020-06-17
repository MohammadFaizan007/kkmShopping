//package com.digitalcashbag.utilities.movie.activities;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.HorizontalScrollView;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.digitalcashbag.R;
//import com.digitalcashbag.utilities.movie.adapter.MovieAdapter;
//import com.digitalcashbag.utilities.movie.adapter.NewMovieAdapter;
//import com.example.library.banner.BannerLayout;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class MovieTickets extends AppCompatActivity {
//
//    Bundle param;
//    @BindView(R.id.side_menu)
//    ImageView sideMenu;
//    @BindView(R.id.title)
//    TextView title;
//    @BindView(R.id.new_movie_recycler)
//    BannerLayout newMovieRecycler;
//    @BindView(R.id.movie_recycler)
//    RecyclerView movieRecycler;
//    @BindView(R.id.all_tv)
//    TextView allTv;
//    @BindView(R.id.hindi_tv)
//    TextView hindiTv;
//    @BindView(R.id.english_tv)
//    TextView englishTv;
//    @BindView(R.id.tamil_tv)
//    TextView tamilTv;
//    @BindView(R.id.bhojpuri_tv)
//    TextView bhojpuriTv;
//    @BindView(R.id.horizontal_scrollview)
//    HorizontalScrollView horizontalScrollview;
//
//    ArrayList<String> movieNameList;
//    List<Integer> list;
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
//        setContentView(R.layout.movie_tickets);
//        ButterKnife.bind(this);
////        param = getIntent().getBundleExtra( "android.intent.extra.INTENT" );
////        title.setText( param.getString( "movie_tickets" ) );
//        title.setText("Select Location");
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        list = new ArrayList<>();
//        list.add((R.drawable.zero));
//        list.add((R.drawable.fryday));
//        list.add((R.drawable.zero));
//        NewMovieAdapter webBannerAdapter = new NewMovieAdapter(this, list);
//        newMovieRecycler.setAdapter(webBannerAdapter);
//
//        movieNameList = new ArrayList<>();
//        movieNameList.add("Fryday");
//        movieNameList.add("Fryday");
//        movieNameList.add("Fryday");
//        movieNameList.add("Fryday");
//        MovieAdapter movieAdapter = new MovieAdapter(movieNameList);
//        movieRecycler.setHasFixedSize(true);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        movieRecycler.setLayoutManager(mLayoutManager);
//        movieRecycler.setNestedScrollingEnabled(true);
//        movieRecycler.setAdapter(movieAdapter);
//
//    }
//
//    @OnClick({R.id.side_menu, R.id.all_tv, R.id.hindi_tv, R.id.english_tv, R.id.tamil_tv, R.id.bhojpuri_tv})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.side_menu:
//                hideKeyboard(this);
//                finish();
//                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
//                break;
//            case R.id.all_tv:
//                changeBtnBg(allTv, hindiTv, englishTv, tamilTv, bhojpuriTv);
//                break;
//            case R.id.hindi_tv:
//                changeBtnBg(hindiTv, allTv, englishTv, tamilTv, bhojpuriTv);
//                break;
//            case R.id.english_tv:
//                changeBtnBg(englishTv, allTv, hindiTv, tamilTv, bhojpuriTv);
//                break;
//            case R.id.tamil_tv:
//                changeBtnBg(tamilTv, allTv, hindiTv, englishTv, bhojpuriTv);
//                break;
//            case R.id.bhojpuri_tv:
//                changeBtnBg(bhojpuriTv, allTv, hindiTv, englishTv, tamilTv);
//                break;
//        }
//    }
//
//    private void changeBtnBg(TextView tv1, TextView tv2, TextView tv3, TextView tv4, TextView tv5) {
//        tv1.setTextColor(getResources().getColor(R.color.white));
//        tv1.setBackground(getResources().getDrawable(R.drawable.rect_btn_bg_darkgreen));
//        tv2.setTextColor(getResources().getColor(R.color.text_color));
//        tv2.setBackground(getResources().getDrawable(R.drawable.rect_hollow));
//        tv3.setTextColor(getResources().getColor(R.color.text_color));
//        tv3.setBackground(getResources().getDrawable(R.drawable.rect_hollow));
//        tv4.setTextColor(getResources().getColor(R.color.text_color));
//        tv4.setBackground(getResources().getDrawable(R.drawable.rect_hollow));
//        tv5.setTextColor(getResources().getColor(R.color.text_color));
//        tv5.setBackground(getResources().getDrawable(R.drawable.rect_hollow));
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.search_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.search_m:
//                super.onSearchRequested();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        finish();
//        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
//    }
//}
