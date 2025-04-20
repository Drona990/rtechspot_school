package com.rtechspot.school.students;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.rtechspot.school.R;
import com.rtechspot.school.utils.Constants;
import com.rtechspot.school.utils.Utility;
import static android.widget.Toast.makeText;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class CoursePayment extends AppCompatActivity {
    WebView webView;
    String CourseId;
    String url;
    public ImageView backBtn;
    public TextView titleTV;
    protected FrameLayout mDrawerLayout, actionBar;
    private static boolean firstTime = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale(getApplicationContext(), Utility.getSharedPreferences(getApplicationContext(), Constants.langCode));
        setContentView(R.layout.coursepayment_activity);
        backBtn = findViewById(R.id.actionBar_backBtn);
        actionBar = findViewById(R.id.actionBarSecondary);
        titleTV = findViewById(R.id.actionBar_title);
        actionBar.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.primaryColour)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.primaryColour)));
        }
        webView = new WebView(this);
        if (firstTime) {
            recreate();
            firstTime = false;
            return;
        }

        webView = findViewById(R.id.web_view);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.no_animation,  R.anim.slide_rightleft);
            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }
        titleTV.setText(getApplicationContext().getString(R.string.coursePayment));

        CourseId = getIntent().getStringExtra("CourseId");

        if (Utility.isConnectingToInternet(getApplicationContext())) {
             url = Utility.getSharedPreferences(getApplicationContext(), "apiUrl") + Constants.coursepaymentGatewayUrl;
            url += CourseId + "/" + Utility.getSharedPreferences(getApplicationContext(), "studentId");
            Log.e("URL", url);
        } else {
            makeText(getApplicationContext(),R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
        }

        final ProgressDialog pd = ProgressDialog.show(CoursePayment.this, "", "Loading..", true);
        webView.getSettings().setJavaScriptEnabled(true); // enable javascript
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setUserAgentString(String.valueOf(Utility.getSharedPreferences(getApplicationContext(), Constants.langCode)));  //disable this line for sbiepay
        webView.getSettings().setLoadWithOverviewMode(true);

        final Activity activity = this;
        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
               // Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }


            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();
            }
        });
        Log.e("Payment URL", "URL " + url);
        webView.loadUrl(url);
    }

    @Override
    protected void onStop() {
        new WebView(this).destroy();
        Utility.setLocale(getApplicationContext(), Utility.getSharedPreferences(getApplicationContext(), Constants.langCode));
        super.onStop();
    }

    public void setLocale(Context context, String localeName){
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale myLocale = new Locale(localeName);
        Locale.setDefault(myLocale);
        configuration.setLocale(myLocale);

        if (Build.VERSION.SDK_INT >= 25) {
            context = context.getApplicationContext().createConfigurationContext(configuration);
            context = context.createConfigurationContext(configuration);
        }

        context.getResources().updateConfiguration(configuration,
                resources.getDisplayMetrics());
    }
}
