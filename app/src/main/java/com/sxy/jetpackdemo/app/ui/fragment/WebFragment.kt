package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.viewmodel.state.HomeViewModel
import com.sxy.jetpackdemo.databinding.FragmentHomeBinding

class WebFragment : BaseFragment<HomeViewModel,FragmentHomeBinding>(){
    override fun layoutId(): Int  = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
}