package com.ice.wb.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ice.common.fragment.BaseLazyFragment
import com.ice.wb.R
import kotlinx.android.synthetic.main.wb_tab_home_fragment.*

/**
 * Desc:
 * Created by lvzhengbin
 * Time: 2020/4/9
 */
class HomeTabFragmentKt : BaseLazyFragment(){

    val TAG : String = "HomeTabFragmentKt"
    lateinit var mTextview : TextView

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
        mTextview.text = "首页tab fragment页"

    }

    private fun addListener(){
        mTextview.setOnClickListener {
            if (wave_view.isAnimating()){
                wave_view.pauseAnimation()
            }else{
                wave_view.playAnimation()
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        Log.d(TAG, "setUserVisibleHint() 首页tab isVisibleToUser = $isVisibleToUser")
        super.setUserVisibleHint(isVisibleToUser)
    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        Log.d(TAG, "onLazyLoad() 首页tab fragment")
        mTextview.text = "首页tab fragment页 onLazyLoad()"
    }
}