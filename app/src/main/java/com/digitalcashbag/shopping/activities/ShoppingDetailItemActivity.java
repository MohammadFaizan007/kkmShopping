package com.digitalcashbag.shopping.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.text.HtmlCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.digitalcashbag.common_activities.FullScreenLogin;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.shopping.adapter.ImageListAdapter;
import com.digitalcashbag.shopping.fragment.SlideshowDialogFragment;
import com.google.gson.JsonObject;
import com.travijuu.numberpicker.library.NumberPicker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.request.RequestAddCart;
import kkm.com.core.model.response.DataItem;
import kkm.com.core.model.response.ProductImageItem;
import kkm.com.core.model.response.ProductSizeItem;
import kkm.com.core.model.response.Products;
import kkm.com.core.model.response.ResponseProductDetails;
import kkm.com.core.model.response.cart.ResponseCartList;
import kkm.com.core.retrofit.Dialog_dismiss;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;
import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class ShoppingDetailItemActivity extends BaseActivity implements Dialog_dismiss, MvpView {

    public static List<ProductImageItem> productImageItems;
    public List<ProductSizeItem> productSizeItems;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.badge)
    ImageView badge;
    @BindView(R.id.actionbar_notifcation_textview)
    TextView cartCounter;
    @BindView(R.id.qtyBox)
    NumberPicker qtyBox;
    @BindView(R.id.txtProductName)
    TextView txtProductName;
    @BindView(R.id.txtItemPrice)
    TextView txtItemPrice;
    @BindView(R.id.txtItemOrgPrice)
    TextView txtItemOrgPrice;
    @BindView(R.id.txtTotalRating)
    TextView txtTotalRating;
    @BindView(R.id.txtTotalReview)
    TextView txtTotalReview;
    @BindView(R.id.sizeRecyclerView)
    RecyclerView sizeRecyclerView;
    @BindView(R.id.txtProductDetails)
    TextView txtProductDetails;
    @BindView(R.id.btn_add_to_cart)
    Button btnAddToCart;
    @BindView(R.id.btn_buy_now)
    Button btnBuyNow;
    @BindView(R.id.image_recycler)
    RecyclerView imageRecycler;
    @BindView(R.id.productImage)
    ImageView productImage;
    private String productId = "", action_type;
    private boolean buy_now;
    private String sizeID = "0";
    private Products productsDetail;
    private ItemSizeAdapter sizeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail_lay_activity);
        ButterKnife.bind(this);
        title.setText(PreferencesManager.getInstance(context).getCategoryName());
        cartCounter.setText(String.valueOf(PreferencesManager.getInstance(context).getCartCount()));

        Bundle bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        sizeRecyclerView.setLayoutManager(manager);

        LinearLayoutManager imgManager = new LinearLayoutManager(context);
        imgManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imageRecycler.setLayoutManager(imgManager);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            productSizeItems = null;
            if (bundle != null) {
                productId = bundle.getString("productId");
            }
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

        productImage.setOnClickListener(v -> {
            try {
                Bundle bundle1 = new Bundle();
                bundle1.putInt("position", 0);
                bundle1.putSerializable("images", (Serializable) productImageItems);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle1);
                newFragment.show(ft, "slideshow");
            } catch (Exception | Error e) {
                e.printStackTrace();
            }

        });
    }

    public void updateCounter() {
        cartCounter.setText(String.valueOf(PreferencesManager.getInstance(context).getCartCount()));
    }

    @OnClick({R.id.side_menu, R.id.badge, R.id.btn_add_to_cart, R.id.btn_buy_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.badge:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                        action_type = "goto_cart";
                        FullScreenLogin dialog = new FullScreenLogin();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        dialog.show(ft, FullScreenLogin.TAG);

                    } else {
                        goToActivity(this, ViewCartActivity.class, null);
                    }
                } else {
                    showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                }

                break;
            case R.id.btn_add_to_cart:
                if (productSizeItems != null) {
                    if (Integer.parseInt(productSizeItems.get(sizeAdapter.position).getProductQty()) > 0) {
                        if (qtyBox.getValue() > 0) {
                            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                                if (PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                                    action_type = "addto_cart";
                                    buy_now = false;
                                    FullScreenLogin dialog = new FullScreenLogin();
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    dialog.show(ft, FullScreenLogin.TAG);

                                } else {
                                    addToCart(false);
                                }
                            } else {
                                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                            }
                        } else {
                            showAlert("Select quantity", R.color.red, R.drawable.error);
                        }
                    } else {
                        showAlert("Item Out of stock", R.color.red, R.drawable.error);
                    }
                } else {
                    showAlert("Item Out of stock", R.color.red, R.drawable.error);
                }
                break;
            case R.id.btn_buy_now:
                if (productSizeItems != null) {
                    if (Integer.parseInt(productSizeItems.get(sizeAdapter.position).getProductQty()) > 0) {
                        if (qtyBox.getValue() > 0) {
                            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                                if (PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                                    action_type = "buy_now";
                                    buy_now = true;
                                    FullScreenLogin dialog = new FullScreenLogin();
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    dialog.show(ft, FullScreenLogin.TAG);
                                } else {
                                    addToCart(true);
                                }
                            } else {
                                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                            }
                        } else {
                            showAlert("Select quantity", R.color.red, R.drawable.error);
                        }
                    } else {
                        showAlert("Item Out of stock", R.color.red, R.drawable.error);
                    }
                } else {
                    showAlert("Item Out of stock", R.color.red, R.drawable.error);
                }
                break;
        }
    }

    private void addToCart(boolean buy_now_bool) {
        showLoading();
        RequestAddCart cart = new RequestAddCart();
        cart.setAddedBy(PreferencesManager.getInstance(context).getUSERID());
        cart.setProductAmt("0");
        cart.setProductId(productsDetail.getProductId());
        cart.setProductQty(String.valueOf(qtyBox.getValue()));
        cart.setSizeID(productSizeItems.get(sizeAdapter.position).getSizeID());
        cart.setPkProductDetailId(productSizeItems.get(sizeAdapter.position).getPkProductDetailId());
        cart.setSrNo("0");
        LoggerUtil.logItem(cart);
        LoggerUtil.logItem(productSizeItems.get(sizeAdapter.position).getPkProductDetailId());
        Call<JsonObject> cartItemCall = apiServices_shoopping.addCartItem(cart);
        cartItemCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (buy_now_bool) {
                    goToActivity(context, ViewCartActivity.class, null);
                } else {
                    getCartItems();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideLoading();
            }

        });
    }

    public void getCartItems() {
        JsonObject object = new JsonObject();
        object.addProperty("UserId", PreferencesManager.getInstance(context).getUSERID());
        showLoading();
        Call<ResponseCartList> call = apiServices_shoopping.getCartItems(object);
        call.enqueue(new Callback<ResponseCartList>() {

            @Override
            public void onResponse(@NonNull Call<ResponseCartList> call, @NonNull Response<ResponseCartList> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                Cons.cartItemList = new ArrayList<>();
                try {
                    Cons.cartItemList = response.body().getCartItemList();
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        PreferencesManager.getInstance(context).setCartCount(response.body().getCartItemList().size());
                        updateCounter();
                    } else {
                        PreferencesManager.getInstance(context).setCartCount(0);
                        updateCounter();
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCartList> call, @NonNull Throwable t) {
                hideLoading();
            }

        });
    }

    private void getProductDetails(String productId) {
        try {
            showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("ProductId", productId);
            Call<ResponseProductDetails> productDetailsCall = apiServices_shoopping.getProductDetails(param);/*GetProducts*/
            LoggerUtil.logItem(param);
            productDetailsCall.enqueue(new Callback<ResponseProductDetails>() {
                @Override
                public void onResponse(@NonNull Call<ResponseProductDetails> call, @NonNull Response<ResponseProductDetails> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getSuccess().equalsIgnoreCase("Success")) {
                        setData(response.body().getData());
                    } else {
                        onBackPressed();
                        showAlert("Something went wrong!", R.color.red, R.drawable.error);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseProductDetails> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData(List<DataItem> dataItems) {
        for (int i = 0; i < dataItems.size(); i++) {
            switch (dataItems.get(i).getSectionTitle()) {
                case "Product Images":
                    productImageItems = dataItems.get(i).getProductImage();
                    ImageListAdapter imageListAdapter = new ImageListAdapter(context, productImageItems, ShoppingDetailItemActivity.this);
                    imageRecycler.setAdapter(imageListAdapter);

                    if (productImageItems.size() > 0) {
                        Glide.with(context).load(productImageItems.get(0).getImages())
                                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available)
                                        .error(R.drawable.image_not_available))
                                .into(productImage);
                    }

                    break;
                case "Product Size":
                    productSizeItems = dataItems.get(i).getProductSize();
                    txtItemPrice.setText(String.format("₹%s", productSizeItems.get(0).getProductPrice()));
                    txtItemOrgPrice.setText(String.format("₹%s", productSizeItems.get(0).getProductOldPrice()));
                    PreferencesManager.getInstance(context).setDeliveryCharge(productSizeItems.get(0).getCourierPrice());
                    txtItemOrgPrice.setPaintFlags(txtItemOrgPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    if (productSizeItems.size() > 0) {
                        sizeAdapter = new ItemSizeAdapter(context, productSizeItems, ShoppingDetailItemActivity.this);
                        sizeRecyclerView.setAdapter(sizeAdapter);
                    }
                    break;
                case "Products":
                    productsDetail = dataItems.get(i).getProducts();
                    txtProductName.setText(productsDetail.getProductName());
                    getClickPosition(0, "size");
                    txtProductDetails.setText(HtmlCompat.fromHtml(productsDetail.getProductDescription(), HtmlCompat.FROM_HTML_MODE_COMPACT));
                    break;
            }
        }

    }

    @Override
    public void getClickPosition(int position, String tag) {
        super.getClickPosition(position, tag);

        switch (tag) {
            case "size":
                try {
                    if (Integer.parseInt(productSizeItems.get(position).getProductQty()) <= 0) {
//                txtItemAvl.setText(getString(R.string.out_of_stock));
//                txtItemAvl.setTextColor(ContextCompat.getColor(context, R.color.red));
                        qtyBox.setVisibility(View.GONE);
                    } else {
                        qtyBox.setValue(1);
                        qtyBox.setVisibility(View.VISIBLE);
//                txtItemAvl.setText(getString(R.string.in_stock));
//                txtItemAvl.setTextColor(ContextCompat.getColor(context, R.color.green));
                        qtyBox.setMax(Integer.parseInt(productSizeItems.get(position).getProductQty()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "image":
                Glide.with(context).load(productImageItems.get(position).getImages())
                        .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available)
                                .error(R.drawable.image_not_available))
                        .into(productImage);
                break;

        }


    }

    @Override
    public void onDismiss() {
        if (action_type.equalsIgnoreCase("goto_cart")) {
            goToActivity(this, ViewCartActivity.class, null);
        } else {
            addToCart(buy_now);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCounter();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getProductDetails(productId);
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }


    public class ItemSizeAdapter extends RecyclerView.Adapter<ItemSizeAdapter.MyViewHolder> {

        public int position = 0;
        private Context context;
        private List<ProductSizeItem> productList;
        private MvpView mvpView;

        ItemSizeAdapter(Context mContext, List<ProductSizeItem> productList, MvpView mvp) {
            this.context = mContext;
            this.productList = productList;
            mvpView = mvp;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_size_adapter, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int pos) {
            holder.txtSize.setText(String.format("%s %s", productList.get(pos).getSizeName(), productList.get(pos).getUnitName()).trim());
            if (position == pos) {
                holder.txtSize.setTextColor(ContextCompat.getColor(context, R.color.white));
                holder.txtSize.setBackgroundResource(R.drawable.rect_btn_bg_darkgreen);
            } else {
                holder.txtSize.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                holder.txtSize.setBackgroundResource(R.drawable.size_border);
            }
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            @BindView(R.id.txtSize)
            TextView txtSize;

            MyViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                mvpView.getClickPosition(getAdapterPosition(), "size");
                position = getAdapterPosition();
                txtItemPrice.setText(String.format("₹%s", productSizeItems.get(position).getProductPrice()));
                txtItemOrgPrice.setText(String.format("₹%s", productSizeItems.get(position).getProductOldPrice()));
                txtItemOrgPrice.setPaintFlags(txtItemOrgPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                PreferencesManager.getInstance(context).setDeliveryCharge(productSizeItems.get(0).getCourierPrice());
                notifyDataSetChanged();
            }
        }
    }
}
