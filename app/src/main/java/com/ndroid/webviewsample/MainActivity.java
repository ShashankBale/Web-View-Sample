package com.ndroid.webviewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etAddressBar;
    ProgressBar pbWebView;
    ImageView ivLogo;
    ImageButton ibSearch;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjects();

        WebSettings webViewSettings = webView.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setUseWideViewPort(true);
        webViewSettings.setLoadWithOverviewMode(true);
        webViewSettings.setTextZoom(100);
        webViewSettings.setBuiltInZoomControls(true);

        String userAgent = webViewSettings.getUserAgentString();
        int insertIndex = userAgent.lastIndexOf(" ");
        userAgent = userAgent.substring(0, insertIndex) + " Shashank/1.0" + userAgent.substring(insertIndex);

        webViewSettings.setUserAgentString(userAgent);

        //webView.setWebViewClient(new WebViewClient());
        webView.setWebViewClient(new MyWebViewClient(this));
        addListeners();

        //load EditText url at first launch
        ibSearch.callOnClick();
    }

    private void initObjects() {
        pbWebView = findViewById(R.id.pbWebView);
        ivLogo = findViewById(R.id.ivLogo);
        etAddressBar = findViewById(R.id.etAddressBar);
        ibSearch = findViewById(R.id.ibSearch);
        webView = findViewById(R.id.webView);
    }

    private void addListeners() {
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = etAddressBar.getText().toString().trim();
                if (url.isEmpty()) {
                    showMsg("Address cannot be empty");
                    return;
                }
                if(!url.contains("http:") && !url.contains("https:"))
                {
                    url = "http://"+url;
                }

                webView.loadUrl(url);
                hideKeyboard(MainActivity.this);
            }
        });


        etAddressBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ibSearch.callOnClick();
                    return true;
                }
                return false;
            }
        });
    }


    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    public void showProgress(boolean b) {
        pbWebView.setVisibility(b ? View.VISIBLE : View.GONE);
        ivLogo.setVisibility(!b ? View.VISIBLE : View.GONE);
    }

    public void updateCurrentUrl(String url) {
        etAddressBar.setText(url);
    }
}
