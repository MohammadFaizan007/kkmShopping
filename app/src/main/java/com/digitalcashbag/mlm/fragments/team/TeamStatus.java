package com.digitalcashbag.mlm.fragments.team;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digitalcashbag.mlm.activities.MainContainerMLM;
import com.digitalcashbag.mlm.adapter.TeamStatusAdapter;
import com.digitalcashbag.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.request.RequestTeamStatus;
import kkm.com.core.model.response.team.ResponseTeamStatus;
import kkm.com.core.model.response.team.TeamStatusItem;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamStatus extends BaseFragment implements MvpView {

    @BindView(R.id.member_recycler)
    RecyclerView memberRecycler;
    Unbinder unbinder;
    @BindView(R.id.txtNoRecord)
    TextView txtNoRecord;
    @BindView(R.id.tv_total_members)
    TextView tvTotalMembers;
    @BindView(R.id.total_members_txt)
    TextView totalMembersTxt;
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
    private ArrayList<TeamStatusItem> itemList = new ArrayList<>();
    private int totalMembers = 0;
    @BindView(R.id.search)
    ImageButton search;
    @BindView(R.id.searchTeamLay)
    RelativeLayout searchTeamLay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team_status, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        ((MainContainerMLM) getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<small><b>" + "Team Status" + "</b></small>"));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        memberRecycler.setLayoutManager(manager);
        progBar.setVisibility(View.VISIBLE);

        searchTeamLay.setVisibility(View.INVISIBLE);
        movetorootLo.setVisibility(View.VISIBLE);


        searchEtId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0)
                        getTeamStatus(searchEtId.getText().toString());
                    else
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                } else if (s.length() == 0) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getTeamStatus(PreferencesManager.getInstance(context).getLoginID());
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                }
            }
        });
        searchEtId.setText("");

        movetoroot.setOnClickListener(v -> {
            searchEtId.setText("");
            getTeamStatus(PreferencesManager.getInstance(context).getLoginID());
        });

        userTeam.setText(String.format("Team %s (%s)", PreferencesManager.getInstance(context).getNAME(), PreferencesManager.getInstance(context).getLoginID()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getClickPosition(int position, String tag) {
        super.getClickPosition(position, tag);
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            Bundle param = new Bundle();
            param.putString("level_name", tag);
            if (searchEtId.getText().toString().length() == 6)
                param.putString("loginId", searchEtId.getText().toString());
            else
                param.putString("loginId", PreferencesManager.getInstance(context).getLoginID());
            Fragment tsLevelViewFragment = new TeamStatusLevelView();
            tsLevelViewFragment.setArguments(param);
            try {
                ((MainContainerMLM) context).ReplaceFragmentadd(tsLevelViewFragment, tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    private void getTeamStatus(String userId) {
        RequestTeamStatus param = new RequestTeamStatus();
        param.setLoginId(userId);
        param.setSessionLoginId(PreferencesManager.getInstance(context).getLoginID());
        LoggerUtil.logItem(param);
        Call<ResponseTeamStatus> responseTeamStatusCall = apiServices.responseTeamStatusCall(param);
        responseTeamStatusCall.enqueue(new Callback<ResponseTeamStatus>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTeamStatus> call, @NonNull Response<ResponseTeamStatus> response) {
                try {
                    progBar.setVisibility(View.GONE);
                    totalMembers = 0;
                    LoggerUtil.logItem(response.body());
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        itemList = (ArrayList<TeamStatusItem>) response.body().getTeamStatus();
                        TeamStatusAdapter adapter = new TeamStatusAdapter(context, itemList, TeamStatus.this);
                        memberRecycler.setAdapter(adapter);
                        memberRecycler.setVisibility(View.VISIBLE);
                        tvTotalMembers.setVisibility(View.VISIBLE);
                        for (int i = 0; i < itemList.size(); i++) {
                            totalMembers = totalMembers + Integer.parseInt(itemList.get(i).getTeamSize());
                        }
                        tvTotalMembers.setText(String.valueOf(totalMembers));
                        totalMembersTxt.setVisibility(View.VISIBLE);
                        txtNoRecord.setVisibility(View.GONE);
                    } else {
                        memberRecycler.setVisibility(View.GONE);
                        tvTotalMembers.setVisibility(View.GONE);
                        totalMembersTxt.setVisibility(View.GONE);
                        txtNoRecord.setVisibility(View.VISIBLE);
                    }
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTeamStatus> call, @NonNull Throwable t) {
            }
        });
    }
}
