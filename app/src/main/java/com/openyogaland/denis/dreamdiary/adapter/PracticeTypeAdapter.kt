package com.openyogaland.denis.dreamdiary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.calculateDiff
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.diffutil.PracticeTypeDiffCallback
import com.openyogaland.denis.dreamdiary.listener.OnPracticeTypeItemClickListener
import com.openyogaland.denis.dreamdiary.view.viewholder.PracticeTypeViewHolder

class
PracticeTypeAdapter(private var practiceTypes : MutableList<String>,
                    private var onPracticeTypeItemClickListener
                    : OnPracticeTypeItemClickListener)
  : Adapter<PracticeTypeViewHolder>()
{
  // fields
  private val itemViewType : Int = 0
  
  fun
  addPracticeType(practiceType : String)
  {
    practiceTypes.toMutableList().add(practiceType)
  }
  
  private fun
  getItem(position : Int) : Any?
  {
    return practiceTypes.toTypedArray()[position]
  }
  
  fun
  getPracticeType(position : Int) : String?
  {
    return getItem(position) as? String
  }
  
  override fun
  getItemId(position : Int) : Long
  {
    return position.toLong()
  }
  
  override fun
  getItemViewType(position : Int) : Int
  {
    return itemViewType
  }
  
  override fun
  onCreateViewHolder(parent : ViewGroup,
                     viewType : Int) : PracticeTypeViewHolder
  {
    val practiceTypeItemView =
      LayoutInflater
      .from(parent.context)
      .inflate(R.layout.practice_type_item,
               parent,
               false)
    return PracticeTypeViewHolder(practiceTypeItemView,
                                  onPracticeTypeItemClickListener)
  }
  
  override fun
  onBindViewHolder(practiceTypeViewHolder : PracticeTypeViewHolder,
                   position : Int)
  {
    val practiceType =
      practiceTypes[practiceTypeViewHolder.adapterPosition]
    
    val practiceTypeTextView =
      practiceTypeViewHolder.practiceTypeTextView
    
    practiceTypeViewHolder.bindPracticeType(practiceType)
    practiceTypeTextView.text = practiceType
  }
  
  override fun
  getItemCount() : Int
  {
    return practiceTypes.size
  }
  
  fun
  updatePracticeTypeListItems(practiceTypes : List<String>)
  {
    val practiceTypesDiffCallback =
      PracticeTypeDiffCallback(this.practiceTypes,
                               practiceTypes)
    
    val diffResult = calculateDiff(practiceTypesDiffCallback)
    
    this.practiceTypes.clear()
    this.practiceTypes.addAll(practiceTypes)
    diffResult.dispatchUpdatesTo(this)
  }
}
