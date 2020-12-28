package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import android.view.View
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.viewmodel.state.HomeViewModel
import com.sxy.jetpackdemo.databinding.FragmentWebBinding
import kotlinx.android.synthetic.main.fragment_web.*

class WebFragment : BaseFragment<HomeViewModel, FragmentWebBinding>(){
    override fun layoutId(): Int  = R.layout.fragment_web

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webview.loadUrl("www.baidu.com")
    }
}