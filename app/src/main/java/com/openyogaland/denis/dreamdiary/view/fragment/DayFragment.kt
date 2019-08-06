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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.adapter.PracticeTypeAdapter
import com.openyogaland.denis.dreamdiary.application.DreamDiary.DreamDiary.log
import com.openyogaland.denis.dreamdiary.listener.OnCancelListener
import com.openyogaland.denis.dreamdiary.listener.OnPracticeTypeAddedListener
import com.openyogaland.denis.dreamdiary.listener.OnPracticeTypeItemClickListener
import com.openyogaland.denis.dreamdiary.model.Practice
import com.openyogaland.denis.dreamdiary.view.dialog.AddPracticeTypeDialog
import com.openyogaland.denis.dreamdiary.viewmodel.ActivityViewModel
import com.openyogaland.denis.dreamdiary.viewmodel.DayViewModel

@Suppress("NAME_SHADOWING")
public class
DayFragment : Fragment()
{
  // architecture fields
  private lateinit var activityViewModel : ActivityViewModel
  private lateinit var dayViewModel : DayViewModel
  
  // view fields
  private lateinit var practiceChooserTextView : AppCompatTextView
  private lateinit var practiceRecycleView : RecyclerView
  private lateinit var addPracticeTypeTextView : AppCompatTextView
  
  // dialog fields
  private var addPracticeTypeDialog : AddPracticeTypeDialog? = null
  
  // practice fields
  private val practiceTypes =
    listOf("Хатха", "Крия", "Мантра", "Пранаяма")
    .toMutableList()
  
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
      ViewModelProviders
      .of(requireActivity())
      .get(ActivityViewModel::class.java)
    
    dayViewModel =
      ViewModelProviders
      .of(this)
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
    
    practiceRecycleView.layoutManager = LinearLayoutManager(context)
    
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
    
    addPracticeTypeTextView
    .setOnClickListener {view : View ->
      (view as AppCompatTextView)
      .let {_ : AppCompatTextView ->
        showAddPracticeTypeDialog()
      }
    }
    
    dayViewModel
    .loadPracticeTypes()
    .observe(this,
             Observer<List<Practice>>
             {practices : List<Practice> ->
               practices
               .map {practice : Practice ->
                 practice.practiceType
               }
               .let {practiceTypes : List<String> ->
                 practiceRecycleView
                 .adapter
                 ?.let {adapter: RecyclerView.Adapter<ViewHolder> ->
                   
                   (adapter as PracticeTypeAdapter)
                   .updatePracticeTypeListItems(practiceTypes)
                 }
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
  showAddPracticeTypeDialog()
  {
    addPracticeTypeDialog =
      addPracticeTypeDialog
      ?: AddPracticeTypeDialog()
    
    addPracticeTypeDialog
    ?.let {addPracticeTypeDialog : AddPracticeTypeDialog ->
      
      addPracticeTypeDialog.isCancelable = true
      
      addPracticeTypeDialog.onCancelListener =
        object : OnCancelListener
        {
          override fun
          onCancel()
          {
            log("DayFragment.showAddPracticeTypeDialog(): " +
                "canceled")
          }
        }
      
      addPracticeTypeDialog.onPracticeTypeAddedListener =
        object : OnPracticeTypeAddedListener
        {
          override fun
          onPracticeTypeAdded(practiceType : String)
          {
            log("DayFragment.showAddPracticeTypeDialog(): " +
                "practiceType = $practiceType")
            dayViewModel.addPracticeType(practiceType)
          }
        }
      
      addPracticeTypeDialog.show(childFragmentManager,
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
  }
}
