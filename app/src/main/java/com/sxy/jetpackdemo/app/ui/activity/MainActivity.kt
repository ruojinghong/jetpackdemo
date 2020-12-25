package com.sxy.jetpackdemo.app.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.blankj.utilcode.util.ToastUtils
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseActivity
import com.sxy.jetpackdemo.databinding.ActivityMainBinding
import com.tencent.bugly.beta.Beta
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.network.manager.NetState

/**
 * @author: sxy
 * @date: 2020/12/17
 * @description:
 */
class MainActivity : BaseActivity<BaseViewModel,ActivityMainBinding>(){

    var exitTime = 0L

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        Beta.checkAppUpgrade(false,true)


        onBackPressedDispatcher.addCallback(this,object : OnBackPressedCallback(true){

            override fun handleOnBackPressed() {
                val nav = Navigation.findNavController(this@MainActivity,R.id.host_fragment)

                if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.mainfragment) {
                    //如果当前界面不是主页，那么直接调用返回即可
                    nav.navigateUp()
                } else {
                    //是主页
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        ToastUtils.showShort("再按一次退出程序")
                        exitTime = System.currentTimeMillis()
                    } else {
                        finish()
                    }
                }

            }

        })
    }

    override fun onNetworkStateChanged(netState: NetState) {
        super.onNetworkStateChanged(netState)
        if (netState.isSuccess) {
            Toast.makeText(applicationContext, "欲言又止的我", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "我特么怎么断网了，发生什么事了", Toast.LENGTH_SHORT).show()
        }
    }
}