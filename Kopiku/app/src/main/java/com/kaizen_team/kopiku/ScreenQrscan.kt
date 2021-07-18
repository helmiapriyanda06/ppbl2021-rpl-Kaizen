package com.kaizen_team.kopiku

import android.R.*
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.*
import com.kaizen_team.kopiku.ui.home.HomeFragment


class ScreenQrscan : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_screen_qrscan)
    val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)

    codeScanner = CodeScanner(this, scannerView)

    // Parameters (default values)
    codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
    codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
    // ex. listOf(BarcodeFormat.QR_CODE)
    codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
    codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
    codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
    codeScanner.isFlashEnabled = false // Whether to enable flash or not

    // Callbacks
    codeScanner.decodeCallback = DecodeCallback {
        runOnUiThread {
            //val intent = Intent(this, HomeActivity::class.java)
            //intent.putExtra("data1", it.text.toString())
            //startActivity(intent)
            //finish()
            val bundle = Bundle()
            bundle.putString("data1", it.text )

            val fragInfo = HomeFragment()
            fragInfo.setArguments(bundle)
            //Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
        }
    }
    codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
        runOnUiThread {
            Toast.makeText(this, "Camera initialization error: ${it.message}",
                Toast.LENGTH_LONG).show()
        }
    }

    scannerView.setOnClickListener {
        codeScanner.startPreview()
    }
}

override fun onResume() {
    super.onResume()
    codeScanner.startPreview()
}

override fun onPause() {
    codeScanner.releaseResources()
    super.onPause()
}
}