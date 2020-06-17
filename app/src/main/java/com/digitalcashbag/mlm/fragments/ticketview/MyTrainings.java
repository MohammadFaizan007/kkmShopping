package com.digitalcashbag.mlm.fragments.ticketview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.mlm.adapter.AllTrainingAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.bookTrainingTicket.RequestBookTrainingTicket;
import kkm.com.core.model.request.ticketAllotment.RequestTrainingAllotment;
import kkm.com.core.model.request.ticketAllotment.ResponseTicketAllotment;
import kkm.com.core.model.request.ticketAllotment.TrainingItems;
import kkm.com.core.model.response.ResponsePincodeDetail;
import kkm.com.core.model.response.bookTrainingTicket.ResponseBookTrainingTicket;
import kkm.com.core.model.response.getMyTraining.EventDetailsItem;
import kkm.com.core.model.response.getMyTraining.ResponseMyTrainings;
import kkm.com.core.model.response.memberNameFromMobile.ResponseName;
import kkm.com.core.model.response.memberNameFromMobile.ResultItem;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.BuildConfig.DYNAMIC_LINK;

public class MyTrainings extends BaseActivity implements MvpView {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.training_list)
    RecyclerView trainingList;
    @BindView(R.id.title)
    TextView title;
    Dialog booking_dialog, transfer_dialog;
    private ArrayList<TrainingItems> trainingItems = new ArrayList<TrainingItems>();
    ResultItem resultItem = new ResultItem();
    public static TextView tv_avail_ticket;
    public static int totalTicketsAll;
    List<EventDetailsItem> detailsItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_trainings);
        ButterKnife.bind(this);
        title.setText("Cashbag Trainings");
        sideMenu.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        trainingList.setLayoutManager(manager);
        try {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getTrainingNames();
            } else {
                createInfoDialog(context, "Alert", getString(R.string.alert_internet));
            }
        } catch (Error | Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.side_menu:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void getTrainingNames() {
        showLoading();
        String url = BuildConfig.BASE_URL_MLM + "GetEventData?LoginId=" + PreferencesManager.getInstance(context).getLoginID();
        Log.e("URL===== ", url);
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        Call<ResponseMyTrainings> call = apiServices.getMyTrainingsCall(url);
        call.enqueue(new Callback<ResponseMyTrainings>() {
            @Override
            public void onResponse(@NotNull Call<ResponseMyTrainings> call, @NotNull Response<ResponseMyTrainings> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                            AllTrainingAdapter shoppingOtherOrderAdapter = new AllTrainingAdapter(context,
                                    response.body().getEventDetails(), MyTrainings.this);
                            detailsItems = response.body().getEventDetails();
                            trainingList.setAdapter(shoppingOtherOrderAdapter);
                            Log.e("SIZEEEE AA- ", "" + (response.body().getEventDetails()).size());
                        } else {
                            showAlert("Something went wrong.", R.color.red, R.drawable.error);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseMyTrainings> call, @NotNull Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void getTrainingID(String trainingamount, String trainingname, String trainingID) {
        super.getTrainingID(trainingamount, trainingname, trainingID);
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            BookingDialog(trainingamount, trainingname, trainingID);
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    private void BookingDialog(String trainingamount, String trainingname, String trainingID) {
        try {
            hideKeyboard();
            booking_dialog = new Dialog(context);
            booking_dialog.setCanceledOnTouchOutside(true);
            booking_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            booking_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            booking_dialog.setContentView(R.layout.booking_dialog);
            booking_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            booking_dialog.show();

            Button btn_submit = booking_dialog.findViewById(R.id.btn_submit);
            Button btn_cancel = booking_dialog.findViewById(R.id.btn_cancel);
            EditText et_name = booking_dialog.findViewById(R.id.et_name);
            EditText et_mobile = booking_dialog.findViewById(R.id.et_mobile);
            EditText et_pincode = booking_dialog.findViewById(R.id.et_pincode);
            TextView ticket_amount = booking_dialog.findViewById(R.id.ticket_amount);
            TextView event_name = booking_dialog.findViewById(R.id.event_name);
            TextView city = booking_dialog.findViewById(R.id.city);
            event_name.setText("Insert details for booking event :\n" + trainingname);
            ticket_amount.setText("Confirmation Amount : ₹" + trainingamount);

            btn_cancel.setOnClickListener(v -> booking_dialog.dismiss());

            btn_submit.setOnClickListener(v -> {
                if (et_name.getText().toString().trim().length() != 0 &&
                        et_mobile.getText().toString().trim().length() != 0 &&
                        et_mobile.getText().toString().trim().length() == 10 &&
                        et_pincode.getText().toString().trim().length() != 0 &&
                        et_pincode.getText().toString().trim().length() == 6) {
                    Log.e("=======Submit -- ", et_name.getText().toString().trim() + " " +
                            et_mobile.getText().toString().trim() + " " +
                            et_pincode.getText().toString().trim());

                    bookTicket(et_name.getText().toString().trim(),
                            et_mobile.getText().toString().trim(),
                            et_pincode.getText().toString().trim(),
                            trainingID);

                    booking_dialog.dismiss();
                    hideKeyboard();
                } else {
                    showMessage("Please fill valid details.");
                }
            });
            et_pincode.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() != 0 && s.length() == 6) {
                        hideKeyboard();
                        String url = BuildConfig.PINCODEURL + et_pincode.getText().toString().trim();
                        LoggerUtil.logItem(url);
                        Call<ResponsePincodeDetail> getCity = apiServices.getStateCity(url);
                        getCity.enqueue(new Callback<ResponsePincodeDetail>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponsePincodeDetail> call, @NonNull Response<ResponsePincodeDetail> response) {
                                LoggerUtil.logItem(response.body());
                                hideLoading();
                                if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                                    city.setText(response.body().getCityName());
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<ResponsePincodeDetail> call, @NonNull Throwable t) {
                                hideLoading();
                            }
                        });
                    } else {
                        city.setText("");
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                }
            });
        } catch (Exception e) {
            hideLoading();
            e.printStackTrace();
        }
    }

    private void bookTicket(String et_name, String et_mobile, String et_pincode, String trainingID) {
        showLoading();
        RequestBookTrainingTicket requestBookTrainingTicket = new RequestBookTrainingTicket();
        requestBookTrainingTicket.setFkMemId(PreferencesManager.getInstance(context).getUSERID());
        requestBookTrainingTicket.setEventId(trainingID);
        requestBookTrainingTicket.setMobile(et_mobile);
        requestBookTrainingTicket.setName(et_name);
        requestBookTrainingTicket.setPinCode(et_pincode);
        LoggerUtil.logItem(requestBookTrainingTicket);
        Call<ResponseBookTrainingTicket> responseBookTrainingTicketCall = apiServices.responseBookTrainingTicketCall(requestBookTrainingTicket);
        responseBookTrainingTicketCall.enqueue(new Callback<ResponseBookTrainingTicket>() {
            @Override
            public void onResponse(Call<ResponseBookTrainingTicket> call, Response<ResponseBookTrainingTicket> response) {
                hideLoading();
                Log.e("==========", "bookTicket");
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        showAlert(response.body().getMessage(), R.color.green, R.drawable.ic_success);
                        int pos = 0;
                        for (int i = 0; i < detailsItems.size(); i++) {
                            if (detailsItems.get(i).getPkEventNameId().equalsIgnoreCase(trainingID)) {
                                pos = i;
                                break;
                            }
                        }
                        String msg = "Dear " + et_name + " Thanks for registration, your registration number: " + response.body().getBookingNo()
                                + " It gives us immense pleasure to invite you to business meet please join us on " + detailsItems.get(pos).getEventDate()
                                + " from " + detailsItems.get(pos).getFromtime()
                                + " to " + detailsItems.get(pos).getToTime() + " at " + detailsItems.get(pos).getLocation() +
                                " Download app : https://" + DYNAMIC_LINK + ".page.link/?invitedby=" + PreferencesManager.getInstance(context).getInviteCode();
                        sendSMS(msg, et_mobile);
                        getTrainingNames();
                    } else {
                        showAlert(response.body().getMessage(), R.color.red, R.drawable.error);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBookTrainingTicket> call, Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void setTransferTicket(String trainingamount, String trainingname, String trainingID, String totalTickets, String date) {
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            TrainingDialog(trainingamount, trainingname, trainingID, totalTickets, date);
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    private void TrainingDialog(String trainingamount, String trainingname, String trainingID, String totalTickets, String date) {
        try {
            hideKeyboard();
            transfer_dialog = new Dialog(context);
            transfer_dialog.setCanceledOnTouchOutside(true);
            transfer_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            Objects.requireNonNull(transfer_dialog.getWindow()).setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            transfer_dialog.setContentView(R.layout.transfer_dialog);
            transfer_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            transfer_dialog.show();

            Button btn_submit = transfer_dialog.findViewById(R.id.btn_submit);
            Button btn_cancel = transfer_dialog.findViewById(R.id.btn_cancel);
            EditText et_no_tickets = transfer_dialog.findViewById(R.id.et_no_tickets);
            EditText et_mobile = transfer_dialog.findViewById(R.id.et_mobile);
            TextView tv_transfer_to_name = transfer_dialog.findViewById(R.id.tv_transfer_to_name);
            TextView tv_msg = transfer_dialog.findViewById(R.id.tv_msg);
            tv_avail_ticket = transfer_dialog.findViewById(R.id.tv_avail_ticket);
            TextView tv_add_more = transfer_dialog.findViewById(R.id.tv_add_more);
            TextView event_name = transfer_dialog.findViewById(R.id.event_name);
            RecyclerView rv_items = transfer_dialog.findViewById(R.id.rv_items);

            tv_avail_ticket.setText("Available Tickets: " + totalTickets);
            totalTicketsAll = Integer.parseInt(totalTickets);

            rv_items.setLayoutManager(new LinearLayoutManager(context));

            event_name.setText((trainingname));
            btn_cancel.setOnClickListener(v -> transfer_dialog.dismiss());

            tv_add_more.setOnClickListener(v -> {

                if (tv_transfer_to_name.getText().toString().length() != 0 && !TextUtils.isEmpty(et_no_tickets.getText().toString())) {
//                    Todo
                    if (Integer.parseInt(et_no_tickets.getText().toString().trim()) <= totalTicketsAll) {
                        totalTicketsAll = totalTicketsAll - Integer.parseInt(et_no_tickets.getText().toString().trim());
                        tv_avail_ticket.setText("Available Tickets: " + totalTicketsAll + "");
                        TrainingItems items = new TrainingItems();
                        items.setAssignedTo(resultItem.getLoginId());
                        items.setDeviceId(resultItem.getDeviceId());
                        items.setFkMemId(resultItem.getFkMemId());
                        items.setName(resultItem.getName());
                        items.setMobile(et_mobile.getText().toString());
                        items.setAssignTicket(et_no_tickets.getText().toString().trim());
                        trainingItems.add(items);
                        AllotedTicketAdapter adapter = new AllotedTicketAdapter(context, trainingItems, this);
                        rv_items.setAdapter(adapter);
                        et_mobile.setText("");
                        et_no_tickets.setText("");
                        et_mobile.requestFocus();
                        hideKeyboard();
                    } else {
                        showMessage("Available Tickets :" + totalTicketsAll + "");
                    }
                } else {
                    showMessage("Enter valid details");
                }

            });

            et_mobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() != 0 && s.length() == 10) {
                        hideKeyboard();
                        if (!(et_mobile.getText().toString().trim().equalsIgnoreCase(PreferencesManager.getInstance(context).getMOBILE()))) {

                            String url = BuildConfig.BASE_URL_MLM + "GetDetailByMobile?Mobile="
                                    + et_mobile.getText().toString().trim() + "&FkMemId=" +
                                    PreferencesManager.getInstance(context).getUSERID();
                            LoggerUtil.logItem(url);
                            Call<ResponseName> getCity = apiServices.getNameFromMobile(url);
                            getCity.enqueue(new Callback<ResponseName>() {
                                @Override
                                public void onResponse(@NonNull Call<ResponseName> call, @NonNull Response<ResponseName> response) {
                                    LoggerUtil.logItem(response.body());
                                    hideLoading();
                                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                                        tv_msg.setVisibility(View.GONE);
                                        tv_transfer_to_name.setVisibility(View.VISIBLE);
                                        tv_transfer_to_name.setText(response.body().getResult().get(0).getName());
                                        resultItem = response.body().getResult().get(0);
                                    } else {
                                        tv_msg.setVisibility(View.VISIBLE);
                                        tv_msg.setText(response.body().getMessage());
                                        tv_transfer_to_name.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<ResponseName> call, @NonNull Throwable t) {
                                    hideLoading();
                                }
                            });
                        } else {
                            showMessage("You can not transfer tickets to yourself.");
                        }
                    } else {
                        tv_msg.setVisibility(View.GONE);
                        tv_msg.setText("");
                        tv_transfer_to_name.setVisibility(View.GONE);
                        tv_transfer_to_name.setText("");
                    }
                }
            });

            btn_submit.setOnClickListener(v -> {
                if (NetworkUtils.getConnectivityStatus(context) != 0)
                    alloteTickets(trainingID, trainingname, date, tv_transfer_to_name.getText().toString().trim());
            });

        } catch (Exception e) {
            hideLoading();
            e.printStackTrace();
        }
    }

    private void alloteTickets(String trainingId, String trainingName, String date, String tv_transfer_to_name) {
        RequestTrainingAllotment allotment = new RequestTrainingAllotment();
        allotment.setAssignedBy(PreferencesManager.getInstance(context).getLoginID());
        allotment.setTrainingId(trainingId);
        allotment.setNotificationMessage(
                "Dear " + tv_transfer_to_name
                        + " It gives us immense pleasure to invite you to " +
                        "business meet please join us by booking this traing " +
                        "and allot tickets to others inorder to make this event successful." +
                        " Download app : https://" +
                        DYNAMIC_LINK + ".page.link/?invitedby=" + PreferencesManager.getInstance(context).getInviteCode());
        allotment.setNotificationTitle(trainingName);
        allotment.setTrainingDate(date);
        allotment.setTrainingList(trainingItems);

        showLoading();

        LoggerUtil.logItem(allotment);

        Call<ResponseTicketAllotment> call = apiServices.alotTickets(allotment);
        call.enqueue(new Callback<ResponseTicketAllotment>() {
            @Override
            public void onResponse(Call<ResponseTicketAllotment> call, Response<ResponseTicketAllotment> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    transfer_dialog.dismiss();
                    detailsItems.clear();
                    getTrainingNames();
                } else {
                    showMessage(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseTicketAllotment> call, Throwable t) {
                hideLoading();

            }
        });
    }

    public static void updateTickets(String tickets) {
        totalTicketsAll = totalTicketsAll + Integer.parseInt(tickets);
        tv_avail_ticket.setText("Available Tickets:" + totalTicketsAll);
    }

    public void sendSMS(String msg, String mobile) {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        Log.e("==Registration====>> ", msg);
        String url = BuildConfig.SMS_URL + mobile + "&msg=" + msg.replace(" ", "%20");
        LoggerUtil.logItem(url);
        Call<String> call = apiServices.getOtp(url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                showAlert("Something went wrong, Try again.",
                        R.color.red, R.drawable.error);
            }
        });
    }

}
