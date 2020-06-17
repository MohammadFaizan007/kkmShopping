package com.digitalcashbag.common_activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitalcashbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.constants.BaseActivity;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class MaintenancePage extends BaseActivity {

    @BindView(R.id.image_main)
    ImageView imageMain;
    Bundle b;
    @BindView(R.id.textView92)
    TextView textView92;
    @BindView(R.id.textView93)
    TextView textView93;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        ButterKnife.bind(this);
        b = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        try {
            if (b != null) {
                String[] separated = b.getString("msg").split("_");
                textView92.setText(separated[1]);
                textView93.setText(separated[2]);
            }
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
        Glide.with(this).load(R.drawable.img33).into(imageMain);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
