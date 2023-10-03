package com.sabirov.core_impl.network

import android.annotation.SuppressLint
import android.content.Context
import com.sabirov.core_impl.SessionKeeper
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionKeeper: SessionKeeper
) {
    //language settings
    private val gson = Gson()
    private val LOCALE_SETTINGS_DATA = "locale_settings_data"
    private val LOCALE = "locale"
    private val localeSettingsPrefs by lazy {
        context.getSharedPreferences(LOCALE_SETTINGS_DATA, Context.MODE_PRIVATE)
    }

    @SuppressLint("ApplySharedPref")
    fun getLanguage(): Locale {
        val localeString = localeSettingsPrefs.getString(LOCALE, null)
        return if (localeString != null) {
            gson.fromJson<Locale>(localeString, Locale::class.java)
        } else {
            val locale = Locale.getDefault()
            localeSettingsPrefs.edit().putString(LOCALE, gson.toJson(locale)).commit()
            locale
        }
    }

    @SuppressLint("ApplySharedPref")
    fun setLanguage(locale: Locale) {
        localeSettingsPrefs.edit().putString(LOCALE, gson.toJson(locale)).commit()
    }
}