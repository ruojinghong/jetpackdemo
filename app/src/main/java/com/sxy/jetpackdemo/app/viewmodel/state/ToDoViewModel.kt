package com.sxy.jetpackdemo.app.viewmodel.state

import com.sxy.jetpackdemo.app.data.model.enums.TodoType
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.IntObservableField
import me.hgj.jetpackmvvm.callback.databind.StringObservableField
import me.hgj.jetpackmvvm.ext.launch

/**
 * @author: sxy
 * @date: 2021/1/23
 * @description:
 */
class ToDoViewModel : BaseViewModel() {

    //标题
    var todoTitle = StringObservableField()

    //内容
    var todoContent =
        StringObservableField()

    //时间
    var todoTime = StringObservableField()

    //优先级
    var todoLeve =
        StringObservableField(TodoType.TodoType1.content)

    //优先级颜色
    var todoColor =
        IntObservableField(TodoType.TodoType1.color)


}