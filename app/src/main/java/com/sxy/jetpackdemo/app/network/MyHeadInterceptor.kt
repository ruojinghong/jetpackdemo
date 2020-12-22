package com.sxy.jetpackdemo.app.network

import com.sxy.jetpackdemo.app.util.CacheUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author: sxy
 * @date: 2020/12/22
 * @description: 自定义头部参数拦截器，传入heads
 */
class MyHeadInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("token", "token123456").build()
        builder.addHeader("device", "Android").build()
        builder.addHeader("isLogin", CacheUtil.isLogin().toString()).build()
        return chain.proceed(builder.build())
    }
}