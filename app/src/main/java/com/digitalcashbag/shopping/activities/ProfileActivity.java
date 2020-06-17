package com.digitalcashbag.shopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.mlm.fragments.profile.EditProfileMlm;
import com.digitalcashbag.shopping.fragment.ViewProfile;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.constants.BaseActivity;

public class ProfileActivity extends BaseActivity implements IPickResult {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
//    @BindView(R.id.frame)
//    FrameLayout frame;

    Fragment currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        ButterKnife.bind(this);

        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ReplaceFragment(new ViewProfile(), "Profile");
    }

    public void ReplaceFragment(Fragment setFragment, String titleStr) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, setFragment, titleStr);
        title.setText(Html.fromHtml("<medium><b>" + titleStr.toUpperCase() + "</b></medium>"));
        title.setVisibility(View.VISIBLE);
        transaction.commit();
    }

    public void ReplaceFragmentAddBack(Fragment setFragment, String titleStr) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().addToBackStack(titleStr);
        transaction.replace(R.id.frame, setFragment, titleStr);
        title.setText(Html.fromHtml("<medium><b>" + titleStr.toUpperCase() + "</b></medium>"));
        title.setVisibility(View.VISIBLE);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        currentFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        ((EditProfileMlm) currentFragment).onPickResult(pickResult);
    }
}
