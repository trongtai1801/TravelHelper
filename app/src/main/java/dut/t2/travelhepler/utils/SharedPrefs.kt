package dut.t2.travelhepler.utils

import android.content.SharedPreferences
import dut.t2.travelhepler.App

class SharedPrefs private constructor() {
    private var mSharedPreferences: SharedPreferences? = null

    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        mSharedPreferences = sharedPreferences
    }

    operator fun <T> get(key: String, anonymousClass: Class<T>): T? {
        if (anonymousClass == String::class.java) {
            return mSharedPreferences!!.getString(key, "") as T
        } else if (anonymousClass == Boolean::class.java) {
            return java.lang.Boolean.valueOf(mSharedPreferences!!.getBoolean(key, true)) as T
        } else if (anonymousClass == Float::class.java) {
            return java.lang.Float.valueOf(mSharedPreferences!!.getFloat(key, 0f)) as T
        } else if (anonymousClass == Int::class.java) {
            return Integer.valueOf(mSharedPreferences!!.getInt(key, 0)) as T
        } else if (anonymousClass == Long::class.java) {
            return java.lang.Long.valueOf(mSharedPreferences!!.getLong(key, 0)) as T
        }
        return null
    }

    fun <T> put(key: String, data: T) {
        val editor = mSharedPreferences!!.edit()
        if (data is String) {
            editor.putString(key, data as String)
        } else if (data is Boolean) {
            editor.putBoolean(key, data as Boolean)
        } else if (data is Float) {
            editor.putFloat(key, data as Float)
        } else if (data is Int) {
            editor.putInt(key, data as Int)
        } else if (data is Long) {
            editor.putLong(key, data as Long)
        }
        editor.apply()
    }

    fun clear() {
        mSharedPreferences!!.edit().clear().apply()
    }

    companion object {
        private lateinit var mInstance: SharedPrefs

        fun getInstance(): SharedPrefs {
            return Holder.INSTANCE

        }
    }

    private object Holder {
        val INSTANCE = SharedPrefs()
    }
}