package com.digitalcashbag.shopping.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.digitalcashbag.common_activities.FullScreenLogin;
import com.digitalcashbag.m2p.scanPay.ScanActivity;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.shopping.activities.MainContainer;
import com.digitalcashbag.shopping.activities.NormalWebViewActivity;
import com.digitalcashbag.shopping.activities.ReferEarn;
import com.digitalcashbag.shopping.activities.ShoppingDetailItemActivity;
import com.digitalcashbag.shopping.activities.ShoppingSiteActivity;
import com.digitalcashbag.shopping.activities.WebViewActivity;
import com.digitalcashbag.shopping.adapter.DashboardBannerAdapter;
import com.digitalcashbag.shopping.adapter.DashboardGridAdapter;
import com.digitalcashbag.shopping.adapter.DashboardGridShoppingOffersAdapter;
import com.digitalcashbag.utilities.billpayment.activity.ElectricityBillPayment;
import com.digitalcashbag.utilities.billpayment.activity.OtherBillPayment;
import com.digitalcashbag.utilities.dmt.DmtActivity;
import com.digitalcashbag.utilities.recharges.activities.AddMoney;
import com.digitalcashbag.utilities.recharges.activities.DthRecharge;
import com.digitalcashbag.utilities.recharges.activities.GiftCardCategories;
import com.digitalcashbag.utilities.recharges.activities.ItemOffsetDecoration;
import com.digitalcashbag.utilities.recharges.activities.MobileRecharge;
import com.digitalcashbag.utilities.themepark.ThemePark;
import com.example.library.banner.BannerLayout;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.response.HomePageItem;
import kkm.com.core.model.response.ResponseCategory;
import kkm.com.core.model.response.shopping.ResponseShoppingOffers;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Dashboard extends BaseFragment implements MvpView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    Bundle param = new Bundle();

    String[] gridViewString = {"Mobile Postpaid",
            "Mobile Prepaid", "Electricity",
            "Gas", "Water", "Insurance Renewal",
            "Broadband", "DTH", /*"DMT",*/
            "Gift Card", "Theme Park", "AEPS"};
    String[] gridViewImageId = {BuildConfig.BASE_URL_ICONS + "mobile_postpaid.png",
            BuildConfig.BASE_URL_ICONS + "mobile_prepaid.png",
            BuildConfig.BASE_URL_ICONS + "electricity.png", BuildConfig.BASE_URL_ICONS + "gas_bill.png",
            BuildConfig.BASE_URL_ICONS + "water_bill.png",
            BuildConfig.BASE_URL_ICONS + "insurance.png",
            BuildConfig.BASE_URL_ICONS + "broad_band.png", BuildConfig.BASE_URL_ICONS + "dth.png",
            /*BuildConfig.BASE_URL_ICONS + "dmt.png",*/ BuildConfig.BASE_URL_ICONS + "coupan.png",
            BuildConfig.BASE_URL_ICONS + "theme_park.png", BuildConfig.BASE_URL_ICONS + "aeps.png"};

    private String[] gridViewStringUtilities = {/*"Movie Tickets",*/ "Netmeds", "PharmEasy",
            "Swiggy", "BookMyShow", "BigBasket",/* "Zomato",*/ "Bigrock",
            "Amazon Prime video", "Domino's Pizza", "Country Delight", "KFC", "Nnnow", "Nykaa", "Fernsnpetals"
            , "New Insurance"};
    String[] gridViewImageIdUtilities = {/*BuildConfig.BASE_URL_ICONS + "movie_tickets.png",
    */ BuildConfig.BASE_URL_ICONS + "netmeds.png",
            BuildConfig.BASE_URL_ICONS + "pharmaeasy.png", BuildConfig.BASE_URL_ICONS +
            "swiggy.png", BuildConfig.BASE_URL_ICONS + "bookmyshow.png", BuildConfig.BASE_URL_ICONS + "bigbaket.png",
            /*BuildConfig.BASE_URL_ICONS + "zomato.png",*/ BuildConfig.BASE_URL_ICONS +
            "big_rock.png", BuildConfig.BASE_URL_ICONS + "amazon_prime_video.png",
            BuildConfig.BASE_URL_ICONS + "dominos.png", BuildConfig.BASE_URL_ICONS +
            "delight.png", BuildConfig.BASE_URL_ICONS + "kfc.png", BuildConfig.BASE_URL_ICONS + "nnnow.png"
            , BuildConfig.BASE_URL_ICONS + "nykaa.png", BuildConfig.BASE_URL_ICONS +
            "fnp.png", BuildConfig.BASE_URL_ICONS + "pb.png"};

    private String[] gridViewStringTravel = {/*"Train",*/ /*"Metro",*/ /*"Bus",*/
            /*"Railyatri", */"Oyo", "Goibibo", /*"Uber",*/ "MakeMyTrip"
            , /*"Ola",*/ "Zoomcar", "Cleartrip", "Confirm TKT"};

    private String[] gridViewImageIdTravel = {/*BuildConfig.BASE_URL_ICONS + "train.png",
    BuildConfig.BASE_URL_ICONS + "metro.png",*/ /*BuildConfig.BASE_URL_ICONS + "bus.png",*/
            /*BuildConfig.BASE_URL_ICONS + "rail_yatri.png",*/ BuildConfig.BASE_URL_ICONS +
            "oyo.png", BuildConfig.BASE_URL_ICONS + "goibibo.png", /*BuildConfig.BASE_URL_ICONS +
            "uber.png",*/ BuildConfig.BASE_URL_ICONS + "makemytrip.png",
            /*BuildConfig.BASE_URL_ICONS + "ola.png",*/
            BuildConfig.BASE_URL_ICONS + "zoom_car.png", BuildConfig.BASE_URL_ICONS + "cleartrip.png"
            , BuildConfig.BASE_URL_ICONS + "confirmtkt.png"};

    private String[] gridViewStringShopping = {"Flipkart", "Myntra",
            "FirstCry", "Zivame", "Amazon", "Shopclues", /*"Jabong",*/ "MOGLIX", "Lenskart",
            "Insider", "Ajio", "FabIndia", "Licious", "Limeroad", "AliExpress"};
    private String[] gridViewImageIdShopping = {BuildConfig.BASE_URL_ICONS + "flipkart.png",
            BuildConfig.BASE_URL_ICONS + "myntra.png", BuildConfig.BASE_URL_ICONS + "fc.png",
            BuildConfig.BASE_URL_ICONS + "zivame.png", BuildConfig.BASE_URL_ICONS + "amazon.png",
            BuildConfig.BASE_URL_ICONS + "shopclues.png",
            /*BuildConfig.BASE_URL_ICONS + "jabong.png",*/ BuildConfig.BASE_URL_ICONS + "moglix.png",
            BuildConfig.BASE_URL_ICONS + "lenskart.png", BuildConfig.BASE_URL_ICONS + "insider.png"
            , BuildConfig.BASE_URL_ICONS + "ajio.png", BuildConfig.BASE_URL_ICONS + "fabindia.png"
            , BuildConfig.BASE_URL_ICONS + "licious.png"
            , BuildConfig.BASE_URL_ICONS + "limeroad.png", BuildConfig.BASE_URL_ICONS + "ali_express.png"};
    private List<HomePageItem> itemList;

    @BindView(R.id.gv)
    RecyclerView gvshoppingoffers;
    Unbinder unbinder;
    RecyclerView androidGridView;
    @BindView(R.id.add_money_lo)
    LinearLayout addMoneyLo;
    @BindView(R.id.medicine_lo)
    LinearLayout medicineLo;
    @BindView(R.id.travel_lo)
    LinearLayout travelLo;
    @BindView(R.id.welcome_userTxt)
    TextView welcomeUserTxt;
    @BindView(R.id.travel)
    TextView travel;
    @BindView(R.id.utilities)
    TextView utilities;
    @BindView(R.id.mainScroll)
    NestedScrollView mainScroll;
    @BindView(R.id.img_bday)
    ImageView imgBday;
    @BindView(R.id.tv_bday_msg)
    TextView tvBdayMsg;
    @BindView(R.id.recycler)
    BannerLayout recyclerBanner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getShoppingOffers();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
        return view;
    }


    private boolean checkForDob(String dob) {
        try {
            LoggerUtil.logItem(dob);
            if (!dob.equalsIgnoreCase("")) {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.ENGLISH);
                Date date1 = dateFormat.parse(Utils.getTodayDate());
                Date date2 = dateFormat.parse(Utils.changeDateFormat(dob));
                LoggerUtil.logItem(date1);
                LoggerUtil.logItem(date2);
                if (date1.equals(date2)) {
                    LoggerUtil.logItem("SAME");
                    return true;
                } else if (date1.before(date2)) {
                    LoggerUtil.logItem("BEFORE");
                    return false;
                } else if (date1.after(date2)) {
                    LoggerUtil.logItem("AFTER");
                    return false;
                }
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void getShoppingOffers() {
        Call<ResponseShoppingOffers> offersCall = apiServices_shoopping.getShoppingOffer(new JsonObject());
        offersCall.enqueue(new Callback<ResponseShoppingOffers>() {
            @Override
            public void onResponse(@NotNull Call<ResponseShoppingOffers> call, @NotNull Response<ResponseShoppingOffers> response) {
                LoggerUtil.logItem(response.body());
                Cons.shoppingoffersitem = new ArrayList<>();
                try {
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                        Cons.shoppingoffersitem = new ArrayList<>();
                        Cons.shoppingoffersitem = response.body().getShoppingOffers();

                        DashboardBannerAdapter webBannerAdapter = new DashboardBannerAdapter(context, Cons.shoppingoffersitem);
                        recyclerBanner.setAdapter(webBannerAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseShoppingOffers> call, @NotNull Throwable t) {
                Cons.shoppingoffersitem = new ArrayList<>();
            }
        });

    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        mainScroll.setSmoothScrollingEnabled(true);

        DashboardGridAdapter adapterViewAndroid = new DashboardGridAdapter(getActivity(), gridViewString, gridViewImageId, this);
        androidGridView = view.findViewById(R.id.grid_view_image_text);
        androidGridView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setHasFixedSize(true);

        DashboardGridAdapter adapterViewAndroidMore = new DashboardGridAdapter(getActivity(), gridViewStringShopping, gridViewImageIdShopping, this);
        RecyclerView gridViewMoreSec = view.findViewById(R.id.grid_view_more_sec);
        gridViewMoreSec.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        gridViewMoreSec.setAdapter(adapterViewAndroidMore);
        gridViewMoreSec.setHasFixedSize(true);

        DashboardGridAdapter adapterViewAndroidTravel = new DashboardGridAdapter(getActivity(), gridViewStringTravel, gridViewImageIdTravel, this);
        RecyclerView grid_view_travel = view.findViewById(R.id.grid_view_travel);
        grid_view_travel.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        grid_view_travel.setAdapter(adapterViewAndroidTravel);
        grid_view_travel.setHasFixedSize(true);

        DashboardGridAdapter adapterViewAndroidUtility = new DashboardGridAdapter(getActivity(), gridViewStringUtilities, gridViewImageIdUtilities, this);
        RecyclerView grid_view_utility = view.findViewById(R.id.grid_view_utility);
        grid_view_utility.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        grid_view_utility.setAdapter(adapterViewAndroidUtility);
        grid_view_utility.setHasFixedSize(true);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.item_offset);
        gvshoppingoffers.addItemDecoration(itemDecoration);
        gvshoppingoffers.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        gvshoppingoffers.setLayoutManager(manager);

        Log.e("VIEW CREATED=== ", " ====== ");
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getShoppingOffer();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

    }


    public void updateName() {
        if (!PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
            welcomeUserTxt.setVisibility(View.VISIBLE);
            welcomeUserTxt.setText(Html.fromHtml("<font color=\"#434343\">Welcome  </font><font color=\"#F1B231\"> <b>" + PreferencesManager.getInstance(context).getNAME() + "</b></font>"));
        } else {
            welcomeUserTxt.setVisibility(View.GONE);
        }

        if (!PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("") &&
                checkForDob(PreferencesManager.getInstance(context).getDOB())) {
            LoggerUtil.logItem("DASHBOARD");
            imgBday.setVisibility(View.VISIBLE);
            tvBdayMsg.setVisibility(View.VISIBLE);
            tvBdayMsg.setText(String.format("Happy Birthday \n%s", PreferencesManager.getInstance(context).getNAME()));
            Glide.with(context).load(PreferencesManager.getInstance(context).getDOB_IMAGE())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.image_not_available).error(R.drawable.image_not_available))
                    .into(imgBday);
        } else {
            imgBday.setVisibility(View.GONE);
            tvBdayMsg.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LoggerUtil.logItem("RESUME");
        updateName();
    }


    @Override
    public void getClickPosition(int position, String tag) {
        param = new Bundle();
        switch (tag) {
            case "Confirm TKT": {
                checkLogin("Confirm TKT");
                break;
            }
            case "Licious": {
                checkLogin("Licious");
                break;
            }
            case "Limeroad": {
                checkLogin("Limeroad");
                break;
            }
            case "AliExpress": {
                checkLogin("AliExpress");
                break;
            }
            case "New Insurance": {
                checkLogin("New Insurance");
                break;
            }
            case "Nykaa": {
                checkLogin("Nykaa");
                break;
            }
            case "Fernsnpetals": {
                checkLogin("Fernsnpetals");
                break;
            }
            case "Cleartrip": {
                checkLogin("Cleartrip");
                break;
            }
            case "Ajio": {
                checkLogin("Ajio");
                break;
            }
            case "FabIndia": {
                checkLogin("FabIndia");
                break;
            }
            case "Theme Park": {
                param.putString("from", getResources().getString(R.string.theme_park));
                checkLogin("Theme Park");
                break;
            }
            case "AEPS": {
                param.putString("from", "AEPS");
                checkLogin("AEPS");
                break;
            }
            case "Gift Card": {
                checkLogin("Gift Card");
                break;
            }
            case "Grofers": {
                checkLogin("Grofers");
                break;
            }
            case "KFC": {
                checkLogin("KFC");
                break;
            }
            case "Country Delight": {
                checkLogin("Country Delight");
                break;
            }
            case "Domino's Pizza": {
                checkLogin("Domino's Pizza");
                break;
            }
            case "Insider": {
                checkLogin("Insider");
                break;
            }
            case "Flipkart": {
                checkLogin("Flipkart");
                break;
            }
            case "Myntra": {
                checkLogin("Myntra");
                break;
            }
            case "FirstCry": {
                checkLogin("FirstCry");
                break;
            }
            case "Zivame": {
                checkLogin("Zivame");
                break;
            }
            case "Amazon": {
                checkLogin("Amazon");
                break;
            }
            case "Shopclues": {
                checkLogin("Shopclues");
                break;
            }
            case "Railyatri": {
                checkLogin("Railyatri");
                break;
            }
            case "Oyo": {
                checkLogin("Oyo");
                break;
            }
            case "Goibibo": {
                checkLogin("Goibibo");
                break;
            }
            case "Uber": {
                checkLogin("Uber");
                break;
            }
            case "MakeMyTrip": {
                checkLogin("MakeMyTrip");
                break;
            }
            case "Ola": {
                checkLogin("Ola");
                break;
            }
            case "Zoomcar": {
                checkLogin("Zoomcar");
                break;
            }
            case "Nnnow": {
                checkLogin("Nnnow");
                break;
            }
            case "Netmeds": {
                checkLogin("Netmeds");
                break;
            }
            case "PharmEasy": {
                checkLogin("PharmEasy");
                break;
            }
            case "Swiggy": {
                checkLogin("Swiggy");
                break;
            }
            case "BookMyShow": {
                checkLogin("BookMyShow");
                break;
            }
            case "BigBasket": {
                checkLogin("BigBasket");
                break;
            }
            case "Zomato": {
                checkLogin("Zomato");
                break;
            }
            case "Jabong": {
                checkLogin("Jabong");
                break;
            }
            case "MOGLIX": {
                checkLogin("MOGLIX");
                break;
            }
            case "Lenskart": {
                checkLogin("Lenskart");
                break;
            }
            case "Bigrock": {
                checkLogin("Bigrock");
                break;
            }
            case "Amazon Prime video": {
                checkLogin("Amazon Prime video");
                break;
            }
            case "Mobile Postpaid": {
                checkLogin("Mobile Postpaid");
                break;
            }
            case "Mobile Prepaid": {
                checkLogin("Mobile Prepaid");
                break;
            }
            case "DTH": {
                checkLogin("DTH");
                break;
            }
            case "Electricity": {
                checkLogin("Electricity");
                break;
            }
            case "Gas": {
                checkLogin("Gas");
                break;
            }
            case "Water": {
                checkLogin("Water");
                break;
            }
            case "Insurance Renewal": {
                checkLogin("Insurance Renewal");
                break;
            }
            case "DMT": {
                dmtDialog(context);
                break;
            }
            case "Broadband": {
                checkLogin("Broadband");
                break;
            }
        }
    }

    public void dmtDialog(Context context) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("DMT");
        builder1.setMessage("Do you really want to DMT?");
        builder1.setCancelable(true);

        builder1.setPositiveButton("Yes", (dialog, id) -> {
            checkLogin("DMT");

        });

        builder1.setNegativeButton("No", (dialog, id) -> dialog.dismiss());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void showItems(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("productId", itemList.get(position).getProductId());
        goToActivity(ShoppingDetailItemActivity.class, bundle);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getShoppingOffer() {
        showLoading();
        JsonObject object = new JsonObject();
        LoggerUtil.logItem(object);
        Call<ResponseCategory> responseCategoryCall = apiServices_shoopping.getHomePageAPI(object);
        responseCategoryCall.enqueue(new Callback<ResponseCategory>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCategory> call, @NonNull Response<ResponseCategory> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success") &&
                            response.body().getHomePage().size() > 0) {
                        itemList = response.body().getHomePage();
                        DashboardGridShoppingOffersAdapter dashboardGridShoppingOffersAdapter = new DashboardGridShoppingOffersAdapter(context, itemList, Dashboard.this);
                        if (gvshoppingoffers != null)
                            gvshoppingoffers.setAdapter(dashboardGridShoppingOffersAdapter);
                    } else {
                        Toast.makeText(context, "No Record Found", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCategory> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    @OnClick({R.id.refer_earn_lo, R.id.add_money_lo, R.id.utility_lo, R.id.medicine_lo, R.id.travel_lo, R.id.cashbag_card_lo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.refer_earn_lo:
                checkLogin("Refer Earn");
                break;
            case R.id.add_money_lo:
                checkLogin("Add Money");
                break;
            case R.id.utility_lo:
                mainScroll.smoothScrollTo((int) utilities.getX(), (int) utilities.getY());
                break;
            case R.id.medicine_lo:
                param.putString("shopping", "Medicines");
                goToActivity(ShoppingSiteActivity.class, param);
                break;
            case R.id.travel_lo:
                mainScroll.smoothScrollTo((int) travel.getX(), (int) travel.getY());
                break;
            case R.id.cashbag_card_lo:
                checkLogin("Cashbag Card");
                break;
        }
    }

    void checkLogin(String bottom_title) {
        ((MainContainer) Objects.requireNonNull(getActivity())).bottomTitle = bottom_title;
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            if (PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                FullScreenLogin dialog = new FullScreenLogin();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                dialog.show(ft, FullScreenLogin.TAG);
            } else {
                switch (bottom_title) {
                    case "AEPS": {
                        openAEPSDialog();
                        break;
                    }
                    case "Broadband": {
                        param.putString("bill", Cons.BROADBAND_BILL_PAYMENT);
                        goToActivity(OtherBillPayment.class, param);
                        break;
                    }
                    case "Insurance Renewal": {
                        param.putString("bill", Cons.INSURANCE_BILL_PAYMENT);
                        goToActivity(OtherBillPayment.class, param);
                        break;
                    }
                    case "Electricity": {
                        param.putString("bill", Cons.ELECTRICITY_BILL_PAYMENT);
                        goToActivity(ElectricityBillPayment.class, param);
                        break;
                    }
                    case "Gas": {
                        param.putString("bill", Cons.GAS_BILL_PAYMENT);
                        goToActivity(OtherBillPayment.class, param);
                        break;
                    }
                    case "Water": {
                        param.putString("bill", Cons.WATER_BILL_PAYMENT);
                        goToActivity(OtherBillPayment.class, param);
                        break;
                    }
                    case "Mobile Postpaid": {
                        param.putString("mobile_recharge", getResources().getString(R.string.mobile_postpaid));
                        goToActivity(MobileRecharge.class, param);
                        break;
                    }
                    case "Mobile Prepaid": {
                        param.putString("mobile_recharge", getResources().getString(R.string.mob_prepaid));
                        goToActivity(MobileRecharge.class, param);
                        break;
                    }
                    case "DTH": {
                        param.putString("dth_rech", getResources().getString(R.string.dth_recharge));
                        goToActivity(DthRecharge.class, param);
                        break;
                    }
                    case "Cashbag Card":
                        goToActivity(ScanActivity.class, null);
//                        showAlert("Coming soon", R.color.red, R.drawable.error);
                        break;
                    case "Theme Park":
                        goToActivity(ThemePark.class, null);
                        break;
                    case "Gift Card":
                        param.putString("from", "Gift Card");
                        goToActivity(GiftCardCategories.class, param);
                        break;
                    case "Refer Earn":
                        param.putString("from", "Refer Earn");
                        goToActivity(ReferEarn.class, param);
                        break;
                    case "Add Money":
                        param.putString("from", getResources().getString(R.string.add_money));
                        goToActivity(AddMoney.class, param);
                        break;
                    case "Confirm TKT": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "https://www.confirmtkt.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Licious": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "https://www.licious.in/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Limeroad": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "https://www.limeroad.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "AliExpress": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.aliexpress.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
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
//                            if (PreferencesManager.getInstance(context).getDOB().equalsIgnoreCase("")) {
//                                openDOBDialog();
//                            } else if (PreferencesManager.getInstance(context).getEMAIL().equalsIgnoreCase("")) {
//                                openEmailDialog();
//                            } else {
//                                moveOnToSPOLogin();
//                            }
                            Bundle bundle = new Bundle();
                            bundle.putString("from", "Main");
                            bundle.putString("link", BuildConfig.SALES_POINT + Utils.convertInto64(BuildConfig.SPO_ID));
                            ((MainContainer) context).goToActivity(context, NormalWebViewActivity.class, bundle);
//                            ((MainContainer) context).goToActivity(context, SalesPointWebLoad.class, null);
                        } else {
                            createInfoDialog(context, "Alert", getString(R.string.alert_internet));
                        }
                        break;
                    }
                    case "Fernsnpetals": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.fnp.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Cleartrip": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.cleartrip.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Ajio": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.ajio.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "FabIndia": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.fabindia.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "DMT":
                        goToActivity(DmtActivity.class, null);
                        break;
                    case "Grofers": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://grofers.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "KFC": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://online.kfc.co.in/home/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, NormalWebViewActivity.class, bundle);
                        break;
                    }
                    case "Nnnow": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "https://www.nnnow.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Country Delight": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://enquiry.countrydelight.in/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Domino's Pizza": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "https://pizzaonline.dominos.co.in&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Insider": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "https://insider.in/bira-91-april-fools-fest/article&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Flipkart": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.flipkart.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Myntra": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.myntra.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "FirstCry": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "http://www.firstcry.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Zivame": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.zivame.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Amazon": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.amazon.in/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Shopclues": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "http://www.shopclues.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Railyatri": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.railyatri.in/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Oyo": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.oyorooms.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Goibibo": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "https://www.goibibo.com/flights/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Uber": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://m.uber.com/looking/&subid=" + PreferencesManager.getInstance(context).getLoginID());
//                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://auth.uber.com/login/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, NormalWebViewActivity.class, bundle);
                        break;
                    }
                    case "MakeMyTrip": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.makemytrip.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Ola": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.olacabs.com//&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, NormalWebViewActivity.class, bundle);
                        break;
                    }
                    case "Zoomcar": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "https://www.zoomcar.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Netmeds": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.netmeds.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, NormalWebViewActivity.class, bundle);
                        break;
                    }
                    case "PharmEasy": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://pharmeasy.in/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, NormalWebViewActivity.class, bundle);
                        break;
                    }
                    case "Swiggy": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.swiggy.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, NormalWebViewActivity.class, bundle);
                        break;
                    }
                    case "BookMyShow": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://in.bookmyshow.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, NormalWebViewActivity.class, bundle);
                        break;
                    }
                    case "BigBasket": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL_CAMPAIGN + "https://www.bigbasket.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, NormalWebViewActivity.class, bundle);
                        break;
                    }
                    case "Zomato": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.zomato.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, NormalWebViewActivity.class, bundle);
                        break;
                    }
                    case "Jabong": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.jabong.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "MOGLIX": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.moglix.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Lenskart": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.lenskart.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Bigrock": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.bigrock.in/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }
                    case "Amazon Prime video": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", BuildConfig.AFFILIATE_URL + "https://www.primevideo.com/&subid=" + PreferencesManager.getInstance(context).getLoginID());
                        ((MainContainer) context).goToActivity(context, WebViewActivity.class, bundle);
                        break;
                    }

                }
            }
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

    }

    Dialog /*dialog_email, dialog_dob,*/ aeps_dialog;

