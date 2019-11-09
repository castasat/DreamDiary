package com.openyogaland.denis.dreamdiary.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager.LayoutParams
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.adapter.PracticeAdapter
import com.openyogaland.denis.dreamdiary.application.DreamDiary.DreamDiary.log
import com.openyogaland.denis.dreamdiary.listener.OnCancelListener
import com.openyogaland.denis.dreamdiary.listener.OnPracticeAddedListener
import com.openyogaland.denis.dreamdiary.listener.OnPracticeEditedListener
import com.openyogaland.denis.dreamdiary.listener.OnPracticeItemClickListener
import com.openyogaland.denis.dreamdiary.listener.OnPracticeItemLongClickListener
import com.openyogaland.denis.dreamdiary.model.Day
import com.openyogaland.denis.dreamdiary.model.Practice
import com.openyogaland.denis.dreamdiary.view.dialog.AddPracticeDialog
import com.openyogaland.denis.dreamdiary.view.dialog.EditPracticeDialog
import com.openyogaland.denis.dreamdiary.viewmodel.DayViewModel
import kotlinx.android.synthetic.main.day_fragment.*

@Suppress("NAME_SHADOWING")
class
DayFragment : Fragment()
{
  // view fields
  private lateinit var dateTextView : AppCompatTextView
  private lateinit var cycleDayCountEditText : AppCompatEditText
  private lateinit var practiceChooserTextView : AppCompatTextView
  private var practiceRecycleView : RecyclerView? = null
  private lateinit var addPracticeTypeTextView : AppCompatTextView
  private lateinit var practiceMinutesEditText : AppCompatEditText
  private lateinit var nutritionEditText : AppCompatEditText
  private lateinit var eventsEditText : AppCompatEditText
  private lateinit var stressLevelSeekBar : AppCompatSeekBar
  private lateinit var stressLevelTextView : AppCompatTextView
  private lateinit var saveDayButton : AppCompatButton
  
  // dialog fields
  private var addPracticeDialog : AddPracticeDialog? = null
  private var editPracticeDialog : EditPracticeDialog? = null
  
  // architecture fields
  /* TODO enable if needed
  private lateinit var activityViewModel : ActivityViewModel*/
  private lateinit var dayViewModel : DayViewModel
  
  @SuppressLint("SetTextI18n")
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
    
    /* TODO enable if needed
    activityViewModel =
      ViewModelProvider(requireActivity() as MainActivity)
      .get(ActivityViewModel::class.java)*/
    
    dayViewModel =
      ViewModelProvider(this)
      .get(DayViewModel::class.java)
    
    dateTextView = view.findViewById(R.id.dateTextView)
    cycleDayCountEditText = view.findViewById(R.id.cycleDayCountEditText)
    practiceChooserTextView = view.findViewById(R.id.practiceChooserTextView)
    practiceRecycleView = view.findViewById(R.id.practiceRecyclerView)
    practiceMinutesEditText = view.findViewById(R.id.practiceMinutesEditText)
    addPracticeTypeTextView = view.findViewById(R.id.addPracticeTypeTextView)
    nutritionEditText = view.findViewById(R.id.nutritionEditText)
    eventsEditText = view.findViewById(R.id.eventsEditText)
    stressLevelSeekBar = view.findViewById(R.id.stressLevelSeekBar)
    stressLevelTextView = view.findViewById(R.id.stressLevelTextView)
    saveDayButton = view.findViewById(R.id.saveDayButton)
    
    practiceChooserTextView
    .setOnClickListener {view : View ->
      (view as AppCompatTextView)
      .let {practiceChooserTextView : AppCompatTextView ->
        practiceChooserTextView
        .setTextColor(ContextCompat
                      .getColor(requireActivity(),
                                R.color.transparent))
        practiceChooserTextView.text = "Не выбрано"
        when(practiceRecycleView?.visibility)
        {
          GONE    ->
          {
            practiceRecycleView?.visibility = VISIBLE
            addPracticeTypeTextView.visibility = VISIBLE
            practiceChooserTextView
            .setCompoundDrawablesWithIntrinsicBounds(0, 0,
                                                     R.drawable.arrow_up,
                                                     0)
          }
          VISIBLE ->
          {
            practiceRecycleView?.visibility = GONE
            addPracticeTypeTextView.visibility = GONE
            practiceChooserTextView
            .setCompoundDrawablesWithIntrinsicBounds(0, 0,
                                                     R.drawable.arrow_down,
                                                     0)
          }
        }
      }
    }
    
    practiceRecycleView?.layoutManager = LinearLayoutManager(context)
    // list is shown, click on list item
    practiceRecycleView?.adapter =
      PracticeAdapter(ArrayList<Practice>(PRACTICE_TYPES_INITIAL_CAPACITY),
                      object : OnPracticeItemClickListener
                      {
                        override fun
                        onPracticeItemClick(practice : Practice)
                        {
                          log("DayFragment.onCreateView()" +
                              ".onPracticeItemClickListener" +
                              ".onPracticeItemClick(): " +
                              "practice = ${practice.practiceType}")
          
                          practiceChooserTextView
                          .setTextColor(ContextCompat
                                        .getColor(requireActivity(),
                                                  R.color.colorPrimary))
                          practiceChooserTextView
                          .setCompoundDrawablesWithIntrinsicBounds(0, 0,
                                                                   R.drawable.arrow_down,
                                                                   0)
                          practiceChooserTextView.text = practice.practiceType
                          practiceRecycleView?.visibility = GONE
                          addPracticeTypeTextView.visibility = GONE
                        }
                      },
                      object : OnPracticeItemLongClickListener
                      {
                        override fun
                        onPracticeItemLongClick(practice : Practice)
                        {
                          log("DayFragment.onCreateView()" +
                              ".onPracticeItemLongClickListener" +
                              ".onPracticeItemLongClick(): " +
                              "practice = ${practice.practiceType}")
          
                          showEditPracticeDialog(practice)
          
                          practiceChooserTextView
                          .setCompoundDrawablesWithIntrinsicBounds(0, 0,
                                                                   R.drawable.arrow_up,
                                                                   0)
                        }
                      })
    
    addPracticeTypeTextView
    .setOnClickListener {_ : View ->
      
      showAddPracticeDialog()
      
      practiceChooserTextView
      .setCompoundDrawablesWithIntrinsicBounds(0, 0,
                                               R.drawable.arrow_up,
                                               0)
    }
    
    dayViewModel
    .allPracticesLiveData
    .observe(this,
             Observer<List<Practice>>
             {practices : List<Practice> ->
               (practiceRecyclerView.adapter as PracticeAdapter)
               .let {practiceAdapter : PracticeAdapter ->
                 practiceAdapter
                 .apply{
                   this.practices.clear()
                   addPractices(practices)
                   notifyDataSetChanged()
                 }
               }
             })
    
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
    
    stressLevelSeekBar
    .setOnSeekBarChangeListener(onSeekBarChangeListener)
    
    stressLevelSeekBar.progressDrawable =
      ContextCompat.getDrawable(activity as Context, R.drawable.stress_seekbar)
    
    saveDayButton
    .setOnClickListener {_ : View ->
      val day = Day()
      day.date = dateTextView.text.toString()
      log("DayFragment.saveDayButton.setOnClickListener(): day.date = ${day.date}")
      // TODO set moon phase day
      day.cycleDay = cycleDayCountEditText.text.toString()
      log("DayFragment.saveDayButton.setOnClickListener(): day.cycleDay = ${day.cycleDay}")
      day.practiceType = practiceChooserTextView.text.toString()
      log("DayFragment.saveDayButton.setOnClickListener(): day.practiceType = ${day.practiceType}")
      day.practiceDurationMinutes = practiceMinutesEditText.text.toString()
      log("DayFragment.saveDayButton.setOnClickListener(): day.practiceDurationMinutes = ${day.practiceDurationMinutes}")
      day.nutrition = nutritionEditText.text.toString()
      log("DayFragment.saveDayButton.setOnClickListener(): day.nutrition = ${day.nutrition}")
      day.events = eventsEditText.text.toString()
      log("DayFragment.saveDayButton.setOnClickListener(): day.events = ${day.events}")
      day.stressLevel = stressLevelSeekBar.progress.toString()
      log("DayFragment.saveDayButton.setOnClickListener(): day.stressLevel = ${day.stressLevel}")
      dayViewModel.saveDay(day)
    }
    
    dayViewModel
    .currentDayLiveData
    .observe(this,
             Observer<Day>
             {day : Day ->
               dateTextView.text = day.date
               // TODO restore moon phase day
               cycleDayCountEditText.setText(day.cycleDay)
               practiceChooserTextView
               .setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorPrimary))
               practiceChooserTextView.text = day.practiceType
               practiceMinutesEditText.setText(day.practiceDurationMinutes)
               nutritionEditText.setText(day.nutrition)
               eventsEditText.setText(day.events)
               stressLevelTextView.text = "Уровень стресса: ${day.stressLevel}%"
               stressLevelSeekBar.progress = day.stressLevel.toInt()
             })
    
    dayViewModel
    .loadAllPractices()
    
    dayViewModel
    .loadDay(dateTextView.text.toString())
    
    return view
  }
  
  override fun onDestroyView()
  {
    super.onDestroyView()
    practiceRecycleView?.adapter = null
    practiceRecycleView = null
  }
  
  private fun
  showEditPracticeDialog(practice : Practice)
  {
    editPracticeDialog = EditPracticeDialog()
    
    editPracticeDialog
    ?.let {editPracticeDialog : EditPracticeDialog ->
      
      editPracticeDialog.isCancelable = true
      
      editPracticeDialog.onCancelListener =
        object : OnCancelListener
        {
          override fun
          onCancel()
          {
            log("DayFragment.showEditPracticeDialog(): " +
                "canceled")
          }
        }
      
      if(practice.practiceType.isNotBlank() && practice.practiceType.isNotEmpty())
      {
        editPracticeDialog.updateEditTextWithPractice(practice)
        
        // TODO remove if works
        /*editPracticeDialog.practiceToEdit = practice
        editPracticeDialog.practiceEditText?.setText(practice.practiceType)*/
      }
      
      editPracticeDialog.onPracticeEditedListener =
        object : OnPracticeEditedListener
        {
          override fun
          onPracticeEdited(practice : Practice)
          {
            log("DayFragment.showEditPracticeDialog(): " +
                "practice = $practice")
            dayViewModel.editPractice(practice)
          }
        }
      
      editPracticeDialog.show(childFragmentManager, EDIT_PRACTICE_DIALOG)
    }
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
    super.onActivityCreated(savedInstanceState)
    
    activity
    ?.let {activity : FragmentActivity ->
      
      activity.window
      ?.let {window : Window ->
        
        // from API 19
        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT)
        {
          window.clearFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS)
          
          // from API 21
          if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP)
          {
            window.addFlags(LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(activity, R.color.colorPrimaryDark)
          }
        }
      }
    }
  }
  
  companion object
  {
    const val ADD_PRACTICE_DIALOG = "add_practice_dialog"
    const val EDIT_PRACTICE_DIALOG = "edit_practice_dialog"
    const val PRACTICE_TYPES_INITIAL_CAPACITY = 8
  }
}
