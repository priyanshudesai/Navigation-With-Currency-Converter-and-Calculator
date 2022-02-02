package com.pd.currencyconverter.ui.cardlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pd.currencyconverter.databinding.ActivityCardImageViewBinding
import com.pd.currencyconverter.utils.ConstantUtils

class CardImageViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardImageViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardImageViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.photoView.setImageBitmap(ConstantUtils.bitmap)
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
}