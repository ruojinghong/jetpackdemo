package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import com.kingja.loadsir.core.LoadService
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.data.model.bean.IntegralResponse
import com.sxy.jetpackdemo.app.ui.adapter.IntegralAdapter
import com.sxy.jetpackdemo.app.viewmodel.state.IntegralViewModel
import com.sxy.jetpackdemo.databinding.FragmentIntegralBinding

/**
 * @author: sxy
 * @date: 2021/1/12
 * @description:
 */
class IntegralFragment: BaseFragment<IntegralViewModel,FragmentIntegralBinding>(){
    override fun layoutId(): Int = R.layout.fragment_integral
    lateinit var loadSir : LoadService<Any>
    private lateinit var integralAdapter: IntegralAdapter

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
}