package com.pd.currencyconverter.ui.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.pd.currencyconverter.R
import com.pd.currencyconverter.utils.ConstantUtils

class SettingsFragment : PreferenceFragmentCompat() {

    private var mListPreference: ListPreference? = null
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_preference_screen, rootKey)

        mListPreference =
                preferenceManager.findPreference<Preference>("theme") as ListPreference?

        val currentTheme = PreferenceManager.getDefaultSharedPreferences(requireContext()).getInt(ConstantUtils.KEY_THEME, ConstantUtils.NORMAL)
        when(currentTheme){
            ConstantUtils.NORMAL -> mListPreference?.setValueIndex(0)
            ConstantUtils.DARK -> mListPreference?.setValueIndex(1)
            ConstantUtils.CHRISTMAS -> mListPreference?.setValueIndex(2)
        }

        mListPreference!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
                when (newValue) {
                    "Normal" -> {
                        mListPreference?.setDefaultValue("Normal")
                        switchTheme(ConstantUtils.NORMAL)
                    }
                    "Dark" -> {
                        mListPreference?.setDefaultValue("Dark")
                        switchTheme(ConstantUtils.DARK)
                    }
                    "Christmas" -> {
                        mListPreference?.setDefaultValue("Christmas")
                        switchTheme(ConstantUtils.CHRISTMAS)
                    }
                }
            false
        }
    }

    private fun switchTheme(currentTheme : Int) {
        Log.e("TAG", currentTheme.toString())
        PreferenceManager.getDefaultSharedPreferences(requireActivity()).edit().putInt(ConstantUtils.KEY_THEME, currentTheme).apply()
        requireActivity().finish()
        startActivity(Intent(activity,activity?.javaClass))
    }
}