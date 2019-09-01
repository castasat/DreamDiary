package com.openyogaland.denis.dreamdiary.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "dream_table")
@Parcelize
data class
Dream(
  
  @PrimaryKey
  @ColumnInfo(name = "date")
  var date : String = "",
  
  @ColumnInfo(name = "moon_phase_day")
  var moonPhaseDay : String = "",
  
  @ColumnInfo(name = "practice")
  var practice : String = "",
  
  @ColumnInfo(name = "dream_description")
  var dreamDescription : String = "",
  
  @ColumnInfo(name = "anchor")
  var anchor : String = "",
  
  @ColumnInfo(name = "dream_duration")
  var dreamDuration : String = "",
  
  @ColumnInfo(name = "lucid_dream")
  var lucidDream : Boolean = false,
  
  @ColumnInfo(name = "emotions")
  var emotions : String = ""
) : Parcelable
