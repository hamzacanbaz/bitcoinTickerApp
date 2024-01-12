package com.canbazdev.bitcointicker.data.local.sharedPreferences

import android.content.Context
import android.content.SharedPreferences


class MyPreferences(val context: Context) {

    private var myPreferences: MyPreferences? = null
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(
        MyPreferencesConfig.SHARED_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun getPreferences(): MyPreferences? {
        if (myPreferences == null) myPreferences = MyPreferences(context)
        return myPreferences
    }

    init {
        editor.apply()
    }

    var userName: String?
        get() =
            sharedPreferences.getString(MyPreferencesConfig.CURRENT_USER_NAME, "Name not found")
        set(userName) {
            editor.putString(MyPreferencesConfig.CURRENT_USER_NAME, userName)
            editor.apply()
        }
    var userEmail: String?
        get() =
            sharedPreferences.getString(MyPreferencesConfig.CURRENT_USER_EMAIL, "Email not found")
        set(userEmail) {
            editor.putString(MyPreferencesConfig.CURRENT_USER_EMAIL, userEmail)
            editor.apply()
        }
    var userUid: String?
        get() =
            sharedPreferences.getString(MyPreferencesConfig.CURRENT_USER_UID, "Uid not found")
        set(userUid) {
            editor.putString(MyPreferencesConfig.CURRENT_USER_UID, userUid)
            editor.apply()
        }

    var selectedCategory: Int
        get() =

            sharedPreferences.getInt(MyPreferencesConfig.SELECTED_CATEGORY, 0)
        set(selectedCategory){
            editor.putInt(MyPreferencesConfig.SELECTED_CATEGORY, selectedCategory)
            editor.apply()
        }
}