package com.pd.currencyconverter.ui.settings

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.pd.currencyconverter.R
import com.pd.currencyconverter.utils.ConstantUtils
import com.pd.currencyconverter.utils.LocaleHelper

class SettingsFragment : PreferenceFragmentCompat() {

    private var mListPreferenceTheme: ListPreference? = null
    private var mListPreferenceLanguage: ListPreference? = null
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_preference_screen, rootKey)

        mListPreferenceTheme =
                preferenceManager.findPreference<Preference>("theme") as ListPreference?
        mListPreferenceLanguage =
            preferenceManager.findPreference<Preference>("language") as ListPreference?

        val currentTheme = PreferenceManager.getDefaultSharedPreferences(requireContext()).getInt(ConstantUtils.KEY_THEME, ConstantUtils.NORMAL)
        when(currentTheme){
            ConstantUtils.NORMAL -> mListPreferenceTheme?.setValueIndex(0)
            ConstantUtils.DARK -> mListPreferenceTheme?.setValueIndex(1)
            ConstantUtils.CHRISTMAS -> mListPreferenceTheme?.setValueIndex(2)
        }

        val currentLanguage = PreferenceManager.getDefaultSharedPreferences(requireContext()).getString(ConstantUtils.KEY_LANGUAGE, "en")
        when(currentLanguage){
            "en" -> mListPreferenceLanguage?.setValueIndex(0)
            "hi" -> mListPreferenceLanguage?.setValueIndex(1)
            "gu" -> mListPreferenceLanguage?.setValueIndex(2)
        }

        mListPreferenceTheme!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
                when (newValue) {
                    "Normal", "??????????????????", "?????????????????????" -> {
                        mListPreferenceTheme?.setDefaultValue("Normal")
                        switchTheme(ConstantUtils.NORMAL)
                    }
                    "Dark", "??????????????????", "???????????????" -> {
                        mListPreferenceTheme?.setDefaultValue("Dark")
                        switchTheme(ConstantUtils.DARK)
                    }
                    "Christmas", "?????????????????????", "?????????????????????" -> {
                        mListPreferenceTheme?.setDefaultValue("Christmas")
                        switchTheme(ConstantUtils.CHRISTMAS)
                    }
                }
            false
        }

        mListPreferenceLanguage!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->

            Log.e("TAG", "lang $newValue")
            when (newValue) {
                "English", "???????????????????????????", "????????????????????????" -> {
                    mListPreferenceLanguage?.setDefaultValue("English")
                    switchLanguage("en")
                }
                "Hindi" , "??????????????????", "??????????????????" -> {
                    mListPreferenceLanguage?.setDefaultValue("Hindi")
                    switchLanguage("hi")
                }
                "Gujarati", "?????????????????????", "?????????????????????" -> {
                    mListPreferenceLanguage?.setDefaultValue("Gujarati")
                    switchLanguage("gu")
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

    private fun switchLanguage(currentLanguage : String) {
        Log.e("TAG", currentLanguage)
        PreferenceManager.getDefaultSharedPreferences(requireActivity()).edit().putString(ConstantUtils.KEY_LANGUAGE, currentLanguage).apply()
        requireActivity().finish()
        startActivity(Intent(activity,activity?.javaClass))
    }
}