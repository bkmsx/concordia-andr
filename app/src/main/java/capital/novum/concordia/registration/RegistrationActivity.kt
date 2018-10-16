package capital.novum.concordia.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.main.ProjectListActivity

class RegistrationActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.registration_activity
    }

    fun rightClick(view : View) {
        var intent = Intent(this, ProjectListActivity::class.java)
        startActivity(intent)
    }
}