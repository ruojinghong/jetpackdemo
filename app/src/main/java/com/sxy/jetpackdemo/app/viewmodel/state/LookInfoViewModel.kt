package com.sxy.jetpackdemo.app.viewmodel.state

import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.StringObservableField

/**
 * @author: sxy
 * @date: 2020/12/28
 * @description:
 */
class LookInfoViewModel : BaseViewModel(){

    var name = StringObservableField("--")

    var imageUrl =
        StringObservableField("https://upload.jianshu.io/users/upload_avatars/9305757/93322613-ff1a-445c-80f9-57f088f7c1b1.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/300/h/300/format/webp")

    var info = StringObservableField()
}