package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.ui.adapter.CollectAdapter
import com.sxy.jetpackdemo.app.viewmodel.request.RequestCollectViewModel
import com.sxy.jetpackdemo.databinding.IncludeListBinding

/**
 * @author: sxy
 * @date: 2021/1/8
 * @description: 收藏的文章集合Fragment
 */
class CollectAriticleFragment : BaseFragment<RequestCollectViewModel,IncludeListBinding>() {
    override fun layoutId(): Int  = R.layout.include_list

    private  lateinit var loadSir : LoadService<Any>
    private val articleAdapter : CollectAdapter by lazy { CollectAdapter(arrayListOf()) }


    override fun initView(savedInstanceState: Bundle?) {

    }
}