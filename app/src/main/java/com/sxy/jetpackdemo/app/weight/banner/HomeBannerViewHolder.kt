package com.sxy.jetpackdemo.app.weight.banner

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sxy.jetpackdemo.R

import com.sxy.jetpackdemo.app.data.model.bean.BannerResponse
import com.zhpan.bannerview.BaseViewHolder
import kotlinx.android.synthetic.main.banner_itemhome.view.*
import me.hgj.jetpackmvvm.base.appContext

/**
 * @author: sxy
 * @date: 2020/12/31
 * @description: 首页轮播图
 */
class HomeBannerViewHolder(view : View) : BaseViewHolder<BannerResponse>(view) {
    override fun bindData(data: BannerResponse?, position: Int, pageSize: Int) {
      val img = itemView.findViewById<ImageView>(R.id.bannerhome_img)
        data?.apply {

            Glide.with(appContext)
                .load(imagePath)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(img)
        }
    }
}