package com.sxy.jetpackdemo.app.data.model.bean

import android.os.Parcelable
import com.sxy.jetpackdemo.app.util.DatetimeUtil
import kotlinx.android.parcel.Parcelize

/**
 * @author: sxy
 * @date: 2021/1/26
 * @description:
 */
@Parcelize
data class TodoResponse(
    var completeDate: Long,
    var completeDateStr: String,
    var content: String,
    var date: Long,
    var dateStr: String,
    var id: Int,
    var priority: Int,
    var status: Int,
    var title: String,
    var type: Int,
    var userId: Int
) : Parcelable {
    fun isDone(): Boolean {
        //判断是否已完成或者已过期
        return if (status == 1) {
            true
        } else {
            DatetimeUtil.nows.time > DatetimeUtil.formatDate(
                DatetimeUtil.DATE_PATTERN,
                dateStr
            ).time
        }
    }
}
