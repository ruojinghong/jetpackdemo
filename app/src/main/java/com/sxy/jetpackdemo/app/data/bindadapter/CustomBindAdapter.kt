package com.sxy.jetpackdemo.app.data.bindadapter

import android.os.SystemClock
import android.text.InputType
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.sxy.jetpackdemo.app.weight.preference.MyColorCircleView
import me.hgj.jetpackmvvm.ext.view.textString

/**
 * 自定义bindadapter
 */
object CustomBindAdapter {

    @BindingAdapter(value = ["circleImageUrl"])
    @JvmStatic
    fun circleImageUrl(view: ImageView, url: String) {
        Glide.with(view.context.applicationContext)
            .load(url)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(view)
    }

    @BindingAdapter("noRepeatClick")
    @JvmStatic
    fun setOnClick(view: View, clickListener: () -> Unit) {
        val mHits = LongArray(2)
        view.setOnClickListener {
            System.arraycopy(mHits, 1, mHits, 0, mHits.size - 1)
            mHits[mHits.size - 1] = SystemClock.uptimeMillis()
            if (mHits[0] < SystemClock.uptimeMillis() - 500) {
                clickListener.invoke()
            }
        }
    }

    @BindingAdapter(value = ["showPwd"])
    @JvmStatic
    fun showPwd(view: EditText, boolean: Boolean) {
        if (boolean) {
            view.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            view.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        view.setSelection(view.textString().length)
    }

    @BindingAdapter(value = ["checkChange"])
    @JvmStatic
    fun checkChange(checkbox: CheckBox, listener: CompoundButton.OnCheckedChangeListener) {
        checkbox.setOnCheckedChangeListener(listener)
    }

    @BindingAdapter(value = ["colorCircleViewColor"])
    @JvmStatic
    fun colorCircleViewColor(view: MyColorCircleView, color: Int) {
        view.setView(color)
    }
}