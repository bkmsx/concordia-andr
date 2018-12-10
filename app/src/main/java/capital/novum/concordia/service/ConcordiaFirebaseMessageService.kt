package capital.novum.concordia.service

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import capital.novum.concordia.R
import capital.novum.concordia.util.Constants
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class ConcordiaFirebaseMessageService : FirebaseMessagingService() {
    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        p0?.let {
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putString(Constants.DEVICE_ID, it).apply()
        }
    }

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        p0?.let {
            it.notification?.let {
                val builder = NotificationCompat.Builder(this, "first")
                        .setContentTitle(it.title)
                        .setContentText(it.body)
                        .setSmallIcon(R.drawable.concordia_white)
                (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                        .notify(1, builder.build())
            }
        }
    }
}