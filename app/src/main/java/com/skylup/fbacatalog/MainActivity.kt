package com.skylup.fbacatalog

import android.content.res.AssetManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.IOException
import java.io.InputStream
import android.webkit.MimeTypeMap;

class MainActivity : AppCompatActivity() {
    var webview: WebView? = null;
    var am: AssetManager? = null;
    var stream: InputStream? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        am = super.getAssets();

        webview = findViewById(R.id.webView);
        webview!!.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                var path: String = request.url.toString().replace("https://fbacatalog.com", "www");
                path = path.replace(Regex("https://.+cloudfront.+/((?:images|flags).+)"), "www/$1");
                try {
                    stream = am!!.open(path);
                    var extention: String = path.replace(Regex(".+\\."), "");
                    return WebResourceResponse(MimeTypeMap.getSingleton().getMimeTypeFromExtension(extention),
                        "utf-8", stream);
                } catch (ex: IOException) {
                }

                return super.shouldInterceptRequest(view, request);
            }
        }
        webview!!.loadUrl("https://fbacatalog.com/")
    }
}
