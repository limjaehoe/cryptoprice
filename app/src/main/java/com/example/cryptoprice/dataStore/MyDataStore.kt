package com.example.cryptoprice.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.cryptoprice.App

class MyDataStore {

    private val context = App.context()

    companion object{
        private val Context.dataStore :  DataStore<Preferences> by preferencesDataStore("user_pref")
    }

    private val mDataStore : DataStore<Preferences> = context.dataStore

    private val FIRST_FLAG = booleanPreferencesKey("FIRST_FLAG")

    //메인 액티비티로 갈때 TRUE 변경

    //처음 접속하는 유저가 아니면 -> TRUE
    //처음 접속하는 유저이면 -> FALSE

    //처음 접속하는 유저
    suspend fun setupFirstData(){
        mDataStore.edit { preferences ->
            preferences[FIRST_FLAG] = true
        }

    }

    suspend fun getFirstData() : Boolean{
        var currrentValue = false

        mDataStore.edit { preferences ->
            currrentValue = preferences[FIRST_FLAG] ?: false
        }
        return currrentValue

    }


}