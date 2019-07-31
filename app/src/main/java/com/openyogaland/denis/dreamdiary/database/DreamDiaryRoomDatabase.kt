package com.openyogaland.denis.dreamdiary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openyogaland.denis.dreamdiary.database.dao.DayDao
import com.openyogaland.denis.dreamdiary.model.Day

@Database(entities = [Day::class],
          version = 1,
          exportSchema = true)
abstract class
DreamDiaryRoomDatabase : RoomDatabase()
{
  abstract fun dayDao() : DayDao
  
  companion object
  {
    private var INSTANCE : DreamDiaryRoomDatabase? = null
    
    fun
    getDatabase(context : Context) : DreamDiaryRoomDatabase?
    {
      if(INSTANCE == null)
      {
        synchronized(DreamDiaryRoomDatabase::class) {
          INSTANCE =
            Room.databaseBuilder(context.applicationContext,
                                 DreamDiaryRoomDatabase::class.java,
                                 "dream_diary_database"
          )
          .build()
        }
      }
      return INSTANCE
    }
  }
}
