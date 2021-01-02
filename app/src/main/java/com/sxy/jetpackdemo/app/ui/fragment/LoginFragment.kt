package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.ext.hideSoftKeyboard
import com.sxy.jetpackdemo.app.ext.showMessage
import com.sxy.jetpackdemo.app.viewmodel.request.RequestLoginRegisterViewModel
import com.sxy.jetpackdemo.app.viewmodel.state.LoginRegisterViewModel
import com.sxy.jetpackdemo.app.viewmodel.state.MainViewModel
import com.sxy.jetpackdemo.databinding.FragmentLookinfoBinding
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

/**
 * @author: sxy
 * @date: 2021/1/2
 * @description:
 */
class LoginFragment  : BaseFragment<LoginRegisterViewModel,FragmentLookinfoBinding>(){

    private val requestLoginRegisterViewModel: RequestLoginRegisterViewModel by viewModels()


    override fun layoutId(): Int = R.layout.fragment_login

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
    inner class ProxyClick {

        fun clear() {
            mViewModel.username.value = ""
        }

        fun login() {
            when {
                mViewModel.username.value.isEmpty() -> showMessage("请填写账号")
                mViewModel.password.get().isEmpty() -> showMessage("请填写密码")
                else -> requestLoginRegisterViewModel.loginReq(
                    mViewModel.username.value,
                    mViewModel.password.get()
                )
            }
        }

        fun goRegister() {
            hideSoftKeyboard(activity)
            nav().navigateAction(R.id.action_mainfragment_to_LoginRegisterFragment)
        }

        var onCheckedChangeListener =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                mViewModel.isShowPwd.set(isChecked)
            }
    }

}