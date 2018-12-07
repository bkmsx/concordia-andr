package capital.novum.concordia.util

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.AsyncTask
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import capital.novum.concordia.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.dialog_ask_confirm.*
import kotlinx.android.synthetic.main.dialog_ask_fingerprint.*
import kotlinx.android.synthetic.main.dialog_cven_exchange.*
import kotlinx.android.synthetic.main.dialog_input_password.*
import kotlinx.android.synthetic.main.dialog_notice.*
import kotlinx.android.synthetic.main.dialog_term_conditions.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

object Utils {
    fun getQrCode(content: String) : Bitmap {
        var bmp = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565)
        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return bmp
    }

    fun showCvenDialog(context: Context?) {
        val dialog = Dialog(context)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.dialog_cven_exchange)
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window.setBackgroundDrawableResource(R.color.transparent)
        dialog.btnClose.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    fun showTermConditions(context: Context?, link:String) {
        val dialog = Dialog(context)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.dialog_term_conditions)
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window.setBackgroundDrawableResource(R.color.transparent)
        dialog.btnTermsClose.setOnClickListener { dialog.dismiss() }
        dialog.webview.loadUrl(link)
        dialog.webview.webViewClient = object : WebViewClient() {
            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                dialog.progressBar.visibility = View.GONE
            }
        }
        dialog.show()
    }

    fun showNoticeDialog(context: Context?, title: String = "Notice", msg: String = "There is an error", callback: (() -> Unit)? = null) {
        val dialog = Dialog(context)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.dialog_notice)
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window.setBackgroundDrawableResource(R.color.transparent)
        dialog.btnOk.setOnClickListener {
            dialog.dismiss()
            callback?.invoke()
        }
        dialog.title.text = title
        dialog.message.text = msg
        dialog.show()
    }

    fun showConfirmDialog(context: Context?, msg: String? = null, callback: (() -> Unit)? = null) {
        val dialog = Dialog(context)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.dialog_ask_confirm)
        if (msg != null) {
            dialog.messageTxt.text = msg
        }
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window.setBackgroundDrawableResource(R.color.transparent)
        dialog.btnYes.setOnClickListener {
            dialog.dismiss()
            callback?.invoke()
        }
        dialog.btnCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    fun showFingerprintDialog(context: Context?, title: String? = null, msg: String? = null,
                              cancelCallback: (()->Unit)? = null, tryAgainCallback: (()->Unit)? = null) : Dialog{
        val dialog = Dialog(context)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.dialog_ask_fingerprint)
        if (msg != null) {
            dialog.messageTxtFingerprint.text = msg
        } else {
            dialog.messageTxtFingerprint.visibility = View.GONE
        }

        if (title != null) {
            dialog.titleTxtFingerprint.text = title
        }

        if (tryAgainCallback == null) {
            dialog.btnTryAgainFingerprint.visibility = View.GONE
        }

        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window.setBackgroundDrawableResource(R.color.transparent)
        dialog.btnTryAgainFingerprint.setOnClickListener {
            dialog.dismiss()
            tryAgainCallback?.invoke()

        }
        dialog.btnCancelFingerprint.setOnClickListener {
            dialog.dismiss()
            cancelCallback?.invoke()
        }
        dialog.show()
        return dialog
    }

    fun showInputPasswordDialog(context: Context?, title: String? = null, msg: String? = null,
                                submitCallback: ((String)->Unit)? = null) : Dialog{
        val dialog = Dialog(context)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.dialog_input_password)
        if (msg != null) {
            dialog.messageTxtInputPassword.text = msg
        }

        if (title != null) {
            dialog.titleTxtInputPassword.text = title
        }

        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window.setBackgroundDrawableResource(R.color.transparent)
        dialog.submitBtnInputPassword.setOnClickListener {
            dialog.dismiss()
            submitCallback?.invoke(dialog.passwordEdtInputPassword.text.toString())
        }
        dialog.cancelBtnInputPassword.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        return dialog
    }

    fun getFileFromBitmap(context: Context, bitmap: Bitmap, fileName: String) : File {
        val f = File(context.getCacheDir(), fileName);
        f.createNewFile();
        val bos = ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90 /*ignored for PNG*/, bos);
        val bitmapdata = bos.toByteArray();

        val fos = FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return f
    }

    fun copyText(context: Context, text: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.primaryClip = ClipData.newPlainText("text", text)
        Toast.makeText(context, "Copied: $text", Toast.LENGTH_LONG).show()
    }

    fun blinkView(context: Context?, view: View, animId: Int? = null) {
        view.startAnimation(AnimationUtils.loadAnimation(context, animId ?: R.anim.blink_anim))
    }
}

class LoadQrCodeAsync(val callback: (Bitmap) -> Unit) : AsyncTask<String, Void, Bitmap>() {
    override fun doInBackground(vararg params: String?): Bitmap {
        return Utils.getQrCode(params[0]!!)
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        callback(result!!)
    }
}