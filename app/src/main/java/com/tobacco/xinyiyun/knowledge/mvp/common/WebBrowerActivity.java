package com.tobacco.xinyiyun.knowledge.mvp.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.base.BaseActivity;
import com.tobacco.xinyiyun.knowledge.view.X5WebView;

import butterknife.Bind;

@LayoutInject(value = R.layout.activity_web_brower, translucentStatus = true)
@ToolbarInject(showBack = true)
public class WebBrowerActivity extends BaseActivity {
    private static final String EXTRA_STRING_URL = "url";
    private static final String EXTRA_STRING_TITLE = "title";
    @Bind(R.id.pb_web)
    ProgressBar mPbWeb;
    @Bind(R.id.web_brower)
    X5WebView mWebBrower;
    @Bind(R.id.ll_content)
    LinearLayout mLlContent;

    public static void start(Context context, String url, String defaultTitle) {
        Intent mIntent = new Intent(context, WebBrowerActivity.class);
        mIntent.putExtra(EXTRA_STRING_URL, url);
        mIntent.putExtra(EXTRA_STRING_TITLE, defaultTitle);
        context.startActivity(mIntent);
    }

    @Override
    public void init() {
        setTitle(getIntent().getStringExtra(EXTRA_STRING_TITLE));
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        CookieSyncManager.getInstance().sync();

        mWebBrower.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              WebResourceRequest request) {

                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        mWebBrower.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
//                setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mPbWeb.setProgress(newProgress);
                if (mPbWeb != null && newProgress != 100) {
                    mPbWeb.setVisibility(View.VISIBLE);
                } else if (mPbWeb != null) {
                    mPbWeb.setVisibility(View.GONE);
                }
            }
        });

        mWebBrower.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {

            }
        });

        mWebBrower.loadUrl(getIntent().getStringExtra(EXTRA_STRING_URL));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebBrower != null && mWebBrower.canGoBack()) {
                mWebBrower.goBack();
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

}
