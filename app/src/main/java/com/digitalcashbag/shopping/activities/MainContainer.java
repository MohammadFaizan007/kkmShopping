package com.digitalcashbag.shopping.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.digitalcashbag.DemoFragment;
import com.digitalcashbag.R;
import com.digitalcashbag.common_activities.FullScreenLogin;
import com.digitalcashbag.common_activities.LoginActivity;
import com.digitalcashbag.common_activities.SearchActivity;
import com.digitalcashbag.common_activities.SettingActivity;
import com.digitalcashbag.mlm.activities.MainContainerMLM;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.shopping.fragment.Dashboard;
import com.digitalcashbag.shopping.fragment.incentive_calculation.IncentiveCalculationFrag;
import com.digitalcashbag.shopping.fragment.myorders_all.MyOrders;
import com.digitalcashbag.utilities.billpayment.activity.ElectricityBillPayment;
import com.digitalcashbag.utilities.billpayment.activity.OtherBillPayment;
import com.digitalcashbag.utilities.dmt.DmtActivity;
import com.digitalcashbag.utilities.recharges.activities.AddMoney;
import com.digitalcashbag.utilities.recharges.activities.DthRecharge;
import com.digitalcashbag.utilities.recharges.activities.GiftCardCategories;
import com.digitalcashbag.utilities.recharges.activities.MobileRecharge;
import com.digitalcashbag.utilities.recharges.activities.MoneyTransfer;
import com.digitalcashbag.utilities.themepark.ThemePark;
import com.digitalcashbag.wallet.WalletNew;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.AppConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.retrofit.Dialog_dismiss;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class MainContainer extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener, Dialog_dismiss, IPickResult {

    private AppUpdater updater;
    public BottomNavigationView bottomNavigationView;
    public String bottomTitle = "";
    @BindView(R.id.notification_bell)
    ImageView notificationBell;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.title)
    TextView title_tv;
    @BindView(R.id.search_et)
    TextView search_et;
    @BindView(R.id.tv_noti_count)
    TextView tv_noti_count;
    @BindView(R.id.side_image)
    ImageView sideImage;
    @BindView(R.id.search_ll)
    LinearLayout searchll;
    private String from = "Dashboard";
    private Fragment setFragment;
    private Fragment currentFragment;
    private FirebaseAuth auth_user = FirebaseAuth.getInstance();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
                ReplaceFragmentWithHome(new Dashboard(), "home");
                return true;
            case R.id.navigation_business:
                bottomNavigationView.getMenu().setGroupCheckable(1, true, true);
                checkLogin(new DemoFragment(), "business");
                return true;
            case R.id.navigation_wallet:
                bottomNavigationView.getMenu().setGroupCheckable(2, true, true);
                checkLogin(new WalletNew(), "Bag");
                return true;
            case R.id.navigation_order:
                bottomNavigationView.getMenu().setGroupCheckable(3, true, true);
                checkLogin(new MyOrders(), "orders");
                return true;
            case R.id.navigation_news:
                bottomNavigationView.getMenu().setGroupCheckable(4, true, true);
                checkLogin(new IncentiveCalculationFrag(), "SAI");
                return true;
        }
        return false;
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_container);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PreferencesManager.getInstance(context).setfirst_visit(false);
        pref = getApplicationContext().getSharedPreferences(AppConfig.SHARED_PREF, 0);

        if (getIntent().getBundleExtra(PAYLOAD_BUNDLE) != null) {
            from = getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("from");
        }

        findIDs();