//    private void openDOBDialog() {
//        hideKeyboard();
//        dialog_dob = new Dialog(context);
//        dialog_dob.setCanceledOnTouchOutside(true);
//        dialog_dob.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
//        dialog_dob.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog_dob.setContentView(R.layout.dialog_dob);
//        dialog_dob.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog_dob.show();
//
//        Button buttonConfirm = dialog_dob.findViewById(R.id.buttonConfirm);
//        Button btn_cancel = dialog_dob.findViewById(R.id.btn_cancel);
//        TextView et_dob = dialog_dob.findViewById(R.id.et_dob);
//        et_dob.setOnClickListener(v -> datePicker(et_dob));
//
//        buttonConfirm.setOnClickListener(v -> {
//            if (et_dob.getText().toString().trim().equalsIgnoreCase("")) {
//                showMessage("Please enter your date of birth.");
//            } else {
//                PreferencesManager.getInstance(context).setDOB(et_dob.getText().toString().trim());
//                if (PreferencesManager.getInstance(context).getEMAIL().equalsIgnoreCase("")) {
//                    openEmailDialog();
//                } else {
//                    moveOnToSPOLogin();
//                }
//            }
//        });
//        btn_cancel.setOnClickListener(v -> {
//            dialog_dob.dismiss();
//        });
//    }

