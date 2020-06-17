package com.digitalcashbag.shopping.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.digitalcashbag.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import kkm.com.core.model.response.ProductImageItem;
import uk.co.senab.photoview.PhotoViewAttacher;


public class SlideshowDialogFragment extends DialogFragment {

    ImageView imageViewPreview;
    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private ViewPager viewPager;
    private int selectedPosition = 0;
    private List<ProductImageItem> productImageItems;
    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            //displayMetaInfo(position);
            selectedPosition = position;
            Log.e("POS==B ", position + " =  " + selectedPosition);
            try {
                new GetImageFromUrl(productImageItems.get(position).getImages(), position).execute();
            } catch (Error | Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private Bitmap bmp;
    private PhotoViewAttacher mAttacher;

    public static SlideshowDialogFragment newInstance() {
        return new SlideshowDialogFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = v.findViewById(R.id.viewpager);

        if (getArguments() != null) {
            selectedPosition = getArguments().getInt("position");
            productImageItems = (List<ProductImageItem>) getArguments().getSerializable("images");
        }

        Log.e(TAG, "position: " + selectedPosition);
        Log.e(TAG, "images size: " + productImageItems.size());

        try {
            new GetImageFromUrlFirst(productImageItems.get(selectedPosition).getImages()).execute();
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        MyViewPagerAdapter() {
            layoutInflater = LayoutInflater.from(getActivity());
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);
            imageViewPreview = view.findViewById(R.id.image_preview);

            Log.e("POS==A ", position + " =  " + selectedPosition);
            new GetImageFromUrl(productImageItems.get(position).getImages(), position).execute();

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return productImageItems.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == (obj);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    //     ZOOMING start
    @SuppressLint("StaticFieldLeak")
    private class GetImageFromUrl extends AsyncTask<String, Void, String> {

        private String imgeUrl;
        private int pos;

        private GetImageFromUrl(String imgeUrl, int pos) {
            this.imgeUrl = imgeUrl;
            this.pos = pos;
        }

        @Override
        protected void onPreExecute() {
//            pd.show();
            super.onPreExecute();
//            selectImage_dialog = new Dialog(getContext(), android.R.style.Theme_Light);
        }


        @Override
        protected String doInBackground(String... params) {
            try {
                Log.e(">>>>>>>>>> ", ">>>>>>>>>> " + imgeUrl);
                InputStream in = new URL(imgeUrl).openStream();
                bmp = BitmapFactory.decodeStream(in);
                Log.e(">>>>>>>>>> ", "BITTTMMMAAPPPSHOOOWWW::: " + bmp);
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(">>>>>>>>>> ", ">>>>>>>>>> " + bmp);
            if (bmp != null) {
                View view = viewPager.getChildAt(pos);
                ImageView imageViewPreview = view.findViewById(R.id.image_preview);

                imageViewPreview.setImageBitmap(bmp);
                //Zooming Image
                mAttacher = new PhotoViewAttacher(imageViewPreview);
                mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
                mAttacher.setOnPhotoTapListener(new PhotoTapListener());
                mAttacher.setOnSingleFlingListener(new SingleFlingListener());
                System.out.println("ATTCCCHHERRR:::: " + mAttacher);
            } else {
                Toast.makeText(getContext(), "bitmap is null ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetImageFromUrlFirst extends AsyncTask<String, Void, String> {

        private String imgeUrl;
//        private int pos;

        private GetImageFromUrlFirst(String imgeUrl) {
            this.imgeUrl = imgeUrl;
//            this.pos = pos;
        }

        @Override
        protected void onPreExecute() {
//            pd.show();
            super.onPreExecute();
//            selectImage_dialog = new Dialog(getContext(), android.R.style.Theme_Light);
        }


        @Override
        protected String doInBackground(String... params) {
            try {
                Log.e(">>>>>>>>>> ", ">>>>>>>>>> " + imgeUrl);
                InputStream in = new URL(imgeUrl).openStream();
                bmp = BitmapFactory.decodeStream(in);
                Log.e(">>>>>>>>>> ", "BITTTMMMAAPPPSHOOOWWW::: " + bmp);
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(">>>>>>>>>> ", ">>>>>>>>>> " + bmp);
            if (bmp != null) {

                MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
                viewPager.setAdapter(myViewPagerAdapter);
                viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
                viewPager.setOffscreenPageLimit(productImageItems.size());
                viewPager.setCurrentItem(selectedPosition, true);

                imageViewPreview.setImageBitmap(bmp);
                //Zooming Image
                mAttacher = new PhotoViewAttacher(imageViewPreview);
                mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
                mAttacher.setOnPhotoTapListener(new PhotoTapListener());
                mAttacher.setOnSingleFlingListener(new SingleFlingListener());
                System.out.println("ATTCCCHHERRR:::: " + mAttacher);
            } else {
                Toast.makeText(getContext(), "bitmap is null ", Toast.LENGTH_SHORT).show();
            }
        }
    }


    // Photo zooming attacher library
    private class MatrixChangeListener implements PhotoViewAttacher.OnMatrixChangedListener {
        @Override
        public void onMatrixChanged(RectF rect) {
        }
    }

    private class PhotoTapListener implements PhotoViewAttacher.OnPhotoTapListener {
        @Override
        public void onPhotoTap(View view, float x, float y) {
//            float xPercentage = x * 100f;
//            float yPercentage = y * 100f;
        }

        @Override
        public void onOutsidePhotoTap() {
        }
    }

    private class SingleFlingListener implements PhotoViewAttacher.OnSingleFlingListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return true;
        }
    }

    //     Zoomongs Ends
}
