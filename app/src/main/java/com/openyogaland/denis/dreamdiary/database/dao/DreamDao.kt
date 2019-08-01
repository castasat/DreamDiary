package com.openyogaland.denis.dreamdiary.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openyogaland.denis.dreamdiary.model.Day
import com.openyogaland.denis.dreamdiary.model.Dream

@Dao
interface
DreamDao
{
  @Insert
  fun insert(dream : Dream)
  
  @Query("DELETE FROM dream_table")
  fun deleteAll()
  
  @Query("SELECT * FROM dream_table ORDER BY date ASC")
  fun getAll() : List<Dream>
}
