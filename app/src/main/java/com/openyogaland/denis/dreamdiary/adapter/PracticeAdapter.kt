package com.openyogaland.denis.dreamdiary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.listener.OnPracticeItemClickListener
import com.openyogaland.denis.dreamdiary.model.Practice
import com.openyogaland.denis.dreamdiary.view.viewholder.PracticeViewHolder

class
PracticeAdapter(private var practices : ArrayList<Practice>,
                private var onPracticeItemClickListener
                    : OnPracticeItemClickListener)
  : Adapter<PracticeViewHolder>()
{
  // fields
  private val itemViewType : Int = 0
  
  fun
  addPractice(practice : Practice)
  {
    practices.add(practice)
  }
  
  private fun
  getItem(position : Int) : Any
  {
    return practices[position]
  }
  
  fun
  getPractice(position : Int) : Practice?
  {
    return getItem(position) as? Practice
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
                     viewType : Int) : PracticeViewHolder
  {
    val practiceTypeItemView =
      LayoutInflater
      .from(parent.context)
      .inflate(R.layout.practice_item,
               parent,
               false)
    return PracticeViewHolder(practiceTypeItemView,
                              onPracticeItemClickListener)
  }
  
  override fun
  onBindViewHolder(practiceViewHolder : PracticeViewHolder,
                   position : Int)
  {
    val practice =
      practices[practiceViewHolder.adapterPosition]
    
    val practiceTypeTextView =
      practiceViewHolder.practiceTextView
    
    practiceViewHolder.bindPractice(practice)
    practiceTypeTextView.text = practice.practiceType
  }
  
  override fun
  getItemCount() : Int
  {
    return practices.size
  }
}
