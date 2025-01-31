package com.stalcraft.blackliststalcraft.presenter.setting

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.stalcraft.blackliststalcraft.R
import dagger.hilt.android.AndroidEntryPoint

class SettingActivity : AppCompatActivity() {
    private lateinit var defPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        defPref = PreferenceManager.getDefaultSharedPreferences(this)

        setContentView(R.layout.activity_setting)
        actionBarSetting()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.settingActivity, SettingFragment()).commit()
        }

    }

    private fun actionBarSetting() {
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}