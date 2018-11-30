package capital.novum.concordia.registration

import android.app.DatePickerDialog
import android.content.Intent
import android.util.Log
import android.widget.DatePicker
import android.widget.TextView
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.registration_activity.*

class RegistrationActivity: BaseActivity(), DatePickerDialog.OnDateSetListener {
    lateinit var datePicker: DatePickerDialog

    /*
    *   Custom Views
    */

    override fun getLayoutId(): Int {
        return R.layout.registration_activity
    }

    override fun customViews() {
        super.customViews()
        datePicker = DatePickerDialog(this, this, 1993, 0, 1)
        btnNext.setOnClickListener { register() }
        dobEdt.setOnClickListener { datePicker.show() }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "NEW USER REGISTRATION"
    }

    /*
        Events
     */
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dobEdt.setText("${dayOfMonth}/${month + 1}/$year", TextView.BufferType.EDITABLE)
    }

    /**
     *  Call API
     */
    private fun register() {
        val password = passwordEdt.text.toString()
        val confirmPass = confirmPassEdt.text.toString()
        if (password != confirmPass) {
            Utils.showNoticeDialog(this, msg = "Passwords don\'t match")
            return
        }
        val params = hashMapOf(
                "first_name" to firstNameEdt.text.toString(),
                "last_name" to lastNameEdt.text.toString(),
                "date_of_birth" to dobEdt.text.toString(),
                "email" to emailEdt.text.toString(),
                "password" to password,
                "device_security_enable" to "${radioYes.isChecked}",
                "type_of_security" to "TOUCHID",
                "referral_code" to referralCodeEdt.text.toString(),
                "device_id" to "123",
                "platform" to "Android",
                "validation" to "0"
        )
        requestHttp(UrlConstant.REGISTER, params) {
            goNext()
        }
    }

    /**
     *  Navigation
     */

    fun goNext() {
        val intent = Intent(this, RegistrationSuccessActivity::class.java)
        startActivity(intent)
    }
}