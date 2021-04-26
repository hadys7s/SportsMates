package com.example.sportsmates.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import androidx.lifecycle.ViewModel
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.bean.StepBean
import com.example.sportsmates.R
import com.example.sportsmates.chat.model.MessageModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.auth.FirebaseAuth
import www.sanju.motiontoast.MotionToast
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList


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

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && lightStatusBar) {
        this.window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        this.window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    this.window?.statusBarColor = ContextCompat.getColor(this, color)
}



fun String.convertToAgoFormat(): CharSequence? {
    Log.v("time",DateUtils.getRelativeTimeSpanString(Instant.parse(this).toEpochMilli()).toString())
    return DateUtils.getRelativeTimeSpanString(Instant.parse(this).toEpochMilli())
}

val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun Activity.changeStatusBarColor(color:Int){
    val window: Window =this.window
    window.statusBarColor= ContextCompat.getColor(this,color)
}

fun stopShimmer(shimmer:ShimmerFrameLayout){
    shimmer.stopShimmer()
    shimmer.visibility=View.GONE
}
fun ViewModel.getCurrentUserID(): String = FirebaseAuth.getInstance().currentUser.uid
fun ViewModel.getCurrentTime(): String = SimpleDateFormat("hh.mm aa ").format(Date())

fun Context.getEncryptedSharedPreferences(fileName: String): SharedPreferences {
    val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

    return EncryptedSharedPreferences
        .create(
            fileName,
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
}
fun Activity.openActivityWithTransitionAnimation(
    targetImage: ImageView
) :Bundle?{
       return ActivityOptionsCompat.makeSceneTransitionAnimation(this, targetImage, "img")
            .toBundle()
}