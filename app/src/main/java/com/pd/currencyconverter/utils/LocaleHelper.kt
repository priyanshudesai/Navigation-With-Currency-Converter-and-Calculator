package com.pd.currencyconverter.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.preference.PreferenceManager
import java.util.*


class LocaleHelper {
    companion object {
        fun setLocale(context: Context) {
            var lang = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(ConstantUtils.KEY_LANGUAGE, "en")
            val locale = Locale(lang)
            Locale.setDefault(locale)
            val resources: Resources = context.resources
            val configuration: Configuration = resources.configuration
            configuration.setLocale(locale)

            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
    }
}