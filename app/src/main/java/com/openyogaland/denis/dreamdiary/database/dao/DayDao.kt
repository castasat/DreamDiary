package com.openyogaland.denis.dreamdiary.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.openyogaland.denis.dreamdiary.model.Day
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface
DayDao
{
  @Insert(onConflict = REPLACE)
  fun insertDay (day : Day)
  
  @Update(onConflict = REPLACE)
  fun updateDay (day : Day)
  
  @Query("DELETE FROM day_table")
  fun deleteAll()
  
  @Query("SELECT * FROM day_table ORDER BY date ASC")
  fun getAllSingleList() : Single<List<Day>>
  
  @Query("SELECT * FROM day_table WHERE date LIKE :date")
  fun getDayMaybe(date : String) : Maybe<Day>
  
  @Query("SELECT * FROM day_table WHERE date LIKE :date")
  fun getDay(date : String) : Day?
}
