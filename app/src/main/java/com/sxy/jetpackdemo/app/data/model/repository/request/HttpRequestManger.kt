package com.sxy.jetpackdemo.app.data.model.repository.request

import com.sxy.jetpackdemo.app.data.model.bean.ApiPagerResponse
import com.sxy.jetpackdemo.app.data.model.bean.ApiResponse
import com.sxy.jetpackdemo.app.data.model.bean.AriticleResponse
import com.sxy.jetpackdemo.app.data.model.bean.UserInfo
import com.sxy.jetpackdemo.app.network.apiService
import com.sxy.jetpackdemo.app.util.CacheUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import me.hgj.jetpackmvvm.network.AppException

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

    /**
     * 注册并登陆
     */
    suspend fun register(username: String, password: String): ApiResponse<UserInfo> {
        val registerData = apiService.register(username, password, password)
        //判断注册结果 注册成功，调用登录接口
        if (registerData.isSucces()) {
            return apiService.login(username, password)
        } else {
            //抛出错误异常
            throw AppException(registerData.errorCode, registerData.errorMsg)
        }
    }
}