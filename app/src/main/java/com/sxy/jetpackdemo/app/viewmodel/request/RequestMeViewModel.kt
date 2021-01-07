package com.sxy.jetpackdemo.app.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.sxy.jetpackdemo.app.data.model.bean.IntegralResponse
import com.sxy.jetpackdemo.app.network.apiService
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

/**
 * @author: sxy
 * @date: 2021/1/7
 * @description:
 */
class RequestMeViewModel : BaseViewModel(){
    var meData = MutableLiveData<ResultState<IntegralResponse>>()

    fun getIntegral() {
        request({ apiService.getIntegral() }, meData)
    }
}