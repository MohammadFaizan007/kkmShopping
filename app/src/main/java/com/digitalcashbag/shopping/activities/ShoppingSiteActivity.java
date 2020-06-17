package com.digitalcashbag.shopping.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.shopping.adapter.ShoppingCategoryActivityAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.model.response.shopping.ShoppingOffersItem;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;
import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class ShoppingSiteActivity extends BaseActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerList)
    RecyclerView recyclerList;
    @BindView(R.id.img_shopping)
    ImageView imgShopping;

    private Bundle param;
    private ShoppingCategoryActivityAdapter adapter;
    private List<ShoppingOffersItem> shoppingoffersitem;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_site_layout);
        ButterKnife.bind(this);


        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        title.setText(param.getString("shopping"));

        sideMenu.setOnClickListener(v -> onBackPressed());


        GridLayoutManager manager = new GridLayoutManager(context, 2);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(manager);
        recyclerList.setItemAnimator(new DefaultItemAnimator());


        shoppingoffersitem = new ArrayList<>();
        for (int i = 0; i < Cons.shoppingoffersitem.size(); i++) {
            if (Cons.shoppingoffersitem.get(i).getOfferCategory().equalsIgnoreCase(param.getString("shopping"))
                    && Cons.shoppingoffersitem.get(i).getOfferOn().equalsIgnoreCase("iiashopping")) {

                Glide.with(context).load(Cons.shoppingoffersitem.get(i).getImageURL())
                        .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available).error(R.drawable.image_not_available)).into(imgShopping);

            } else if (Cons.shoppingoffersitem.get(i).getOfferCategory().equalsIgnoreCase(param.getString("shopping")) &&
                    !Cons.shoppingoffersitem.get(i).getOfferOn().equalsIgnoreCase("iiashopping")) {
                shoppingoffersitem.add(Cons.shoppingoffersitem.get(i));
            }
        }
        if (shoppingoffersitem.size() > 0) {
            adapter = new ShoppingCategoryActivityAdapter(context, shoppingoffersitem);
            recyclerList.setAdapter(adapter);
        }
    }

    @OnClick(R.id.img_shopping)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("shopping", "Shopping");
        ((ShoppingSiteActivity) context).goToActivity(context, ShoppingActivityMain.class, bundle);
    }

}
