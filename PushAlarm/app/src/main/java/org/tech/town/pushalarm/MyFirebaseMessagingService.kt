package org.tech.town.pushalarm

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        createNotificationChannel()

        val type = message.data["type"]
            ?.let { NotificationType.valueOf(it) }
        val title = message.data["title"]
        val body = message.data["body"]

        type ?: return



        NotificationManagerCompat.from(this)
            .notify(type.id, createNotification(type, title, body))

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESCRIPTION

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    @SuppressLint("RemoteViewLayout")
    private fun createNotification(
        type: NotificationType,
        title: String?,
        body: String?
    ): Notification {
        val intent = Intent(this, MainActivity::class.java).apply{
            putExtra("notificationType", "${type.title} 타입")
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(this, type.id, intent, FLAG_UPDATE_CURRENT)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)


        when (type) {
            NotificationType.NORMAL -> Unit
            NotificationType.EXPANDABLE -> {
                notificationBuilder.setStyle(
                    NotificationCompat.BigTextStyle().bigText(
                        "\uD83D\uDE00 \uD83D\uDE03 \uD83D\uDE04 " +
                                "\uD83D\uDE01 \uD83D\uDE06 \uD83D\uDE05 " +
                                "\uD83D\uDE02 \uD83E\uDD23 \uD83E\uDD72 " +
                                "\uD83E\uDD79 ☺️ \uD83D\uDE0A \uD83D\uDE07 " +
                                "\uD83D\uDE42 \uD83D\uDE43 \uD83D\uDE09 " +
                                "\uD83D\uDE0C \uD83D\uDE0D \uD83E\uDD70 " +
                                "\uD83D\uDE18 \uD83D\uDE17 \uD83D\uDE19 " +
                                "\uD83D\uDE1A \uD83D\uDE0B \uD83D\uDE1B " +
                                "\uD83D\uDE1D \uD83D\uDE1C \uD83E\uDD2A " +
                                "\uD83E\uDD28 \uD83E\uDDD0 \uD83E\uDD13 " +
                                "\uD83D\uDE0E \uD83E\uDD78 \uD83E\uDD29 " +
                                "\uD83E\uDD73 \uD83D\uDE0F \uD83D\uDE12 " +
                                "\uD83D\uDE1E \uD83D\uDE14 \uD83D\uDE1F " +
                                "\uD83D\uDE15 \uD83D\uDE41 ☹️ \uD83D\uDE23" +
                                " \uD83D\uDE16 \uD83D\uDE2B \uD83D\uDE29 " +
                                "\uD83E\uDD7A \uD83D\uDE22 \uD83D\uDE2D " +
                                "\uD83D\uDE2E\u200D\uD83D\uDCA8" +
                                " \uD83D\uDE24 \uD83D\uDE20 \uD83D\uDE21" +
                                " \uD83E\uDD2C \uD83E\uDD2F \uD83D\uDE33 " +
                                "\uD83E\uDD75 \uD83E\uDD76 \uD83D\uDE31 \uD83D\uDE28 " +
                                "\uD83D\uDE30 \uD83D\uDE25 \uD83D\uDE13 \uD83E\uDEE3 " +
                                "\uD83E\uDD17 \uD83E\uDEE1 \uD83E\uDD14 \uD83E\uDEE2 " +
                                "\uD83E\uDD2D \uD83E\uDD2B \uD83E\uDD25 \uD83D\uDE36 " +
                                "\uD83D\uDE36\u200D\uD83C\uDF2B️" +
                                " \uD83D\uDE10 \uD83D\uDE11 \uD83D\uDE2C " +
                                "\uD83E\uDEE0 \uD83D\uDE44 \uD83D\uDE2F "

                    )
                )
            }
            NotificationType.CUSTOM -> {
                notificationBuilder
                    .setStyle(
                        NotificationCompat.DecoratedCustomViewStyle()
                    )
                    .setCustomContentView(
                        RemoteViews(
                            packageName,
                            R.layout.view_custom_notification
                        ).apply {
                            setTextViewText(R.id.title, title)
                            setTextViewText(R.id.body, body)
                        }
                    )
            }
        }


        return notificationBuilder.build()
    }

    companion object {
        private const val CHANNEL_NAME = "Emoji Party"
        private const val CHANNEL_DESCRIPTION = "Emoji Party를 위한 채널"
        private const val CHANNEL_ID = "Channel Id"
    }
}