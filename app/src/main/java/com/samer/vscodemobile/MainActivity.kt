package com.samer.vscodemobile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : Activity() {
        private lateinit var web: WebView
            private var pendingOpen = false
                private var pendingSave = false
                    private var pendingText = ""
                        private var pendingName = "untitled.txt"

                            override fun onCreate(savedInstanceState: Bundle?) {
                                        super.onCreate(savedInstanceState)
                                                web = WebView(this)
                                                        web.settings.javaScriptEnabled = true
                                                                web.settings.domStorageEnabled = true
                                                                        web.settings.allowFileAccess = true
                                                                                web.webViewClient = WebViewClient()
                                                                                        web.webChromeClient = WebChromeClient()
                                                                                                web.addJavascriptInterface(Bridge(), "Android")
                                                                                                        setContentView(web)
                                                                                                                web.loadUrl("file:///android_asset/index.html")
                            }

                                inner class Bridge {
                                            @JavascriptInterface fun openFile() { runOnUiThread { pendingOpen = true; val i = Intent(Intent.ACTION_OPEN_DOCUMENT).apply { addCategory(Intent.CATEGORY_OPENABLE); type = "*/*" }; startActivityForResult(i, 10) } }
                                                    @JavascriptInterface fun saveFile(name: String, content: String) { runOnUiThread { pendingName = name.ifBlank { "untitled.txt" }; pendingText = content; pendingSave = true; val i = Intent(Intent.ACTION_CREATE_DOCUMENT).apply { addCategory(Intent.CATEGORY_OPENABLE); type = "text/plain"; putExtra(Intent.EXTRA_TITLE, pendingName) }; startActivityForResult(i, 11) } }
                                                            @JavascriptInterface fun toast(message: String) { runOnUiThread { Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show() } }
                                }

                                    @Deprecated("Deprecated in Android API but kept for broad device compatibility")
                                        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                                                    super.onActivityResult(requestCode, resultCode, data)
                                                            if (resultCode != RESULT_OK || data?.data == null) return
                                                                    val uri = data.data!!
                                                                            try {
                                                                                            if (requestCode == 10 && pendingOpen) {
                                                                                                                val text = contentResolver.openInputStream(uri)!!.bufferedReader().use { it.readText() }
                                                                                                                                val name = queryName(uri)
                                                                                                                                                val quoted = org.json.JSONObject.quote(text)
                                                                                                                                                                val quotedName = org.json.JSONObject.quote(name)
                                                                                                                                                                                web.evaluateJavascript("window.loadOpenedFile($quotedName,$quoted)", null)
                                                                                                                                                                                                pendingOpen = false
                                                                                            } else if (requestCode == 11 && pendingSave) {
                                                                                                                contentResolver.openOutputStream(uri, "wt")!!.bufferedWriter().use { it.write(pendingText) }
                                                                                                                                web.evaluateJavascript("window.savedFile()", null)
                                                                                                                                                pendingSave = false
                                                                                            }
                                                                            } catch (e: Exception) { Toast.makeText(this, "File error: ${e.message}", Toast.LENGTH_LONG).show() }
                                        }

                                            private fun queryName(uri: Uri): String = (uri.lastPathSegment ?: "opened-file.txt").substringAfterLast('/')
                                                override fun onBackPressed() { if (web.canGoBack()) web.goBack() else super.onBackPressed() }
}

                                                                                            }
                                                                            }
                            }
}