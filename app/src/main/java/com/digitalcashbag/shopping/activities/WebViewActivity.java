package com.digitalcashbag.shopping.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.digitalcashbag.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.utils.LoggerUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.shopping_progress)
    ProgressBar progressBar;

    Bundle param;
    String ua = "Mozilla/5.0 (Linux; Android 4.4.4; One Build/KTU84L.H4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.135 Mobile Safari/537.36";

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_lay);
        ButterKnife.bind(this);
        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        Log.e("Link==>", param.getString("link"));
        LoggerUtil.logItem(param.getString("link"));
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
            } else {
                CookieManager.getInstance().setAcceptCookie(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        loadLink("https://www.whatismybrowser.com/detect/are-cookies-enabled");
//        loadLink("https://www.whatismybrowser.com/detect/are-third-party-cookies-enabled");

        progressBar.setVisibility(View.VISIBLE);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUserAgentString(ua);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
//        webView.getSettings().setLightTouchEnabled(true);
//        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
//        webView.getSettings().setEnableSmoothTransition(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
//        MyJavaScriptInterface jsInterface = new MyJavaScriptInterface(this);
//        webView.addJavascriptInterface(jsInterface, "HtmlViewer");
//        webView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");
//        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

//        if (param.getString("link").contains("pdf"))
//            openPDF(param.getString("link"));
//        else
//            loadLink(param.getString("link"));

        loadLink(param.getString("link"));
    }

    void openPDF(String link) {
        String pdf = link;
        webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
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
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void loadLink(String link) {
        try {
            if (param.getString("from").equalsIgnoreCase("Add Money")) {
                webView.setWebViewClient(getWebViewClientEasyPay());
                webView.loadUrl(link);
            } else {
                webView.setWebViewClient(new WebViewClientNormal());
                webView.loadUrl(link);
            }
        } catch (Error | Exception e) {
            webView.setWebViewClient(new WebViewClientNormal());
            webView.loadUrl(link);
            e.printStackTrace();
        }



    }



    // Open previous opened link from history on webview when back button pressed


    public class WebViewClientNormal extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LoggerUtil.logItem(url);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }


    }


    private WebViewClient getWebViewClientEasyPay() {

        return new WebViewClient() {

            // Handle API until level 21
            @SuppressWarnings("deprecation")
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

                return getNewResponse(url);
            }

            private WebResourceResponse getNewResponse(String url) {

                try {
                    OkHttpClient httpClient = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(url.trim())
                            .addHeader("Authorization", "BES0EP0AGG") // Example header
                            .addHeader("Referer", "sdfsd") // Example header
                            .build();

                    Response response = httpClient.newCall(request).execute();

                    return new WebResourceResponse(
                            null,
                            response.header("content-encoding", "utf-8"),
                            response.body().byteStream()
                    );

                } catch (Exception e) {
                    return null;
                }
            }

            // Handle API 21+
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

                String url = request.getUrl().toString();

                return getNewResponse(url);
            }

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
            }

        };
    }

}