//    private void openEmailDialog() {
//        hideKeyboard();
//        dialog_email = new Dialog(context);
//        dialog_email.setCanceledOnTouchOutside(true);
//        dialog_email.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
//        dialog_email.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog_email.setContentView(R.layout.dialog_email);
//        dialog_email.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog_email.show();
//
//        Button buttonConfirm = dialog_email.findViewById(R.id.buttonConfirm);
//        Button btn_cancel = dialog_email.findViewById(R.id.btn_cancel);
//        EditText et_email = dialog_email.findViewById(R.id.et_email);
//        buttonConfirm.setOnClickListener(v -> {
//            if (et_email.getText().toString().trim().equalsIgnoreCase("")) {
//                showMessage("Please enter your email address.");
//            } else if (!Utils.isEmailAddress(et_email.getText().toString().trim())) {
//                showMessage("Please enter a valid email address.");
//            } else {
//                PreferencesManager.getInstance(context).setEMAIL(et_email.getText().toString().trim());
//                dialog_email.dismiss();
//                moveOnToSPOLogin();
//            }
//        });
//        btn_cancel.setOnClickListener(v -> {
//            dialog_email.dismiss();
//        });
//    }

//    private void datePicker(TextView et_dob) {
//        int mYear, mMonth, mDay;
//        Calendar cal = Calendar.getInstance();
//        mYear = cal.get(Calendar.YEAR);
//        mMonth = cal.get(Calendar.MONTH);
//        mDay = cal.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog pickerDialog = new DatePickerDialog(context, R.style.DatePickerDialogTheme, (view, year, month, dayOfMonth) -> {
//            String dob = String.format(Locale.ENGLISH, "%d/%d/%d", dayOfMonth, month + 1, year);
//
//            et_dob.setText(dob);
//
//        }, mYear, mMonth, mDay);
//
//        cal.add(Calendar.YEAR, -18);
//        pickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
//        pickerDialog.show();
//    }

