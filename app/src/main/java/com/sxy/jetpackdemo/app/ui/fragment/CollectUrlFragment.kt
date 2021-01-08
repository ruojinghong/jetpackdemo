package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.viewmodel.request.RequestCollectViewModel
import com.sxy.jetpackdemo.databinding.FragmentCollectBinding

/**
 * @author: sxy
 * @date: 2021/1/8
 * @description:收藏的网址集合Fragment
 */
class  CollectUrlFragment  : BaseFragment<RequestCollectViewModel,FragmentCollectBinding>(){
    override fun layoutId(): Int = R.layout.include_list

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
}