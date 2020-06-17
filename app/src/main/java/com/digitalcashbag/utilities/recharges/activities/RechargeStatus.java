package com.digitalcashbag.utilities.recharges.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.digitalcashbag.shopping.activities.MainContainer;
import com.digitalcashbag.shopping.activities.ReferEarn;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.BuildConfig;
import kkm.com.core.constants.BaseActivity;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;
import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class RechargeStatus extends BaseActivity {

    Bundle bundle;
    String mobile_recharge = "", dth_recharge = "", from;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.statusImage)
    ImageView statusImage;
    @BindView(R.id.statBottom)
    TextView statBottom;
    @BindView(R.id.tvRechargeAmount)
    TextView tvRechargeAmount;
    @BindView(R.id.operator_image)
    ImageView operatorImage;
    @BindView(R.id.tvTransID)
    TextView tvTransID;
    @BindView(R.id.tvMobileNumber)
    TextView tvMobileNumber;
    @BindView(R.id.tvDateAndTime)
    TextView tvDateAndTime;
    @BindView(R.id.recharge_another_number)
    TextView rechargeAnotherNumber;
    @BindView(R.id.imageView6)
    ImageView imageView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_confirm);
        ButterKnife.bind(this);

        Glide.with(context).load(BuildConfig.BASE_URL_ICONS + "success_invite.png").
                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher))
                .into(imageView6);

        title.setText("Recharge Confirmation");
        sideMenu.setOnClickListener(v -> onBackPressed());
        try {
            bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
            if (bundle != null) {
                mobile_recharge = bundle.getString("mobile_recharge");
                dth_recharge = bundle.getString("dth_rech");
                from = bundle.getString("from");
                tvMobileNumber.setText(String.format("Mob: %s", bundle.getString("MOBNO")));
                tvRechargeAmount.setText(bundle.getString("AMT"));
                tvTransID.setText(String.format("ID: %s", bundle.getString("TRAK_ID")));
                tvDateAndTime.setText(String.format("%s%s", getResources().getString(R.string.date), bundle.getString("Date")));

                if (bundle.getString("OPERATOR__TYPE").equalsIgnoreCase("Airtel")) {
                    setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/airtel.png");
                } else if (bundle.getString("OPERATOR__TYPE").equalsIgnoreCase("Idea")) {
                    setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/idea.png");
                } else if (bundle.getString("OPERATOR__TYPE").equalsIgnoreCase("BSNLTopUp")) {
                    setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/bsnl.png");
                } else if (bundle.getString("OPERATOR__TYPE").equalsIgnoreCase("TATADocomoFlexi")) {
                    setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/tatadocomo.png");
                } else if (bundle.getString("OPERATOR__TYPE").equalsIgnoreCase("Vodafone")) {
                    setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/vodafone.png");
                } else if (bundle.getString("OPERATOR__TYPE").equalsIgnoreCase("TATADocomoSpecial")) {
                    setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/tatadocomo.png");
                } else if (bundle.getString("OPERATOR__TYPE").equalsIgnoreCase("BSNLValiditySpecial")) {
                    setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/bsnl.png");
                } else if (bundle.getString("OPERATOR__TYPE").equalsIgnoreCase("MTNLTopUP")) {
                    setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/mts.png");
                } else if (bundle.getString("OPERATOR__TYPE").equalsIgnoreCase("MTNLValiditySpecial")) {
                    setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/mts.png");
                } else if (bundle.getString("OPERATOR__TYPE").equalsIgnoreCase("RelianceDigitalTV")) {
                    setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/reliancedigitaltv.png");
                } else if (bundle.getString("OPERATOR__TYPE").equalsIgnoreCase("SUNTV")) {
                    setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/suntv.png");
                } else if (bundle.getString("OPERATOR__TYPE").equalsIgnoreCase("VideoconD2H")) {
                    setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/videocondh.png");
                } else if (bundle.getString("OPERATOR__TYPE").equalsIgnoreCase("DISHTV")) {
                    setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/dishtv.png");
                } else if (bundle.getString("OPERATOR__TYPE").equalsIgnoreCase("Jio")) {
                    setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/jio.png");
                }

                if (bundle.getString("TRANS_STATUS").equalsIgnoreCase("Failed")) {
                    statBottom.setText(String.format("₹%s %s", bundle.getString("AMT"), getResources().getString(R.string.recharge_failed)));
                    statusImage.setBackground(getResources().getDrawable(R.drawable.rchg_failed));
                } else {
                    statBottom.setText(String.format("₹%s %s", bundle.getString("AMT"), getResources().getString(R.string.recharge_successfull)));
                    statusImage.setBackground(getResources().getDrawable(R.drawable.rchg_successful));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setProviderImage(String path) {
        Glide.with(context).load(path)
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.in_utility)
                        .error(R.drawable.in_utility))
                .into(operatorImage);
    }

    @OnClick({R.id.recharge_another_number, R.id.tv_refer_friend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recharge_another_number:
                hideKeyboard();
                finish();
                overridePendingTransition(kkm.com.core.R.anim.slide_from_right, kkm.com.core.R.anim.slide_to_left);
                break;
            case R.id.tv_refer_friend:
                goToActivity(context, ReferEarn.class, null);
                break;
        }
    }

    public void goToDashboard() {
        Intent intent1 = new Intent(context, MainContainer.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
        finish();
        overridePendingTransition(kkm.com.core.R.anim.slide_from_right, kkm.com.core.R.anim.slide_to_left);
    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(context, MainContainer.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}