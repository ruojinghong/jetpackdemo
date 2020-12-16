package com.sxy.jetpackdemo.app

import android.support.multidex.MultiDex
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.tencent.mmkv.MMKV
import me.hgj.jetpackmvvm.base.BaseApp
import me.hgj.jetpackmvvm.demo.app.weight.loadCallBack.EmptyCallback
import me.hgj.jetpackmvvm.demo.app.weight.loadCallBack.ErrorCallback
import me.hgj.jetpackmvvm.demo.app.weight.loadCallBack.LoadingCallback

/**
 * @author: sxy
 * @date: 2020/12/16
 * @description:
 */
object App :BaseApp(){
    lateinit var  instance : App
    override fun onCreate() {
        super.onCreate()
        instance =  this
        //方法数量太多
        MultiDex.install(this)
        MMKV.initialize(this.filesDir.absolutePath+"/mmkv")
        //界面加载管理初始化
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .setDefaultCallback(SuccessCallback::class.java)
            .commit()
        //初始化bugly
        val context  = applicationContext
        //包名
        val packageName = context.packageName
        //进程名
        val processName = getProcessName(android.os.Process.myPid())
    }


}