package capital.novum.concordia.main.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import capital.novum.concordia.R
import capital.novum.concordia.main.LoginActivity
import capital.novum.concordia.setting.*
import capital.novum.concordia.share.ShareInformationActivity
import kotlinx.android.synthetic.main.setting_fragment.view.*

class SettingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.setting_fragment, container, false)

        view.touchSetting.setOnClickListener { goToTouchSetting() }

        view.changePassword.setSettingIcon(R.mipmap.user)
        view.changePassword.setSettingName("Change Password")
        view.changePassword.setOnClickListener { goToChangePassword() }

        view.updatePassport.setSettingIcon(R.mipmap.passport)
        view.updatePassport.setSettingName("Update Passport")
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
        view.btnEditPhone.setOnClickListener { goToChangeMobile() }
        view.btnShare.setOnClickListener { goToShareWithFriends() }

        view.btnCven.setOnClickListener { goToCvenExchange() }
        return view
    }

    /**
     *  Navigations
     */

    private fun goToTouchSetting() {
        val intent = Intent(activity, PersonalConfigurationActivity::class.java)
        startActivity(intent)
    }

    private fun goToChangePassword() {
        val intent = Intent(activity, ChangePasswordActivity::class.java)
        startActivity(intent)
    }

    private fun goToChangeMobile() {
        val intent = Intent(activity, ChangeMobilePhoneActivity::class.java)
        startActivity(intent)
    }

    private fun goToUpdatePassport() {
        val intent = Intent(activity, UpdatePassportActivity::class.java)
        startActivity(intent)
    }

    private fun goToWalletList() {
        val intent = Intent(activity, WalletListActivity::class.java)
        startActivity(intent)
    }

    private fun goToParticipateHistory() {
        val intent = Intent(activity, HistoryListActivity::class.java)
        startActivity(intent)
    }

    private fun goToReferralNetwork() {
        val intent = Intent(activity, ReferralCodeActivity::class.java)
        startActivity(intent)
    }

    private fun goToShareWithFriends() {
        val intent = Intent(activity, ShareInformationActivity::class.java)
        startActivity(intent)
    }

    private fun goToCvenExchange() {
        val intent = Intent(activity, CvenExchangeActivity::class.java)
        startActivity(intent)
    }

    private fun logout() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}