//    public void registerSPO() {
//        RequestSPORegistration requestSPORegistration = new RequestSPORegistration();
//        requestSPORegistration.setUserType("0");
//        requestSPORegistration.setMobile(PreferencesManager.getInstance(context).getMOBILE());
//        requestSPORegistration.setPassword(PreferencesManager.getInstance(context).getSavedPASSWORD());
//        requestSPORegistration.setName(PreferencesManager.getInstance(context).getNAME());
//        requestSPORegistration.setEmail(PreferencesManager.getInstance(context).getEMAIL());
//        requestSPORegistration.setPinCode(PreferencesManager.getInstance(context).getPINCODE());
//        SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
//        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//        Date date;
//        try {
//            date = originalFormat.parse(PreferencesManager.getInstance(context).getDOB());
//            System.out.println("Old Format :   " + originalFormat.format(date));
//            System.out.println("New Format :   " + targetFormat.format(date));
//            requestSPORegistration.setDateOfBirth(targetFormat.format(date));//06/05/1987
//        } catch (ParseException ex) {
//            ex.printStackTrace();
//        }
//        requestSPORegistration.setReferralCode(BuildConfig.SPO_ID);
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
////                        showAlert("Error :\n" + response.body().getAsJsonObject("data")
////                                        .getAsJsonObject("message").getAsJsonArray("Mobile").getAsString() + "\n"
////                                        + response.body().getAsJsonObject("data")
////                                        .getAsJsonObject("message").getAsJsonArray("Email").getAsString()
////                                , R.color.red, R.drawable.error);
//                        LoggerUtil.logItem(response.body().getAsJsonObject("data").getAsJsonObject("message").toString());
//                        String message = parseJson(new JSONObject(response.body().getAsJsonObject("data").getAsJsonObject("message").toString()));
//
//                        showAlert("Error :\n" + message, R.color.red, R.drawable.error);
//
//                    } else {
//                        Log.e("Success", " in registration-------\n " +
//                                response.body().getAsJsonObject("data").get("user").getAsString() + "\n" +
//                                response.body().getAsJsonObject("data").get("username").getAsString());
//
//                        PreferencesManager.getInstance(context).setSPOID(response.body().getAsJsonObject("data").get("user").getAsString());
//                        PreferencesManager.getInstance(context).setSPO_USERNAME(response.body().getAsJsonObject("data").get("username").getAsString());
//                        if (PreferencesManager.getInstance(context).getSPO_PASSWORD().equalsIgnoreCase("")) {
//                            PreferencesManager.getInstance(context).setSPO_PASSWORD(PreferencesManager.getInstance(context).getSavedPASSWORD());
//                        }
////                        {"data":{"error":false,"message":"User Registered Successfully.","errorDetails":[],"user":41913,"username":"ADCD101227"}}
//                        saveSPOData();
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

