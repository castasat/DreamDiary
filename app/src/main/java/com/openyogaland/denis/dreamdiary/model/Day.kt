package com.openyogaland.denis.dreamdiary.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "day_table")
@Parcelize
data class
Day(
  @PrimaryKey
  @ColumnInfo(name = "date")
  var date : String = "",
  
  @ColumnInfo(name = "moon_phase_day")
  var moonPhaseDay : String = "",
  
  @ColumnInfo(name = "cycle_day")
  var cycleDay : String = "",
  
  @ColumnInfo(name = "practice_type")
  var practiceType : String = "",
  
  @ColumnInfo(name = "practice_duration_minutes")
  var practiceDurationMinutes : String = "",
  
  @ColumnInfo(name = "nutrition")
  var nutrition : String = "",
  
  @ColumnInfo(name = "events")
  var events : String = "",
  
  @ColumnInfo(name = "stress_level")
  var stressLevel : String = ""
) : Parcelable
