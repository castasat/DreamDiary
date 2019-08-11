package com.openyogaland.denis.dreamdiary.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.openyogaland.denis.dreamdiary.model.Day
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface
DayDao
{
  @Insert(onConflict = REPLACE)
  fun insert (day : Day) : Long
  
  @Query("DELETE FROM day_table")
  fun deleteAll()
  
  @Query("SELECT * FROM day_table ORDER BY date ASC")
  fun getAll() : Single<List<Day>>
  
  @Query("SELECT * FROM day_table WHERE day_id = :dayId")
  fun getDay(dayId : Long) : Maybe<Day>
}
