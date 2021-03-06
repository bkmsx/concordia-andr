package capital.novum.concordia.main

import android.os.Bundle
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.fragment.ProjectFragment
import capital.novum.concordia.main.fragment.SettingFragment

class ProjectListActivity : BaseActivity() {
    var isProjectFragment = true
    var projectFragment = ProjectFragment()
    var settingFragment = SettingFragment()

    override fun getLayoutId(): Int {
        return R.layout.project_list_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, projectFragment)
                .commit()
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.visibility = View.INVISIBLE
        leftToolbarButton.visibility = View.VISIBLE
        rightToolbarButton.visibility = View.VISIBLE
        leftToolbarButton.setImageResource(R.mipmap.concordia_5)
    }

    override fun rightToolbarClick() {
        super.rightToolbarClick()
        if (isProjectFragment) {
            supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                    .add(R.id.fragment_container, settingFragment)
                    .commit()
            isProjectFragment = false
            rightToolbarButton.setImageResource(R.mipmap.close)
        } else {
            supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                    .remove(settingFragment)
                    .commit()
            isProjectFragment = true
            rightToolbarButton.setImageResource(R.mipmap.menu)
        }
    }

    override fun leftToolbarClick() {

    }

    override fun onBackPressed() {

    }
}