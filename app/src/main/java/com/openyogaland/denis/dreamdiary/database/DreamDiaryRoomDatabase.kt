package com.openyogaland.denis.dreamdiary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openyogaland.denis.dreamdiary.database.dao.DayDao
import com.openyogaland.denis.dreamdiary.database.dao.DreamDao
import com.openyogaland.denis.dreamdiary.database.dao.PracticeDao
import com.openyogaland.denis.dreamdiary.model.Day
import com.openyogaland.denis.dreamdiary.model.Dream
import com.openyogaland.denis.dreamdiary.model.Practice

@Database(entities =
          [Practice::class, Day::class, Dream::class],
          version = 1,
          exportSchema = true)
abstract class
DreamDiaryRoomDatabase : RoomDatabase()
{
  abstract fun practiceDao() : PracticeDao
  abstract fun dayDao() : DayDao
  abstract fun dreamDao() : DreamDao
  
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
            Room
            .databaseBuilder(context.applicationContext,
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
