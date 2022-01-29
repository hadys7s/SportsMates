package com.example.sportsmates.ext

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.bean.StepBean
import com.example.sportsmates.R
import www.sanju.motiontoast.MotionToast

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
            val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
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


fun Fragment.openTopActivity(activity: FragmentActivity?, targetActivity: Activity,bundle: Bundle?=null) {
    val targetActivity = Intent(
        activity,
        targetActivity::class.java
    )
    targetActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(targetActivity,bundle)
}

fun Fragment.displayErrorToast(title: String?, message: String) {
    MotionToast.darkToast(
        requireActivity(),
        title,
        message,
        MotionToast.TOAST_ERROR,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
    )
}
fun Fragment.displayWarningToast(title: String?, message: String) {
    MotionToast.darkToast(
        requireActivity(),
        title,
        message,
        MotionToast.TOAST_WARNING,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
    )
}
fun Fragment.displayInfoToast(title: String?, message: String) {
    MotionToast.darkToast(
        requireActivity(),
        title,
        message,
        MotionToast.TOAST_INFO,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
    )
}

fun Fragment.withTransitionAnimation(
    targetImage: ImageView
): Bundle? {
    return ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, targetImage, "img")
        .toBundle()
}
fun Fragment.openActivity(targetActivity: Activity,bundle: Bundle?=null){
    val intent=Intent(requireActivity(),targetActivity::class.java)
    startActivity(intent,bundle)
}