package com.digitalcashbag.mlm.fragments.team;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digitalcashbag.mlm.adapter.TeamStatusLevelViewAdapter;
import com.digitalcashbag.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.request.RequestTSLevelView;
import kkm.com.core.model.request.ResponseTsLevelSearch;
import kkm.com.core.model.response.team.ResponseTSLevelView;
import kkm.com.core.model.response.team.TeamStatusdetailsItem;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.HidingScrollListener;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamStatusLevelView extends BaseFragment implements MvpView {

    @BindView(R.id.member_recycler)
    RecyclerView memberRecycler;
    Unbinder unbinder;
    @BindView(R.id.txtNoRecord)
    TextView txtNoRecord;
    @BindView(R.id.total_members_txt)
    TextView totalMembersTxt;
    @BindView(R.id.tv_total_members)
    TextView tvTotalMembers;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.user_team)
    TextView userTeam;
    @BindView(R.id.search_et_id)
    EditText searchEtId;
    @BindView(R.id.movetoroot)
    TextView movetoroot;
    @BindView(R.id.movetoroot_lo)
    RelativeLayout movetorootLo;
    @BindView(R.id.prog_bar)
    ProgressBar progBar;
    @BindView(R.id.search)
    ImageButton search;
    @BindView(R.id.searchTeamLay)
    RelativeLayout searchTeamLay;

    private int pageNo = 1;
    private ArrayList<TeamStatusdetailsItem> itemList = new ArrayList<>();
    private ArrayList<TeamStatusdetailsItem> searchList = new ArrayList<>();
    private TeamStatusLevelViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team_status, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        memberRecycler.setLayoutManager(manager);
        memberRecycler.setItemAnimator(new DefaultItemAnimator());
        progBar.setVisibility(View.VISIBLE);
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getTeamStatusLevelView(getArguments().getString("level_name"), getArguments().getString("loginId"));
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
        movetorootLo.setVisibility(View.GONE);
        tvTotalMembers.setVisibility(View.GONE);
        totalMembersTxt.setVisibility(View.GONE);
        searchTeamLay.setVisibility(View.VISIBLE);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 && itemList.size() > 0) {
                    adapter = new TeamStatusLevelViewAdapter(context, itemList, TeamStatusLevelView.this);
                    memberRecycler.setAdapter(adapter);
                    memberRecycler.setVisibility(View.VISIBLE);
                    txtNoRecord.setVisibility(View.GONE);
                }
            }
        });

        memberRecycler.addOnScrollListener(new HidingScrollListener(manager) {
            @Override
            public void onHide() {
            }

            @Override
            public void onLoadMore(int i) {
                Log.e(">>>> ", "you have reached to the bottom = " + pageNo + "" + i);
                if (NetworkUtils.getConnectivityStatus(getActivity()) != 0) {
                    pageNo = i;
                    Log.e(">>>> ", "you have reached to the bottom = " + pageNo);
                    getTeamStatusLevelView(getArguments().getString("level_name"), getArguments().getString("loginId"));
                }
            }

            @Override
            public void onShow() {
            }
        });

        search.setOnClickListener(v -> {
            if (!searchEt.getText().toString().trim().equalsIgnoreCase(""))
                getTeamStatusLevelViewSeach(getArguments().getString("level_name"), getArguments().getString("loginId"), searchEt.getText().toString());
            else if (itemList.size() > 0) {
                adapter = new TeamStatusLevelViewAdapter(context, itemList, TeamStatusLevelView.this);
                memberRecycler.setAdapter(adapter);
                memberRecycler.setVisibility(View.VISIBLE);
                txtNoRecord.setVisibility(View.GONE);
            }
        });

        userTeam.setText(String.format("%s's Team (%s)", PreferencesManager.getInstance(context).getNAME(), PreferencesManager.getInstance(context).getLoginID()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void getClickPosition(int position, String tag) {
        super.getClickPosition(position, tag);
//        if (NetworkUtils.getConnectivityStatus(context) != 0) {
//
//        } else {
//            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
//        }
    }


    private void getTeamStatusLevelView(String level_name, String loginId) {
        RequestTSLevelView param = new RequestTSLevelView();
        param.setPageNumber(String.valueOf(pageNo));
        param.setPageSize("50");
        param.setFkMemID(loginId);
        param.setLevelName(level_name);
//        showLoading();
        LoggerUtil.logItem(param);
        Call<ResponseTSLevelView> responseTSLevelViewCall = apiServices.responseTsLevelViewCall(param);
        responseTSLevelViewCall.enqueue(new Callback<ResponseTSLevelView>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTSLevelView> call, @NonNull Response<ResponseTSLevelView> response) {
//                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    progBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        if (pageNo == 1) {
                            itemList.addAll(response.body().getTeamStatusdetails());
                            adapter = new TeamStatusLevelViewAdapter(context, itemList, TeamStatusLevelView.this);
                            memberRecycler.setAdapter(adapter);
                            memberRecycler.setVisibility(View.VISIBLE);
                            txtNoRecord.setVisibility(View.GONE);
                        } else {
                            itemList.addAll(response.body().getTeamStatusdetails());
                            adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                        }
                    } else if (pageNo == 1) {
                        txtNoRecord.setVisibility(View.VISIBLE);
                        memberRecycler.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTSLevelView> call, @NonNull Throwable t) {
//                hideLoading();
            }
        });
    }


    private void getTeamStatusLevelViewSeach(String level_name, String loginId, String searchKey) {
        ResponseTsLevelSearch param = new ResponseTsLevelSearch();
        param.setLoginId(loginId);
        param.setLevelName(level_name);
        param.setSearchKey(searchKey);
        showLoading();
        LoggerUtil.logItem(param);
        Call<ResponseTSLevelView> responseTSLevelViewCall = apiServices.GetTeamStatusDetailsWithOutPaging(param);
        responseTSLevelViewCall.enqueue(new Callback<ResponseTSLevelView>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTSLevelView> call, @NonNull Response<ResponseTSLevelView> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    progBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        searchList = new ArrayList<>();
                        searchList.addAll(response.body().getTeamStatusdetails());
                        adapter = new TeamStatusLevelViewAdapter(context, searchList, TeamStatusLevelView.this);
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
            public void onFailure(@NonNull Call<ResponseTSLevelView> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    public void reset() {
        itemList.clear();
        pageNo = 1;
        adapter.notifyItemRangeChanged(0, 0);
    }
}
