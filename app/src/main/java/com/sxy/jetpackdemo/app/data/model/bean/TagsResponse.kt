package com.sxy.jetpackdemo.app.data.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author: sxy
 * @date: 2020/12/21
 * @description:
 */
@Parcelize
data class TagsResponse(var name:String, var url:String) : Parcelable
