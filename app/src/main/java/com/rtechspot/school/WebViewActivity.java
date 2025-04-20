package com.rtechspot.school;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.rtechspot.school.utils.Constants;
import com.rtechspot.school.utils.Utility;

public class WebViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        String url = Utility.getSharedPreferences(getApplicationContext(), Constants.apiUrl);
        String webPath = getString(R.string.webUrl);
        if (url != null && !url.isEmpty()) {
            if (url.contains("/api/")) {
                url = url.replace("/api/", webPath);
            } else if (url.contains("/app/")) {
                url = url.replace("/app/", webPath);
            } else if (!url.endsWith(webPath)) {
                if (!url.endsWith("/")) {
                    url += "/";
                }
                url += webPath.replaceFirst("^/", "");
            }
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }
            webView.loadUrl(url);
        } else {
            Toast.makeText(this, "No URL found", Toast.LENGTH_SHORT).show();
        }

    }

}
