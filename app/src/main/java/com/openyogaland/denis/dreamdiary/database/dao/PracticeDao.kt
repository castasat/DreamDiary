package com.openyogaland.denis.dreamdiary.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openyogaland.denis.dreamdiary.model.Day
import com.openyogaland.denis.dreamdiary.model.Practice

@Dao
interface
PracticeDao
{
  @Insert
  fun insert(practice : Practice)
  
  @Query("DELETE FROM practice_table")
  fun deleteAll()
  
  @Query("SELECT * FROM practice_table")
  fun getAll() : List<Practice>
}
