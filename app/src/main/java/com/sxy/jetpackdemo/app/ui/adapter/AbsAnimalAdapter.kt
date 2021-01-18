package com.sxy.jetpackdemo.app.ui.adapter

import android.annotation.SuppressLint
import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.sxy.jetpackdemo.app.ext.setAdapterAnimation
import com.sxy.jetpackdemo.app.util.SettingUtil


abstract class AbsAnimalAdapter<T,VH: BaseViewHolder>  @JvmOverloads
constructor(@LayoutRes private val layoutResId: Int,
            data: MutableList<T>? = null)  : BaseQuickAdapter<T,VH>(layoutResId,data) {

    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

}