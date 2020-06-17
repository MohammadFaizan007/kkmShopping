package com.digitalcashbag.mlm.fragments.team;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.mlm.adapter.DirectMemberAdapter;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.response.team.DirectMembersItem;
import kkm.com.core.model.response.team.ResponseDirect;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectMember extends BaseFragment implements MvpView {

    public static ArrayList<String> previousIds = new ArrayList<>();
    public static ArrayList<String> previousNames = new ArrayList<>();
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.btn_root)
    TextView btnRoot;
    @BindView(R.id.member_recycler)
    RecyclerView memberRecycler;
    Unbinder unbinder;
    @BindView(R.id.txtNoRecord)
    TextView txtNoRecord;
    @BindView(R.id.btn_up)
    TextView btn_up;
    private String rootId;
    private ArrayList<DirectMembersItem> itemList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.direct_member, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        rootId = PreferencesManager.getInstance(context).getLoginID();
        txtName.setText(PreferencesManager.getInstance(context).getNAME());
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        memberRecycler.setLayoutManager(manager);


        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getDirect(rootId);
            btn_up.setVisibility(View.GONE);
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_root, R.id.btn_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_root:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    txtName.setText(PreferencesManager.getInstance(context).getNAME());
                    getDirect(rootId);
                    btn_up.setVisibility(View.GONE);
                    previousIds.clear();
                    previousNames.clear();
                } else {
                    showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                }
                break;
            case R.id.btn_up:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (previousIds.size() > 1) {
                        previousIds.remove(previousIds.size() - 1);
                        previousNames.remove(previousNames.size() - 1);
                        getDirect(previousIds.get(previousIds.size() - 1));
                        txtName.setText(previousNames.get(previousNames.size() - 1));
                    } else {
                        previousIds.clear();
                        previousNames.clear();
                        btn_up.setVisibility(View.GONE);
                        getDirect(rootId);
                        txtName.setText(PreferencesManager.getInstance(context).getNAME());
                    }
                    LoggerUtil.logItem(previousIds + " " + previousNames);
                } else {
                    showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                }
                break;
        }
    }

    @Override
    public void getClickPositionDirectMember(int position, String tag, String memberId) {
        super.getClickPosition(position, tag);
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            previousIds.add(memberId);
            previousNames.add(tag);
            txtName.setText(itemList.get(position).getMemberName());
            getDirect(memberId);
            btn_up.setVisibility(View.VISIBLE);
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    private void getDirect(String loginId) {
        JsonObject param = new JsonObject();
        LoggerUtil.logItem(previousIds + " " + previousNames);
        param.addProperty("LoginId", loginId);
        showLoading();
        LoggerUtil.logItem(param);
        Call<ResponseDirect> directCall = apiServices.getDirects(param);

        directCall.enqueue(new Callback<ResponseDirect>() {
            @Override
            public void onResponse(@NonNull Call<ResponseDirect> call, @NonNull Response<ResponseDirect> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                        itemList = (ArrayList<DirectMembersItem>) response.body().getDirectMembers();
                        DirectMemberAdapter adapter = new DirectMemberAdapter(context, itemList, DirectMember.this);
                        memberRecycler.setAdapter(adapter);
                        memberRecycler.setVisibility(View.VISIBLE);
                        txtNoRecord.setVisibility(View.GONE);
                    } else {
                        memberRecycler.setVisibility(View.GONE);
                        txtNoRecord.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseDirect> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }
}
