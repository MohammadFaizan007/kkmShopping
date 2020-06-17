package kkm.com.core.constants;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tapadoo.alerter.Alerter;

import java.util.Date;
import java.util.Locale;
import java.util.Random;

import kkm.com.core.R;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.DialogUtil;
import kkm.com.core.utils.Utils;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;


public abstract class BaseFragment extends Fragment implements MvpView {
    // Toolbar toolbar;
    protected static final int ASK_SEND_SMS_PERMISSION_REQUEST_CODE = 14;
    private static final String TAG = "BaseFragment";
    protected final Gson gson = new Gson();
    public Activity context;
    public ApiServices apiServices, apiServices_shoopping, apiServices_utility/*, apiServicesM2P*/;
    public ServiceGenerator serviceGenerator;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        serviceGenerator = ServiceGenerator.getInstance();
        apiServices = ServiceGenerator.createService(ApiServices.class);
        apiServices_shoopping = ServiceGenerator.createServiceShopping(ApiServices.class);
        apiServices_utility = ServiceGenerator.createServiceUtility(ApiServices.class);
//        apiServicesM2P = ServiceGenerator.createServiceM2P(ApiServices.class);
    }

    @Override
    public void getPayoutWithdrawalId(String requestId) {

    }

    @Override
    public void openSearchCategory(String searchItemId, String searchName) {

    }

    @Override
    public void checkAvailability(String id, String date, String name, String amount) {

    }

    @Override
    public void getClickPosition(int position, String tag) {

    }

    @Override
    public void getGiftCardCategoryId(String id, String name) {

    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    public void onError(int resId) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void getDateDetails(Date date) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PreferencesManager.initializeInstance(getContext());
        onViewCreatedStuff(view, savedInstanceState);
    }

    public void showAlert(String msg, int color, int icon) {
        Alerter.create(context).setText(msg).setTextAppearance(R.style.alertTextColor).setBackgroundColorRes(color).setIcon(icon).show();
    }

    public abstract void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState);

    public String generatePin() {
        Random random = new Random();
        return String.format(Locale.ENGLISH, "%04d", random.nextInt(10000));
    }

    public void showLoading() {
//        if (mProgressDialog != null) {
        mProgressDialog = DialogUtil.showLoadingDialog(getActivity(), TAG);
//        }
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void createInfoDialog(Context context, String title, String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setNegativeButton("OK", (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void goToActivity(Class<?> classActivity, Bundle bundle) {
        Utils.hideSoftKeyboard(getActivity());
        Intent intent = new Intent(getContext(), classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        getActivity().startActivity(intent);
//        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void goToActivityWithFinish(Class<?> classActivity, Bundle bundle) {
        Intent intent = new Intent(getContext(), classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        Utils.hideSoftKeyboard(getActivity());
        getActivity().startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void checkSMSPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
            requestSMSPermission();
    }

    public void requestSMSPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.SEND_SMS) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_SMS) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.RECEIVE_SMS)) {
            Utils.createSimpleDialog1(getActivity(), getString(R.string.alert_text), getString(R.string.permission_camera_rationale11), getString(R.string.reqst_permission), new Utils.Method() {
                @Override
                public void execute() {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, ASK_SEND_SMS_PERMISSION_REQUEST_CODE);
                }
            });
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, ASK_SEND_SMS_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getClickPositionDirectMember(int position, String tag, String memberId) {

    }
}
