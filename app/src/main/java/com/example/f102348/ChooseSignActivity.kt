package com.example.f102348

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChooseSignActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_choose_sign_screen)
        setButtonsListeners()
    }

    private fun setButtonsListeners() {
        findViewById<Button>(R.id.aries_button).setOnClickListener {
            goToNextActivity(R.string.aries, R.drawable.aries_colorful)
        }
        findViewById<Button>(R.id.taurus_button).setOnClickListener {
            goToNextActivity(R.string.taurus, R.drawable.taurus_colorful)
        }
        findViewById<Button>(R.id.gemini_button).setOnClickListener {
            goToNextActivity(R.string.gemini, R.drawable.gemini_colorful)
        }
        findViewById<Button>(R.id.cancer_button).setOnClickListener {
            goToNextActivity(R.string.cancer, R.drawable.cancer_colorful)
        }
        findViewById<Button>(R.id.leo_button).setOnClickListener {
            goToNextActivity(R.string.leo, R.drawable.leo_colorful)
        }
        findViewById<Button>(R.id.virgo_button).setOnClickListener {
            goToNextActivity(R.string.virgo, R.drawable.virgo_colorful)
        }
        findViewById<Button>(R.id.libra_button).setOnClickListener {
            goToNextActivity(R.string.libra, R.drawable.libra_colorful)
        }
        findViewById<Button>(R.id.scorpius_button).setOnClickListener {
            goToNextActivity(R.string.scorpius, R.drawable.scorpius_colorful)
        }
        findViewById<Button>(R.id.sagittarius_button).setOnClickListener {
            goToNextActivity(R.string.sagittarius, R.drawable.sagittarius_colorful)
        }
        findViewById<Button>(R.id.capricornus_button).setOnClickListener {
            goToNextActivity(R.string.capricorn, R.drawable.capricornius_colorful)
        }
        findViewById<Button>(R.id.aquarius_button).setOnClickListener {
            goToNextActivity(R.string.aquarius, R.drawable.aquarius_colorful)
        }
        findViewById<Button>(R.id.pisces_button).setOnClickListener {
            goToNextActivity(R.string.pisces, R.drawable.pisces_colorful)
        }
    }

    private fun goToNextActivity(signRes: Int, signImageRes: Int) =
        startActivity(
            Intent(this, MainActivity::class.java).putExtra("singRes", signRes)
                .putExtra("signImage", signImageRes)
        )


    override fun onDestroy() {
        super.onDestroy()
        supportActionBar?.show()
    }
}