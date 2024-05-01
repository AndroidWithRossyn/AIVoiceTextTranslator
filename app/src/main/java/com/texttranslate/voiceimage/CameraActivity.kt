package com.texttranslate.voiceimage

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.with
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import com.texttranslate.voiceimage.base.PermissionHelperActivity
import com.texttranslate.voiceimage.base.PermitConstant
import com.texttranslate.voiceimage.databinding.ActivityCameraBinding
import com.texttranslate.voiceimage.interfaces.PermissionResult
import com.texttranslate.voiceimage.utils.Global.isLatestVersion
import com.texttranslate.voiceimage.utils.Utils
import java.io.IOException

class CameraActivity : PermissionHelperActivity(), View.OnClickListener {
    var cameraSource: CameraSource? = null
    var mText: String? = null
    private var binding: ActivityCameraBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.getRoot())
        val textRecognizer = TextRecognizer.Builder(this).build()
//        if (!textRecognizer.isOperational) {
//            Log.w("MainActivity", "Detector dependencies are not yet available")
//        } else {
        cameraSource = CameraSource.Builder(this, textRecognizer)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedPreviewSize(1280, 1024)
            .setRequestedFps(2.0f)
            .setAutoFocusEnabled(true)
            .build()
        binding!!.surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            this@CameraActivity,
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this@CameraActivity, arrayOf(Manifest.permission.CAMERA),
                            22
                        )
                        return
                    }
                    cameraSource!!.start(binding!!.surfaceView.holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource!!.stop()
            }
        })
        textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
            override fun release() {}
            override fun receiveDetections(detections: Detections<TextBlock>) {
                val items = detections.detectedItems
                if (items.size() != 0) {
                    val stringBuilder = StringBuilder()
                    for (i in 0 until items.size()) {
                        val item = items.valueAt(i)
                        stringBuilder.append(item.value)
                        stringBuilder.append("\n")
                        mText = stringBuilder.toString()
                    }
                }
            }
        })

        binding!!.mIVCamera.setOnClickListener(this)
        binding!!.mIVGallery.setOnClickListener(this)
        binding!!.mIVBack.setOnClickListener(this)
    }

    private var sendText: String? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onClick(v: View) {
        if (v === binding!!.mIVBack) {
            super@CameraActivity.finish()
        } else if (v === binding!!.mIVGallery) {
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
            askCompactPermissions(permissions, object : PermissionResult {
                override fun permissionGranted() {
                    Utils.displayProgress(this@CameraActivity)
                    with(this@CameraActivity).crop().galleryOnly()
                        .galleryMimeTypes(arrayOf("image/png", "image/jpg", "image/jpeg"))
                        .maxResultSize(1080, 1920).start(102)
                }

                override fun permissionDenied() {
                    Utils.showToast(this@CameraActivity, "Permission Denied..!")
                }

                override fun permissionForeverDenied() {
                    Utils.showToast(this@CameraActivity, "Permission Forever Denied..!")
                }
            })
        } else if (v === binding!!.mIVCamera) {
            sendText = mText
            if (!Utils.isEmptyStr(sendText)) {
                val intent = Intent(this@CameraActivity, TextActivity::class.java)
                intent.putExtra("text", sendText)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "There is no Data..!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            22 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }
                    try {
                        cameraSource!!.start(binding!!.surfaceView.holder)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == -1) {
            val uri = data?.data
            val mPreviewIv = ImageView(this@CameraActivity)
            mPreviewIv.setImageURI(uri)
            //            mPath = uri.getPath();
            val bitmapDrawable = mPreviewIv.getDrawable() as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            val recognizer = TextRecognizer.Builder(applicationContext).build()
            val frame = Frame.Builder().setBitmap(bitmap).build()
            val items = recognizer.detect(frame)
            val sb = StringBuilder()
            //get text from sb until there is no text
            for (i in 0 until items.size()) {
                val myItem = items.valueAt(i)
                sb.append(myItem.value)
                sb.append("\n")
            }
            Utils.dismissProgress()
            sendText = sb.toString()
            if (!Utils.isEmptyStr(sendText)) {
                val intent = Intent(this@CameraActivity, TextActivity::class.java)
                intent.putExtra("text", sendText)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "There is no Data..!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Utils.dismissProgress()
        }
    }
}