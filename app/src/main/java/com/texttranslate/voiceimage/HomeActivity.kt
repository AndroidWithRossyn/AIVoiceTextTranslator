package com.texttranslate.voiceimage

import android.Manifest
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.texttranslate.voiceimage.base.PermissionHelperActivity
import com.texttranslate.voiceimage.base.PermitConstant
import com.texttranslate.voiceimage.databinding.ActivityHomeBinding
import com.texttranslate.voiceimage.dialogs.RateDialog
import com.texttranslate.voiceimage.interfaces.PermissionResult
import com.texttranslate.voiceimage.utils.Global.isLatestVersion
import com.texttranslate.voiceimage.utils.Utils

class HomeActivity : PermissionHelperActivity(), View.OnClickListener {
    private var binding: ActivityHomeBinding? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.getRoot())
        mCheckPermission()
        binding!!.mTxtTitle.paint.setShader(
            Utils.getShader(
                binding!!.mTxtTitle
            )
        )

        binding!!.mRLShare.setOnClickListener(this)
        binding!!.mRLRateUs.setOnClickListener(this)
        binding!!.mRLPrivacy.setOnClickListener(this)

        binding!!.mIVVoice.setOnClickListener(this)
        binding!!.mIVText.setOnClickListener(this)
        binding!!.mIVCamera.setOnClickListener(this)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEnabled) {
                    isEnabled = false
                    showExitDialog(this@HomeActivity)
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun mCheckPermission() {
        val permissions: Array<String> = if (isLatestVersion) {
            arrayOf(
                PermitConstant.Manifest_CAMERA,
                PermitConstant.READ_MEDIA_IMAGES,
                PermitConstant.READ_MEDIA_VIDEOS,
                PermitConstant.POST_NOTIFICATIONS
            )
        } else {
            arrayOf(
                PermitConstant.Manifest_CAMERA,
                PermitConstant.Manifest_READ_EXTERNAL_STORAGE,
                PermitConstant.Manifest_WRITE_EXTERNAL_STORAGE
            )
        }
        if (!isPermissionsGranted(this@HomeActivity, permissions)) {
            askCompactPermissions(permissions, object : PermissionResult {
                override fun permissionGranted() {}
                override fun permissionDenied() {
//                    Utils.showToast(HomeActivity.this, "Permission Denied..!");
                }

                override fun permissionForeverDenied() {
//                    Utils.showToast(HomeActivity.this, "Permission Forever Denied..!");
                }
            })
        }
    }

    fun showExitDialog(context: Context) {
        val dialogLogOut = Dialog(context)
        dialogLogOut.setContentView(R.layout.activity_exit)
        dialogLogOut.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogLogOut.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogLogOut.window?.setGravity(Gravity.BOTTOM)
        dialogLogOut.setCanceledOnTouchOutside(true)
        val rateus = dialogLogOut.findViewById<TextView>(R.id.rateus)
        val btnyes = dialogLogOut.findViewById<TextView>(R.id.yes)
        val btnno = dialogLogOut.findViewById<TextView>(R.id.no)

        // click listener for No
        rateus.setOnClickListener {
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
            dialogLogOut.dismiss()
        }

        // click listener for Yes
        btnyes.setOnClickListener {
            dialogLogOut.dismiss()
            finish()
        }
        btnno.setOnClickListener {
            dialogLogOut.dismiss()
        }
        dialogLogOut.show()
    }

    override fun onClick(v: View) {
         if (v === binding!!.mIVVoice) {
            mCheckVoicePermission()
        } else if (v === binding!!.mIVText) {
            startActivity(Intent(this@HomeActivity, TextActivity::class.java))
        } else if (v === binding!!.mIVCamera) {
            val isAvailable = Utils.isGooglePlayServicesAvailable(applicationContext)
            if (isAvailable) {
                CheckPermission()
            } else {
                Toast.makeText(
                    this@HomeActivity,
                    "Google Play Services is not available on Your device",
                    Toast.LENGTH_SHORT
                ).show()
            }

        } else if (v === binding!!.mRLShare) {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.setType("text/plain")
                shareIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    getResources().getString(R.string.app_name)
                )
                val shareMessage =
                    ("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID).toString() + "\n\n"
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (v === binding!!.mRLRateUs) {
            RateDialog(object : RateDialog.OnClickListener {
                override fun OnRate() {
                    try {
                        val sb = StringBuilder()
                        sb.append("market://details?id=")
                        sb.append(packageName)
                        startActivity(
                            Intent(
                                "android.intent.action.VIEW",
                                Uri.parse(sb.toString())
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        val rateIntent =
                            rateIntentForUrl("https://play.google.com/store/apps/details")
                        startActivity(rateIntent)
                    }
                }

                override fun OnCancel() {}
            }).show(supportFragmentManager, "")
        } else if (v === binding!!.mRLPrivacy) {
            val browserIntent =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://sites.google.com/view/all-languages-translators")
                )
            startActivity(browserIntent)
        }
    }

    private fun rateIntentForUrl(url: String): Intent {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, packageName)))
        var flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        flags = if (Build.VERSION.SDK_INT >= 21) {
            flags or Intent.FLAG_ACTIVITY_NEW_DOCUMENT
        } else {
            flags or Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
        }
        intent.addFlags(flags)
        return intent
    }

    private fun mCheckVoicePermission() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                startActivity(Intent(this@HomeActivity, VoiceActivity::class.java))
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    this@HomeActivity,
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


    private fun CheckPermission() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                startActivity(Intent(this@HomeActivity, CameraActivity::class.java))
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    this@HomeActivity,
                    "Permission Denied\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        if (Utils.isLatestVersion) {
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