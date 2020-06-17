package com.digitalcashbag.utilities.news.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalcashbag.R;
import com.digitalcashbag.utilities.news.adapter.PopularAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.retrofit.MvpView;

public class Popular extends BaseFragment implements MvpView {
    @BindView(R.id.news_popular_rv)
    RecyclerView newsPopularRv;
    Unbinder unbinder;

    String[] newsString = {"After Republicans Corner Sunder Pichai, Google CEO Takes On", "After Republicans Corner Sunder Pichai, Google CEO Takes On", "After Republicans Corner Sunder Pichai, Google CEO Takes On",
            "After Republicans Corner Sunder Pichai, Google CEO Takes On", "After Republicans Corner Sunder Pichai, Google CEO Takes On", "After Republicans Corner Sunder Pichai, Google CEO Takes On",
            "After Republicans Corner Sunder Pichai, Google CEO Takes On", "After Republicans Corner Sunder Pichai, Google CEO Takes On", "After Republicans Corner Sunder Pichai, Google CEO Takes On",
            "After Republicans Corner Sunder Pichai, Google CEO Takes On",};

    public Popular() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_popular, container, false);
        unbinder = ButterKnife.bind(this, view);

        PopularAdapter popularAdapter = new PopularAdapter(getActivity(), newsString, this);
        newsPopularRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsPopularRv.setAdapter(popularAdapter);
        newsPopularRv.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

