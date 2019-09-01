package com.openyogaland.denis.dreamdiary.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.openyogaland.denis.dreamdiary.model.Dream
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface
DreamDao
{
  @Insert(onConflict = REPLACE)
  fun insertDream(dream : Dream) : Long
  
  @Update(onConflict = REPLACE)
  fun updateDream(dream : Dream)
  
  @Query("DELETE FROM dream_table")
  fun deleteAll()
  
  @Query("SELECT * FROM dream_table ORDER BY date ASC")
  fun getAllSingleList() : Single<List<Dream>>
  
  @Query("SELECT * FROM dream_table WHERE date LIKE :date")
  fun getDreamMaybe(date : String) : Maybe<Dream>
  
  @Query("SELECT * FROM dream_table WHERE date LIKE :date")
  fun getDream(date : String) : Dream?
}
