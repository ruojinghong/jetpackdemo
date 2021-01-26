package com.sxy.jetpackdemo.app.ui.adapter

import android.util.TypedValue
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.data.model.bean.TodoResponse
import com.sxy.jetpackdemo.app.data.model.enums.TodoType
import com.sxy.jetpackdemo.app.util.SettingUtil

/**
 * @author: sxy
 * @date: 2021/1/26
 * @description:
 */
class TodoAdapter(data:ArrayList<TodoResponse>) : AbsAnimalAdapter<TodoResponse,BaseViewHolder>
    (R.layout.item_todo,data) {
    override fun convert(holder: BaseViewHolder, item: TodoResponse) {
        //赋值
        item.run {
            holder.setText(R.id.item_todo_title, title)
            holder.setText(R.id.item_todo_content, content)
            holder.setText(R.id.item_todo_date, dateStr)
            if (status == 1) {
                //已完成
                holder.setVisible(R.id.item_todo_status, true)
                holder.setImageResource(R.id.item_todo_status, R.mipmap.ic_done)
                holder.getView<CardView>(R.id.item_todo_cardview).foreground = context.getDrawable(R.drawable.forground_shape)
            } else {
                if (isDone()) {
                    //未完成并且超过了预定完成时间
                    holder.setVisible(R.id.item_todo_status, true)
                    holder.setImageResource(R.id.item_todo_status, R.mipmap.ic_yiguoqi)
                    holder.getView<CardView>(R.id.item_todo_cardview).foreground = context.getDrawable(R.drawable.forground_shape)
                } else {
                    //未完成
                    holder.setVisible(R.id.item_todo_status, false)
                    TypedValue().apply {
                        context.theme.resolveAttribute(R.attr.selectableItemBackground, this, true)
                    }.run {
                        holder.getView<CardView>(R.id.item_todo_cardview).foreground = context.getDrawable(resourceId)
                    }
                }
            }
            holder.getView<ImageView>(R.id.item_todo_tag).imageTintList = SettingUtil.getOneColorStateList(
                TodoType.byType(priority).color)
        }
    }
}