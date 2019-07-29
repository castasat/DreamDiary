package com.openyogaland.denis.dreamdiary.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import com.openyogaland.denis.dreamdiary.R
import kotlinx.android.synthetic.main.day_fragment.*

@Suppress("NAME_SHADOWING")
public class
DayFragment : Fragment()
{
  override fun
  onCreateView(inflater : LayoutInflater,
               container : ViewGroup?,
               savedInstanceState : Bundle?) : View?
  {
    val view : View =
      inflater
      .inflate(R.layout.day_fragment,
               container,
               false)
    
    val arrayAdapter =
      ArrayAdapter
      .createFromResource(requireActivity(),
                          R.array.practice_types,
                          android.R.layout.simple_spinner_item)
    arrayAdapter
    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    
    practiceSpinner
    ?.onItemSelectedListener =
      object : AdapterView.OnItemSelectedListener
      {
        override fun
        onItemSelected(parent : AdapterView<*>?,
                       itemSelected : View?,
                       selectedItemPosition : Int,
                       selectedId : Long)
        {
          parent
          ?.let {parent : AdapterView<*> ->
            parent
            .getChildAt(0)
            ?.let {view : View ->
              if(view is TextView)
              {
                context
                ?.let {context : Context ->
                  view
                  .setTextColor(getColor(context, R.color.grayed_text_gray))
                }
                view
                .textSize = 12.0f
                view
                .setPadding(0, 0, 0, 0)
              }
            }
          }
        }
        
        override fun
        onNothingSelected(parent : AdapterView<*>?)
        {
          // TODO
        }
      }
    
    practiceSpinner
    ?.adapter = arrayAdapter
    
    val stressLevelSeekBar : AppCompatSeekBar =
      view
      .findViewById<AppCompatSeekBar>(R.id.stressLevelSeekBar)
    
    val stressLevelTextView =
      view
      .findViewById<TextView>(R.id.stressLevelTextView)
    
    val onSeekBarChangeListener =
      object : SeekBar.OnSeekBarChangeListener
      {
        override fun
        onStartTrackingTouch(seekBar : SeekBar?)
        {
          if(context != null)
          {
            // context?.toast("Пользователь коснулся")
          }
        }
        
        override fun
        onStopTrackingTouch(seekBar : SeekBar?)
        {
          // context?.toast("Пользователь перестал касаться")
        }
        
        override fun
        onProgressChanged(seekBar : SeekBar?,
                          progress : Int,
                          fromUser : Boolean)
        {
          stressLevelTextView
          .text = "Уровень стресса: $progress%"
        }
      }
    
    stressLevelSeekBar
    .setOnSeekBarChangeListener(onSeekBarChangeListener)
    
    stressLevelSeekBar
    .progressDrawable =
      ContextCompat
      .getDrawable(activity as Context, R.drawable.stress_seekbar)
    
    return view
  }
  
  override fun onActivityCreated(savedInstanceState : Bundle?)
  {
    super
    .onActivityCreated(savedInstanceState)
  }
}
