package com.yunjung.sample.util.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import com.yunjung.sample.R
import com.yunjung.sample.databinding.LayoutLoadingBinding
import com.yunjung.sample.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Loading @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutLoadingBinding.inflate(LayoutInflater.from(context), this, true)

    private var hasLoading: Boolean = false
    private val visibleLoading: Animation =
        AnimationUtils.loadAnimation(
            context,
            R.anim.fade_in
        ).apply {
            duration = 80
            setAnimationListener(object: Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                    Logger.d("loading show")
                    binding.root.visibility = View.VISIBLE
                }
                override fun onAnimationEnd(p0: Animation?) { }
                override fun onAnimationRepeat(p0: Animation?) { }
            })
        }

    private val goneLoading: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.fade_out
        ).apply {
            duration = 80
            setAnimationListener(object: Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) { }
                override fun onAnimationEnd(p0: Animation?) {
                    Logger.d("loading hide")
                    binding.container.visibility = View.GONE
                }
                override fun onAnimationRepeat(p0: Animation?) { }
            })
        }
    }

    fun show(){
        if(hasLoading) return

        hasLoading = true
        CoroutineScope(Dispatchers.Main).launch {
            binding.root.visibility = View.VISIBLE
            binding.root.startAnimation(visibleLoading)
            binding.loading.playAnimation()
        }
    }

    fun hide(){
        if(!hasLoading) return

        hasLoading = false
        CoroutineScope(Dispatchers.Main).launch {
            binding.root.startAnimation(goneLoading)
            binding.loading.pauseAnimation()
        }
    }
}