//        if (NetworkUtils.getConnectivityStatus(context) != 0) {
//            getShoppingOffers();
//        } else {
//            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
//        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        return true;
    }

    public void findIDs() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        switch (from) {
            case "Bag":
                bottomNavigationView.getMenu().findItem(R.id.navigation_wallet).setChecked(true);
                checkLogin(new WalletNew(), getResources().getString(R.string.title_wallet));
                break;
            case "orders":
                bottomNavigationView.getMenu().findItem(R.id.navigation_order).setChecked(true);
                checkLogin(new DemoFragment(), getResources().getString(R.string.title_payout));
                break;
            case "SAI":
                bottomNavigationView.getMenu().findItem(R.id.navigation_news).setChecked(true);
                checkLogin(new IncentiveCalculationFrag(), "SAI");
                break;
            default:
                bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
                ReplaceFragmentWithHome(new Dashboard(), getResources().getString(R.string.title_home));
                break;
        }
    }

    public void checkLogin(Fragment setFragment, String bottom_title) {
        bottomTitle = bottom_title;
        this.setFragment = setFragment;
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            if (PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                FullScreenLogin dialog = new FullScreenLogin();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                dialog.show(ft, FullScreenLogin.TAG);
            } else {
                switch (bottom_title) {
                    case "Bag":
                    case "orders":
                        ReplaceFragment(setFragment, bottom_title);
                        break;
                    case "SAI":
                        ReplaceFragment(setFragment, "Self Assessment LevelIncome");
                        break;
                    case "my profile":
                        goToActivity(MainContainer.this, ProfileActivity.class, null);
                        break;
                    case "business":
                        goToActivityWithFinish(MainContainerMLM.class, null);
                        break;
                    default:
                        // For business and other click option
                        goToActivityWithFinish(LoginActivity.class, null);
                        break;
                }
            }
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

    }

    @OnClick({R.id.notification_bell, R.id.search_et, R.id.side_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.notification_bell:
                goToActivity(context, NotificationList.class, null);
                break;
            case R.id.search_et:
                goToActivity(context, SearchActivity.class, null);
                break;
            case R.id.side_image:
                hideKeyboard();
                PopupMenu popup_sidemenu = new PopupMenu(context, view);
                popup_sidemenu.getMenuInflater().inflate(R.menu.menu_dashboard, popup_sidemenu.getMenu());
                if (PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                    popup_sidemenu.getMenu().findItem(R.id.action_login).setVisible(true);
                    popup_sidemenu.getMenu().findItem(R.id.action_logout).setVisible(false);
                    popup_sidemenu.getMenu().findItem(R.id.action_setting).setVisible(false);
                    popup_sidemenu.getMenu().findItem(R.id.action_refer).setVisible(false);
                } else {
                    popup_sidemenu.getMenu().findItem(R.id.action_refer).setVisible(true);
                    popup_sidemenu.getMenu().findItem(R.id.action_setting).setVisible(false);
                    popup_sidemenu.getMenu().findItem(R.id.action_login).setVisible(false);
                    popup_sidemenu.getMenu().findItem(R.id.action_logout).setVisible(true);
                }
                popup_sidemenu.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.action_profile:
                            checkLogin(new DemoFragment(), "my profile");
                            return true;
                        case R.id.action_logout:
                            logoutDialog(context, LoginActivity.class);
                            return true;
                        case R.id.action_refer:
                            goToActivity(context, ReferEarn.class, null);
                            return true;
                        case R.id.action_setting:
                            goToActivity(context, SettingActivity.class, null);
                            return true;
                        case R.id.action_login:
                            goToActivityWithFinish(LoginActivity.class, null);
                            return true;
                        default:
                            return false;
                    }
                });
                popup_sidemenu.show();
                break;
        }
    }


    public void logoutDialog(Context context, Class activity) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Logout");
        builder1.setMessage("Do you really want to logout?");
        builder1.setCancelable(true);

        builder1.setPositiveButton("Yes", (dialog, id) -> {
            getSignOut(activity);
            dialog.dismiss();
        });

        builder1.setNegativeButton("No", (dialog, id) -> dialog.dismiss());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void ReplaceFragmentWithHome(Fragment setFragment, String title) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, setFragment, title);
        getSupportActionBar().setTitle(Html.fromHtml("<small><b>" + title.toUpperCase() + "</b></small>"));
        searchll.setVisibility(View.VISIBLE);
        notificationBell.setVisibility(View.VISIBLE);
//        recycler.setVisibility(View.VISIBLE);
        sideImage.setVisibility(View.VISIBLE);
        title_tv.setVisibility(View.GONE);
        transaction.commit();
    }

    public void ReplaceFragment(Fragment setFragment, String title) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, setFragment, title);
        searchll.setVisibility(View.GONE);
        notificationBell.setVisibility(View.VISIBLE);
        title_tv.setText(Html.fromHtml("<medium><b>" + title.toUpperCase() + "</b></medium>"));
        title_tv.setVisibility(View.VISIBLE);
        sideImage.setVisibility(View.VISIBLE);
