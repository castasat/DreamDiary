package com.openyogaland.denis.dreamdiary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.listener.OnPracticeItemClickListener
import com.openyogaland.denis.dreamdiary.listener.OnPracticeItemLongClickListener
import com.openyogaland.denis.dreamdiary.model.Practice
import com.openyogaland.denis.dreamdiary.view.viewholder.PracticeViewHolder

class
PracticeAdapter(var practices : ArrayList<Practice>,
                private var onPracticeItemClickListener
                : OnPracticeItemClickListener,
                private var onPracticeItemLongClickListener
                : OnPracticeItemLongClickListener)
  : Adapter<PracticeViewHolder>()
{
  // fields
  private val itemViewType : Int = 0
  
  fun
  addPractices(practices : List<Practice>)
  {
    this.practices
    .addAll(practices)
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
      .inflate(R.layout.item_practice_layout,
               parent,
               false)
    return PracticeViewHolder(practiceTypeItemView,
                              onPracticeItemClickListener,
                              onPracticeItemLongClickListener)
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
