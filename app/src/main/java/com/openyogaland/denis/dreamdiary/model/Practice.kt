package com.openyogaland.denis.dreamdiary.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "practice_table")
@Parcelize
data class Practice
(
  @PrimaryKey
  @ColumnInfo(name = "practice_id")
  var practiceId : Long,
  
  @ColumnInfo(name = "practice_type")
  var practiceType : String
) : Parcelable
