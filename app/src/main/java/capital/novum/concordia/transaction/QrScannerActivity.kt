package capital.novum.concordia.transaction

import me.dm7.barcodescanner.zxing.ZXingScannerView
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.zxing.Result


class QrScannerActivity : Activity(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZXingScannerView(this)   // Programmatically initialize the scanner view
        setContentView(mScannerView)                // Set the scanner view as the content view
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(rawResult: Result) {
        Log.v("QR", rawResult.getText()) // Prints scan results
        val intent = Intent()
        intent.putExtra("qrCode", rawResult.text)
        setResult(RESULT_OK, intent)
        finish()
    }
}