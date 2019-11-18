package com.openyogaland.denis.dreamdiary.view.fragment

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager.LayoutParams
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.application.DreamDiary.DreamDiary.log
import com.openyogaland.denis.dreamdiary.model.Dream
import com.openyogaland.denis.dreamdiary.viewmodel.DreamViewModel
import com.redmadrobot.inputmask.MaskedTextChangedListener

@Suppress("NAME_SHADOWING")
class
DreamFragment : Fragment()
{
  // view fields
  private lateinit var dateTextView : AppCompatTextView
  private lateinit var practiceEditText : AppCompatEditText
  private lateinit var dreamDescriptionEditText : AppCompatEditText
  private lateinit var anchorEditText : AppCompatEditText
  private lateinit var dreamDurationTimeEditText : AppCompatEditText
  private lateinit var lucidDreamCheckBox : AppCompatCheckBox
  private lateinit var emotionsEditText : AppCompatEditText
  private lateinit var saveDreamButton : AppCompatButton
  
  // architecture fields
  /* TODO enable if needed
  private lateinit var activityViewModel : ActivityViewModel*/
  private lateinit var dreamViewModel : DreamViewModel
  
  override fun
  onCreateView(inflater : LayoutInflater,
               container : ViewGroup?,
               savedInstanceState : Bundle?) : View?
  {
    val view : View =
      inflater
      .inflate(R.layout.dream_fragment,
               container,
               false)
    
    /* TODO enable if needed
    activityViewModel =
      ViewModelProvider(requireActivity() as MainActivity)
      .get(ActivityViewModel::class.java)*/
    
    dreamViewModel =
      ViewModelProvider(this)
      .get(DreamViewModel::class.java)
    
    dateTextView = view.findViewById(R.id.dateTextView)
    practiceEditText = view.findViewById(R.id.practiceEditText)
    dreamDescriptionEditText = view.findViewById(R.id.dreamEditText)
    anchorEditText = view.findViewById(R.id.anchorEditText)
    dreamDurationTimeEditText = view.findViewById(R.id.dreamDurationTimeEditText)
    lucidDreamCheckBox = view.findViewById(R.id.lucidDreamCheckBox)
    emotionsEditText = view.findViewById(R.id.feelingsEditText)
    saveDreamButton = view.findViewById(R.id.saveDreamButton)
    
    saveDreamButton
    .setOnClickListener {_ : View ->
      val dream = Dream()
      dream.date = dateTextView.text.toString()
      log("DreamFragment.saveDreamButton.setOnClickListener(): dream.date = ${dream.date}")
      // TODO set moon phase day
      dream.practice = practiceEditText.text.toString()
      log("DreamFragment.saveDreamButton.setOnClickListener(): dream.practice = ${dream.practice}")
      dream.dreamDescription = dreamDescriptionEditText.text.toString()
      log("DreamFragment.saveDreamButton.setOnClickListener(): dream.dreamDescription = ${dream.dreamDescription}")
      dream.anchor = anchorEditText.text.toString()
      log("DreamFragment.saveDreamButton.setOnClickListener(): dream.anchor = ${dream.anchor}")
      dream.dreamDuration = dreamDurationTimeEditText.text.toString()
      log("DreamFragment.saveDreamButton.setOnClickListener(): dream.dreamDuration = ${dream.dreamDuration}")
      dream.lucidDream = lucidDreamCheckBox.isChecked
      log("DreamFragment.saveDreamButton.setOnClickListener(): dream.lucidDream = ${dream.lucidDream}")
      dream.emotions = emotionsEditText.text.toString()
      log("DreamFragment.saveDreamButton.setOnClickListener(): dream.emotions = ${dream.emotions}")
      dreamViewModel.saveDream(dream)
    }
    
    dreamViewModel
    .currentDreamLiveData
    .observe(viewLifecycleOwner,
             Observer<Dream>
             {dream : Dream ->
               dateTextView.text = dream.date
               // TODO restore moon phase day
               practiceEditText.setText(dream.practice)
               dreamDescriptionEditText.setText(dream.dreamDescription)
               anchorEditText.setText(dream.anchor)
               dreamDurationTimeEditText.setText(dream.dreamDuration)
               lucidDreamCheckBox.isChecked = dream.lucidDream
               emotionsEditText.setText(dream.emotions)
             })
    
    dreamViewModel
    .loadDream(dateTextView.text.toString())
    
    return view
  }
  
  private fun
  setInputMaskToEditText(format : String, editText : EditText)
  {
    val maskedTextChangedListener = MaskedTextChangedListener(format, editText)
    editText.addTextChangedListener(maskedTextChangedListener)
    editText.onFocusChangeListener = maskedTextChangedListener
    editText.hint = maskedTextChangedListener.placeholder()
  }
  
  override fun
  onActivityCreated(savedInstanceState : Bundle?)
  {
    super
    .onActivityCreated(savedInstanceState)
    
    setInputMaskToEditText("[00]{:}[00]{ - }[00]{:}[00]",
                           dreamDurationTimeEditText)
  
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
}
