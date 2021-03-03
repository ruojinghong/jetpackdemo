package com.sxy.jetpackdemo.app.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.viewModelScope
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.color.colorChooser
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.event.AppViewModel
import com.sxy.jetpackdemo.app.data.model.bean.BannerResponse
import com.sxy.jetpackdemo.app.ext.initClose
import com.sxy.jetpackdemo.app.ext.showMessage
import com.sxy.jetpackdemo.app.network.NetworkApi
import com.sxy.jetpackdemo.app.util.CacheDataManager
import com.sxy.jetpackdemo.app.util.CacheUtil
import com.sxy.jetpackdemo.app.util.ColorUtils
import com.sxy.jetpackdemo.app.util.SettingUtil
import com.sxy.jetpackdemo.app.weight.preference.CheckBoxPreference
import com.sxy.jetpackdemo.app.weight.preference.IconPreference
import com.sxy.jetpackdemo.app.weight.preference.PreferenceCategory
import com.tencent.bugly.beta.Beta
import kotlinx.coroutines.runBlocking
import me.hgj.jetpackmvvm.ext.getAppViewModel
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.network.NetworkUtil.url
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import kotlin.concurrent.thread


/**
 * @author: sxy
 * @date: 2021/2/3
 * @description:
 */
class SettingFragment : PreferenceFragmentCompat()
    ,SharedPreferences.OnSharedPreferenceChangeListener {

    //这里不能继承BaseFragment了，所以手动获取一下 AppViewModel
    val shareViewModel: AppViewModel by lazy { getAppViewModel<AppViewModel>() }
    private var colorPreview: IconPreference? = null
    var toolbarView: View? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //这里重写根据PreferenceFragmentCompat 的布局 ，往他的根布局插入了一个toolbar
        val containerView = view.findViewById<FrameLayout>(android.R.id.list_container)
        containerView.let {
                var linearLayout = it.parent as? LinearLayout
                linearLayout?.run {
                    toolbarView = LayoutInflater.from(context).inflate(
                        R.layout.include_toolbar,
                        null
                    )
                    toolbarView?.let {

                        it.findViewById<Toolbar>(R.id.toolbar)?.initClose("设置"){
                                nav().navigateUp()
                        }
                    }
                    addView(toolbarView, 0)
                }
        }
        val client = OkHttpClient().newBuilder()
            .build()
//            thread {
////                for (i in 984141131..1000000000) {
//                for (i in 984147298..1000000000) {
//                    val request: Request = Request.Builder()
//                        .url("http://8.210.46.21:9090/voice/6000000${i}.mp3")
//                        .method("GET", null)
//                        .build()
//                    val response: Response = client.newCall(request).execute()
//                    if (response.code() == 200) {
//                        Log.i("hahahhaa", i.toString())
//                    }
//                }
//            }



//        thread {
////                for (i in 2128986517..1000000000) {
//            for (i in 2128986500..1000000000) {
//                val request: Request = Request.Builder()
//                    .url("http://8.210.46.21:9090/voice/6000000${i}.mp3")
//                    .method("GET", null)
//                    .build()
//                val response: Response = client.newCall(request).execute()
//                if (response.code() == 200) {
//                    Log.i("hahahhaa", i.toString())
//                }
//            }
//        }

//        thread {
////                for (i in 2128986517..1000000000) {
//            for (i in 4318491710..1000000000) {
//                val request: Request = Request.Builder()
//                    .url("http://8.210.46.21:9090/voice/6000000${i}.wav")
//                    .method("GET", null)
//                    .build()
//                val response: Response = client.newCall(request).execute()
//                if (response.code() == 200) {
//                    Log.i("hahahhaa", i.toString())
//                }
//            }
//        }

        thread {
//                for (i in 2128986517..1000000000) {
            for (i in 818239513..1000000000) {
                val request: Request = Request.Builder()
                    .url("http://8.210.46.21:9090/voice/6000000${i}.mp3")
                    .method("GET", null)
                    .build()
                val response: Response = client.newCall(request).execute()
                if (response.code() == 200) {
                    Log.i("hahahhaa", i.toString())
                }
            }
        }


    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
                addPreferencesFromResource(R.xml.root_preference)

        colorPreview = findPreference("color")
        setText()
        findPreference<Preference>("exit")?.isVisible = CacheUtil.isLogin()
        findPreference<Preference>("exit")?.setOnPreferenceClickListener {
            showMessage(
                "确定退出登录吗",
                positiveButtonText = "退出",
                negativeButtonText = "取消",
                positiveAction = {
                    //清空cookie
                    NetworkApi.INSTANCE.cookieJar.clear()
                    CacheUtil.setUser(null)
                    shareViewModel.userinfo.value = null
                    nav().navigateUp()
                })
            false
        }

        findPreference<Preference>("clearCache")?.setOnPreferenceClickListener {
            showMessage(
                "确定清理缓存吗",
                positiveButtonText = "清理",
                negativeButtonText = "取消",
                positiveAction = {
                    activity?.let { CacheDataManager.clearAllCache(it as? AppCompatActivity) }
                    setText()
                })
            false
        }
        findPreference<Preference>("mode")?.setOnPreferenceClickListener {
            activity?.let { activity ->
                MaterialDialog(activity).show {
                    cancelable(false)
                    lifecycleOwner(viewLifecycleOwner)
                    listItemsSingleChoice(
                        R.array.setting_modes,
                        initialSelection = SettingUtil.getListMode()
                    ) { dialog, index, text ->
                        SettingUtil.setListMode(index)
                        it.summary = text
                        //通知其他界面立马修改配置
                        shareViewModel.appAnimation.value = index
                    }
                    title(text = "设置列表动画")
                    positiveButton(R.string.confirm)
                    negativeButton(R.string.cancel)
                    getActionButton(WhichButton.POSITIVE).updateTextColor(
                        SettingUtil.getColor(
                            activity
                        )
                    )
                    getActionButton(WhichButton.NEGATIVE).updateTextColor(
                        SettingUtil.getColor(
                            activity
                        )
                    )
                }
            }

            false
        }
        findPreference<IconPreference>("color")?.setOnPreferenceClickListener {
            activity?.let { activity ->
                MaterialDialog(activity).show {
                    title(R.string.choose_theme_color)
                    colorChooser(
                        ColorUtils.ACCENT_COLORS,
                        initialSelection = SettingUtil.getColor(activity),
                        subColors = ColorUtils.PRIMARY_COLORS_SUB
                    ) { dialog, color ->
                        ///修改颜色
                        SettingUtil.setColor(activity, color)
                        findPreference<PreferenceCategory>("base")?.setTitleColor(color)
                        findPreference<PreferenceCategory>("other")?.setTitleColor(color)
                        findPreference<PreferenceCategory>("about")?.setTitleColor(color)
                        findPreference<CheckBoxPreference>("top")?.setBottonColor()
                        toolbarView?.setBackgroundColor(color)
                        //通知其他界面立马修改配置
                        shareViewModel.appColor.value = color
                    }
                    getActionButton(WhichButton.POSITIVE).updateTextColor(
                        SettingUtil.getColor(
                            activity
                        )
                    )
                    getActionButton(WhichButton.NEGATIVE).updateTextColor(
                        SettingUtil.getColor(
                            activity
                        )
                    )
                    positiveButton(R.string.done)
                    negativeButton(R.string.cancel)
                }
            }
            false
        }
        findPreference<Preference>("version")?.setOnPreferenceClickListener {
            Beta.checkUpgrade(true, false)
            false
        }
        findPreference<Preference>("copyRight")?.setOnPreferenceClickListener {
            activity?.let {
                showMessage(it.getString(R.string.copyright_tip))
            }
            false
        }
        findPreference<Preference>("author")?.setOnPreferenceClickListener {
            showMessage(
                title = "联系作者",
                message = "扣　扣：823945570\n\n邮　箱：823945570@qq.com"
            )
            false
        }
        findPreference<Preference>("project")?.setOnPreferenceClickListener {
            val data = BannerResponse(
                title = "一位练习时长两年半的菜虚鲲制作的玩安卓App",
                url = findPreference<Preference>("project")?.summary.toString()
            )
            view?.let {
                nav().navigateAction(R.id.action_to_webFragment, Bundle()
                    .apply { putParcelable("bannerdata", data) })
            }
            false
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == "color") {
            colorPreview?.setView()
        }
        if (key == "top") {
            CacheUtil.setIsNeedTop(sharedPreferences.getBoolean("top", true))
        }
    }

    /**
     * 初始化设值
     */
    private fun setText() {
        requireActivity()?.let {

            findPreference<CheckBoxPreference>("top")?.isChecked = CacheUtil.isNeedTop()

            findPreference<Preference>("clearCache")?.summary =
                CacheDataManager.getTotalCacheSize(it)

            findPreference<Preference>("version")?.summary = "当前版本 " + AppUtils.getAppVersionName()

            val modes = it.resources.getStringArray(R.array.setting_modes)
            findPreference<Preference>("mode")?.summary =
                modes[SettingUtil.getListMode()]
        }
    }
}