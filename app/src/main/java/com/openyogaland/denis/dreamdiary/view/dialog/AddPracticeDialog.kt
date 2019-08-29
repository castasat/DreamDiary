package com.openyogaland.denis.dreamdiary.view.dialog

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import com.openyogaland.denis.dreamdiary.listener.OnPracticeAddedListener
import com.openyogaland.denis.dreamdiary.model.Practice

@Suppress("NAME_SHADOWING")
class
AddPracticeDialog : BasePracticeDialog()
{
  var onPracticeAddedListener : OnPracticeAddedListener? = null
  
  override fun
  onViewCreated(view : View,
                savedInstanceState : Bundle?)
  {
    super.onViewCreated(view, savedInstanceState)
    
    practiceEditText
    ?.let {practiceEditText : AppCompatEditText ->
      
      clear(practiceEditText)
      
      practiceEditText
      .setOnEditorActionListener {_ : TextView,
                                  actionId : Int,
                                  keyEvent : KeyEvent? ->
        
        if(actionId == EditorInfo.IME_ACTION_DONE ||
           keyEvent?.action == KeyEvent.ACTION_DOWN &&
           keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)
        {
          addPractice()
          dialog?.dismiss()
          true
        }
        else
        {
          false
        }
      }
    }
    
    buttonTextView
    .setOnClickListener {_ : View ->
      addPractice()
      dialog?.dismiss()
    }
  }
  
  override fun
  onResume()
  {
    super.onResume()
    
    practiceEditText
    ?.let {practiceEditText : AppCompatEditText ->
      clear(practiceEditText)
    }
  }
  
  private fun
  addPractice()
  {
    practiceEditText
    ?.text
    ?.toString()
    ?.let {practiceType : String ->
      if(practiceType.isNotBlank() &&
         practiceType.isNotEmpty())
      {
        onPracticeAddedListener
        ?.onPracticeAdded(Practice(practiceType))
      }
    }
  }
  
  private fun
  clear(editText : AppCompatEditText)
  {
    editText.text?.clear()
  }
}