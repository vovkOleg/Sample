package mock.brains.mvvmappskeleton.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import mock.brains.mvvmappskeleton.R
import mock.brains.mvvmappskeleton.component.main.MainActivity
import mock.brains.mvvmappskeleton.core.extension.notificationManager
import mock.brains.mvvmappskeleton.worker.PushUpdateWorker
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import java.util.*

class MessagingService : FirebaseMessagingService(), KoinComponent {

    private val repository by inject<MessagingServiceRepository>()
    private val gson by inject<Gson>()
    private val notifyManager by lazy { notificationManager }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        createChannel()

        Timber.d("NOTIFICATION: ${remoteMessage.notification?.title} <> ${remoteMessage.notification?.body}, DATA <> ${remoteMessage.data}")
        val message = gson.fromJson(remoteMessage.data["body"], Message::class.java) // TODO proper parsing
        showNotification(remoteMessage.data["title"], message)
    }

    private fun getActionIntent(): Intent {
        val intent = Intent(this, MainActivity::class.java)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.action = Intent.ACTION_MAIN
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return intent
    }

    @Suppress("deprecation") // uses older api version
    private fun showNotification(title: String?, message: Message?) {
        val id = message?.userId?.toInt() ?: Date().time.toInt() % Integer.MAX_VALUE

        val notificationBuilder =
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
                NotificationCompat.Builder(applicationContext)
            else
                NotificationCompat.Builder(applicationContext, PRIMARY_CHANNEL)
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)

        val intent = getActionIntent().apply {
            val bundle = Bundle().apply {
                putLong(NOTIFICATION_USER_ID, message?.userId ?: -1L)
            }
            putExtra(NOTIFICATION_BUNDLE, bundle)
        }

        val pendingIntent = PendingIntent.getActivity(applicationContext, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        notificationBuilder.setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher) // TODO set proper icon
            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))
            .setColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setDefaults(Notification.DEFAULT_ALL)
            .setGroupSummary(false)

        if (message?.message.isNullOrEmpty().not()) {
            notificationBuilder.setContentText(message?.message)
        }
        if (title.isNullOrEmpty().not()) {
            notificationBuilder.setContentTitle(title)
        } else {
            notificationBuilder.setContentTitle(getString(R.string.app_name))
        }

        notifyManager.notify(id, notificationBuilder.build())

        if (message?.userId != null) { // TODO change if needed update broadcast by push message
            LocalBroadcastManager.getInstance(this)
                .sendBroadcast(Intent(UPDATE_BROADCAST_NOTIFICATION))
        }
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notifyManager.notificationChannels.isEmpty()) {
                val channel = NotificationChannel(PRIMARY_CHANNEL, PRIMARY_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
                channel.enableLights(true)
                channel.enableVibration(true)
                channel.lightColor = Color.MAGENTA
                channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                notifyManager.createNotificationChannel(channel)
            }
        }
    }

    override fun onNewToken(token: String) {
        repository.setPushToken(token)
        Timber.d("PUSH TOKEN: $token")
        if (repository.getToken().isNotEmpty()) {
            PushUpdateWorker.startWorker(this)
        }
    }

    companion object {

        private const val PRIMARY_CHANNEL = "notifications"
        private const val PRIMARY_CHANNEL_NAME = "Notifications"

        const val NOTIFICATION_BUNDLE = "BUNDLE"
        const val NOTIFICATION_USER_ID = "USER_ID"

        const val UPDATE_BROADCAST_NOTIFICATION = "action.UPDATE_BROADCAST_NOTIFICATION"
    }

    private class Message(
        @SerializedName("user_id")
        val userId: Long?,
        @SerializedName("message")
        val message: String?
    )
}