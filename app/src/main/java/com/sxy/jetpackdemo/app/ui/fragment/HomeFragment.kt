package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ToastUtils
import com.kingja.loadsir.core.LoadService
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.ext.*
import com.sxy.jetpackdemo.app.viewmodel.state.HomeViewModel
import com.sxy.jetpackdemo.app.ui.adapter.AriticleAdapter
import com.sxy.jetpackdemo.app.viewmodel.request.RequestCollectViewModel
import com.sxy.jetpackdemo.app.viewmodel.request.RequestHomeViewModel
import com.sxy.jetpackdemo.app.weight.recyclerview.DefineLoadMoreView
import com.sxy.jetpackdemo.app.weight.recyclerview.SpaceItemDecoration
import com.sxy.jetpackdemo.databinding.FragmentMainBinding
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.include_list.*
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
        //初始化recyclerview
        recyclerView.init(LinearLayoutManager(context),articleAdapter).let {
            //因为首页要添加轮播图，所以我设置了firstNeedTop字段为false,即第一条数据不需要设置间距
            //添加分割线
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), false))
            footView = it.initFooter(SwipeRecyclerView.LoadMoreListener {
                    requestHomeViewModel.getHomeData(false)
            })
            //初始化FloatingActionButton
            it.initFloatBtn(floatbtn)
        }
        //初始化 SwipeRefreshLayout
        swipeRefresh.init {
            //触发刷新监听时请求数据
            requestHomeViewModel.getHomeData(true)
        }
            articleAdapter.run {
              setCollectClick{item, v, position ->
                    if(v.isChecked){
                        requestCollectViewModel.uncollect(item.id)
                    }else{
                        requestCollectViewModel.collect(item.id)
                    }
              }

                setOnItemClickListener { adapter, view, position ->
                    nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                        putParcelable(
                            "ariticleData",
                            articleAdapter.data[position - this@HomeFragment.recyclerView.headerCount]
                        )
                    })
                }
            }



    }

    override fun createObserver() {
        super.createObserver()
        requestHomeViewModel.run {
            //监听首页文章列表请求的数据变化
            homeDataState.observe(viewLifecycleOwner, Observer {
                //设值 新写了个拓展函数，搞死了这个恶心的重复代码
                loadListData(it, articleAdapter, loadsir, recyclerView, swipeRefresh)
            })
        }
    }
}