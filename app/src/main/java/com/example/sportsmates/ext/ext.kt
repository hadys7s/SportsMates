package com.example.sportsmates.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.bean.StepBean
import com.example.sportsmates.R


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
                permission
                , PERMISSION_CODE
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
                R.color.orange
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
                R.drawable.step_view_complete
            )
        )
        .setStepsViewIndicatorDefaultIcon(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.step_view_uncomplete
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
