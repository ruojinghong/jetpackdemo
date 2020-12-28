package com.sxy.jetpackdemo.app.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.sxy.jetpackdemo.app.data.model.bean.AriticleResponse
import com.sxy.jetpackdemo.app.data.model.bean.ShareResponse
import com.sxy.jetpackdemo.app.network.stateCallback.ListDataUiState
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @author: sxy
 * @date: 2020/12/28
 * @description:
 */
class RequestLookInfoViewModel : BaseViewModel() {

    var pageNo = 1
    var shareListDataUiState  = MutableLiveData<ListDataUiState<AriticleResponse>>()
    var shareResponse = MutableLiveData<ShareResponse>()
}