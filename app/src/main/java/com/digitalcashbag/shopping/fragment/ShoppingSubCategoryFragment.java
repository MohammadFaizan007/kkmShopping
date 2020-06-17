package com.digitalcashbag.shopping.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.activities.ShoppingActivityMain;
import com.digitalcashbag.shopping.adapter.ShoppingSubCategoryFragmentAdapter;
import com.digitalcashbag.utilities.recharges.activities.ItemOffsetDecoration;
import com.google.gson.JsonObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.response.shopping.ResponseMainCategory;
import kkm.com.core.model.response.shopping.SubCategoryItem;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShoppingSubCategoryFragment extends BaseFragment implements MvpView {
    Unbinder unbinder;
    @BindView(R.id.gv)
    RecyclerView gv;
    String category_id, title_st;
    private List<SubCategoryItem> itemList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopping_category_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

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

    private void getShoppingCategory() {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("PK_CategoryId", "");
        object.addProperty("FK_CategoryId", category_id);
        object.addProperty("Parameter", "1");
        LoggerUtil.logItem(object);
        Call<ResponseMainCategory> responseCategoryCall = apiServices_shoopping.getShoppingMainCategory(object);
        responseCategoryCall.enqueue(new Callback<ResponseMainCategory>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMainCategory> call, @NonNull Response<ResponseMainCategory> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")
                        && response.body().getCategoryData().size() > 0 &&
                        response.body().getCategoryData().get(0).getSubCategory().size() > 0) {
                    itemList = response.body().getCategoryData().get(0).getSubCategory();
                    ShoppingSubCategoryFragmentAdapter shoppingFragmentAdapter =
                            new ShoppingSubCategoryFragmentAdapter(context, itemList, ShoppingSubCategoryFragment.this);
                    gv.setAdapter(shoppingFragmentAdapter);
                } else {
//                    Toast.makeText(context, "No Record Found", Toast.LENGTH_SHORT).show();
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        ((ShoppingActivityMain) getActivity()).ReplaceFragment_AddBack(new ShoppingProductListFragment(), title_st, Integer.parseInt(category_id));
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMainCategory> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void getClickPosition(int id, String name) {
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getShoppingCategory(id, name);
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    private void getShoppingCategory(int id, String name) {
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("PK_CategoryId", "");
        object.addProperty("FK_CategoryId", id);
        object.addProperty("Parameter", "2");
        LoggerUtil.logItem(object);
        Call<ResponseMainCategory> responseCategoryCall = apiServices_shoopping.getShoppingMainCategory(object);
        responseCategoryCall.enqueue(new Callback<ResponseMainCategory>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMainCategory> call, @NonNull Response<ResponseMainCategory> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")
                        && response.body().getCategoryData().size() > 0 &&
                        response.body().getCategoryData().get(0).getChildCategory().size() > 0) {
                    ((ShoppingActivityMain) getActivity()).ReplaceFragment_AddBack(new ShoppingSubCategoryItemsFragment(), name, id);
                } else {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        ((ShoppingActivityMain) getActivity()).ReplaceFragment_AddBack(new ShoppingProductListFragment(), name, id);
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
//                    Toast.makeText(context, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMainCategory> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
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
            getShoppingCategory();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }
}
