package com.dicoding.rifqi.githubuserapp3

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.rifqi.githubuserapp3.setting.SettingPreferences
import kotlinx.coroutines.launch

class ThemeViewModel(private val preferences: SettingPreferences) : ViewModel() {

    fun setThemeMode(isNightMode: Boolean) {
        viewModelScope.launch { preferences.saveThemeSetting(isNightMode) }
    }

    fun getThemeMode(): LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }
}