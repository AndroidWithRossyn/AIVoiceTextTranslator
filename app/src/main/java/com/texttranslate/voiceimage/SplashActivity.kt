package com.texttranslate.voiceimage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.texttranslate.voiceimage.databinding.ActivitySplashBinding
import com.texttranslate.voiceimage.utils.Utils

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    var splashActivity: SplashActivity? = null
    var binding: ActivitySplashBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.getRoot())
        splashActivity = this
        Handler().postDelayed({
            if (Utils.isNetworkAvailable(this@SplashActivity)) {
                startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
                finish()
            } else {
                Toast.makeText(this@SplashActivity, "Turn On Internet..!", Toast.LENGTH_SHORT)
                    .show()
            }
        }, 2000)
    }
}