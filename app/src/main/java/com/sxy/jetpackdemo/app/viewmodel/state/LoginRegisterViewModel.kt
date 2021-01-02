package com.sxy.jetpackdemo.app.viewmodel.state

import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.BooleanObservableField
import me.hgj.jetpackmvvm.callback.databind.StringObservableField
import me.hgj.jetpackmvvm.callback.livedata.StringLiveData

/**
 * @author: sxy
 * @date: 2021/1/2
 * @description:
 */
class LoginRegisterViewModel : BaseViewModel() {

    //用户名
    var username = StringLiveData()

    //密码(登录注册界面)
    var password = StringObservableField()

    var password2 = StringObservableField()

    //是否显示明文密码（登录注册界面）
    var isShowPwd = BooleanObservableField()

    var isShowPwd2 = BooleanObservableField()
}