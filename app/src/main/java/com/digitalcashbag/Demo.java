package com.digitalcashbag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.shopping.activities.MainContainer;
import com.digitalcashbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;


public class Demo extends AppCompatActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    Bundle param;

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        title.setText(param.getString("bottom_view"));
    }

    @OnClick({R.id.side_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                hideKeyboard(this);
                Intent intent = new Intent(this, MainContainer.class);
                param.putString("bottom_view", getResources().getString(R.string.title_home));
                intent.putExtra("android.intent.extra.INTENT", param);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        hideKeyboard(this);
        Intent intent = new Intent(this, MainContainer.class);
        param.putString("bottom_view", getResources().getString(R.string.title_home));
        intent.putExtra(PAYLOAD_BUNDLE, param);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}