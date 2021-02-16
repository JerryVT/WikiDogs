package com.example.wikidogs.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import com.example.wikidogs.R
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.wikidogs.view.MainActivity

class NotificationsHelper(val context: Context) {

    private val CHANNEL_ID = "Dogs Channel id"
    private val NOTIFICATION_ID = 235

    fun createNotification() {
        createNotificationChannel()

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(  context, 0, intent, 0)

        val icon = BitmapFactory.decodeResource(context.resources,R.drawable.dog_icon)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.dog)
                .setLargeIcon(icon)
                .setContentTitle("Dogs retrieved")
                .setContentText("This notification has some content")
                .setStyle(
                        NotificationCompat.BigPictureStyle()
                                .bigPicture(icon)
                                .bigLargeIcon(null)
                )
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {       //creating channel, applicable onlt for os O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: String = CHANNEL_ID
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel:NotificationChannel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}