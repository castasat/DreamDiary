package com.openyogaland.denis.dreamdiary.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.openyogaland.denis.dreamdiary.R
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.dream_fragment.*

public class
DreamFragment : Fragment()
{
  override fun
  onCreateView(inflater : LayoutInflater,
               container : ViewGroup?,
               savedInstanceState : Bundle?) : View?
  {
    val view : View =
      inflater
      .inflate(R.layout.dream_fragment, container, false)
    
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
