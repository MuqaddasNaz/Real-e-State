package com.gulfrealestates.realestate.Utills

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import com.gulfrealestates.realestate.Models.Property
import com.gulfrealestates.realestate.Models.Users
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gulfrealestates.realestate.Models.Register

class SharedPref(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)

    fun save(key: String, value: String) {
        with(preferences.edit()) {
            putString(key, value)
            commit()
        }
    }

    fun putBoolean(key: String?, value: Boolean?) {
        val editor = preferences.edit()
        editor.putBoolean(key, value!!)
        editor.apply()
    }

    fun getBoolean(key: String?): Boolean {
        return preferences.getBoolean(key, false)
    }


    fun get(key: String, defaultValue: String = ""): String =
        preferences.getString(key, defaultValue) ?: defaultValue

    fun saveInt(key: String, value: Int) {
        with(preferences.edit()) {
            putInt(key, value)
            commit()
        }
    }


    fun getInt(key: String, defaultValue: Int = 0): Int =
        preferences.getInt(key, defaultValue)

    fun saveImg(key: String, image: ByteArray) {
        val imgStr = Base64.encodeToString(image, Base64.DEFAULT)
        with(preferences.edit()) {
            putString(key, imgStr)
            commit()
        }
    }

    fun getFavoritesList(): ArrayList<Property>? {
        val jsonString = preferences.getString("favorites", "")
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Property?>?>() {}.type
        return gson.fromJson<ArrayList<Property>>(jsonString, type)
    }

    fun removeFavorite(item: Property) {
        val favoritesList = getFavoritesList()
        favoritesList?.remove(item)
        val gson = Gson()
        val jsonString = gson.toJson(favoritesList)
        val editor = preferences.edit()
        editor.putString("favorites", jsonString)
        editor.apply()
    }

    fun saveToFav(item: Property){
        val favoritesList = getFavoritesList() ?: ArrayList()
        favoritesList.add(item)
        val gson = Gson()
        val jsonString = gson.toJson(favoritesList)
        val editor = preferences.edit()
        editor.putString("favorites", jsonString)
        editor.apply()

    }
    fun getImg(key: String): ByteArray {
        val data = get(key, "")
        return Base64.decode(data, Base64.DEFAULT)
    }

    fun clear() {
        with(preferences.edit()) {
            clear()
            commit()
        }
    }

    fun getProfile(): Users? {
        val data = get("users")
        return if (data.isEmpty()) null else Gson().fromJson(data, Users::class.java)
    }

    fun getUsers(): Users? {
        // Yahaan par users ko retrieve karne ka code add karein
        return getProfile()
    }


    fun clearPreferences() {
        clear()
    }


    fun saveUsers(user: Users) {
        val editor = preferences.edit()
        editor.putString("users", Gson().toJson(user))
        editor.apply()
    }

    companion object {
        private const val PREF_KEY = "YourPrefKey"
    }
}
