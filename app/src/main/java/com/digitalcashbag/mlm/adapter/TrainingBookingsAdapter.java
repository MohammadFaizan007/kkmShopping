package com.digitalcashbag.mlm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.digitalcashbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.model.response.trainingBookings.EventDetailListItem;

public class TrainingBookingsAdapter extends RecyclerView.Adapter<TrainingBookingsAdapter.ViewHolder> {

    private Context mContext;
    private List<EventDetailListItem> list;
    private Bitmap bitmap;
    private final static int QRcodeWidth = 500;

    public TrainingBookingsAdapter(Context context, List<EventDetailListItem> list) {
        mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booked_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        holder.memberName.setText(list.get(listPosition).getName());
        holder.phonenumber.setText(list.get(listPosition).getMobile());
        new GetQRCode(holder.qrCode, holder.progbar).execute(list.get(listPosition).getBookingNo());
        if (list.get(listPosition).getBookingStatus().equalsIgnoreCase("Booked")) {
            holder.scan.setVisibility(View.VISIBLE);
        }else {
            holder.scan.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.qr_code)
        ImageView qrCode;
        @BindView(R.id.progbar)
        ProgressBar progbar;
        @BindView(R.id.member_name)
        TextView memberName;
        @BindView(R.id.phonenumber)
        TextView phonenumber;
        @BindView(R.id.scan)
        ImageView scan;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetQRCode extends AsyncTask<String, Void, String> {

        ImageView imageView;
        ProgressBar bar;

        public GetQRCode(ImageView imageView, ProgressBar bar) {
            this.imageView = imageView;
            this.bar = bar;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                bitmap = TextToImageEncode(params[0]);
            } catch (Error e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                bar.setVisibility(View.GONE);
            } else {
//                openchoice(getActivity());
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        mContext.getResources().getColor(R.color.text_color) : mContext.getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

}
