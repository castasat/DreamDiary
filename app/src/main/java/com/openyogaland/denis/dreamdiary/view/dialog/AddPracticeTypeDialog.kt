package com.openyogaland.denis.dreamdiary.view.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatDialogFragment
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.listener.OnCancelListener
import com.openyogaland.denis.dreamdiary.listener.OnPracticeTypeAddedListener

class
AddPracticeTypeDialog : AppCompatDialogFragment()
{
  var onPracticeTypeAddedListener : OnPracticeTypeAddedListener? = null
  var onCancelListener : OnCancelListener? = null
  
  override fun
  onCreateView(inflater : LayoutInflater,
               container : ViewGroup?,
               savedInstanceState : Bundle?) : View
  {
    val view = inflater
    .inflate(R.layout.add_practice_type_dialog,
             container,
             false)
    
    dialog
    ?.let {dialog : Dialog ->
      if(!dialog.isShowing)
      {
        dialog.show()
      }
    }
    return view
  }
  
  override fun
  onResume()
  {
    super.onResume()
    
    dialog
    ?.let {dialog : Dialog ->
      
      dialog.window
      ?.let {window : Window ->
        
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
