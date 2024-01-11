package com.stalcraft.blackliststalcraft.presenter.setting

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.stalcraft.blackliststalcraft.R
import dagger.hilt.android.AndroidEntryPoint

class SettingFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_preference, rootKey)
        val currentTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val listPreferenceTextSize = findPreference<ListPreference>("text_size_key") as ListPreference
//        val listPreferenceVictorineClue = findPreference<ListPreference>("victorine_clue_key") as ListPreference
        if (currentTheme == Configuration.UI_MODE_NIGHT_YES) {
            listPreferenceTextSize.setIcon(R.drawable.ic_text_light)
                //   listPreferenceVictorineClue.setIcon(R.drawable.ic_clue_light)
        } else {
            //listPreferenceVictorineClue.setIcon(R.drawable.ic_clue)
            listPreferenceTextSize.setIcon(R.drawable.ic_text_night)
        }
    }
}