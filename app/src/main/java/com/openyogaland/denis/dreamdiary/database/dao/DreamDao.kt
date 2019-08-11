package com.openyogaland.denis.dreamdiary.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.openyogaland.denis.dreamdiary.model.Dream
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface
DreamDao
{
  @Insert(onConflict = REPLACE)
  fun insert(dream : Dream)
  
  @Query("DELETE FROM dream_table")
  fun deleteAll()
  
  @Query("SELECT * FROM dream_table ORDER BY dateTextView ASC")
  fun getAll() : Single<List<Dream>>
  
  @Query("SELECT * FROM dream_table WHERE dream_id = :dreamId")
  fun getDream(dreamId : Long) : Maybe<Dream>
}