//        recycler.setVisibility(View.GONE);
        getSupportActionBar().setTitle(Html.fromHtml("<medium><b>" + title.toUpperCase() + "</b></medium>"));
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() < 1) {
            if (currentFragment instanceof Dashboard) {
                super.onBackPressed();
                finish();
//                moveTaskToBack(true);
            } else {
                ReplaceFragmentWithHome(new Dashboard(), "Dashboard");
                bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
            }
        } else {
            fm.popBackStack();
        }
    }

    @Override
    public void onDismiss() {
        LoggerUtil.logItem(bottomTitle);
        Bundle param = new Bundle();
        if (!PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
            switch (bottomTitle) {
                case "AEPS": {
                    ((Dashboard) currentFragment).openAEPSDialog();
                    break;
                }
                case "Broadband": {
                    param.putString("bill", Cons.BROADBAND_BILL_PAYMENT);
                    goToActivity(MainContainer.this, OtherBillPayment.class, param);
                    break;
                }
                case "Insurance Renewal": {
                    param.putString("bill", Cons.INSURANCE_BILL_PAYMENT);
                    goToActivity(MainContainer.this, OtherBillPayment.class, param);
                    break;
                }
                case "Electricity": {
                    param.putString("bill", Cons.ELECTRICITY_BILL_PAYMENT);
                    goToActivity(MainContainer.this, ElectricityBillPayment.class, param);
                    break;
                }
                case "Gas": {
                    param.putString("bill", Cons.GAS_BILL_PAYMENT);
                    goToActivity(MainContainer.this, OtherBillPayment.class, param);
                    break;
                }
                case "Water": {
                    param.putString("bill", Cons.WATER_BILL_PAYMENT);
                    goToActivity(MainContainer.this, OtherBillPayment.class, param);
                    break;
                }
                case "Mobile Postpaid": {
                    param.putString("mobile_recharge", getResources().getString(R.string.mobile_postpaid));
                    goToActivity(MainContainer.this, MobileRecharge.class, param);
                    break;
                }
                case "Mobile Prepaid": {
                    param.putString("mobile_recharge", getResources().getString(R.string.mob_prepaid));
                    goToActivity(MainContainer.this, MobileRecharge.class, param);
                    break;
                }
                case "DTH": {
                    param.putString("dth_rech", getResources().getString(R.string.dth_recharge));
                    goToActivity(MainContainer.this, DthRecharge.class, param);
                    break;
                }

                case "Bag":
                case "orders":
                case "SAI":
                    ReplaceFragment(setFragment, bottomTitle);
                    break;
                case "my profile":
                    goToActivity(MainContainer.this, ProfileActivity.class, null);
                    break;
                case "Refer Earn":
                    param.putString("from", "Refer Earn");
                    goToActivity(MainContainer.this, ReferEarn.class, param);
                    break;
                case "business":
                    goToActivityWithFinish(MainContainerMLM.class, null);
                    break;
                case "Theme Park":
                    goToActivity(MainContainer.this, ThemePark.class, null);
                    break;
                case "Add Money":
                    param.putString("from", getResources().getString(R.string.add_money));
                    goToActivity(MainContainer.this, AddMoney.class, param);
                    break;
                case "Money Transfer":
                    param.putString("from", getResources().getString(R.string.money_tgransfer));
                    goToActivity(MainContainer.this, MoneyTransfer.class, param);
                    break;
                case "DMT":
                    goToActivity(MainContainer.this, DmtActivity.class, null);
                    break;
                case "Gift Card":
                    param.putString("from", "Gift Card Categories");
                    goToActivity(MainContainer.this, GiftCardCategories.class, param);
                    break;
                case "Country Delight": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://enquiry.countrydelight.in/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Domino's Pizza": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "https://pizzaonline.dominos.co.in&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Insider": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "https://insider.in/bira-91-april-fools-fest/article&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Flipkart": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.flipkart.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Myntra": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.myntra.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "FirstCry": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "http://www.firstcry.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Zivame": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.zivame.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Amazon": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.amazon.in/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Shopclues": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "http://www.shopclues.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Railyatri": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.railyatri.in/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Oyo": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.oyorooms.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Goibibo": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "https://www.goibibo.com/flights/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Uber": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://m.uber.com/looking/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "MakeMyTrip": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.makemytrip.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Netmeds": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.netmeds.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "PharmEasy": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://pharmeasy.in/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Swiggy": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.swiggy.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "BookMyShow": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://in.bookmyshow.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "BigBasket": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "https://www.bigbasket.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Zomato": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.zomato.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Jabong": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.jabong.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "MOGLIX": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.moglix.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Lenskart": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.lenskart.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Bigrock": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.bigrock.in/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }

                case "Confirm TKT": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.confirmtkt.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Licious": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.licious.in/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Limeroad": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.limeroad.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }

                case "Nykaa": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.nykaa.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }

                case "New Insurance": {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.SALES_POINT + Utils.convertInto64(BuildConfig.SPO_ID));
                        ((MainContainer) context).goToActivity(context, NormalWebViewActivity.class, bundle);
                    } else {
                        createInfoDialog(context, "Alert", getString(R.string.alert_internet));
                    }
                    break;
                }
                case "Amazon Prime video": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.primevideo.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "KFC": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://online.kfc.co.in/home/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Grofers": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://grofers.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Cashbag Card": {
                    break;
                }
                case "Ola": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.olacabs.com//&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Zoomcar": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "https://www.zoomcar.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                case "Nnnow": {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "https://www.nnnow.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                    ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                    break;
                }
                default:
                    // For business and other click option
                    goToActivityWithFinish(LoginActivity.class, null);
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            checkUpdate();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

        if (!PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("0"))
            getNotificationCount();
    }

    private void checkUpdate() {
        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this).setUpdateFrom(UpdateFrom.GOOGLE_PLAY).
                withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
                        Log.d("Latest Version", update.getLatestVersion());
                        Log.d("Latest Version Code", "=" + update.getLatestVersionCode());
                        Log.d("Release notes", update.getReleaseNotes());
                        Log.d("URL", "=" + update.getUrlToDownload());
                        Log.d("Is update available?", Boolean.toString(isUpdateAvailable));
                        if (isUpdateAvailable) {
                            if (updater == null) {
                                updater = new AppUpdater(MainContainer.this).setDisplay(Display.DIALOG);
                                updater.setContentOnUpdateAvailable("Update " + update.getLatestVersion() + " is available to download. Download the latest version to get latest" + "features, improvements and bug fixes.");
                                updater.setButtonDoNotShowAgain("");
                                updater.setButtonDismiss(" ");
                                updater.setCancelable(false);
                                updater.start();
                            } else {
                                updater.start();
                            }
                        }
                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {
                        Log.d("AppUpdater Error", "Something went wrong");

                    }
                });
        appUpdaterUtils.start();
    }

