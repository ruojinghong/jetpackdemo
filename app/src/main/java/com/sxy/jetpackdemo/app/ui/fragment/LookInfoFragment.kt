package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.kingja.loadsir.core.LoadService
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.ui.adapter.AriticleAdapter
import com.sxy.jetpackdemo.app.viewmodel.request.RequestCollectViewModel
import com.sxy.jetpackdemo.app.viewmodel.request.RequestLookInfoViewModel
import com.sxy.jetpackdemo.databinding.FragmentLookinfoBinding
import com.sxy.jetpackdemo.databinding.FragmentWebBinding
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @author: sxy
 * @date: 2020/12/28
 * @description: 个人详情
 */
class LookInfoFragment : BaseFragment<BaseViewModel,FragmentLookinfoBinding>(){

    //对方的Id
    private var shareId = 0
    //界面状态管理者
    private lateinit var loadsir : LoadService<Any>

    private val articleAdapter : AriticleAdapter by lazy { AriticleAdapter(arrayListOf(),true) }

    private val requestCollectViewModel:RequestCollectViewModel by viewModels()

    //专门负责请求数据的ViewModel
    private val requestLookInfoViewModel: RequestLookInfoViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_lookinfo

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
}