package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.viewmodel.request.RequestCollectViewModel
import com.sxy.jetpackdemo.databinding.FragmentCollectBinding

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
        val intPlus: Int.(Int) -> Int = { a -> this.times(a)
        }

        showLoading(b.toString())
    }
}