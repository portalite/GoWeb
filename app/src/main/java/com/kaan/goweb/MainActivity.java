
package com.kaan.goweb;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import android.webkit.*;
import android.graphics.drawable.GradientDrawable;
import java.net.URLEncoder;

public class MainActivity extends Activity {

    WebView web;
    EditText address;

    int bg = Color.rgb(15, 20, 32);
    int panel = Color.rgb(29, 37, 53);
    int accent = Color.rgb(75, 130, 255);

    int dp(int n) {
        return (int)(n * getResources().getDisplayMetrics().density);
    }

    GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(radius));
        return d;
    }

    TextView button(String text) {
        TextView b = new TextView(this);
        b.setText(text);
        b.setTextColor(Color.WHITE);
        b.setTextSize(19);
        b.setGravity(Gravity.CENTER);
        b.setBackground(shape(panel, 16));
        return b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(bg);
        root.setPadding(dp(12), dp(12), dp(12), dp(8));

        TextView title = new TextView(this);
        title.setText("GoWeb");
        title.setTextSize(27);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(Color.WHITE);

        TextView subtitle = new TextView(this);
        subtitle.setText("KEŞFETMEYE BAŞLA");
        subtitle.setTextSize(10);
        subtitle.setTextColor(Color.LTGRAY);

        root.addView(title);
        root.addView(subtitle);

        LinearLayout bar = new LinearLayout(this);
        bar.setGravity(Gravity.CENTER_VERTICAL);
        bar.setBackground(shape(panel, 18));
        bar.setPadding(dp(10), 0, dp(5), 0);

        address = new EditText(this);
        address.setSingleLine(true);
        address.setText("https://www.google.com");
        address.setHint("Ara veya adres yaz...");
        address.setHintTextColor(Color.LTGRAY);
        address.setTextColor(Color.WHITE);
        address.setTextSize(14);
        address.setBackgroundColor(Color.TRANSPARENT);
        address.setImeOptions(EditorInfo.IME_ACTION_GO);
        address.setSelectAllOnFocus(false);

        bar.addView(address,
            new LinearLayout.LayoutParams(0, dp(52), 1));

        TextView go = new TextView(this);
        go.setText("Git");
        go.setTextColor(Color.WHITE);
        go.setTextSize(14);
        go.setTypeface(null, Typeface.BOLD);
        go.setGravity(Gravity.CENTER);
        go.setBackground(shape(accent, 14));

        LinearLayout.LayoutParams gp =
            new LinearLayout.LayoutParams(dp(55), dp(42));
        bar.addView(go, gp);

        LinearLayout.LayoutParams bp =
            new LinearLayout.LayoutParams(-1, dp(56));
        bp.topMargin = dp(16);
        bp.bottomMargin = dp(10);
        root.addView(bar, bp);

        web = new WebView(this);
        web.setBackgroundColor(Color.WHITE);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);

        root.addView(web,
            new LinearLayout.LayoutParams(-1, 0, 1));

        LinearLayout bottom = new LinearLayout(this);
        bottom.setGravity(Gravity.CENTER);
        bottom.setPadding(0, dp(9), 0, 0);

        TextView back = button("‹");
        TextView forward = button("›");
        TextView home = button("⌂");
        TextView refresh = button("↻");

        TextView[] buttons = {back, forward, home, refresh};

        for (TextView b : buttons) {
            LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(0, dp(46), 1);
            p.setMargins(dp(4), 0, dp(4), 0);
            bottom.addView(b, p);
        }

        root.addView(bottom);
        setContentView(root);

        go.setOnClickListener(v -> openSite());

        address.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO ||
                actionId == EditorInfo.IME_ACTION_DONE) {
                openSite();
                return true;
            }
            return false;
        });

        back.setOnClickListener(v -> {
            if (web.canGoBack()) web.goBack();
        });

        forward.setOnClickListener(v -> {
            if (web.canGoForward()) web.goForward();
        });

        home.setOnClickListener(v -> {
            address.setText("https://www.google.com");
            web.loadUrl("https://www.google.com");
        });

        refresh.setOnClickListener(v -> web.reload());

        openSite();
    }

    void openSite() {
        String text = address.getText().toString().trim();

        if (text.isEmpty()) return;

        if (text.startsWith("http://") ||
            text.startsWith("https://")) {
            web.loadUrl(text);
        } else if (text.matches(
            "(?i)^[a-z0-9.-]+\\.[a-z]{2,}(/.*)?$")) {
            web.loadUrl("https://" + text);
        } else {
            try {
                String query = URLEncoder.encode(text, "UTF-8");
                web.loadUrl(
                    "https://www.google.com/search?q=" + query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
