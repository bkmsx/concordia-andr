package capital.novum.concordia.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import capital.novum.concordia.R

abstract class BaseActivity : AppCompatActivity() {
    lateinit var rightToolbarButton: ImageView
    lateinit var leftToolbarButton: ImageView
    lateinit var toolbarTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        var toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        if (toolbar != null) {
            toolbar.title = ""
            setSupportActionBar(toolbar)
            setupToolBar()
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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

    }

    abstract fun getLayoutId(): Int
}