//    private void getShoppingOffers() {
//        Call<ResponseShoppingOffers> offersCall = apiServices_shoopping.getShoppingOffer(new JsonObject());
//        offersCall.enqueue(new Callback<ResponseShoppingOffers>() {
//            @Override
//            public void onResponse(@NotNull Call<ResponseShoppingOffers> call, @NotNull Response<ResponseShoppingOffers> response) {
//                LoggerUtil.logItem(response.body());
//                Cons.shoppingoffersitem = new ArrayList<>();
//                try {
//                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
//                        Cons.shoppingoffersitem = new ArrayList<>();
//                        Cons.shoppingoffersitem = response.body().getShoppingOffers();
//
//                        DashboardBannerAdapter webBannerAdapter = new DashboardBannerAdapter(context, Cons.shoppingoffersitem);
//                        recyclerBanner.setAdapter(webBannerAdapter);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<ResponseShoppingOffers> call, @NotNull Throwable t) {
//                Cons.shoppingoffersitem = new ArrayList<>();
//            }
//        });
//
//    }

    private SharedPreferences pref;

    private void getSignOut(Class activity) {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Fk_MemId", PreferencesManager.getInstance(context).getUSERID());
        object.addProperty("AndroidId", PreferencesManager.getInstance(context).getANDROIDID());
        object.addProperty("DeviceId", pref.getString("regId", ""));

        LoggerUtil.logItem(object);

        Call<JsonObject> call = apiServices.getUserSignout(object);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                assert response.body() != null;
                LoggerUtil.logItem(response.body());
                if (response.body().get("response").getAsString().equalsIgnoreCase("Success")) {
                    boolean name = PreferencesManager.getInstance(context).getfirst_visit();
                    PreferencesManager.getInstance(context).clear();
                    PreferencesManager.getInstance(context).setfirst_visit(name);

                    Intent intent1 = new Intent(context, activity);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                    if (auth_user.getCurrentUser() != null) {
                        auth_user.signOut();
                    }
                    finish();
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

            }
        });
    }

