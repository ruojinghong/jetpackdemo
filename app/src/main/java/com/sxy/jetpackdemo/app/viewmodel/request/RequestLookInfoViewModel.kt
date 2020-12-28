package com.sxy.jetpackdemo.app.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.sxy.jetpackdemo.app.data.model.bean.AriticleResponse
import com.sxy.jetpackdemo.app.data.model.bean.ShareResponse
import com.sxy.jetpackdemo.app.network.apiService
import com.sxy.jetpackdemo.app.network.stateCallback.ListDataUiState
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request

/**
 * @author: sxy
 * @date: 2020/12/28
 * @description:
 */
class RequestLookInfoViewModel : BaseViewModel() {

    var pageNo = 1
    var shareListDataUiState  = MutableLiveData<ListDataUiState<AriticleResponse>>()
    var shareResponse = MutableLiveData<ShareResponse>()

    fun getLookinfo(id: Int, isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        request({ apiService.getShareByidData(id, pageNo) }, {
            //请求成功
            pageNo++
            shareResponse.value = it
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = it.shareArticles.isRefresh(),
                    isEmpty = it.shareArticles.isEmpty(),
                    hasMore = it.shareArticles.hasMore(),
                    isFirstEmpty = isRefresh && it.shareArticles.isEmpty(),
                    listData = it.shareArticles.datas
                )
            shareListDataUiState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<AriticleResponse>()
                )
            shareListDataUiState.value = listDataUiState
        })
    }
}