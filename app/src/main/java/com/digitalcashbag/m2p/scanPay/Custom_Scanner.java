package com.digitalcashbag.m2p.scanPay;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.m2p.AddMoneyToCard;
import com.google.gson.JsonObject;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Custom_Scanner extends BaseActivity implements
        DecoratedBarcodeView.TorchListener {

    protected static final int PERMISSION_CONTACTS_REQUEST_CODE = 13;
    @BindView(R.id.zxing_barcode_scanner)
    DecoratedBarcodeView barcodeScannerView;
    @BindView(R.id.switch_flashlight)
    ImageButton switchFlashlight;

    @BindView(R.id.etPhoneNumber)
    AppCompatEditText etPhoneNumber;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.btn_Pay)
    Button btn_Pay;
    @BindView(R.id.img_phonebook)
    ImageView img_phonebook;

    boolean isTorch = false;
    private CaptureManager capture;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scanner);
        ButterKnife.bind(this);

        barcodeScannerView.setTorchListener(this);

        // if the device does not have flashlight in its camera,
        // then remove the switch flashlight button...

        if (!hasFlash()) {
            switchFlashlight.setVisibility(View.GONE);
        }

        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etPhoneNumber.getText().toString().length() == 10) {
                    hideKeyboard();
                    if (etPhoneNumber.getText().toString().trim().equalsIgnoreCase(PreferencesManager.getInstance(context).getMOBILE())) {
                        txtName.setText("You can not transfer money to your own account.");
                        enableOrDisableView(false);
                    } else {
                        getM2pUser(etPhoneNumber.getText().toString().trim());
                    }

                } else {
                    enableOrDisableView(false);
                    txtName.setText("");
                }
            }
        });
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(this.getIntent(), savedInstanceState);
        capture.decode();

        img_phonebook.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED) {
                hideKeyboard();
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contactPickerIntent, 1);
            } else {
                requestContactPermission();
            }
        });


    }

    public void requestContactPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
            Utils.createSimpleDialog1(this, getString(R.string.alert_text), getString(R.string.permission_camera_rationale11), getString(R.string.reqst_permission), () -> ActivityCompat.requestPermissions(Custom_Scanner.this, new String[]{
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_CONTACTS},
                    PERMISSION_CONTACTS_REQUEST_CODE));

        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_CONTACTS},
                    PERMISSION_CONTACTS_REQUEST_CODE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }


    /**
     * Check if the device's camera has a Flashlight.
     *
     * @return true if there is Flashlight, otherwise false.
     */
    private boolean hasFlash() {
        return getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public void switchFlashlight(View view) {
        if (!isTorch) {
            barcodeScannerView.setTorchOn();
        } else {
            barcodeScannerView.setTorchOff();
        }
    }

    @Override
    public void onTorchOn() {
        isTorch = true;
        switchFlashlight.setImageResource(R.drawable.ic_flash_on);
    }

    @Override
    public void onTorchOff() {
        isTorch = false;
        switchFlashlight.setImageResource(R.drawable.ic_flash_off);
    }


    private void getM2pUser(String mobileNo) {
        showLoading();
        hideKeyboard();
        String url = BuildConfig.BASE_URL_MLM + "CheckM2PUser?Mobile=" + mobileNo;
        LoggerUtil.logItem(url);
        Call<JsonObject> call = apiServices.checkM2pUser(url);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
//                {
//                    "response": "Success",
//                        "entityId": "202204",
//                        "name": "Mr Anand Verma"
//                }
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body() != null && response.body().get("response").getAsString().equalsIgnoreCase("Success")) {

                    bundle.putString("name", response.body().get("name").getAsString());
                    bundle.putString("entityId", response.body().get("entityId").getAsString());
                    bundle.putString("mobileno", mobileNo);

                    txtName.setText(response.body().get("name").getAsString());


                    enableOrDisableView(true);

                    btn_Pay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToActivityWithFinish(Custom_Scanner.this, AddMoneyToCard.class, bundle);
                            finishAffinity();
                        }
                    });
                } else {
                    txtName.setText("Invalid User");
                    enableOrDisableView(false);
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                hideLoading();
            }
        });

    }

    private void enableOrDisableView(boolean bool) {
        btn_Pay.setEnabled(bool);
        btn_Pay.setClickable(bool);
        btn_Pay.setFocusable(bool);
        btn_Pay.setFocusableInTouchMode(bool);
        if (bool) {
            btn_Pay.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        } else {
            btn_Pay.setBackgroundColor(ContextCompat.getColor(context, R.color.text_color_light));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        try {
            switch (requestCode) {
                case 1:
                    Uri contactData = data.getData();
                    assert contactData != null;
                    Cursor cur = getContentResolver().query(contactData, null, null, null, null);
                    assert cur != null;
                    if (cur.getCount() > 0) {// thats mean some resutl has been found
                        if (cur.moveToNext()) {
                            String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                            String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            Log.d("Names", name);
                            if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                                assert phones != null;
                                while (phones.moveToNext()) {
                                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                                    String onlyNumber = phoneNumber.replaceAll( "[^0-9]", "" );
                                    Log.d("Number", phoneNumber);
                                    etPhoneNumber.setText("");
                                    etPhoneNumber.setText(String.format("+91 %s", modifyNumber(phoneNumber.replaceAll(" ", ""))));
                                    etPhoneNumber.setSelection(etPhoneNumber.getText().toString().length());
                                }
                                phones.close();
                            }
                        }
                    }
                    cur.close();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            etPhoneNumber.setText("");
            showMessage("Invalid number.");
        }
    }

    private String modifyNumber(String num) {
        if (num.startsWith("91")) {
            num = num.replaceFirst("91", "");
        } else if (num.startsWith("+91")) {
            num = num.replaceFirst("\\+(91)", "");
        } else if (num.startsWith("0")) {
            num = num.replaceFirst("0", "");
        }
        return num;
    }

}
