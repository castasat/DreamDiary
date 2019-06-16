package com.openyogaland.denis.dreamdiary.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import com.openyogaland.denis.dreamdiary.R
import kotlinx.android.synthetic.main.day_fragment.*

public class
DayFragment : Fragment()
{
  override fun onCreateView(inflater : LayoutInflater,
                            container : ViewGroup?,
                            savedInstanceState : Bundle?) : View?
  {
    val view : View =
      inflater.inflate(R.layout.day_fragment,
                       container,
                       false)
    
    return view
  }

}
