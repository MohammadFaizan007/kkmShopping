package com.digitalcashbag.shopping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.BuildConfig;
import kkm.com.core.model.response.shopping.LstINRItem;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class ShoppingOtherOrderAdapter extends RecyclerView.Adapter<ShoppingOtherOrderAdapter.MyViewHolder> {
    private Context context;
    private List<LstINRItem> productList;

    public ShoppingOtherOrderAdapter(Context mContext, List<LstINRItem> productList) {
        this.context = mContext;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_other_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvProductName.setText(productList.get(position).getStoreName());
        if (productList.get(position).getStatus().equalsIgnoreCase("pending")) {
            holder.tvProductStatus.setText("Pending");
            holder.tvProductStatus.setTextColor(ContextCompat.getColor(context, R.color.yellow));
        } else if (productList.get(position).getStatus().equalsIgnoreCase("decline")) {
            holder.tvProductStatus.setText("Declined");
            holder.tvProductStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            holder.tvProductStatus.setText(productList.get(position).getStatus());
        }

        holder.tvOredrId.setText(String.format("%s %s", context.getResources().getString(R.string.order_id), productList.get(position).getTransactionId()));
        setImage(productList.get(position).getStoreName(), holder.imgProduct);
        holder.tvProductTotalPrice.setText(String.format("%s%s", context.getResources().getString(R.string.total), productList.get(position).getSaleAmount()));

        holder.tv_date.setText(productList.get(position).getTransactionDate());
    }

    private void setImage(String name, ImageView view) {
        String image_id = BuildConfig.BASE_URL_ICONS + "wallet.png";
        switch (name) {
            case "Amazon":
                image_id = BuildConfig.BASE_URL_ICONS + "amazon.png";
                break;
            case "Flipkart":
                image_id = BuildConfig.BASE_URL_ICONS + "flipkart.png";
                break;
            case "Zomato":
                image_id = BuildConfig.BASE_URL_ICONS + "zomato.png";
                break;
            case "Myntra":
                image_id = BuildConfig.BASE_URL_ICONS + "myntra.png";
                break;
            case "Netmeds":
                image_id = BuildConfig.BASE_URL_ICONS + "netmeds.png";
                break;
            case "Swiggy":
                image_id = BuildConfig.BASE_URL_ICONS + "swiggy.png";
                break;
            case "Dominos":
                image_id = BuildConfig.BASE_URL_ICONS + "dominos.png";
                break;
            case "Goibibo (Flight)":
                image_id = BuildConfig.BASE_URL_ICONS + "goibibo.png";
                break;
            case "ShopClues":
                image_id = BuildConfig.BASE_URL_ICONS + "shopclues.png";
                break;
            case "BookMyShow":
                image_id = BuildConfig.BASE_URL_ICONS + "bookmyshow.png";
                break;
            case "Oyo Rooms":
                image_id = BuildConfig.BASE_URL_ICONS + "oyo.png";
                break;
            case "Makemytrip":
                image_id = BuildConfig.BASE_URL_ICONS + "makemytrip.png";
                break;
            case "Zivame":
                image_id = BuildConfig.BASE_URL_ICONS + "zivame.png";
                break;
            case "PharmEasy":
                image_id = BuildConfig.BASE_URL_ICONS + "pharmaeasy.png";
                break;
            case "BigBasket":
                image_id = BuildConfig.BASE_URL_ICONS + "bigbaket.png";
                break;
            case "RailYatri":
                image_id = BuildConfig.BASE_URL_ICONS + "rail_yatri.png";
                break;
            case "Jabong":
                image_id = BuildConfig.BASE_URL_ICONS + "jabong.png";
                break;
            case "Firstcry":
                image_id = BuildConfig.BASE_URL_ICONS + "fc.png";
                break;
            case "Amazon Prime Video":
                image_id = BuildConfig.BASE_URL_ICONS + "amazon_prime_video.png";
                break;
            case "Lenskart":
                image_id = BuildConfig.BASE_URL_ICONS + "lenskart.png";
                break;
            case "Moglix":
                image_id = BuildConfig.BASE_URL_ICONS + "moglix.png";
                break;
            case "Bigrock":
                image_id = BuildConfig.BASE_URL_ICONS + "big_rock.png";
                break;
            case "Country Delight":
                image_id = BuildConfig.BASE_URL_ICONS + "delight.png";
                break;
            case "Insider-Bira 91 April Fools’ Fest":
                image_id = BuildConfig.BASE_URL_ICONS + "insider.png";
                break;

            case "Airtel":
                image_id = BuildConfig.BASE_URL_ICONS + "providers/airtel.png";
                break;
            case "Idea":
                image_id = BuildConfig.BASE_URL_ICONS + "providers/idea.png";
                break;
            case "BSNLTopUp":
                image_id = BuildConfig.BASE_URL_ICONS + "providers/bsnl.png";
                break;
            case "TATADocomoFlexi":
                image_id = BuildConfig.BASE_URL_ICONS + "providers/tatadocomo.png";
                break;
            case "Vodafone":
                image_id = BuildConfig.BASE_URL_ICONS + "providers/vodafone.png";
                break;
            case "TATADocomoSpecial":
                image_id = BuildConfig.BASE_URL_ICONS + "providers/tatadocomo.png";
                break;
            case "BSNLValiditySpecial":
                image_id = BuildConfig.BASE_URL_ICONS + "providers/bsnl.png";
                break;
            case "MTNLTopUP":
                image_id = BuildConfig.BASE_URL_ICONS + "providers/mts.png";
                break;
            case "MTNLValiditySpecial":
                image_id = BuildConfig.BASE_URL_ICONS + "providers/mts.png";
                break;
            case "RelianceDigitalTV":
                image_id = BuildConfig.BASE_URL_ICONS + "providers/reliancedigitaltv.png";
                break;
            case "SUNTV":
                image_id = BuildConfig.BASE_URL_ICONS + "providers/suntv.png";
                break;
            case "VideoconD2H":
                image_id = BuildConfig.BASE_URL_ICONS + "providers/videocondh.png";
                break;
            case "DISHTV":
                image_id = BuildConfig.BASE_URL_ICONS + "providers/dishtv.png";
                break;
            case "Jio":
                image_id = BuildConfig.BASE_URL_ICONS + "providers/jio.png";
                break;
        }
        Glide.with(context).load(image_id).apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).
                placeholder(R.drawable.bag_color).error(R.drawable.bag_color)).into(view);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_product)
        ImageView imgProduct;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_oredrId)
        TextView tvOredrId;
        @BindView(R.id.tv_product_status)
        TextView tvProductStatus;
        @BindView(R.id.tv_product_total_price)
        TextView tvProductTotalPrice;
        @BindView(R.id.tv_date)
        TextView tv_date;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}