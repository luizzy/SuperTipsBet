
package com.winningbets.supertipsbet;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class livescorefrag extends Fragment {

    WebView webView;

    private static final String TAG ="FACEBOOK_ADS" ;

    ProgressBar progressBar;
    View v;

    SwipeRefreshLayout refresher;
    private AdView mAdView;




    public livescorefrag() {
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message) {

            switch (message.what) {
                case 1:{
                    webViewGoBack();
                }break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.telegram_websites, container, false);


        mAdView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        progressBar =  v.findViewById(R.id.progressBar2);
        progressBar.setMax(100);

        refresher =  v.findViewById(R.id.refresher);
        refresher.setColorSchemeResources(R.color.blue, R.color.lightBlue, R.color.deepPurple, R.color.purple, R.color.pink, R.color.orange, R.color.red);
        webView =  v.findViewById(R.id.webView);
        webView.setFocusableInTouchMode(false);
        webView.setFocusable(false);
        webView.setWebViewClient(new livescorefrag.WebViewClientDemo());
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;


            }
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,     String url) {
                if(url.contains("google")||url.contains("facebook")){
                    InputStream textStream = new ByteArrayInputStream("".getBytes());
                    return getTextWebResource(textStream);
                }
                return super.shouldInterceptRequest(view, url);
            }

        });;
        webView.setWebChromeClient(new livescorefrag.myWebChrome());
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically (true); // set js can open a window, such as the window.open (), default is false
        mWebSettings.setJavaScriptEnabled (true); // whether to allow JavaScript scripts to run, the default is false. When set true, it will remind cause XSS vulnerabilities
        mWebSettings.setSupportZoom (true); // Can zoom, default true
        mWebSettings.setBuiltInZoomControls (true); // whether to display the zoom button, the default false
        mWebSettings.setUseWideViewPort (true); // set this property, it can be arbitrarily scaled. Large view mode
        mWebSettings.setLoadWithOverviewMode (true); // solve together and setUseWideViewPort (true) page adaptation issues
        mWebSettings.setAppCacheEnabled (true); // whether to use the cache
        mWebSettings.setDomStorageEnabled (true); // enable local store DOM
        mWebSettings.setLoadsImagesAutomatically (true); // Load picture
        mWebSettings.setMediaPlaybackRequiresUserGesture (false); // play audio, multimedia require users to manually? Set to false to automatically play

        webView.setOnKeyListener(new View.OnKeyListener(){

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && webView.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }

                return false;
            }

        });

        CookieManager.getInstance().setAcceptCookie(true);
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = cm.getActiveNetwork();
            if (nw == null) {
                webView.loadUrl("http://www.livescore.cz");
            } else {
                NetworkInfo nwInfo = cm.getActiveNetworkInfo();
                webView.loadUrl("http://www.livescore.cz");
            }


        } else {
            Toast.makeText(getActivity(), "Network problem, please reload the page", Toast.LENGTH_SHORT).show();
        }
        refresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager cm =
                        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                    webView.loadUrl("http://www.livescore.cz");


                } else {
                    Toast.makeText(getActivity(), "Network problem, please reload the page", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return v;
    }
    private WebResourceResponse getTextWebResource(InputStream data) {
        return new WebResourceResponse("text/plain", "UTF-8", data);
    }

    private void webViewGoBack(){
        webView.goBack();
    }

    private class myWebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    private class WebViewClientDemo extends WebViewClient {
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
           webView.loadUrl("javascript:(function() { " + "document.getElementsByClass('adsbygoogle').style.display='none'; })()");

            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            refresher.setRefreshing(false);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            refresher.setRefreshing(true);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}