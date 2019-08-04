package com.openyogaland.denis.dreamdiary.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.adapter.PracticeTypeAdapter
import com.openyogaland.denis.dreamdiary.listener.OnPracticeTypeItemClickListener

@Suppress("NAME_SHADOWING")
public class
DayFragment : Fragment()
{
  // view fields
  private lateinit var practiceChooserTextView : AppCompatTextView
  private lateinit var practiceRecycleView : RecyclerView
  private lateinit var addPracticeTypeTextView : AppCompatTextView
  
  // practice fields
  private val practiceTypes = listOf("Хатха", "Крия", "Мантра", "Пранаяма")
  
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
    
    practiceRecycleView.layoutManager = LinearLayoutManager(context)
    // list is shown, click on list item
    practiceRecycleView.adapter =
      PracticeTypeAdapter(practiceTypes,
                          object : OnPracticeTypeItemClickListener
                          {
                            override fun
                            onPracticeTypeItemClick(practiceType : String)
                            {
                              practiceChooserTextView
                              .setTextColor(ContextCompat
                                            .getColor(requireActivity(),
                                                      R.color.colorPrimary))
                              practiceChooserTextView.text = practiceType
                              practiceRecycleView.visibility = GONE
                              addPracticeTypeTextView.visibility = GONE
                            }
                          })
    
    practiceChooserTextView
    .setOnClickListener {view : View ->
      (view as AppCompatTextView)
      .let {practiceChooserTextView : AppCompatTextView ->
        practiceChooserTextView
        .setTextColor(ContextCompat
                      .getColor(requireActivity(),
                                R.color.transparent))
        practiceChooserTextView.text = "Не выбрано"
        when(practiceRecycleView.visibility)
        {
          GONE    ->
          {
            practiceRecycleView.visibility = VISIBLE
            addPracticeTypeTextView.visibility = VISIBLE
            // TODO change compoundDrawable of practiceChooserTextView to arrayUp
          }
          VISIBLE ->
          {
            practiceRecycleView.visibility = GONE
            addPracticeTypeTextView.visibility = GONE
            // TODO change compoundDrawable of practiceChooserTextView to arrayDown
          }
        }
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
        
        @SuppressLint("SetTextI18n")
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
