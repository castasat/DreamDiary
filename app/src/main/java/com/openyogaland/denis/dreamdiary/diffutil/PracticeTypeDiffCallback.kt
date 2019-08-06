package com.openyogaland.denis.dreamdiary.diffutil

import androidx.recyclerview.widget.DiffUtil

class
PracticeTypeDiffCallback
  (
  private val oldPracticeTypeList : List<String>,
  private val newPracticeTypeList : List<String>
) : DiffUtil.Callback()
{
  override fun
  areItemsTheSame(oldItemPosition : Int,
                  newItemPosition : Int) : Boolean
  {
    return oldPracticeTypeList[oldItemPosition] ==
    newPracticeTypeList[newItemPosition]
  }
  
  override fun
  getOldListSize() : Int = oldPracticeTypeList.size
  
  override fun
  getNewListSize() : Int = newPracticeTypeList.size

  override fun
  areContentsTheSame(oldItemPosition : Int,
                     newItemPosition : Int) : Boolean
  {
    val oldPracticeType : String = oldPracticeTypeList[oldItemPosition]
    val newPracticeType : String = newPracticeTypeList[newItemPosition]
    return oldPracticeType == newPracticeType
  }
  
  override fun
  getChangePayload(oldItemPosition : Int,
                   newItemPosition : Int) : Any?
  {
    // Implement method if you're going to use ItemAnimator
    return super.getChangePayload(oldItemPosition, newItemPosition)
  }
}
