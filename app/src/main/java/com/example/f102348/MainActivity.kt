package com.example.f102348

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.f102348.database.MyDatabaseHelper
import com.example.f102348.domain.model.Sign
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Today's horoscope"

        val dbHelper = MyDatabaseHelper(this)
        val database: SQLiteDatabase = dbHelper.readableDatabase
        val signResName = intent.extras!!.getInt("singRes")
        val sign: Sign?
        runBlocking {
            sign = dbHelper.getSign(signResName)
            dbHelper.close()
            database.close()
        }
        sign?.let { showOnScreen(signResName, it) }
    }

    private fun showOnScreen(signResName: Int, sign: Sign) {
        findViewById<ImageView>(R.id.main_sign_image)
            .setBackgroundResource(intent.extras!!.getInt("signImage"))
        findViewById<TextView>(R.id.sign_name).text = getString(signResName)
        findViewById<TextView>(R.id.date_range).text = sign.dateRange
        findViewById<TextView>(R.id.current_date).append(sign.currentDate)
        findViewById<TextView>(R.id.prediction).append(sign.prediction)
        findViewById<TextView>(R.id.compatibility).append(sign.compatibility)
        findViewById<TextView>(R.id.mood).append(sign.mood)
        findViewById<TextView>(R.id.color).append(sign.color)
        findViewById<TextView>(R.id.lucky_number).append(sign.luckyNumber.toString())
        findViewById<TextView>(R.id.lucky_time).append(sign.luckyTime)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}