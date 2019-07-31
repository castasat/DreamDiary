package com.openyogaland.denis.dreamdiary.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.openyogaland.denis.dreamdiary.database.DreamDiaryRoomDatabase
import com.openyogaland.denis.dreamdiary.database.dao.DayDao
import java.lang.ref.WeakReference

class ActivityViewModel(application : Application)
  : AndroidViewModel(application)
{
  // context fields
  private lateinit var contextWeakReference : WeakReference<Context>
  private val applicationContext : Context? get() = contextWeakReference.get()
  
  // room database fields
  private lateinit var dreamDiaryRoomDatabase : DreamDiaryRoomDatabase
  private lateinit var dayDao : DayDao
  
  init
  {
    initializeApplicationContext(application)
    initializeRoomDatabase()
  }
  
  private fun
  initializeApplicationContext(application : Application) : Context
  {
    contextWeakReference = WeakReference(application.applicationContext)
    return application
    .applicationContext
  }
  
  private fun
  initializeRoomDatabase()
  {
    applicationContext
    ?.let {applicationContext : Context ->
      
      DreamDiaryRoomDatabase
      .getDatabase(applicationContext)
     
      ?.let {dreamDiaryRoomDatabase : DreamDiaryRoomDatabase ->
        this.dreamDiaryRoomDatabase = dreamDiaryRoomDatabase
      }
      
      dayDao = dreamDiaryRoomDatabase.dayDao()
    }
  }
}
