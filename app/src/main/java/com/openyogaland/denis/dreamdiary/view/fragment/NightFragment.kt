package com.openyogaland.denis.dreamdiary.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.openyogaland.denis.dreamdiary.R

public class
NightFragment : Fragment()
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
