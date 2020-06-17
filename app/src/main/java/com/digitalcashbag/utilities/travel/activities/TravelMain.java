package com.digitalcashbag.utilities.travel.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.DemoFragment;
import com.digitalcashbag.R;
import com.digitalcashbag.utilities.travel.fragments.Bus;
import com.digitalcashbag.utilities.travel.fragments.Travel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;


public class TravelMain extends AppCompatActivity {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;

    Bundle param;
    @BindView(R.id.bell_icon)
    ImageView helpIcon;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travelmain);
        ButterKnife.bind(this);
        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        title.setText(param.getString("travel"));

        helpIcon.setVisibility(View.VISIBLE);
        helpIcon.setImageResource(R.drawable.help_ic);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        if (param.getString("travel_type").equalsIgnoreCase(getResources().getString(R.string.bus))) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Travel(), getResources().getString(R.string.train));
        adapter.addFragment(new Bus(), getResources().getString(R.string.bus));
        adapter.addFragment(new DemoFragment(), getResources().getString(R.string.flights));
        adapter.addFragment(new DemoFragment(), getResources().getString(R.string.hotel));
        viewPager.setAdapter(adapter);
    }

    @OnClick({R.id.side_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                hideKeyboard(this);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
        }
    }

    @OnClick(R.id.bell_icon)
    public void onViewClicked() {
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}