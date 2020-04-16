package com.ice.wb.fragment

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ice.common.fragment.BaseLazyFragment
import com.ice.wb.R
import com.ice.wb.widget.SquareProgressBar
import com.ice.wb.widget.SquareProgressViewKt

/**
 * Desc:
 * Created by lvzhengbin
 * Time: 2020/4/9
 */
class HomeTabFragmentKt : BaseLazyFragment(){

    val TAG : String = "HomeTabFragmentKt"
    lateinit var mTextview : TextView
    lateinit var mSquareProgressBar : SquareProgressBar
    lateinit var mSquareProgressViewKt: SquareProgressViewKt

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.wb_tab_home_fragment, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated 首页tab fragment")
        initView(view)
        addListener()
    }

    private fun initView(view : View) {
        mTextview = view.findViewById(R.id.fragment_tag)
        mTextview.setText("首页tab fragment页")
        mSquareProgressBar = view.findViewById(R.id.wb_square_progress)
        mSquareProgressBar.setImage(R.drawable.station_post_default_bg)

        mSquareProgressViewKt = view.findViewById(R.id.wb_square_progress_kt)

        val valueAnimator = ValueAnimator.ofInt(0, 100)
        valueAnimator.setDuration(4000)
        valueAnimator.addUpdateListener { valueAnimator1: ValueAnimator ->
            val animatedValue = valueAnimator1.animatedValue as Int
            //mSquareProgressBar.setProgress(animatedValue)
            mSquareProgressViewKt.setProgress(animatedValue.toDouble())
        }
        valueAnimator.start()
    }

    private fun addListener(){

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        Log.d(TAG, "setUserVisibleHint() 首页tab isVisibleToUser = $isVisibleToUser")
        super.setUserVisibleHint(isVisibleToUser)
    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        Log.d(TAG, "onLazyLoad() 首页tab fragment")
        mTextview.setText("首页tab fragment页 onLazyLoad()")
    }
}