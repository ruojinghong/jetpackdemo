package com.sxy.jetpackdemo.app.data.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author: sxy
 * @date: 2021/1/7
 * @description:
 */
@Parcelize
data class IntegralResponse(var coinCount: Int,//当前积分
                            var rank: Int,
                            var userId: Int,
                            var username: String) : Parcelable
