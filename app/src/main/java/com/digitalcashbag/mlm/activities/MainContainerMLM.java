package com.digitalcashbag.mlm.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.digitalcashbag.mlm.fragments.ChangePassword;
import com.digitalcashbag.mlm.fragments.DashboardMLM;
import com.digitalcashbag.mlm.fragments.ProfileUpdateRequest;
import com.digitalcashbag.mlm.fragments.SupportMLM;
import com.digitalcashbag.mlm.fragments.profile.EditProfileMlm;
import com.digitalcashbag.mlm.fragments.profile.ViewProfile;
import com.digitalcashbag.mlm.fragments.team.DirectMember;
import com.digitalcashbag.mlm.fragments.team.Downline;
import com.digitalcashbag.mlm.fragments.team.TeamStatus;
import com.digitalcashbag.mlm.fragments.ticketview.MyTrainings;
import com.digitalcashbag.mlm.fragments.wallet.E_WalletRequest;
import com.digitalcashbag.mlm.fragments.wallet.IncentiveLedger;
import com.digitalcashbag.mlm.fragments.wallet.WalletLedger;
import com.digitalcashbag.mlm.fragments.wallet.WalletRequest;
import com.digitalcashbag.shopping.activities.MainContainer;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.utils.LoggerUtil;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;


public class MainContainerMLM extends BaseActivity implements View.OnClickListener, IPickResult {

    private int drawable;
    private Fragment currentFragment;
    private TextView profile_name, selectedText, dashboard, my_training, open_shopping, profile, my_wallet, wallet_request, wallet_ledger, incentive_ledger, my_team, direct_member, downline, teamstatus, my_income, payout_report,
            matching_bonus, change_pass, support, edit_details, logout;
    private LinearLayout my_wallet_lo, my_team_lo, my_income_lo;
    private DrawerLayout drawer;
    private ImageView dash_profile_iv, dp_background;
    private LinearLayout visibleLayout = null;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_container_mlm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        drawer = findViewById(R.id.drawer_layout);
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        Fragment dashboardMLM = new DashboardMLM();
        ReplaceFragment(dashboardMLM, "Dashboard");
        findIDs(hView);

