package com.ice.wb.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.*
import com.ice.common.utils.SystemUtil
import com.ice.wb.R


/**
 * Desc:  </p>
 * Author: lvzhengbin@lizhi.fm
 * Time: 2021/12/8.
 */
class MatchWaveView @JvmOverloads constructor(context: Context,
                                              attrs: AttributeSet? = null,
                                              defStyleAttr: Int = 0): View(context, attrs, defStyleAttr){

    private var mPaint: Paint = Paint()
    private lateinit var animatorSet: AnimatorSet

    companion object{
        const val ANIMATION_TIME = 2000L
    }

    init {
        mPaint.isAntiAlias = true
        mPaint.color = getContext().resources.getColor(R.color.theme_default_color)
        mPaint.strokeWidth = SystemUtil.dip2pxWithFloat(getContext(), 0.8f)
        mPaint.style = Paint.Style.STROKE
        initAnimation()
    }

    private fun initAnimation(){
        val alphaAnimator = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f).apply {
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
        }
        val scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", 0.2f, 1f).apply {
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
        }
        val scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", 0.2f, 1f).apply {
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
        }
        animatorSet = AnimatorSet().apply {
            playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator)
            duration = ANIMATION_TIME
            interpolator = DecelerateInterpolator()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cx = width / 2
        val cy = height / 2
        val maxRadius = Math.min(cx, cy).toFloat()
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), maxRadius, mPaint)
    }

    fun playAnimation() {
        if (animatorSet.isPaused){
            animatorSet.resume()
        }else{
            animatorSet.start()
        }
    }

    fun pauseAnimation(){
        if (animatorSet.isRunning){
            animatorSet.pause()
        }
    }

    fun cancelAnimation(){
        animatorSet.cancel()
    }

    fun isAnimating(): Boolean{
        return if (animatorSet.isStarted.not()){
            false
        }else{
            animatorSet.isPaused.not()
        }
    }


}