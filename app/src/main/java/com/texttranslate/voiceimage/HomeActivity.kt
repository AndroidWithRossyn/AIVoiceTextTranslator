package com.texttranslate.voiceimage

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
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
        binding!!.mRLStarted.setOnClickListener(this)
        binding!!.mRLShare.setOnClickListener(this)
        binding!!.mRLRateUs.setOnClickListener(this)
        binding!!.mRLPrivacy.setOnClickListener(this)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEnabled) {
                    isEnabled = false
                    startActivity(Intent(this@HomeActivity, ExitActivity::class.java))
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

    override fun onClick(v: View) {
        if (v === binding!!.mRLStarted) {
            startActivity(Intent(this@HomeActivity, StartActivity::class.java))
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
                Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/OmaPrakash"))
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
}