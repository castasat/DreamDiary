package com.openyogaland.denis.dreamdiary.view.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager.LayoutParams
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.listener.OnCancelListener
import com.openyogaland.denis.dreamdiary.listener.OnPracticeAddedListener
import com.openyogaland.denis.dreamdiary.model.Practice

@Suppress("NAME_SHADOWING")
class
AddPracticeDialog : AppCompatDialogFragment()
{
  // view fields
  private lateinit var addPracticeEditText : AppCompatEditText
  private lateinit var addPracticeButton : AppCompatTextView
  
  var onPracticeAddedListener : OnPracticeAddedListener? = null
  var onCancelListener : OnCancelListener? = null
  
  override fun
  onCreateView(inflater : LayoutInflater,
               container : ViewGroup?,
               savedInstanceState : Bundle?) : View
  {
    val view = inflater
    .inflate(R.layout.add_practice_dialog,
             container,
             false)
    
    addPracticeEditText = view.findViewById(R.id.addPracticeEditText)
    addPracticeButton = view.findViewById(R.id.addPracticeButton)
    
    clear(addPracticeEditText)
    
    addPracticeEditText
    .setOnEditorActionListener {_ : TextView,
                                actionId : Int,
                                keyEvent : KeyEvent? ->
      
      if(actionId == IME_ACTION_DONE ||
         keyEvent?.action == ACTION_DOWN &&
         keyEvent.keyCode == KEYCODE_ENTER)
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
    
    addPracticeButton
    .setOnClickListener {_ : View ->
      addPractice()
      dialog?.dismiss()
    }
    
    dialog
    ?.let {dialog : Dialog ->
      if(!dialog.isShowing)
      {
        dialog.show()
      }
    }
    return view
  }
  
  private fun
  addPractice()
  {
    addPracticeEditText
    .text
    ?.toString()
    ?.let {practiceType : String ->
      onPracticeAddedListener
      ?.onPracticeAdded(Practice(practiceType))
    }
  }
  
  override fun
  onResume()
  {
    super.onResume()
    
    dialog
    ?.let {dialog : Dialog ->
      
      dialog.window
      ?.let {window : Window ->
        
        window.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        
        window.attributes
        ?.let {layoutParams : LayoutParams ->
          
          val outTypedValue = TypedValue()
          resources.getValue(R.dimen.dim_amount, outTypedValue, true)
          
          layoutParams.dimAmount = outTypedValue.float
          layoutParams.width = resources.getDimension(R.dimen.add_practice_type_dialog_width).toInt()
          layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
          window.attributes = layoutParams
        }
      }
      if(!dialog.isShowing)
      {
        dialog.show()
      }
      clear(addPracticeEditText)
    }
  }
  
  override fun
  onCreateDialog(savedInstanceState : Bundle?) : Dialog
  {
    val dialog = super.onCreateDialog(savedInstanceState)
    dialog.window?.requestFeature(FEATURE_NO_TITLE)
    dialog.setCanceledOnTouchOutside(true)
    if(!dialog.isShowing)
    {
      dialog.show()
    }
    return dialog
  }
  
  override fun
  onCancel(dialog : DialogInterface)
  {
    onCancelListener?.onCancel()
    dialog.dismiss()
  }
  
  private fun
  clear(editText : AppCompatEditText)
  {
    editText.text?.clear()
  }
}
