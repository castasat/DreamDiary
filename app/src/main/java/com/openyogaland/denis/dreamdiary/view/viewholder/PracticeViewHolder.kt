package com.openyogaland.denis.dreamdiary.view.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.listener.OnPracticeItemClickListener
import com.openyogaland.denis.dreamdiary.listener.OnPracticeItemLongClickListener
import com.openyogaland.denis.dreamdiary.model.Practice

class
PracticeViewHolder(practiceView : View,
                   onItemClickListener : OnPracticeItemClickListener,
                   onItemLongClickListener : OnPracticeItemLongClickListener)
  : ViewHolder(practiceView)
{
  lateinit var practice : Practice
  
  val practiceTextView : AppCompatTextView =
    practiceView.findViewById(R.id.practiceTextView)
  
  init
  {
    practiceView
    .setOnClickListener {_ : View ->
      onItemClickListener.onPracticeItemClick(practice)
    }
    
    practiceView
    .setOnLongClickListener {_ : View ->
      onItemLongClickListener.onPracticeItemLongClick(practice)
      true
    }
  }
  
  fun
  bindPractice(practice : Practice)
  {
    this.practice = practice
  }
}
