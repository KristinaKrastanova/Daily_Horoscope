package com.example.f102348

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.f102348.workers.DailyHoroscopeWorker
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        thread {
            scheduleDailyHoroscopeUpdate()
        }
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, ChooseSignActivity::class.java))
            finish()
        }, 3000)
    }

    private fun scheduleDailyHoroscopeUpdate() {
        val workManager = WorkManager.getInstance(application)
        val periodicWorkRequest =
            PeriodicWorkRequestBuilder<DailyHoroscopeWorker>(24, TimeUnit.HOURS).build()
        workManager.enqueueUniquePeriodicWork(
            "APICall",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }
}