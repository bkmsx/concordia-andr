package capital.novum.concordia.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import capital.novum.concordia.R
import capital.novum.concordia.service.ConcordiaService
import capital.novum.concordia.util.UrlConstant
import io.reactivex.disposables.Disposable
import android.app.Activity
import android.preference.PreferenceManager
import android.util.Log
import android.view.inputmethod.InputMethodManager
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import kotlin.collections.HashMap


abstract class BaseActivity : AppCompatActivity() {
    lateinit var rightToolbarButton: ImageView
    lateinit var leftToolbarButton: ImageView
    lateinit var toolbarTitle: TextView
    lateinit var progressSpinner: LinearLayout

    val concordiaService by lazy {
        ConcordiaService.create(UrlConstant.BASE_URL)
    }

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        if (toolbar != null) {
            toolbar.title = ""
            setSupportActionBar(toolbar)
            setupToolBar()
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        customViews()
    }

    open fun setupToolBar() {
        rightToolbarButton = findViewById(R.id.right_button)
        leftToolbarButton = findViewById(R.id.left_button)
        toolbarTitle = findViewById(R.id.toolbar_title)
        leftToolbarButton.setOnClickListener { leftToolbarClick() }
        rightToolbarButton.setOnClickListener { rightToolbarClick() }
    }

    open fun rightToolbarClick() {

    }

    open fun leftToolbarClick() {
        onBackPressed()
    }

    abstract fun getLayoutId(): Int

    open fun customViews() {
        progressSpinner = LayoutInflater.from(this).inflate(R.layout.progress_bar_spinner, null) as LinearLayout
        (window.decorView.rootView as ViewGroup).getChildAt(0).setOnClickListener { hideSoftKeyboard() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    open fun showProgressSpinner() {
        (window.decorView as ViewGroup).addView(progressSpinner, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }

    open fun hideProgressSpinner() {
        (window.decorView as ViewGroup).removeView(progressSpinner)
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager = getSystemService(
                Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
                currentFocus!!.windowToken, 0)
    }

    fun requestHttp(url: String, params: HashMap<String, String>? = null, callback: (Any) -> Unit) {
        val token = PreferenceManager.getDefaultSharedPreferences(this).getString(UserConstant.token, "")
        val observable = when (url) {
            UrlConstant.DELETE_PARTICIPATE -> concordiaService.deleteParticipate(token, params!!)
            UrlConstant.DELETE_WALLET -> concordiaService.deleteWallet(token, params!!)
            UrlConstant.CHANGE_CONFIGURATION -> concordiaService.changePersonalCofiguration(token, params!!)
            UrlConstant.LIST_PARTICIPATE -> concordiaService.listParticipate(token)
            UrlConstant.PARTICIPATE_DETAIL -> concordiaService.participateDetail(token, params!!)
            UrlConstant.LIST_CITIZENSHIP -> concordiaService.getNationalities()
            UrlConstant.SEND_OTP -> concordiaService.sendOTP(params!!)
            else -> concordiaService.updateWallet(token, params!!)
        }
        showProgressSpinner()
        disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    hideProgressSpinner()
                    if (result.code != 200) {
                        Utils.showNoticeDialog(this, msg = result.message)
                    } else {
                        callback(result)
                    }
                }
    }

    fun uploadFilesWithParams(passport: File, selfie: File?, paramMap: HashMap<String, String>, callback: () -> Unit) {
        val token = PreferenceManager.getDefaultSharedPreferences(this).getString(UserConstant.token, "")

        val params = HashMap<String, RequestBody>()
        paramMap.forEach {
            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), it.value)
            params.put(it.key, requestBody)
        }
        val requestPassport = RequestBody.create(MediaType.parse("multipart/form-data"), passport)
        val passportPart = MultipartBody.Part.createFormData("passport_photo", passport.name, requestPassport)

        val requestSelfie = if (selfie != null) RequestBody.create(MediaType.parse("multipart/form-data"), selfie) else null
        val selfiePart = if (requestSelfie != null) MultipartBody.Part.createFormData("selfie_photo", passport.name, requestSelfie) else null

        val observable = concordiaService.updatePassport(token, params, passportPart, selfiePart)
        showProgressSpinner()
        disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    hideProgressSpinner()
                    if (result.code != 200) {
                        Utils.showNoticeDialog(this, msg = result.message)
                    } else {
                        callback()
                    }
                }
    }
}
