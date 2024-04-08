package com.texttranslate.voiceimage

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.texttranslate.voiceimage.base.PermissionHelperActivity
import com.texttranslate.voiceimage.base.PermitConstant
import com.texttranslate.voiceimage.databinding.ActivityStartBinding
import com.texttranslate.voiceimage.utils.Utils

class StartActivity : PermissionHelperActivity(), View.OnClickListener {
    private var binding: ActivityStartBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.getRoot())
        binding!!.mIVBack.setOnClickListener(this)
        binding!!.mIVVoice.setOnClickListener(this)
        binding!!.mIVText.setOnClickListener(this)
        binding!!.mIVCamera.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onClick(v: View) {
        if (v === binding!!.mIVBack) {
            super@StartActivity.finish()
        } else if (v === binding!!.mIVVoice) {
            mCheckVoicePermission()
        } else if (v === binding!!.mIVText) {
            startActivity(Intent(this@StartActivity, TextActivity::class.java))
        } else if (v === binding!!.mIVCamera) {
            CheckPermission()
        }
    }

    private fun mCheckVoicePermission() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                startActivity(Intent(this@StartActivity, VoiceActivity::class.java))
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    this@StartActivity,
                    "Permission Denied\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        TedPermission.create()
            .setPermissionListener(permissionlistener)
            .setRationaleTitle(R.string.rationale_title)
            .setRationaleMessage(R.string.rationale_message_voice)
            .setDeniedTitle("Permission denied")
            .setDeniedMessage(
                "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]"
            )
            .setGotoSettingButtonText("Settings")
            .setPermissions(Manifest.permission.RECORD_AUDIO)
            .check()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun CheckPermission() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                startActivity(Intent(this@StartActivity, CameraActivity::class.java))
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    this@StartActivity,
                    "Permission Denied\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        if (Utils.isLatestVersion()) {
            TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setRationaleTitle(R.string.rationale_title)
                .setRationaleMessage(R.string.rationale_message)
                .setDeniedTitle("Permission denied")
                .setDeniedMessage(
                    "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]"
                )
                .setGotoSettingButtonText("Settings")
                .setPermissions(PermitConstant.Manifest_CAMERA, PermitConstant.READ_MEDIA_IMAGES)
                .check()
        } else {
            TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setRationaleTitle(R.string.rationale_title)
                .setRationaleMessage(R.string.rationale_message)
                .setDeniedTitle("Permission denied")
                .setDeniedMessage(
                    "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]"
                )
                .setGotoSettingButtonText("Settings")
                .setPermissions(
                    Manifest.permission.CAMERA,
                    PermitConstant.Manifest_READ_EXTERNAL_STORAGE,
                    PermitConstant.Manifest_WRITE_EXTERNAL_STORAGE
                )
                .check()
        }
    }
}