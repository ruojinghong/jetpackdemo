package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.ext.init
import com.sxy.jetpackdemo.app.ext.initMain
import com.sxy.jetpackdemo.app.ext.interceptLongClick
import com.sxy.jetpackdemo.app.viewmodel.state.MainViewModel

import com.sxy.jetpackdemo.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * @author: sxy
 * @date: 2020/12/16
 * @description:
 */
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {

    override fun layoutId() = R.layout.fragment_main

    override fun initView(savedInstanceState: Bundle?) {
        //初始化viewpager2
        mainViewpager.initMain(this)
        //初始化 bottomBar
        mainBottom.init {
            when (it) {
                R.id.menu_main -> mainViewpager.setCurrentItem(0, false)
                R.id.menu_project -> mainViewpager.setCurrentItem(1, false)
                R.id.menu_system -> mainViewpager.setCurrentItem(2, false)
                R.id.menu_public -> mainViewpager.setCurrentItem(3, false)
                R.id.menu_me -> mainViewpager.setCurrentItem(4, false)
            }
        }
        mainBottom.interceptLongClick(
            R.id.menu_main,
            R.id.menu_project,
            R.id.menu_system,
            R.id.menu_public,
            R.id.menu_me
        )

    }

    override fun createObserver() {
        appViewModel.appColor.observe(viewLifecycleOwner, Observer {
//            setUiTheme(it, mainBottom)
        })
    }

}