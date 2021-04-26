package com.example.sportsmates.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    tag: String? = null,
    @IdRes containerViewId: Int
) {
    val ft = supportFragmentManager
        .beginTransaction()
        .replace(containerViewId, fragment, tag)
        .commit()
}

fun AppCompatActivity.openTopActivity(context: Context, targetActivity: Activity) {
    val targetActivity = Intent(
        context,
        targetActivity::class.java
    )
    targetActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(targetActivity)
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

fun Activity.changeStatusBarColor(color:Int){
    val window: Window =this.window
    window.statusBarColor= ContextCompat.getColor(this,color)
}

fun Activity.openActivityWithTransitionAnimation(
    targetImage: ImageView
) : Bundle?{
    return ActivityOptionsCompat.makeSceneTransitionAnimation(this, targetImage, "img")
        .toBundle()
}