//    void registerSPO() {
//        RequestSPORegistration requestSPORegistration = new RequestSPORegistration();
//        requestSPORegistration.setUserType("0");
//        requestSPORegistration.setMobile(PreferencesManager.getInstance(context).getMOBILE());
//        requestSPORegistration.setPassword(PreferencesManager.getInstance(context).getSavedPASSWORD());
//        requestSPORegistration.setName(PreferencesManager.getInstance(context).getNAME());
//        requestSPORegistration.setEmail(PreferencesManager.getInstance(context).getEMAIL());
//        requestSPORegistration.setPinCode(PreferencesManager.getInstance(context).getPINCODE());
//        SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
//        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date;
//        try {
//            date = originalFormat.parse(PreferencesManager.getInstance(context).getDOB());
//            System.out.println("Old Format :   " + originalFormat.format(date));
//            System.out.println("New Format :   " + targetFormat.format(date));
//            requestSPORegistration.setDateOfBirth(targetFormat.format(date));//06/05/1987
//        } catch (ParseException ex) {
//            ex.printStackTrace();
//        }
//        requestSPORegistration.setReferralCode("ADCA101138");// For Demo
//        requestSPORegistration.setSource("CashBag");
//        LoggerUtil.logItem(requestSPORegistration);
//        showLoading();
//        Call<JsonObject> call = apiServicesSalesPoint.getRequestSpoRegistrationCall(requestSPORegistration, "#$SPO!@#!7@86#7#6@");
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
//                hideLoading();
//                Log.e("Profile View==========", "");
//                LoggerUtil.logItem(response.body());
//
////                {
////                    "data": {
////                    "error": true,
////                            "message": {
////                        "Mobile": [
////                        "The mobile has already been taken."
////                                  ],
////                        "Email": [
////                        "The email has already been taken."
////                                  ],
////                        "DateOfBirth": [
////                        "The date of birth does not match the format Y-m-d."
////                                      ],
////                        "referralCode": [
////                        "The selected referral code is invalid."
////                                          ]
////                    },
////                    "errorDetails": []
////                }
////                }
////  =============================================================================================================
//                try {
//                    if (response.body() != null && response.body()
//                            .getAsJsonObject("data").get("error").getAsBoolean()) {
//                        Log.e("Error", " in registration------- ");
//
//                        showAlert("Error :\n" + response.body().getAsJsonObject("data")
//                                        .getAsJsonObject("message").getAsJsonArray("Mobile").getAsString() + "\n"
//                                        + response.body().getAsJsonObject("data")
//                                        .getAsJsonObject("message").getAsJsonArray("Email").getAsString()
//                                , R.color.red, R.drawable.error);
//                    } else {
//                        Log.e("Success", " in registration-------\n " +
//                                response.body().getAsJsonObject("data").get("user").getAsString() + "\n" +
//                                response.body().getAsJsonObject("data").get("username").getAsString());
//
//                        PreferencesManager.getInstance(context).setSPOID(response.body().getAsJsonObject("data").get("user").getAsString());
//                        PreferencesManager.getInstance(context).setSPO_USERNAME(response.body().getAsJsonObject("data").get("username").getAsString());
//
////                        {"data":{"error":false,"message":"User Registered Successfully.","errorDetails":[],"user":41913,"username":"ADCD101227"}}
//
//                        Bundle bundle = new Bundle();
//                        bundle.putString("link", "https://www.salespointonline.com/login");
//                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
//                hideLoading();
//                LoggerUtil.logItem(t.getMessage());
//                LoggerUtil.logItem(t.toString());
//            }
//        });
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        currentFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        ((MyOrders) currentFragment).onPickResult(pickResult);
    }

    private void getNotificationCount() {
        String url = BuildConfig.BASE_URL_MLM + "Notificationcount?Fk_memID=" + PreferencesManager.getInstance(context).getUSERID();
        LoggerUtil.logItem(url);

        Call<JsonObject> call = apiServices.getNotificationCount(url);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                LoggerUtil.logItem(response.body());
                assert response.body() != null;
                if (response.body().get("response").getAsString().equalsIgnoreCase("Success")) {
                    if (response.body().get("totalNotification").getAsString().equalsIgnoreCase("0"))
                        tv_noti_count.setVisibility(View.GONE);
                    else {
                        tv_noti_count.setVisibility(View.VISIBLE);
                        tv_noti_count.setText(response.body().get("totalNotification").getAsString());
                    }
                } else {
                    tv_noti_count.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}
