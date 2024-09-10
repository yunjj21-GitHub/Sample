package com.yunjung.sample.util.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ScrollView
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.yunjung.sample.R
import com.yunjung.sample.databinding.LayoutAppbarBinding
import com.yunjung.sample.util.SmplLogger
import com.yunjung.sample.util.extension.getStatusBarHeight

class SmplAppBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutAppbarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        SmplLogger.d("SmplAppBar set status bar height to ${context.getStatusBarHeight()}")
        binding.statusBarHeight = context.getStatusBarHeight()
        binding.container.visibility = View.GONE
        changeTheme(Theme.TRANSPARENT)
    }

    fun setScrollView(scrollView: ScrollView){
        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollViewY = scrollView.scrollY
            val smplAppBarH = this.height
            if(scrollViewY < smplAppBarH) changeTheme(Theme.TRANSPARENT)
            else changeTheme(Theme.WHITE)
        }
    }

    private fun changeTheme(theme: Theme){
        when(theme){
            Theme.TRANSPARENT -> {
                binding.container.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
                binding.appName.setTextColor(ContextCompat.getColor(context, R.color.white))
                binding.alramBtn.setBackgroundResource(R.drawable.ic_alram_white)
                binding.menuBtn.setBackgroundResource(R.drawable.ic_menu_white)
            }
            Theme.WHITE -> {
                binding.container.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                binding.appName.setTextColor(ContextCompat.getColor(context, R.color.black))
                binding.alramBtn.setBackgroundResource(R.drawable.ic_alram_black)
                binding.menuBtn.setBackgroundResource(R.drawable.ic_menu_black)
            }
        }
    }

    fun show(){
        binding.container.visibility = View.VISIBLE
    }

    fun hide(){
        binding.container.visibility = View.GONE
    }

    enum class Theme{
        TRANSPARENT, WHITE
    }
}