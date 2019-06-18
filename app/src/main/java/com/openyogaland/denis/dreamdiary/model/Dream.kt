package com.openyogaland.denis.dreamdiary.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator

public class
Dream() : Parcelable
{
  companion object CREATOR : Creator<Dream>
  {
    override fun createFromParcel(parcel : Parcel) : Dream
    {
      return Dream(parcel)
    }
    
    override fun newArray(size : Int) : Array<Dream?>
    {
      return arrayOfNulls(size)
    }
  }
  
  constructor(parcel : Parcel) : this()
  {
  }
  
  override fun writeToParcel(p0 : Parcel?, p1 : Int)
  {
    // TODO 
  }
  
  override fun describeContents() : Int
  {
    // TODO
    return 0
  }
}
