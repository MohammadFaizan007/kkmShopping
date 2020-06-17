package com.digitalcashbag.shopping.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.activities.ShoppingActivityMain;
import com.digitalcashbag.shopping.activities.ShoppingDetailItemActivity;
import com.digitalcashbag.shopping.adapter.ShoppingProductListFragmentAdapter;
import com.digitalcashbag.utilities.recharges.activities.ItemOffsetDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.request.RequestProducList;
import kkm.com.core.model.response.ResponseProductList;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShoppingProductListFragment extends BaseFragment implements MvpView {
    Unbinder unbinder;
    @BindView(R.id.gv)
    RecyclerView gv;
    String category_id, title_st;
    Bundle bundle = new Bundle();
    private ArrayList<Integer> brandArr;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopping_category_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        try {

            category_id = getArguments().getString("id");
            title_st = getArguments().getString("title");

            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.item_offset);
            gv.addItemDecoration(itemDecoration);
            gv.setHasFixedSize(true);
            gv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } catch (Error | Exception e) {
            e.printStackTrace();
        }

    }

    private void setProductList() {
        showLoading();
        brandArr = new ArrayList<>();
        String priceFrom = "", priceTo = "", parameter = "", parentCategoryID = "";
//        if (!categoryWise) {
//            Bundle bundle = getIntent().getExtras();
//            priceFrom = bundle.getString("priceFrom");
//            priceTo = bundle.getString("priceTo");
//            parameter = bundle.getString("parameter");
//        }
        RequestProducList requestProducList = new RequestProducList();
        requestProducList.setCategoryID(category_id);
        requestProducList.setParentCategoryID(parentCategoryID);
        requestProducList.setPriceRangeFrom(priceFrom);
        requestProducList.setPriceRangeTo(priceTo);
        requestProducList.setBrandID("1");
        requestProducList.setParameter(parameter);
        requestProducList.setBrandArr(brandArr);
        LoggerUtil.logItem(requestProducList);
        Call<ResponseProductList> productListCall = apiServices_shoopping.getProductList(requestProducList);
        productListCall.enqueue(new Callback<ResponseProductList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProductList> call,
                                   @NonNull Response<ResponseProductList> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    ShoppingProductListFragmentAdapter adapter = new ShoppingProductListFragmentAdapter(context, response.body().getProductList(), ShoppingProductListFragment.this);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
                    gv.setAdapter(adapter);
                } else {
                    Toast.makeText(context, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseProductList> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }


    @Override
    public void getClickPosition(int id, String name) {
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            PreferencesManager.getInstance(context).setCategoryName(name);
            bundle.putString("productId", String.valueOf(id));
            goToActivity(ShoppingDetailItemActivity.class, bundle);
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ShoppingActivityMain) context).title.setText(title_st);
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            setProductList();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }
}
