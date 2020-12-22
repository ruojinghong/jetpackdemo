package com.sxy.jetpackdemo.app.data.model.repository.request

import com.sxy.jetpackdemo.app.data.model.bean.ApiPagerResponse
import com.sxy.jetpackdemo.app.data.model.bean.ApiResponse
import com.sxy.jetpackdemo.app.data.model.bean.AriticleResponse
import com.sxy.jetpackdemo.app.network.apiService
import com.sxy.jetpackdemo.app.util.CacheUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * @author: sxy
 * @date: 2020/12/22
 * @description: 处理协程请求类
 */
val HttpRequestCoroutine: HttpRequestManger by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    HttpRequestManger()
}

class HttpRequestManger {

    /**
     * 获取首页文章数据
     */
    suspend fun getHomeData(pageNo: Int): ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>> {
        //同时异步请求2个接口，请求完成后合并数据
        return withContext(Dispatchers.IO) {
            val data = async { apiService.getAritrilList(pageNo) }
            //如果App配置打开了首页请求置顶文章，且是第一页
            if (CacheUtil.isNeedTop() && pageNo == 0) {
                val topData = async { apiService.getTopAritrilList() }
                data.await().data.datas.addAll(0, topData.await().data)
                data.await()
            } else {
                data.await()
            }
        }
    }
}