//    private String parseJson(JSONObject data) {
//        LoggerUtil.logItem(data);
//        if (data != null) {
//            Iterator<String> it = data.keys();
//            while (it.hasNext()) {
//                String key = it.next();
//                try {
//                    if (data.get(key) instanceof JSONArray) {
//                        JSONArray arry = data.getJSONArray(key);
//                        int size = arry.length();
//                        for (int i = 0; i < size; i++) {
//                            if (arry.get(i) instanceof JSONObject)
//                                parseJson(arry.getJSONObject(i));
//                            else
//                                return arry.getString(i);
//                        }
//                    } else if (data.get(key) instanceof JSONObject) {
//                        parseJson(data.getJSONObject(key));
//                    } else {
//                        System.out.println(key + ":" + data.getString(key));
//                        return data.getString(key);
//                    }
//                } catch (Throwable e) {
//                    e.printStackTrace();
//                    try {
//                        System.out.println(key + ":" + data.getString(key));
//                        return data.getString(key);
//                    } catch (Exception ee) {
//                        ee.printStackTrace();
//                        return "Something went wrong";
//                    }
//                }
//            }
//        }
//        return "Something went wrong";
//    }

//    private void saveSPOData() {
//        RequestSavePolicyBazarData requestSavePolicyBazarData = new RequestSavePolicyBazarData();
//        requestSavePolicyBazarData.setDOB(PreferencesManager.getInstance(context).getDOB());
//        requestSavePolicyBazarData.setPBID(PreferencesManager.getInstance(context).getSPOID());
//        requestSavePolicyBazarData.setPBPassword(PreferencesManager.getInstance(context).getSPO_PASSWORD());
//        requestSavePolicyBazarData.setPBUserName(PreferencesManager.getInstance(context).getSPO_USERNAME());
//        requestSavePolicyBazarData.setFKMemId(PreferencesManager.getInstance(context).getUSERID());
//        requestSavePolicyBazarData.setEmail(PreferencesManager.getInstance(context).getEMAIL());
//
//        Call<ResponsePolicyBazarData> responsePolicyBazarDataCall = apiServices.savePolicyBazarData(requestSavePolicyBazarData);
//        LoggerUtil.logItem(requestSavePolicyBazarData);
//        responsePolicyBazarDataCall.enqueue(new Callback<ResponsePolicyBazarData>() {
//            @Override
//            public void onResponse(@NotNull Call<ResponsePolicyBazarData> call, @NotNull Response<ResponsePolicyBazarData> response) {
//                LoggerUtil.logItem(response.body());
//                if (response.body().getResponse().equalsIgnoreCase("Success")) {
//                    moveOnToSPOLogin();
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<ResponsePolicyBazarData> call, @NotNull Throwable t) {
//            }
//        });
//    }

//    void moveOnToSPOLogin() {
//        if (PreferencesManager.getInstance(context).getSPOID().equalsIgnoreCase("")) {
//            registerSPO();
//        } else {
//            Bundle bundle = new Bundle();
//            bundle.putString("link", "");
//            bundle.putString("from", "spo");
//            ((MainContainer) context).goToActivity(context, SalesPointWebLoad.class, bundle);
//        }
//    }

    public void openAEPSDialog() {
        hideKeyboard();
        aeps_dialog = new Dialog(context);
        aeps_dialog.setCanceledOnTouchOutside(true);
        aeps_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        aeps_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        aeps_dialog.setContentView(R.layout.dialog_add_aeps_money);
        aeps_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        aeps_dialog.show();

        Button btn_add_money = aeps_dialog.findViewById(R.id.btn_add_money);
        Button btn_cancel = aeps_dialog.findViewById(R.id.btn_cancel);
        EditText et_amount = aeps_dialog.findViewById(R.id.et_amount);
        btn_add_money.setOnClickListener(v -> {

            if (et_amount.getText().toString().trim().equalsIgnoreCase("")) {
                showMessage("Please enter amount");
            } else if (Integer.parseInt(et_amount.getText().toString().trim()) < 1) {
                showMessage("Minimum transaction amount is 10");
            } else {
                param = new Bundle();
                param.putString("from", "AEPS");
                param.putString("amount", et_amount.getText().toString().trim());
                goToActivity(AddMoney.class, param);
                aeps_dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(v -> {
            aeps_dialog.dismiss();
        });
    }
}
