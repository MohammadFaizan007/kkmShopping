package com.digitalcashbag.mlm.fragments.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalcashbag.R;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.utils.LoggerUtil;

public class EditProfileMlm extends BaseFragment implements IPickResult {
    Unbinder unbinder;
    ViewPagerAdapter adapter;
    int currentPosition = 0;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile_mlm, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPosition = i;
                LoggerUtil.logItem(currentPosition + " selected");
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.select();
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new EditPersonalDetails(), "Personal");
        adapter.addFragment(new EditBankDetails(), "Bank");
        adapter.addFragment(new EditKycDetail(), "KYC");
        adapter.addFragment(new EditInsuranceDetails(), "Insurance");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if (adapter.getItem(currentPosition) instanceof EditKycDetail) {
            if (currentPosition == 2) {
                ((EditKycDetail) adapter.getItem(currentPosition)).onPickResult(pickResult);
            }
        } else if (adapter.getItem(currentPosition) instanceof EditBankDetails) {
            if (currentPosition == 1) {
                ((EditBankDetails) adapter.getItem(currentPosition)).onPickResult(pickResult);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.getItem(currentPosition).onActivityResult(requestCode, resultCode, data);

//        if (adapter.getItem(currentPosition) instanceof EditKycDetail) {
//            if (currentPosition == 2) {
//                adapter.getItem(currentPosition).onActivityResult(requestCode, resultCode, data);
//            }
//        } else if (adapter.getItem(currentPosition) instanceof EditBankDetails) {
//            if (currentPosition == 1) {
//                adapter.getItem(currentPosition).onActivityResult(requestCode, resultCode, data);
//            }
//        } else if (adapter.getItem(currentPosition) instanceof EditPersonalDetails) {
//            if (currentPosition == 0) {
//                adapter.getItem(currentPosition).onActivityResult(requestCode, resultCode, data);
//            }
//        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
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

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
