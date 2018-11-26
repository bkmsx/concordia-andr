package capital.novum.concordia.setting

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import kotlinx.android.synthetic.main.setting_update_passport_activity.*

class UpdatePassportActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_update_passport_activity
    }

    override fun customViews() {
        super.customViews()
        spinnerCitizenship.changeBackground(R.drawable.gray_bottom_line_bg)
        spinnerCitizenship.changeTextColor(Color.WHITE)
        spinnerCountry.changeBackground(R.drawable.gray_bottom_line_bg)
        spinnerCountry.changeTextColor(Color.WHITE)

        btnPassport.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.setType("image/*")
            startActivityForResult(galleryIntent, 2)
        }
        btnSelfie.setOnClickListener {
            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 1)
        }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "UPDATE PASSPORT DETAILS"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            btnSelfie.setImageBitmap(bitmap)
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            val imageStream = contentResolver.openInputStream(imageUri)
            val image = BitmapFactory.decodeStream(imageStream)
            btnPassport.setBitmap(image)
        }
    }

}