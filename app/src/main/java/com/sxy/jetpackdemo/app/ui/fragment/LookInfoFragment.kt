package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.kingja.loadsir.core.LoadService
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.ext.*
import com.sxy.jetpackdemo.app.ui.adapter.AriticleAdapter
import com.sxy.jetpackdemo.app.viewmodel.request.RequestCollectViewModel
import com.sxy.jetpackdemo.app.viewmodel.request.RequestLookInfoViewModel
import com.sxy.jetpackdemo.app.viewmodel.state.LookInfoViewModel
import com.sxy.jetpackdemo.app.weight.recyclerview.SpaceItemDecoration
import com.sxy.jetpackdemo.databinding.FragmentLookinfoBinding
import com.sxy.jetpackdemo.databinding.FragmentWebBinding
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.fragment_lookinfo.*
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

/**
 * @author: sxy
 * @date: 2020/12/28
 * @description: 个人详情
 */
class LookInfoFragment : BaseFragment<LookInfoViewModel,FragmentLookinfoBinding>(){

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
       arguments?.let {
            shareId = it.getInt("id")
       }
        mDatabind.vm = mViewModel
        appViewModel.appColor.value?.let { share_layout.setBackgroundColor(it) }
        toolbar.initClose(){
            nav().navigateUp()
        }
        loadsir = loadServiceInit(share_linear) {
            loadsir.showLoading()
            requestLookInfoViewModel.getLookinfo(shareId, true)
        }

        recyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            it.initFooter(SwipeRecyclerView.LoadMoreListener {
                requestLookInfoViewModel.getLookinfo(shareId, false)
            })
            it.initFloatBtn(floatbtn)
        }
        swipeRefresh.init {
            requestLookInfoViewModel.getLookinfo(shareId, true)
        }

        articleAdapter.run {
            setCollectClick { item, v, position ->
                if (v.isChecked) {
                    requestCollectViewModel.uncollect(item.id)
                } else {
                    requestCollectViewModel.collect(item.id)
                }
            }
            setOnItemClickListener { adapter, view, position ->
                nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                    putParcelable(
                        "ariticleData",
                        articleAdapter.data[position - this@LookInfoFragment.recyclerView.headerCount]
                    )
                })
            }
        }
    }
}