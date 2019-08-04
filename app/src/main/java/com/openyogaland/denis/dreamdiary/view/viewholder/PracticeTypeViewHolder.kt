package com.openyogaland.denis.dreamdiary.view.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.listener.OnPracticeTypeItemClickListener

class
PracticeTypeViewHolder(practiceTypeView : View,
                       onItemClickListener : OnPracticeTypeItemClickListener) : ViewHolder(practiceTypeView)
{
  // fields
  lateinit var practiceType : String
  
  val practiceTypeTextView : AppCompatTextView =
    practiceTypeView.findViewById(R.id.practiceTypeTextView)
  
  init
  {
    practiceTypeView
    .setOnClickListener {_ : View ->
      onItemClickListener.onPracticeTypeItemClick(practiceType)
    }
  }
  
  fun
  bindPracticeType(practiceType : String)
  {
    this.practiceType = practiceType
  }
}
