package com.digitalcashbag.utilities.recharges.browse_plan;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.utilities.recharges.activities.MobileRecharge;
import com.digitalcashbag.utilities.recharges.adapter.BrowsePlanAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.CheckErrorCode;
import kkm.com.core.model.response.jioPrepaid.ResponseAddInfoJio;
import kkm.com.core.model.response.jioPrepaid.ResponseJioBrowsePlanPrepaid;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.DialogUtil;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class BrowsePlanJio extends DialogFragment implements MvpView {
    public static String TAG = "BrowsePlanJio";
    Unbinder unbinder;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.browsePlan_recycler)
    RecyclerView browsePlanRecycler;
    @BindView(R.id.txtNoRecord)
    TextView txtNoRecord;
    private ProgressDialog mProgressDialog;

    String keys = BuildConfig.CASHBAG_KEY;
    public SecretKey easypay_key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.browse_plan, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            easypay_key = new SecretKeySpec(keys.getBytes(), "AES");
            title.setText("Available Plan");
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            browsePlanRecycler.setLayoutManager(manager);
            if (NetworkUtils.getConnectivityStatus(getActivity()) != 0) {
                getPlan(getArguments().getString("MobNo"));
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void getPlan(String mobNo) {
        ApiServices apiServices = ServiceGenerator.createServiceUtilityV2(ApiServices.class);
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("NUMBER", mobNo);
        object.addProperty("REQ_TYPE", "1");
        object.addProperty("AMOUNT", "10.00");

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(object.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LoggerUtil.logItem(object);
        Call<JsonObject> call = apiServices.getJioPlan(body);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                try {

                    hideLoading();
                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    JsonArray convertedObject = new Gson().fromJson(paramResponse, JsonArray.class);


                    Cons.responseJioBrowsePlanArray = new ArrayList<>();
                    LoggerUtil.logItem(convertedObject.toString());
                    if(convertedObject.size()>0) {
                        JsonObject object = convertedObject.get(0).getAsJsonObject();
                        if (object.get("ERROR").getAsString().equalsIgnoreCase("0") &&
                                object.get("RESULT").getAsString().equalsIgnoreCase("0")) {
                            Log.e("ORIGINAL", (object.get("ADDINFO").getAsString()));
                            String addinfo = Utils.replaceBackSlash(object.get("ADDINFO").getAsString());
                            Log.e("Object==", "PARSING---------------");
                            Log.e("AFTER", addinfo);

                            String firstChar = String.valueOf(addinfo.charAt(0));
                            if (firstChar.equalsIgnoreCase("[")) {
                                Log.e("[", addinfo);
                                Cons.responseJioBrowsePlanArray = Utils.getList(addinfo, ResponseJioBrowsePlanPrepaid.class);
                                BrowsePlanAdapter adapter = new BrowsePlanAdapter(getActivity(), Cons.responseJioBrowsePlanArray.get(0).getAddinfo().getPlanoffering(), BrowsePlanJio.this);
                                browsePlanRecycler.setAdapter(adapter);
                                browsePlanRecycler.setVisibility(View.VISIBLE);
                                txtNoRecord.setVisibility(View.GONE);
                                Log.e("Array==", Cons.responseJioBrowsePlanArray.get(0).getErrmsg());
                            } else {
                                Log.e("else", addinfo);
                                Gson gson = new GsonBuilder().create();
                                ResponseAddInfoJio responseJioBrowsePlanPrepaid = gson.fromJson(addinfo, ResponseAddInfoJio.class);
                                LoggerUtil.logItem(responseJioBrowsePlanPrepaid);
                                if (responseJioBrowsePlanPrepaid.getPlanoffering() != null && responseJioBrowsePlanPrepaid.getPlanoffering().size() > 0) {
                                    BrowsePlanAdapter adapter = new BrowsePlanAdapter(getActivity(), responseJioBrowsePlanPrepaid.getPlanoffering(), BrowsePlanJio.this);
                                    browsePlanRecycler.setAdapter(adapter);
                                    browsePlanRecycler.setVisibility(View.VISIBLE);
                                    txtNoRecord.setVisibility(View.GONE);
                                } else {
                                    browsePlanRecycler.setVisibility(View.GONE);
                                    txtNoRecord.setVisibility(View.VISIBLE);
                                }
                                Log.e("Object==", "Fail");
                            }

                        } else {
                            hideLoading();
                            CheckErrorCode checkErrorCode = new CheckErrorCode();
                            checkErrorCode.isValidTransaction(getActivity(), object.get("error").getAsString());
                            browsePlanRecycler.setVisibility(View.GONE);
                            txtNoRecord.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    browsePlanRecycler.setVisibility(View.GONE);
                    txtNoRecord.setVisibility(View.VISIBLE);
//                    Toast.makeText(getContext(), "Something went wrong. \nPlease try after some time.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }


    public void showAlert(String msg, int color, int icon) {
        Alerter.create(getActivity())
                .setText(msg)
                .setTextAppearance(kkm.com.core.R.style.alertTextColor)
                .setBackgroundColorRes(color)
                .setIcon(icon)
                .show();
    }

    public void goToActivity(Activity activity, Class<?> classActivity, Bundle bundle) {
        Utils.hideSoftKeyboard(activity);
        Intent intent = new Intent(activity, classActivity);
        if (bundle != null)
            intent.putExtra(PAYLOAD_BUNDLE, bundle);
        activity.startActivity(intent);
        getActivity().overridePendingTransition(kkm.com.core.R.anim.slide_from_right, kkm.com.core.R.anim.slide_to_left);
        dismiss();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    public void onError(int resId) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public void getTrainingID(String trainingamount, String trainingname, String trainingID) {

    }

    @Override
    public void getDateDetails(Date date) {

    }

    @Override
    public void getClickPosition(int amount, String id) {
        ((MobileRecharge) getContext()).etAmount.setText(String.valueOf(amount));
        ((MobileRecharge) getContext()).jio_plan_id = id;
        dismiss();
    }


    @Override
    public void getGiftCardCategoryId(String id, String name) {

    }

    @Override
    public void checkAvailability(String id, String date, String name, String amount) {

    }

    @Override
    public void openSearchCategory(String searchItemId, String searchName) {

    }

    @Override
    public void getPayoutWithdrawalId(String requestId) {

    }

    public void showLoading() {
        mProgressDialog = DialogUtil.showLoadingDialog(getActivity(), "Base Activity");
    }

    @Override
    public void getClickPositionDirectMember(int position, String tag, String memberId) {

    }

    @OnClick(R.id.side_menu)
    public void onViewClicked() {
        dismiss();
    }

    @Override
    public void setTransferTicket(String trainingamount, String trainingname, String trainingID, String totalTickets, String date) {

    }
}
