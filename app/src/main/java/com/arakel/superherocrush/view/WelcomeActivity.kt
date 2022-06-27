package com.arakel.superherocrush.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.arakel.superherocrush.R
import android.content.Intent

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val action = supportActionBar
        action?.hide()

        try {
            Handler().postDelayed({
                startActivity(Intent
                    (this@WelcomeActivity,
                    PlayActivity::class.java))
            }, 5000)
        }

        catch (e:Exception){
            e.printStackTrace()
        }

    }
}