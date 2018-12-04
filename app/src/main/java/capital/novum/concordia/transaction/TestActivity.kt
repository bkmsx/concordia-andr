package capital.novum.concordia.transaction

import android.Manifest
import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.util.Utils
import java.lang.Exception
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator

class TestActivity : BaseActivity() {
    private val tag = javaClass.toString()
    lateinit var cipher: Cipher
    lateinit var keyStore: KeyStore
    lateinit var keyGenerator: KeyGenerator
    lateinit var cryptoObject: FingerprintManager.CryptoObject
    lateinit var fingerprintManager: FingerprintManager
    lateinit var keyguardManager: KeyguardManager
    val KEY_NAME = "Concordia"
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.test_activity
    }

    override fun customViews() {
        super.customViews()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            fingerprintManager = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
            if (!fingerprintManager.isHardwareDetected) {
                Utils.showNoticeDialog(this, msg = "Device doesn\'t support fingerprint authentication")
                return
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                Utils.showNoticeDialog(this, msg = "Please grant fingerprint permission")
                return
            }

            if (!fingerprintManager.hasEnrolledFingerprints()) {
                Utils.showNoticeDialog(this, msg = "No fingerprint configured")
                return
            }

            if (!keyguardManager.isKeyguardSecure) {
                Utils.showNoticeDialog(this, msg = "Please enable lockscreen security in your device")
                return
            }

            generateKey()
            if (initCipher()) {
                cryptoObject = FingerprintManager.CryptoObject(cipher)
                val helper = FingerprintHandler()
                helper.startAuth(fingerprintManager, cryptoObject)
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun generateKey() {
        keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        keyStore.load(null)

        keyGenerator.init(
                KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build())
        keyGenerator.generateKey()
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun initCipher() : Boolean {
        cipher = Cipher.getInstance(
                "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}")
        keyStore.load(null)
        val key = keyStore.getKey(KEY_NAME, null)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return true
    }

    @TargetApi(Build.VERSION_CODES.M)
    private class FingerprintHandler() : FingerprintManager.AuthenticationCallback() {
        lateinit var cancellationSignal: CancellationSignal

        fun startAuth(manager: FingerprintManager, cryptoObject: FingerprintManager.CryptoObject) {
            cancellationSignal = CancellationSignal()
            manager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
            super.onAuthenticationError(errorCode, errString)
            Log.e("Handler", errString.toString())
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Log.e("Handler", "Failed!!")
        }

        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
            super.onAuthenticationHelp(helpCode, helpString)
            Log.e("Handler", helpString.toString())
        }

        override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
            super.onAuthenticationSucceeded(result)
            Log.e("Handler", "Success!!")
        }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "PARTICIPATE"
    }

    /*
        Events
     */

    fun goNext() {
        val intent = Intent(this, AmountTokensActivity::class.java)
        startActivity(intent)
    }

}