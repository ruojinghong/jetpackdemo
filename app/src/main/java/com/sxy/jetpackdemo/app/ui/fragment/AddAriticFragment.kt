package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.ext.initClose
import com.sxy.jetpackdemo.app.ext.showMessage
import com.sxy.jetpackdemo.app.util.SettingUtil
import com.sxy.jetpackdemo.app.viewmodel.request.RequestAriticleViewModel
import com.sxy.jetpackdemo.app.viewmodel.state.AriticleViewModel
import com.sxy.jetpackdemo.databinding.FragmentAddAriticBinding
import kotlinx.android.synthetic.main.fragment_add_aritic.*
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.parseState
import me.hgj.jetpackmvvm.ext.view.clickNoRepeat

/**
 * @author: sxy
 * @date: 2021/1/21
 * @description:
 */
class AddAriticFragment
    : BaseFragment<AriticleViewModel,FragmentAddAriticBinding>() {
    val requestViewModel: RequestAriticleViewModel by viewModels()


    override fun layoutId(): Int = R.layout.fragment_add_aritic

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        appViewModel.userinfo.value?.let {
            if (it.nickname.isEmpty()) mViewModel.shareName.set(it.username) else mViewModel.shareName.set(
                it.nickname
            )
        }
        appViewModel.appColor.value?.let { SettingUtil.setShapColor(share_submit, it) }
        toolbar.run {
            initClose ("分享文章"){
                nav().navigateUp()
            }
            inflateMenu(R.menu.share_menu)
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.share_guize ->{
                        requireActivity()?.let {activity ->
                                    MaterialDialog(context,BottomSheet())
                                        .lifecycleOwner(viewLifecycleOwner)
                                        .show {
                                            title(text = "温馨提示")
                                            customView(
                                                R.layout.customview,
                                                scrollable = true,
                                                horizontalPadding = true
                                            )
                                            positiveButton (text = "知道了")
                                            cornerRadius(16f)
                                            getActionButton(WhichButton.POSITIVE).updateTextColor(
                                                SettingUtil.getColor(activity)
                                            )
                                            getActionButton(WhichButton.NEGATIVE).updateTextColor(
                                                SettingUtil.getColor(activity)
                                            )
                                        }
                        }
                    }
                }
                true
            }
        }

        share_submit.clickNoRepeat {
            when {
                mViewModel.shareTitle.get().isEmpty() -> {
                    showMessage("请填写文章标题")
                }
                mViewModel.shareUrl.get().isEmpty() -> {
                    showMessage("请填写文章链接")
                }
                else -> {
                    showMessage("确定分享吗？", positiveButtonText = "分享", positiveAction = {
                        requestViewModel.addAriticle(
                            mViewModel.shareTitle.get(),
                            mViewModel.shareUrl.get()
                        )
                    }, negativeButtonText = "取消")
                }
            }
        }
    }

    override fun createObserver() {
        requestViewModel.addData.observe(viewLifecycleOwner, Observer { resultState ->
            parseState(resultState, {
                eventViewModel.shareArticleEvent.value = true
                nav().navigateUp()
            }, {
                showMessage(it.errorMsg)
            })
        })
    }
}