package com.yunjung.sample.util.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.yunjung.sample.R
import com.yunjung.sample.databinding.LayoutAppbarBinding
import com.yunjung.sample.util.extension.getStatusBarHeight

class SmplAppBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutAppbarBinding.inflate(LayoutInflater.from(context), this, true)
    // MainAppBar (Activity 에 부착된 AppBar)
    private val mainAppBar: SmplAppBar = this.apply { setTheme(Type.MAIN) }
    // ChdAppBar (Fragment 에 부착된 AppBar)
    private val chdAppBar: SmplAppBar by lazy {
        SmplAppBar(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                endToEnd = LayoutParams.PARENT_ID
                startToStart = LayoutParams.PARENT_ID
                topToTop = LayoutParams.PARENT_ID
            }
            setTheme(Type.CHILD)
        }
    }
    // MainAppBar Visibility Animation
    private val fadeInAnim: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.fade_in
        ).apply {
            duration = 340
        }
    }
    private val fadeOutAnim: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.fade_out
        ).apply {
            duration = 340
        }
    }

    init {
        binding.statusBarHeight = context.getStatusBarHeight()
    }

    /*
    * SmplAppBar(MainAppBar, ChdAppBar 보임 설정)
    * */
    fun show(mainAppBarScrollView: ScrollView, chdAppBarLayout: ConstraintLayout){
        mainAppBar.binding.container.visibility = View.VISIBLE
        mainAppBarScrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollViewY = mainAppBarScrollView.scrollY
            val smplAppBarH = this.height
            if(scrollViewY < smplAppBarH) isMainAppBarVisible(false)
            else isMainAppBarVisible(true)
        }
        chdAppBar.binding.container.visibility = View.VISIBLE

        chdAppBarLayout.addView(chdAppBar)
    }

    /*
    * SmplAppBar(MainAppBar, ChdAppBar 숨김 설정)
    * */
    fun hide(chdAppBarLayout: ConstraintLayout? = null){
        mainAppBar.binding.container.visibility = View.GONE
        chdAppBar.binding.container.visibility = View.GONE

        if(chdAppBarLayout == null) return
        chdAppBarLayout.removeView(chdAppBar)
    }

    /*
    * MainAppBar 보임 여부 설정
    * */
    private fun isMainAppBarVisible(isVisible: Boolean){
        if(isVisible) {
            binding.appBar.visibility = View.VISIBLE
            binding.appBar.animation = fadeInAnim
        }else{
            binding.appBar.visibility = View.INVISIBLE
            binding.appBar.animation = fadeOutAnim
        }
    }

    private fun setTheme(type: Type){
        if(type == Type.MAIN){
            // mainAppBar.binding
            binding.run{
                appBar.visibility = View.INVISIBLE
                appBar.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                appName.setTextColor(ContextCompat.getColor(context, R.color.black))
                alramBtn.setBackgroundResource(R.drawable.ic_alram_black)
                menuBtn.setBackgroundResource(R.drawable.ic_menu_black)
            }
        }else{
            // chdAppBar.binding
            binding.run {
                appBar.visibility = View.VISIBLE
                appBar.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
                appName.setTextColor(ContextCompat.getColor(context, R.color.white))
                alramBtn.setBackgroundResource(R.drawable.ic_alram_white)
                menuBtn.setBackgroundResource(R.drawable.ic_menu_white)
            }
        }
    }

    enum class Type{
        MAIN, CHILD
    }
}