package com.sxy.jetpackdemo.app.data.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author: sxy
 * @date: 2020/12/28
 * @description:
 */
@Parcelize
data class ShareResponse(var coinInfo : CoinInfoResponse,
                         var shareArticles: ApiPagerResponse<ArrayList<AriticleResponse>>) : Parcelable