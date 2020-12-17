package com.sxy.jetpackdemo.app.weight.banner

import android.view.View
import android.widget.TextView
import com.sxy.jetpackdemo.R
import com.zhpan.bannerview.BaseViewHolder

/**
 * @author: sxy
 * @date: 2020/12/17
 * @description:
 */
class WelcomeBannerViewHolder(view:View) :BaseViewHolder<String>(view) {
    override fun bindData(data: String?, position: Int, pageSize: Int) {
       val textView = findView<TextView>(R.id.banner_text)
        textView.text = data
    }
}