package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.ext.initClose
import com.sxy.jetpackdemo.app.ext.showMessage
import com.sxy.jetpackdemo.app.util.CacheUtil
import com.sxy.jetpackdemo.app.util.SettingUtil
import com.sxy.jetpackdemo.app.viewmodel.request.RequestLoginRegisterViewModel
import com.sxy.jetpackdemo.app.viewmodel.state.LoginRegisterViewModel
import com.sxy.jetpackdemo.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.ext.parseState

class RegisterFragment : BaseFragment<LoginRegisterViewModel,FragmentRegisterBinding>() {
    override fun layoutId(): Int  = R.layout.fragment_register
    val requestLoginRegisterViewModel : RequestLoginRegisterViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
       mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
        toolbar.initClose("注册") {
            nav().navigateUp()
        }
        //设置颜色跟主题颜色一致
        appViewModel.appColor.value?.let {
            SettingUtil.setShapColor(registerSub, it)
            toolbar.setBackgroundColor(it)
        }
    }

    override fun createObserver() {
        super.createObserver()
        requestLoginRegisterViewModel.loginResult.observe(viewLifecycleOwner, Observer {
                resultState ->
            parseState(resultState, {
                CacheUtil.setIsLogin(true)
                CacheUtil.setUser(it)
                appViewModel.userinfo.value = it
                nav().navigateAction(R.id.action_registerFrgment_to_mainFragment)
            }, {
                showMessage(it.errorMsg)
            })

        })
    }

    inner class ProxyClick {
        /**清空*/
        fun clear() {
            mViewModel.username.value=""
        }

        /**注册*/
        fun register() {
            when {
                mViewModel.username.value.isEmpty() -> showMessage("请填写账号")
                mViewModel.password.get().isEmpty() -> showMessage("请填写密码")
                mViewModel.password2.get().isEmpty() -> showMessage("请填写确认密码")
                mViewModel.password.get().length < 6 -> showMessage("密码最少6位")
                mViewModel.password.get() != mViewModel.password2.get() -> showMessage("密码不一致")
                else -> requestLoginRegisterViewModel.registerAndlogin(
                    mViewModel.username.value,
                    mViewModel.password.get()
                )
            }
        }

        var onCheckedChangeListener1 = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            mViewModel.isShowPwd.set(isChecked)
        }
        var onCheckedChangeListener2 = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            mViewModel.isShowPwd2.set(isChecked)
        }
    }
}