package kkm.com.core.constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;

import java.util.Date;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import kkm.com.core.BuildConfig;
import kkm.com.core.R;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.DialogUtil;
import kkm.com.core.utils.NetworkConnectionChecker;
import kkm.com.core.utils.Utils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;


public abstract class BaseActivity extends AppCompatActivity implements NetworkConnectionChecker.OnConnectivityChangedListener, View.OnClickListener, MvpView {
    private static final String TAG = "BaseActivity";
    public ApiServices apiServices, apiServicesMLMV2, apiServices_shoopping, apiServices_utility, apiServices_utilityV2, apiServicesM2PV2;
    public ServiceGenerator serviceGenerator;
    public Activity context;
    public boolean isConnected;
    private ProgressDialog mProgressDialog;

    String keys = BuildConfig.CASHBAG_KEY;
    public SecretKey easypay_key;

    public static void finishActivity(Activity activity) {
        Utils.hideSoftKeyboard(activity);
        activity.finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        easypay_key = new SecretKeySpec(keys.getBytes(), "AES");
        if (PreferencesManager.getInstance(context).getANDROIDID().equalsIgnoreCase("")) {
            PreferencesManager.getInstance(context).setANDROIDID(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
            Log.e("Android", "Android ID =======: " + PreferencesManager.getInstance(context).getANDROIDID());
        }
        serviceGenerator = ServiceGenerator.getInstance();
        apiServices = ServiceGenerator.createService(ApiServices.class);
        apiServicesMLMV2 = ServiceGenerator.createServiceMLMV2(ApiServices.class);
        apiServicesM2PV2 = ServiceGenerator.createServiceM2PV2(ApiServices.class);
        apiServices_shoopping = ServiceGenerator.createServiceShopping(ApiServices.class);
        apiServices_utility = ServiceGenerator.createServiceUtility(ApiServices.class);
//        apiServicesM2P = ServiceGenerator.createServiceM2P(ApiServices.class);
        apiServices_utilityV2 = ServiceGenerator.createServiceUtilityV2(ApiServices.class);
        new DialogUtil(BaseActivity.this);

    }

    @Override
    public void getTrainingID(String trainingamount, String trainingname, String trainingID) {

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
    public void getGiftCardCategoryId(String id, String name) {

    }


    public void goToActivityWithFinish(Class<?> classActivity, Bundle bundle) {
        Intent intent = new Intent(context, classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        Utils.hideSoftKeyboard(context);
        context.startActivity(intent);
        context.finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public String generatePin() {
        Random random = new Random();
        @SuppressLint("DefaultLocale") String randomPIN = String.format("%04d", random.nextInt(10000));
        return randomPIN;
    }

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showLoading() {
        mProgressDialog = DialogUtil.showLoadingDialog(context, "Base Activity");
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
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
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public boolean isNetworkConnected() {
        return isConnected;
    }

    public void showAlert(String msg, int color, int icon) {
        Alerter.create(context).setText(msg).setTextAppearance(R.style.alertTextColor).setBackgroundColorRes(color).setIcon(icon).show();
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void getDateDetails(Date date) {

    }

    @Override
    public void getClickPosition(int position, String tag) {

    }

    @Override
    public void connectivityChanged(boolean availableNow) {
        isConnected = availableNow;
    }

    public void createInfoDialog(Context context, String title, String msg) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setNegativeButton("OK", (dialog, id) -> {
            dialog.cancel();
            finish();
        });

        android.support.v7.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void hideSoftKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View v = activity.getCurrentFocus();
        if (v != null) {
            IBinder binder = activity.getCurrentFocus().getWindowToken();
            if (binder != null) {
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(binder, 0);
                }
            }
        }
    }

    public void goToActivity(Activity activity, Class<?> classActivity, Bundle bundle) {
        Utils.hideSoftKeyboard(activity);
        Intent intent = new Intent(activity, classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        activity.startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void goToActivityWithFinish(Activity activity, Class<?> classActivity, Bundle bundle) {
        Intent intent = new Intent(context, classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        Utils.hideSoftKeyboard(activity);
        activity.startActivity(intent);
        activity.finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void getClickPositionDirectMember(int position, String tag, String memberId) {

    }

    @Override
    public void setTransferTicket(String trainingamount, String trainingname, String trainingID, String totalTickets, String date) {

    }
}