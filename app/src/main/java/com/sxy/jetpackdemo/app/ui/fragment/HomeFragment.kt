package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.databinding.FragmentMainBinding
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @author: sxy
 * @date: 2020/12/16
 * @description:
 */
class HomeFragment : BaseFragment<BaseViewModel,FragmentMainBinding>() {
    override fun layoutId(): Int = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
}