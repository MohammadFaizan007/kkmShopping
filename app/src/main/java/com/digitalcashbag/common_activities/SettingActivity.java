package com.digitalcashbag.common_activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.rate_app)
    TextView rateApp;
    @BindView(R.id.terms_condition)
    TextView termsCondition;
    @BindView(R.id.m2p_customer_update)
    TextView m2pCustomerUpdate;
    @BindView(R.id.m2p_card_detail)
    TextView m2pCardDetail;
    @BindView(R.id.m2p_card_replace)
    TextView m2pCardReplace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.setting_text));
    }


    @OnClick({R.id.side_menu, R.id.m2p_customer_update, R.id.m2p_card_detail, R.id.m2p_card_replace})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                finish();
                break;
            case R.id.m2p_customer_update:
                break;
            case R.id.m2p_card_detail:
                break;
            case R.id.m2p_card_replace:
                break;
        }
    }
}
