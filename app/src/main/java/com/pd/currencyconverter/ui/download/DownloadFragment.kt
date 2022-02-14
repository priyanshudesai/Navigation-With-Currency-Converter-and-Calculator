package com.pd.currencyconverter.ui.download

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.text.format.Formatter
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.pd.currencyconverter.databinding.FragmentDownloadBinding
import com.pd.currencyconverter.utils.FirebaseAnalyticsHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL


class DownloadFragment : Fragment() {

    private lateinit var binding: FragmentDownloadBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadBinding.inflate(inflater, container, false)
        var root = binding.root

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, " Allow the Storage Permission", Toast.LENGTH_LONG).show()
            ActivityCompat.requestPermissions(
                (requireActivity()), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                25
            )
        }

        binding.etUrlDownload.setText("https://research.nhm.org/pdfs/10840/10840.pdf")

        binding.btnDownload.setOnClickListener {
            if (binding.etUrlDownload.text.isNotEmpty()){
                val url = binding.etUrlDownload.text.toString()
                if (Patterns.WEB_URL.matcher(url).matches()){
                    var manager: DownloadManager = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    val uri: Uri =
                        Uri.parse(url)
                    val request = DownloadManager.Request(uri)

                    val fileName: String =
                        URLUtil.guessFileName(url, null, MimeTypeMap.getFileExtensionFromUrl(url))


                    request.setTitle(fileName);
                    request.setDescription("Downloading")
                    request.allowScanningByMediaScanner()
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName)
                    val reference: Long = manager.enqueue(request)

                    val mProgressBar = binding.pbDownload

                    val query = DownloadManager.Query().setFilterById(reference)



//                    binding.tvProgressDownload.visibility = View.VISIBLE
//                    binding.tvFileNameDownload.visibility = View.VISIBLE
//                    binding.pbDownload.visibility = View.VISIBLE
//                    binding.tvStatusDownload.visibility = View.VISIBLE
                    binding.cvCardDownload.visibility = View.VISIBLE

                    binding.tvFileNameDownload.text = fileName

                    lifecycleScope.launchWhenStarted {
                        var lastMsg: String = ""
                        var lastDProgress: Long = 0
                        var lastBytesDownloaded: Long = 0
                        var downloading = true

//                        Log.e("DownloadManagerT", " Lifecycle is :"+Thread.currentThread().name)
                        withContext(Dispatchers.Default) {
//                            Log.e("DownloadManagerT", " Default is :"+Thread.currentThread().name)
                            while (downloading) {
                                Log.e("DownloadManager", "While reached")
                                val cursor: Cursor = manager.query(query)
                                cursor.moveToFirst()
                                if(cursor.moveToFirst()) {
                                    Log.e("DownloadManager", "Cursor move to first")
                                    val bytes_downloaded = cursor.getLong(
                                        cursor
                                            .getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                                    )
                                    val bytes_total =
                                        cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

                                    var dl_progress: Long =
                                        ((bytes_downloaded * 100L) / bytes_total).toLong()

                                    if (cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                                        downloading = false
                                    }
                                    val status =
                                        cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
                                    val msg: String? = statusMessage(
                                        url,
                                        File(Environment.DIRECTORY_DOWNLOADS),
                                        status,
                                        cursor
                                    )
//                            Log.e("DownloadManager", " Status is :$msg")
                                    if (msg != lastMsg) {
                                        withContext(Dispatchers.Main) {
//                                            Log.e(
//                                                "DownloadManagerT",
//                                                " Main is :" + Thread.currentThread().name
//                                            )
                                            binding.tvStatusDownload.text = msg
                                            Log.e("DownloadManager", "Status is :$msg")
                                        }
                                        lastMsg = msg ?: ""
                                    }
                                    if (dl_progress != lastDProgress) {
                                        withContext(Dispatchers.Main) {
                                            mProgressBar.progress = dl_progress.toInt()
                                            binding.tvProgressDownload.text =
                                                dl_progress.toString().plus("%")
                                            Log.e("DownloadManager", "Progress is :$dl_progress")
                                        }
                                        lastDProgress = dl_progress ?: 0
                                    }
                                    if (bytes_downloaded != lastBytesDownloaded) {
                                        withContext(Dispatchers.Main) {
                                            binding.tvSizeDownload.text =
                                                Formatter.formatFileSize(context, bytes_downloaded) + "/" +
                                                        Formatter.formatFileSize(context, bytes_total) +
                                                        " Downloaded"
                                            Log.e(
                                                "DownloadManager",
                                                Formatter.formatFileSize(context, bytes_downloaded) + "/" +
                                                        Formatter.formatFileSize(context, bytes_total) +
                                                        " Downloaded"
                                            )
                                        }
                                        lastBytesDownloaded = bytes_downloaded ?: 0
                                    }
                                }
                                cursor.close()
                            }
                        }
                    }
                }else{
                    binding.etUrlDownload.error = "Enter Valid URL"
                }
            }
        }
        return root
    }

    private fun statusMessage(url: String, directory: File, status: Int, cursor: Cursor): String? {
        var msg = ""
        msg = when (status) {
            DownloadManager.STATUS_FAILED -> {
                val stat = StatFs(context?.getExternalFilesDir(null)?.absolutePath)
                val bytesAvailable = stat.blockSizeLong * stat.availableBlocksLong
                val myUrl = URL(url)
                val urlConnection = myUrl.openConnection()
                urlConnection.connect()
                val fileSize: Long = urlConnection.contentLength.toLong()
                var reason =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_REASON))
                return if (reason == DownloadManager.ERROR_INSUFFICIENT_SPACE) {
                    "Not Enough Space!!\nFile Size : ${Formatter.formatFileSize(context, fileSize)} Available Space : ${Formatter.formatFileSize(context, bytesAvailable)}"
                } else {
                    "Download has been failed, please try again"
                }
            }
            DownloadManager.STATUS_PAUSED -> "Paused"
            DownloadManager.STATUS_PENDING -> "Pending"
            DownloadManager.STATUS_RUNNING -> "Downloading..."
            DownloadManager.STATUS_SUCCESSFUL -> "Downloaded successfully in $directory" + File.separator + url.substring(
                url.lastIndexOf("/") + 1
            )
            else -> "There's nothing to download"
        }
        return msg
    }

    private fun getSize(size: Long): String {
        var s = ""
        val kb = (size / 1024).toDouble()
        val mb = kb / 1024
        val gb = mb / 1024
        val tb = gb / 1024
        if (size < 1024) {
            s = "$size Bytes"
        } else if (size >= 1024 && size < 1024 * 1024) {
            s = String.format("%.2f", kb) + " KB"
        } else if (size >= 1024 * 1024 && size < 1024 * 1024 * 1024) {
            s = String.format("%.2f", mb) + " MB"
        } else if (size >= 1024 * 1024 * 1024 && size < (1024 * 1024).toLong() * (1024 * 1024).toLong()) {
            s = String.format("%.2f", gb) + " GB"
        } else if (size >= (1024 * 1024).toLong() * (1024 * 1024).toLong()) {
            s = String.format("%.2f", tb) + " TB"
        }
        return s
    }

    override fun onResume() {
        super.onResume()
        FirebaseAnalyticsHelper.logScreenEvent("DownloadScreen", "DownloadFragment")
    }
}