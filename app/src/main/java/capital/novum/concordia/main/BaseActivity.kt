package capital.novum.concordia.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.ImageView
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

abstract class BaseActivity : AppCompatActivity() {
    lateinit var rightToolbarButton: ImageView
    lateinit var leftToolbarButton: ImageView
    lateinit var toolbarTitle: TextView

    val concordiaService by lazy {
        ConcordiaService.create()
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
        leftToolbarButton.setOnClickListener{leftToolbarClick()}
        rightToolbarButton.setOnClickListener{rightToolbarClick()}
    }

    open fun rightToolbarClick() {

    }

    open fun leftToolbarClick() {
        onBackPressed()
    }

    abstract fun getLayoutId(): Int

    open fun customViews(){}

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

}