        disableNavigationViewScrollbars(navigationView);

    }

    public void findIDs(View view) {

        profile_name = view.findViewById(R.id.profile_name);
        dash_profile_iv = view.findViewById(R.id.dash_profile_iv);
        dashboard = view.findViewById(R.id.dashboard);
        my_training = view.findViewById(R.id.my_training);
        profile = view.findViewById(R.id.profile);
        my_wallet = view.findViewById(R.id.my_wallet);
        wallet_request = view.findViewById(R.id.ewallet_request);
        wallet_ledger = view.findViewById(R.id.wallet_ledger);
        incentive_ledger = view.findViewById(R.id.incentive_ledger);
        my_team = view.findViewById(R.id.my_team);
        direct_member = view.findViewById(R.id.direct_member);
        teamstatus = view.findViewById(R.id.teamstatus);
        downline = view.findViewById(R.id.downline);
        my_income = view.findViewById(R.id.my_income);
        payout_report = view.findViewById(R.id.payout_report);
        matching_bonus = view.findViewById(R.id.matching_bonus);
        change_pass = view.findViewById(R.id.change_pass);
        support = view.findViewById(R.id.support);
        logout = view.findViewById(R.id.logout);
        edit_details = view.findViewById(R.id.edit_details);
        my_wallet_lo = view.findViewById(R.id.my_wallet_lo);
        my_team_lo = view.findViewById(R.id.my_team_lo);
        my_income_lo = view.findViewById(R.id.my_income_lo);
        open_shopping = view.findViewById(R.id.open_shopping);
        dp_background = view.findViewById(R.id.dp_background);

        dashboard.setOnClickListener(this);
        profile.setOnClickListener(this);
        my_training.setOnClickListener(this);
        wallet_request.setOnClickListener(this);
        wallet_ledger.setOnClickListener(this);
        incentive_ledger.setOnClickListener(this);
        direct_member.setOnClickListener(this);
        downline.setOnClickListener(this);
        teamstatus.setOnClickListener(this);
        payout_report.setOnClickListener(this);
        matching_bonus.setOnClickListener(this);
        change_pass.setOnClickListener(this);
        support.setOnClickListener(this);
        open_shopping.setOnClickListener(this);
        logout.setOnClickListener(this);
        edit_details.setOnClickListener(this);

        LoggerUtil.logItem(PreferencesManager.getInstance(context).getNAME());
        profile_name.setText(String.format("%s %s", PreferencesManager.getInstance(context).getNAME(), PreferencesManager.getInstance(context).getLNAME()));
        Glide.with(context).load(PreferencesManager.getInstance(context).getPROFILEPIC())
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round))
                .into(dash_profile_iv);

        Glide.with(context).load(BuildConfig.BASE_URL_ICONS + "new_headnavbg.png")
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC))
                .into(dp_background);


        my_team.setOnClickListener(v -> {
            hideOpenedLayout(my_team_lo, my_team, R.drawable.new_team);
            if (my_team_lo.getVisibility() == View.GONE) {
                my_team_lo.setVisibility(View.VISIBLE);
                my_team.setCompoundDrawablesWithIntrinsicBounds(R.drawable.new_team, 0, R.drawable.down_w, 0);
            } else {
                my_team_lo.setVisibility(View.GONE);
                my_team.setCompoundDrawablesWithIntrinsicBounds(R.drawable.new_team, 0, R.drawable.right_w, 0);
            }

        });

        my_wallet.setOnClickListener(v -> {
            hideOpenedLayout(my_wallet_lo, my_wallet, R.drawable.new_mywallet);
            if (my_wallet_lo.getVisibility() == View.GONE) {
                my_wallet_lo.setVisibility(View.VISIBLE);
                my_wallet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.new_mywallet, 0, R.drawable.down_w, 0);
            } else {
                my_wallet_lo.setVisibility(View.GONE);
                my_wallet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.new_mywallet, 0, R.drawable.right_w, 0);
            }

        });

        my_income.setOnClickListener(v -> {
            hideOpenedLayout(my_income_lo, my_income, R.drawable.new_myincentive);
            drawer.closeDrawers();
        });

    }

    private void hideOpenedLayout(LinearLayout ll, TextView tv, int drawable) {
        if (visibleLayout != null && visibleLayout != ll) {
            visibleLayout.setVisibility(View.GONE);
            selectedText.setCompoundDrawablesWithIntrinsicBounds(this.drawable, 0, R.drawable.right_w, 0);
        }
        visibleLayout = ll;
        selectedText = tv;
        this.drawable = drawable;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dashboard:
                drawer.closeDrawers();
                ReplaceFragment(new DashboardMLM(), "Dashboard");
                break;
            case R.id.my_training:
                drawer.closeDrawers();
                goToActivity(context, MyTrainings.class, null);
                break;
            case R.id.open_shopping:
                drawer.closeDrawers();
                goToActivityWithFinish(MainContainer.class, null);
                break;
            case R.id.profile:
                drawer.closeDrawers();
                ReplaceFragment(new ViewProfile(), "Profile");
                break;
            case R.id.ewallet_request:
                drawer.closeDrawers();
                ReplaceFragmentaddtoback(new WalletRequest(), "Bag Request");
                break;
            case R.id.incentive_ledger:
                drawer.closeDrawers();
                ReplaceFragment(new IncentiveLedger(), "LevelIncome Ledger");
                break;
            case R.id.wallet_ledger:
                drawer.closeDrawers();
                ReplaceFragment(new WalletLedger(), "Bag Ledger");
                break;
            case R.id.direct_member:
                drawer.closeDrawers();
                ReplaceFragment(new DirectMember(), "Direct Member");
                break;
            case R.id.downline:
                drawer.closeDrawers();
                ReplaceFragment(new Downline(), "Downline");
                break;
            case R.id.teamstatus:
                drawer.closeDrawers();
                ReplaceFragmentadd(new TeamStatus(), "Team Status");
                break;
            case R.id.payout_report:
                drawer.closeDrawers();
                ReplaceFragment(new DashboardMLM(), "Payout Report");
                break;
            case R.id.matching_bonus:
                drawer.closeDrawers();
                ReplaceFragment(new DashboardMLM(), "Matching Bonus");
                break;
            case R.id.change_pass:
                drawer.closeDrawers();
                ReplaceFragment(new ChangePassword(), "Change Password");
                break;
            case R.id.support:
                drawer.closeDrawers();
                ReplaceFragment(new SupportMLM(), "Support");
                break;
            case R.id.edit_details:
                drawer.closeDrawers();
                ReplaceFragment(new ProfileUpdateRequest(), "Update Details Request");
                break;
            case R.id.logout:
                drawer.closeDrawers();
//                logoutDialog(context, LoginActivity.class);
                break;
        }
    }

    public void ReplaceFragment(Fragment setFragment, String titleStr) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mlm_frame, setFragment, titleStr);
        getSupportActionBar().setTitle(Html.fromHtml("<small><b>" + titleStr.toUpperCase() + "</b></small>"));
        transaction.commit();
    }

    public void ReplaceFragmentaddtoback(Fragment setFragment, String titleStr) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction transaction = fragmentManager.beginTransaction().addToBackStack(null);
        transaction.replace(R.id.mlm_frame, setFragment, titleStr);
        getSupportActionBar().setTitle(Html.fromHtml("<small><b>" + titleStr.toUpperCase() + "</b></small>"));
        transaction.commit();
    }

    public void ReplaceFragmentadd(Fragment setFragment, String titleStr) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().addToBackStack(null);
        transaction.replace(R.id.mlm_frame, setFragment, titleStr);
        getSupportActionBar().setTitle(Html.fromHtml("<small><b>" + titleStr.toUpperCase() + "</b></small>"));
        transaction.commit();
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    public void addFragment(Fragment setFragment, String title) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        setFragment.setArguments(param);
        transaction.replace(R.id.mlm_frame, setFragment, title).addToBackStack(null).commit();
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if (currentFragment instanceof E_WalletRequest)
            ((E_WalletRequest) currentFragment).onPickResult(pickResult);
        else if (currentFragment instanceof EditProfileMlm)
            ((EditProfileMlm) currentFragment).onPickResult(pickResult);
        else if (currentFragment instanceof SupportMLM)
            ((SupportMLM) currentFragment).onPickResult(pickResult);
        else if (currentFragment instanceof ProfileUpdateRequest)
            ((ProfileUpdateRequest) currentFragment).onPickResult(pickResult);
    }

    @Override
    public void onBackPressed() {
        try {
            FragmentManager fm = getSupportFragmentManager();
            Fragment currentFragment = fm.findFragmentById(R.id.mlm_frame);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else if (fm.getBackStackEntryCount() <= 1) {
                LoggerUtil.logItem(currentFragment.getTag());
                if (currentFragment.getTag().equals("Dashboard")) {
                    goToActivityWithFinish(MainContainer.class, null);
                } else if (currentFragment.getTag().equalsIgnoreCase("Team Status")) {
                    fm.popBackStack();
                } else {
                    ReplaceFragment(new DashboardMLM(), "Dashboard");
                }
            } else {
                fm.popBackStack();
            }
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        currentFragment.onActivityResult(requestCode, resultCode, data);

    }

}
