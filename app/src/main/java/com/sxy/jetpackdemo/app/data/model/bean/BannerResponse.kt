package com.sxy.jetpackdemo.app.data.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author: sxy
 * @date: 2020/12/22
 * @description:
 */
@Parcelize
data class BannerResponse(
    var desc: String = "",
    var id: Int = 0,
    var imagePath: String = "",
    var isVisible: Int = 0,
    var order: Int = 0,
    var title: String = "",
    var type: Int = 0,
    var url: String = ""
) : Parcelable
