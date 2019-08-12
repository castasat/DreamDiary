package com.openyogaland.denis.dreamdiary.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.viewmodel.DreamViewModel
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.dream_fragment.*

@Suppress("NAME_SHADOWING")
public class
DreamFragment : Fragment()
{
  // view fields
  private lateinit var dateTextView : AppCompatTextView
  
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
  }
}
