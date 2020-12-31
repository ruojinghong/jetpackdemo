package com.sxy.jetpackdemo.app.weight.banner

import android.view.View
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.data.model.bean.BannerResponse
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * @author: sxy
 * @date: 2020/12/31
 * @description:
 */
class HomeBannerAdapter : BaseBannerAdapter<BannerResponse,HomeBannerViewHolder>() {
    override fun createViewHolder(itemView: View, viewType: Int): HomeBannerViewHolder = HomeBannerViewHolder(itemView)

    override fun onBind(
        holder: HomeBannerViewHolder?,
        data: BannerResponse?,
        position: Int,
        pageSize: Int
    ) {
      holder?.bindData(data,position,pageSize)
    }

    override fun getLayoutId(viewType: Int): Int  = R.layout.banner_itemhome
}