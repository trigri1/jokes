package com.test.jokes.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.test.data.local.db.AppDataBase
import com.test.data.local.prefs.PrefsHelper
import com.test.data.local.prefs.PrefsHelperImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private const val DB_NAME = "jokes_db"
private const val PREFS_NAME = "jokes_prefs"

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDataBase(context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, DB_NAME).build()
    }

    @Singleton
    @Provides
    fun providePrefHelper(prefsHelper: PrefsHelperImpl): PrefsHelper {
        return prefsHelper
    }

    @Singleton
    @Provides
    fun provideSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

}