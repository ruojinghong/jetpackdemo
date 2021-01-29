package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.data.model.bean.TodoResponse
import com.sxy.jetpackdemo.app.data.model.enums.TodoType
import com.sxy.jetpackdemo.app.ext.showMessage
import com.sxy.jetpackdemo.app.util.DatetimeUtil
import com.sxy.jetpackdemo.app.viewmodel.request.RequestTodoViewModel
import com.sxy.jetpackdemo.app.viewmodel.state.ToDoViewModel
import com.sxy.jetpackdemo.app.weight.customview.PriorityDialog
import com.sxy.jetpackdemo.databinding.FragmentAddtodoBinding
import me.hgj.jetpackmvvm.ext.util.notNull
import java.util.*

/**
 * @author: sxy
 * @date: 2021/1/28
 * @description:
 */
class AddToDoFragment : BaseFragment<ToDoViewModel,FragmentAddtodoBinding>(){
    private var todoResponse: TodoResponse? = null

    val requestViewModel : RequestTodoViewModel by  viewModels()

    inner class ProxyClick {
        /** 选择时间*/
        fun todoTime() {
            activity?.let {
                MaterialDialog(it)
                    .lifecycleOwner(this@AddToDoFragment).show {
                        cornerRadius(0f)
                        datePicker(minDate = Calendar.getInstance()) { dialog, date ->
                            mViewModel.todoTime.set(
                                DatetimeUtil.formatDate(
                                    date.time,
                                    DatetimeUtil.DATE_PATTERN
                                )
                            )
                        }
                    }
            }
        }

        /**选择类型*/
        fun todoType() {
            activity?.let {
                PriorityDialog(it, TodoType.byValue(mViewModel.todoLeve.get()).type).apply {
                    setPriorityInterface(object : PriorityDialog.PriorityInterface {
                        override fun onSelect(type: TodoType) {
                            mViewModel.todoLeve.set(type.content)
                            mViewModel.todoColor.set(type.color)
                        }
                    })
                }.show()
            }
        }

        /**提交*/
        fun submit() {
            when {
                mViewModel.todoTitle.get().isEmpty() -> {
                    showMessage("请填写标题")
                }
                mViewModel.todoContent.get().isEmpty() -> {
                    showMessage("请填写内容")
                }
                mViewModel.todoTime.get().isEmpty() -> {
                    showMessage("请选择预计完成时间")
                }
                else -> {
                    todoResponse.notNull({
                        showMessage("确认提交编辑吗？", positiveButtonText = "提交", positiveAction = {
                            requestViewModel.updateTodo(
                                it.id,
                                mViewModel.todoTitle.get(),
                                mViewModel.todoContent.get(),
                                mViewModel.todoTime.get(),
                                TodoType.byValue(mViewModel.todoLeve.get()).type
                            )
                        }, negativeButtonText = "取消")
                    }, {
                        showMessage("确认添加吗？", positiveButtonText = "添加", positiveAction = {
                            requestViewModel.addTodo(
                                mViewModel.todoTitle.get(),
                                mViewModel.todoContent.get(),
                                mViewModel.todoTime.get(),
                                TodoType.byValue(mViewModel.todoLeve.get()).type
                            )
                        }, negativeButtonText = "取消")
                    })
                }
            }
        }
    }

    override fun layoutId(): Int  = R.layout.fragment_addtodo

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

}