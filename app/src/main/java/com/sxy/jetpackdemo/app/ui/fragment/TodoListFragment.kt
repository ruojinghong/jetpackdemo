package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.viewmodel.state.ToDoViewModel
import com.sxy.jetpackdemo.databinding.FragmentListBinding

/**
 * @author: sxy
 * @date: 2021/1/23
 * @description:
 */
class TodoListFragment :BaseFragment<ToDoViewModel,FragmentListBinding>() {
    override fun layoutId(): Int  = R.layout.fragment_list

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
}