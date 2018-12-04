package capital.novum.concordia.util

import android.Manifest
import android.annotation.TargetApi
import android.app.Dialog
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.v4.app.ActivityCompat
import android.util.Log
import capital.novum.concordia.transaction.TestActivity
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator

@TargetApi(Build.VERSION_CODES.M)
class FingerprintUtil(val context: Context) : FingerprintManager.AuthenticationCallback() {
    private val cancellationSignal by lazy { CancellationSignal() }
    private val fingerprintManager by lazy { context.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager }
    private val keyguardManager by lazy { context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager }
    private val cipher by lazy {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        keyStore.load(null)

        keyGenerator.init(
                KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build())
        keyGenerator.generateKey()
       val cipher = Cipher.getInstance(
                "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}")
        keyStore.load(null)
        val key = keyStore.getKey(KEY_NAME, null)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        cipher
    }

    private val cryptoObject by lazy {
        FingerprintManager.CryptoObject(cipher)
    }
    private lateinit var callback: () -> Unit
    private lateinit var dialog: Dialog
    private lateinit var email:String
    val KEY_NAME = "Concordia"

    fun canUseFingerprint() : Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false

        if (!fingerprintManager.isHardwareDetected) {
            return false
        }

        if (!fingerprintManager.hasEnrolledFingerprints()) {
            return false
        }

        if (!keyguardManager.isKeyguardSecure) {
            return false
        }

        return true
    }

    fun startAuth(title: String, msg: String, callback: () -> Unit) {
        dialog = Utils.showFingerprintDialog(context, title = title, msg = msg, cancelCallback = {cancel()})
        this.callback = callback
        this.email = msg
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
    }

    private fun cancel() {
        cancellationSignal.cancel()
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        super.onAuthenticationError(errorCode, errString)
        Log.e("Handler", errString.toString())
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        Log.e("Handler", "Failed!!")
        dialog.dismiss()
        dialog = Utils.showFingerprintDialog(context,
                title = "Try Again",
                msg = email,
                cancelCallback = {cancel()}
        )
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        super.onAuthenticationHelp(helpCode, helpString)
        Log.e("Handler", helpString.toString())
    }

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)
        Log.e("Handler", "Success!!")
        dialog.dismiss()
        callback()
    }
}