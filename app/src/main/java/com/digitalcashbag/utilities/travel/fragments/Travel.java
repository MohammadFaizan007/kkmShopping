package com.digitalcashbag.utilities.travel.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kkm.com.core.utils.Utils;

public class Travel extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rg_tickettype)
    RadioGroup rg_tickettype;
    @BindView(R.id.rb_bookticket)
    RadioButton rb_bookticket;
    @BindView(R.id.rb_checkpnr)
    RadioButton rb_checkpnr;
    @BindView(R.id.search_train_btn)
    Button searchtrainbtn;
    @BindView(R.id.input_journy_date)
    TextInputLayout inputjournydate;
    @BindView(R.id.et_journy_date)
    TextInputEditText etjournydate;
    @BindView(R.id.et_from)
    TextInputEditText etfrom;
    @BindView(R.id.input_layout_from)
    TextInputLayout inputlayoutfrom;
    @BindView(R.id.et_to)
    TextInputEditText etto;
    @BindView(R.id.input_layout_to)
    TextInputLayout inputlayoutto;
    @BindView(R.id.btn_exchange)
    ImageButton btnexchange;
    @BindView(R.id.book_ticket_lo)
    ConstraintLayout bookTicketLo;
    @BindView(R.id.et_pnr_no)
    TextInputEditText etPnrNo;
    @BindView(R.id.input_pnr_no)
    TextInputLayout inputPnrNo;
    @BindView(R.id.check_pnr_btn)
    Button checkPnrBtn;
    @BindView(R.id.pnr_lo)
    ConstraintLayout pnrLo;

    long departInMilli = 0;
    String from_st = "", to_st = "", journy_date_st = "", pnr_number_st = "";

    public Travel() {
        // Required empty public constructor
    }

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.travel, container, false);
        unbinder = ButterKnife.bind(this, view);
        departInMilli = Calendar.getInstance().getTimeInMillis();

        rg_tickettype.setOnCheckedChangeListener((radioGroup, i) -> {
            if (rb_bookticket.isChecked()) {
                bookTicketLo.setVisibility(View.VISIBLE);
                pnrLo.setVisibility(View.GONE);
            } else if (rb_checkpnr.isChecked()) {
                bookTicketLo.setVisibility(View.GONE);
                pnrLo.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @OnClick({R.id.search_train_btn, R.id.et_journy_date, R.id.btn_exchange, R.id.check_pnr_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_train_btn:
                if (Validation()) {
                    hideKeyboard(getActivity());
                    Toast.makeText(getActivity(), getResources().getString(R.string.avalable_train_to) + from_st, Toast.LENGTH_SHORT).show();
//                    finish();
                }
                break;
            case R.id.et_journy_date:
                datePicker(etjournydate, true);
                break;
            case R.id.btn_exchange:
                String code, city;
                code = etfrom.getText().toString();
                city = etto.getText().toString();
                etfrom.setText(city);
                etto.setText(code);
                break;
            case R.id.check_pnr_btn:
                if (ValidationPnr()) {
                    hideKeyboard(getActivity());
//                    Toast.makeText( getActivity(), "Status of your PNR number : " + pnr_number_st + " is confirmed", Toast.LENGTH_SHORT ).show();
                }
                break;
        }
    }

    private void datePicker(final TextView et, final boolean depart) {
        Calendar cal = Calendar.getInstance();
        int mYear, mMonth, mDay;
        if (!depart) {
            cal.setTimeInMillis(departInMilli);
        }
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                et.setText(Utils.changeDateFormat(dayOfMonth, monthOfYear, year));
                if (depart) {
                    Calendar departcal = Calendar.getInstance();
                    departcal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    departcal.set(Calendar.MONTH, monthOfYear);
                    departcal.set(Calendar.YEAR, year);
                    departInMilli = departcal.getTimeInMillis();
                }
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 100:
                    etfrom.setText(data.getStringExtra("CODE"));
                    etto.setText(data.getStringExtra("CITY"));
                    break;
                case 101:
                    etto.setText(data.getStringExtra("CITY"));
                    etfrom.setText(data.getStringExtra("CODE"));
                    break;
            }
        }
    }

    private boolean Validation() {
        try {
            from_st = etfrom.getText().toString();
            to_st = etto.getText().toString();
            journy_date_st = etjournydate.getText().toString();

            if (from_st.length() == 0) {
                showError(getResources().getString(R.string.source_location_err), etfrom);
                return false;
            }
            if (to_st.length() == 0) {
                showError(getResources().getString(R.string.destination_err), etto);
                return false;
            }

            if (from_st.length() == to_st.length()) {
                showError(getResources().getString(R.string.same_location_error), etto);
                return false;
            }

            if (journy_date_st.length() == 0) {
                showError(getResources().getString(R.string.select_journey_date_err), etto);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean ValidationPnr() {
        try {
            pnr_number_st = etPnrNo.getText().toString();
            if (pnr_number_st.length() == 0) {
                showError(getResources().getString(R.string.pnr_number_err), etPnrNo);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void showError(String error_st, EditText editText) {
        Dialog error_dialog = new Dialog(getActivity());
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

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}

