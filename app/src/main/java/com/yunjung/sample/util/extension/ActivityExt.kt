package com.yunjung.sample.util.extension

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.yunjung.sample.databinding.LayoutToastBinding

fun Activity.setStatusBarTransparent(container: View){
    window.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
}

fun Activity.showToast(message: String){
    val binding = LayoutToastBinding.inflate(LayoutInflater.from(this))
    val rootView = binding.root
    binding.message.text = message
    Toast(this).apply {
        view = rootView
        duration = Toast.LENGTH_SHORT
        setGravity(Gravity.FILL_HORIZONTAL, 0, 10000)
        show()
    }
}