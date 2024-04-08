package com.texttranslate.voiceimage

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.texttranslate.voiceimage.databinding.ActivityExitBinding

class ExitActivity : AppCompatActivity() {
    private var binding: ActivityExitBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExitBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.getRoot())
        binding!!.rateus.setOnClickListener { v: View? ->
            val uri = Uri.parse("market://details?id=$packageName")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY or
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
            try {
                startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                    )
                )
            }
        }
        binding!!.yes.setOnClickListener { v: View? ->
            finishAffinity()
            System.exit(0)
        }
        binding!!.no.setOnClickListener { v: View? -> super@ExitActivity.onBackPressed() }
    }
}