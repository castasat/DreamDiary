package com.openyogaland.denis.dreamdiary.view.dialog

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.openyogaland.denis.dreamdiary.listener.OnPracticeEditedListener
import com.openyogaland.denis.dreamdiary.model.Practice

@Suppress("NAME_SHADOWING")
class
EditPracticeDialog : BasePracticeDialog()
{
  var onPracticeEditedListener : OnPracticeEditedListener? = null
  var practiceToEdit : Practice? = null
  
  override fun
  onViewCreated(view : View,
                savedInstanceState : Bundle?)
  {
    super.onViewCreated(view, savedInstanceState)
    
    practiceToEdit
    ?.let {(practiceType) ->
      if(practiceType.isNotEmpty() &&
         practiceType.isNotBlank())
      {
        practiceEditText.setText(practiceType)
      }
    }
    
    practiceEditText.hint = "Редактировать практику"
    
    practiceEditText
    .setOnEditorActionListener {_ : TextView,
                                actionId : Int,
                                keyEvent : KeyEvent? ->
      
      if(actionId == EditorInfo.IME_ACTION_DONE ||
         keyEvent?.action == KeyEvent.ACTION_DOWN &&
         keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)
      {
        editPractice()
        dialog?.dismiss()
        true
      }
      else
      {
        false
      }
    }

    buttonTextView.text = "Редактировать"
    
    buttonTextView
    .setOnClickListener {_ : View ->
      editPractice()
      dialog?.dismiss()
    }
  }
  
  private fun
  editPractice()
  {
    practiceEditText
    .text
    ?.toString()
    ?.let {practiceType : String ->
      if(practiceType.isNotBlank() &&
         practiceType.isNotEmpty())
      {
        practiceToEdit
        ?.let {practiceToEdit : Practice ->
          practiceToEdit.practiceType = practiceType
          
          onPracticeEditedListener
          ?.onPracticeEdited(practiceToEdit)
        }
      }
    }
  }
}