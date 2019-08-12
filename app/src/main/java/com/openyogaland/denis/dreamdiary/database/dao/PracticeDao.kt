package com.openyogaland.denis.dreamdiary.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.openyogaland.denis.dreamdiary.model.Practice
import io.reactivex.Single

@Dao
interface
PracticeDao
{
  @Insert(onConflict = REPLACE)
  fun insert(practice : Practice)
  
  @Query("DELETE FROM practice_table")
  fun deleteAll()
  
  @Query("SELECT * FROM practice_table")
  fun getAll() : Single<List<Practice>>
}
