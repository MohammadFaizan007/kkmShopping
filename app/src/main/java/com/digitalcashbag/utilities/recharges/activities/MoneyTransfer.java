package com.digitalcashbag.utilities.recharges.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.utilities.recharges.adapter.MoneyTransferAccountListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.utils.Utils;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class MoneyTransfer extends BaseActivity {


    protected static final int PERMISSION_CONTACTS_REQUEST_CODE = 13;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_upi_id)
    TextInputEditText etUpiId;
    @BindView(R.id.input_layout_upi_id)
    TextInputLayout inputLayoutUpiId;
    @BindView(R.id.et_ifsc_code)
    TextInputEditText etIfscCode;
    @BindView(R.id.input_layout_ifsc_code)
    TextInputLayout inputLayoutIfscCode;
    @BindView(R.id.et_acc_holder_name)
    TextInputEditText etAccHolderName;
    @BindView(R.id.input_layout_acc_holder_name)
    TextInputLayout inputLayoutAccHolderName;
    @BindView(R.id.proceed_btn)
    Button proceedBtn;
    @BindView(R.id.view_all)
    TextView viewAll;
    @BindView(R.id.rv_upi_saved_account)
    RecyclerView rvUpiSavedAccount;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.rv_saved_account)
    RecyclerView rvSavedAccount;
    Bundle param;
    ArrayList<String> nameList, saved_acc_nameList;
    ArrayList<String> numberList, saved_acc_idList;
    private String account_nnumber_st = "", ifsc_code_st = "", name_st = "";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_transfer);
        ButterKnife.bind(this);
        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        title.setText(param.getString("from"));

        nameList = new ArrayList<String>();
        numberList = new ArrayList<String>();

        etUpiId.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() > 0) {
                    inputLayoutIfscCode.setVisibility(View.VISIBLE);
                    inputLayoutAccHolderName.setVisibility(View.VISIBLE);
                    proceedBtn.setVisibility(View.VISIBLE);
                } else {
                    inputLayoutIfscCode.setVisibility(View.GONE);
                    inputLayoutAccHolderName.setVisibility(View.GONE);
                    proceedBtn.setVisibility(View.GONE);
                }
            }
        });

        etUpiId.setOnTouchListener((v, event) -> {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (etUpiId.getRight() - etUpiId.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                            == PackageManager.PERMISSION_GRANTED) {
                        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(contactPickerIntent, 1);
                    } else {
                        requestContactPermission();
                    }
                    return true;
                }
            }
            return false;
        });

//        try {
//            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
//            assert phones != null;
//            while (phones.moveToNext()) {
//                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//                numberList.add(phoneNumber);
//                nameList.add(name);
//
//                Log.d("name>>", name + "  " + phoneNumber);
//            }
//            phones.close();
//
//            rvSavedAccount.setHasFixedSize(true);
//            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//            rvSavedAccount.setLayoutManager(layoutManager);
//            rvSavedAccount.setItemAnimator(new DefaultItemAnimator());
//            MoneyTransferContactListAdapter adapter = new MoneyTransferContactListAdapter(nameList, numberList);
//            rvSavedAccount.setAdapter(adapter);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /* Save Account List*/
        saved_acc_nameList = new ArrayList<>();
//        saved_acc_nameList.add("Shri Kishan");
//        saved_acc_nameList.add("Anand Verma");
        saved_acc_idList = new ArrayList<>();
//        saved_acc_idList.add("shrikishan@kkm");
//        saved_acc_idList.add("anandverma@kkm");

        rvUpiSavedAccount.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        rvUpiSavedAccount.setLayoutManager(layoutManager1);
        rvUpiSavedAccount.setItemAnimator(new DefaultItemAnimator());
        MoneyTransferAccountListAdapter adapter2 = new MoneyTransferAccountListAdapter(saved_acc_nameList, saved_acc_idList);
        rvUpiSavedAccount.setAdapter(adapter2);

    }


    @OnClick({R.id.side_menu, R.id.proceed_btn, R.id.view_all, R.id.search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.proceed_btn:
                if (Validation()) {
                    hideKeyboard();
                    param.putString("money_transfer", getResources().getString(R.string.money_tgransfer));
                    goToActivity(MoneyTransfer.this, MoneyTransfer_TransferRupee.class, param);
                }
                break;
            case R.id.view_all:
                break;
            case R.id.search:
                break;
        }
    }

    private boolean Validation() {
        try {
            account_nnumber_st = etUpiId.getText().toString();
            ifsc_code_st = etIfscCode.getText().toString();
            name_st = etAccHolderName.getText().toString();
            if (account_nnumber_st.length() == 0) {
                showError(getResources().getString(R.string.upi_id_err), etUpiId);
                return false;
            }
            if (ifsc_code_st.length() == 0) {
                showError(getResources().getString(R.string.ifsc_code_err), etIfscCode);
                return false;
            }
            if (name_st.length() == 0) {
                showError(getResources().getString(R.string.account_holder_name_err), etAccHolderName);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void requestContactPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
            Utils.createSimpleDialog1(this, getString(R.string.alert_text), getString(R.string.permission_camera_rationale11), getString(R.string.reqst_permission), () -> ActivityCompat.requestPermissions(MoneyTransfer.this, new String[]{
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

    private void showError(String error_st, EditText editText) {
        Dialog error_dialog = new Dialog(this);
        error_dialog.setCanceledOnTouchOutside(false);
        error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        error_dialog.setContentView(R.layout.error_dialoge);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        error_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView error_text = error_dialog.findViewById(R.id.error_text);
        Button ok_btn = error_dialog.findViewById(R.id.ok_btn);
        error_text.setText(error_st);
        error_dialog.show();
        ok_btn.setOnClickListener(view -> {
            error_dialog.dismiss();
            requestFocus(editText);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case 1:
                Uri contactData = data.getData();
                Cursor cur = getContentResolver().query(contactData, null, null, null, null);
                if (cur.getCount() > 0) {// thats mean some resutl has been found
                    if (cur.moveToNext()) {
                        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        Log.d("Names", name);
                        if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                            while (phones.moveToNext()) {
                                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                                    String onlyNumber = phoneNumber.replaceAll( "[^0-9]", "" );
                                Log.d("Number", phoneNumber);
                                etUpiId.setText("");
                                etUpiId.setText("+91 " + modifyNumber(phoneNumber.replaceAll(" ", "")));
                                etUpiId.setSelection(14);
//                                    etMobNo.setText( onlyNumber );
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
    }

    private String modifyNumber(String num) {
        if (num.startsWith("91")) {
            num = num.replaceFirst("91", "");
        } else if (num.startsWith("+91")) {
            num = num.replaceFirst("\\+(91)", "");
        } else if (num.startsWith("0")) {
            num = num.replaceFirst("0", "");
        }
        return num.trim();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
