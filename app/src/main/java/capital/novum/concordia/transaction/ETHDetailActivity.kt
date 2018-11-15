package capital.novum.concordia.transaction

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.WriterException
import android.graphics.Bitmap
import android.graphics.Color
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import kotlinx.android.synthetic.main.transaction_eth_detail_activity.*
import org.jetbrains.annotations.Nullable


class ETHDetailActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.transaction_eth_detail_activity
    }

    override fun customViews() {
        super.customViews()
        header.setIndex(4)

        qrCode.setImageBitmap(getQrCode("Bkmsx"))
    }

    override fun setupToolBar() {
        super.setupToolBar()
        leftToolbarButton.setImageResource(R.mipmap.back_blue)
        toolbarTitle.text = "PARTICIPATE"
    }

    /*
        Events
     */

    fun goNext(view : View) {
        val intent = Intent(this, InputWalletActivity::class.java)
        startActivity(intent)
    }

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
}