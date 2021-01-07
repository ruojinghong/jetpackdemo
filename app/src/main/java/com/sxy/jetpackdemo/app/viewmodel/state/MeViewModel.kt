package com.sxy.jetpackdemo.app.viewmodel.state

import com.sxy.jetpackdemo.app.util.ColorUtils
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.IntObservableField
import me.hgj.jetpackmvvm.callback.databind.StringObservableField
import me.hgj.jetpackmvvm.callback.livedata.UnPeekLiveData

/**
 * @author: sxy
 * @date: 2021/1/7
 * @description:
 */
class MeViewModel : BaseViewModel() {
    var name = StringObservableField("请先登录~")

    var integral = IntObservableField(0)

    var info = StringObservableField("id：--　排名：-")

    var imageUrl = StringObservableField(ColorUtils.randomImage())

    var testString = UnPeekLiveData<String>()
}