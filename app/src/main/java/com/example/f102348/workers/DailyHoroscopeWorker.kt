package com.example.f102348.workers

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.f102348.R
import com.example.f102348.database.MyDatabaseHelper
import com.example.f102348.enums.SignEnum
import com.example.f102348.network.ApiCaller


@JvmField
val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
    "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications when horoscope is updating"

@JvmField
val NOTIFICATION_TITLE: CharSequence = "DailyHoroscope"
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID = 1

class DailyHoroscopeWorker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {
    private lateinit var builder: NotificationCompat.Builder

    override fun doWork(): Result {
        makeStatusNotification(context.getString(R.string.getting_horoscope), context)
        return try {
            val dbHelper = MyDatabaseHelper(context)
            val database: SQLiteDatabase = dbHelper.writableDatabase
            SignEnum.values().forEach { value ->
                dbHelper.addSign(
                    ApiCaller.getSignWithPredictions(
                        value.name.lowercase(),
                        value.stringResource
                    )
                )
            }
            makeStatusNotification(context.getString(R.string.horoscope_stored), context)
            dbHelper.close()
            database.close()
            Result.success()
        } catch (e: Exception) {
            makeStatusNotification(context.getString(R.string.error_data), context)
            Result.failure()
        }
    }

    private fun makeStatusNotification(message: String, context: Context) {
        val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)

        builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            AlertDialog.Builder(context)
                .setCancelable(false)
                .setMessage(context.getString(R.string.explain_permission))
                .setPositiveButton(context.getString(R.string.sure)) { _, _ ->
                    showNotification()
                }
                .setNegativeButton(context.getString(R.string.no)) { dialog, _ ->
                    dialog.cancel()
                }
                .show()
            return
        }
        showNotification()
    }

    @SuppressLint("MissingPermission")
    private fun showNotification() =
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}

