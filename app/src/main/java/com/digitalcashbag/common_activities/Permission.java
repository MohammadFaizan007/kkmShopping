package com.digitalcashbag.common_activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.activities.MainContainer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.Utils;

public class Permission extends BaseActivity {

    protected static final int PERMISSION_REQUEST = 15;
    @BindView(R.id.i_understand_btn)
    Button iUnderstandBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.i_understand_btn)
    public void onViewClicked() {
        if (hasPermission())
            goToActivityWithFinish(Permission.this, MainContainer.class, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST) {
            if (permissions.length == grantResults.length) {
                LoggerUtil.logItem("FROM result");
                PreferencesManager.getInstance(context).setfirst_visit(false);
                goToActivityWithFinish(Permission.this, MainContainer.class, null);
            } else {
                hasPermission();
            }
        }


    }

    public boolean hasPermission() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
            LoggerUtil.logItem(false);
            return false;
        } else {
            LoggerUtil.logItem(false);
            return true;
        }
    }

    public void requestPermission() {
        if (
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
//                 ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Utils.createSimpleDialog1(context, getString(kkm.com.core.R.string.alert_text), getString(kkm.com.core.R.string.permission_camera_rationale11), getString(kkm.com.core.R.string.reqst_permission), new Utils.Method() {
                @Override
                public void execute() {
                    ActivityCompat.requestPermissions(context, new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                                    Manifest.permission.CALL_PHONE,
                                    Manifest.permission.READ_CONTACTS,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA},
                            PERMISSION_REQUEST);
                }
            });

        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    PERMISSION_REQUEST);
        }
    }

}
