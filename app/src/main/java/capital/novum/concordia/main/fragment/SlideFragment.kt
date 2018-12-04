package capital.novum.concordia.main.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import capital.novum.concordia.R
import capital.novum.concordia.main.LoginActivity
import capital.novum.concordia.registration.RegistrationActivity
import kotlinx.android.synthetic.main.slide_fragment.view.*

class SlideFragment: Fragment() {
    var position: Int = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.slide_fragment, container, false)
        when (position) {
            0 -> {
                view.setBackgroundResource(R.mipmap.bg1)
                view.indicator.getChildAt(0).setBackgroundResource(R.drawable.white_circle_bg)
                view.titleTxt.text = "FOLLOW YOUR HEART"
                view.detailTxt.text = "Concordia Ventures Mobile gives you access to interesting " +
                        "blockchain projects and allows you to support projects which you are passionate in."
            }
            1 -> {
                view.setBackgroundResource(R.mipmap.bg2)
                view.indicator.getChildAt(1).setBackgroundResource(R.drawable.white_circle_bg)
                view.titleTxt.text = "EARLY ACCESS"
                view.detailTxt.text = "Some projects give you early access privileges. Get more value from your " +
                        "early support! A uniquely Concordia advantage!"
            }
            2 -> {
                view.setBackgroundResource(R.mipmap.bg3)
                view.indicator.getChildAt(2).setBackgroundResource(R.drawable.white_circle_bg)
                view.titleTxt.text = "HIGH QUALITY"
                view.detailTxt.text = "We will only release your invesment to the projects owners after the projects owners meet critical " +
                        "milestones set by us."
            }
            3 -> {
                view.setBackgroundResource(R.mipmap.bg4)
                view.indicator.getChildAt(3).setBackgroundResource(R.drawable.white_circle_bg)
                view.titleTxt.text = "RECEIVE COMMISSIONS FOREVER! LIMITED PERIOD ONLY!"
                view.detailTxt.text = "Join now and receive up to 4% of whatever your referees' purchase, forever!"
            }
            else -> {
                view.setBackgroundResource(R.mipmap.bg5)
                view.indicator.getChildAt(4).setBackgroundResource(R.drawable.white_circle_bg)
                view.titleTxt.text = "SIGN UP NOW"
                view.signupBtn.visibility = View.VISIBLE
                view.detailTxt.visibility = View.GONE
                view.signupBtn.setOnClickListener {
                    val loginIntent = Intent(activity, LoginActivity::class.java)
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    activity?.startActivity(loginIntent)
                    val registrationIntent = Intent(activity, RegistrationActivity::class.java)
                    activity?.startActivity(registrationIntent)
                    activity?.finish()
                }
            }
        }
        return view
    }
}