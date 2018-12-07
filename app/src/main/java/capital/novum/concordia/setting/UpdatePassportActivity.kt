package capital.novum.concordia.setting

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.Citizenship
import capital.novum.concordia.model.Country
import capital.novum.concordia.model.Nationality
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.registration.VerifyOTPActivity
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import kotlinx.android.synthetic.main.setting_update_passport_activity.*

class UpdatePassportActivity : BaseActivity(), DatePickerDialog.OnDateSetListener {
    lateinit var citizenships: List<Citizenship>
    lateinit var countries: List<Country>
    lateinit var sharedPreference: SharedPreferences
    var passportBitmap: Bitmap? = null
    var selfieBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCitizenship()
    }
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

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        passportNumberEdt.setText(sharedPreference.getString(UserConstant.passportNumber, ""))
        dobEdt.setText(sharedPreference.getString(UserConstant.dateOfBirth, ""))
        phoneNumberEdt.setText(sharedPreference.getString(UserConstant.phoneNumber, ""))

        val datePicker = DatePickerDialog(this, this, 1993, 0, 1)
        dobEdt.setOnClickListener { datePicker.show() }

        btnPassport.setOnClickListener {
            gotoGallery()
        }
        btnSelfie.setOnClickListener {
            Utils.blinkView(this, btnSelfie)
            askCameraPermission { gotoCamera() }
        }

        btnNext.setOnClickListener { validateData() }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "UPDATE PASSPORT DETAILS"
    }

    /**
     *  Events
     */
    private fun validateData() {
        if (dobEdt.text.toString() == "") {
            Utils.showNoticeDialog(this, msg = "Please pick your date of birth")
            return
        }
        if (passportNumberEdt.text.toString() == "") {
            Utils.showNoticeDialog(this, msg = "Please input your passport number")
            return
        }

        if (phoneNumberEdt.text.toString() == "") {
            Utils.showNoticeDialog(this, msg = "Please input your phone number")
            return
        }

        if (passportBitmap == null) {
            Utils.showNoticeDialog(this, msg = "Please select passport photo to upload")
            return
        }

        sendOTP()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dobEdt.setText("${dayOfMonth}/${month + 1}/$year", TextView.BufferType.EDITABLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            selfieBitmap = data?.extras?.get("data") as Bitmap
            btnSelfie.setImageBitmap(selfieBitmap)
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            val imageStream = contentResolver.openInputStream(imageUri)
            passportBitmap = BitmapFactory.decodeStream(imageStream)
            btnPassport.setBitmap(passportBitmap as Bitmap)
        }

        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            updatePassport()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gotoCamera()
                } else {
                    Utils.showNoticeDialog(this, msg = "Take picture feature needs camera permission")
                }
            }
            else -> {}
        }
    }

    /**
     *  Call API
     */
    private fun updatePassport() {
        val citizenship = spinnerCitizenship.getSelected()
        val citizenshipId = citizenships.findLast { it.nationality == citizenship }?.nationalityId
        val country = spinnerCountry.getSelected()
        val passportNumber = passportNumberEdt.text.toString()
        val countryCode = countryCodePicker.selectedCountryCode
        val phoneNumber = phoneNumberEdt.text.toString()
        val dob = dobEdt.text.toString()
        val params = hashMapOf(
                "citizenship" to citizenship,
                "citizenship_id" to "$citizenshipId",
                "country_of_residence" to country,
                "passport_number" to passportNumber,
                "country_code" to countryCode,
                "phone_number" to phoneNumber,
                "date_of_birth" to dob
        )
        val passportFile = Utils.getFileFromBitmap(this, passportBitmap as Bitmap, "passport")
        val selfieFile = if (selfieBitmap != null) Utils.getFileFromBitmap(this, selfieBitmap as Bitmap, "selfie") else null
        uploadFilesWithParams(passportFile, selfieFile, params) {
            Utils.showNoticeDialog(this, msg = "Thanks for uploading passport, take some day to review") {
                sharedPreference.edit()
                        .putString(UserConstant.citizenship, citizenship)
                        .putString(UserConstant.citizenshipId, "$citizenshipId")
                        .putString(UserConstant.countryOfResidence, country)
                        .putString(UserConstant.passportNumber, passportNumber)
                        .putString(UserConstant.countryCode, countryCode)
                        .putString(UserConstant.phoneNumber, phoneNumber)
                        .putString(UserConstant.dateOfBirth, dob)
                        .apply()
                finish()
            }
        }
    }

    private fun getCitizenship() {
        requestHttp(UrlConstant.LIST_CITIZENSHIP) {
            val result = it as Nationality
            citizenships = result.citizenships!!
            countries = result.countries!!
            spinnerCitizenship.setData(citizenships.map { it.nationality })
            spinnerCountry.setData(countries.map { it.country })
        }
    }

    private fun sendOTP() {
        val countryCode = countryCodePicker.selectedCountryCode
        val phoneNumber = phoneNumberEdt.text.toString()
        val currentTime = System.currentTimeMillis()
        val otpTimeStamp = sharedPreference.getLong("otpTimeStamp", 0)
        if (currentTime - otpTimeStamp < 60000L) {
            gotoOtpVerification()
            return
        }
        sharedPreference.edit().putLong("otpTimeStamp", currentTime).apply()

        requestHttp(UrlConstant.SEND_OTP, hashMapOf(
                "country_code" to countryCode,
                "phone_number" to phoneNumber,
                "via" to "sms"
        )) {
            gotoOtpVerification()
        }
    }

    /**
     *  Navigations
     */
    private fun gotoOtpVerification() {
        val intent = Intent(this, VerifyOTPActivity::class.java)
        intent.putExtra("countryCode", countryCodePicker.selectedCountryCode)
        intent.putExtra("phoneNumber", phoneNumberEdt.text.toString())
        startActivityForResult(intent, 3)
    }

    private fun gotoGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.setType("image/*")
        startActivityForResult(galleryIntent, 2)
    }

    private fun gotoCamera() {
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 1)
    }
}