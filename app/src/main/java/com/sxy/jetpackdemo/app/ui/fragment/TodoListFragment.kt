package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.kingja.loadsir.core.LoadService
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.ui.adapter.TodoAdapter
import com.sxy.jetpackdemo.app.viewmodel.request.RequestTodoViewModel
import com.sxy.jetpackdemo.app.viewmodel.state.ToDoViewModel
import com.sxy.jetpackdemo.databinding.FragmentListBinding

/**
 * @author: sxy
 * @date: 2021/1/23
 * @description:
 */
class TodoListFragment :BaseFragment<ToDoViewModel,FragmentListBinding>() {

    //适配器
    private val articleAdapter: TodoAdapter by lazy { TodoAdapter(arrayListOf()) }

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //请求数据ViewModel
    private val requestViewModel: RequestTodoViewModel by viewModels()
    override fun layoutId(): Int  = R.layout.fragment_list

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
}