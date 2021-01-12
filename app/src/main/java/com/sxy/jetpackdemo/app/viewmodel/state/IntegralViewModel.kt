package com.sxy.jetpackdemo.app.viewmodel.state

import androidx.databinding.ObservableField
import com.sxy.jetpackdemo.app.data.model.bean.IntegralResponse
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @author: sxy
 * @date: 2021/1/12
 * @description:
 */
class IntegralViewModel : BaseViewModel() {
    var rank = ObservableField<IntegralResponse>()
}