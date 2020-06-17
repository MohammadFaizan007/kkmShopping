package com.digitalcashbag.common_activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.digitalcashbag.R;
import com.digitalcashbag.adapter.SearchAdapter;
import com.digitalcashbag.shopping.activities.ShoppingCategoryItemsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.RequestSearch;
import kkm.com.core.model.response.searchResponse.ResponseSearchItems;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.search)
    SearchView search;
    @BindView(R.id.rv_search_items)
    RecyclerView rvSearchItems;
    Bundle param = new Bundle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        ButterKnife.bind(this);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearchItems.setLayoutManager(manager);

        if (NetworkUtils.getConnectivityStatus(context) != 0) searchList("");
        else
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);

        search.setOnClickListener(v -> search.onActionViewExpanded());

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchList(s);
                return false;
            }
        });
    }

    public void searchList(String key) {
        RequestSearch search = new RequestSearch();
        search.setCreatedBy(PreferencesManager.getInstance(context).getUSERID());
        search.setSearchString(key);

        Call<ResponseSearchItems> call = apiServices_shoopping.getSearchList(search);
        call.enqueue(new Callback<ResponseSearchItems>() {
            @Override
            public void onResponse(@NonNull Call<ResponseSearchItems> call, @NonNull Response<ResponseSearchItems> response) {
                LoggerUtil.logItem(response.body());
                if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                    SearchAdapter adapter = new SearchAdapter(context, response.body(), SearchActivity.this);
                    rvSearchItems.setAdapter(adapter);
                }
            }


            @Override
            public void onFailure(@NonNull Call<ResponseSearchItems> call, @NonNull Throwable t) {
            }
        });
    }


    @Override
    public void openSearchCategory(String searchItemId, String searchName) {
        super.openSearchCategory(searchItemId, searchName);
        param.putString("CategoryId", String.valueOf(searchItemId));
        param.putString("CategoryName", searchName);
        goToActivity(context, ShoppingCategoryItemsActivity.class, param);
    }

}
