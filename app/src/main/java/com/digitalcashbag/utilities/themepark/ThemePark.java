package com.digitalcashbag.utilities.themepark;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.utilities.adapter.ThemeParkAdapter;
import com.google.gson.JsonObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.app.CheckErrorCode;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.themeParkResponse.theme_park_maincategories.ResponseThemeParkMainCategories;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemePark extends BaseActivity {
    @BindView(R.id.title)
    TextView title;

    Bundle param = new Bundle();
    @BindView(R.id.theme_recyclerView)
    RecyclerView themeRecyclerView;
    @BindView(R.id.side_menu)
    ImageView sideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_park);
        ButterKnife.bind(this);
        try {
            title.setText("Theme Park");
            sideMenu.setOnClickListener(v -> finish());

            GridLayoutManager manager = new GridLayoutManager(context, 2);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            themeRecyclerView.setLayoutManager(manager);

            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getThemeParkCategories();
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getThemeParkCategories() {
        try {
//        {"Type":"1","AMOUNT":"10.00","AMOUNT_ALL":"10.00","NUMBER":"9918703130"}
//            RequestThemePark requestThemePark = new RequestThemePark();
//            requestThemePark.setType("1");
//            requestThemePark.setAMOUNT("1.00");
//            requestThemePark.setAMOUNTALL("1.00");
//            requestThemePark.setNUMBER(PreferencesManager.getInstance(context).getMOBILE());

            JsonObject paramObj = new JsonObject();
            paramObj.addProperty("AMOUNT", "1.00");
            paramObj.addProperty("AMOUNT_ALL", "1.00");
            paramObj.addProperty("Type", "1");
            paramObj.addProperty("NUMBER", PreferencesManager.getInstance(context).getMOBILE());
            LoggerUtil.logItem(paramObj);

            JsonObject body = new JsonObject();
            try {
                body.addProperty("body", Cons.encryptMsg(paramObj.toString(), easypay_key));
                LoggerUtil.logItem(body);
            } catch (Exception e) {
                e.printStackTrace();
            }

            showLoading();


            Call<JsonObject> responseThemeParkCall = apiServices_utilityV2.getThemeParkCategories(body);
            responseThemeParkCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    try {

                        LoggerUtil.logItem(response.body());
//

                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                        List<ResponseThemeParkMainCategories> list = Utils.getList(paramResponse, ResponseThemeParkMainCategories.class);
                        LoggerUtil.logItem(list);

                        if ((list.get(0).getError().equalsIgnoreCase("0")) && (list.get(0).getResult().equalsIgnoreCase("0"))) {
                            if (list.get(0).getAddinfo() != null) {
                                if (list.get(0).getAddinfo().size() > 0) {
                                    ThemeParkAdapter themeParkAdapter = new ThemeParkAdapter(context, list.get(0).getAddinfo(), ThemePark.this);
                                    themeRecyclerView.setAdapter(themeParkAdapter);
                                } else {
                                    showAlert("No records found.", R.color.red, R.drawable.alerter_ic_notifications);
                                }
                            } else {
                                showAlert("Something went wrong.", R.color.red, R.drawable.alerter_ic_notifications);
                            }
                        } else {
                            CheckErrorCode checkErrorCode = new CheckErrorCode();
                            checkErrorCode.isValidTransaction(context, list.get(0).getError());
                        }
                    } catch (Error | Exception e) {
                        hideLoading();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Error | Exception e) {
            hideLoading();
            e.printStackTrace();
        }
    }


    @Override
    public void getGiftCardCategoryId(String id, String name) {
        param.putString("giftId", id);
        param.putString("giftName", name);
        goToActivity(context, ThemeParkPackeges.class, param);
    }
}
