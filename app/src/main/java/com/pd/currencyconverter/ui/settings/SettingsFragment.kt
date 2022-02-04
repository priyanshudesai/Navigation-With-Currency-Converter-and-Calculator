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
                    "Normal", "साधारण", "સામાન્ય" -> {
                        mListPreferenceTheme?.setDefaultValue("Normal")
                        switchTheme(ConstantUtils.NORMAL)
                    }
                    "Dark", "अंधेरा", "શ્યામ" -> {
                        mListPreferenceTheme?.setDefaultValue("Dark")
                        switchTheme(ConstantUtils.DARK)
                    }
                    "Christmas", "क्रिसमस", "ક્રિસમસ" -> {
                        mListPreferenceTheme?.setDefaultValue("Christmas")
                        switchTheme(ConstantUtils.CHRISTMAS)
                    }
                }
            false
        }

        mListPreferenceLanguage!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->

            Log.e("TAG", "lang $newValue")
            when (newValue) {
                "English", "अंग्रेज़ी", "અંગ્રેજી" -> {
                    mListPreferenceLanguage?.setDefaultValue("English")
                    switchLanguage("en")
                }
                "Hindi" , "हिन्दी", "હિન્દી" -> {
                    mListPreferenceLanguage?.setDefaultValue("Hindi")
                    switchLanguage("hi")
                }
                "Gujarati", "गुजराती", "ગુજરાતી" -> {
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