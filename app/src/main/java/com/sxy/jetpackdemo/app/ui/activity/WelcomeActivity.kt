package com.sxy.jetpackdemo.app.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseActivity
import com.sxy.jetpackdemo.app.util.CacheUtil
import com.sxy.jetpackdemo.app.util.SettingUtil
import com.sxy.jetpackdemo.app.weight.banner.WelcomeBannerAdapter
import com.sxy.jetpackdemo.app.weight.banner.WelcomeBannerViewHolder
import com.sxy.jetpackdemo.databinding.ActivityWelcomeBinding
import com.zhpan.bannerview.BannerViewPager
import kotlinx.android.synthetic.main.activity_welcome.*
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.view.gone
import me.hgj.jetpackmvvm.ext.view.visible

/**
 * @author: sxy
 * @date: 2020/12/17
 * @description: 欢迎页
 */
class WelcomeActivity :BaseActivity<BaseViewModel,ActivityWelcomeBinding>(){

    var resList = arrayListOf<String>("1","2","3")
    private lateinit var mViewPager: BannerViewPager<String, WelcomeBannerViewHolder>

    override fun layoutId(): Int = R.layout.activity_welcome

    override fun initView(savedInstanceState: Bundle?) {
        //防止出现按Home键回到桌面时，再次点击重新进入该界面bug
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT !== 0) {
            finish()
            return
        }
        mDatabind.click = ProxyClick()
        welcome_baseview.setBackgroundColor(SettingUtil.getColor(this))
        mViewPager = findViewById(R.id.banner_view)
        if(CacheUtil.isFirst()){
            //是第一次打开APP 显示引导页
            welcome_image.gone()

        mViewPager.apply {
                 adapter = WelcomeBannerAdapter()
                setLifecycleRegistry(lifecycle)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    if(position == resList.size-1){
                        welcomeJoin.visible()
                    }else{
                        welcomeJoin.gone()
                    }

                }


            })
            create(resList)
        }

        }else
        {
            //不是第一次打开App 0.3秒后自动跳转到主页
            welcome_image.visible()
            mViewPager.postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                //带点渐变动画
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }, 300)
        }
    }

    inner  class ProxyClick{
        fun toMain(){
            CacheUtil.setFirst(false)
            startActivity(Intent(this@WelcomeActivity,MainActivity::class.java))
            finish()
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }


    }
}