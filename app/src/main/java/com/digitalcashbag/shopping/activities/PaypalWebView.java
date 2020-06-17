package com.digitalcashbag.shopping.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.digitalcashbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.utils.LoggerUtil;

import static kkm.com.core.BuildConfig.FAIL_URL;
import static kkm.com.core.BuildConfig.SUCCESS_URL;
import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class PaypalWebView extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.shopping_progress)
    ProgressBar progressBar;
    String from;
    private Bundle param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_lay);
        ButterKnife.bind(this);

        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        LoggerUtil.logItem(param.getString("link"));
        from = param.getString("from");
        try {
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
            } else {
                CookieManager.getInstance().setAcceptCookie(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadLink(param.getString("link"));

        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void loadLink(String link) {
        WebSettings webSetting = webView.getSettings();
//        webSetting.setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webView.loadUrl(link);
    }


    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            if (url.contains(SUCCESS_URL) || url.contains(FAIL_URL)) {

//                http://cashbag.co.in/Home/SuccessView?InvoiceNo=473822
                LoggerUtil.logItem(url);
                String[] invoice = url.split("=");
                String invoiceNo = "";
                if (invoice.length > 1) {
                    invoiceNo = url.split("=")[1];
                } else {
                    invoiceNo = "";
                }
                if (from.equalsIgnoreCase("shopping")) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("invoice_no", invoiceNo);
                    if (url.contains(SUCCESS_URL)) {
                        setResult(Activity.RESULT_OK, resultIntent);
                    } else {
                        setResult(Activity.RESULT_CANCELED, resultIntent);
                    }
                    finish();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("invoice_no", invoiceNo);
                    bundle.putString("from", from);
                    goToActivityWithFinish(context, TransactionStatusPaypal.class, bundle);
                }
            }
        }

    }
}
