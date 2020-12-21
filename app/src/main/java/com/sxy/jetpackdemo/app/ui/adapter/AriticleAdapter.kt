package com.sxy.jetpackdemo.app.ui.adapter

import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.sxy.jetpackdemo.app.data.model.bean.AriticleResponse
import com.sxy.jetpackdemo.app.weight.customview.CollectView

/**
 * @author: sxy
 * @date: 2020/12/21
 * @description:
 */
class AriticleAdapter(data:MutableList<AriticleResponse>?) :
        BaseDelegateMultiAdapter<AriticleResponse,BaseViewHolder>(data){
        private val Ariticle = 1//文章类型
        private val Project = 2//项目类型
        private var showTag = false//是否显示标签
        private var collectAction : (item : AriticleResponse,v: CollectView,position : Int) -> Unit =
                {_: AriticleResponse,_:CollectView,_:Int ->}


        override fun convert(holder: BaseViewHolder, item: AriticleResponse) {
                TODO("Not yet implemented")
        }
}