package com.digitalcashbag.mlm.fragments.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.CameraFragmentApi;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.github.florent37.camerafragment.listeners.CameraFragmentControlsAdapter;
import com.github.florent37.camerafragment.listeners.CameraFragmentResultAdapter;
import com.github.florent37.camerafragment.listeners.CameraFragmentStateAdapter;
import com.github.florent37.camerafragment.listeners.CameraFragmentVideoRecordTextAdapter;
import com.github.florent37.camerafragment.widgets.RecordButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraFragmentMainActivity extends AppCompatActivity {

    public static final String FRAGMENT_TAG = "camera";
    private static final int REQUEST_CAMERA_PERMISSIONS = 931;

    @BindView(R.id.record_button)
    RecordButton recordButton;


    @BindView(R.id.cameraLayout)
    View cameraLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camerafragment_activity_main);
        ButterKnife.bind(this);

        onAddCameraClicked();
    }


    @OnClick(R.id.record_button)
    public void onRecordButtonClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.takePhotoOrCaptureVideo(
                    new CameraFragmentResultAdapter() {
                        @Override
                        public void onVideoRecorded(String filePath) {
                            Toast.makeText(getBaseContext(), "onVideoRecorded " + filePath, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPhotoTaken(byte[] bytes, String filePath) {
                            Toast.makeText(CameraFragmentMainActivity.this, "onPhotoTaken " + filePath, Toast.LENGTH_SHORT).show();

                            Intent resultIntent = new Intent();
                            // TODO Add extras or a data URI to this intent as appropriate.
                            resultIntent.putExtra("data", filePath);
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        }
                    },
                    "/storage/self/primary",
                    "photo0");
        }
    }


    public void onAddCameraClicked() {
        final String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};

        final List<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[permissionsToRequest.size()]), REQUEST_CAMERA_PERMISSIONS);
        } else addCamera();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length != 0) {
            addCamera();
        }
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public void addCamera() {
        cameraLayout.setVisibility(View.VISIBLE);

        final CameraFragment cameraFragment = CameraFragment.newInstance(new Configuration.Builder()
                .setCamera(Configuration.CAMERA_FACE_FRONT).build());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, cameraFragment, FRAGMENT_TAG)
                .commitAllowingStateLoss();

        cameraFragment.setStateListener(new CameraFragmentStateAdapter() {

            @Override
            public void onCurrentCameraBack() {
            }

            @Override
            public void onCurrentCameraFront() {
            }

            @Override
            public void onFlashAuto() {
            }

            @Override
            public void onFlashOn() {
            }

            @Override
            public void onFlashOff() {
            }

            @Override
            public void onCameraSetupForPhoto() {
                recordButton.displayPhotoState();
            }

            @Override
            public void onCameraSetupForVideo() {
                recordButton.displayVideoRecordStateReady();
            }

            @Override
            public void shouldRotateControls(int degrees) {
            }

            @Override
            public void onRecordStateVideoReadyForRecord() {
                recordButton.displayVideoRecordStateReady();
            }

            @Override
            public void onRecordStateVideoInProgress() {
                recordButton.displayVideoRecordStateInProgress();
            }

            @Override
            public void onRecordStatePhoto() {
                recordButton.displayPhotoState();
            }

            @Override
            public void onStopVideoRecord() {
            }

            @Override
            public void onStartVideoRecord(File outputFile) {
            }
        });

        cameraFragment.setControlsListener(new CameraFragmentControlsAdapter() {
            @Override
            public void lockControls() {
                recordButton.setEnabled(false);
            }

            @Override
            public void unLockControls() {
                recordButton.setEnabled(true);
            }

            @Override
            public void allowCameraSwitching(boolean allow) {
            }

            @Override
            public void allowRecord(boolean allow) {
                recordButton.setEnabled(allow);
            }

            @Override
            public void setMediaActionSwitchVisible(boolean visible) {
            }
        });

        cameraFragment.setTextListener(new CameraFragmentVideoRecordTextAdapter() {
            @Override
            public void setRecordSizeText(long size, String text) {
            }

            @Override
            public void setRecordSizeTextVisible(boolean visible) {
            }

            @Override
            public void setRecordDurationText(String text) {
            }

            @Override
            public void setRecordDurationTextVisible(boolean visible) {
            }
        });
    }

    private CameraFragmentApi getCameraFragment() {
        return (CameraFragmentApi) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }
}