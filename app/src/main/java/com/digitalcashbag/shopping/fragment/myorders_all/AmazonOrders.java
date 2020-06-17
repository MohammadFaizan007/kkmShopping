package com.digitalcashbag.shopping.fragment.myorders_all;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.adapter.ShoppingOtherOrderAdapter;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.request.RequestAmazonAdd;
import kkm.com.core.model.response.ResponseAmazonAdd;
import kkm.com.core.model.response.shopping.LstINRItem;
import kkm.com.core.model.response.shopping.ResponseINRDeailsOrders;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.FileUtils;
import kkm.com.core.utils.HidingScrollListener;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class AmazonOrders extends BaseFragment implements IPickCancel, IPickResult {

    public LinearLayoutManager layoutManager;
    Unbinder unbinder;
    @BindView(R.id.rv_orderlist)
    RecyclerView rvorderlist;
    @BindView(R.id.noRecord)
    TextView noRecord;
    int pageNo = 1;
    List<LstINRItem> items;
    ShoppingOtherOrderAdapter shoppingOtherOrderAdapter;
    @BindView(R.id.add_amazon)
    FloatingActionButton addAmazon;
    Dialog add_amazonOrder_dialog;
    PickImageDialog dialog;
    File documentFile;
    ImageView screen_preview;
    String amazonFile = "";

    EditText et_OrderAmount, etOrderDate, et_OrderNumber, et_OrderName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderlist, null);
        unbinder = ButterKnife.bind(this, view);
        items = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        rvorderlist.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        rvorderlist.setItemAnimator(new DefaultItemAnimator());
        addAmazon.setVisibility(View.VISIBLE);
        addAmazon.setOnClickListener(v -> addAmazonOrderDialog());

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getOtherOrders();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

        rvorderlist.addOnScrollListener(new HidingScrollListener(layoutManager) {
            @Override
            public void onHide() {
            }

            @Override
            public void onLoadMore(int i) {
                Log.e(">>>> ", "you have reached to the bottom = " + pageNo + "" + i);
                if (NetworkUtils.getConnectivityStatus(getActivity()) != 0) {
                    pageNo = i;
                    Log.e(">>>> ", "you have reached to the bottom = " + pageNo);
                    getOtherOrders();
                }
            }

            @Override
            public void onShow() {
            }
        });

        rvorderlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && addAmazon.isShown()) {
                    addAmazon.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    addAmazon.show();
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }


    private void getOtherOrders() {
        showLoading();
        String url = BuildConfig.BASE_URL_INR + "GetINRDealsTransaction?LoginId=" +
                PreferencesManager.getInstance(context).getUSERID() + "&Page=" + pageNo + "&StoreName=amaz";
        Log.e("URL======    ", url);

        Call<ResponseINRDeailsOrders> call = apiServices.getOtherOrders(url);
        call.enqueue(new Callback<ResponseINRDeailsOrders>() {
            @Override
            public void onResponse(@NonNull Call<ResponseINRDeailsOrders> call, @NonNull Response<ResponseINRDeailsOrders> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                            if (pageNo == 1) {
                                items = new ArrayList<>();
                                items.addAll(response.body().getLstINR());
                                shoppingOtherOrderAdapter = new ShoppingOtherOrderAdapter(context, items);
                                rvorderlist.setAdapter(shoppingOtherOrderAdapter);
                                Log.e("SIZEEEE AA- ", "" + items.size());
                            } else {
                                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                                    items.addAll(response.body().getLstINR());
                                    shoppingOtherOrderAdapter.notifyItemRangeChanged(0, shoppingOtherOrderAdapter.getItemCount());
                                    Log.e("SIZEEEE BB- ", "" + items.size());
                                }
                            }
                        } else {
                            if (pageNo == 1) {
                                noRecord.setVisibility(View.VISIBLE);
                                rvorderlist.setVisibility(View.GONE);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseINRDeailsOrders> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void addAmazonOrderDialog() {
        try {
            hideKeyboard();
            add_amazonOrder_dialog = new Dialog(context);
            add_amazonOrder_dialog.setCanceledOnTouchOutside(true);
            add_amazonOrder_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            add_amazonOrder_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            add_amazonOrder_dialog.setContentView(R.layout.add_amazon_order);
            add_amazonOrder_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            add_amazonOrder_dialog.show();

            Button btn_submit = add_amazonOrder_dialog.findViewById(R.id.btn_submit);
            Button selectDocument = add_amazonOrder_dialog.findViewById(R.id.selectDocument);
            Button btn_cancel = add_amazonOrder_dialog.findViewById(R.id.btn_cancel);
            et_OrderAmount = add_amazonOrder_dialog.findViewById(R.id.et_OrderAmount);
            etOrderDate = add_amazonOrder_dialog.findViewById(R.id.etOrderDate);
            et_OrderNumber = add_amazonOrder_dialog.findViewById(R.id.et_OrderNumber);
            et_OrderName = add_amazonOrder_dialog.findViewById(R.id.et_OrderName);
            screen_preview = add_amazonOrder_dialog.findViewById(R.id.screen_preview);

            btn_cancel.setOnClickListener(v -> add_amazonOrder_dialog.dismiss());


            etOrderDate.setOnClickListener(v -> datePicker(etOrderDate));


            btn_submit.setOnClickListener(v -> {
                if (et_OrderAmount.getText().toString().trim().length() != 0 &&
                        etOrderDate.getText().toString().trim().length() != 0 &&
                        et_OrderNumber.getText().toString().trim().length() != 0 &&
                        et_OrderName.getText().toString().trim().length() != 0 &&
                        amazonFile.length() != 0) {
                    Log.e("=======Submit -- ", et_OrderAmount.getText().toString().trim() + " " +
                            etOrderDate.getText().toString().trim() + " " +
                            et_OrderName.getText().toString().trim() + " " +
                            et_OrderNumber.getText().toString().trim());
                    addAmazonProduct();
//                    uploadBagRequestFile(documentFile, response.body().getpK_RequestId());
                } else {
                    showMessage("Please fill all the details.");
                }
            });
            selectDocument.setOnClickListener(v -> showDialog());

        } catch (Exception e) {
            hideLoading();
            e.printStackTrace();
        }
    }

    void showDialog() {
        PickSetup pickSetup = new PickSetup();
        pickSetup.setTitle("Choose Document");
        pickSetup.setGalleryIcon(com.vansuita.pickimage.R.mipmap.gallery_colored);
        pickSetup.setCameraIcon(com.vansuita.pickimage.R.mipmap.camera_colored);
        pickSetup.setCancelTextColor(R.color.colorAccent);
        dialog = PickImageDialog.build(pickSetup);
        dialog.setOnPickCancel(this);
        dialog.show(getFragmentManager());
    }

    private void datePicker(final EditText et) {
        Calendar cal = Calendar.getInstance();
        int mYear, mMonth, mDay;
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DatePickerDialogTheme, (view, year, monthOfYear, dayOfMonth) -> {
            et.setText(Utils.changeDateFormatSlash(dayOfMonth, monthOfYear, year));

        }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    private void uploadBagRequestFile(File file, String reqId) {

        showLoading();
        //creating request body for Profilefile
        RequestBody requestBody = null;
        if (file != null)
            requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody Fk_MemIdBody = RequestBody.create(MediaType.parse("text/plain"), "ClientsDocument");
        // TODO CHANGE ACTION AS PER REQUIREMENT
        RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "Amazon");
        RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody uniquenoBody = RequestBody.create(MediaType.parse("text/plain"), reqId);
        MultipartBody.Part body = MultipartBody.Part.createFormData("", file.getName(), requestBody);
//        LoggerUtil.logItem(reqId);

        //creating our api
        ApiServices apiServices = ServiceGenerator.createServiceFile(ApiServices.class);
        //creating a call and calling the upload image method
        Call<JsonObject> call = apiServices.uploadImage(String.valueOf(PreferencesManager.getInstance(context).getUSERID()), Fk_MemIdBody, action, typeBody, uniquenoBody, body);
        //finally performing the call
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                LoggerUtil.logItem(response.body());
                hideLoading();
                add_amazonOrder_dialog.dismiss();
                getOtherOrders();
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("***********", call.request().url().toString());
                Log.e("***********", "= " + t.getLocalizedMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pageNo = 1;
        unbinder.unbind();
    }

    @Override
    public void onCancelClick() {

    }

    @Override
    public void onPickResult(PickResult pickResult) {
        Log.e("RESULT", " = " + pickResult.getPath());
        if (pickResult.getError() == null) {
            CropImage.activity(pickResult.getUri())
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setFixAspectRatio(false)
                    .start(context);
        } else {
            Log.e("RESULT", "ERROR = " + pickResult.getError());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                documentFile = FileUtils.getFile(context, result.getUri());
                Bitmap bitmap = Utils.getCompressedBitmap(documentFile.getAbsolutePath());
                screen_preview.setImageBitmap(bitmap);
                amazonFile = "File";
                Log.e("Document File ", documentFile.toString());
            }
        }
    }

    private void addAmazonProduct() {
        RequestAmazonAdd add = new RequestAmazonAdd();
        add.setAmount(et_OrderAmount.getText().toString().trim());
        add.setInvoiceNo(et_OrderNumber.getText().toString().trim());
        add.setShoppingDate(etOrderDate.getText().toString());
        add.setLoginId(PreferencesManager.getInstance(context).getLoginID());
        add.setProductName(et_OrderName.getText().toString());
        add.setStoreName("Amazon");

        showLoading();
        LoggerUtil.logItem("AmazonAddData " + add.toString());

        Call<ResponseAmazonAdd> call = apiServices.addAmazonProduct(add);
        call.enqueue(new Callback<ResponseAmazonAdd>() {
            @Override
            public void onResponse(@NotNull Call<ResponseAmazonAdd> call, @NotNull Response<ResponseAmazonAdd> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {
                    if (response.body() != null && response.body().getResponse().equalsIgnoreCase("success")) {
                        uploadBagRequestFile(documentFile, response.body().getAmazonId());
                        showMessage(response.body().getResponse());
                    } else {
                        showMessage(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseAmazonAdd> call, @NotNull Throwable t) {
                hideLoading();
            }
        });
    }
}
