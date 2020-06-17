package com.digitalcashbag.utilities.tatasky;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.activities.MainContainer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.constants.BaseActivity;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class TataskyBookingStatus extends BaseActivity {


    @BindView(R.id.statusImage)
    ImageView statusImage;
    @BindView(R.id.tvstatTop)
    TextView tvstatTop;
    @BindView(R.id.constOne)
    ConstraintLayout constOne;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tvErrorMsge)
    TextView tvErrorMsge;
    @BindView(R.id.tvSession)
    TextView tvSession;
    @BindView(R.id.statBox)
    TextView statBox;
    @BindView(R.id.tvOperatorName)
    TextView tvOperatorName;
    @BindView(R.id.goto_dashboard)
    TextView gotoDashboard;
    @BindView(R.id.recharge_another_number)
    TextView rechargeAnotherNumber;
    @BindView(R.id.constTwo)
    ConstraintLayout constTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tatasky_status);
        ButterKnife.bind(this);
        try {
            Bundle bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
            if (bundle != null) {
                tvDate.setText(getResources().getString(R.string.date) + bundle.getString("Date"));
                if (bundle.getString("TRANS_STATUS").equalsIgnoreCase("Failed")) {
                    statBox.setText(getResources().getString(R.string.failed));
                    tvstatTop.setText("Booking Failed");
                    statBox.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                    statusImage.setBackground(getResources().getDrawable(R.drawable.failed));
                    constOne.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                } else {
                    statBox.setText(getResources().getString(R.string.completed));
                    tvstatTop.setText("Booked Successfully");
                    statBox.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
                    statusImage.setBackground(getResources().getDrawable(R.drawable.successful));
                    constOne.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.goto_dashboard, R.id.recharge_another_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goto_dashboard:
                onBackPressed();
                break;
            case R.id.recharge_another_number:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(context, MainContainer.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
        finish();
        overridePendingTransition(kkm.com.core.R.anim.slide_from_right, kkm.com.core.R.anim.slide_to_left);
    }

}