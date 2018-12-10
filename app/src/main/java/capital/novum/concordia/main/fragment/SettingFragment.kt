package capital.novum.concordia.main.fragment

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import capital.novum.concordia.R
import capital.novum.concordia.main.LoginActivity
import capital.novum.concordia.model.LocalData
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.setting.*
import capital.novum.concordia.share.ShareInformationActivity
import capital.novum.concordia.util.Constants
import capital.novum.concordia.util.Utils
import kotlinx.android.synthetic.main.setting_fragment.*
import kotlinx.android.synthetic.main.setting_fragment.view.*

class SettingFragment : Fragment() {
    val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.setting_fragment, container, false)

        val firstName = sharedPreferences.getString(UserConstant.firstName, "")
        val lastName = sharedPreferences.getString(UserConstant.lastName, "")
        val email = sharedPreferences.getString(UserConstant.email, "")
        val countryCode = sharedPreferences.getString(UserConstant.countryCode, null)
        val phoneNumber = sharedPreferences.getString(UserConstant.phoneNumber, null)
        val status = sharedPreferences.getString(UserConstant.kycStatus, null)
        val passportVerify = sharedPreferences.getString(UserConstant.passportVerified, null)
        if (status != Constants.CLEARED) {
            view.statusImg.setImageResource(R.mipmap.timer)
            view.statusTxt.text = "Unverified"
        }
        if (countryCode != null) {
            view.mobileNumberTxt.text = "+$countryCode $phoneNumber"
        } else {
            view.mobileNumberTxt.visibility = View.GONE
            view.btnEditPhone.visibility = View.GONE
        }
        view.nameTxt.text = "${firstName.toUpperCase()} ${lastName.toUpperCase()}"
        view.emailTxt.text = email

        view.touchSetting.setOnClickListener { goToTouchSetting() }

        view.changePassword.setSettingIcon(R.mipmap.user)
        view.changePassword.setSettingName("Change Password")
        view.changePassword.setOnClickListener { goToChangePassword() }

        view.updatePassport.setSettingIcon(R.mipmap.passport)
        view.updatePassport.setSettingName(if (status == Constants.CLEARED || passportVerify == "1")
            "Update Passport" else "Update Passport (VERIFY NOW)")
        view.updatePassport.setOnClickListener { goToUpdatePassport() }

        view.walletList.setSettingIcon(R.mipmap.wallet)
        view.walletList.setSettingName("Update Wallet")
        view.walletList.setOnClickListener { goToWalletList() }

        view.participateHistory.setSettingIcon(R.mipmap.history)
        view.participateHistory.setSettingName("Participate History")
        view.participateHistory.setOnClickListener { goToParticipateHistory() }

        view.referralNetwork.setSettingIcon(R.mipmap.referral_blue)
        view.referralNetwork.setSettingName("My Referral Network")
        view.referralNetwork.setOnClickListener { goToReferralNetwork() }

        view.btnLogout.setOnClickListener { logout() }
        view.btnEditPhone.setOnClickListener {
            Utils.blinkView(activity, view.btnEditPhone)
            goToChangeMobile()
        }
        view.btnShare.setOnClickListener { goToShareWithFriends() }

        view.btnCven.setOnClickListener { goToCvenExchange() }
        return view
    }

    /**
     *  Navigations
     */
    private fun goToTouchSetting() {
        val intent = Intent(activity, PersonalConfigurationActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun goToChangePassword() {
        val intent = Intent(activity, ChangePasswordActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun goToChangeMobile() {
        val intent = Intent(activity, ChangeMobilePhoneActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun goToUpdatePassport() {
        val intent = Intent(activity, UpdatePassportActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun goToWalletList() {
        val intent = Intent(activity, WalletListActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun goToParticipateHistory() {
        val intent = Intent(activity, HistoryListActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun goToReferralNetwork() {
        val intent = Intent(activity, ReferralCodeActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun goToShareWithFriends() {
        val intent = Intent(activity, ShareInformationActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun goToCvenExchange() {
        val intent = Intent(activity, CvenExchangeActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun logout() {
        LocalData.removeUserDetail(activity!!)
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}