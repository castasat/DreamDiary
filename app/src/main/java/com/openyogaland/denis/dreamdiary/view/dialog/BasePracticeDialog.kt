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
import kotlinx.android.synthetic.main.base_practice_dialog.*

@Suppress("NAME_SHADOWING")
open class
BasePracticeDialog : AppCompatDialogFragment()
{
  // view fields
  lateinit var practiceEditText : AppCompatEditText
  lateinit var buttonTextView : AppCompatTextView
  
  var onCancelListener : OnCancelListener? = null
  
  override fun
  onCreateView(inflater : LayoutInflater,
               container : ViewGroup?,
               savedInstanceState : Bundle?) : View
  {
    val view = inflater
    .inflate(R.layout.base_practice_dialog,
             container,
             false)
    
    practiceEditText = view.findViewById(R.id.practiceEditText)
    buttonTextView = view.findViewById(R.id.buttonTextView)
    
    return view
  }
  
  override fun
  onViewCreated(view : View,
                savedInstanceState : Bundle?)
  {
    super.onViewCreated(view, savedInstanceState)
  
    dialog
    ?.let {dialog : Dialog ->
      if(!dialog.isShowing)
      {
        dialog.show()
      }
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
}
