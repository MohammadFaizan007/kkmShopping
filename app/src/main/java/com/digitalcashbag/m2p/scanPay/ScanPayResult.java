package com.digitalcashbag.m2p.scanPay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.digitalcashbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.utils.Utils;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class ScanPayResult extends BaseActivity {

    @BindView(R.id.img_trans_status)
    ImageView imgTransStatus;
    @BindView(R.id.tv_trans_status)
    TextView tvTransStatus;
    @BindView(R.id.tv_trans_date)
    TextView tvTransDate;
    @BindView(R.id.bg_view)
    ConstraintLayout bgView;
    @BindView(R.id.transaction_id)
    TextView transactionId;
    @BindView(R.id.tv_name_letters)
    TextView tvNameLetters;
    @BindView(R.id.tv_ben_name)
    TextView tvBenName;
    @BindView(R.id.tv_ben_mobile)
    TextView tvBenMobile;
    @BindView(R.id.tv_trans_amount)
    TextView tvTransAmount;
    @BindView(R.id.img_share)
    TextView imgShare;
    @BindView(R.id.benCard)
    CardView benCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanned_pay_transaction_result);
        ButterKnife.bind(this);
        Bundle b = getIntent().getBundleExtra(PAYLOAD_BUNDLE);

        if (b != null) {

            if (b.getString("from").equalsIgnoreCase("AddFundToSelf") || b.getString("from").equalsIgnoreCase("AddFundToOther")) {

//                {
//                    "response": "success",
//                        "result": {
//                    "result": {
//                        "txId": 12345678901
//                    },
//                    "exception": null,
//                            "pagination": null
//                }
//                }
                benCard.setVisibility(View.GONE);
                JsonObject convertedObject = new Gson().fromJson(b.getString("response"), JsonObject.class);
                if (convertedObject.get("response").getAsString().equalsIgnoreCase("Success")) {

                    JsonObject result = convertedObject.getAsJsonObject("result").getAsJsonObject("result");
                    transactionId.setText(result.get("txId").getAsString());
                    tvTransStatus.setText("Transaction Successful");
                    tvTransDate.setText(Utils.getTodayDatetime());
                    bgView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    imgTransStatus.setImageResource(R.drawable.successful);
                } else {
                    tvTransStatus.setText("Transaction Failed");
                    tvTransDate.setText(Utils.getTodayDatetime());
                    bgView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_red));
                    imgTransStatus.setImageResource(R.drawable.failed);
                }

            } else if (b.getString("from").equalsIgnoreCase("ScanActivity")) {
                benCard.setVisibility(View.VISIBLE);
                tvBenName.setText(b.getString("name"));
                tvBenMobile.setText(b.getString("mobile"));
                tvTransAmount.setText(b.getString("amount"));

                JsonObject convertedObject = new Gson().fromJson(b.getString("response"), JsonObject.class);

//        {
//            "response": "success",
//                "result": {
//                      "result": {
//                        "txId": 12345678901,
//                        "retrivalReferenceNo": "704618883422",
//                        "authCode": "717089",
//                        "action": "Success"
//                                 }
//                          },
//            "exception": null,
//            "pagination": null
//        }
                if (convertedObject.get("response").getAsString().equalsIgnoreCase("Success")) {
                    JsonObject result = convertedObject.getAsJsonObject("result").getAsJsonObject("result");
                    transactionId.setText(result.get("txId").getAsString());
                    tvTransStatus.setText("Transaction Successful");
                    tvTransDate.setText(Utils.getTodayDatetime());
                    bgView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    imgTransStatus.setImageResource(R.drawable.successful);
                } else {
                    tvTransStatus.setText("Transaction Failed");
                    tvTransDate.setText(Utils.getTodayDatetime());
                    bgView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_red));
                    imgTransStatus.setImageResource(R.drawable.failed);
                }
            }

        }


    }

    @OnClick(R.id.btn_done)
    public void onViewClicked() {
        onBackPressed();
    }
}
