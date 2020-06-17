package com.digitalcashbag.utilities.dmt.addmoney;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalcashbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.constants.BaseFragment;

public class AddMoneyContainer extends BaseFragment {

    @BindView(R.id.add_money_pager_header)
    PagerTabStrip add_money_pager_header;
    @BindView(R.id.add_money_pager)
    ViewPager add_money_pager;
    Unbinder unbinder;

    MyPagerAdapter adapterViewPager;
    Fragment currentFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_money, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        add_money_pager.setAdapter(adapterViewPager);
        currentFragment = adapterViewPager.getItem(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    class MyPagerAdapter extends FragmentPagerAdapter {
        Fragment addBeneficiary = new AddBeneficiary();
        Fragment beneficiaryList = new BeneficiaryList();
        //        Fragment transaction = new Transaction();
        private int NUM_ITEMS = 2;

        MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return addBeneficiary;
                case 1: // Fragment # 1 - This will show SecondFragment different title
                    return beneficiaryList;
//                case 2: // Fragment # 1 - This will show SecondFragment different title
//                    return transaction;
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.add_beneficiary);
                case 1:
                    return getString(R.string.beneficiary_list);
//                case 2:
//                    return "Transaction";
            }
            return "";
        }

    }

}
