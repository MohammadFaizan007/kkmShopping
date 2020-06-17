package com.digitalcashbag.common_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.constants.LocaleManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.constants.BaseActivity;

public class SelectLanguage extends BaseActivity {

    @BindView(R.id.english)
    TextView english;
    @BindView(R.id.hindi)
    TextView hindi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_language);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.english, R.id.hindi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.english:
                LocaleManager.setNewLocale(context, LocaleManager.LANGUAGE_ENGLISH);
                goToActivityWithFinish(SelectLanguage.this, Permission.class, null);
                break;
            case R.id.hindi:
                LocaleManager.setNewLocale(context, LocaleManager.LANGUAGE_HINDI);
                goToActivityWithFinish(SelectLanguage.this, Permission.class, null);
                break;
        }
    }

}
