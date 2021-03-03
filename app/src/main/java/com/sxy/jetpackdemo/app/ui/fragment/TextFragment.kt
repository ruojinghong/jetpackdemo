package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.viewmodel.state.MeViewModel
import com.sxy.jetpackdemo.databinding.FragmentMeBinding
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author: sxy
 * @date: 2021/2/23
 * @description:
 */
class TextFragment : BaseFragment<MeViewModel, FragmentMeBinding>() {
    override fun layoutId(): Int = R.layout.fragment_me
    override fun initView(savedInstanceState: Bundle?) {

//        var textViews = ArrayList<TextView>()
//        textViews.add(Button(context))
//        var button: Button = textViews.get(0)
//
//
//        var buttons = ArrayList<Button>()
//        buttons.add(TextView(context))
//        var textview: TextView = buttons.get(0)


//        printTexts(buttons)


    }

//    fun printTexts(textViews: List<in TextView>) {
//        for (text in textViews) {
//            println(text.text)
//        }
//    }

}