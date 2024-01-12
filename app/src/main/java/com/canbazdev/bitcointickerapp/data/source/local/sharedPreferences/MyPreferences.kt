package com.canbazdev.bitcointickerapp.data.source.local.sharedPreferences

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

//    var userName: String?
//        get() =
//            sharedPreferences.getString(MyPreferencesConfig.CURRENT_USER_NAME, "Name not found")
//        set(userName) {
//            editor.putString(MyPreferencesConfig.CURRENT_USER_NAME, userName)
//            editor.apply()
//        }

}