package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.ext.bindViewPager2
import com.sxy.jetpackdemo.app.ext.init
import com.sxy.jetpackdemo.app.ext.initClose
import com.sxy.jetpackdemo.app.viewmodel.request.RequestCollectViewModel
import com.sxy.jetpackdemo.databinding.FragmentCollectBinding
import kotlinx.android.synthetic.main.fragment_collect.*
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.ext.nav

/**
 * @author: sxy
 * @date: 2021/1/8
 * @description:
 */
class CollectFragment : BaseFragment<RequestCollectViewModel,FragmentCollectBinding>() {
    override fun layoutId(): Int  = R.layout.fragment_collect
    var titleData = arrayListOf<String>("文章","网址")
    private var fragments = arrayListOf<Fragment>()
    init {
    fragments.add(CollectAriticleFragment())
    fragments.add(CollectUrlFragment())
    }

    override fun initView(savedInstanceState: Bundle?) {
        val a = { i: Int -> i+1 }
        val b = a(1)
        val stringPlus: (String, String) -> String = String::plus
        val stirngplus:(String ,String) -> Int = { s, s2 ->s.length  }
        val intPlus: (Int, Int) -> Int = { a,b -> a.times(a)}


            //初始化时设置顶部主题颜色
            appViewModel.appColor.value?.let { collect_viewpager_linear.setBackgroundColor(it) }
            //初始化viewpager2
            collect_view_pager.init(this,fragments)
            //初始化 magic_indicator
            collect_magic_indicator.bindViewPager2(collect_view_pager,mStringList = titleData)
            toolbar.initClose() {
                nav().navigateUp()
            }




    }
}