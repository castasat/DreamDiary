package com.openyogaland.denis.dreamdiary.viewmodel

import android.app.Application

class DayViewModel(application : Application): BaseViewModel(application)
{
  init
  {
    initializeApplicationContext(application)
    initializeRoomDatabase()
  }
}
