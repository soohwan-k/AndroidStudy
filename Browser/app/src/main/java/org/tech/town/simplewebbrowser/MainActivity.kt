package org.tech.town.simplewebbrowser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    private val addressBar: EditText by lazy {
        findViewById(R.id.addressBar)
    }
    private val webView: WebView by lazy {
        findViewById(R.id.webView)
    }

    private val homeButton: ImageButton by lazy {
        findViewById(R.id.homeButton)
    }

    private val backButton: ImageButton by lazy {
        findViewById(R.id.backButton)
    }

    private val forwardButton: ImageButton by lazy {
        findViewById(R.id.forwardButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        bindViews()
    }

    override fun onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(DEFAULT_URL)
        }
    }

    private fun bindViews() {
        addressBar.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                webView.loadUrl("http://" + addressBar.text.toString())
            }

            return@setOnEditorActionListener false
        }

        backButton.setOnClickListener {
            webView.goBack()
        }

        forwardButton.setOnClickListener {
            webView.goForward()
        }

        homeButton.setOnClickListener(){
            webView.loadUrl(DEFAULT_URL)
        }
    }

    companion object{
        private const val DEFAULT_URL = "http://www.google.com"
    }
}