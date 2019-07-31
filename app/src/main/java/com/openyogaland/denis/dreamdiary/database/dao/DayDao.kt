package com.openyogaland.denis.dreamdiary.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openyogaland.denis.dreamdiary.model.Day

@Dao
interface
DayDao
{
  @Insert
  fun insert (day : Day)
  
  @Query("DELETE FROM day_table")
  fun deleteAll()
  
  @Query("SELECT * FROM day_table ORDER BY date ASC")
  fun getAllHabits() : List<Day>
}
