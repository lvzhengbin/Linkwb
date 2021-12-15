package com.ice.wb.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
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
    private var mRadius = 0f

    private var mValueAnimator: ValueAnimator? = null
    private var progress = 0
    private var mIsWave = false

    companion object{
        const val ANIMATION_TIME = 2000L
    }

    init {
        mPaint.isAntiAlias = true
        mPaint.color = getContext().resources.getColor(R.color.theme_default_color)
        mPaint.strokeWidth = SystemUtil.dip2pxWithFloat(getContext(), 0.8f)
        mPaint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cx = width / 2
        val cy = height / 2
        val maxRadius = Math.min(cx, cy)

        if (progress * maxRadius > 0) {
            mPaint.alpha = 255 - ((progress - 30) * 255 / (100 - 30))
            mRadius = (progress * maxRadius / 100).toFloat()
            canvas.drawCircle(cx.toFloat(), cy.toFloat(), mRadius, mPaint)
        }
        if(mIsWave){
            invalidate()
        }
    }

    fun playAnimation() {
        mIsWave = true
        invalidate()
        if (mValueAnimator == null){
            mValueAnimator = ValueAnimator.ofInt(30, 100).apply {
                duration = ANIMATION_TIME
                interpolator = DecelerateInterpolator()
                repeatMode = ValueAnimator.RESTART
                repeatCount = ValueAnimator.INFINITE
                addUpdateListener{ animation ->
                    progress = animation.animatedValue as Int
                    //invalidate()
                }
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        visibility = VISIBLE
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        visibility = GONE
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        visibility = GONE
                    }

                    override fun onAnimationRepeat(animation: Animator) {}
                })
                start()
            }
        }else{
            mValueAnimator?.let {
                if (it.isPaused){
                    it.resume()
                }
            }
        }
    }

    fun pauseAnimation(){
        mIsWave = false
        if (mValueAnimator?.isRunning == true){
            mValueAnimator?.pause()
        }
    }

    fun cancelAnimation(){
        mIsWave = false
        mValueAnimator?.let {
            it.cancel()
        }
    }

    fun isAnimating(): Boolean{
        return mIsWave
    }


}