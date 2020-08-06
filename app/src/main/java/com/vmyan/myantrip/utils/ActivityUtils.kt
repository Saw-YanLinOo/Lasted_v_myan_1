package com.vmyan.myantrip.utils

import android.app.Activity
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


/**
 * Can show [Toast] from every [Activity].
 */
fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(activity, message, duration).show()
}

/**
 * Returns Color from resource.
 * @param id Color Resource ID
 */
fun Fragment.getColorRes(@ColorRes id: Int) = activity?.let { ContextCompat.getColor(it, id) }



