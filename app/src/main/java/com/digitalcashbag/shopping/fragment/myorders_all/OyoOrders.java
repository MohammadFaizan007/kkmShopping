package com.digitalcashbag.shopping.fragment.myorders_all;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.adapter.ShoppingOrderAdapter;
import com.digitalcashbag.shopping.adapter.ShoppingOtherOrderAdapter;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.request.RequestAmazonAdd;
import kkm.com.core.model.response.ResponseAmazonAdd;
import kkm.com.core.model.response.ResponseOrderList;
import kkm.com.core.model.response.shopping.LstINRItem;
import kkm.com.core.model.response.shopping.ResponseINRDeailsOrders;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.FileUtils;
import kkm.com.core.utils.HidingScrollListener;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class OyoOrders extends BaseFragment{

    public LinearLayoutManager layoutManager;
    Unbinder unbinder;
    @BindView(R.id.rv_orderlist)
    RecyclerView rvorderlist;
    @BindView(R.id.noRecord)
    TextView noRecord;
    int pageNo = 1;
    List<LstINRItem> items;
    ShoppingOtherOrderAdapter shoppingOtherOrderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderlist, null);
        unbinder = ButterKnife.bind(this, view);
        items = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        rvorderlist.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        rvorderlist.setItemAnimator(new DefaultItemAnimator());

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getOtherOrders();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

        rvorderlist.addOnScrollListener(new HidingScrollListener(layoutManager) {
            @Override
            public void onHide() {
            }

            @Override
            public void onLoadMore(int i) {
                Log.e(">>>> ", "you have reached to the bottom = " + pageNo + "" + i);
                if (NetworkUtils.getConnectivityStatus(getActivity()) != 0) {
                    pageNo = i;
                    Log.e(">>>> ", "you have reached to the bottom = " + pageNo);
                    getOtherOrders();
                }
            }

            @Override
            public void onShow() {
            }
        });

    }


    private void getOtherOrders() {
        showLoading();
        String url = BuildConfig.BASE_URL_INR + "GetINRDealsTransaction?LoginId=" +
                PreferencesManager.getInstance(context).getUSERID() + "&Page=" + pageNo + "&StoreName=oyo";
        Log.e("URL======    ", url);
        ApiServices apiServicesINR = ServiceGenerator.createService(ApiServices.class);
        Call<ResponseINRDeailsOrders> call = apiServicesINR.getOtherOrders(url);
        call.enqueue(new Callback<ResponseINRDeailsOrders>() {
            @Override
            public void onResponse(@NonNull Call<ResponseINRDeailsOrders> call, @NonNull Response<ResponseINRDeailsOrders> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                            if (pageNo == 1) {
                                items.addAll(response.body().getLstINR());
                                shoppingOtherOrderAdapter = new ShoppingOtherOrderAdapter(context, items);
                                rvorderlist.setAdapter(shoppingOtherOrderAdapter);
                                Log.e("SIZEEEE AA- ", "" + items.size());
                            } else {
                                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                                    items.addAll(response.body().getLstINR());
                                    shoppingOtherOrderAdapter.notifyItemRangeChanged(0, shoppingOtherOrderAdapter.getItemCount());
                                    Log.e("SIZEEEE BB- ", "" + items.size());
                                }
                            }
                        } else {
                            if (pageNo == 1) {
                                noRecord.setVisibility(View.VISIBLE);
                                rvorderlist.setVisibility(View.GONE);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseINRDeailsOrders> call, @NonNull Throwable t) {
                noRecord.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pageNo = 1;
        unbinder.unbind();
    }

}