package com.example.sportsmates.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.bean.StepBean
import com.example.sportsmates.R
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


fun Fragment.replaceFragment(
    fragment: Fragment,
    tag: String? = null,
    @IdRes containerViewId: Int
) {
    val ft = activity?.supportFragmentManager
        ?.beginTransaction()
        ?.replace(containerViewId, fragment, tag)
        ?.commit()
}

fun Fragment.pushFragment(
    fragment: Fragment,
    tag: String? = null,
    @IdRes containerViewId: Int
) {
    val ft = activity?.supportFragmentManager
        ?.beginTransaction()
        ?.replace(containerViewId, fragment, tag)
        ?.addToBackStack(fragment.javaClass.name)
        ?.commit()

}


fun Fragment.handleStoragePermission(
    onSelectImageCallback: (() -> Unit), PERMISSION_CODE: Int
) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(
                permission, PERMISSION_CODE
            )
        } else {
            onSelectImageCallback()

        }
    }
}

fun Fragment.selectImage(PICK_IMAGE_REQUEST: Int) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_PICK
    startActivityForResult(
        Intent.createChooser(intent, "Select Image from here...."),
        PICK_IMAGE_REQUEST
    )

}

fun Fragment.setStepper(state1: Int, state2: Int, state3: Int, stepView: HorizontalStepView) {
    val stepsList = ArrayList<StepBean>()
    val stepBean0 = StepBean("1", state1)
    val stepBean1 = StepBean("2", state2)
    val stepBean2 = StepBean("3", state3)
    stepsList.add(stepBean0)
    stepsList.add(stepBean1)
    stepsList.add(stepBean2)
    stepView.setStepViewTexts(stepsList)
        .setStepsViewIndicatorCompletedLineColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.main_green
            )
        )
        .setStepsViewIndicatorUnCompletedLineColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.grey
            )
        )
        .setStepsViewIndicatorCompleteIcon(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_green_check
            )
        )
        .setStepsViewIndicatorDefaultIcon(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic__unchecked
            )
        )

}


fun Fragment.openTopActivity(activity: FragmentActivity?, targetActivity: Activity) {
    val targetActivity = Intent(
        activity,
        targetActivity::class.java
    )
    targetActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(targetActivity)
}

fun String.convertToAgoFormat(): CharSequence? {
    Log.v("time",DateUtils.getRelativeTimeSpanString(Instant.parse(this).toEpochMilli()).toString())
    return DateUtils.getRelativeTimeSpanString(Instant.parse(this).toEpochMilli())
}

val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun Fragment.displayErrorToast(title: String?, message: String) {
    MotionToast.darkToast(
        activity!!,
        title,
        message,
        MotionToast.TOAST_ERROR,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(activity!!, R.font.helvetica_regular)
    )
}
    fun Fragment.displayWarningToast(title: String?, message: String) {
        MotionToast.darkToast(
            activity!!,
            title,
            message,
            MotionToast.TOAST_WARNING,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(activity!!, R.font.helvetica_regular)
        )
    }
fun Fragment.displayInfoToast(title: String?, message: String) {
    MotionToast.darkToast(
        activity!!,
        title,
        message,
        MotionToast.TOAST_INFO,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(activity!!, R.font.helvetica_regular)
    )
}
fun Activity.changeStatusBarColor(color:Int){
    val window: Window =this.window
    window.statusBarColor= ContextCompat.getColor(this,color)
}

fun stopShimmer(shimmer:ShimmerFrameLayout){
    shimmer.stopShimmer()
    shimmer.visibility=View.GONE
}
fun Activity.getCurrentTime():String{
    val sdf = SimpleDateFormat("hh.mm aa ")
    val currentTime = sdf.format(Date())
    return currentTime
}
fun ViewModel.getCurrentUserID():String{
    val currentUserId = FirebaseAuth.getInstance().currentUser.uid
    return currentUserId
}

