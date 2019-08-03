package com.openyogaland.denis.dreamdiary.viewmodel

import android.app.Application
import com.openyogaland.denis.dreamdiary.database.dao.DayDao
import com.openyogaland.denis.dreamdiary.database.dao.PracticeDao

class
DayViewModel(application : Application)
  : BaseViewModel(application)
{
  // room database fields
  private lateinit var practiceDao : PracticeDao
  private lateinit var dayDao : DayDao
  
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
  
    practiceDao = dreamDiaryRoomDatabase.practiceDao()
    dayDao = dreamDiaryRoomDatabase.dayDao()
  }
}
