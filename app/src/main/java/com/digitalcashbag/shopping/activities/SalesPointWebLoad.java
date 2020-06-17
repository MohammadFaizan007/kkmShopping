package com.digitalcashbag.shopping.activities;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.digitalcashbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.BuildConfig;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.Utils;

public class SalesPointWebLoad extends BaseActivity {
    String tempUrl = "", payUrl = "";
    String ua = "Mozilla/5.0 " +
            "(Linux; Android 4.4.4; One Build/KTU84L.H4)" +
            " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.135 Mobile Safari/537.36";
    boolean loadScript = true;
    //    String spo_username, spo_password;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.shopping_progress)
    ProgressBar shoppingProgress;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_lay);
        ButterKnife.bind(this);

//        spo_username = PreferencesManager.getInstance(context).getSPO_USERNAME();
//        spo_password = PreferencesManager.getInstance(context).getSPO_PASSWORD();
//        Log.e("==========", spo_username + " / " + spo_password);

        payUrl = "https://qa.salespointonline.com/btob-products?term=" + Utils.convertInto64(BuildConfig.SPO_ID);
        revertCall();
    }

    @SuppressLint("JavascriptInterface")
    private void revertCall() {

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setClickable(true);
        webView.setWebChromeClient(new WebChromeClient());


        webView.requestFocus(View.FOCUS_DOWN);
        webView.setWebViewClient(new PQClient());
        // webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setUserAgentString(ua);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setBuiltInZoomControls(true);
        // webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(true);
        // webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
//         webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setEnableSmoothTransition(true);
        MyJavaScriptInterface jsInterface = new MyJavaScriptInterface(this);
        webView.addJavascriptInterface(jsInterface, "HtmlViewer");

        webView.getSettings().setAppCacheEnabled(false);
        webView.clearCache(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

//        webView.loadUrl("https://www.salespointonline.com/");
        // webView.reload();
        webView.loadUrl(payUrl);
    }

    class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        public void showHTML(String html) {
            new AlertDialog.Builder(ctx).setTitle("HTML").setMessage(html)
                    .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
        }

    }

    @Override
    protected void onDestroy() {
        webView.clearCache(true);
        super.onDestroy();
    }


    public class PQClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            shoppingProgress.setVisibility(View.VISIBLE);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // If url contains mailto link then open Mail Intent
            tempUrl = url;
            view.loadUrl(url);
            return true;
        }

        // Called when all page resources loaded
        public void onPageFinished(WebView view, String url) {
            try {

                LoggerUtil.logItem(url);
//                if (url.contains("login")) {
//                    webView.setVisibility(View.GONE);
//                    if (loadScript) {
//                        loadScript = false;
//                        view.loadUrl("javascript:document.getElementById('user').value = '"
//                                + spo_username
//                                + "';document.getElementById('password').value='"
//                                + spo_password
//                                + "';document.forms['frmLogin'].submit();");
//                    }
//
//                } else {
//                    if (url.contains("partner-dashboard") || url.contains("dashboard")) {
//                        shoppingProgress.setVisibility(View.GONE);
//                        webView.setVisibility(View.VISIBLE);
//                    }
//                }
            } catch (Exception exception) {
                exception.printStackTrace();

            }
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack() && !tempUrl.contains("dashboard")) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
