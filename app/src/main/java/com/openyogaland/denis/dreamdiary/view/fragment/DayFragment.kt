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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.adapter.PracticeAdapter
import com.openyogaland.denis.dreamdiary.application.DreamDiary.DreamDiary.log
import com.openyogaland.denis.dreamdiary.listener.OnCancelListener
import com.openyogaland.denis.dreamdiary.listener.OnPracticeAddedListener
import com.openyogaland.denis.dreamdiary.listener.OnPracticeItemClickListener
import com.openyogaland.denis.dreamdiary.model.Practice
import com.openyogaland.denis.dreamdiary.view.dialog.AddPracticeDialog
import com.openyogaland.denis.dreamdiary.viewmodel.ActivityViewModel
import com.openyogaland.denis.dreamdiary.viewmodel.DayViewModel
import kotlinx.android.synthetic.main.day_fragment.*

@Suppress("NAME_SHADOWING")
public class
DayFragment : Fragment()
{
  // view fields
  private lateinit var practiceChooserTextView : AppCompatTextView
  private lateinit var practiceRecycleView : RecyclerView
  private lateinit var addPracticeTypeTextView : AppCompatTextView
  
  // dialog fields
  private var addPracticeDialog : AddPracticeDialog? = null
  
  // architecture fields
  private lateinit var activityViewModel : ActivityViewModel
  private lateinit var dayViewModel : DayViewModel
  
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
    
    activityViewModel =
      ViewModelProvider(requireActivity())
      .get(ActivityViewModel::class.java)
    
    dayViewModel =
      ViewModelProvider(this)
      .get(DayViewModel::class.java)
    
    practiceChooserTextView = view.findViewById(R.id.practiceChooserTextView)
    practiceRecycleView = view.findViewById(R.id.practiceRecyclerView)
    addPracticeTypeTextView = view.findViewById(R.id.addPracticeTypeTextView)
    
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
            practiceChooserTextView
            .setCompoundDrawablesWithIntrinsicBounds(0,
                                                     0,
                                                     R.drawable.arrow_up,
                                                     0)
          }
          VISIBLE ->
          {
            practiceRecycleView.visibility = GONE
            addPracticeTypeTextView.visibility = GONE
            practiceChooserTextView
            .setCompoundDrawablesWithIntrinsicBounds(0,
                                                     0,
                                                     R.drawable.arrow_down,
                                                     0)
          }
        }
      }
    }
    
    practiceRecycleView.layoutManager = LinearLayoutManager(context)
    // list is shown, click on list item
    practiceRecycleView.adapter =
      PracticeAdapter(ArrayList<Practice>(PRACTICE_TYPES_INITIAL_CAPACITY),
                      object : OnPracticeItemClickListener
                      {
                        override fun
                        onPracticeItemClick(practice : Practice)
                        {
                          practiceChooserTextView
                          .setTextColor(ContextCompat
                                        .getColor(requireActivity(),
                                                  R.color.colorPrimary))
                          practiceChooserTextView.text = practice.practiceType
                          practiceRecycleView.visibility = GONE
                          addPracticeTypeTextView.visibility = GONE
                        }
                      })
    
    addPracticeTypeTextView
    .setOnClickListener {view : View ->
      (view as AppCompatTextView)
      .let {_ : AppCompatTextView ->
        showAddPracticeDialog()
      }
    }
    
    dayViewModel
    .downloadAllPractices()
    .observe(this,
             Observer<List<Practice>>
             {practices : List<Practice> ->
               (practiceRecyclerView.adapter as PracticeAdapter)
               .let {practiceAdapter : PracticeAdapter ->
        
                 practiceAdapter.practices.clear()
                 practiceAdapter.addPractices(practices)
                 practiceAdapter.notifyDataSetChanged()
               }
             })
    
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
  
  private fun
  showAddPracticeDialog()
  {
    addPracticeDialog =
      addPracticeDialog
      ?: AddPracticeDialog()
    
    addPracticeDialog
    ?.let {addPracticeDialog : AddPracticeDialog ->
      
      addPracticeDialog.isCancelable = true
      
      addPracticeDialog.onCancelListener =
        object : OnCancelListener
        {
          override fun
          onCancel()
          {
            log("DayFragment.showAddPracticeDialog(): " +
                "canceled")
          }
        }
      
      addPracticeDialog.onPracticeAddedListener =
        object : OnPracticeAddedListener
        {
          override fun
          onPracticeAdded(practice : Practice)
          {
            log("DayFragment.showAddPracticeDialog(): " +
                "practice = $practice")
            dayViewModel.addPractice(practice)
          }
        }
      
      addPracticeDialog.show(childFragmentManager,
                             ADD_PRACTICE_DIALOG)
    }
  }
  
  override fun
  onActivityCreated(savedInstanceState : Bundle?)
  {
    super
    .onActivityCreated(savedInstanceState)
  }
  
  companion object
  {
    const val ADD_PRACTICE_DIALOG = "add_practice_dialog"
    const val PRACTICE_TYPES_INITIAL_CAPACITY = 8
  }
}
