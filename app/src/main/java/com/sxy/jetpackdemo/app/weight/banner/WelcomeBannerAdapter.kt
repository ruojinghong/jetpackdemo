package com.sxy.jetpackdemo.app.weight.banner

import android.view.View
import com.sxy.jetpackdemo.R
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * @author: sxy
 * @date: 2020/12/17
 * @description:
 */
class WelcomeBannerAdapter : BaseBannerAdapter<String,WelcomeBannerViewHolder>(){
    override fun createViewHolder(itemView: View, viewType: Int): WelcomeBannerViewHolder = WelcomeBannerViewHolder(itemView)

    override fun onBind(
        holder: WelcomeBannerViewHolder?,
        data: String?,
        position: Int,
        pageSize: Int
    ) {
       holder?.bindData(data,position,pageSize)
    }

    override fun getLayoutId(viewType: Int): Int = R.layout.banner_itemwelcome
}