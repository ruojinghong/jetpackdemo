package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment
import com.sxy.jetpackdemo.app.ext.hideSoftKeyboard
import com.sxy.jetpackdemo.app.ext.init
import com.sxy.jetpackdemo.app.ext.initClose
import com.sxy.jetpackdemo.app.ext.showMessage
import com.sxy.jetpackdemo.app.util.CacheUtil
import com.sxy.jetpackdemo.app.util.SettingUtil
import com.sxy.jetpackdemo.app.viewmodel.request.RequestLoginRegisterViewModel
import com.sxy.jetpackdemo.app.viewmodel.state.LoginRegisterViewModel
import com.sxy.jetpackdemo.app.viewmodel.state.MainViewModel
import com.sxy.jetpackdemo.databinding.FragmentLoginBinding
import com.sxy.jetpackdemo.databinding.FragmentLookinfoBinding
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.ext.parseState

/**
 * @author: sxy
 * @date: 2021/1/2
 * @description:
 */
class LoginFragment  : BaseFragment<LoginRegisterViewModel,FragmentLoginBinding>(){

    private val requestLoginRegisterViewModel: RequestLoginRegisterViewModel by viewModels()


    override fun layoutId(): Int = R.layout.fragment_login

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
        toolbar.initClose ("登录"){
            nav().navigateUp()
        }
        //设置颜色跟主题颜色一致
        appViewModel.appColor.value?.let {
            SettingUtil.setShapColor(loginSub, it)
            loginGoregister.setTextColor(it)
            toolbar.setBackgroundColor(it)
        }
    }

    override fun createObserver() {
        super.createObserver()
        requestLoginRegisterViewModel.
                loginResult.observe(viewLifecycleOwner, Observer { result ->
                    parseState(result,{
                        //登录成功，通知数据改变
                        CacheUtil.setUser(it)
//                        CacheUtil.setIsLogin(true)
                        appViewModel.userinfo.value = it
                        nav().navigateUp()
                    },{
                        //登录失败
                        showMessage(it.errorMsg)
                    })
        })

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