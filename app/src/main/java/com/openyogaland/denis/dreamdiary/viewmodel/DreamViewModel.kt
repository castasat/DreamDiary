package com.openyogaland.denis.dreamdiary.viewmodel

import android.app.Application
import com.openyogaland.denis.dreamdiary.database.dao.DreamDao

class DreamViewModel(application : Application)
  : BaseViewModel(application)
{
  // room database fields
  private lateinit var dreamDao : DreamDao
  
  init
  {
    initializeApplicationContext(application)
    initializeRoomDatabase()
  }
  
  override fun
  initializeRoomDatabase()
  {
    super
    .initializeRoomDatabase()
    dreamDao = dreamDiaryRoomDatabase.dreamDao()
  }
}
