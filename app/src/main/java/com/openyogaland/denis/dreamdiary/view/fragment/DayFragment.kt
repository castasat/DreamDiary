package com.openyogaland.denis.dreamdiary.view.fragment

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.application.DreamDiary.DreamDiary.log

@Suppress("NAME_SHADOWING")
public class
DayFragment : Fragment()
{
  // view fields
  private lateinit var practiceChooserTextView : AppCompatTextView
  private lateinit var practiceRecycleView : RecyclerView
  private lateinit var addPracticeTypeTextView : AppCompatTextView
  
  // drawable fields
  private var arrowUpDrawable : Drawable? = null
  private var arrowDownDrawable : Drawable? = null
  
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
    
    practiceChooserTextView = view.findViewById(R.id.practiceChooserTextView)
    practiceRecycleView = view.findViewById(R.id.practiceRecyclerView)
    addPracticeTypeTextView = view.findViewById(R.id.addPracticeTypeTextView)
    
    practiceChooserTextView.setOnClickListener {view : View ->
      (view as AppCompatTextView)
      .let {practiceChooserTextView : AppCompatTextView ->
 
        practiceRecycleView.visibility = VISIBLE
        addPracticeTypeTextView.visibility = VISIBLE
      }
    }
  
  val stressLevelSeekBar : AppCompatSeekBar =
    view.findViewById(R.id.stressLevelSeekBar)
  
  val stressLevelTextView : TextView =
    view.findViewById(R.id.stressLevelTextView)
  
  val onSeekBarChangeListener =
    object : SeekBar.OnSeekBarChangeListener
    {
      override fun onStartTrackingTouch(seekBar : SeekBar?)
      {
      }
      
      override fun onStopTrackingTouch(seekBar : SeekBar?)
      {
      }
      
      override fun
      onProgressChanged(seekBar : SeekBar?,
                        progress : Int,
                        fromUser : Boolean)
      {
        stressLevelTextView.text = "Уровень стресса: $progress%"
      }
    }
  
  stressLevelSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener)
  
  stressLevelSeekBar.progressDrawable =
  ContextCompat.getDrawable(activity as Context, R.drawable.stress_seekbar)
  
  return view
  }
  
  override fun
  onActivityCreated(savedInstanceState : Bundle?)
  {
    super
    .onActivityCreated(savedInstanceState)
  }
}
