package com.sxy.jetpackdemo.app.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.content.SharedPreferencesCompat
import androidx.preference.PreferenceFragmentCompat
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.base.activity.BaseFragment

/**
 * @author: sxy
 * @date: 2021/2/3
 * @description:
 */
class SettingFragment : PreferenceFragmentCompat() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
                addPreferencesFromResource(R.xml.root_preference)
    }
}