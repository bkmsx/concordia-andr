package capital.novum.concordia.util

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.view.ViewGroup
import capital.novum.concordia.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.dialog_cven_exchange.*
import kotlinx.android.synthetic.main.dialog_notice.*
import kotlinx.android.synthetic.main.dialog_term_conditions.*

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
        dialog.show()
    }

    fun showNoticeDialog(context: Context?, title: String = "Notice", msg: String = "There is an error") {
        val dialog = Dialog(context)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.dialog_notice)
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window.setBackgroundDrawableResource(R.color.transparent)
        dialog.btnOk.setOnClickListener { dialog.dismiss() }
        dialog.title.text = title
        dialog.message.text = msg
        dialog.show()
    }
}