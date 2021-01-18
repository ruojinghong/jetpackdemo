package com.sxy.jetpackdemo.app.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.sxy.jetpackdemo.R


import com.sxy.jetpackdemo.app.data.model.bean.AriticleResponse

class ShareAdapter(data:ArrayList<AriticleResponse>) : AbsAnimalAdapter<AriticleResponse, BaseViewHolder>(
    R.layout.item_share_ariticle,data) {

    override fun convert(holder: BaseViewHolder, item: AriticleResponse) {
        //赋值
        item.run {
            holder.setText(R.id.item_share_title, title)
            holder.setText(R.id.item_share_date, niceDate)
        }
    }
}