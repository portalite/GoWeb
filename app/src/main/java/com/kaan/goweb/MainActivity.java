
package com.kaan.goweb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    WebView web;
    EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout bar = new LinearLayout(this);

        address = new EditText(this);
        address.setSingleLine(true);
        address.setText("https://www.google.com");
        address.setLayoutParams(
            new LinearLayout.LayoutParams(0, -2, 1)
        );

        Button go = new Button(this);
        go.setText("Git");

        bar.addView(address);
        bar.addView(go);

        web = new WebView(this);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);

        layout.addView(bar);
        layout.addView(web, new LinearLayout.LayoutParams(-1, 0, 1));

        setContentView(layout);

        go.setOnClickListener(v -> openSite());
        address.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO ||
                actionId == EditorInfo.IME_ACTION_DONE) {
                openSite();
                return true;
            }
            return false;
        });

        openSite();
    }

    void openSite() {
        String url = address.getText().toString().trim();

        if (!url.startsWith("http://") &&
            !url.startsWith("https://")) {
            url = "https://" + url;
        }

        web.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
