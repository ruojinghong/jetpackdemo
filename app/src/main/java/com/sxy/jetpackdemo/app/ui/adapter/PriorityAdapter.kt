package com.sxy.jetpackdemo.app.ui.adapter

import android.widget.BaseAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.data.model.enums.TodoType
import com.sxy.jetpackdemo.app.weight.preference.MyColorCircleView

/**
 * @author: sxy
 * @date: 2021/1/29
 * @description:
 */
class PriorityAdapter(data : ArrayList<TodoType>)
    : BaseQuickAdapter<TodoType,BaseViewHolder>(R.layout.item_todo_dialog) {
    var checkType = TodoType.TodoType1.type
    constructor(data: ArrayList<TodoType>, checkType: Int):this(data){
        this.checkType = checkType
    }


    override fun convert(holder: BaseViewHolder, item: TodoType) {
        //赋值
        item.run {
            holder.setText(R.id.item_todo_dialog_name, item.content)
            if (checkType == item.type) {
                holder.getView<MyColorCircleView>(R.id.item_todo_dialog_icon).setViewSelect(item.color)
            } else {
                holder.getView<MyColorCircleView>(R.id.item_todo_dialog_icon).setView(item.color)
            }
        }
    }
}