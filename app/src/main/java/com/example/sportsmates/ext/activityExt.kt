package com.example.sportsmates.ext

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.sportsmates.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import www.sanju.motiontoast.MotionToast


fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    tag: String? = null,
    state: Bundle? = null,
    @IdRes containerViewId: Int
) {
    if (state == null) {
        this.supportFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment, tag)
            .commit()
    }
}



fun AppCompatActivity.openTopActivity(
    context: Context,
    targetActivity: Activity,
    bundle: Bundle? = null
) {
    val targetActivity = Intent(
        context,
        targetActivity::class.java
    )
    targetActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(targetActivity, bundle)
}

fun Activity.isNotchDevice(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P &&
            window.decorView.rootWindowInsets.displayCutout != null
}

fun Activity.setFullScreenWithTransparentStatusBar(
    @ColorRes color: Int = android.R.color.transparent,
    lightStatusBar: Boolean = true
) {

    this.window?.decorView?.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    this.window?.statusBarColor = ContextCompat.getColor(this, color)
}

fun Activity.selectImage(PICK_IMAGE_REQUEST: Int) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_PICK
    startActivityForResult(
        Intent.createChooser(intent, "Select Image from here...."),
        PICK_IMAGE_REQUEST
    )

}

fun Activity.handleStoragePermission(
    onSelectImageCallback: (() -> Unit), PERMISSION_CODE: Int
) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(
                permission, PERMISSION_CODE
            )
        } else {
            onSelectImageCallback()

        }
    }
}

fun Activity.displayErrorToast(title: String?, message: String) {
    MotionToast.darkToast(
        this,
        title,
        message,
        MotionToast.TOAST_ERROR,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.helvetica_regular)
    )
}

fun Activity.displayWarningToast(title: String?, message: String) {
    MotionToast.darkToast(
        this,
        title,
        message,
        MotionToast.TOAST_WARNING,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.helvetica_regular)
    )
}

fun Activity.displayInfoToast(title: String?, message: String) {
    MotionToast.darkToast(
        this,
        title,
        message,
        MotionToast.TOAST_INFO,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.helvetica_regular)
    )
}

fun Activity.displaySuccessToast(title: String?, message: String) {
    MotionToast.darkToast(
        this,
        title,
        message,
        MotionToast.TOAST_SUCCESS,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.helvetica_regular)
    )
}

fun Activity.changeStatusBarColor(color: Int) {
    val window: Window = this.window
    window.statusBarColor = ContextCompat.getColor(this, color)
}

fun Activity.withTransitionAnimation(
    targetImage: ImageView
): Bundle? {
    return ActivityOptionsCompat.makeSceneTransitionAnimation(this, targetImage, "img")
        .toBundle()
}

fun <T> AppCompatActivity.stateCollector(flow: Flow<T>, collect:FlowCollector<T>){
    this.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
            flow.collect(collect)
        }
    }
}

