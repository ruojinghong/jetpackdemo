package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.blankj.utilcode.util.ToastUtils
import com.kingja.loadsir.core.LoadService
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.ext.init
import com.sxy.jetpackdemo.app.ext.loadServiceInit
import com.sxy.jetpackdemo.app.ext.showLoading
import com.sxy.jetpackdemo.app.viewmodel.state.HomeViewModel
import com.sxy.jetpackdemo.app.ui.adapter.AriticleAdapter
import com.sxy.jetpackdemo.app.viewmodel.request.RequestCollectViewModel
import com.sxy.jetpackdemo.app.viewmodel.request.RequestHomeViewModel
import com.sxy.jetpackdemo.app.weight.recyclerview.DefineLoadMoreView
import com.sxy.jetpackdemo.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

/**
 * @author: sxy
 * @date: 2020/12/16
 * @description:
 */
class HomeFragment : BaseFragment<HomeViewModel,FragmentMainBinding>() {
    override fun layoutId(): Int = R.layout.fragment_home
    //适配器
    private val articleAdapter : AriticleAdapter by lazy { AriticleAdapter(arrayListOf(),true) }

    //界面状态管理者
    private lateinit var  loadsir : LoadService<Any>

    //recyclerview的底部加载view，动态改变颜色
    private lateinit var  footView:DefineLoadMoreView
    //收藏viewModel
    private val requestCollectViewModel: RequestCollectViewModel by viewModels()
    //请求数据ViewModel
    private val requestHomeViewModel: RequestHomeViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        //状态页配置
        loadsir = loadServiceInit(swipeRefresh) {
            //点击重试时触发的操作
            loadsir.showLoading()
            requestHomeViewModel.getBannerData()
            requestHomeViewModel.getHomeData(true)
        }
        //初始化 toolbar
        toolbar.run {
            init("首页")
            inflateMenu(R.menu.home_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.home_search -> {
                        ToastUtils.showShort("跳转到searchFragment")
//                    nav().navigateAction(R.id.action_mainfragment_to_searchFragment)
                    }
                }
                true
            }
        }
    }
}