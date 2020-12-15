package me.hgj.jetpackmvvm.demo.app.weight.loadCallBack

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.sxy.jetpackdemo.R


class LoadingCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_loading
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}