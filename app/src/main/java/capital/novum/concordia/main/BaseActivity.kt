package capital.novum.concordia.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import capital.novum.concordia.R
import capital.novum.concordia.model.Nationality
import capital.novum.concordia.service.ConcordiaService
import capital.novum.concordia.util.Constant
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.app.Activity
import android.database.Observable
import android.renderscript.Element
import android.view.inputmethod.InputMethodManager
import capital.novum.concordia.model.LocalData
import capital.novum.concordia.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


abstract class BaseActivity : AppCompatActivity() {
    lateinit var rightToolbarButton: ImageView
    lateinit var leftToolbarButton: ImageView
    lateinit var toolbarTitle: TextView
    lateinit var progressSpinner: LinearLayout

    val concordiaService by lazy {
        ConcordiaService.create(Constant.BASE_URL)
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
}
