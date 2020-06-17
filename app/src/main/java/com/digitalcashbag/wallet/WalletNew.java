package com.digitalcashbag.wallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.m2p.M2PCardDetail;
import com.digitalcashbag.m2p.M2PKYCRegistration;
import com.digitalcashbag.m2p.M2PKitRequest;
import com.digitalcashbag.shopping.Cons;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.request.RequestUnclearedBalance;
import kkm.com.core.model.request.utility.RequestBalanceAmount;
import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
import kkm.com.core.model.response.ResponseUnclearedBalance;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WalletNew extends BaseFragment implements MvpView {

    private static DecimalFormat formatWallet = new DecimalFormat("0.00");
    String kitNo, m2pStatus;
    @BindView(R.id.total_txt)
    TextView totalTxt;
    @BindView(R.id.bag_bal)
    TextView bagBal;
    @BindView(R.id.view_bag_detail)
    TextView viewBagDetail;
    @BindView(R.id.comm_amt)
    TextView commAmt;
    @BindView(R.id.view_commission_detail)
    TextView viewCommissionDetail;
    @BindView(R.id.hold_amt)
    TextView holdAmt;
    @BindView(R.id.view_hold_detail)
    TextView viewHoldDetail;
    @BindView(R.id.hold_view)
    ImageView holdView;
    @BindView(R.id.unclear_amt)
    TextView unclearAmt;
    @BindView(R.id.view_unclear_detail)
    TextView viewUnclearDetail;
    @BindView(R.id.unclear_view)
    ImageView unclearView;
    @BindView(R.id.notice_txt)
    TextView noticeTxt;
    Unbinder unbinder;
    @BindView(R.id.requestBankKit)
    TextView requestBankKit;
    @BindView(R.id.bagamount_card)
    CardView bagamountCard;
    @BindView(R.id.commission_card)
    CardView commissionCard;
    @BindView(R.id.hold_card)
    CardView holdCard;
    @BindView(R.id.unclear_card)
    CardView unclearCard;
    @BindView(R.id.hold_reason)
    TextView holdReason;
    @BindView(R.id.card_amt)
    TextView cardAmt;
    @BindView(R.id.cardbalance_card)
    CardView cardbalanceCard;
    @BindView(R.id.requestBankKit_lo)
    CardView requestBankKitLo;

    Double total;
    Double cardBalance;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.walletnew_activity, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getBalanceAmount();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    private void getBalanceAmount() {
        showLoading();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("MemberId", PreferencesManager.getInstance(context).getUSERID());
        LoggerUtil.logItem(jsonObject);

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(jsonObject.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<JsonObject> walletBalanceCall = apiServices_utilityV2.getbalanceAmount(body);
        walletBalanceCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    ResponseBalanceAmount convertedObject = new Gson().fromJson(paramResponse, ResponseBalanceAmount.class);
                    if (response.body() != null && convertedObject.getStatus().equalsIgnoreCase("Success")) {
                        String amount = formatWallet.format(Double.parseDouble(String.valueOf(convertedObject.getBalanceAmount())));
                        bagBal.setText(String.format("₹ %s", amount));
                    }

                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getUnclearedBalanceAmount();
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }

                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                LoggerUtil.logItem(t.getMessage());
                hideLoading();
            }
        });
    }

    private void getUnclearedBalanceAmount() {
        RequestUnclearedBalance requestUnclearedBalance = new RequestUnclearedBalance();
        requestUnclearedBalance.setFKToId(PreferencesManager.getInstance(context).getUSERID());
        LoggerUtil.logItem(requestUnclearedBalance);
        Call<ResponseUnclearedBalance> responseUnclearedBalanceCall = apiServices.responseUnclearedBalanceCall(requestUnclearedBalance);
        responseUnclearedBalanceCall.enqueue(new Callback<ResponseUnclearedBalance>() {
            @Override
            public void onResponse(@NotNull Call<ResponseUnclearedBalance> call, @NotNull Response<ResponseUnclearedBalance> response) {
                Log.e("==========", "getUnclearedBalanceAmount");
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {

                        PreferencesManager.getInstance(context).setUNCLEARED_BALANCE(response.body().getUnclearedamount());
                        PreferencesManager.getInstance(context).setKIT_NO(response.body().getKitNo());
                        unclearAmt.setText(String.format("₹ %s", response.body().getUnclearedamount()));
                        kitNo = response.body().getKitNo();
                        m2pStatus = response.body().getM2pStatus();

                        Log.e("M2p", "===" + response.body().getM2pStatus());

//                      cardAmt.setText(String.format("₹ %s", response.body().getBagAmount()));// here card amount is bag amount by mistake from API end
                        commAmt.setText(String.format("₹ %s", response.body().getTotalCommission()));
                        holdAmt.setText(String.format("₹ %s", response.body().getHoldAmount()));
                        noticeTxt.setText(response.body().getNotice());
                        holdReason.setText(response.body().getHoldReason());

                        total = Double.parseDouble(response.body().getTotalWalletAmount());
                        cardBalance = Double.parseDouble(cardAmt.getText().toString().replace("₹ ", ""));

                        totalTxt.setText(String.format("Total ₹ %s", total + cardBalance));

                        //blank ; 0 = not requested yet
                        //blank ; 1 = requested, pending for approval from admin
                        //!blank ; 2 = Kit Number assigned, approved by admin, waiting for KYC
                        //!blank ; 3 = KYC done, waiting for linking card
                        //!blank ; 4 = Card Linked go for PIN generation (not used anymore)

                        if (kitNo.equalsIgnoreCase("") && m2pStatus.equalsIgnoreCase("0")) {
                            initializeView("Request For Bank Kit", R.drawable.ic_m2p_rrequest, R.drawable.blue_box, R.color.blue_text_dark);
                        } else if (kitNo.equalsIgnoreCase("") && m2pStatus.equalsIgnoreCase("1")) {
                            initializeView("Pending approval from admin", R.drawable.pending, R.drawable.yellow_box, R.color.red);
                        } else if ((!kitNo.equalsIgnoreCase("")) && m2pStatus.equalsIgnoreCase("2")) {
                            initializeView("Update your KYC to link your wallet to Bank Wallet", R.drawable.warning_red, R.drawable.yellow_box, R.color.red);
                        } else if ((!kitNo.equalsIgnoreCase("")) && m2pStatus.equalsIgnoreCase("3")) {
//                            initializeView("Link your Cashbag card with your account & generate PIN", R.drawable.link_connect, R.drawable.rect_btn_bg_darkgreen, R.color.white);
                            cardbalanceCard.setVisibility(View.VISIBLE);
                            getCardbalance();
                        }
                    } else {
                        PreferencesManager.getInstance(context).setUNCLEARED_BALANCE("00.00");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseUnclearedBalance> call, @NotNull Throwable t) {

            }
        });
    }

    private void getCardbalance() {
        try {
            showLoading();
            String url = BuildConfig.BASE_URL_M2PV2 + "GetBalanceByEntityId?EntityId=" + Cons.encryptMsg(PreferencesManager.getInstance(context).getLoginID(), easypay_key);
            Log.e("URL======    ", url);
            ApiServices apiServicesM2P = ServiceGenerator.createServiceM2P(ApiServices.class);
            Call<JsonObject> call = apiServicesM2P.getCardBalance(url);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.message());
                    LoggerUtil.logItem(response.code());
                    LoggerUtil.logItem(response.errorBody());
                    LoggerUtil.logItem(response.headers());
                    LoggerUtil.logItem(response.raw().body());
                    LoggerUtil.logItem(call.request().url());
                    JsonObject response_new;
                    try {
                        response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key), JsonObject.class);
//
                        Log.e("============ ", String.valueOf(response_new));
                        if (response.body() != null && response_new.get("response").getAsString().equalsIgnoreCase("Success")) {
                            cardAmt.setText(String.format("₹ %s", response_new.getAsJsonObject("result").getAsJsonArray("result")
                                    .get(0).getAsJsonObject().get("balance").getAsString()));

                            cardBalance = Double.parseDouble(cardAmt.getText().toString().replace("₹ ", ""));
                            Log.e("card", "" + cardBalance);
                            Log.e("card", "" + Math.abs(total + cardBalance));
                            totalTxt.setText(String.format("Total ₹ %s", total + cardBalance));
                        } else {
                            cardAmt.setText(String.format("₹ %s", "0"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //
                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                    LoggerUtil.logItem(t.getMessage());
                }
            });
        } catch (Error |
                Exception e) {
            e.printStackTrace();
        }
    }


    private void initializeView(String msg, int left_icon, int bg_drawable, int text_color) {
        requestBankKitLo.setVisibility(View.VISIBLE);
        requestBankKit.setText(msg);
        requestBankKit.setCompoundDrawablesWithIntrinsicBounds(left_icon, 0, 0, 0);
        requestBankKit.setBackgroundResource(bg_drawable);
        requestBankKit.setTextColor(getResources().getColor(text_color));
    }

    @OnClick({R.id.bagamount_card, R.id.commission_card, R.id.cardbalance_card, R.id.hold_card, R.id.unclear_card, R.id.requestBankKit_lo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.transfer_commission:
                showAlert("Under Progress", R.color.red, R.drawable.error);
                break;
            case R.id.hold_card:
                goToActivity(HoldBagDetail.class, null);
                break;
            case R.id.cardbalance_card:
                goToActivity(M2PCardDetail.class, null);
                break;
            case R.id.bagamount_card:
                goToActivity(AllTransaction.class, null);
                break;
            case R.id.unclear_card:
                goToActivity(LevelIncome.class, null);
                break;
            case R.id.requestBankKit_lo:
                if (kitNo.equalsIgnoreCase("") && m2pStatus.equalsIgnoreCase("0")) {
                    goToActivity(M2PKitRequest.class, null);
                } else if (kitNo.equalsIgnoreCase("") && m2pStatus.equalsIgnoreCase("1")) {
                } else if ((!kitNo.equalsIgnoreCase("")) && m2pStatus.equalsIgnoreCase("2")) {
                    goToActivity(M2PKYCRegistration.class, null);
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}