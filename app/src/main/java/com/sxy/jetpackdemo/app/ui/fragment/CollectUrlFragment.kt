package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.kingja.loadsir.core.LoadService
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.ext.*
import com.sxy.jetpackdemo.app.ui.adapter.CollectAdapter
import com.sxy.jetpackdemo.app.ui.adapter.CollectUrlAdapter
import com.sxy.jetpackdemo.app.viewmodel.request.RequestCollectViewModel
import com.sxy.jetpackdemo.app.weight.recyclerview.SpaceItemDecoration
import com.sxy.jetpackdemo.databinding.FragmentCollectBinding
import com.sxy.jetpackdemo.databinding.IncludeListBinding
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

/**
 * @author: sxy
 * @date: 2021/1/8
 * @description:收藏的网址集合Fragment
 */
class  CollectUrlFragment  : BaseFragment<RequestCollectViewModel, IncludeListBinding>(){
    override fun layoutId(): Int = R.layout.include_list

    private lateinit var loadsir : LoadService<Any>
    private  val ariticleAdapter : CollectUrlAdapter by lazy { CollectUrlAdapter(arrayListOf()) }

    override fun initView(savedInstanceState: Bundle?) {
        loadsir = loadServiceInit(swipeRefresh){
            loadsir.showLoading()
            mViewModel.getCollectUrlData()
        }
        recyclerView.init(LinearLayoutManager(context),ariticleAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            //初始化FloatingActionButton
            it.initFloatBtn(floatbtn)

        }

        swipeRefresh.init {
            mViewModel.getCollectUrlData()
        }


        ariticleAdapter.run {

            setCollectClick { item, v, position ->
                if (v.isChecked) {
                    mViewModel.uncollectUrl(item.id)
                } else {
                    mViewModel.collectUrl(item.name, item.link)
                }
            }
            setOnItemClickListener { _, view, position ->
                nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                    putParcelable("collectUrl", ariticleAdapter.data[position])
                })
            }
        }
    }
    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        mViewModel.getCollectUrlData()
    }

    override fun createObserver() {
        mViewModel.urlDataState.observe(viewLifecycleOwner, Observer {
            swipeRefresh.isRefreshing = false
            recyclerView.loadMoreFinish(it.isEmpty, it.hasMore)
            if (it.isSuccess) {
                //成功
                when {
                    //第一页并没有数据 显示空布局界面
                    it.isEmpty -> {
                        loadsir.showEmpty()
                    }
                    else -> {
                        loadsir.showSuccess()
                        ariticleAdapter.setList(it.listData)
                    }
                }
            } else {
                //失败
                loadsir.showError(it.errMessage)
            }
        })
        mViewModel.collectUrlUiState.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                for (index in ariticleAdapter.data.indices) {
                    if (ariticleAdapter.data[index].id == it.id) {
                        ariticleAdapter.remove(index)
                        if (ariticleAdapter.data.size == 0) {
                            loadsir.showEmpty()
                        }
                        return@Observer
                    }
                }
            } else {
                showMessage(it.errorMsg)
                for (index in ariticleAdapter.data.indices) {
                    if (ariticleAdapter.data[index].id == it.id) {
                        ariticleAdapter.notifyItemChanged(index)
                        break
                    }
                }
            }
        })
        eventViewModel.run {
            //监听全局的收藏信息 收藏的Id跟本列表的数据id匹配则 需要删除他 否则则请求最新收藏数据
            collectEvent.observeInFragment(this@CollectUrlFragment, Observer {
                for (index in ariticleAdapter.data.indices) {
                    if (ariticleAdapter.data[index].id == it.id) {
                        ariticleAdapter.data.removeAt(index)
                        ariticleAdapter.notifyItemChanged(index)
                        if (ariticleAdapter.data.size == 0) {
                            loadsir.showEmpty()
                        }
                        return@Observer
                    }
                }
                mViewModel.getCollectUrlData()
            })
        }
    }
}