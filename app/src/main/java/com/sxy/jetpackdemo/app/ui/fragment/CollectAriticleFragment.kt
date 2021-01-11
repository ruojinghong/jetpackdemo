package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.data.model.bean.CollectBus
import com.sxy.jetpackdemo.app.ext.*
import com.sxy.jetpackdemo.app.ui.adapter.CollectAdapter
import com.sxy.jetpackdemo.app.viewmodel.request.RequestCollectViewModel
import com.sxy.jetpackdemo.app.weight.recyclerview.SpaceItemDecoration
import com.sxy.jetpackdemo.databinding.IncludeListBinding
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

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
            loadSir = loadServiceInit(swipeRefresh) {
                //点击重试时触发的操作
                loadSir.showLoading()
                mViewModel.getCollectAriticleData(true)
            }
        //初始化RecyclerView
        recyclerView.init(LinearLayoutManager(context),articleAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0,ConvertUtils.dp2px(8f)))
            it.initFooter(SwipeRecyclerView.LoadMoreListener {

                mViewModel.getCollectAriticleData(false)

            })
            //初始化FloatingActionBar
            it.initFloatBtn(floatbtn)
        }

        swipeRefresh.init {
            mViewModel.getCollectAriticleData(true)
        }

        articleAdapter.run {
                setCollectClick{ item, v, _ ->
                    if(v.isChecked){
                        mViewModel.uncollect(item.originId)
                    }else{
                        mViewModel.collect(item.originId)
                    }
                }
            setOnItemClickListener{ _, _, position ->
               nav().navigateAction(R.id.action_to_webFragment,Bundle().apply {
                   putParcelable("collect",articleAdapter.data[position])
               })

            }

        }



    }

    override fun lazyLoadData() {
        loadSir.showLoading()
        mViewModel.getCollectAriticleData(true)
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.ariticleDataState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, articleAdapter, loadSir, recyclerView,swipeRefresh)

        })
        mViewModel.collectUiState.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                //收藏或取消收藏操作成功，发送全局收藏消息
                eventViewModel.collectEvent.value = CollectBus(it.id, it.collect)
            } else {
                showMessage(it.errorMsg)
                for (index in articleAdapter.data.indices) {
                    if (articleAdapter.data[index].originId == it.id) {
                        articleAdapter.notifyItemChanged(index)
                        break
                    }
                }
            }
        })
        eventViewModel.run {
            //监听全局的收藏信息 收藏的Id跟本列表的数据id匹配则 需要删除他 否则则请求最新收藏数据
            collectEvent.observeInFragment(this@CollectAriticleFragment, Observer {
                for (index in articleAdapter.data.indices) {
                    if (articleAdapter.data[index].originId == it.id) {
                        articleAdapter.remove(index)
                        if (articleAdapter.data.size == 0) {
                            loadSir.showEmpty()
                        }
                        return@Observer
                    }
                }
                mViewModel.getCollectAriticleData(true)
            })
        }
    }
}