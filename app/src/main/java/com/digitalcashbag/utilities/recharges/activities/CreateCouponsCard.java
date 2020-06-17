package com.digitalcashbag.utilities.recharges.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.CheckErrorCode;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
import kkm.com.core.model.response.giftCardResponse.ResponseCouponsDetails;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;
import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class CreateCouponsCard extends BaseActivity {
    @BindView(R.id.img_gift)
    ImageView imgGift;
    @BindView(R.id.tv_card_name)
    TextView tvCardName;
    @BindView(R.id.tv_amount_dd)
    TextView tvAmountDropDown;
    @BindView(R.id.et_amount)
    EditText etAmount;
    @BindView(R.id.tv_handling_charges)
    TextView tvHandlingCharges;
    @BindView(R.id.tv_about_product)
    TextView tvAboutProduct;
    @BindView(R.id.tv_tnc)
    TextView tvTnc;
    @BindView(R.id.title)
    TextView title;
    Bundle param;
    List<String> slabAmounts = new ArrayList<>();
    List<String> themes = new ArrayList<>();
    List<String> themesNames = new ArrayList<>();
    @BindView(R.id.tv_min_amt)
    TextView tvMinAmt;
    @BindView(R.id.tv_max_amt)
    TextView tvMaxAmt;
    PopupMenu popupSlabs, popupThemes;
    @BindView(R.id.textView49)
    TextView textView49;
    int minAmount = 0, maxAmount = 0;
    @BindView(R.id.tv_themes)
    TextView tv_themes;
    boolean isValidAmount = false;
    @BindView(R.id.first_name_et)
    TextInputEditText firstNameEt;
    @BindView(R.id.last_name_et)
    TextInputEditText lastNameEt;
    @BindView(R.id.mobile_edt_no)
    TextInputEditText mobileEdtNo;
    @BindView(R.id.email_et)
    TextInputEditText emailEt;
    @BindView(R.id.btn_create_coupon)
    Button btnCreateCoupon;
    String selectedTheme = "", productAmount = "";
    @BindView(R.id.et_gift_msg)
    EditText etGiftMsg;

    ResponseCouponsDetails giftsCards = new ResponseCouponsDetails();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupons_details);
        ButterKnife.bind(this);

        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        title.setText(param.getString("productName"));

        popupSlabs = new PopupMenu(context, tvAmountDropDown);
        popupThemes = new PopupMenu(context, tv_themes);

        themes.add("woohoo Anniversary Design{Anniversary}");
        themes.add("woohoo Best Wishes Design{Best Wishes}");
        themes.add("woohoo Birthday Design{Happy Birthday}");
        themes.add("woohoo christmas Design{Merry Christmas}");
        themes.add("woohoo Congratulations Design{Congratulations}");
        themes.add("woohoo Diwali Design{Diwali}");
        themes.add("woohoo Friendship Day Design{Friendship Day}");
        themes.add("woohoo Holi Design{Holi}");
        themes.add("woohoo I Love You Design{I Love You}");
        themes.add("woohoo New Year Design{Happy New Year}");
        themes.add("woohoo Rakshabandhan Design{Rakshabandhan}");
        themes.add("woohoo Teachers Day Design{Teacher's Day}");
        themes.add("woohoo Thank You Design{Thank You}");
        themes.add("woohoo Valentines Day Design{Valentine's Day}");
        themes.add("woohoo Wedding Design{Wedding}");

        themesNames.add("Anniversary Design");
        themesNames.add("Best Wishes Design");
        themesNames.add("Birthday Design");
        themesNames.add("christmas Design");
        themesNames.add("Congratulations Design");
        themesNames.add("Diwali Design");
        themesNames.add("Friendship Day Design");
        themesNames.add("Holi Design");
        themesNames.add("I Love You Design");
        themesNames.add("New Year Design");
        themesNames.add("Rakshabandhan Design");
        themesNames.add("Teachers Day Design");
        themesNames.add("Thank You Design");
        themesNames.add("Valentines Day Design");
        themesNames.add("Wedding Design");

        popupThemes.getMenu().clear();
        LoggerUtil.logItem(themesNames.size());
        for (int i = 0; i < themesNames.size(); i++) {
            popupThemes.getMenu().add(themesNames.get(i));
        }

        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getCouponsDetails();
        else
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);

        tvAmountDropDown.setOnClickListener(v -> {
            popupSlabs.setOnMenuItemClickListener(item -> {
                tvAmountDropDown.setText(item.getTitle());
                productAmount = item.getTitle().toString();
                popupSlabs.dismiss();
                return true;
            });
            popupSlabs.show();
        });

        tv_themes.setOnClickListener(v -> {
            popupThemes.setOnMenuItemClickListener(item -> {
                tv_themes.setText(item.getTitle());
                for (int i = 0; i < themes.size(); i++) {
                    if (themes.get(i).contains(item.getTitle())) {
                        selectedTheme = themes.get(i);
                        LoggerUtil.logItem(selectedTheme);
                        break;
                    }
                }
                popupThemes.dismiss();
                return true;
            });
            popupThemes.show();
        });

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    if (Float.parseFloat(etAmount.getText().toString()) < minAmount ||
                            Float.parseFloat(etAmount.getText().toString()) > maxAmount) {
                        isValidAmount = false;
                        etAmount.setError("invalid amount");
                    } else {
                        etAmount.clearFocus();
                        isValidAmount = true;
                    }
                }
            }
        });
    }

    private void getCouponsDetails() {
        showLoading();
//        RequestCouponsDetails categories = new RequestCouponsDetails();
//        categories.setAmount("10.00");
//        categories.setAmountAll("10.00");
//        categories.setType("3");
//        categories.setProductId(param.getString("productId"));
        try {
            JsonObject object = new JsonObject();
            object.addProperty("Amount", "10.00");
            object.addProperty("Amount_All", "10.00");
            object.addProperty("Type", "3");
            object.addProperty("Product_id", param.getString("productId"));

            LoggerUtil.logItem(object);

            JsonObject body = new JsonObject();

            body.addProperty("body", Cons.encryptMsg(object.toString(), easypay_key));
            LoggerUtil.logItem(body);


            Call<JsonObject> call = apiServices_utilityV2.getCouponsDetails(body);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());

                    try {

                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                        JsonArray convertedObject = new Gson().fromJson(paramResponse, JsonArray.class);
                        LoggerUtil.logItem(convertedObject);

                        JsonObject object = convertedObject.get(0).getAsJsonObject();
                        if (object.get("ERROR").getAsString().equalsIgnoreCase("0") &&
                                object.get("RESULT").getAsString().equalsIgnoreCase("0")) {
                            JSONObject addinfo = new JSONObject((Utils.replaceBackSlash(object.get("ADDINFO").getAsString())));
                            giftsCards.setId(addinfo.getString("id"));
                            giftsCards.setCustomDenominations(addinfo.getString("custom_denominations"));
                            giftsCards.setDescription(addinfo.getString("description"));
                            giftsCards.setImages(addinfo.getString("images"));
                            giftsCards.setMaxCustomPrice(addinfo.getString("max_custom_price"));
                            giftsCards.setMinCustomPrice(addinfo.getString("min_custom_price"));
                            giftsCards.setName(addinfo.getString("name"));
                            giftsCards.setOrderHandlingCharge(addinfo.getInt("order_handling_charge"));
                            giftsCards.setPaywithamazonDisable(addinfo.getBoolean("paywithamazon_disable"));
                            giftsCards.setPriceType(addinfo.getString("price_type"));
                            giftsCards.setProductType(addinfo.getString("product_type"));
                            giftsCards.setTncMobile(addinfo.getString("tnc_mobile"));
                            setData();


                        } else {
                            CheckErrorCode checkErrorCode = new CheckErrorCode();
                            checkErrorCode.isValidTransaction(context, object.get("error").getAsString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    void setData() {
        Glide.with(context).load(giftsCards.getImages()).apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.pending).error(R.drawable.pending)).into(imgGift);

        tvCardName.setText(giftsCards.getName());
        tvAboutProduct.setText(giftsCards.getDescription());

        tvMinAmt.setText(String.format("Min\n₹%s", giftsCards.getMinCustomPrice()));
        tvMaxAmt.setText(String.format("Max\n₹%s", giftsCards.getMaxCustomPrice()));

        minAmount = Integer.parseInt(giftsCards.getMinCustomPrice());
        maxAmount = Integer.parseInt(giftsCards.getMaxCustomPrice());

        if (giftsCards.getPriceType().equalsIgnoreCase("Slab")) {
            tvAmountDropDown.setVisibility(View.VISIBLE);
            textView49.setVisibility(View.VISIBLE);
            etAmount.setVisibility(View.GONE);
            tvMinAmt.setVisibility(View.GONE);
            tvMaxAmt.setVisibility(View.GONE);
            String[] str = giftsCards.getCustomDenominations().split(",");
            slabAmounts = Arrays.asList(str);
            popupSlabs.getMenu().clear();
            LoggerUtil.logItem(slabAmounts.size());
            for (int i = 0; i < slabAmounts.size(); i++) {
                popupSlabs.getMenu().add(slabAmounts.get(i));
            }
        } else {
            tvMinAmt.setVisibility(View.VISIBLE);
            tvMaxAmt.setVisibility(View.VISIBLE);
            etAmount.setVisibility(View.VISIBLE);
            tvAmountDropDown.setVisibility(View.GONE);
            textView49.setVisibility(View.GONE);
        }
        tvHandlingCharges.setText(String.format("Handling Charges : ₹%d", giftsCards.getOrderHandlingCharge()));
        tvTnc.setText(Html.fromHtml(giftsCards.getTncMobile()));
    }

    @OnClick({R.id.side_menu, R.id.btn_create_coupon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                onBackPressed();
                break;
            case R.id.btn_create_coupon:
                if (validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0)
                        getWalletBalance();
                    else
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                }
                break;
        }
    }

    private boolean validation() {
        if (giftsCards.getPriceType().equalsIgnoreCase("Slab"))
            productAmount = tvAmountDropDown.getText().toString();
        else
            productAmount = etAmount.getText().toString().trim();
        if (productAmount.length() == 0) {
            showAlert("Amount can't be blank", R.color.red, R.drawable.error);
            return false;
        } else if (selectedTheme.length() == 0) {
            showAlert("Please select any theme", R.color.red, R.drawable.error);
            return false;
        } else if (etGiftMsg.getText().toString().trim().length() == 0) {
            etGiftMsg.setError("Enter gift message");
            return false;
        } else if (Objects.requireNonNull(firstNameEt.getText()).toString().trim().length() == 0) {
            firstNameEt.setError("Enter beneficiary first name");
            return false;
        } else if (Objects.requireNonNull(lastNameEt.getText()).toString().trim().length() == 0) {
            lastNameEt.setError("Enter beneficiary last name");
            return false;
        } else if (Objects.requireNonNull(mobileEdtNo.getText()).toString().trim().length() != 10) {
            mobileEdtNo.setError("Enter beneficiary mobile number");
            return false;
        } else if (!(Utils.isEmailAddress(emailEt.getText().toString().trim()))) {
            emailEt.setError("Enter beneficiary email id");
            return false;
        }
        return true;
    }

    void createGiftCard() {
        showLoading();
//        RequestCreateGiftCard card = new RequestCreateGiftCard();
//        card.setAmount(productAmount);
//        card.setAmountAll(productAmount);
//        card.setBenfName(firstNameEt.getText().toString().trim());
//        card.setBenlName(lastNameEt.getText().toString().trim());
//        card.setBenMobile(mobileEdtNo.getText().toString().trim());
//        card.setEmail(PreferencesManager.getInstance(context).getEMAIL());
//        card.setEmail("azeem@quaeretech.com");
//        card.setBillingEmail(emailEt.getText().toString().trim());
//        card.setFK_MemID(PreferencesManager.getInstance(context).getUSERID());
//        card.setFName(PreferencesManager.getInstance(context).getNAME());
//        card.setGiftmessage(etGiftMsg.getText().toString());
//        card.setLName(PreferencesManager.getInstance(context).getLNAME());
//        card.setNUMBER(PreferencesManager.getInstance(context).getMOBILE());
//        card.setPrice(productAmount);
//        card.setProductId(giftsCards.getId());
//        card.setProducttype(giftsCards.getProductType());
//        card.setQty("1");
//        card.setTheme(selectedTheme);
//        card.setType("5");
//        LoggerUtil.logItem(card);

        JsonObject mainjson = new JsonObject();
        mainjson.addProperty("Amount", productAmount);
        mainjson.addProperty("Amount_All", productAmount);
        mainjson.addProperty("benfName", firstNameEt.getText().toString().trim());
        mainjson.addProperty("benlName", lastNameEt.getText().toString().trim());
        mainjson.addProperty("benMobile", mobileEdtNo.getText().toString().trim());
        mainjson.addProperty("Email", PreferencesManager.getInstance(context).getEMAIL());
        mainjson.addProperty("billing_email", emailEt.getText().toString().trim());
        mainjson.addProperty("FK_MemID", PreferencesManager.getInstance(context).getUSERID());
        mainjson.addProperty("fName", PreferencesManager.getInstance(context).getNAME());
        mainjson.addProperty("giftmessage", etGiftMsg.getText().toString());
        mainjson.addProperty("lName", PreferencesManager.getInstance(context).getLNAME());
        mainjson.addProperty("NUMBER", PreferencesManager.getInstance(context).getMOBILE());
        mainjson.addProperty("price", productAmount);
        mainjson.addProperty("Product_id", giftsCards.getId());
        mainjson.addProperty("Producttype", giftsCards.getProductType());
        mainjson.addProperty("qty", "1");
        mainjson.addProperty("theme", selectedTheme);
        mainjson.addProperty("Type", "5");


        LoggerUtil.logItem(mainjson);

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(mainjson.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<JsonObject> call = apiServices_utilityV2.createGiftCard(body);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                try {

                    String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                    JsonArray convertedObject = new Gson().fromJson(paramResponse, JsonArray.class);
                    LoggerUtil.logItem(convertedObject);
                    JsonObject object = convertedObject.get(0).getAsJsonObject();
                    if (object.get("ERROR").getAsString().equalsIgnoreCase("0") &&
                            object.get("RESULT").getAsString().equalsIgnoreCase("0")) {

                        JSONObject addinfo = new JSONObject((Utils.replaceBackSlash(object.get("ADDINFO").getAsString())));
                        showMessage(addinfo.getString("message"));
                    } else {
                        CheckErrorCode checkErrorCode = new CheckErrorCode();
                        checkErrorCode.isValidTransaction(context, object.get("error").getAsString());
                    }
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

    private void getWalletBalance() {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("MemberId", PreferencesManager.getInstance(context).getUSERID());
            LoggerUtil.logItem(jsonObject);

            JsonObject body = new JsonObject();
            try {
                body.addProperty("body", Cons.encryptMsg(jsonObject.toString(), easypay_key));
                LoggerUtil.logItem(body);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Call<JsonObject> walletBalanceCall = apiServices_utilityV2.getbalanceAmount(body);
            walletBalanceCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {

                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                        ResponseBalanceAmount convertedObject = new Gson().fromJson(paramResponse, ResponseBalanceAmount.class);
                        if (convertedObject.getStatus().equalsIgnoreCase("Success")) {
                            if (Float.parseFloat(String.valueOf(convertedObject.getBalanceAmount())) >= Float.parseFloat(productAmount)) {
                                createGiftCard();
                            } else
                                createAddBalanceDialog(context, "Insufficient bag balance", "You have insufficient balance in your bag, add money before making transactions.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createAddBalanceDialog(Context context, String title, String msg) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setNegativeButton("Add Balance", (dialog, id) -> {
            dialog.dismiss();
            Bundle b = new Bundle();
            b.putString("total", productAmount);
            b.putString("from", "coupon");
            goToActivity(CreateCouponsCard.this, AddMoney.class, b);
        });

        android.support.v7.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }


}