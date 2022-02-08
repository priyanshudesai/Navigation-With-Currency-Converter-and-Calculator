package com.pd.currencyconverter.ui.cardlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.pd.currencyconverter.databinding.ActivityCardImageViewBinding
import com.pd.currencyconverter.utils.ConstantUtils
import com.pd.currencyconverter.utils.FirebaseAnalyticsHelper
import com.pd.currencyconverter.utils.LocaleHelper

class CardImageViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardImageViewBinding
    private var currentTheme = ConstantUtils.NORMAL
    override fun onCreate(savedInstanceState: Bundle?) {

        currentTheme = PreferenceManager.getDefaultSharedPreferences(this@CardImageViewActivity)
            .getInt(ConstantUtils.KEY_THEME, ConstantUtils.NORMAL)
        setTheme(currentTheme)
        LocaleHelper.setLocale(this)

        super.onCreate(savedInstanceState)
        binding = ActivityCardImageViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.photoView.setImageBitmap(ConstantUtils.bitmap)

        binding.btnBackCardImageView.setOnClickListener {
            super.onBackPressed()
        }
//        val webView = binding.wvCardImage
//        webView.setBackgroundColor(-0x1000000)
//        webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
//        webView.settings.builtInZoomControls = true
//        webView.settings.setSupportZoom(true)
//        webView.getSettings().setAllowFileAccess(true)
//
//        val bitmap: Bitmap = ConstantUtils.bitmap
//        var html = "<html><body><img src='{IMAGE_PLACEHOLDER}' /></body></html>"
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        lifecycleScope.launch{
//            withContext(Dispatchers.Main){
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//                val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
//                val imageBase64: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
//                val image = "data:image/png;base64,$imageBase64"
//
//                html = html.replace("{IMAGE_PLACEHOLDER}", image)
//                webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
//            }
//        }
         //webView.getSettings().setDisplayZoomControls(false);  // API 11


//        webview.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", "")
    }
    override fun onResume() {
        super.onResume()
        FirebaseAnalyticsHelper.logScreenEvent("CardImageViewScreen", "CardImageViewActivity")
    }
}