package com.sxy.jetpackdemo.app.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.VibrateUtils
import com.just.agentweb.AgentWeb
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.data.model.bean.*
import com.sxy.jetpackdemo.app.data.model.enums.CollectType
import com.sxy.jetpackdemo.app.ext.hideSoftKeyboard
import com.sxy.jetpackdemo.app.ext.initClose
import com.sxy.jetpackdemo.app.ext.showMessage
import com.sxy.jetpackdemo.app.util.CacheUtil
import com.sxy.jetpackdemo.app.viewmodel.request.RequestCollectViewModel
import com.sxy.jetpackdemo.app.viewmodel.state.HomeViewModel
import com.sxy.jetpackdemo.app.viewmodel.state.WebViewModel
import com.sxy.jetpackdemo.databinding.FragmentWebBinding
import kotlinx.android.synthetic.main.fragment_web.*
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.ext.nav

class WebFragment : BaseFragment<WebViewModel, FragmentWebBinding>(){
    private var mAgentWeb : AgentWeb? = null
    private var preWeb : AgentWeb.PreAgentWeb? = null
    private val requestCollectViewModel : RequestCollectViewModel by viewModels()





    override fun layoutId(): Int  = R.layout.fragment_web

    override fun initView(savedInstanceState: Bundle?) {
                setHasOptionsMenu(true)

        arguments?.run {

            getParcelable<AriticleResponse>("ariticleData")?.let{

                mViewModel.ariticleId = it.id
                mViewModel.showTitle = it.title
                mViewModel.collect = it.collect
                mViewModel.url = it.link
                mViewModel.collectType = CollectType.Ariticle.type

            }

            //点击首页轮播图进来的
            getParcelable<BannerResponse>("bannerdata")?.let {
                mViewModel.ariticleId = it.id
                mViewModel.showTitle = it.title
                //从首页轮播图 没法判断是否已经收藏过，所以直接默认没有收藏
                mViewModel.collect = false
                mViewModel.url = it.url
                mViewModel.collectType = CollectType.Url.type
            }
            //从收藏文章列表点进来的
            getParcelable<CollectResponse>("collect")?.let {
                mViewModel.ariticleId = it.originId
                mViewModel.showTitle = it.title
                //从收藏列表过来的，肯定 是 true 了
                mViewModel.collect = true
                mViewModel.url = it.link
                mViewModel.collectType = CollectType.Ariticle.type
            }
            //点击收藏网址列表进来的
            getParcelable<CollectUrlResponse>("collectUrl")?.let {
                mViewModel.ariticleId = it.id
                mViewModel.showTitle = it.name
                //从收藏列表过来的，肯定 是 true 了
                mViewModel.collect = true
                mViewModel.url = it.link
                mViewModel.collectType = CollectType.Url.type
            }
        }

        toolbar.run {
                    //设置menu 关键代码
                mActivity.setSupportActionBar(this)
            initClose(mViewModel.showTitle){
                    hideSoftKeyboard(activity)
                        mAgentWeb?.let {
                        if(it.webCreator.webView.canGoBack()){
                                it.webCreator.webView.goBack()
                        }else{
                            nav().navigateUp()
                        }

                }
            }
        }
        preWeb = AgentWeb.with(this)
            .setAgentWebParent(webcontent,LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT
        )).useDefaultIndicator()
            .createAgentWeb()
            .ready()
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mAgentWeb = preWeb?.go(mViewModel.url)

        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mAgentWeb?.let { web ->
                        if (web.webCreator.webView.canGoBack()) {
                            web.webCreator.webView.goBack()
                        } else {
                            nav().navigateUp()
                        }
                    }
                }
            })
    }

    override fun createObserver() {
        requestCollectViewModel.collectUiState.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                mViewModel.collect = it.collect
                eventViewModel.collectEvent.value = CollectBus(it.id, it.collect)
                //刷新一下menu
                mActivity.window?.invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL)
                mActivity.invalidateOptionsMenu()
            } else {
                showMessage(it.errorMsg)
            }
        })
        requestCollectViewModel.collectUrlUiState.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                eventViewModel.collectEvent.value = CollectBus(it.id, it.collect)
                mViewModel.collect = it.collect
                //刷新一下menu
                mActivity.window?.invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL)
                mActivity.invalidateOptionsMenu()
            } else {
                showMessage(it.errorMsg)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.web_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        context?.let {
            if (mViewModel.collect) {
                menu.findItem(R.id.web_collect).icon =
                    ContextCompat.getDrawable(it, R.mipmap.ic_collected)
            } else {
                menu.findItem(R.id.web_collect).icon =
                    ContextCompat.getDrawable(it, R.mipmap.ic_collect)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.web_share -> {
                //分享
                startActivity(Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "{${mViewModel.showTitle}}:${mViewModel.url}")
                    type = "text/plain"
                }, "分享到"))
            }
            R.id.web_refresh -> {
                //刷新网页
                mAgentWeb?.urlLoader?.reload()
            }
            R.id.web_collect -> {
                //点击收藏 震动一下
                VibrateUtils.vibrate(40)
                //是否已经登录了，没登录需要跳转到登录页去
                if (CacheUtil.isLogin()) {
                    //是否已经收藏了
                    if (mViewModel.collect) {
                        if (mViewModel.collectType == CollectType.Url.type) {
                            //取消收藏网址
                            requestCollectViewModel.uncollectUrl(mViewModel.ariticleId)
                        } else {
                            //取消收藏文章
                            requestCollectViewModel.uncollect(mViewModel.ariticleId)
                        }
                    } else {
                        if (mViewModel.collectType == CollectType.Url.type) {
                            //收藏网址
                            requestCollectViewModel.collectUrl(mViewModel.showTitle, mViewModel.url)
                        } else {
                            //收藏文章
                            requestCollectViewModel.collect(mViewModel.ariticleId)
                        }
                    }
                } else {
                    //跳转到登录页
//                    ToastUtils.showShort("跳转到登录页未实现")
                    nav().navigate(R.id.action_to_loginFragment)
                }
            }
            R.id.web_liulanqi -> {
                //用浏览器打开
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mViewModel.url)))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPause() {

        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        mActivity.setSupportActionBar(null)
        super.onDestroy()
    }
}